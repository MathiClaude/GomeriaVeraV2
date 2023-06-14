package com.proyecto.is2.proyecto.controller;
import com.proyecto.is2.proyecto.controller.dto.UsuarioDTO;
import com.proyecto.is2.proyecto.controller.dto.ServicioDTO;
import com.proyecto.is2.proyecto.model.Rol;
import com.proyecto.is2.proyecto.model.Usuario;
import com.proyecto.is2.proyecto.model.Servicio;
import com.proyecto.is2.proyecto.services.RolServiceImp;
import com.proyecto.is2.proyecto.services.ServicioServiceImp;
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
@RequestMapping("/servicios")
public class ServicioController {
        final String VIEW = "servicio"; // identificador de la vista
    final String VIEW_PATH = "servicio";
    String operacion = "";
    final String FORM_VIEW = VIEW_PATH + "/servicios";
    final String FORM_NEW = VIEW_PATH + "/nuevo";
    final String FORM_EDIT = VIEW_PATH + "/editar";
    final String RD_FORM_VIEW = "redirect:/servicios";
    final String FALTA_PERMISO_VIEW = "falta-permiso";
    final String RD_FALTA_PERMISO_VIEW = "redirect:/" + FALTA_PERMISO_VIEW;
    final String ASIGNAR_ROL_VIEW = VIEW_PATH + "/asignar-rol";
//    final String RD_ASIGNAR_ROL_VIEW = "redirect:/" + ASIGNAR_ROL_VIEW;
    final String P_ASIGNAR_ROL = "asignar-rol-usuario";
    
    @Autowired
    private ServicioServiceImp servicioService; // llamada a los servicios de servicio

    @Autowired
    private RolServiceImp rolService;//llamada a servicios de roles

    @Autowired
    private UsuarioServiceImp usuarioService;//llamada a servicios de roles

    /**
     * Instancia un UsuarioDTO para rellenar con datos
     * del formulario para luego mapearlo a un objeto
     * Usuario.
     * @return
     */
    @ModelAttribute("servicio")
    public ServicioDTO instanciarObjetoDTO() {
        return new ServicioDTO();
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
            model.addAttribute("listServicios", servicioService.listar());//lista los servicios
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

        if(crear) {
            return FORM_NEW;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @PostMapping("/crear")
    public String crearObjeto(@ModelAttribute("servicio") ServicioDTO objetoDTO,
                              RedirectAttributes attributes) {
        this.operacion = "crear-";

//        if (result.hasErrors()) {
//            return FORM_NEW;
//        }

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            Servicio servicio = new Servicio();
            servicioService.convertirDTO(servicio, objetoDTO);

            servicioService.guardar(servicio);

            attributes.addFlashAttribute("message", "¡Servicio creado exitosamente!");

            return RD_FORM_VIEW;
        } else {
            return RD_FALTA_PERMISO_VIEW;
            

        }
    }

    @GetMapping("/{id}")
    public String formEditar(@PathVariable String id, Model model) {
        boolean eliminar = usuarioService.tienePermiso("eliminar-" + VIEW);
        boolean asignarRol = usuarioService.tienePermiso("asignar-rol-" + VIEW);
        Servicio servicio;

        // validar el id
        try {
            Long idServicio = Long.parseLong(id);
            servicio = servicioService.existeServicio(idServicio);
        } catch(Exception e) {
            return RD_FORM_VIEW;
        }

        model.addAttribute("product", servicio);

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
    public String actualizarObjeto(@PathVariable Long id, @ModelAttribute("servicio") ServicioDTO objetoDTO,
                                   BindingResult result, RedirectAttributes attributes) {
        this.operacion = "actualizar-";
        Servicio servicio;

        if (result.hasErrors()) {
//            studentDTO.setId(id);
            return RD_FORM_VIEW;
        }

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            servicio = servicioService.existeServicio(id);
            if(servicio != null) {
                servicioService.convertirDTO(servicio, objetoDTO);

                // si tiene permiso se le asigna el rol con id del formulario
                // sino se queda con el mismo rol.

                //este tampoco creo que se usa
                /*if(servicioService.tienePermiso(P_ASIGNAR_ROL)) {
                    servicio.setRol(rolService.existeRol(objetoDTO.getIdRol().longValue()));
                }*/

                attributes.addFlashAttribute("message", "¡Servicio actualizado correctamente!");
                servicioService.guardar(servicio);
                return RD_FORM_VIEW;
            }
        }
        return RD_FALTA_PERMISO_VIEW;
    }

    @GetMapping("/{id}/delete")
        public String eliminarObjeto(@PathVariable String id, RedirectAttributes attributes  ) {
        this.operacion = "eliminar-";
        Long idServicio;

        try {
            idServicio = Long.parseLong(id);
        } catch (Exception e) {
            return RD_FORM_VIEW;
        }

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            Servicio servicio = servicioService.obtenerServicio(idServicio);
            servicioService.eliminarServicio(servicio);
            attributes.addFlashAttribute("message", "¡Servicio eliminado correctamente!");
            return RD_FORM_VIEW;
        } else {
            return RD_FALTA_PERMISO_VIEW;
        }
    }
}
