package com.proyecto.is2.proyecto.repository;

import com.proyecto.is2.proyecto.model.TipoProducto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoProductoRepository extends JpaRepository<TipoProducto, Long> {
    // public Marca findByDescripcion(String descripcion);
    public TipoProducto findByIdTipoProducto(Long idTipoProducto);

}