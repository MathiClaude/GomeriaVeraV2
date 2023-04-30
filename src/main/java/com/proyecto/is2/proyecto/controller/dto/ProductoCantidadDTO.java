package com.proyecto.is2.proyecto.controller.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class ProductoCantidadDTO {
    private Long ProductoCantidad;
    private String cantidad;
    private String fechaIngreso;
    private String esActual;
    private Long producto_id;

}
