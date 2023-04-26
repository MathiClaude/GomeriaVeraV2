package com.proyecto.is2.proyecto.repository;

import com.proyecto.is2.proyecto.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    //public Usuario findByEmail(String email);
    public Venta findByIdVenta(Long idVenta);

}
