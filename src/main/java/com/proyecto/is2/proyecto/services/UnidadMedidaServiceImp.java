package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.controller.dto.UnidadMedidaDTO;
import com.proyecto.is2.proyecto.model.Permiso;
import com.proyecto.is2.proyecto.model.UnidadMedida;
import com.proyecto.is2.proyecto.model.Usuario;
import com.proyecto.is2.proyecto.repository.UnidadMedidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;

@Service
public class UnidadMedidaServiceImp implements UnidadMedidaService {

    @Autowired
    private UnidadMedidaRepository unidadMedidaRepository;


    @Override
    public void convertirDTO(UnidadMedida unidadMedida, UnidadMedidaDTO objetoDTO) {
        unidadMedida.setCodigo(objetoDTO.getCodigo());
        unidadMedida.setDescripcion(objetoDTO.getDescripcion());
        return;
    }

    @Override
    public UnidadMedida guardar(UnidadMedida unidadMedida) {
        return unidadMedidaRepository.save(unidadMedida);
    }

    @Override
    public List<UnidadMedida> listar() {
        return unidadMedidaRepository.findAll();
    }

    @Override
    public UnidadMedida existeUnidadMedida(Long id) {
        return unidadMedidaRepository.findByIdUnidadMedida(id);
    }

    @Override
    public UnidadMedida obtenerUnidadMedida(Long id) {
        return unidadMedidaRepository.findByIdUnidadMedida(id);
    }
    
    @Override
    public void eliminarUnidadMedida(UnidadMedida unidadMedida) {
        unidadMedidaRepository.delete(unidadMedida);
    }

    @Override
    public boolean tienePermiso(String permiso) {
        /*for(Permiso aux : this.verPermisosUsuarioActual()) {
            if(permiso.equals(aux.toString())) return true;
        }*/
        return true;
    }

}
