package com.proyecto.is2.proyecto.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "UnidadMedida")
public class UnidadMedida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUnidadMedida;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "descripcion")
    private String descripcion;

    public UnidadMedida() {

    }
    public UnidadMedida(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public String toString() {
        return codigo;
    }

    /* RELACIONES DE BASE DE DATOS */

    /* Relacion con Permiso */
    /*@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "rol_permiso",
            joinColumns = {@JoinColumn(name = "rol_id")},
            inverseJoinColumns = {@JoinColumn(name = "permiso_id")})
    private Set<Permiso> permisos = new HashSet<>();*/

    /* Relacion con Usuario
    @OneToMany(mappedBy = "rol")
    private Set<Usuario> usuarios = new HashSet<>();*/

}
