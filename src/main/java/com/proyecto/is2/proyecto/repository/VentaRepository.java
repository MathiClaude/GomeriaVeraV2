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

    /*
        Nro. de Comprobante
        Cliente
        Fecha
        Vendedor
        Total
        Impuesto   
    */
    // INFORME DE PRODUCTO MAS VENDIDO POR CANTIDAD
    @Query(value="SELECT "+
                "c.name||' '||c.last_name AS cliente, fecha_venta, monto_total ,COALESCE(u.username,'admin') as username,COALESCE(nro_factura,0) AS nro_factura , COALESCE(monto_impuesto,0) AS monto_impuesto , v.id_venta "+
                "FROM venta v "+
                "JOIN cliente c ON c.id_cliente = v.cliente_id "+
                "LEFT JOIN usuario u ON u.id_usuario = v.usuario_id "+
                "ORDER BY v.id_venta DESC LIMIT 10 ",nativeQuery = true)
    List<Tuple>  findInformeHistorialNative(); 


    // INFORME DE PRODUCTO MAS VENDIDO POR CANTIDAD
    @Query(value="SELECT p.codigo, p.nombre_producto, count(1) AS cantidad, sum(v.precio) AS monto "+
                 "FROM venta_detalle v "+
                 "JOIN producto p ON p.id_producto = v.producto_id "+
                 "GROUP BY nombre_producto,codigo ORDER BY cantidad DESC LIMIT 10 ",nativeQuery = true)
    List<Tuple>  findInformeProductoCantNative(); 

    // Informe de producto mas vendido por montoVentas
    @Query(value="SELECT p.codigo,p.nombre_producto,count(1) AS cantidad, sum(v.precio) AS monto "+
                 "FROM venta_detalle v "+
                 "JOIN producto p ON p.id_producto = v.producto_id "+
                 "GROUP BY nombre_producto,codigo ORDER BY monto DESC LIMIT 10 ",nativeQuery = true)
    List<Tuple>  findInformeProductoMontoNative(); 

    // Informe de servicios más frecuentes
    @Query(value="SELECT s.codigo, s.nombre, sum(v.precio) AS monto "+
                 "FROM venta_detalle v "+
                 "JOIN servicio s ON s.id_servicio = v.servicio_id "+
                 "GROUP BY s.nombre,s.codigo ORDER BY monto DESC LIMIT 10 ",nativeQuery = true)
    List<Tuple>  findInformeServicio(); 

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

    // reporte del gráfico servicios más vendido
    @Query(value="SELECT s.nombre , count(1) as cantidad "+
                 "FROM venta_detalle v "+
                 "JOIN servicio s ON s.id_servicio = v.servicio_id "+
                 "GROUP BY s.nombre ",nativeQuery = true)
    List<Tuple>  findGraphServicioNative(); 

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
                "c.name||' '||c.last_name AS cliente, fecha_venta, monto_total ,COALESCE(u.username,'admin') as username,COALESCE(nro_factura,0) AS nro_factura , COALESCE(monto_impuesto,0) AS monto_impuesto "+
                "FROM venta v "+
                "JOIN cliente c ON c.id_cliente = v.cliente_id "+
                "LEFT JOIN usuario u ON u.id_usuario = v.usuario_id "+
                "WHERE v.cliente_id = ?1 ",nativeQuery = true)
    List<Tuple>  findVentasByClienteNative( Long cliente_id);

    @Query(value="SELECT "+
                "c.name||' '||c.last_name AS cliente, fecha_venta, monto_total , COALESCE(u.username,'admin') as username,COALESCE(nro_factura,0) AS nro_factura , COALESCE(monto_impuesto,0) AS monto_impuesto "+
                "FROM venta v "+
                "JOIN cliente c ON c.id_cliente = v.cliente_id "+
                "JOIN usuario u ON u.id_usuario = v.usuario_id "+
                "WHERE v.usuario_id = ?1 ",nativeQuery = true)
    List<Tuple>  findVentasByUsuarioNative( Long usuario_id); 


    @Query(value="SELECT "+
                "c.name||' '||c.last_name AS cliente, fecha_venta, monto_total , COALESCE(u.username,'admin') as username,COALESCE(nro_factura,0) AS nro_factura , COALESCE(monto_impuesto,0) AS monto_impuesto "+
                "FROM venta v "+
                "JOIN cliente c ON c.id_cliente = v.cliente_id "+
                "JOIN usuario u ON u.id_usuario = v.usuario_id "+
                "WHERE v.usuario_id = ?1 AND v.cliente_id = ?2 ",nativeQuery = true)
    List<Tuple>  findVentasUsuarioClienteByNative( Long usuario_id,Long cliente_id);

    
    @Query(value="SELECT "+
                "c.name||' '||c.last_name AS cliente, fecha_venta, monto_total , COALESCE(u.username,'admin') as username,COALESCE(nro_factura,0) AS nro_factura , COALESCE(monto_impuesto,0) AS monto_impuesto "+
                "FROM venta v "+
                "JOIN cliente c ON c.id_cliente = v.cliente_id "+
                "JOIN usuario u ON u.id_usuario = v.usuario_id "+
                "WHERE TO_DATE(fecha_venta,'YYYY-MM-DD') between  TO_DATE(?1,'MM-DD-YYYY') AND TO_DATE(?2,'MM-DD-YYYY') ",nativeQuery = true)// E, pasame como genera la url para quitar el reporte de ventas tu navegador, necesito saber si es MM-DD-YYYY para tomar :v (ojala ese tomar seauna caipi pero nel :'v)
    List<Tuple>  findVentasByRangoNative( String fechaDesde,String fechaHasta);


    @Query(value="SELECT "+
                "c.name||' '||c.last_name AS cliente, fecha_venta, monto_total , COALESCE(u.username,'admin') as username,COALESCE(nro_factura,0) AS nro_factura , COALESCE(monto_impuesto,0) AS monto_impuesto "+
                "FROM venta v "+
                "JOIN cliente c ON c.id_cliente = v.cliente_id "+
                "JOIN usuario u ON u.id_usuario = v.usuario_id "+
                "WHERE v.cliente_id = ?1 AND TO_DATE(fecha_venta,'YYYY-MM-DD') between  TO_DATE(?2,'MM-DD-YYYY') AND TO_DATE(?3,'MM-DD-YYYY') ",nativeQuery = true)
    List<Tuple>  findVentasByRangoClienteNative( Long cliente_id,String fechaDesde,String fechaHasta);

    @Query(value="SELECT "+
                "c.name||' '||c.last_name AS cliente, fecha_venta, monto_total , COALESCE(u.username,'admin') as username,COALESCE(nro_factura,0) AS nro_factura , COALESCE(monto_impuesto,0) AS monto_impuesto "+
                "FROM venta v "+
                "JOIN cliente c ON c.id_cliente = v.cliente_id "+
                "JOIN usuario u ON u.id_usuario = v.usuario_id "+
                "WHERE v.usuario_id = ?1 AND TO_DATE(fecha_venta,'YYYY-MM-DD') between  TO_DATE(?2,'MM-DD-YYYY') AND TO_DATE(?3,'MM-DD-YYYY') ",nativeQuery = true)
    List<Tuple>  findVentasByRangoUsuarioNative( Long usuario_id,String fechaDesde,String fechaHasta);

    @Query(value="SELECT "+
                "c.name||' '||c.last_name AS cliente, fecha_venta, monto_total , COALESCE(u.username,'admin') as username,COALESCE(nro_factura,0) AS nro_factura , COALESCE(monto_impuesto,0) AS monto_impuesto "+
                "FROM venta v "+
                "JOIN cliente c ON c.id_cliente = v.cliente_id "+
                "JOIN usuario u ON u.id_usuario = v.usuario_id "+
                "WHERE v.cliente_id = ?1 AND v.usuario_id = ?2  ",nativeQuery = true)
    List<Tuple>  findVentasByRangoClienteUsuarioNative( Long cliente_id,Long usuario_id);


    @Query(value="SELECT "+
                "c.name||' '||c.last_name AS cliente, fecha_venta, monto_total , COALESCE(u.username,'admin') as username,COALESCE(nro_factura,0) AS nro_factura , COALESCE(monto_impuesto,0) AS monto_impuesto "+
                "FROM venta v "+
                "JOIN cliente c ON c.id_cliente = v.cliente_id "+
                "JOIN usuario u ON u.id_usuario = v.usuario_id "+
                "WHERE v.cliente_id = ?1 AND v.usuario_id = ?2 AND TO_DATE(fecha_venta,'YYYY-MM-DD') between  TO_DATE(?3,'MM-DD-YYYY') AND TO_DATE(?4,'MM-DD-YYYY') ",nativeQuery = true)
    List<Tuple>  findVentasByRangoAllNative( Long cliente_id,Long usuario_id,String fechaDesde,String fechaHasta);


}