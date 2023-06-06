package com.proyecto.is2.proyecto.repository;

import com.proyecto.is2.proyecto.model.Caja;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CajaRepository extends JpaRepository<Caja, Long> {
    public Caja findByIdCaja(Long idCaja);
    //public Caja findByDescripcion(String descripcion);    
    //public Caja findByPinCaja(String pinCaja);
    //public Caja findBySaldoInicialCaja(String saldoInicial);
    


}