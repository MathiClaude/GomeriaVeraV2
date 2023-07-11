package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.controller.dto.ProductoDTO;
import com.proyecto.is2.proyecto.model.Producto;
import com.proyecto.is2.proyecto.model.Vista;
import com.proyecto.is2.proyecto.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;

@Service
public class ProductoServiceImp implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

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
    public void convertirDTO(Producto producto, ProductoDTO objetoDTO) {
        producto.setNombreProducto(objetoDTO.getNombreProducto());
        producto.setPrecio(objetoDTO.getPrecio());
        producto.setCantidad(objetoDTO.getCantidad());
        producto.setPrecioCompra(objetoDTO.getPrecioCompra());
        producto.setTmpPrecio(objetoDTO.getTmpPrecio());
        producto.setCantidadMinima(objetoDTO.getCantidadMinima());
        //producto.setTipoProducto(objetoDTO.getTipoProducto());
        producto.setCodigo(objetoDTO.getCodigo());
        //producto.setUnidadMedida(objetoDTO.getUnidadMedida());
        //producto.setFamiliaProducto(objetoDTO.getFamiliaProducto());
        producto.setImpuesto(objetoDTO.getImpuesto());
        producto.setProcedencia(objetoDTO.getProcedencia());
        //producto.setProveedor(objetoDTO.getProveedor());
        producto.setDescripcion(objetoDTO.getDescripcion());


        return;
    }

    @Override
    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public List<Producto> listar() {
        return productoRepository.findAll();
    }

    @Override
    public Producto existeProducto(Long id) {
        return productoRepository.findByIdProducto(id);
    }

    @Override
    public Producto obtenerProducto(Long id) {
        return productoRepository.findByIdProducto(id);
    }

    @Override
    public void eliminarProducto(Producto producto) {
        productoRepository.delete(producto);
    }

    @Override
    public boolean tienePermiso(String permiso) {
        /*for(Permiso aux : this.verPermisosUsuarioActual()) {
            if(permiso.equals(aux.toString())) return true;
        }*/
        return true;
    }

}
