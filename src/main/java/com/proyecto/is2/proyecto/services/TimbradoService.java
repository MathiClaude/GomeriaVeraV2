package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.controller.dto.TimbradoDTO;
import com.proyecto.is2.proyecto.model.Timbrado;

import java.util.List;
import java.util.Set;

public interface TimbradoService {

    /**
     * Este mÃ©todo mapea los datos de un formulario para convertirlos
     * a un objeto de tipo Usuario
     * @param objetoDTO los datos del formulario
     * @return el objeto Usuario creado
     */
    public void convertirDTO(Timbrado caja, TimbradoDTO objetoDTO);

    /**
     * Persiste un objeto del tipo TipoDocumento
     * @param usuario objeto a persistir
     * @return el objeto persistido
     */
    public Timbrado guardar(Timbrado timbrado);

    /**
     * Lista todos los usuarios que existen
     * @return
     */
    public List<Timbrado> listar();


    /**
     * Cambia el rol de un usuario
     * @param usuario a quien se le cambia el rol
     * @param rol a ser asignado al usuario
     * @return boolean
     */
    public Timbrado existeTimbrado(Long id);

    public void eliminarTimbrado(Timbrado timbrado);

    public Timbrado obtenerTimbrado(Long id);

    public boolean tienePermiso(String permiso);
}