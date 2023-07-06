package com.proyecto.is2.proyecto.repository;

import com.proyecto.is2.proyecto.model.Producto;

import java.util.List;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    //public Usuario findByEmail(String email);
    public Producto findByIdProducto(Long idProducto);



    //QUERY PARA REPORTE DE STOCK POR PROVEEDOR
    @Query(value="SELECT "+
                "p.nombre AS proveedor,c.id_compra, c.fecha_compra, c.monto_compra ,COALESCE(u.username,'admin') as username, c.estado "+
                "FROM compra c "+
                "JOIN proveedor p ON p.id_proveedor = c.proveedor_id "+
                "LEFT JOIN usuario u ON u.id_usuario = c.usuario_id " +
                "WHERE c.proveedor_id = ?1",nativeQuery = true)
    List<Tuple>  findStockByProveedorNative( Long proveedor_id); 



    //QUERY PARA REPORTE DE STOCK POR TIPO DE PRODUCTO
    @Query(value="SELECT "+
                "p.nombre AS proveedor,c.id_compra, c.fecha_compra, c.monto_compra ,COALESCE(u.username,'admin') as username, c.estado "+
                "FROM compra c "+
                "JOIN proveedor p ON p.id_proveedor = c.proveedor_id "+
                "LEFT JOIN usuario u ON u.id_usuario = c.usuario_id " +
                "WHERE c.estado = ?1",nativeQuery = true)
    List<Tuple>  findStockByTipoProNative( String id_tipoProducto); 

}
