package com.proyecto.is2.proyecto.controller.dto;

import lombok.Data;

@Data
public class ReporteCajaDTO {
    private String descripcion;  
    private String usuario;

    private String concepto;
    private String monto;
    private String fechaOpe;
    private String idOpe;

    //c.descripcion, u.username , o.concepto , o.monto , o.fecha_operacion, o.id_operacion 
    public ReporteCajaDTO(String descripcion, String usuario, String concepto, String monto, String fechaOpe, String idOpe) {
        this.descripcion = descripcion;
        this.usuario = usuario;
        this.concepto = concepto;
        this.monto = monto;
        this.fechaOpe = "";
        this.idOpe = idOpe;
    }
    
   
   
    /*
    "c.name||' '||c.last_name AS cliente, fecha_venta, monto_total , u.username "+
      */
    

    
}