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

    /*public String toString() {
        return email;
    }*/

    /* RELACIONES DE BASE DE DATOS */

    /** Relacion con Rol **/
    @ManyToOne
    @JoinColumn(name = "rol_id", referencedColumnName = "idRol")
    private Rol rol;

    /* Relacion con UserStory */
    @OneToMany(mappedBy = "usuario")
    private Set<UserStory> userStories = new HashSet<>();

} /* Se crea un relacion tambien con Proyecto */
