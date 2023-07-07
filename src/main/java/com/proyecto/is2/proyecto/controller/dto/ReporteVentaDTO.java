package com.proyecto.is2.proyecto.controller.dto;

import lombok.Data;

@Data
public class ReporteVentaDTO {
    private String cliente;
    private String fechaVenta;
    private String montoTotal;
    private String usuario;
    private String nroFactura;
    private String timbrado;
    private String impuesto;
    
    public ReporteVentaDTO(String cliente, String fechaVenta, String montoTotal, String usuario) {
        this.cliente = cliente;
        this.fechaVenta = fechaVenta;
        this.montoTotal = montoTotal;
        this.usuario = usuario;
        this.nroFactura = "";
        this.timbrado = "";
        this.impuesto = "";
    }

    public ReporteVentaDTO(String cliente, String fechaVenta, String montoTotal, String usuario,String nroFactura, String impuesto) {
        this.cliente = cliente;
        this.fechaVenta = fechaVenta;
        this.montoTotal = montoTotal;
        this.usuario = usuario;
        this.nroFactura = nroFactura;
        this.timbrado = "";
        this.impuesto = impuesto;
    }

   
   
    /*
    "c.name||' '||c.last_name AS cliente, fecha_venta, monto_total , u.username "+
      */
    

    
}