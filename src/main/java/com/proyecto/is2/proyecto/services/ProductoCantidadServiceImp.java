package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.controller.dto.ProductoCantidadDTO;
import com.proyecto.is2.proyecto.model.ProductoCantidad;
import com.proyecto.is2.proyecto.model.Vista;
import com.proyecto.is2.proyecto.repository.ProductoCantidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;

@Service
public class ProductoCantidadServiceImp implements ProductoCantidadService {

    @Autowired
    private ProductoCantidadRepository productoCantidadRepository;

    //@Autowired
    //private VistaServiceImp vistaService;

    /*public void crearPermisos() {
        vistaService.crearVistas();

        List<Permiso> permisos = new ArrayList<>();

        permisos.add(new Permiso("conectarse", "Permiso para conectase al sistema"));
        permisos.add(new Permiso("asignar-rol-usuario", "Permite asignar roles con permiso a un Usuario"));
        permisos.add(new Permiso("asignar-permisos-rol", "Permite asignar permisos a un rol"));
        permisos.add(new Permiso("eliminar-permisos-rol", "Permite eliminar permisos a un rol"));
        permisos.add(new Permiso("agregar-miembro-proyecto", "Permite agregar nuevos miembros al proyecto"));
        permisos.add(new Permiso("eliminar-miembro-proyecto", "Permite eliminar miembros del proyecto"));

        for(Vista vista : vistaService.listar()) {
            Permiso p1 = new Permiso("crear", "Permiso de creacion");
            p1.setVista(vista);
            Permiso p2 = new Permiso("actualizar", "Permiso de actualizacion");
            p2.setVista(vista);
            Permiso p3 = new Permiso("eliminar", "Permiso de eliminacion");
            p3.setVista(vista);
            Permiso p4 = new Permiso("consultar", "Permiso de consulta");
            p4.setVista(vista);

            permisos.addAll(Arrays.asList(p1, p2, p3, p4));
        }

        permisoRepository.saveAll(permisos);
    }*/

    @Override
    public void convertirDTO(ProductoCantidad productoCantidad, ProductoCantidadDTO objetoDTO) {
        //permiso.setNombre(objetoDTO.getNombre());
        //permiso.setDescripcion(objetoDTO.getDescripcion());
        //permiso.setVista(vistaService.existeVista(objetoDTO.getIdVista()));
        return;
    }

    @Override
    public ProductoCantidad guardar(ProductoCantidad productoCantidad) {
        return productoCantidadRepository.save(productoCantidad);
    }

    @Override
    public List<ProductoCantidad> listar() {
        return productoCantidadRepository.findAll();
    }

    @Override
    public ProductoCantidad existeProductoCantidad(Long id) {
        return productoCantidadRepository.findByIdProductoCantidad(id);
    }

    @Override
    public void eliminarProductoCantidad(ProductoCantidad productoCantidad) {
        productoCantidadRepository.delete(productoCantidad);
    }
}
