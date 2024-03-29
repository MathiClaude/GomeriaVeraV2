package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.controller.dto.AperturaCajaDTO;
import com.proyecto.is2.proyecto.model.AperturaCaja;

import java.util.List;
import java.util.Set;

public interface AperturaCajaService {

    /**
     * Este mÃ©todo mapea los datos de un formulario para convertirlos
     * a un objeto de tipo Usuario
     * @param objetoDTO los datos del formulario
     * @return el objeto Usuario creado
     */
    public void convertirDTO(AperturaCaja aperturaCaja, AperturaCajaDTO objetoDTO);

    /**
     * Persiste un objeto del tipo TipoDocumento
     * @param usuario objeto a persistir
     * @return el objeto persistido
     */
    public AperturaCaja guardar(AperturaCaja aperturaCaja);

    /**
     * Lista todos los usuarios que existen
     * @return
     */
    public List<AperturaCaja> listar();


    /**
     * Cambia el rol de un usuario
     * @param usuario a quien se le cambia el rol
     * @param rol a ser asignado al usuario
     * @return boolean
     */
    public AperturaCaja existeAperturaCaja(Long id);

    public void eliminarAperturaCaja(AperturaCaja caja);

    public AperturaCaja obtenerAperturaCaja(Long id);

    public boolean tienePermiso(String permiso);
}