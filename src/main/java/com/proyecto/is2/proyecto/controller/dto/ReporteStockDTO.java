package com.proyecto.is2.proyecto.controller.dto;

import lombok.Data;

@Data
public class ReporteStockDTO {
    private String codigo;
    private String producto;
    private String tipoProducto;
    private String proveedor;
    private Integer cantidad;
    private String unidadMedida;
    private Float precio;
    // (codigo, nombreProducto, tipo_producto_id,proveedor,  cantidad, unidad_medida_id, precio)

    public ReporteStockDTO(String codigo, String producto, String tipoProducto, String proveedor, Integer cantidad,String unidadMedida, Float precio) {
        this.codigo=codigo;
        this.producto = producto;
        this.tipoProducto = tipoProducto;
        this.proveedor = proveedor;
        this.cantidad = cantidad;
        this.unidadMedida = unidadMedida;
        this.precio = precio;
    }   
   
    /*
    "c..name||' '||c.last_name AS cliente, fecha_venta, monto_total , u.username "+
      */
    

    
}