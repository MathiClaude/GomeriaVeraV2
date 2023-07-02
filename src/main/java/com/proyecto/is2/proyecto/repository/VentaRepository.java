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


    /*Reporte de Venta {cliente,fechaVenta,nFactura,vendedor,total,impuesto}[cajero,fecha,cliente]*/
    @Query(value="SELECT "+
                "c.name||' '||c.last_name AS cliente, fecha_venta, monto_total , u.username "+
                "FROM venta v "+
                "JOIN cliente c ON c.id_cliente = v.cliente_id "+
                "JOIN usuario u ON u.id_usuario = v.usuario_id "+
                "WHERE v.cliente_id = ?1 ",nativeQuery = true)
    List<Tuple>  findVentasByClienteNative( Long cliente_id); 

    @Query(value="SELECT "+
                "c.name||' '||c.last_name AS cliente, fecha_venta, monto_total , u.username "+
                "FROM venta v "+
                "JOIN cliente c ON c.id_cliente = v.cliente_id "+
                "JOIN usuario u ON u.id_usuario = v.usuario_id "+
                "WHERE v.usuario_id = ?1 ",nativeQuery = true)
    List<Tuple>  findVentasByUsuarioNative( Long usuario_id); 

    @Query(value="SELECT "+
                "c.name||' '||c.last_name AS cliente, fecha_venta, monto_total , u.username "+
                "FROM venta v "+
                "JOIN cliente c ON c.id_cliente = v.cliente_id "+
                "JOIN usuario u ON u.id_usuario = v.usuario_id "+
                "WHERE fecha_venta between  ?1 AND ?2 ",nativeQuery = true)
    List<Tuple>  findVentasByRangoNative( String fechaDesde,String fechaHasta);


    @Query(value="SELECT "+
                "c.name||' '||c.last_name AS cliente, fecha_venta, monto_total , u.username "+
                "FROM venta v "+
                "JOIN cliente c ON c.id_cliente = v.cliente_id "+
                "JOIN usuario u ON u.id_usuario = v.usuario_id "+
                "WHERE v.cliente_id = ?1 AND fecha_venta between  ?2 AND ?3 ",nativeQuery = true)
    List<Tuple>  findVentasByRangoClienteNative( Long cliente_id,String fechaDesde,String fechaHasta);

    @Query(value="SELECT "+
                "c.name||' '||c.last_name AS cliente, fecha_venta, monto_total , u.username "+
                "FROM venta v "+
                "JOIN cliente c ON c.id_cliente = v.cliente_id "+
                "JOIN usuario u ON u.id_usuario = v.usuario_id "+
                "WHERE v.usuario_id = ?1 AND fecha_venta between  ?2 AND ?3 ",nativeQuery = true)
    List<Tuple>  findVentasByRangoUsuarioNative( Long usuario_id,String fechaDesde,String fechaHasta);

    @Query(value="SELECT "+
                "c.name||' '||c.last_name AS cliente, fecha_venta, monto_total , u.username "+
                "FROM venta v "+
                "JOIN cliente c ON c.id_cliente = v.cliente_id "+
                "JOIN usuario u ON u.id_usuario = v.usuario_id "+
                "WHERE v.cliente_id = ?1 ,v.usuario_id = ?2 AND fecha_venta between  ?3 AND ?4 ",nativeQuery = true)
    List<Tuple>  findVentasByRangoClienteUsuarioNative( Long cliente_id,Long usuario_id,String fechaDesde,String fechaHasta);



}
