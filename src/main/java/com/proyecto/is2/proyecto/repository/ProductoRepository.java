package com.proyecto.is2.proyecto.repository;

import com.proyecto.is2.proyecto.model.Producto;

import java.util.List;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    //public Usuario findByEmail(String email);
    public Producto findByIdProducto(Long idProducto);

    @Query(value="SELECT "+
                "p.codigo,p.nombre_producto,tp.descripcion, pro.nombre, p.cantidad ,um.descripcion as cod_um , p.precio "+
                "FROM producto p "+
                "JOIN tipo_producto tp ON tp.id_tipo_producto = p.tipo_producto_id "+
                "JOIN proveedor pro ON pro.id_proveedor = p.proveedor_id " +
                "JOIN unidad_medida um ON um.id_unidad_medida = p.unidad_medida_id " +
                "WHERE p.cantidad < p.cantidad_minima ",nativeQuery = true)
    List<Tuple>  findStockByCantMinimaNative(); 
   

    //QUERY PARA REPORTE DE STOCK POR PROVEEDOR (codigo, nombreProducto, tipo_producto_id,proveedor,  cantidad, unidad_medida_id, precio) {proveedor,tipoProducto}
    @Query(value="SELECT "+
                "p.codigo,p.nombre_producto,tp.descripcion, pro.nombre, p.cantidad ,um.descripcion as cod_um , p.precio "+
                "FROM producto p "+
                "JOIN tipo_producto tp ON tp.id_tipo_producto = p.tipo_producto_id "+
                "JOIN proveedor pro ON pro.id_proveedor = p.proveedor_id " +
                "JOIN unidad_medida um ON um.id_unidad_medida = p.unidad_medida_id " +
                "WHERE p.proveedor_id = ?1 ",nativeQuery = true)
    List<Tuple>  findStockByProveedorNative( Long proveedor_id); 



    //QUERY PARA REPORTE DE STOCK POR TIPO DE PRODUCTO
    @Query(value="SELECT "+
                "p.codigo,p.nombre_producto,tp.descripcion, pro.nombre, p.cantidad ,um.descripcion as cod_um , p.precio "+
                "FROM producto p "+
                "JOIN tipo_producto tp ON tp.id_tipo_producto = p.tipo_producto_id "+
                "JOIN proveedor pro ON pro.id_proveedor = p.proveedor_id " +
                "JOIN unidad_medida um ON um.id_unidad_medida = p.unidad_medida_id " +
                "WHERE p.tipo_producto_id = ?1 ",nativeQuery = true)
    List<Tuple>  findStockByTipoProNative( Long id_tipoProducto); 

    //QUERY PARA REPORTE DE STOCK POR TIPO DE PRODUCTO Y PROVEEDOR
    @Query(value="SELECT "+
                "p.codigo,p.nombre_producto,tp.descripcion, pro.nombre, p.cantidad ,um.descripcion as cod_um , p.precio "+
                "FROM producto p "+
                "JOIN tipo_producto tp ON tp.id_tipo_producto = p.tipo_producto_id "+
                "JOIN proveedor pro ON pro.id_proveedor = p.proveedor_id " +
                "JOIN unidad_medida um ON um.id_unidad_medida = p.unidad_medida_id " +
                "WHERE p.tipo_producto_id = ?1 AND p.proveedor_id = ?2  ",nativeQuery = true)
    List<Tuple>  findStockByTipoProProveedorNative( Long id_tipoProducto, Long id_proveedor); 

}
