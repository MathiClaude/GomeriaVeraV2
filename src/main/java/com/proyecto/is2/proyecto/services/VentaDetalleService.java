package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.model.VentaDetalle;

import java.util.Collection;
import java.util.List;

public interface VentaDetalleService {

    /**
     * Persiste un objeto @Entity
     * @param obj objeto a persistir
     * @return el objeto persistido
     */
    public VentaDetalle guardar(VentaDetalle obj);

    /**
     * Listar todos los objetos @Entity persistidos
     * @return
     */
    public List<VentaDetalle> listarTodos();

    /**
     * Borra el objeto @Entity entre los disponibles
     * @param obj objeto a persistido a ser borrado
     */
    public void eliminar(VentaDetalle obj);

    /**
     * Borra los objetos @Entity entre los disponibles
     * @param objs objetos persistido a ser borrados
     */
    public void eliminar(Collection<VentaDetalle> objs);

    /**
     * Verifica si existe el objeto
     * @param idObj id de la entidad
     * @return objeto instanciado
     */
    public VentaDetalle obtenerInstancia(Long idObj);
}
