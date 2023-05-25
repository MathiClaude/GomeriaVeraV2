package com.proyecto.is2.proyecto.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Proveedor")
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProveedor;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "nombreFantasia")
    private String nombreFantasia;


    @Column(name = "nombreContacto")
    private String nombreContacto;

    @Column(name = "contacto")
    private String contacto;

    @Column(name = "esActual")
    private String esActual;

    @Column(name = "ruc")
    private String ruc;

    @Column(name = "tipoDocumento")
    private String tipoDocumento;

    @Column(name = "tipoPersona")
    private String tipoPersona;

    @Column(name = "direccion")
    private String direccion;

} /* Se crea un relacion tambien con Proyecto */
