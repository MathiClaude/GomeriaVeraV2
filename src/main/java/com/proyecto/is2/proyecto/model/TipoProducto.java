package com.proyecto.is2.proyecto.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "TipoProducto")
public class TipoProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipoProducto;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "ganancia")
    private Float ganancia;

    public TipoProducto() {

    }
    public TipoProducto(String descripcion) {
        this.descripcion = descripcion;
    }

    public String toString() {
        return descripcion;
    }



    /* RELACIONES DE BASE DE DATOS */

    /* Relacion con Producto */
    // @OneToMany(mappedBy = "marca")
    // private Set<Producto> productos = new HashSet<>();

} /* Se crea un relacion tambien con Proyecto */