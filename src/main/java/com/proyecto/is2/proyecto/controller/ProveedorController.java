package com.proyecto.is2.proyecto.controller;
import com.proyecto.is2.proyecto.controller.dto.ProveedorDTO;
import com.proyecto.is2.proyecto.model.Proveedor;
import com.proyecto.is2.proyecto.services.RolServiceImp;
import com.proyecto.is2.proyecto.services.ProveedorServiceImp;
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
@RequestMapping("/proveedores")
public class ProveedorController {
    final String VIEW = "proveedor"; // identificador de la vista
    final String VIEW_PATH = "proveedor";
    String operacion = "";
    final String FORM_VIEW = VIEW_PATH + "/proveedores";
    final String FORM_NEW = VIEW_PATH + "/nuevo";
    final String FORM_EDIT = VIEW_PATH + "/editar";
    final String RD_FORM_VIEW = "redirect:/proveedores";
    final String FALTA_PERMISO_VIEW = "falta-permiso";
    final String RD_FALTA_PERMISO_VIEW = "redirect:/" + FALTA_PERMISO_VIEW;
    final String ASIGNAR_ROL_VIEW = VIEW_PATH + "/asignar-rol";
//    final String RD_ASIGNAR_ROL_VIEW = "redirect:/" + ASIGNAR_ROL_VIEW;
    final String P_ASIGNAR_ROL = "asignar-rol-usuario";

    @Autowired
    private ProveedorServiceImp proveedorService; // llamada a los proveedors de proveedor
    
    @Autowired
    private RolServiceImp rolService;//llamada a proveedors de roles

    /**
     * Instancia un UsuarioDTO para rellenar con datos
     * del formulario para luego mapearlo a un objeto
     * Usuario.
     * @return
     */
    @ModelAttribute("proveedor")
    public ProveedorDTO instanciarObjetoDTO() {
        return new ProveedorDTO();
    }

    @GetMapping
    public String mostrarGrilla(Model model) {

        boolean consultar = proveedorService.tienePermiso("consultar-" + VIEW);
        boolean crear = proveedorService.tienePermiso("crear-" + VIEW);
        boolean eliminar = proveedorService.tienePermiso("eliminar-" + VIEW);
        boolean actualizar = proveedorService.tienePermiso("actualizar-" + VIEW);

        //        if(!crear && !eliminar && !actualizar) {
        //            return FALTA_PERMISO_VIEW;
        //        }

        if(consultar) {
            model.addAttribute("listProveedor", proveedorService.listar());//lista los proveedors
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
        boolean crear = proveedorService.tienePermiso("crear-" + VIEW);
        boolean asignarRol = proveedorService.tienePermiso("asignar-rol-" + VIEW);

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
    public String crearObjeto(@ModelAttribute("proveedor") ProveedorDTO objetoDTO,
                              RedirectAttributes attributes) {
        this.operacion = "crear-";

//        if (result.hasErrors()) {
//            return FORM_NEW;
//        }

        if(proveedorService.tienePermiso(operacion + VIEW)) {
            Proveedor proveedor = new Proveedor();
            proveedorService.convertirDTO(proveedor, objetoDTO);

            proveedorService.guardar(proveedor);

            attributes.addFlashAttribute("message", "¡Proveedor creado exitosamente!");

            return RD_FORM_VIEW;
        } else {
            return RD_FALTA_PERMISO_VIEW;
            

        }
    }

    @GetMapping("/{id}")
    public String formEditar(@PathVariable String id, Model model) {
        boolean eliminar = proveedorService.tienePermiso("eliminar-" + VIEW);
        boolean asignarRol = proveedorService.tienePermiso("asignar-rol-" + VIEW);
        Proveedor proveedor;

        // validar el id
        try {
            Long idProveedor = Long.parseLong(id);
            proveedor = proveedorService.existeProveedor(idProveedor);
        } catch(Exception e) {
            return RD_FORM_VIEW;
        }

        model.addAttribute("proveedor", proveedor);


        if(eliminar) {
            return FORM_EDIT;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @PostMapping("/{id}")
    public String actualizarObjeto(@PathVariable Long id, @ModelAttribute("proveedor") ProveedorDTO objetoDTO,
                                   BindingResult result, RedirectAttributes attributes) {
        this.operacion = "actualizar-";
        Proveedor proveedor;

        if (result.hasErrors()) {
//            studentDTO.setId(id);
            return RD_FORM_VIEW;
        }

        if(proveedorService.tienePermiso(operacion + VIEW)) {
            proveedor = proveedorService.existeProveedor(id);
            if(proveedor != null) {
                proveedorService.convertirDTO(proveedor, objetoDTO);

                attributes.addFlashAttribute("message", "¡Proveedor actualizado correctamente!");
                proveedorService.guardar(proveedor);
                return RD_FORM_VIEW;
            }
        }
        return RD_FALTA_PERMISO_VIEW;
    }

    @GetMapping("/{id}/delete")
        public String eliminarObjeto(@PathVariable String id, RedirectAttributes attributes  ) {
        this.operacion = "eliminar-";
        Long idProveedor;

        try {
            idProveedor = Long.parseLong(id);
        } catch (Exception e) {
            return RD_FORM_VIEW;
        }

        if(proveedorService.tienePermiso(operacion + VIEW)) {
            Proveedor proveedor = proveedorService.obtenerProveedor(idProveedor);
            proveedorService.eliminarProveedor(proveedor);
            attributes.addFlashAttribute("message", "¡Proveedor eliminado correctamente!");
            return RD_FORM_VIEW;
        } else {
            return RD_FALTA_PERMISO_VIEW;
        }
    }

}
