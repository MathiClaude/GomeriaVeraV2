package com.proyecto.is2.proyecto.controller;
import com.proyecto.is2.proyecto.controller.dto.ProductoDTO;
import com.proyecto.is2.proyecto.model.Rol;
import com.proyecto.is2.proyecto.model.Usuario;
import com.proyecto.is2.proyecto.model.Servicio;
import com.proyecto.is2.proyecto.model.Producto;
import com.proyecto.is2.proyecto.services.RolServiceImp;
import com.proyecto.is2.proyecto.services.MarcaServiceImp;
import com.proyecto.is2.proyecto.services.ProductoServiceImp;
import com.proyecto.is2.proyecto.services.ProveedorServiceImp;
import com.proyecto.is2.proyecto.services.ServicioServiceImp;
import com.proyecto.is2.proyecto.services.TipoProductoServiceImp;
import com.proyecto.is2.proyecto.services.UnidadMedidaServiceImp;
import com.proyecto.is2.proyecto.services.UsuarioServiceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

/**
 * Controlador encargado de recibir las peticiones
 * para realizar venta
 */
@Controller
@RequestMapping("/productos")
public class ProductoCrudController {
    final String VIEW = "producto"; // identificador de la vista
    final String VIEW_PATH = "producto";
    String operacion = "";
    final String FORM_VIEW = VIEW_PATH + "/productos";
    final String FORM_NEW = VIEW_PATH + "/nuevo";
    final String FORM_EDIT = VIEW_PATH + "/editar";
    final String RD_FORM_VIEW = "redirect:/productos";
    final String FALTA_PERMISO_VIEW = "falta-permiso";
    final String RD_FALTA_PERMISO_VIEW = "redirect:/" + FALTA_PERMISO_VIEW;
    //final String ASIGNAR_ROL_VIEW = VIEW_PATH + "/asignar-rol";
//    final String RD_ASIGNAR_ROL_VIEW = "redirect:/" + ASIGNAR_ROL_VIEW;
    final String P_ASIGNAR_ROL = "asignar-rol-usuario";

    @Autowired
    private ProductoServiceImp productoService; // llamada a los servicios de usuario

    @Autowired
    private ServicioServiceImp servicioService; // llamada a los servicios de usuario

    @Autowired
    private RolServiceImp rolService;//llamada a servicios de roles

    //servicios para los selects
    @Autowired
    private TipoProductoServiceImp tipoProductoService; 
    @Autowired
    private UnidadMedidaServiceImp unidadMedidaService; 
    @Autowired
    private MarcaServiceImp marcaService; 
    @Autowired
    private ProveedorServiceImp proveedorService; 
    @Autowired
    private UsuarioServiceImp usuarioService;

    /**
     * Instancia un UsuarioDTO para rellenar con datos
     * del formulario para luego mapearlo a un objeto
     * Usuario.
     * @return
     */
    @ModelAttribute("producto")
    public ProductoDTO instanciarObjetoDTO() {
        return new ProductoDTO();
    }

    @GetMapping
    public String mostrarGrilla(Model model) {

        boolean consultar = usuarioService.tienePermiso("consultar-" + VIEW);
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);
        boolean eliminar = usuarioService.tienePermiso("eliminar-" + VIEW);
        boolean actualizar = usuarioService.tienePermiso("actualizar-" + VIEW);

        //        if(!crear && !eliminar && !actualizar) {
        //            return FALTA_PERMISO_VIEW;
        //        }

        if(consultar) {
            model.addAttribute("listProduct", productoService.listar());//lista los productos
            model.addAttribute("listServicios", servicioService.listar());//lista los productos
        } else {
            return FALTA_PERMISO_VIEW;
        }

        model.addAttribute("permisoVer", consultar);
        model.addAttribute("permisoCrear", crear);
        model.addAttribute("permisoEliminar", eliminar);
        model.addAttribute("permisoActualizar", actualizar);

