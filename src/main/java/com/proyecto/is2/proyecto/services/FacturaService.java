package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.model.Factura;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface FacturaService {

    /**
     * Persiste un objeto @Entity
     * @param obj objeto a persistir
     * @return el objeto persistido
     */
    public Factura guardar(Factura obj);

    /**
     * Listar todos los objetos @Entity persistidos
     * @return
     */
    public List<Factura> listarTodos();

    /**
     * Borra el objeto @Entity entre los disponibles
     * @param obj objeto a persistido a ser borrado
     */
    public void eliminar(Factura obj);

    /**
     * Borra los objetos @Entity entre los disponibles
     * @param objs objetos persistido a ser borrados
     */
    public void eliminar(Collection<Factura> objs);

    /**
     * Verifica si existe el objeto
     * @param idObj id de la entidad
     * @return objeto instanciado
     */
    public Factura obtenerInstancia(Long idObj);
}
