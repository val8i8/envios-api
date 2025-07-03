package com.perfulandia.envios_api.controller;

import com.perfulandia.envios_api.dto.EnvioDTO;
import com.perfulandia.envios_api.model.Envio;
import com.perfulandia.envios_api.service.EnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/envios")

public class EnvioController {
    @Autowired
    private EnvioService envioService;

    // Crear un nuevo envío
    @PostMapping
    public ResponseEntity<Envio> crearEnvio(@RequestBody Envio envio) {
        Envio nuevoEnvio = envioService.guardarEnvio(envio);
        return ResponseEntity.ok(nuevoEnvio);
    }

    // Obtener un envío por su ID (formato entidad)
    @GetMapping("/{id}")
    public ResponseEntity<Envio> obtenerEnvioPorId(@PathVariable Integer id) {
        Optional<Envio> envioOpt = envioService.obtenerEnvioPorId(id);
        return envioOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Obtener un envío por su ID (formato DTO) + HATEOAS
    @GetMapping("/dto/{id}")
    public ResponseEntity<EntityModel<EnvioDTO>> obtenerEnvioDTOporId(@PathVariable Integer id) {
        Optional<EnvioDTO> envioDTOOpt = envioService.obtenerEnvioDTOporId(id);

        return envioDTOOpt.map(envioDTO -> {
            EntityModel<EnvioDTO> recurso = EntityModel.of(envioDTO);
            recurso.add(linkTo(methodOn(EnvioController.class).obtenerEnvioDTOporId(id)).withSelfRel());
            recurso.add(linkTo(methodOn(EnvioController.class).obtenerTodosLosEnviosDTO()).withRel("todosLosEnvios"));
            return ResponseEntity.ok(recurso);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Listar todos los envíos en formato DTO + HATEOAS
    @GetMapping("/dto")
    public ResponseEntity<CollectionModel<EntityModel<EnvioDTO>>> obtenerTodosLosEnviosDTO() {
        List<EnvioDTO> envios = envioService.obtenerTodosLosEnviosDTO();

        List<EntityModel<EnvioDTO>> recursos = envios.stream()
                .map(envioDTO -> {
                    EntityModel<EnvioDTO> recurso = EntityModel.of(envioDTO);
                    recurso.add(linkTo(methodOn(EnvioController.class)
                            .obtenerEnvioDTOporId(envioDTO.getIdEnvio())).withSelfRel());
                    return recurso;
                })
                .collect(Collectors.toList());

        CollectionModel<EntityModel<EnvioDTO>> coleccion = CollectionModel.of(recursos);
        coleccion.add(linkTo(methodOn(EnvioController.class).obtenerTodosLosEnviosDTO()).withSelfRel());

        return ResponseEntity.ok(coleccion);
    }

    // Actualizar el estado de un envío
    @PutMapping("/{id}/estado")
    public ResponseEntity<Envio> actualizarEstadoEnvio(@PathVariable Integer id, @RequestParam String nuevoEstado) {
        try {
            Envio envioActualizado = envioService.actualizarEstado(id, nuevoEstado);
            return ResponseEntity.ok(envioActualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Listar todos los envíos (entidad completa)
    @GetMapping
    public ResponseEntity<List<Envio>> obtenerTodosLosEnvios() {
        List<Envio> envios = envioService.obtenerTodosLosEnvios();
        return ResponseEntity.ok(envios);
    }

}
