package com.proyecto.is2.proyecto.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    @Column(name = "name")
    private String name;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "documentType")
    private String documentType;

    @Column(name = "documentNumber")
    private String documentNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "telephone")
    private String telephone;

    public String toString() {
        return email;
    }

    /* RELACIONES DE BASE DE DATOS */

    /** Relacion con Rol **///Agregar relacion con venta. Falta tabla
    //@ManyToOne
    //@JoinColumn(name = "rol_id", referencedColumnName = "idRol")
    //private Rol rol;

    /* Relacion con UserStory */
    //@OneToMany(mappedBy = "usuario")
    //private Set<UserStory> userStories = new HashSet<>();

} /* Se crea un relacion tambien con Proyecto */