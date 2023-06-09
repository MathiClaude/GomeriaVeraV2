package com.proyecto.is2.proyecto.controller.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
public class CompraDTO {
    private Long idCompra;
    private String montoCompra;
    private String fechaCompra;
    private String esActual;
    private BigDecimal montoTotal;
    private Long idUsuario;
    private Long idProveedor;

}
