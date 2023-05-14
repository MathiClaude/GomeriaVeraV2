package com.proyecto.is2.proyecto.services;


import com.proyecto.is2.proyecto.model.Producto;
import com.proyecto.is2.proyecto.controller.dto.ProductoDTO;

import java.util.List;
import java.util.Set;

public interface ProductoService {

    /**
     * Este método mapea los datos de un formulario para convertirlos
     * a un objeto de tipo Producto
     * @param objetoDTO los datos del formulario
     * @return el objeto Producto creado
     */
    public void convertirDTO(Producto producto, ProductoDTO objetoDTO);

    /**
     * Persiste un objeto del tipo Producto
     * @param Producto objeto a persistir
     * @return el objeto persistido
     */
    public Producto guardar(Producto producto);

    /**
     * Lista todos los producto que existen
     * @return
     */
    public List<Producto> listar();

    /**
     * Verifica si existe un producto a traves de su id
     * @param id del producto
     * @return el producto si existe
     */
    public Producto existeProducto(Long id);

    public void eliminarProducto(Producto producto);

    public Producto obtenerProducto(Long id);

    public boolean tienePermiso(String permiso);

}
