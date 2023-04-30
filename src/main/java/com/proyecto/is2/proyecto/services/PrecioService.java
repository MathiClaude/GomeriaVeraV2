package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.model.Precio;
import com.proyecto.is2.proyecto.controller.dto.PrecioDTO;

import java.util.List;
import java.util.Set;

public interface PrecioService {

    /**
     * Este método mapea los datos de un formulario para convertirlos
     * a un objeto de tipo Producto
     * @param objetoDTO los datos del formulario
     * @return el objeto Producto creado
     */
    public void convertirDTO(Precio precio, PrecioDTO objetoDTO);

    /**
     * Persiste un objeto del tipo Producto
     * @param Precio objeto a persistir
     * @return el objeto persistido
     */
    public Precio guardar(Precio precio);

    /**
     * Lista todos los producto que existen
     * @return
     */
    public List<Precio> listar();

    /**
     * Verifica si existe un producto a traves de su id
     * @param id del producto
     * @return el producto si existe
     */
    public Precio existePrecio(Long id);

    public void eliminarPrecio(Precio precio);

}
