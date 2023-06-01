package com.proyecto.is2.proyecto.controller;
import com.proyecto.is2.proyecto.controller.dto.ClienteDTO;
import com.proyecto.is2.proyecto.model.Cliente;
import com.proyecto.is2.proyecto.services.RolServiceImp;
import com.proyecto.is2.proyecto.services.TipoDocumentoService;
import com.proyecto.is2.proyecto.services.TipoDocumentoServiceImp;
import com.proyecto.is2.proyecto.services.ClienteServiceImp;
import com.proyecto.is2.proyecto.repository.ClienteRepository;
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
@RequestMapping("/clientes")
public class ClientesController {
    final String VIEW = "cliente"; // identificador de la vista
    final String VIEW_PATH = "cliente";
    String operacion = "";
    final String FORM_VIEW = VIEW_PATH + "/clientes";
    final String FORM_NEW = VIEW_PATH + "/nuevo";
    final String FORM_EDIT = VIEW_PATH + "/editar";
    final String RD_FORM_VIEW = "redirect:/clientes";
    final String FALTA_PERMISO_VIEW = "falta-permiso";
    final String RD_FALTA_PERMISO_VIEW = "redirect:/" + FALTA_PERMISO_VIEW;
    final String ASIGNAR_ROL_VIEW = VIEW_PATH + "/asignar-rol";
//    final String RD_ASIGNAR_ROL_VIEW = "redirect:/" + ASIGNAR_ROL_VIEW;
    final String P_ASIGNAR_ROL = "asignar-rol-usuario";

    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private ClienteServiceImp clienteService; // llamada a los servicios de usuario

    
    @Autowired
    private RolServiceImp rolService;//llamada a servicios de roles

    @Autowired
    private TipoDocumentoServiceImp tipoDocumentoService;//llamada a servicios de roles

    /**
     * Instancia un UsuarioDTO para rellenar con datos
     * del formulario para luego mapearlo a un objeto
     * Usuario.
     * @return
     */
    @ModelAttribute("cliente")
    public ClienteDTO instanciarObjetoDTO() {
        return new ClienteDTO();
    }

    @GetMapping
    public String mostrarGrilla(Model model) {

        boolean consultar = clienteService.tienePermiso("consultar-" + VIEW);
        boolean crear = clienteService.tienePermiso("crear-" + VIEW);
        boolean eliminar = clienteService.tienePermiso("eliminar-" + VIEW);
        boolean actualizar = clienteService.tienePermiso("actualizar-" + VIEW);

        //        if(!crear && !eliminar && !actualizar) {
        //            return FALTA_PERMISO_VIEW;
        //        }

        if(consultar) {
            model.addAttribute("listClientes", clienteService.listar());//lista los usuarios
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
        boolean crear = clienteService.tienePermiso("crear-" + VIEW);
        boolean asignarRol = clienteService.tienePermiso("asignar-rol-" + VIEW);

        /*if(asignarRol) {
            model.addAttribute("roles", rolService.listar());
        }
        model.addAttribute("permisoAsignarRol", asignarRol);*/

        //seteamos valores de documentos
        //if(true){
            model.addAttribute("tipoDoc", tipoDocumentoService.listar());
  
        System.out.println("HOLA MUNDO XDDD:" + tipoDocumentoService.listar());
        //}
        model.addAttribute("permisoAsignarDoc", true);


        if(crear) {
            return FORM_NEW;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @PostMapping("/crear")
    public String crearObjeto(@ModelAttribute("cliente") ClienteDTO objetoDTO,
                              RedirectAttributes attributes) {
        this.operacion = "crear-";

//        if (result.hasErrors()) {
//            return FORM_NEW;
//        }

        if(clienteService.tienePermiso(operacion + VIEW)) {
            Cliente cliente = new Cliente();
            clienteService.convertirDTO(cliente, objetoDTO);
            cliente.setTipoDocumento(tipoDocumentoService.existeTipoDocumento(objetoDTO.getDocumentType().longValue()));

            clienteService.guardar(cliente);

            attributes.addFlashAttribute("message", "¡Cliente creado exitosamente!");

            return RD_FORM_VIEW;
        } else {
            return RD_FALTA_PERMISO_VIEW;
            

        }
    }

    @GetMapping("/{id}")
    public String formEditar(@PathVariable String id, Model model) {
        boolean eliminar = clienteService.tienePermiso("eliminar-" + VIEW);
        boolean asignarRol = clienteService.tienePermiso("asignar-rol-" + VIEW);
        Cliente cliente;

        // validar el id
        try {
            Long idCliente = Long.parseLong(id);
            cliente = clienteService.existeCliente(idCliente);
        } catch(Exception e) {
            return RD_FORM_VIEW;
        }

        model.addAttribute("user", cliente);

        // validar si puede cambiar de rol
        if(asignarRol) {
            model.addAttribute("roles", rolService.listar());
        }
        model.addAttribute("permisoAsignarRol", asignarRol);
        
        //seteamos valores de documentos
        //if(true){
            model.addAttribute("tipoDocu", tipoDocumentoService.listar());
        //}
        model.addAttribute("permisoAsignarDoc", true);

        if(eliminar) {
            return FORM_EDIT;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @PostMapping("/{id}")
    public String actualizarObjeto(@PathVariable Long id, @ModelAttribute("cliente") ClienteDTO objetoDTO,
                                   BindingResult result, RedirectAttributes attributes) {
        this.operacion = "actualizar-";
        Cliente cliente;

        if (result.hasErrors()) {
//            studentDTO.setId(id);
            return RD_FORM_VIEW;
        }

        if(clienteService.tienePermiso(operacion + VIEW)) {
            cliente = clienteService.existeCliente(id);
            if(cliente != null) {
                clienteService.convertirDTO(cliente, objetoDTO);

                attributes.addFlashAttribute("message", "¡Usuario actualizado correctamente!");
                clienteService.guardar(cliente);
                return RD_FORM_VIEW;
            }
        }
        return RD_FALTA_PERMISO_VIEW;
    }

    @GetMapping("/{id}/delete")
        public String eliminarObjeto(@PathVariable String id, RedirectAttributes attributes  ) {
        this.operacion = "eliminar-";
        Long idCliente;

        try {
            idCliente = Long.parseLong(id);
        } catch (Exception e) {
            return RD_FORM_VIEW;
        }

        if(clienteService.tienePermiso(operacion + VIEW)) {
            Cliente cliente = clienteService.obtenerCliente(idCliente);
            clienteService.eliminarCliente(cliente);
            attributes.addFlashAttribute("message", "¡Cliente eliminado correctamente!");
            return RD_FORM_VIEW;
        } else {
            return RD_FALTA_PERMISO_VIEW;
        }
    }


}
