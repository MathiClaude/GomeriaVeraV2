package com.proyecto.is2.proyecto.controller.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class PrecioDTO {
    private Long Precio;
    private String precioProducto;
    private String fechaIngreso;
    private String esActual;
    private Long producto_id;

}
