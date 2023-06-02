package com.proyecto.is2.proyecto.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

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

    // @Column(name = "nombreFantasia")
    // private String nombreFantasia;

    @Column(name = "razonSocial")
    private String razonSocial;

    @Column(name = "contacto") //Nombre de contacto 
    private String contacto;

    @Column(name = "esActual") // Estado del proveedor [Activo/Inactivo]
    private String esActual;

    @Column(name = "ruc")// Corresponde a documento
    private String ruc;

    @Column(name = "tipoDocumento")
    private String tipoDocumento;

    @Column(name = "tipoPersona")
    private String tipoPersona;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "telefono")
    private String telefono;

    @OneToMany(mappedBy = "proveedor")
    private List<Contacto> contactos;

} /* Se crea un relacion tambien con Proyecto */
