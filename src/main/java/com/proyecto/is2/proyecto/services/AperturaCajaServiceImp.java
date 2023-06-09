package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.Util.GeneralUtils;
import com.proyecto.is2.proyecto.controller.dto.AperturaCajaDTO;
import com.proyecto.is2.proyecto.model.Permiso;
import com.proyecto.is2.proyecto.model.Rol;
import com.proyecto.is2.proyecto.model.AperturaCaja;
import com.proyecto.is2.proyecto.model.Caja;
import com.proyecto.is2.proyecto.repository.AperturaCajaRepository;
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
public class AperturaCajaServiceImp implements AperturaCajaService {

    @Autowired
    private AperturaCajaRepository AperturaCajaRepository;


    @Override
    public void convertirDTO(AperturaCaja aperturaCaja, AperturaCajaDTO objetoDTO) {        
        aperturaCaja.setFechaApertura(objetoDTO.getFechaApertura());
        aperturaCaja.setFechaCierre(objetoDTO.getFechaCierre());
        aperturaCaja.setSaldoApertura(objetoDTO.getSaldoApertura());
        aperturaCaja.setSaldoCierre(objetoDTO.getSaldoCierre());
        aperturaCaja.setEstado(objetoDTO.getEstado());

        return;
    }

    @Override
    public AperturaCaja guardar(AperturaCaja aperturaCaja) {
        return AperturaCajaRepository.save(aperturaCaja);
    }

    @Override
    public List<AperturaCaja> listar() {
        return AperturaCajaRepository.findAll();
    }

    @Override
    public AperturaCaja existeAperturaCaja(Long id) {
        return AperturaCajaRepository.findByIdAperturaCaja(id);
    }

    @Override
    public AperturaCaja obtenerAperturaCaja(Long id) {
        return AperturaCajaRepository.findByIdAperturaCaja(id);
    }

    @Override
    public void eliminarAperturaCaja(AperturaCaja aperturaCaja) {
        AperturaCajaRepository.delete(aperturaCaja);
    }

    @Override
    public boolean tienePermiso(String permiso) {
        /*for(Permiso aux : this.verPermisosUsuarioActual()) {
            if(permiso.equals(aux.toString())) return true;
        }*/
        return true;
    }

}