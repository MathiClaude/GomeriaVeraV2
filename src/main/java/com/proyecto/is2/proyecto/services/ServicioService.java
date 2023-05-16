package com.proyecto.is2.proyecto.services;


import com.proyecto.is2.proyecto.model.Servicio;
import com.proyecto.is2.proyecto.controller.dto.ServicioDTO;

import java.util.List;
import java.util.Set;

public interface ServicioService {

    /**
     * Este método mapea los datos de un formulario para convertirlos
     * a un objeto de tipo Servicio
     * @param objetoDTO los datos del formulario
     * @return el objeto Servicio creado
     */
    public void convertirDTO(Servicio servicio, ServicioDTO objetoDTO);

    /**
     * Persiste un objeto del tipo Servicio
     * @param servicio objeto a persistir
     * @return el objeto persistido
     */
    public Servicio guardar(Servicio servicio);

    /**
     * Lista todos los Servicio que existen
     * @return
     */
    public List<Servicio> listar();

    /**
     * Verifica si existe un Servicio a traves de su id
     * @param id del Servicio
     * @return el Servicio si existe
     */
    public Servicio existeServicio(Long id);

    public void eliminarServicio(Servicio servicio);

    public Servicio obtenerServicio(Long id);

    public boolean tienePermiso(String permiso);

}
