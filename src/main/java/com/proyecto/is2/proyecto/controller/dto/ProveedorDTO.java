package com.proyecto.is2.proyecto.controller.dto;
import com.proyecto.is2.proyecto.model.Contacto;

import lombok.Data;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

@Data
public class ProveedorDTO {
    private Long idProveedor;
    private String nombre;
    private String razonSocial;
    private String contacto;
    private String esActual;
    private String tipoDocumento;
    private String tipoPersona;
    private String telefono;
    private String ruc;
    private String direccion;

    List<Contacto> contactos;
}
