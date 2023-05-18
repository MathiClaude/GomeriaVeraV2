package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.controller.dto.TipoProductoDTO;
import com.proyecto.is2.proyecto.model.TipoProducto;

import java.util.List;
import java.util.Set;

public interface TipoProductoService {

    /**
     * Este mÃ©todo mapea los datos de un formulario para convertirlos
     * a un objeto de tipo Usuario
     * @param objetoDTO los datos del formulario
     * @return el objeto Usuario creado
     */
    public void convertirDTO(TipoProducto tipoProducto, TipoProductoDTO objetoDTO);

    /**
     * Persiste un objeto del tipo TipoDocumento
     * @param usuario objeto a persistir
     * @return el objeto persistido
     */
    public TipoProducto guardar(TipoProducto tipoProducto);

    /**
     * Lista todos los usuarios que existen
     * @return
     */
    public List<TipoProducto> listar();

    public TipoProducto existeTipoProducto(Long id);

    public void eliminarTipoProducto(TipoProducto tipoProducto);

    public TipoProducto obtenerTipoProducto(Long id);

    public boolean tienePermiso(String permiso);
}