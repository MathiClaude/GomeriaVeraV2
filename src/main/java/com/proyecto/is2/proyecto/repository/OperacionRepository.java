package com.proyecto.is2.proyecto.repository;

import com.proyecto.is2.proyecto.model.Operacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperacionRepository extends JpaRepository<Operacion, Long> {
    public Operacion findByIdOperacion(Long idOperacion);
    //public Caja findByDescripcion(String descripcion);    
    //public Caja findByPinCaja(String pinCaja);
    //public Caja findBySaldoInicialCaja(String saldoInicial);
    


}