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
@Table(name = "VentaDetalle")
public class VentaDetalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVentaDetalle;

    @Column(name = "cantidad")
    Float cantidad;

    @Column(name = "precio")
    Float precio;


    @ManyToOne
    @JoinColumn(name = "producto_id", referencedColumnName = "idProducto")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "venta_id", referencedColumnName = "idVenta")
    private Venta venta;
} 
