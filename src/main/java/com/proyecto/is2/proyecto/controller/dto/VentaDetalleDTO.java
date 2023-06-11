package com.proyecto.is2.proyecto.controller.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
// import com.proyecto.is2.proyecto.controller.dto.VentaDetalleDTO;


@Data
public class VentaDetalleDTO {
    private Long idVenta;
    private Float cantidad;
    private Float precio;
    private Float productoId;

}
