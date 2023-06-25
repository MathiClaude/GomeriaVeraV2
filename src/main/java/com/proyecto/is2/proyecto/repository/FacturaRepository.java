package com.proyecto.is2.proyecto.repository;

import com.proyecto.is2.proyecto.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepository extends JpaRepository<Factura, Long> {
    //public Usuario findByEmail(String email);
    public Factura findByIdFactura(Long idFactura);

}
