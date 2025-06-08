package com.perfulandia.envios_api.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Table(name= "envios")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Envio {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_envio")
    private Integer idEnvio;

    @Column(name = "id_venta")
    private Integer idVenta;

    @Column(name = "direccion_envio")
    private String direccionEnvio;

    @Column(name = "estado_envio")
    private String estadoEnvio;

    @Column(name = "fecha_envio")
    private Date fechaEnvio;

    @Column(name = "fecha_entrega")
    private Date fechaEntrega;

}
