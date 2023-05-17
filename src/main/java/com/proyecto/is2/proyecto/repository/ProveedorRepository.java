package com.proyecto.is2.proyecto.repository;

import com.proyecto.is2.proyecto.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    //public Usuario findByEmail(String email);
    public Proveedor findByIdProveedor(Long idProveedor);

}
