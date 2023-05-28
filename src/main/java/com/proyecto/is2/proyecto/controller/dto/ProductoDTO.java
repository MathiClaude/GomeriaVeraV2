package com.proyecto.is2.proyecto.controller.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class ProductoDTO {
    private Long idProducto;
    private String nombreProducto;
    private String codigo;
    private String familiaProducto;
    private String unidadMedida;
    private String impuesto;
    private String proveedor;
    private String tipoProducto;
    private String fechaIngreso;
    private String esActual;
    private String descripcion;

    private Integer precio;//sacar. Es solo para pruebas
    private Integer cantidad;//sacar Es solo para prubas
    private String procedencia;

}
