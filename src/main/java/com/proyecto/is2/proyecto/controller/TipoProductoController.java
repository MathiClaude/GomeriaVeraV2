package com.proyecto.is2.proyecto.controller;
import com.proyecto.is2.proyecto.controller.dto.TipoProductoDTO;
import com.proyecto.is2.proyecto.controller.dto.UsuarioDTO;
import com.proyecto.is2.proyecto.controller.dto.ServicioDTO;
import com.proyecto.is2.proyecto.model.TipoProducto;
import com.proyecto.is2.proyecto.model.Usuario;
import com.proyecto.is2.proyecto.model.Servicio;
import com.proyecto.is2.proyecto.services.TipoProductoServiceImp;
import com.proyecto.is2.proyecto.services.UsuarioServiceImp;
import com.proyecto.is2.proyecto.services.ServicioServiceImp;
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
@RequestMapping("/tipoProductos")
public class TipoProductoController {
    final String VIEW = "tipoProducto"; // identificador de la vista
    final String VIEW_PATH = "tipoProducto";
    String operacion = "";
    final String FORM_VIEW = VIEW_PATH + "/tipoProductos";
    final String FORM_NEW = VIEW_PATH + "/nuevo";
    final String FORM_EDIT = VIEW_PATH + "/editar";
    final String RD_FORM_VIEW = "redirect:/tipoProductos";
    final String FALTA_PERMISO_VIEW = "falta-permiso";
    final String RD_FALTA_PERMISO_VIEW = "redirect:/" + FALTA_PERMISO_VIEW;
    //final String ASIGNAR_ROL_VIEW = VIEW_PATH + "/asignar-rol";
//    final String RD_ASIGNAR_ROL_VIEW = "redirect:/" + ASIGNAR_ROL_VIEW;
    final String P_ASIGNAR_ROL = "asignar-rol-usuario";
    
    @Autowired
    private ServicioServiceImp servicioService; // llamada a los servicios de servicio

    @Autowired
    private UsuarioServiceImp usuarioService; // llamada a los servicios de usuario

    @Autowired
    private TipoProductoServiceImp tipoProductoService;//llamada a servicios de roles

    /**
     * Instancia un UsuarioDTO para rellenar con datos
     * del formulario para luego mapearlo a un objeto
     * Usuario.
     * @return
     */
    @ModelAttribute("tipoProducto")
    public TipoProductoDTO instanciarObjetoDTO() {
        return new TipoProductoDTO();
    }

