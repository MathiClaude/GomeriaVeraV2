package com.proyecto.is2.proyecto.services;


import com.proyecto.is2.proyecto.model.Compra;
import com.proyecto.is2.proyecto.controller.dto.CompraDTO;

import java.util.List;
import java.util.Set;

public interface CompraService {

    /**
     * Este método mapea los datos de un formulario para convertirlos
     * a un objeto de tipo Venta
     * @param objetoDTO los datos del formulario
     * @return el objeto Venta creado
     */
    public void convertirDTO(Compra compra, CompraDTO objetoDTO);

    /**
     * Persiste un objeto del tipo Venta
     * @param Compra objeto a persistir
     * @return el objeto persistido
     */
    public Compra guardar(Compra compra);

    /**
     * Lista todos los producto que existen
     * @return
     */
    public List<Compra> listar();

    /**
     * Verifica si existe una venta a traves de su id
     * @param id de la venta
     * @return la venta si existe
     */
    public Compra existeCompra(Long id);

    public void eliminarCompra(Compra compra);

    public Compra obtenerCompra(Long id);

    public boolean tienePermiso(String permiso);

}
