package com.proyecto.is2.proyecto.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Venta")
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVenta;

    @Column(name = "montoVenta")
    private String montoVenta;

    @Column(name = "fechaVenta")
    private String fechaVenta;

    @Column(name = "esActual")
    private String esActual;

    @Column(name = "montoTotal")
    private BigDecimal montoTotal;

    @Column(name = "montoImpuesto")
    private BigDecimal montoImpuesto;

    @Column(name = "nroFactura")
    private BigDecimal nroFactura;

    // @Column(name = "timbrado")
    // private String timbrado;

    @Column(name = "documento")
    private String documento;

    public Venta() {

    }
    /*public Venta(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }*/ //constructor aun no definido

    public String toString() {
        return montoVenta;
    }


    /* RELACIONES DE BASE DE DATOS */

    
    /* Relacion con usuario */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id", referencedColumnName = "idCliente")
    @JsonBackReference
    private Cliente cliente;

    /* Relacion con usuario */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", referencedColumnName = "idUsuario")
    @JsonBackReference
    private Usuario usuario;

    /* Relacion con timbrado */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "timbrado_id", referencedColumnName = "idTimbrado")
    @JsonBackReference
    private Timbrado timbrado;

    /* Relacion con ComprobanteVenta */
    /*@ManyToOne
    @JoinColumn(name = "comprobante_venta_id", referencedColumnName = "idComprobante")
    private Comprobante comprobante;*/




} 
