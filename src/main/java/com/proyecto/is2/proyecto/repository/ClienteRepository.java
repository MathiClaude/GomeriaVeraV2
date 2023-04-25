package com.proyecto.is2.proyecto.repository;

import com.proyecto.is2.proyecto.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    //public Usuario findByEmail(String email);
    public Cliente findByIdCliente(Long idCliente);

}
