package com.proyecto.is2.proyecto.controller.dto;

import lombok.Data;
import java.util.List;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.proyecto.is2.proyecto.model.Contacto;
import com.proyecto.is2.proyecto.model.Factura;

@Data
public class CompraDTO {
    private Long idCompra;
    private String montoCompra;
    private String fechaCompra;
    private String esActual;
    private String estado;
    private BigDecimal montoTotal;
    private Long idUsuario;
    private long idProveedor;
    List<Factura> facturas;

    //public CompraDTO() {
     //   this.idProveedor = new ProveedorDTO();
    //}

}
