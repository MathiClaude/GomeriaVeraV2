package com.proyecto.is2.proyecto.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "CompraDetalle")
public class CompraDetalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCompraDetalle;

    @Column(name = "cantidad")
    Float cantidad;

    @Column(name = "precio")
    Float precio;

    /* ************************ */
    /* RELACIONES ENTRE OBJETOS */
    /* ************************ */

    @ManyToOne
    @JoinColumn(name = "producto_id", referencedColumnName = "idProducto")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "compra_id", referencedColumnName = "idCompra")
    private Compra compra;
} 
