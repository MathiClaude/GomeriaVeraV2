package com.proyecto.is2.proyecto.repository;

import com.proyecto.is2.proyecto.model.ProductoCantidad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoCantidadRepository extends JpaRepository<ProductoCantidad, Long> {
    //public Usuario findByEmail(String email);
    public ProductoCantidad findByIdProductoCantidad(Long idProductoCantidad);

}
