package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.services.ProductoService;
import com.proyecto.is2.proyecto.services.UsuarioService;
/*import com.sistema.dobby.administration.util.ModelAttributes;
import com.sistema.dobby.administration.util.Permisos;*/
import com.proyecto.is2.proyecto.model.VentaDetalle;
import com.proyecto.is2.proyecto.repository.VentaDetalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class VentaDetalleServiceImp implements VentaDetalleService {
    @Autowired
    VentaDetalleRepository ventaDetalleRepository;

    @Autowired
    ProductoService productoService;

    @Autowired
    UsuarioService usuarioService;

    @Override
    public VentaDetalle guardar(VentaDetalle obj) {
        return ventaDetalleRepository.save(obj);
    }

    @Override
    public List<VentaDetalle> listarTodos() {
        //boolean consultar = usuarioService.tienePermiso(Permisos.READ_ADMINISTRATION_PRIVILEGE.name);

        //if(!consultar) throw new AuthorizationServiceException(ModelAttributes.TITLE_403);

        return ventaDetalleRepository.findAll();
    }

    @Override
    public void eliminar(VentaDetalle obj) {
        ventaDetalleRepository.delete(obj);
    }

    @Override
    public void eliminar(Collection<VentaDetalle> objs) {
        ventaDetalleRepository.deleteAll(objs);
    }

    @Override
    public VentaDetalle obtenerInstancia(Long idObj) {
        //boolean consultar = usuarioService.tienePermiso(Permisos.READ_ADMINISTRATION_PRIVILEGE.name);

        //if(!consultar) throw new AuthorizationServiceException(ModelAttributes.TITLE_403);

        return ventaDetalleRepository.findByIdVentaDetalle(idObj);
    }
}
