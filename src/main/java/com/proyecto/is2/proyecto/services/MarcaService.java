package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.controller.dto.MarcaDTO;
import com.proyecto.is2.proyecto.model.Marca;

import java.util.List;
import java.util.Set;

public interface MarcaService {

    /**
     * Este mÃ©todo mapea los datos de un formulario para convertirlos
     * a un objeto de tipo Usuario
     * @param objetoDTO los datos del formulario
     * @return el objeto Usuario creado
     */
    public void convertirDTO(Marca marca, MarcaDTO objetoDTO);

    /**
     * Persiste un objeto del tipo TipoDocumento
     * @param usuario objeto a persistir
     * @return el objeto persistido
     */
    public Marca guardar(Marca marca);

    /**
     * Lista todos los usuarios que existen
     * @return
     */
    public List<Marca> listar();


    /**
     * Cambia el rol de un usuario
     * @param usuario a quien se le cambia el rol
     * @param rol a ser asignado al usuario
     * @return boolean
     */
    public Marca existeMarca(Long id);

    public void eliminarMarca(Marca marca);

    public Marca obtenerMarca(Long id);

    public boolean tienePermiso(String permiso);
}