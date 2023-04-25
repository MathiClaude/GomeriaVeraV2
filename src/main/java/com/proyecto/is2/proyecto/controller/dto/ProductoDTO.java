package com.proyecto.is2.proyecto.controller.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class ProductoDTO {
    private Long idProducto;
    private String nombreProducto;
    private String fechaIngreso;
    private String esActual;

}
