package com.proyecto.is2.proyecto.repository;

import com.proyecto.is2.proyecto.model.Caja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import javax.persistence.Tuple;

public interface CajaRepository extends JpaRepository<Caja, Long> {
    public Caja findByIdCaja(Long idCaja);
    //public Caja findByDescripcion(String descripcion);    
    //public Caja findByPinCaja(String pinCaja);
    //public Caja findBySaldoInicialCaja(String saldoInicial);
    
    @Query(value="SELECT "+
                " c.descripcion, u.username , o.concepto , o.monto , o.fecha_operacion  o.id_operacion" + 
                "FROM operacion o "+
                "JOIN caja c ON c.id_caja  = o.caja_id  "+
                "JOIN usuario u ON u.id_usuario = o.usuario_id "+
                "WHERE c.id_caja = ?1 "+
                " ORDER BY fecha_operacion DESC LIMIT 10 ",nativeQuery = true)
    List<Tuple>  findHistorialOperacionesByidCajaNative(Long id_caja); 

    @Query(value="SELECT "+
                " c.descripcion, u.username , o.concepto , o.monto , o.fecha_operacion  o.id_operacion" + 
                "FROM operacion o "+
                "JOIN caja c ON c.id_caja  = o.caja_id  "+
                "JOIN usuario u ON u.id_usuario = o.usuario_id "+
                "WHERE u.id_usuario = ?1 "+
                " ORDER BY fecha_operacion DESC LIMIT 10 ",nativeQuery = true)
    List<Tuple>  findHistorialOperacionesByidUsuarioNative(Long id_usuario); 


    @Query(value="SELECT "+
                " c.descripcion, u.username , o.concepto , o.monto , o.fecha_operacion  o.id_operacion" + 
                "FROM operacion o "+
                "JOIN caja c ON c.id_caja  = o.caja_id  "+
                "JOIN usuario u ON u.id_usuario = o.usuario_id "+
                "WHERE TO_DATE(fecha_operacion,'DD/MM/YYYY HH24:MI:SS') between  TO_DATE(?1,'MM-DD-YYYY') AND TO_DATE(?2,'MM-DD-YYYY') "+
                " ORDER BY fecha_operacion DESC LIMIT 10 ",nativeQuery = true)
    List<Tuple>  findHistorialOperacionesByRangoNative(String fechaDesde,String fechaHasta); 


    @Query(value="SELECT "+
                " c.descripcion, u.username , o.concepto , o.monto , o.fecha_operacion  o.id_operacion" + 
                "FROM operacion o "+
                "JOIN caja c ON c.id_caja  = o.caja_id  "+
                "JOIN usuario u ON u.id_usuario = o.usuario_id "+
                "WHERE c.id_caja = ?1 AND u.id_usuario = ?2 "+
                " ORDER BY fecha_operacion DESC LIMIT 10 ",nativeQuery = true)
    List<Tuple>  findHistorialOperacionesByidCajaUsuarioNative(Long id_caja,Long id_usuario); 

    @Query(value="SELECT "+
                " c.descripcion, u.username , o.concepto , o.monto , o.fecha_operacion  o.id_operacion" + 
                "FROM operacion o "+
                "JOIN caja c ON c.id_caja  = o.caja_id  "+
                "JOIN usuario u ON u.id_usuario = o.usuario_id "+
                "WHERE c.id_caja = ?1 AND TO_DATE(fecha_operacion,'DD/MM/YYYY HH24:MI:SS') between  TO_DATE(?2,'MM-DD-YYYY') AND TO_DATE(?3,'MM-DD-YYYY') "+
                " ORDER BY fecha_operacion DESC LIMIT 10 ",nativeQuery = true)
    List<Tuple>  findHistorialOperacionesByidCajaRangoNative(Long id_caja,String fechaDesde,String fechaHasta); 

    @Query(value="SELECT "+
                " c.descripcion, u.username , o.concepto , o.monto , o.fecha_operacion  o.id_operacion" + 
                "FROM operacion o "+
                "JOIN caja c ON c.id_caja  = o.caja_id  "+
                "JOIN usuario u ON u.id_usuario = o.usuario_id "+
                "WHERE u.id_usuario = ?1 AND TO_DATE(fecha_operacion,'DD/MM/YYYY HH24:MI:SS') between  TO_DATE(?2,'MM-DD-YYYY') AND TO_DATE(?3,'MM-DD-YYYY') "+
                " ORDER BY fecha_operacion DESC LIMIT 10 ",nativeQuery = true)
    List<Tuple>  findHistorialOperacionesByidUsuarioRangoNative(Long id_usuario,String fechaDesde,String fechaHasta); 

    @Query(value="SELECT "+
                " c.descripcion, u.username , o.concepto , o.monto , o.fecha_operacion  o.id_operacion" + 
                "FROM operacion o "+
                "JOIN caja c ON c.id_caja  = o.caja_id  "+
                "JOIN usuario u ON u.id_usuario = o.usuario_id "+
                "WHERE c.id_caja = ?1 AND u.id_usuario = ?2 AND TO_DATE(fecha_operacion,'DD/MM/YYYY HH24:MI:SS') between  TO_DATE(?3,'MM-DD-YYYY') AND TO_DATE(?4,'MM-DD-YYYY') "+
                " ORDER BY fecha_operacion DESC LIMIT 10 ",nativeQuery = true)
    List<Tuple>  findHistorialOperacionesByidCajaRangoNative(Long id_caja,Long id_usuario,String fechaDesde,String fechaHasta); 

    @Query(value="SELECT "+
                " id_caja, descripcion ,estado" + 
                "FROM caja c "+    
                "WHERE c.estado =?1",nativeQuery = true)
    List<Tuple>  findListadoCajas(String estado); 

}