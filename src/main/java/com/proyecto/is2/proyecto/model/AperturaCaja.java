package com.proyecto.is2.proyecto.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "AperturaCaja")
public class AperturaCaja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAperturaCaja;
    
    @Column(name = "idCaja")
    private Long idCaja;

    @Column(name = "idUsuario")
    private Long idUsuario;

    
    @Column(name = "fechaApertura")
    private String fechaApertura;

    @Column(name = "fechaCierre")
    private String fechaCierre;

    @Column(name = "saldoApertura")
    private String saldoApertura;

    @Column(name = "saldoCierre")
    private String saldoCierre;

    @Column(name = "estado")
    private String estado;


} /* Se crea un relacion tambien con Proyecto */