        return FORM_VIEW;
    }

    @GetMapping("/nuevo")
    public String formNuevo(Model model) {
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);
        boolean asignarRol = usuarioService.tienePermiso("asignar-rol-" + VIEW);

        if(asignarRol) {
            model.addAttribute("roles", rolService.listar());
        }
        model.addAttribute("permisoAsignarRol", asignarRol);

        //cargamos los selects
        model.addAttribute("tiposProductos", tipoProductoService.listar());
        model.addAttribute("unidadMedidas", unidadMedidaService.listar());
        model.addAttribute("marcas", marcaService.listar());
        model.addAttribute("proveedores", proveedorService.listar());


        if(crear) {
            return FORM_NEW;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @PostMapping("/crear")
    public String crearObjeto(@ModelAttribute("producto") ProductoDTO objetoDTO,
                              RedirectAttributes attributes) {
        this.operacion = "crear-";

//        if (result.hasErrors()) {
//            return FORM_NEW;
//        }

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            Producto producto = new Producto();
            productoService.convertirDTO(producto, objetoDTO);

            // si tiene permiso se le asigna el rol con id del formulario
            // sino se le asignar un rol por defecto.

            //esta parte creo que no se usa para producto
            /*if(productoService.tienePermiso(P_ASIGNAR_ROL)) {
                producto.setRol(rolService.existeRol(objetoDTO.getIdRol().longValue()));
            } else {
                producto.setRol(rolService.existeRol(1L)); // ID 1: Rol sin permisos.
            }*/

            producto.setTipoProducto(tipoProductoService.existeTipoProducto(objetoDTO.getIdTipoProducto().longValue()));
            producto.setMarca(marcaService.existeMarca(objetoDTO.getIdMarca().longValue()));
            producto.setUnidadMedida(unidadMedidaService.existeUnidadMedida(objetoDTO.getIdUnidadMedida().longValue()));
            producto.setProveedor(proveedorService.existeProveedor(objetoDTO.getIdProveedor().longValue()));

            productoService.guardar(producto);

            attributes.addFlashAttribute("message", "¡Producto creado exitosamente!");

            return RD_FORM_VIEW;
        } else {
            return RD_FALTA_PERMISO_VIEW;
        }
    }

    @GetMapping("/{id}")
    public String formEditar(@PathVariable String id, Model model) {
        boolean eliminar = usuarioService.tienePermiso("eliminar-" + VIEW);
        boolean asignarRol = usuarioService.tienePermiso("asignar-rol-" + VIEW);
        Producto producto;

        // validar el id
        try {
            Long idProducto = Long.parseLong(id);
            producto = productoService.existeProducto(idProducto);
        } catch(Exception e) {
            return RD_FORM_VIEW;
        }

        model.addAttribute("product", producto);

        // validar si puede cambiar de rol
        //esta parte creo que no se usa tampoco
        //TODO
        /*if(asignarRol) {
            model.addAttribute("roles", rolService.listar());
        }
        model.addAttribute("permisoAsignarRol", asignarRol);*/

        if(eliminar) {
            return FORM_EDIT;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @PostMapping("/{id}")
    public String actualizarObjeto(@PathVariable Long id, @ModelAttribute("producto") ProductoDTO objetoDTO,
                                   BindingResult result, RedirectAttributes attributes) {
        this.operacion = "actualizar-";
        Producto producto;

        if (result.hasErrors()) {
//            studentDTO.setId(id);
            return RD_FORM_VIEW;
        }

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            producto = productoService.existeProducto(id);
            if(producto != null) {
                productoService.convertirDTO(producto, objetoDTO);

                // si tiene permiso se le asigna el rol con id del formulario
                // sino se queda con el mismo rol.

                //este tampoco creo que se usa
                /*if(productoService.tienePermiso(P_ASIGNAR_ROL)) {
                    producto.setRol(rolService.existeRol(objetoDTO.getIdRol().longValue()));
                }*/

                attributes.addFlashAttribute("message", "¡Producto actualizado correctamente!");
                productoService.guardar(producto);
                return RD_FORM_VIEW;
            }
        }
        return RD_FALTA_PERMISO_VIEW;
    }

    @GetMapping("/{id}/delete")
        public String eliminarObjeto(@PathVariable String id, RedirectAttributes attributes  ) {
        this.operacion = "eliminar-";
        Long idProducto;

        try {
            idProducto = Long.parseLong(id);
        } catch (Exception e) {
            return RD_FORM_VIEW;
        }

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            Producto producto = productoService.obtenerProducto(idProducto);
            productoService.eliminarProducto(producto);
            attributes.addFlashAttribute("message", "¡Producto eliminado correctamente!");
            return RD_FORM_VIEW;
        } else {
            return RD_FALTA_PERMISO_VIEW;
        }
    }


}
