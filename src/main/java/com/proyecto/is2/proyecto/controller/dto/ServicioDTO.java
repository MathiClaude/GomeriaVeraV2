package com.proyecto.is2.proyecto.controller.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class ServicioDTO {
    private Long idServicio;
    private String nombre;
    private String codigo;
    private String descripcionServicio;
    private Integer precio;
    private Integer impuesto;    
    private Long idTipoProducto;
    
    

}
