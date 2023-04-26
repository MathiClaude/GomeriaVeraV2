package com.proyecto.is2.proyecto.controller.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class VentaDTO {
    private Long idVenta;
    private String montoVenta;
    private String fechaVenta;
    private String esActual;
    private Long idUsuario;

}
