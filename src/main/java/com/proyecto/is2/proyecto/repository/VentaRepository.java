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


    // reporte del gráfico POR CAJERO
    @Query(value="SELECT u.username ,sum(monto_total) total "+
                 "FROM venta v "+
                 "JOIN usuario u ON u.id_usuario = v.usuario_id "+
                 "GROUP BY username ",nativeQuery = true)
    List<Tuple>  findGraphCajeroNative(); 

    // reporte del gráfico Producto mas vendido
    @Query(value="SELECT p.nombre_producto, count(1) cantidad "+
                 "FROM venta_detalle v "+
                 "JOIN producto p ON p.id_producto = v.producto_id "+
                 "GROUP BY nombre_producto ",nativeQuery = true)
    List<Tuple>  findGraphProductoNative(); 

    // reporte del gráfico POR CLIENTE
    @Query(value="SELECT c.name||' '||c.last_name AS cliente,sum(monto_total) total "+
                 "FROM venta v "+
                 "JOIN cliente c ON c.id_cliente = v.cliente_id "+
                 "GROUP BY cliente ",nativeQuery = true)
    List<Tuple>  findGraphClienteNative(); 

    // REPORTE ESTADISTICO DE VENTAS MENSUALES
    @Query(value="SELECT EXTRACT(MONTH FROM TO_DATE(fecha_venta,'YYYY-MM-DD')) as mes , count(1)"+
                 "  FROM venta GROUP BY mes",nativeQuery = true)
    List<Tuple>  findGraphNative(); 

    /*Reporte de Venta {cliente,fechaVenta,nFactura,vendedor,total,impuesto}[cajero,fecha,cliente]*/
    @Query(value="SELECT "+
                "c.name||' '||c.last_name AS cliente, fecha_venta, monto_total ,COALESCE(u.username,'admin') as username "+
                "FROM venta v "+
                "JOIN cliente c ON c.id_cliente = v.cliente_id "+
                "LEFT JOIN usuario u ON u.id_usuario = v.usuario_id "+
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
                "WHERE v.usuario_id = ?1 AND v.cliente_id = ?2 ",nativeQuery = true)
    List<Tuple>  findVentasUsuarioClienteByNative( Long usuario_id,Long cliente_id);

    
    @Query(value="SELECT "+
                "c.name||' '||c.last_name AS cliente, fecha_venta, monto_total , u.username "+
                "FROM venta v "+
                "JOIN cliente c ON c.id_cliente = v.cliente_id "+
                "JOIN usuario u ON u.id_usuario = v.usuario_id "+
                "WHERE TO_DATE(fecha_venta,'YYYY-MM-DD') between  TO_DATE(?1,'DD-MM-YYYY') AND TO_DATE(?2,'DD-MM-YYYY') ",nativeQuery = true)// E, pasame como genera la url para quitar el reporte de ventas tu navegador, necesito saber si es dd-mm-yyyy para tomar :v (ojala ese tomar seauna caipi pero nel :'v)
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
    List<Tuple>  findVentasByRangoClienteUsuarioNative( Long cliente_id,Long usuario_id);


    @Query(value="SELECT "+
                "c.name||' '||c.last_name AS cliente, fecha_venta, monto_total , u.username "+
                "FROM venta v "+
                "JOIN cliente c ON c.id_cliente = v.cliente_id "+
                "JOIN usuario u ON u.id_usuario = v.usuario_id "+
                "WHERE v.cliente_id = ?1 ,v.usuario_id = ?2 AND fecha_venta between  ?3 AND ?4 ",nativeQuery = true)
    List<Tuple>  findVentasByRangoAllNative( Long cliente_id,Long usuario_id,String fechaDesde,String fechaHasta);


}
