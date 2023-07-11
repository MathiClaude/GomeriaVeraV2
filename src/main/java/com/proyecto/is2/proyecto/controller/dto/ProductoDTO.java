package com.proyecto.is2.proyecto.controller.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class ProductoDTO {
    private Long idProducto;
    private String nombreProducto;
    private String codigo;
    //private String familiaProducto;
    private String unidadMedida;
    private String impuesto;
    private String proveedor;
    //private String tipoProducto;
    private String fechaIngreso;
    private String esActual;
    private String descripcion;

    private Long idTipoProducto;//nueva columna para el id
    private Long idMarca;//nueva columna para el id
    private Long idUnidadMedida;//nueva columna para el id
    private Long idProveedor;//nueva columna para el id

    private Integer precio;//sacar. Es solo para pruebas
    private Integer precioCompra;
    private Integer tmpPrecio;
    private Integer cantidad;//sacar Es solo para prubas
    private Integer cantidadMinima;
    private String procedencia;


}
