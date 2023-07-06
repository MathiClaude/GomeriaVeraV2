package com.proyecto.is2.proyecto.controller.dto;

import lombok.Data;

@Data
public class ReporteVentaProductoDTO {
    private String idProducto;
    private String descripcion;
    private String cantProducto;
    private String monto;
    
    public ReporteVentaProductoDTO(String idProducto, String descripcion, String cantProducto, String monto) {
        this.idProducto = idProducto;
        this.descripcion = descripcion;
        this.cantProducto = cantProducto;
        this.monto = monto;
    }
    
    
   
   
    /*
    "c.name||' '||c.last_name AS cliente, fecha_venta, monto_total , u.username "+
      */
    

    
}