package com.proyecto.is2.proyecto.repository;

import com.proyecto.is2.proyecto.model.VentaDetalle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaDetalleRepository extends JpaRepository<VentaDetalle, Long> {
    //public Usuario findByEmail(String email);
    public VentaDetalle findByIdVentaDetalle(Long idVentaDetalle);

}
