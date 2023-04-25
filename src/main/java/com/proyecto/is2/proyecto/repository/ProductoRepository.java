package com.proyecto.is2.proyecto.repository;

import com.proyecto.is2.proyecto.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    //public Usuario findByEmail(String email);
    public Producto findByIdProducto(Long idProducto);

}
