package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.controller.dto.UnidadMedidaDTO;
import com.proyecto.is2.proyecto.model.UnidadMedida;

import java.util.List;
import java.util.Set;

public interface UnidadMedidaService {

    /**
     * Este método mapea los datos de un formulario para convertirlos
     * a un objeto de tipo Rol
     * @param rol para la entidad
     * @param objetoDTO los datos del formulario
     * @return el objeto Rol creado
     */
    public void convertirDTO(UnidadMedida unidadMedida, UnidadMedidaDTO objetoDTO);

    /**
     * Persiste un objeto del tipo Usuario
     * @param rol objeto a persistir
     * @return el objeto persistido
     */
    public UnidadMedida guardar(UnidadMedida unidadMedida);

    /**
     * Lista todos los roles que existen
     * @return
     */
    public List<UnidadMedida> listar();

    /**
     * Realiza una busqueda entre los roles creados
     * @param id identificador del rol a buscar
     * @return Rol encontrado o null.
     */
    public UnidadMedida existeUnidadMedida(Long id);

    public void eliminarUnidadMedida(UnidadMedida unidadMedida);

    public UnidadMedida obtenerUnidadMedida(Long id);

    public boolean tienePermiso(String permiso);


}
