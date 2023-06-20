package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.model.CompraDetalle;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface CompraDetalleService {

    /**
     * Persiste un objeto @Entity
     * @param obj objeto a persistir
     * @return el objeto persistido
     */
    public CompraDetalle guardar(CompraDetalle obj);

    /**
     * Listar todos los objetos @Entity persistidos
     * @return
     */
    public List<CompraDetalle> listarTodos();

    /**
     * Borra el objeto @Entity entre los disponibles
     * @param obj objeto a persistido a ser borrado
     */
    public void eliminar(CompraDetalle obj);

    /**
     * Borra los objetos @Entity entre los disponibles
     * @param objs objetos persistido a ser borrados
     */
    public void eliminar(Collection<CompraDetalle> objs);

    /**
     * Verifica si existe el objeto
     * @param idObj id de la entidad
     * @return objeto instanciado
     */
    public CompraDetalle obtenerInstancia(Long idObj);
}
