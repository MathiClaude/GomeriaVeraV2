package com.proyecto.is2.proyecto.services;


import com.proyecto.is2.proyecto.model.Venta;
import com.proyecto.is2.proyecto.controller.dto.VentaDTO;

import java.util.List;
import java.util.Set;

public interface VentaService {

    /**
     * Este método mapea los datos de un formulario para convertirlos
     * a un objeto de tipo Venta
     * @param objetoDTO los datos del formulario
     * @return el objeto Venta creado
     */
    public void convertirDTO(Venta venta, VentaDTO objetoDTO);

    /**
     * Persiste un objeto del tipo Venta
     * @param Venta objeto a persistir
     * @return el objeto persistido
     */
    public Venta guardar(Venta venta);

    /**
     * Lista todos los producto que existen
     * @return
     */
    public List<Venta> listar();

    /**
     * Verifica si existe una venta a traves de su id
     * @param id de la venta
     * @return la venta si existe
     */
    public Venta existeVenta(Long id);

    public void eliminarVenta(Venta venta);

    public Venta obtenerVenta(Long id);

    public boolean tienePermiso(String permiso);

}
