package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.Util.GeneralUtils;
import com.proyecto.is2.proyecto.controller.dto.TimbradoDTO;
import com.proyecto.is2.proyecto.model.Permiso;
import com.proyecto.is2.proyecto.model.Rol;
import com.proyecto.is2.proyecto.model.Timbrado;
import com.proyecto.is2.proyecto.repository.TimbradoRepository;
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
public class TimbradoServiceImp implements TimbradoService {

    @Autowired
    private TimbradoRepository timbradoRepository;


    @Override
    public void convertirDTO(Timbrado timbrado, TimbradoDTO objetoDTO) {        
        timbrado.setNroTimbrado(objetoDTO.getNroTimbrado());
        timbrado.setEstado(objetoDTO.getEstado());
        timbrado.setFchDesde(objetoDTO.getFchDesde());
        timbrado.setFchHasta(objetoDTO.getFchHasta());
        timbrado.setNroDesde(objetoDTO.getNroDesde());
        timbrado.setNroHasta(objetoDTO.getNroHasta());


        return;
    }

    @Override
    public Timbrado guardar(Timbrado timbrado) {
        return timbradoRepository.save(timbrado);
    }

    @Override
    public List<Timbrado> listar() {
        return timbradoRepository.findAll();
    }

    @Override
    public Timbrado existeTimbrado(Long id) {
        return timbradoRepository.findByIdTimbrado(id);
    }

    @Override
    public Timbrado obtenerTimbrado(Long id) {
        return timbradoRepository.findByIdTimbrado(id);
    }

    @Override
    public void eliminarTimbrado(Timbrado timbrado) {
        timbradoRepository.delete(timbrado);
    }

    @Override
    public boolean tienePermiso(String permiso) {
        /*for(Permiso aux : this.verPermisosUsuarioActual()) {
            if(permiso.equals(aux.toString())) return true;
        }*/
        return true;
    }

}