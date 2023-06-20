package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.services.ProductoService;
import com.proyecto.is2.proyecto.services.UsuarioService;
/*import com.sistema.dobby.administration.util.ModelAttributes;
import com.sistema.dobby.administration.util.Permisos;*/
import com.proyecto.is2.proyecto.model.CompraDetalle;
import com.proyecto.is2.proyecto.repository.CompraDetalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class CompraDetalleServiceImp implements CompraDetalleService {
    @Autowired
    CompraDetalleRepository compraDetalleRepository;

    @Autowired
    ProductoService productoService;

    @Autowired
    UsuarioService usuarioService;

    @Override
    public CompraDetalle guardar(CompraDetalle obj) {
        return compraDetalleRepository.save(obj);
    }

    @Override
    public List<CompraDetalle> listarTodos() {
        //boolean consultar = usuarioService.tienePermiso(Permisos.READ_ADMINISTRATION_PRIVILEGE.name);

        //if(!consultar) throw new AuthorizationServiceException(ModelAttributes.TITLE_403);

        return compraDetalleRepository.findAll();
    }

    @Override
    public void eliminar(CompraDetalle obj) {
        compraDetalleRepository.delete(obj);
    }

    @Override
    public void eliminar(Collection<CompraDetalle> objs) {
        compraDetalleRepository.deleteAll(objs);
    }

    @Override
    public CompraDetalle obtenerInstancia(Long idObj) {
        //boolean consultar = usuarioService.tienePermiso(Permisos.READ_ADMINISTRATION_PRIVILEGE.name);

        //if(!consultar) throw new AuthorizationServiceException(ModelAttributes.TITLE_403);

        return compraDetalleRepository.findByIdCompraDetalle(idObj);
    }
}
