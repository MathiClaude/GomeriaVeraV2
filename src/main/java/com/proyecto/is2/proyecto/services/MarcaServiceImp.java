package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.Util.GeneralUtils;
import com.proyecto.is2.proyecto.controller.dto.MarcaDTO;
import com.proyecto.is2.proyecto.model.Permiso;
import com.proyecto.is2.proyecto.model.Rol;
import com.proyecto.is2.proyecto.model.Usuario;
import com.proyecto.is2.proyecto.model.Marca;
import com.proyecto.is2.proyecto.repository.MarcaRepository;
import com.proyecto.is2.proyecto.repository.UsuarioRepository;

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

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    public void convertirDTO(Marca marca, MarcaDTO objetoDTO) {
        marca.setNombre(objetoDTO.getNombre());
        marca.setDescripcion(objetoDTO.getDescripcion());

        return;
    }

    @Override
    public Marca guardar(Marca marca) {
        return marcaRepository.save(marca);
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
    public void eliminarMarca(Marca marca) {
        marcaRepository.delete(marca);
    }


}