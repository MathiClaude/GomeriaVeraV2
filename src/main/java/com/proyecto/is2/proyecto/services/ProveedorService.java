package com.proyecto.is2.proyecto.services;


import com.proyecto.is2.proyecto.model.Proveedor;
import com.proyecto.is2.proyecto.controller.dto.ProveedorDTO;

import java.util.List;
import java.util.Set;

public interface ProveedorService {

    /**
     * Este método mapea los datos de un formulario para convertirlos
     * a un objeto de tipo Proveedor
     * @param objetoDTO los datos del formulario
     * @return el objeto Proveedor creado
     */
    public void convertirDTO(Proveedor proveedor, ProveedorDTO objetoDTO);

    /**
     * Persiste un objeto del tipo Proveedor
     * @param proveedor objeto a persistir
     * @return el objeto persistido
     */
    public Proveedor guardar(Proveedor proveedor);

    /**
     * Lista todos los Proveedor que existen
     * @return
     */
    public List<Proveedor> listar();

    /**
     * Verifica si existe un Proveedor a traves de su id
     * @param id del Proveedor
     * @return el Proveedor si existe
     */
    public Proveedor existeProveedor(Long id);

    public void eliminarProveedor(Proveedor proveedor);

    public Proveedor obtenerProveedor(Long id);

    public boolean tienePermiso(String permiso);

}
