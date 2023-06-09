package com.proyecto.is2.proyecto.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Operacion")
public class Operacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOperacion;

    @Column(name = "idCaja")
    private Long idCaja;

    @Column(name = "idUsuario")
    private Long idUsuario;


    @Column(name = "concepto")
    private String concepto;

    @Column(name = "saldoAnterior")
    private String saldoAnterior;

    @Column(name = "monto")
    private String monto;

    
    @Column(name = "saldoPosterior")
    private String saldoPosterior;

    @Column(name = "fechaOperacion")
    private String fechaOperacion;

    
} /* Se crea un relacion tambien con Proyecto */