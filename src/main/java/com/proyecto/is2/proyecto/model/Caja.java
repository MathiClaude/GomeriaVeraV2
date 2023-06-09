package com.proyecto.is2.proyecto.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Caja")
public class Caja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCaja;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "pinCaja")
    private String pinCaja;

    @Column(name = "estado")
    private String estado;

    @Column(name = "saldoInicial")
    private String saldoInicial;
} /* Se crea un relacion tambien con Proyecto */