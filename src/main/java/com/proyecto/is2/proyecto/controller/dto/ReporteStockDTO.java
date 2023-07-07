package com.proyecto.is2.proyecto.controller.dto;

import lombok.Data;

@Data
public class ReporteStockDTO {
    private String proveedor;
    private String producto;
    private String tipoProducto;
    private String marca;

    
    public ReporteStockDTO(String proveedor, String producto, String tipoProducto, String marca, String precio) {
        this.proveedor = proveedor;
        this.producto = producto;
        this.tipoProducto = tipoProducto;
        this.marca = marca;
        this.precio = precio;
    }
    private String precio;

    

   
   
    /*
    "c..name||' '||c.last_name AS cliente, fecha_venta, monto_total , u.username "+
      */
    

    
}