package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.model.ProductoCantidad;
import com.proyecto.is2.proyecto.controller.dto.ProductoCantidadDTO;

import java.util.List;
import java.util.Set;

public interface ProductoCantidadService {

    /**
     * Este método mapea los datos de un formulario para convertirlos
     * a un objeto de tipo Producto
     * @param objetoDTO los datos del formulario
     * @return el objeto Producto creado
     */
    public void convertirDTO(ProductoCantidad productoCantidad, ProductoCantidadDTO objetoDTO);

    /**
     * Persiste un objeto del tipo Producto
     * @param ProductoCantidad objeto a persistir
     * @return el objeto persistido
     */
    public ProductoCantidad guardar(ProductoCantidad productoCantidad);

    /**
     * Lista todos los producto que existen
     * @return
     */
    public List<ProductoCantidad> listar();

    /**
     * Verifica si existe un producto a traves de su id
     * @param id del producto
     * @return el producto si existe
     */
    public ProductoCantidad existeProductoCantidad(Long id);

    public void eliminarProductoCantidad(ProductoCantidad productoCantidad);

}
