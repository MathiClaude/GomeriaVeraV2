package com.proyecto.is2.proyecto.controller.dto;

import lombok.Data;

@Data
public class ReporteCompraDTO {
    private String proveedor;
    private String idCompra;
    private String fechaCompra;

    private String montoTotal;
    private String usuario;
    private String estado;
    private String impuesto;
    
    public ReporteCompraDTO(String proveedor, String idCompra,String fechaCompra, String montoTotal, String usuario,String estado) {
        this.idCompra = idCompra;
        this.proveedor = proveedor;
        this.fechaCompra = fechaCompra;
        this.montoTotal = montoTotal;
        this.usuario = usuario;
        this.estado = estado;
        this.impuesto = "";
    }

   
   
    /*
    "c..name||' '||c.last_name AS cliente, fecha_venta, monto_total , u.username "+
      */
    

    
}