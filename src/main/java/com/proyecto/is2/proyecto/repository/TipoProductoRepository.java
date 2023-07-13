package com.proyecto.is2.proyecto.repository;

import com.proyecto.is2.proyecto.model.TipoProducto;
import com.proyecto.is2.proyecto.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.hibernate.annotations.Table;

public interface TipoProductoRepository extends JpaRepository<TipoProducto, Long> {
    // public Marca findByDescripcion(String descripcion);
    public TipoProducto findByIdTipoProducto(Long idTipoProducto);

}