package com.proyecto.is2.proyecto.repository;

import com.proyecto.is2.proyecto.model.Venta;
import com.proyecto.is2.proyecto.model.VentaDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VentaDetalleRepository extends JpaRepository<VentaDetalle, Long> {
    //public Usuario findByEmail(String email);
    public VentaDetalle findByIdVentaDetalle(Long idVentaDetalle);
    public List<VentaDetalle> findByVenta(Venta venta);

}
