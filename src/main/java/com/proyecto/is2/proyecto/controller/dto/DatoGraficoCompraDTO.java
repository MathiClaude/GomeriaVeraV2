package com.proyecto.is2.proyecto.controller.dto;

import lombok.Data;

@Data
public class DatoGraficoCompraDTO {
    private String mes;
    private Integer cantidad;

    public DatoGraficoCompraDTO(String mes,String cantidad){
        this.mes = mes;
        this.cantidad = Integer.parseInt( cantidad);
    }

    
}