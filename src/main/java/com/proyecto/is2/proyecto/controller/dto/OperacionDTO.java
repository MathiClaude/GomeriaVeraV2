package com.proyecto.is2.proyecto.controller.dto;

import lombok.Data;

@Data
public class OperacionDTO {
    private Long idOperacion;
    private Long idCaja;
    private Long idUsuario;    
    private String concepto;
    private String saldoAnterior;
    private String monto;
    private String saldoPosterior;
    private String fechaOperacion;
    
}