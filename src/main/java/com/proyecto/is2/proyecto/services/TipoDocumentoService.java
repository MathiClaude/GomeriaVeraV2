package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.model.TipoDocumento;
import com.proyecto.is2.proyecto.controller.dto.TipoDocumentoDTO;

import java.util.List;
import java.util.Set;

public interface TipoDocumentoService {

    /**
     * Este método mapea los datos de un formulario para convertirlos
     * a un objeto de tipo Producto
     * @param objetoDTO los datos del formulario
     * @return el objeto Producto creado
     */
    public void convertirDTO(TipoDocumento tipoDocumento, TipoDocumentoDTO objetoDTO);

    /**
     * Persiste un objeto del tipo Producto
     * @param TipoDocumento objeto a persistir
     * @return el objeto persistido
     */
    public TipoDocumento guardar(TipoDocumento tipoDocumento);

    /**
     * Lista todos los producto que existen
     * @return
     */
    public List<TipoDocumento> listar();

    /**
     * Verifica si existe un tipoDocumento a traves de su id
     * @param id del tipoDocumento
     * @return el tipoDocumento si existe
     */
    public TipoDocumento existeTipoDocumento(Long id);

    public void eliminarTipoDocumento(TipoDocumento tipoDocumento);

}
