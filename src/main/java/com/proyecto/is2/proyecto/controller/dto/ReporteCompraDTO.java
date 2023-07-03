package com.proyecto.is2.proyecto.controller.dto;

import lombok.Data;

@Data
public class ReporteCompraDTO {
    private String proveedor;
    private String fechaCompra;
    private String montoTotal;
    private String usuario;
    private String impuesto;
    
    public ReporteCompraDTO(String proveedor, String fechaCompra, String montoTotal, String usuario) {
        this.proveedor = proveedor;
        this.fechaCompra = fechaCompra;
        this.montoTotal = montoTotal;
        this.usuario = usuario;
        this.impuesto = "";
    }

   
   
    /*
    "c..name||' '||c.last_name AS cliente, fecha_venta, monto_total , u.username "+
      */
    

    
}