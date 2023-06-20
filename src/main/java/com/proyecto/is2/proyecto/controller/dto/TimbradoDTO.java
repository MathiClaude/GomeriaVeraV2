package com.proyecto.is2.proyecto.controller.dto;

import lombok.Data;

@Data
public class TimbradoDTO {
    private Long idTimbrado;
    private String nroTimbrado;
    private String estado;
    private String fchDesde;
    private String fchHasta;
    private Integer nroDesde;
    private Integer nroHasta;


    
}