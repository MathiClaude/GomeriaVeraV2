package com.proyecto.is2.proyecto.controller.dto;

import lombok.Data;

@Data
public class ReporteStockDTO {
    private String proveedor;
    private String idCompra;
    private String fechaCompra;
    private Float montoCompra;
    private String usuario;
    private String estado;
    
    
    public ReporteStockDTO(String proveedor, String idCompra, String fechaCompra, String montoCompra, String usuario,String estado) {
        this.proveedor = proveedor;
        this.idCompra = idCompra;
        this.fechaCompra = fechaCompra;
        this.montoCompra = new Float(montoCompra);
        this.usuario = usuario;
        this.estado = estado;
    }
    private String precio;

    

   
   
    /*
    "c..name||' '||c.last_name AS cliente, fecha_venta, monto_total , u.username "+
      */
    

    
}