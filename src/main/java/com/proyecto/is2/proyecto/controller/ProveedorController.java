package com.proyecto.is2.proyecto.controller;
import com.proyecto.is2.proyecto.controller.dto.ProveedorDTO;
import com.proyecto.is2.proyecto.model.Proveedor;
import com.proyecto.is2.proyecto.model.Contacto;
import com.proyecto.is2.proyecto.services.RolServiceImp;
import com.proyecto.is2.proyecto.services.TipoDocumentoServiceImp;
import com.proyecto.is2.proyecto.services.ProveedorServiceImp;
import com.proyecto.is2.proyecto.repository.ContactoRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

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
    private TipoDocumentoServiceImp tipoDocumentoService; 

    @Autowired
    private RolServiceImp rolService;//llamada a proveedors de roles

    @Autowired
    ContactoRespository contactoRespository;

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

        model.addAttribute("tiposDocumentos", tipoDocumentoService.listar());

        
        if(crear) {
            return FORM_NEW;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @PostMapping("/crear")
    public String crearObjeto(@ModelAttribute("proveedor") ProveedorDTO objetoDTO,
                        @RequestParam(value = "c-nombres[]", required = false) String[] nombres,
                        @RequestParam(value = "c-telefonos[]", required = false) String[] telefonos,
                        @RequestParam(value = "c-correos[]", required = false) String[] correos,
                        RedirectAttributes attributes, Model model, BindingResult result) {
                        this.operacion = "crear-";

        // if (result.hasErrors()) {
        //     return FORM_NEW;
        // }

        if(proveedorService.tienePermiso(operacion + VIEW)) {
            Proveedor proveedor = new Proveedor();
            List<Contacto> contactos = null;
            proveedorService.convertirDTO(proveedor, objetoDTO);
            contactos = proveedor.getContactos();

            if(contactos == null) {
                // si no tiene contactos
                contactos = new ArrayList<>();
            } else if(nombres == null) {
                contactoRespository.deleteAll(contactos);
                contactos.clear();
            } else {
                List<Contacto> newList = new ArrayList<>();
                // contactos eliminados o modificados se eliminan
                for(int k=0; k < contactos.size(); k++) {
                    boolean flat = false;
                    for(int i=0; i < nombres.length; i++) {
                        if(contactos.get(k).getNombre().equals(nombres[i]) &&
                                contactos.get(k).getCorreo().equals(correos[i]) &&
                                contactos.get(k).getTelefono().equals(telefonos[i])) {
                            newList.add(contactos.get(k));
                            flat = true;
                            break;
                        }
                    }
                    if(!flat) {
                        contactoRespository.delete(contactos.get(k));
                        contactos.remove(contactos.get(k));
                    }
                }
                // elementos agregados o no modificados se agregan a la nueva lista
                for(int i=0; i < nombres.length; i++) {
                    boolean flat = false;
                    for(int k=0; k < contactos.size(); k++) {
                        if(nombres[i].equals(contactos.get(k).getNombre()) &&
                                telefonos[i].equals(contactos.get(k).getTelefono()) &&
                                correos[i].equals(contactos.get(k).getCorreo())) {
                            newList.add(contactos.get(k));
                            flat = true;
                            break;
                        }
                    }
                    if(!flat) {
                        Contacto tmpContacto = new Contacto();
                        tmpContacto.setNombre(nombres[i]);
                        tmpContacto.setTelefono(telefonos[i]);
                        tmpContacto.setCorreo(correos[i]);
                        tmpContacto.setProveedor(proveedor);
                        newList.add(contactoRespository.save(tmpContacto));
                    }
                }
                contactos = newList;
            }

            proveedor.setContactos(contactos);

            proveedorService.guardar(proveedor);

            attributes.addFlashAttribute("message", "¡Proveedor creado exitosamente!");

            return RD_FORM_VIEW;
        } else {
            return RD_FALTA_PERMISO_VIEW;
            

        }
    }

    /*@Transactional
    @PostMapping("/nuevo")
    public String crearObjeto(@ModelAttribute(value = ModelAttributes.PROVEEDOR) ProveedorDTO dto,
                              @RequestParam(value = "c-nombres[]", required = false) String[] nombres,
                              @RequestParam(value = "c-telefonos[]", required = false) String[] telefonos,
                              @RequestParam(value = "c-correos[]", required = false) String[] correos,
                              RedirectAttributes attributes, Model model, BindingResult result) {
        if (result.hasErrors()) {
            return DATA_CREATE_URL;
        }

        boolean privillege = usuarioService.tienePermiso(Permisos.WRITE_ADMINISTRATION_PRIVILEGE.name);

        if(privillege) {
            try {
                Proveedor proveedor = proveedorService.obtenerInstancia(dto.getIdProveedor());
                Persona persona = personaService.obtenerInstancia(dto.getPersona().getIdPersona());
                List<Contacto> contactos = null;

                if(dto.getIdProveedor() == null && persona != null) {
                    throw new Exception("No se puede crear la persona, porque ya existe.");
                } else if(dto.getIdProveedor() == null && persona == null) {
                    persona = new Persona();
                    proveedor = new Proveedor();
                } else if(dto.getIdProveedor() != null && persona == null) {
                    throw new Exception("No se ha podido encontrar la persona asociada al proveedor.");
                }

                proveedor.setPersona(persona);
                proveedorService.convertirDTO(proveedor, dto);
                persona = personaService.guardar(proveedor.getPersona());
                proveedor.setPersona(persona);
                contactos = proveedor.getContactos();

                if(contactos == null) {
                    // si no tiene contactos
                    contactos = new ArrayList<>();
                } else if(nombres == null) {
                    contactoRespository.deleteAll(contactos);
                    contactos.clear();
                } else {
                    List<Contacto> newList = new ArrayList<>();
                    // contactos eliminados o modificados se eliminan
                    for(int k=0; k < contactos.size(); k++) {
                        boolean flat = false;
                        for(int i=0; i < nombres.length; i++) {
                            if(contactos.get(k).getNombre().equals(nombres[i]) &&
                                    contactos.get(k).getCorreo().equals(correos[i]) &&
                                    contactos.get(k).getTelefono().equals(telefonos[i])) {
                                newList.add(contactos.get(k));
                                flat = true;
                                break;
                            }
                        }
                        if(!flat) {
                            contactoRespository.delete(contactos.get(k));
                            contactos.remove(contactos.get(k));
                        }
                    }
                    // elementos agregados o no modificados se agregan a la nueva lista
                    for(int i=0; i < nombres.length; i++) {
                        boolean flat = false;
                        for(int k=0; k < contactos.size(); k++) {
                            if(nombres[i].equals(contactos.get(k).getNombre()) &&
                                    telefonos[i].equals(contactos.get(k).getTelefono()) &&
                                    correos[i].equals(contactos.get(k).getCorreo())) {
                                newList.add(contactos.get(k));
                                flat = true;
                                break;
                            }
                        }
                        if(!flat) {
                            Contacto tmpContacto = new Contacto();
                            tmpContacto.setNombre(nombres[i]);
                            tmpContacto.setTelefono(telefonos[i]);
                            tmpContacto.setCorreo(correos[i]);
                            tmpContacto.setProveedor(proveedor);
                            newList.add(contactoRespository.save(tmpContacto));
                        }
                    }
                    contactos = newList;
                }

                proveedor.setContactos(contactos);
                proveedorService.guardar(proveedor);

                if(dto.getIdProveedor() == null) {
                    attributes.addFlashAttribute(ModelAttributes.ALERT_MESSAGE, "¡Proveedor creado correctamente!");
                } else {
                    attributes.addFlashAttribute(ModelAttributes.ALERT_MESSAGE, "¡Proveedor actualizado correctamente!");
                }

                attributes.addFlashAttribute(ModelAttributes.ALERT_TYPE, ModelAttributes.ALERT_SUCCESS);
            } catch (AuthorizationServiceException e) {
//                attributes.addFlashAttribute(ModelAttributes.ALERT_MESSAGE, e.getMessage());
//                attributes.addFlashAttribute(ModelAttributes.ALERT_TYPE, ModelAttributes.ALERT_NOTIFICATION);
                model.addAttribute(ModelAttributes.ERROR_CODE, ModelAttributes.CODE_PRIVILLEGE);
                model.addAttribute(ModelAttributes.ERROR_MSG, ModelAttributes.MSG_403);
                model.addAttribute(ModelAttributes.ERROR_TITLE, ModelAttributes.TITLE_403);
                return ERROR_VIEW;
            } catch (Exception e) {
//                attributes.addFlashAttribute(ModelAttributes.ALERT_MESSAGE, e.getMessage());
//                attributes.addFlashAttribute(ModelAttributes.ALERT_TYPE, ModelAttributes.ALERT_ERROR);
                model.addAttribute(ModelAttributes.ERROR_CODE, ModelAttributes.ERROR_GENERIC);
                model.addAttribute(ModelAttributes.ERROR_MSG, "");
                model.addAttribute(ModelAttributes.ERROR_TITLE, e.getMessage());
                return ERROR_VIEW;
            }
        } else {
//            attributes.addFlashAttribute(ModelAttributes.ALERT_MESSAGE, ModelAttributes.TITLE_403);
//            attributes.addFlashAttribute(ModelAttributes.ALERT_TYPE, ModelAttributes.ALERT_NOTIFICATION);
            model.addAttribute(ModelAttributes.ERROR_CODE, ModelAttributes.CODE_PRIVILLEGE);
            model.addAttribute(ModelAttributes.ERROR_MSG, ModelAttributes.MSG_403);
            model.addAttribute(ModelAttributes.ERROR_TITLE, ModelAttributes.TITLE_403);
            return ERROR_VIEW;
        }
        return REDIRECT_VIEW + PROVEERS_URL;
    }*/


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
            //studentDTO.setId(id);
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
