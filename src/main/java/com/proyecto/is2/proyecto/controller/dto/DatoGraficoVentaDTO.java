package com.proyecto.is2.proyecto.controller.dto;

import lombok.Data;

@Data
public class DatoGraficoVentaDTO {
    private String mes;
    private Integer cantidad;

    public DatoGraficoVentaDTO(String mes,String cantidad){
        this.mes = mes;
        this.cantidad = Integer.parseInt( cantidad);
    }

    
}