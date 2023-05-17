package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.controller.dto.ServicioDTO;
import com.proyecto.is2.proyecto.model.Servicio;
import com.proyecto.is2.proyecto.model.Vista;
import com.proyecto.is2.proyecto.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;

@Service
public class ServicioServiceImp implements ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;


    @Override
    public void convertirDTO(Servicio servicio, ServicioDTO objetoDTO) {
        servicio.setDescripcionServicio(objetoDTO.getDescripcionServicio());
        servicio.setPrecio(objetoDTO.getPrecio());
        
        return;
    }

    @Override
    public Servicio guardar(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    @Override
    public List<Servicio> listar() {
        return servicioRepository.findAll();
    }

    @Override
    public Servicio existeServicio(Long id) {
        return servicioRepository.findByIdServicio(id);
    }

    @Override
    public Servicio obtenerServicio(Long id) {
        return servicioRepository.findByIdServicio(id);
    }

    @Override
    public void eliminarServicio(Servicio servicio) {
        servicioRepository.delete(servicio);
    }

    @Override
    public boolean tienePermiso(String permiso) {
        /*for(Permiso aux : this.verPermisosUsuarioActual()) {
            if(permiso.equals(aux.toString())) return true;
        }*/
        return true;
    }

}
