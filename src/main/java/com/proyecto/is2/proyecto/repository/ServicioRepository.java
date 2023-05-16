package com.proyecto.is2.proyecto.repository;

import com.proyecto.is2.proyecto.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicioRepository extends JpaRepository<Servicio, Long> {
    //public Usuario findByEmail(String email);
    public Servicio findByIdServicio(Long idServicio);

}
