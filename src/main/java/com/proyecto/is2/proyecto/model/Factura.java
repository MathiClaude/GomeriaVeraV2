package com.proyecto.is2.proyecto.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Collection;

@Getter
@Setter
@Entity
@Table(name = "Factura")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFactura;

    @Column(name = "monto")
    private String monto;

    @Column(name = "fecha")
    private String fecha;

    @Column(name = "nroDocumento")
    private String nroDocumento;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "esActual")
    private String esActual;

    @Column(name = "montoTotal")
    private BigDecimal montoTotal;


    public Factura() {

    }
    /*public Venta(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }*/ //constructor aun no definido

    public String toString() {
        return nroDocumento;
    }


    /* RELACIONES DE BASE DE DATOS */

    /* Relacion con compra */
    @ManyToOne
    @JoinColumn(name = "compra_id", referencedColumnName = "idCompra")
    private Compra compra;
    
    /* Relacion con usuario */
    /*@ManyToOne
    @JoinColumn(name = "proveedor_id", referencedColumnName = "idProveedor")
    private Proveedor proveedor;*/

    /* Relacion con cliente */
    /*@ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "idUsuario")
    private Usuario usuario;*/

    /* Relacion con detalles */
    //@OneToMany(mappedBy = "compra", cascade = CascadeType.ALL)
    //private Collection<CompraDetalle> items;

    /* Relacion con ComprobanteVenta */
    /*@ManyToOne
    @JoinColumn(name = "comprobante_venta_id", referencedColumnName = "idComprobante")
    private Comprobante comprobante;*/

} 
