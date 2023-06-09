package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.Util.GeneralUtils;
import com.proyecto.is2.proyecto.controller.dto.OperacionDTO;
import com.proyecto.is2.proyecto.model.Permiso;
import com.proyecto.is2.proyecto.model.Rol;
import com.proyecto.is2.proyecto.model.Operacion;
import com.proyecto.is2.proyecto.model.Operacion;
import com.proyecto.is2.proyecto.repository.OperacionRepository;
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
public class OperacionServiceImp implements OperacionService {

    @Autowired
    private OperacionRepository OperacionRepository;


    @Override
    public void convertirDTO(Operacion operacion, OperacionDTO objetoDTO) {        
        operacion.setConcepto(objetoDTO.getConcepto());
        operacion.setSaldoAnterior(objetoDTO.getSaldoAnterior());
        operacion.setMonto(objetoDTO.getMonto());
        operacion.setSaldoPosterior(objetoDTO.getSaldoPosterior());
        operacion.setFechaOperacion(objetoDTO.getFechaOperacion());

        return;
    }

    @Override
    public Operacion guardar(Operacion operacion) {
        return OperacionRepository.save(operacion);
    }

    @Override
    public List<Operacion> listar() {
        return OperacionRepository.findAll();
    }

    @Override
    public Operacion existeOperacion(Long id) {
        return OperacionRepository.findByIdOperacion(id);
    }

    @Override
    public Operacion obtenerOperacion(Long id) {
        return OperacionRepository.findByIdOperacion(id);
    }

    @Override
    public void eliminarOperacion(Operacion operacion) {
        OperacionRepository.delete(operacion);
    }

    @Override
    public boolean tienePermiso(String permiso) {
        /*for(Permiso aux : this.verPermisosUsuarioActual()) {
            if(permiso.equals(aux.toString())) return true;
        }*/
        return true;
    }

}