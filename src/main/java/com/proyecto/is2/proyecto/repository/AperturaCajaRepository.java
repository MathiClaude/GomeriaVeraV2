package com.proyecto.is2.proyecto.repository;

import com.proyecto.is2.proyecto.model.AperturaCaja;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface AperturaCajaRepository extends JpaRepository<AperturaCaja, Long> {
    public AperturaCaja findByIdAperturaCaja(Long idAperturaCaja);
    public List<AperturaCaja> findByIdUsuarioOrderByIdAperturaCajaDesc(Long idUsuario);
    //public Caja findByDescripcion(String descripcion);    
    //public Caja findByPinCaja(String pinCaja);
    //public Caja findBySaldoInicialCaja(String saldoInicial);
    


}