package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.Util.GeneralUtils;
import com.proyecto.is2.proyecto.controller.dto.CajaDTO;
import com.proyecto.is2.proyecto.model.Permiso;
import com.proyecto.is2.proyecto.model.Rol;
import com.proyecto.is2.proyecto.model.Caja;
import com.proyecto.is2.proyecto.repository.CajaRepository;
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
public class CajaServiceImp implements CajaService {

    @Autowired
    private CajaRepository cajaRepository;


    @Override
    public void convertirDTO(Caja caja, CajaDTO objetoDTO) {        
        caja.setDescripcion(objetoDTO.getDescripcion());
        caja.setPinCaja(objetoDTO.getPinCaja());
        caja.setSaldoInicial(objetoDTO.getSaldoInicial());

        return;
    }

    @Override
    public Caja guardar(Caja caja) {
        return cajaRepository.save(caja);
    }

    @Override
    public List<Caja> listar() {
        return cajaRepository.findAll();
    }

    @Override
    public Caja existeCaja(Long id) {
        return cajaRepository.findByIdCaja(id);
    }

    @Override
    public Caja obtenerCaja(Long id) {
        return cajaRepository.findByIdCaja(id);
    }

    @Override
    public void eliminarCaja(Caja caja) {
        cajaRepository.delete(caja);
    }

    @Override
    public boolean tienePermiso(String permiso) {
        /*for(Permiso aux : this.verPermisosUsuarioActual()) {
            if(permiso.equals(aux.toString())) return true;
        }*/
        return true;
    }

}