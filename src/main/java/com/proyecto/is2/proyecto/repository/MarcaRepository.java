package com.proyecto.is2.proyecto.repository;

import com.proyecto.is2.proyecto.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarcaRepository extends JpaRepository<Marca, Long> {
    // public Marca findByDescripcion(String descripcion);
    public Marca findByIdMarca(Long idMarca);

}