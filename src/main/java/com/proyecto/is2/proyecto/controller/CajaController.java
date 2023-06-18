package com.proyecto.is2.proyecto.controller;
import com.proyecto.is2.proyecto.controller.dto.ProveedorDTO;
import com.proyecto.is2.proyecto.controller.dto.CajaDTO;
import com.proyecto.is2.proyecto.controller.dto.AperturaCajaDTO;
import com.proyecto.is2.proyecto.model.Proveedor;
import com.proyecto.is2.proyecto.model.Contacto;
import com.proyecto.is2.proyecto.model.Caja;
import com.proyecto.is2.proyecto.model.AperturaCaja;
import com.proyecto.is2.proyecto.model.Usuario;
import com.proyecto.is2.proyecto.services.RolServiceImp;
import com.proyecto.is2.proyecto.services.TipoDocumentoServiceImp;
import com.proyecto.is2.proyecto.services.UsuarioServiceImp;
import com.proyecto.is2.proyecto.services.ProveedorServiceImp;
import com.proyecto.is2.proyecto.services.CajaServiceImp;
import com.proyecto.is2.proyecto.services.AperturaCajaServiceImp;
import com.proyecto.is2.proyecto.repository.ContactoRespository;
import com.proyecto.is2.proyecto.repository.AperturaCajaRepository;
import com.proyecto.is2.proyecto.repository.CajaRepository;
import com.proyecto.is2.proyecto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
// import java.security.Principal;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Controlador encargado de recibir las peticiones
 * para realizar venta
 */
@Controller
@RequestMapping("/aperturaCaja")
public class CajaController {
    final String VIEW = "caja"; // identificador de la vista
    final String VIEW_PATH = "caja";
    String operacion = "";
    final String FORM_VIEW = VIEW_PATH + "/aperturaCaja";
    final String FORM_NEW = VIEW_PATH + "/nuevo";
    final String FORM_EDIT = VIEW_PATH + "/editar";
    final String FORM_VIEW_CIERRE = VIEW_PATH + "/cierreCaja";
    final String RD_FORM_VIEW = "redirect:/aperturaCaja";
    final String FALTA_PERMISO_VIEW = "falta-permiso";
    final String RD_FALTA_PERMISO_VIEW = "redirect:/" + FALTA_PERMISO_VIEW;
    final String ASIGNAR_ROL_VIEW = VIEW_PATH + "/asignar-rol";
//    final String RD_ASIGNAR_ROL_VIEW = "redirect:/" + ASIGNAR_ROL_VIEW;
    final String P_ASIGNAR_ROL = "asignar-rol-usuario";

    @Autowired
    private CajaServiceImp cajaService; // llamada a los proveedors de proveedor
    
    @Autowired
    private UsuarioServiceImp usuarioService;

    @Autowired
    private RolServiceImp rolService;//llamada a proveedors de roles

    @Autowired
    private AperturaCajaServiceImp aperturaCajaService;//llamada a proveedors de Apertura caja

    @Autowired
    ContactoRespository contactoRespository;
    
    @Autowired
    UsuarioRepository usuarioRespository;

    @Autowired
    AperturaCajaRepository aperturaCajaRepository;

    @Autowired
    CajaRepository cajaRepository;

    /**
     * Instancia un UsuarioDTO para rellenar con datos
     * del formulario para luego mapearlo a un objeto
     * Usuario.
     * @return
     */
    @ModelAttribute("caja")
    public CajaDTO instanciarObjetoDTO() {
        return new CajaDTO();
    }

    @GetMapping
    public String mostrarGrilla(Model model) {

        boolean consultar = usuarioService.tienePermiso("consultar-" + VIEW);
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);
        boolean eliminar = usuarioService.tienePermiso("eliminar-" + VIEW);
        boolean actualizar = usuarioService.tienePermiso("actualizar-" + VIEW);

        if(consultar) {
            model.addAttribute("listCaja", cajaService.listar());//lista los cajas
            String username = SecurityContextHolder.getContext().getAuthentication().getName(); //Obtener datos del usuario logueado[Basico]
            Usuario usuario = usuarioRespository.findByEmail(username);// Obtener todos los datos del usuario 
            
        } else {
            return FALTA_PERMISO_VIEW;
        }

        model.addAttribute("permisoVer", consultar);
        model.addAttribute("permisoCrear", crear);
        model.addAttribute("permisoEliminar", eliminar);
        model.addAttribute("permisoActualizar", actualizar);

        return FORM_VIEW;
    }