    @GetMapping
    public String mostrarGrilla(Model model) {

        boolean consultar = tipoProductoService.tienePermiso("consultar-" + VIEW);
        boolean crear = tipoProductoService.tienePermiso("crear-" + VIEW);
        boolean eliminar = tipoProductoService.tienePermiso("eliminar-" + VIEW);
        boolean actualizar = tipoProductoService.tienePermiso("actualizar-" + VIEW);

        //        if(!crear && !eliminar && !actualizar) {
        //            return FALTA_PERMISO_VIEW;
        //        }

        if(consultar) {
            model.addAttribute("listTipDoc", tipoProductoService.listar());//lista los servicios
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
        boolean crear = tipoProductoService.tienePermiso("crear-" + VIEW);
        boolean asignarRol = tipoProductoService.tienePermiso("asignar-rol-" + VIEW);

        if(asignarRol) {
            model.addAttribute("tipoProductos", tipoProductoService.listar());
        }
        model.addAttribute("permisoAsignarRol", asignarRol);

        if(crear) {
            return FORM_NEW;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @PostMapping("/crear")
    public String crearObjeto(@ModelAttribute("tipoProducto") TipoProductoDTO objetoDTO,
                              RedirectAttributes attributes) {
        this.operacion = "crear-";

//        if (result.hasErrors()) {
//            return FORM_NEW;
//        }

        if(tipoProductoService.tienePermiso(operacion + VIEW)) {
            TipoProducto tipoProducto = new TipoProducto();
            tipoProductoService.convertirDTO(tipoProducto, objetoDTO);

            tipoProductoService.guardar(tipoProducto);

            attributes.addFlashAttribute("message", "¡Tipo producto creado exitosamente!");

            return RD_FORM_VIEW;
        } else {
            return RD_FALTA_PERMISO_VIEW;
            

        }
    }

    @GetMapping("/{id}")
    public String formEditar(@PathVariable String id, Model model) {
        boolean eliminar = tipoProductoService.tienePermiso("eliminar-" + VIEW);
        boolean asignarRol = tipoProductoService.tienePermiso("asignar-rol-" + VIEW);
        TipoProducto tipoProducto;

        // validar el id
        try {
            Long idTipoProducto = Long.parseLong(id);
            tipoProducto = tipoProductoService.existeTipoProducto(idTipoProducto);
        } catch(Exception e) {
            return RD_FORM_VIEW;
        }

        model.addAttribute("tipProduc", tipoProducto);

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
    public String actualizarObjeto(@PathVariable Long id, @ModelAttribute("tipoProducto") TipoProductoDTO objetoDTO,
                                   BindingResult result, RedirectAttributes attributes) {
        this.operacion = "actualizar-";
        TipoProducto tipoProducto;

        if (result.hasErrors()) {
//            studentDTO.setId(id);
            return RD_FORM_VIEW;
        }

        if(tipoProductoService.tienePermiso(operacion + VIEW)) {
            tipoProducto = tipoProductoService.existeTipoProducto(id);
            if(tipoProducto != null) {
                tipoProductoService.convertirDTO(tipoProducto, objetoDTO);

                // si tiene permiso se le asigna el rol con id del formulario
                // sino se queda con el mismo rol.

                //este tampoco creo que se usa
                /*if(servicioService.tienePermiso(P_ASIGNAR_ROL)) {
                    servicio.setRol(rolService.existeRol(objetoDTO.getIdRol().longValue()));
                }*/

                attributes.addFlashAttribute("message", "¡Tipo producto actualizada correctamente!");
                tipoProductoService.guardar(tipoProducto);
                return RD_FORM_VIEW;
            }
        }
        return RD_FALTA_PERMISO_VIEW;
    }

    @GetMapping("/{id}/delete")
        public String eliminarObjeto(@PathVariable String id, RedirectAttributes attributes  ) {
        this.operacion = "eliminar-";
        Long idTipoProducto;

        try {
            idTipoProducto = Long.parseLong(id);
        } catch (Exception e) {
            return RD_FORM_VIEW;
        }

        if(tipoProductoService.tienePermiso(operacion + VIEW)) {
            TipoProducto tipoProducto = tipoProductoService.obtenerTipoProducto(idTipoProducto);
            tipoProductoService.eliminarTipoProducto(tipoProducto);
            attributes.addFlashAttribute("message", "¡Servicio eliminado correctamente!");
            return RD_FORM_VIEW;
        } else {
            return RD_FALTA_PERMISO_VIEW;
        }
    }

//    @GetMapping("asignar-rol")
//    public String asignarRolUsuario() {
//        this.operacion = "asignar-rol-";
//
//        if(usuarioService.tienePermiso(operacion + VIEW)) {
//            if(usuarioService.tienePermiso(operacion + VIEW)) {
//                return ASIGNAR_ROL_VIEW;
//            }
//        }
//        return FALTA_PERMISO_VIEW;
//    }

//    @PostMapping("asignar-rol")
//    public String asignarRolUsuarioForm(@RequestParam("id_rol") Long idRol, @RequestParam("username") String email) {
//        this.operacion = "asignar-rol-";
//
//        if(usuarioService.tienePermiso(operacion + VIEW)) {
//            Usuario usuario = usuarioService.existeUsuario(email);
//            Rol rol = rolService.existeRol(idRol);
//
//            if(usuario != null && rol != null) {
//                usuario.setRol(rol);
//                usuarioService.guardar(usuario);
//                return RD_ASIGNAR_ROL_VIEW;
//            }
//        }
//        return RD_FALTA_PERMISO_VIEW;
//    }

}
