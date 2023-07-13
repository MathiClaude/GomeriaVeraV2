package com.proyecto.is2.proyecto.repository;

import com.proyecto.is2.proyecto.model.Compra;
import com.proyecto.is2.proyecto.model.CompraDetalle;
import com.proyecto.is2.proyecto.model.Factura;
import com.proyecto.is2.proyecto.model.Proveedor;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepository extends JpaRepository<Factura, Long> {
    //public Usuario findByEmail(String email);
    public Factura findByIdFactura(Long idFactura);
    public List<Factura> findByEstado(String estado);
    public List<Factura> findByProveedor(Proveedor proveedor);
    public List<Factura> findByTipo(String tipo);
    public List<Factura> findByCompra(Compra compra);
    public List<Factura> findByEstadoAndProveedorAndTipo(String estado,Proveedor proveedor,String tipo);
}
