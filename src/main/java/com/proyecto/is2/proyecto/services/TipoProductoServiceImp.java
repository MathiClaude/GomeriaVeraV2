package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.Util.GeneralUtils;
import com.proyecto.is2.proyecto.controller.dto.TipoProductoDTO;
import com.proyecto.is2.proyecto.model.Permiso;
import com.proyecto.is2.proyecto.model.Rol;
import com.proyecto.is2.proyecto.model.TipoProducto;
import com.proyecto.is2.proyecto.repository.TipoProductoRepository;
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
public class TipoProductoServiceImp implements TipoProductoService {

    @Autowired
    private TipoProductoRepository tipoProductoRepository;


    @Override
    public void convertirDTO(TipoProducto tipoProducto, TipoProductoDTO objetoDTO) {
        tipoProducto.setDescripcion(objetoDTO.getDescripcion());

        return;
    }

    @Override
    public TipoProducto guardar(TipoProducto tipoProducto) {
        return tipoProductoRepository.save(tipoProducto);
    }

    @Override
    public List<TipoProducto> listar() {
        return tipoProductoRepository.findAll();
    }

    @Override
    public TipoProducto existeTipoProducto(Long id) {
        return tipoProductoRepository.findByIdTipoProducto(id);
    }

    @Override
    public TipoProducto obtenerTipoProducto(Long id) {
        return tipoProductoRepository.findByIdTipoProducto(id);
    }

    @Override
    public void eliminarTipoProducto(TipoProducto tipoProducto) {
        tipoProductoRepository.delete(tipoProducto);
    }

    @Override
    public boolean tienePermiso(String permiso) {
        /*for(Permiso aux : this.verPermisosUsuarioActual()) {
            if(permiso.equals(aux.toString())) return true;
        }*/
        return true;
    }

}
