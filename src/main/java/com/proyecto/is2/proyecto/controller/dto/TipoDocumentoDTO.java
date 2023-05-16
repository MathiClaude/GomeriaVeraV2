package com.proyecto.is2.proyecto.controller.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class TipoDocumentoDTO {
    
    private Long idTipoDocumento;//comentario que quiera : D PONELE QUE SE CREA SOLOS NJSJSJS
    
    private String nombre;////comentario que quiera : D CI, RUC, PASAPORTE
    
    private String descripcion;//comentario que quiera : D CEDULA DE IDENTIDAD, XD
    
    private String formatoValido;
    
    private int longitudValida;
    
    private String caracteresValidos;
    
    private boolean obligatorio;
    
    private LocalDateTime fechaCreacion;
    
    private LocalDateTime fechaActualizacion;
    
    //private Long clienteId;

    // getters y setters
    
}
