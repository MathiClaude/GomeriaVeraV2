package com.proyecto.is2.proyecto.repository;

import com.proyecto.is2.proyecto.model.Timbrado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimbradoRepository extends JpaRepository<Timbrado, Long> {
    public Timbrado findByIdTimbrado(Long idTimbrado);
    public Timbrado findByEstado(String estado);
    public Timbrado findByNroTimbrado(Integer nroTimbrado);
    //public Caja findByDescripcion(String descripcion);    
    //public Caja findByPinCaja(String pinCaja);
    //public Caja findBySaldoInicialCaja(String saldoInicial);
    


}