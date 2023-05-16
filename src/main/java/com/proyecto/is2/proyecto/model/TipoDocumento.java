package com.proyecto.is2.proyecto.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tipoDocumento")
public class TipoDocumento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipoDocumento;
    
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "descripcion")
    private String descripcion;
    
    @Column(name = "formato_valido")
    private String formatoValido;
    
    @Column(name = "longitud_valida")
    private int longitudValida;
    
    @Column(name = "caracteres_validos")
    private String caracteresValidos;
    
    @Column(name = "obligatorio")
    private boolean obligatorio;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    //@ManyToOne(fetch = FetchType.LAZY)
   /* @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false, referencedColumnName = "idCliente")
    private Cliente cliente;*/


} /* Se crea un relacion tambien con Proyecto */
