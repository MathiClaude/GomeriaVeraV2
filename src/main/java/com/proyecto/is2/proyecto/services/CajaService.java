package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.controller.dto.CajaDTO;
import com.proyecto.is2.proyecto.model.Caja;

import java.util.List;
import java.util.Set;

public interface CajaService {

    /**
     * Este mÃ©todo mapea los datos de un formulario para convertirlos
     * a un objeto de tipo Usuario
     * @param objetoDTO los datos del formulario
     * @return el objeto Usuario creado
     */
    public void convertirDTO(Caja caja, CajaDTO objetoDTO);

    /**
     * Persiste un objeto del tipo TipoDocumento
     * @param usuario objeto a persistir
     * @return el objeto persistido
     */
    public Caja guardar(Caja caja);

    /**
     * Lista todos los usuarios que existen
     * @return
     */
    public List<Caja> listar();


    /**
     * Cambia el rol de un usuario
     * @param usuario a quien se le cambia el rol
     * @param rol a ser asignado al usuario
     * @return boolean
     */
    public Caja existeCaja(Long id);

    public void eliminarCaja(Caja caja);

    public Caja obtenerCaja(Long id);

    public boolean tienePermiso(String permiso);
}