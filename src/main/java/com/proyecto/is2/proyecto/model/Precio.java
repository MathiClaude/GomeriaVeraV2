package com.proyecto.is2.proyecto.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Precio")
public class Precio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPrecio;

    @Column(name = "precioProducto")
    private String precioProducto;

    @Column(name = "fechaIngreso")
    private String fechaIngreso;

    @Column(name = "esActual")
    private String esActual;

    public Precio() {

    }
    /*public Venta(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }*/ //constructor aun no definido

    public String toString() {
        return precioProducto;
    }


    /* RELACIONES DE BASE DE DATOS */

    
    /* Relacion con producto */
    @ManyToOne
    @JoinColumn(name = "producto_id", referencedColumnName = "idProducto")
    private Producto producto;

    /* Relacion con ComprobanteVenta */
    /*@ManyToOne
    @JoinColumn(name = "comprobante_venta_id", referencedColumnName = "idComprobante")
    private Comprobante comprobante;*/




} 
