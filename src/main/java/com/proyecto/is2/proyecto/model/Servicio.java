package com.proyecto.is2.proyecto.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Servicio")
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idServicio;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "descripcionServicio")
    private String descripcionServicio;

    @Column(name = "precio")
    private Integer precio;

    @Column(name = "impuesto")
    private Integer impuesto;

     /** Relación con tipo producto **/
     @ManyToOne
     @JoinColumn(name = "tipo_producto_id", referencedColumnName = "idTipoProducto")
     private TipoProducto tipoProducto;
 

} /* Se crea un relacion tambien con Proyecto */
