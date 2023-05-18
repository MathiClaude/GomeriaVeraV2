package com.proyecto.is2.proyecto.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Marcas", uniqueConstraints = @UniqueConstraint(columnNames = "descripcion"))
public class Marca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMarca;

    @Column(name = "descripcion")
    private String descripcion;



} /* Se crea un relacion tambien con Proyecto */