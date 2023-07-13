package com.proyecto.is2.proyecto.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Collection;

@Getter
@Setter
@Entity
@Table(name = "Compra")
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCompra;

    @Column(name = "montoCompra")
    private String montoCompra;

    @Column(name = "fechaCompra")
    private String fechaCompra;

    @Column(name = "esActual")
    private String esActual;

    @Column(name = "montoTotal")
    private BigDecimal montoTotal;

    @Column(name = "estado")
    private String estado;

    @Column(name = "motivoAnulacion")
    private String motivoAnulacion;
    
    @Column(name = "usuarioIdAnulacion")
    private String usuarioIdAnulacion;

    @Column(name = "usuarioIdRecepcion")
    private String usuarioIdRecepcion;

    public Compra() {

    }
    /*public Venta(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }*/ //constructor aun no definido

    public String toString() {
        return montoCompra;
    }


    /* RELACIONES DE BASE DE DATOS */

    
    /* Relacion con usuario */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "proveedor_id", referencedColumnName = "idProveedor")
    @JsonBackReference
    private Proveedor proveedor;

    /* Relacion con cliente */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", referencedColumnName = "idUsuario")
    @JsonBackReference
    private Usuario usuario;

    /* Relacion con detalles */
    //@OneToMany(mappedBy = "compra", cascade = CascadeType.ALL)
    //private Collection<CompraDetalle> items;

    /* Relacion con ComprobanteVenta */
    /*@ManyToOne
    @JoinColumn(name = "comprobante_venta_id", referencedColumnName = "idComprobante")
    private Comprobante comprobante;*/

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL)
    private List<Factura> facturas;

} 
