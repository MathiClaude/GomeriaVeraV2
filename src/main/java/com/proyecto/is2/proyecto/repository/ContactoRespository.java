package com.proyecto.is2.proyecto.repository;

import com.proyecto.is2.proyecto.model.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactoRespository extends JpaRepository<Contacto, Long> {
}
