package com.proyecto.is2.proyecto.repository;

import com.proyecto.is2.proyecto.model.Compra;
import com.proyecto.is2.proyecto.model.CompraDetalle;
import com.proyecto.is2.proyecto.model.Venta;
import com.proyecto.is2.proyecto.model.VentaDetalle;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CompraDetalleRepository extends JpaRepository<CompraDetalle, Long> {
    //public Usuario findByEmail(String email);
    public CompraDetalle findByIdCompraDetalle(Long idCompraDetalle);
    public List<CompraDetalle> findByCompra(Compra compra);
   // public List<CompraDetalle> findByIdCompra(Long idCompra);

}
