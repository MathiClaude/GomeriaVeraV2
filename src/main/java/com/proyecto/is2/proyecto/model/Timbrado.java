package com.proyecto.is2.proyecto.model;
import com.proyecto.is2.proyecto.Util.EstadosTimbrado;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Timbrado")
public class Timbrado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTimbrado;
    
    @Column(name = "fchDesde")
    private String fchDesde;

    @Column(name = "fchHasta")
    private String fchHasta;

    
    @Column(name = "nroTimbrado")
    private String nroTimbrado;

    @Column(name = "nroDesde")
    private Integer nroDesde;

    @Column(name = "nroHasta")
    private Integer nroHasta;
    
    @Column(name = "estado")
    private String estado;

    @Column(name = "nro_actual")
    private String nro_actual;


    public Timbrado() {
        this.estado = EstadosTimbrado.ACTIVO.name();
    }

    /* ************************ */
    /* RELACIONES ENTRE OBJETOS */
    /* ************************ */    


} 