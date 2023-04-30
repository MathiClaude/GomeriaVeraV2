package com.proyecto.is2.proyecto.repository;

import com.proyecto.is2.proyecto.model.Precio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrecioRepository extends JpaRepository<Precio, Long> {
    //public Usuario findByEmail(String email);
    public Precio findByIdPrecio(Long idPrecio);

}
