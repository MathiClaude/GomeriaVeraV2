package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.services.ProductoService;
import com.proyecto.is2.proyecto.services.UsuarioService;
/*import com.sistema.dobby.administration.util.ModelAttributes;
import com.sistema.dobby.administration.util.Permisos;*/
import com.proyecto.is2.proyecto.model.Factura;
import com.proyecto.is2.proyecto.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class FacturaServiceImp implements FacturaService {
    @Autowired
    FacturaRepository facturaRepository;

    @Autowired
    ProductoService productoService;

    @Autowired
    UsuarioService usuarioService;

    @Override
    public Factura guardar(Factura obj) {
        return facturaRepository.save(obj);
    }

    @Override
    public List<Factura> listarTodos() {
        //boolean consultar = usuarioService.tienePermiso(Permisos.READ_ADMINISTRATION_PRIVILEGE.name);

        //if(!consultar) throw new AuthorizationServiceException(ModelAttributes.TITLE_403);

        return facturaRepository.findAll();
    }

    @Override
    public void eliminar(Factura obj) {
        facturaRepository.delete(obj);
    }

    @Override
    public void eliminar(Collection<Factura> objs) {
        facturaRepository.deleteAll(objs);
    }

    @Override
    public Factura obtenerInstancia(Long idObj) {
        //boolean consultar = usuarioService.tienePermiso(Permisos.READ_ADMINISTRATION_PRIVILEGE.name);

        //if(!consultar) throw new AuthorizationServiceException(ModelAttributes.TITLE_403);

        return facturaRepository.findByIdFactura(idObj);
    }
}
