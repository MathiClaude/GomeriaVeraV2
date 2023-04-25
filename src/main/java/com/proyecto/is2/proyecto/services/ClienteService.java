package com.proyecto.is2.proyecto.services;


import com.proyecto.is2.proyecto.model.Cliente;
import com.proyecto.is2.proyecto.controller.dto.ClienteDTO;

import java.util.List;
import java.util.Set;

public interface ClienteService {

    /**
     * Este método mapea los datos de un formulario para convertirlos
     * a un objeto de tipo Permiso
     * @param objetoDTO los datos del formulario
     * @return el objeto Permiso creado
     */
    public void convertirDTO(Cliente cliente, ClienteDTO objetoDTO);

    /**
     * Persiste un objeto del tipo Cliente
     * @param Cliente objeto a persistir
     * @return el objeto persistido
     */
    public Cliente guardar(Cliente cliente);

    /**
     * Lista todos los cliente que existen
     * @return
     */
    public List<Cliente> listar();

    /**
     * Verifica si existe un cliente a traves de su id
     * @param id del cliente
     * @return el cliente si existe
     */
    public Cliente existeCliente(Long id);

    public void eliminarCliente(Cliente cliente);

}
