package com.proyecto.is2.proyecto.controller.dto;

import lombok.Data;

@Data
public class ReporteCajaDTO {
    private String proveedor;  
    private String descripcion;

    private String fechaOperacion;
    private String monto;
    private String usuario;

    
    public ReporteCajaDTO(String proveedor, String descripcion, String fechaOperacion, String monto, String usuario) {
        this.proveedor = proveedor;
        this.descripcion = descripcion;
        this.fechaOperacion = fechaOperacion;
        this.monto = monto;
        this.usuario = usuario;
    }
    
   
   
    /*
    "c.name||' '||c.last_name AS cliente, fecha_venta, monto_total , u.username "+
      */
    

    
}