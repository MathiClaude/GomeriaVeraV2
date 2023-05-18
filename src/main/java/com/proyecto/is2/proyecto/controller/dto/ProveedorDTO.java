package com.proyecto.is2.proyecto.controller.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class ProveedorDTO {
    private Long idProveedor;
    private String nombre;
    private String nombreFantasia;
    private String nombreContacto;
    private String ruc;
    private String contacto;
    private String esActual;

}
