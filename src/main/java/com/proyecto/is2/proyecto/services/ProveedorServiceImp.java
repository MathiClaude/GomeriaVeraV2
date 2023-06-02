package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.controller.dto.ProveedorDTO;
import com.proyecto.is2.proyecto.model.Proveedor;
import com.proyecto.is2.proyecto.model.Vista;
import com.proyecto.is2.proyecto.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;

@Service
public class ProveedorServiceImp implements ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;


    @Override
    public void convertirDTO(Proveedor proveedor, ProveedorDTO objetoDTO) {
        proveedor.setNombre(objetoDTO.getNombre());
        proveedor.setRazonSocial(objetoDTO.getRazonSocial());
        proveedor.setContacto(objetoDTO.getContacto());
        proveedor.setEsActual(objetoDTO.getEsActual());
        proveedor.setTipoDocumento(objetoDTO.getTipoDocumento());
        proveedor.setTipoPersona(objetoDTO.getTipoPersona());
        proveedor.setRuc(objetoDTO.getRuc());
        proveedor.setTelefono(objetoDTO.getTelefono());
        proveedor.setDireccion(objetoDTO.getDireccion());
        proveedor.setContactos(objetoDTO.getContactos());
        
        return;
    }

    @Override
    public Proveedor guardar(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    @Override
    public List<Proveedor> listar() {
        return proveedorRepository.findAll();
    }

    @Override
    public Proveedor existeProveedor(Long id) {
        return proveedorRepository.findByIdProveedor(id);
    }

    @Override
    public Proveedor obtenerProveedor(Long id) {
        return proveedorRepository.findByIdProveedor(id);
    }

    @Override
    public void eliminarProveedor(Proveedor proveedor) {
        proveedorRepository.delete(proveedor);
    }

    @Override
    public boolean tienePermiso(String permiso) {
        /*for(Permiso aux : this.verPermisosUsuarioActual()) {
            if(permiso.equals(aux.toString())) return true;
        }*/
        return true;
    }

}
