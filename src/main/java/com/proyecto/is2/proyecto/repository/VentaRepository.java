package com.proyecto.is2.proyecto.repository;

import com.proyecto.is2.proyecto.model.Venta;
import com.proyecto.is2.proyecto.controller.dto.DatoGraficoVentaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import javax.persistence.Tuple;


import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    //public Usuario findByEmail(String email);
    public Venta findByIdVenta(Long idVenta);

    @Query(value="SELECT coalesce(EXTRACT(MONTH from TO_DATE(fecha_venta, 'YYYY-MM-DD')),'0') as mes , count(1)  FROM venta GROUP BY mes",nativeQuery = true)
    List<Tuple>  findGraphNative(); 

}
