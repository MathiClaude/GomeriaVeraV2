package com.proyecto.is2.proyecto.controller.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
// import com.proyecto.is2.proyecto.controller.dto.VentaDetalleDTO;


@Data
public class VentaDTO {
    private Long idVenta;
    private String montoVenta;
    private String fechaVenta;
    private String esActual;
    private BigDecimal montoTotal;
    private Long idUsuario;
    private Long idCliente;

    // private Set<VentaDetalleDTO> ventaDetalle;

}
