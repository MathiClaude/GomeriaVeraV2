package com.proyecto.is2.proyecto.repository;

import com.proyecto.is2.proyecto.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompraRepository extends JpaRepository<Compra, Long> {
    //public Usuario findByEmail(String email);
    public Compra findByIdCompra(Long idCompra);

}