@GetMapping("/cierre")
    public String mostrarGrillaCierre(Model model) {

        boolean consultar = usuarioService.tienePermiso("consultar-" + VIEW);
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);
        boolean eliminar = usuarioService.tienePermiso("eliminar-" + VIEW);
        boolean actualizar = usuarioService.tienePermiso("actualizar-" + VIEW);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRespository.findByEmail(username);// Obtener todos los datos del usuario 
        model.addAttribute("nombreCajero", usuario.getUsername());//Nombre del cajero

        List<AperturaCaja> cajaApertura = aperturaCajaRepository.findByIdUsuarioOrderByIdAperturaCajaDesc(usuario.getIdUsuario());


        if(consultar) {

            model.addAttribute("listCaja", cajaService.listar());//lista los cajas

            
            model.addAttribute("usuario", usuario.getUsername());//Datos de usuario
            if(cajaApertura.size()>0){
                if((cajaApertura.get(0).getEstado().toUpperCase().equals("ABRIDO"))){
                    
                    model.addAttribute("aperturaCaja","true"); // CAJA ESTA ABIERTA 
                    
                    Caja cajaActual = cajaRepository.findByIdCaja(cajaApertura.get(0).getIdCaja());
                    model.addAttribute("cajaActual", cajaActual.getDescripcion());//lista las cajas
                    //model.addAttribute("saldoCierre", cajaActual.getSaldoCierre());//lista los productos
                    model.addAttribute("fechaCierre", cajaApertura.get(0).getFechaCierre() );//lista los productos
                }
            }    

        } else {
            return FALTA_PERMISO_VIEW;
        }

        model.addAttribute("permisoVer", consultar);
        model.addAttribute("permisoCrear", crear);
        model.addAttribute("permisoEliminar", eliminar);
        model.addAttribute("permisoActualizar", actualizar);

        return FORM_VIEW_CIERRE;
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
    public String crearObjeto(@ModelAttribute("caja") CajaDTO objetoDTO,
                        RedirectAttributes attributes, Model model, BindingResult result) {
                        this.operacion = "crear-";

        // if (result.hasErrors()) {
        //     return FORM_NEW;
        // }

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            Caja caja = new Caja();
            cajaService.convertirDTO(caja, objetoDTO);

            cajaService.guardar(caja);

            attributes.addFlashAttribute("message", "¡Caja creada exitosamente!");

            return RD_FORM_VIEW;
        } else {
            return RD_FALTA_PERMISO_VIEW;
            

        }
    }

    @PostMapping("/abrir")
    public String crearApertura(@ModelAttribute("AperturaCaja") AperturaCajaDTO objetoDTO,
                        RedirectAttributes attributes, Model model, BindingResult result) {
                        this.operacion = "crear-";

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Usuario usuario = usuarioRespository.findByEmail(username);

            AperturaCaja aperturaCaja = new AperturaCaja();

            // String idCaja = objetoDTO.getIdCaja();
            objetoDTO.setFechaApertura(java.time.LocalDate.now().toString());
            objetoDTO.setIdUsuario(usuario.getIdUsuario());
            aperturaCajaService.convertirDTO(aperturaCaja, objetoDTO);

            aperturaCaja.setIdCaja(objetoDTO.getIdCaja());
            aperturaCaja.setIdUsuario(usuario.getIdUsuario());
            aperturaCaja.setEstado("aBrIdO");

            aperturaCajaService.guardar(aperturaCaja);

            attributes.addFlashAttribute("msgCaja", "Caja abrido exitosamente !");
            

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
        boolean eliminar = usuarioService.tienePermiso("eliminar-" + VIEW);
        boolean asignarRol = usuarioService.tienePermiso("asignar-rol-" + VIEW);
        Caja caja;

        // validar el id
        try {
            Long idCaja = Long.parseLong(id);
            caja = cajaService.existeCaja(idCaja);
        } catch(Exception e) {
            return RD_FORM_VIEW;
        }

        model.addAttribute("caja", caja);


        if(eliminar) {
            return FORM_EDIT;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @PostMapping("/{id}")
    public String actualizarObjeto(@PathVariable Long id, @ModelAttribute("caja") CajaDTO objetoDTO,
                                   BindingResult result, RedirectAttributes attributes) {
        this.operacion = "actualizar-";
        Caja caja;

        if (result.hasErrors()) {
            //studentDTO.setId(id);
            return RD_FORM_VIEW;
        }

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            caja = cajaService.existeCaja(id);
            if(caja != null) {
                cajaService.convertirDTO(caja, objetoDTO);

                attributes.addFlashAttribute("message", "¡Caja actualizada correctamente!");
                cajaService.guardar(caja);
                return RD_FORM_VIEW;
            }
        }
        return RD_FALTA_PERMISO_VIEW;
    }

    @GetMapping("/{id}/delete")
        public String eliminarObjeto(@PathVariable String id, RedirectAttributes attributes  ) {
        this.operacion = "eliminar-";
        Long idCaja;

        try {
            idCaja = Long.parseLong(id);
        } catch (Exception e) {
            return RD_FORM_VIEW;
        }

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            Caja caja = cajaService.obtenerCaja(idCaja);
            cajaService.eliminarCaja(caja);
            attributes.addFlashAttribute("message", "¡Caja eliminado correctamente!");
            return RD_FORM_VIEW;
        } else {
            return RD_FALTA_PERMISO_VIEW;
        }
    }

}
