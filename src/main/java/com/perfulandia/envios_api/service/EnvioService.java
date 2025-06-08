package com.perfulandia.envios_api.service;

import com.perfulandia.envios_api.dto.EnvioDTO;
import com.perfulandia.envios_api.model.Envio;
import com.perfulandia.envios_api.repository.EnvioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnvioService {
     @Autowired
    private EnvioRepository envioRepository;

    // Guardar un Envío
    public Envio guardarEnvio(Envio envio) {
        return envioRepository.save(envio);
    }

    // Obtener un Envío por ID 
    public Optional<Envio> obtenerEnvioPorId(Integer idEnvio) {
        return envioRepository.findById(idEnvio);
    }

    // Obtener un Envío por ID en formato DTO
    public Optional<EnvioDTO> obtenerEnvioDTOporId(Integer idEnvio) {
        return envioRepository.findById(idEnvio)
                .map(envio -> new EnvioDTO(
                        envio.getIdEnvio(),
                        envio.getIdVenta(),
                        envio.getDireccionEnvio(),
                        envio.getEstadoEnvio(),
                        envio.getFechaEnvio(),
                        envio.getFechaEntrega()
                ));
    }

    // Listar todos los Envíos en formato DTO
    public List<EnvioDTO> obtenerTodosLosEnviosDTO() {
        return envioRepository.findAll()
                .stream()
                .map(envio -> new EnvioDTO(
                        envio.getIdEnvio(),
                        envio.getIdVenta(),
                        envio.getDireccionEnvio(),
                        envio.getEstadoEnvio(),
                        envio.getFechaEnvio(),
                        envio.getFechaEntrega()
                ))
                .collect(Collectors.toList());
    }

    // Actualizar estado de un envío
    public Envio actualizarEstado(Integer idEnvio, String nuevoEstado) throws Exception {
        Optional<Envio> optionalEnvio = envioRepository.findById(idEnvio);
        if (optionalEnvio.isPresent()) {
            Envio envio = optionalEnvio.get();
            envio.setEstadoEnvio(nuevoEstado);
            return envioRepository.save(envio);
        } else {
            throw new Exception("Envío no encontrado");
        }
    }

    // Obtener todos los envíos
    public List<Envio> obtenerTodosLosEnvios() {
        return envioRepository.findAll();
    }

}
