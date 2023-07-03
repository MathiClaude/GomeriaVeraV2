
package com.proyecto.is2.proyecto.controller;
import com.proyecto.is2.proyecto.controller.dto.MarcaDTO;

import com.proyecto.is2.proyecto.model.Permiso;
import com.proyecto.is2.proyecto.model.Marca;
import com.proyecto.is2.proyecto.model.Producto;
import com.proyecto.is2.proyecto.services.RolServiceImp;
import com.proyecto.is2.proyecto.services.MarcaServiceImp;
import com.proyecto.is2.proyecto.services.PermisoServiceImp;
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
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/stocks")
public class InventarioController {
    final String VIEW = "gestionProducto"; // identificador de la vista
    final String VIEW_PATH = "gestionProducto";
    String operacion = "";
    final String FORM_VIEW = VIEW_PATH + "/stocks";
    final String FORM_NEW = VIEW_PATH + "/nuevo";
    final String FORM_EDIT = VIEW_PATH + "/editar";
    final String RD_FORM_VIEW = "redirect:/stocks";
    final String FALTA_PERMISO_VIEW = "falta-permiso";
    final String RD_FALTA_PERMISO_VIEW = "redirect:/" + FALTA_PERMISO_VIEW;
    final String LISTA_VIEW = VIEW + "/listar";
    final String ASIGNAR_ROL_VIEW = VIEW + "/asignar-rol";
    final String RD_ASIGNAR_ROL_VIEW = "redirect:/" + ASIGNAR_ROL_VIEW;

    @Autowired
    private MarcaServiceImp marcaService;

    @Autowired
    private PermisoServiceImp permisoService;

    @Autowired
    private UsuarioServiceImp usuarioService;

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
    private ProveedorServiceImp proveedorService; 


    @ModelAttribute("listPermisos")
    public List<Long> listaPermisos() {
        return new ArrayList<>();
    }

    @ModelAttribute("marca")
    public MarcaDTO instanciarObjetoDTO() {
        return new MarcaDTO();
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

        if(crear) {
            return FORM_NEW;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @PostMapping("/crear")
    public String crearObjeto(@ModelAttribute("marca") MarcaDTO objetoDTO,
            RedirectAttributes attributes) {
        this.operacion = "crear-";

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            Marca marca = new Marca();
            marcaService.convertirDTO(marca, objetoDTO);
            marcaService.guardar(marca);

            attributes.addFlashAttribute("message", "Marca creado exitosamente!");

            return RD_FORM_VIEW;
        } else {
            return RD_FALTA_PERMISO_VIEW;
        }
    }

    @GetMapping("/{id}/delete")
    public String eliminarObjeto(@PathVariable String id, RedirectAttributes attributes) {
        this.operacion = "eliminar-";
        Long idMarca;

        try {
            idMarca = Long.parseLong(id);
        } catch (Exception e) {
            attributes.addFlashAttribute("message", "¡Id marca no valido!");
            return RD_FORM_VIEW;
        }

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            Marca marca = marcaService.existeMarca(idMarca);
            marcaService.eliminarMarca(marca);
            attributes.addFlashAttribute("message", "Marca eliminado correctamente!");
            return RD_FORM_VIEW;
        } else {
            return RD_FALTA_PERMISO_VIEW;
        }
    }

    @GetMapping("/{id}")
    public String formEditar(@PathVariable String id, Model model) {
        boolean eliminar = usuarioService.tienePermiso("eliminar-" + VIEW);
        Marca marca;
        Producto producto;

        // validar el id
        try {
            Long idProducto = Long.parseLong(id);
            producto = productoService.existeProducto(idProducto);
        } catch(Exception e) {
            return RD_FORM_VIEW;
        }

        model.addAttribute("product", producto);
        if(eliminar) {
            //model.addAttribute("marca", marca);
            return FORM_EDIT;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @PostMapping("/{id}")
    public String actualizarObjeto(@PathVariable Long id, @ModelAttribute("producto") MarcaDTO objetoDTO,
                                   BindingResult result, RedirectAttributes attributes) {
        this.operacion = "actualizar-";
        Marca marca;

        if (result.hasErrors()) {
            return RD_FORM_VIEW;
        }

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            marca = marcaService.existeMarca(id);
            if(marca != null) {
                marcaService.convertirDTO(marca, objetoDTO);
                attributes.addFlashAttribute("message", "Marca actualizado correctamente!");
                marcaService.guardar(marca);
                return RD_FORM_VIEW;
            } else {
                attributes.addFlashAttribute("message", "¡Id del marca no existe!");
                return RD_FORM_VIEW;
            }
        }
        return RD_FALTA_PERMISO_VIEW;
    }



   /* @GetMapping("/{idRol}/permisos")
    public String verPermisosRol(Model model, @PathVariable Long idRol) {
        if(usuarioService.tienePermiso("asignar-permisos-rol")) {
            Rol rol = rolService.existeRol(idRol);
            if(rol != null) {
                model.addAttribute("objRol", rol);
                model.addAttribute("listPermiso", permisoService.listar());
                model.addAttribute("listRolPermiso", rol.getPermisos());
                model.addAttribute("permisoAsignarPer", true);
                model.addAttribute("permisoEliminarPer", usuarioService.tienePermiso("eliminar-permisos-rol"));
                model.addAttribute("permisoVer", usuarioService.tienePermiso("consultar-permiso"));
                return "rol/asignar-permisos";
            }
        } else {
            return FALTA_PERMISO_VIEW;
        }
        System.out.println("ERROR. No se encontro rol con ID:" + idRol);
        return RD_FORM_VIEW;
    }*/

    /**
     * Agregar un nuevo permiso al rol.
     * @param idRol identificador del rol.
     * @param idPermiso identificador del permiso.
     * @return
     */
    /*@PostMapping("/agregar-permiso")
    public String agregarPermiso(@RequestParam("id_rol") Integer idRol, @RequestParam("id_permiso") Integer idPermiso, Model model) {
        this.operacion = "asignar-permisos-rol";

        if(usuarioService.tienePermiso(operacion)) {
            Rol rol = rolService.existeRol(idRol.longValue());
            Permiso permiso = permisoService.existePermiso(idPermiso.longValue());

            if(rol != null && permiso != null) {
                rol.getPermisos().add(permiso);
                rolService.guardar(rol);
            } else {
                model.addAttribute("error", "Error. Rol: " + rol + ". Permiso: " + permiso);
                return "general-error";
            }

            return "redirect:/roles/" + idRol + "/permisos";
        }

        return RD_FALTA_PERMISO_VIEW;
    }*/

    /**
     * Eliminar un permiso del rol.
     * @param idRol identificador del rol.
     * @param idPermiso identificador del permiso.
     * @return
     */
   /*  @PostMapping("/eliminar-permiso")
    public String eliminarPermiso(@RequestParam("id_rol") Long idRol, @RequestParam("id_permiso") Long idPermiso, Model model) {
        this.operacion = "asignar-permisos-rol";

        if(usuarioService.tienePermiso(operacion)) {
            Rol rol = rolService.existeRol(idRol);
            Permiso permiso = permisoService.existePermiso(idPermiso);

            if(rol != null && permiso != null) {
                rol.getPermisos().remove(permiso);
                rolService.guardar(rol);
            } else {
                model.addAttribute("error", "Error. Rol: " + rol + ". Permiso: " + permiso);
                return "general-error";
            }

            return "redirect:/roles/" + idRol + "/permisos";
        }

        return RD_FALTA_PERMISO_VIEW;
    }*/

}
