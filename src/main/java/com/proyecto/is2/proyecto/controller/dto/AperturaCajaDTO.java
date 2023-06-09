package com.proyecto.is2.proyecto.controller.dto;

import lombok.Data;

@Data
public class AperturaCajaDTO {
    private Long idApertura;
    private Long idCaja;
    private Long idUsuario;    
    private String fechaApertura;
    private String fechaCierre;
    private String saldoApertura;
    private String saldoCierre;
    private String estado;
    
}