package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.controller.dto.ClienteDTO;
import com.proyecto.is2.proyecto.model.Cliente;
import com.proyecto.is2.proyecto.model.Vista;
import com.proyecto.is2.proyecto.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;

@Service
public class ClienteServiceImp implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;


    @Override
    public void convertirDTO(Cliente cliente, ClienteDTO objetoDTO) {
        cliente.setName(objetoDTO.getName());
        cliente.setLastName(objetoDTO.getLastName());
        cliente.setEmail(objetoDTO.getEmail());
        cliente.setTelephone(objetoDTO.getTelephone());
        cliente.setDocumentNumber(objetoDTO.getDocumentNumber());
        cliente.setDocumentType(objetoDTO.getDocumentType());
        return;
    }

    @Override
    public Cliente guardar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente existeCliente(Long id) {
        return clienteRepository.findByIdCliente(id);
    }

    @Override
    public Cliente obtenerCliente(Long id) {
        return clienteRepository.findByIdCliente(id);
    }

    @Override
    public void eliminarCliente(Cliente cliente) {
        clienteRepository.delete(cliente);
    }

    @Override
    public boolean tienePermiso(String permiso) {
        /*for(Permiso aux : this.verPermisosUsuarioActual()) {
            if(permiso.equals(aux.toString())) return true;
        }*/
        return true;
    }
    
}
