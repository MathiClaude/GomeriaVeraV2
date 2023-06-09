package com.proyecto.is2.proyecto.repository;

import com.proyecto.is2.proyecto.model.CompraDetalle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompraDetalleRepository extends JpaRepository<CompraDetalle, Long> {
    //public Usuario findByEmail(String email);
    public CompraDetalle findByIdCompraDetalle(Long idCompraDetalle);

}
