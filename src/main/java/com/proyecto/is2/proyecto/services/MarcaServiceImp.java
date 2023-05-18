package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.Util.GeneralUtils;
import com.proyecto.is2.proyecto.controller.dto.MarcaDTO;
import com.proyecto.is2.proyecto.model.Permiso;
import com.proyecto.is2.proyecto.model.Rol;
import com.proyecto.is2.proyecto.model.Marca;
import com.proyecto.is2.proyecto.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;

@Service
public class MarcaServiceImp implements MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;


    @Override
    public void convertirDTO(Marca servicio, MarcaDTO objetoDTO) {
        servicio.setDescripcion(objetoDTO.getDescripcion());

        return;
    }

    @Override
    public Marca guardar(Marca servicio) {
        return marcaRepository.save(servicio);
    }

    @Override
    public List<Marca> listar() {
        return marcaRepository.findAll();
    }

    @Override
    public Marca existeMarca(Long id) {
        return marcaRepository.findByIdMarca(id);
    }

    @Override
    public Marca obtenerMarca(Long id) {
        return marcaRepository.findByIdMarca(id);
    }

    @Override
    public void eliminarMarca(Marca servicio) {
        marcaRepository.delete(servicio);
    }

    @Override
    public boolean tienePermiso(String permiso) {
        /*for(Permiso aux : this.verPermisosUsuarioActual()) {
            if(permiso.equals(aux.toString())) return true;
        }*/
        return true;
    }

}