package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.controller.dto.OperacionDTO;
import com.proyecto.is2.proyecto.model.Operacion;

import java.util.List;
import java.util.Set;

public interface OperacionService {

    /**
     * Este mÃ©todo mapea los datos de un formulario para convertirlos
     * a un objeto de tipo Usuario
     * @param objetoDTO los datos del formulario
     * @return el objeto Usuario creado
     */
    public void convertirDTO(Operacion operacion, OperacionDTO objetoDTO);

    /**
     * Persiste un objeto del tipo TipoDocumento
     * @param usuario objeto a persistir
     * @return el objeto persistido
     */
    public Operacion guardar(Operacion operacion);

    /**
     * Lista todos los usuarios que existen
     * @return
     */
    public List<Operacion> listar();


    /**
     * Cambia el rol de un usuario
     * @param usuario a quien se le cambia el rol
     * @param rol a ser asignado al usuario
     * @return boolean
     */
    public Operacion existeOperacion(Long id);

    public void eliminarOperacion(Operacion operacion);

    public Operacion obtenerOperacion(Long id);

    public boolean tienePermiso(String permiso);
}