package com.perfulandia.envios_api.controller;

import com.perfulandia.envios_api.dto.EnvioDTO;
import com.perfulandia.envios_api.model.Envio;
import com.perfulandia.envios_api.service.EnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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

    // Obtener un envío por su ID (formato DTO)
    @GetMapping("/dto/{id}")
    public ResponseEntity<EnvioDTO> obtenerEnvioDTOporId(@PathVariable Integer id) {
        Optional<EnvioDTO> envioDTOOpt = envioService.obtenerEnvioDTOporId(id);
        return envioDTOOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Listar todos los envíos en formato DTO
    @GetMapping("/dto")
    public ResponseEntity<List<EnvioDTO>> obtenerTodosLosEnviosDTO() {
        List<EnvioDTO> envios = envioService.obtenerTodosLosEnviosDTO();
        return ResponseEntity.ok(envios);
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
