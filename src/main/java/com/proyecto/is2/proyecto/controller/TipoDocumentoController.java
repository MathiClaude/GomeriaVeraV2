package com.proyecto.is2.proyecto.controller;
import com.proyecto.is2.proyecto.model.Rol;
import com.proyecto.is2.proyecto.services.RolServiceImp;
import com.proyecto.is2.proyecto.model.TipoDocumento;
import com.proyecto.is2.proyecto.services.TipoDocumentoServiceImp;
import com.proyecto.is2.proyecto.services.UsuarioServiceImp;
import com.proyecto.is2.proyecto.controller.dto.TipoDocumentoDTO;
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
@RequestMapping("/tipoDocumentos")
public class TipoDocumentoController {
    final String VIEW = "tipoDoc"; // identificador de la vista
    final String VIEW_PATH = "tipoDocumento";
    String operacion = "";
    final String FORM_VIEW = VIEW_PATH + "/tipoDocumentos";
    final String FORM_NEW = VIEW_PATH + "/nuevo";
    final String FORM_EDIT = VIEW_PATH + "/editar";
    final String RD_FORM_VIEW = "redirect:/tipoDocumentos";
    final String FALTA_PERMISO_VIEW = "falta-permiso";
    final String RD_FALTA_PERMISO_VIEW = "redirect:/" + FALTA_PERMISO_VIEW;
    final String ASIGNAR_ROL_VIEW = VIEW_PATH + "/asignar-rol";
//    final String RD_ASIGNAR_ROL_VIEW = "redirect:/" + ASIGNAR_ROL_VIEW;
    final String P_ASIGNAR_ROL = "asignar-rol-usuario";

    @Autowired
    private TipoDocumentoServiceImp tipoDocumentoService; // llamada a los servicios de usuario

    @Autowired
    private UsuarioServiceImp usuarioService;
    
    @Autowired
    private RolServiceImp rolService;//llamada a servicios de roles

    /**
     * Instancia un UsuarioDTO para rellenar con datos
     * del formulario para luego mapearlo a un objeto
     * Usuario.
     * @return
     */
    @ModelAttribute("tipoDocumento")
    public TipoDocumentoDTO instanciarObjetoDTO() {
        return new TipoDocumentoDTO();
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
            model.addAttribute("listTipDoc", tipoDocumentoService.listar());//lista los usuarios
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
        boolean crear = tipoDocumentoService.tienePermiso("crear-" + VIEW);
        boolean asignarRol = tipoDocumentoService.tienePermiso("asignar-rol-" + VIEW);

        if(asignarRol) {
            model.addAttribute("roles", rolService.listar());
        }
        model.addAttribute("permisoAsignarRol", asignarRol);

        if(crear) {
            return FORM_NEW;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @PostMapping("/crear")
    public String crearObjeto(@ModelAttribute("tipoDocumento") TipoDocumentoDTO objetoDTO,
                              RedirectAttributes attributes) {
        this.operacion = "crear-";

//        if (result.hasErrors()) {
//            return FORM_NEW;
//        }

        if(tipoDocumentoService.tienePermiso(operacion + VIEW)) {
            TipoDocumento tipoDocumento = new TipoDocumento();
            tipoDocumentoService.convertirDTO(tipoDocumento, objetoDTO);

            // si tiene permiso se le asigna el rol con id del formulario
            // sino se le asignar un rol por defecto.
            /*if(tipoDocumentoService.tienePermiso(P_ASIGNAR_ROL)) {
                tipoDocumento.setRol(rolService.existeRol(objetoDTO.getIdRol().longValue()));
            } else {
                tipoDocumento.setRol(rolService.existeRol(1L)); // ID 1: Rol sin permisos.
            }*/

            tipoDocumentoService.guardar(tipoDocumento);

            attributes.addFlashAttribute("message", "¡Tipo de Documento creado exitosamente!");

            return RD_FORM_VIEW;
        } else {
            return RD_FALTA_PERMISO_VIEW;
        }
    }

    @GetMapping("/{id}")
    public String formEditar(@PathVariable String id, Model model) {
        boolean eliminar = tipoDocumentoService.tienePermiso("eliminar-" + VIEW);
        boolean asignarRol = tipoDocumentoService.tienePermiso("asignar-rol-" + VIEW);
        TipoDocumento tipoDocumento;

        // validar el id
        try {
            Long idTipoDocumento = Long.parseLong(id);
            tipoDocumento = tipoDocumentoService.existeTipoDocumento(idTipoDocumento);
        } catch(Exception e) {
            return RD_FORM_VIEW;
        }

        model.addAttribute("tipoDocumento", tipoDocumento);

        // validar si puede cambiar de rol
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
    public String actualizarObjeto(@PathVariable Long id, @ModelAttribute("tipoDocumento") TipoDocumentoDTO objetoDTO,
                                   BindingResult result, RedirectAttributes attributes) {
        this.operacion = "actualizar-";
        TipoDocumento tipoDocumento;

        if (result.hasErrors()) {
//            studentDTO.setId(id);
            return RD_FORM_VIEW;
        }

        if(tipoDocumentoService.tienePermiso(operacion + VIEW)) {
            tipoDocumento = tipoDocumentoService.existeTipoDocumento(id);
            if(tipoDocumento != null) {
                tipoDocumentoService.convertirDTO(tipoDocumento, objetoDTO);

                // si tiene permiso se le asigna el rol con id del formulario
                // sino se queda con el mismo rol.
                /*if(tipoDocumentoService.tienePermiso(P_ASIGNAR_ROL)) {
                    tipoDocumento.setRol(rolService.existeRol(objetoDTO.getIdTipoDocumento().longValue()));
                }*/

                attributes.addFlashAttribute("message", "¡Tipo de Documento actualizado correctamente!");
                tipoDocumentoService.guardar(tipoDocumento);
                return RD_FORM_VIEW;
            }
        }
        return RD_FALTA_PERMISO_VIEW;
    }

    @GetMapping("/{id}/delete")
        public String eliminarObjeto(@PathVariable String id, RedirectAttributes attributes  ) {
        this.operacion = "eliminar-";
        Long idTipoDocumento;

        try {
            idTipoDocumento = Long.parseLong(id);
        } catch (Exception e) {
            return RD_FORM_VIEW;
        }

        if(tipoDocumentoService.tienePermiso(operacion + VIEW)) {
            TipoDocumento tipoDocumento = tipoDocumentoService.obtenerTipoDocumento(idTipoDocumento);
            tipoDocumentoService.eliminarTipoDocumento(tipoDocumento);
            attributes.addFlashAttribute("message", "Tipo de Documento eliminado correctamente!");
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
