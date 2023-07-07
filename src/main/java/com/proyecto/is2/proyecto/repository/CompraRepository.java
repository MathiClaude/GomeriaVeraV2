package com.proyecto.is2.proyecto.repository;

import com.proyecto.is2.proyecto.model.Compra;

import java.util.List;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompraRepository extends JpaRepository<Compra, Long> {
    //public Usuario findByEmail(String email);
    public Compra findByIdCompra(Long idCompra);

    public Compra findByEstado(String estado);

    //QUERY PARA GRÁFICO 
    @Query(value="SELECT "+
                 " coalesce(EXTRACT(MONTH from TO_DATE(fecha_compra, 'YYYY-MM-DD')),'0') as mes , count(1) "+
                 " FROM venta GROUP BY mes",nativeQuery = true)
    List<Tuple>  findGraphNative(); 
    


    //QUERY PARA REPORTE DE COMPRA POR PROVEEDOR
    @Query(value="SELECT "+
                "p.nombre AS proveedor, c.fecha_compra, c.monto_compra ,COALESCE(u.username,'admin') as username, c.estado "+
                "FROM compra c "+
                "JOIN proveedor p ON p.id_proveedor = c.proveedor_id "+
                "LEFT JOIN usuario u ON u.id_usuario = c.usuario_id " +
                "WHERE c.proveedor_id = ?1",nativeQuery = true)
    List<Tuple>  findComprasByProveedorNative( Long proveedor_id); 

    //QUERY PARA REPORTE DE COMPRA POR ESTADO
    @Query(value="SELECT "+
                "p.nombre AS proveedor, c.fecha_compra, c.monto_compra ,COALESCE(u.username,'admin') as username, c.estado "+
                "FROM compra c "+
                "JOIN proveedor p ON p.id_proveedor = c.proveedor_id "+
                "LEFT JOIN usuario u ON u.id_usuario = c.usuario_id " +
                "WHERE c.estado = ?1",nativeQuery = true)
    List<Tuple>  findComprasByEstadoNative( String estado); 

    //QUERY PARA REPORTE DE COMPRA POR ESTADO Y PROVEEDOR
    @Query(value="SELECT "+
                "p.nombre AS proveedor, c.fecha_compra, c.monto_compra ,COALESCE(u.username,'admin') as username, c.estado "+
                "FROM compra c "+
                "JOIN proveedor p ON p.id_proveedor = c.proveedor_id "+
                "LEFT JOIN usuario u ON u.id_usuario = c.usuario_id " +
                "WHERE c.estado = ?1 AND c.proveedor_id = ?2",nativeQuery = true)
    List<Tuple>  findComprasByEstadoProveedorNative( String estado, Long proveedor_id); 

    //QUERY PARA REPORTE DE COMPRA POR RANGO DE FECHAS
     @Query(value="SELECT "+
                "p.nombre AS proveedor, c.fecha_compra, c.monto_compra ,COALESCE(u.username,'admin') as username, c.estado "+
                "FROM compra c "+
                "JOIN proveedor p ON p.id_proveedor = c.proveedor_id "+
                "LEFT JOIN usuario u ON u.id_usuario = c.usuario_id " +
                "WHERE TO_DATE(fecha_compra,'DD/MM/YYYY HH24:MI:SS') between  TO_DATE(?1,'MM-DD-YYYY') AND TO_DATE(?2,'MM-DD-YYYY') ",nativeQuery = true)
    List<Tuple>  findComprasByRangoNative( String fechaDesde,String fechaHasta);

    //QUERY PARA REPORTE DE COMPRA POR PROVEEDOR Y RANGO DE FECHAS
    @Query(value="SELECT "+
                "p.nombre AS proveedor, c.fecha_compra, c.monto_compra ,COALESCE(u.username,'admin') as username, c.estado "+
                "FROM compra c "+
                "JOIN proveedor p ON p.id_proveedor = c.proveedor_id "+
                "LEFT JOIN usuario u ON u.id_usuario = c.usuario_id " +
                "WHERE c.proveedor_id = ?1 AND TO_DATE(fecha_compra,'DD/MM/YYYY HH24:MI:SS') between  TO_DATE(?2,'MM-DD-YYYY') AND TO_DATE(?3,'MM-DD-YYYY') ",nativeQuery = true)
    List<Tuple>  findComprasByRangoProveedorNative( Long proveedor_id,String fechaDesde,String fechaHasta);

    //QUERY PARA REPORTE DE COMPRA POR ESTADO Y RANGO DE FECHAS
    @Query(value="SELECT "+
                "p.nombre AS proveedor, c.fecha_compra, c.monto_compra ,COALESCE(u.username,'admin') as username, c.estado "+
                "FROM compra c "+
                "JOIN proveedor p ON p.id_proveedor = c.proveedor_id "+
                "LEFT JOIN usuario u ON u.id_usuario = c.usuario_id " +
                "WHERE c.estado = ?1 AND TO_DATE(fecha_compra,'DD/MM/YYYY HH24:MI:SS') between  TO_DATE(?2,'MM-DD-YYYY') AND TO_DATE(?3,'MM-DD-YYYY') ",nativeQuery = true)
    List<Tuple>  findComprasByRangoEstadoNative( String estado,String fechaDesde,String fechaHasta);

    @Query(value="SELECT "+
                "p.nombre AS proveedor, c.fecha_compra, c.monto_compra ,COALESCE(u.username,'admin') as username, c.estado "+
                "FROM compra c "+
                "JOIN proveedor p ON p.id_proveedor = c.proveedor_id "+
                "LEFT JOIN usuario u ON u.id_usuario = c.usuario_id " +
                "WHERE c.proveedor_id = ?1 AND c.estado = ?2 AND TO_DATE(fecha_compra,'DD/MM/YYYY HH24:MI:SS') between  TO_DATE(?3,'MM-DD-YYYY') AND TO_DATE(?4,'MM-DD-YYYY') ",nativeQuery = true)
    List<Tuple>  findVentasByRangoAllNative( Long proveedor_id,String estado, String fechaDesde, String fechaHasta);


}
