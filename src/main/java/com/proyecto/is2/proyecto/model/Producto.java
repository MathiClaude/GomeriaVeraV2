package com.proyecto.is2.proyecto.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;

    @Column(name = "nombreProducto")
    private String nombreProducto;

    @Column(name = "fechaIngreso")
    private String fechaIngreso;

    @Column(name = "esActual")
    private String esActual;

    @Column(name = "precio")
    private Integer precio;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "familiaProducto")
    private String familiaProducto;

    @Column(name = "tipoProducto")
    private String tipoProducto;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "unidadMedida")
    private String unidadMedida;

    @Column(name = "color")
    private String color;

    @Column(name = "procedencia")
    private String procedencia;

    /*public String toString() {
        return email;
    }*/

    /* RELACIONES DE BASE DE DATOS */

    /** Relacion con Rol **/
    /*@ManyToOne
    @JoinColumn(name = "rol_id", referencedColumnName = "idRol")
    private Rol rol;*/

    /* Relacion con UserStory */
    /*@OneToMany(mappedBy = "usuario")
    private Set<UserStory> userStories = new HashSet<>();*/

} /* Se crea un relacion tambien con Proyecto */
