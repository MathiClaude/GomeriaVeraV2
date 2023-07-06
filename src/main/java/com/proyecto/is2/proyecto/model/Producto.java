package com.proyecto.is2.proyecto.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonBackReference;

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

    @Column(name = "precio")
    private Integer precio;

    @Column(name = "cantidad")
    private Integer cantidad;

    /*@Column(name = "familiaProducto")
    private String familiaProducto;*/

    /*@Column(name = "tipoProducto")
    private String tipoProducto;*/

    @Column(name = "codigo")
    private String codigo;

    /*@Column(name = "unidadMedida")
    private String unidadMedida;*/

    @Column(name = "procedencia")
    private String procedencia;

    /*@Column(name = "proveedor")
    private String proveedor;*/

    @Column(name = "impuesto")
    private String impuesto;

    @Column(name = "descripcion")
    private String descripcion;


    public Producto() {

    }
    /*public Producto(String nombreProducto, String tipoProducto) {
        this.nombreProducto = nombreProducto;
        this.tipoProducto = tipoProducto;
    }*/

    public Producto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
        //this.tipoProducto = tipoProducto;
    }

    public String toString() {
        return nombreProducto;
    }

    /* RELACIONES DE BASE DE DATOS */

    /** Relacion con tipo producto **/
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_producto_id", referencedColumnName = "idTipoProducto")
    @JsonBackReference
    private TipoProducto tipoProducto;

    /** Relacion con Marcas **/
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "marca_id", referencedColumnName = "idMarca")
    @JsonBackReference
    private Marca marca;

    /** Relacion con Unidad Medida **/
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "unidad_medida_id", referencedColumnName = "idUnidadMedida")
    @JsonBackReference
    private UnidadMedida unidadMedida;

    /** Relacion con Unidad Medida **/
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "proveedor_id", referencedColumnName = "idProveedor")
    @JsonBackReference
    private Proveedor proveedor;


    /* Relacion con UserStory */
    /*@OneToMany(mappedBy = "usuario")
    private Set<UserStory> userStories = new HashSet<>();*/

} /* Se crea un relacion tambien con Proyecto */
