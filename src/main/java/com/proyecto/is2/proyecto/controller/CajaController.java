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
                    model.addAttribute("saldoApertura", cajaApertura.get(0).getSaldoApertura() );//lista los productos
                }
            }  //  

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

    @PostMapping("/cerrar")
    public String actualizarObjeto(@ModelAttribute("AperturaCaja") AperturaCajaDTO objetoDTO,
                                   BindingResult result, RedirectAttributes attributes) {
        this.operacion = "actualizar-";// :v
        Caja caja;

        if (result.hasErrors()) {
            //studentDTO.setId(id);
            return RD_FORM_VIEW;
        }

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Usuario usuario = usuarioRespository.findByEmail(username);// Obtener todos los datos del usuario 
            List<AperturaCaja> cajaApertura = aperturaCajaRepository.findByIdUsuarioOrderByIdAperturaCajaDesc(usuario.getIdUsuario());
            AperturaCaja aperturaCaja = cajaApertura.get(0);

            // String idCaja = objetoDTO.getIdCaja();
            aperturaCaja.setFechaCierre (java.time.LocalDate.now().toString());
            aperturaCaja.setSaldoCierre(objetoDTO.getSaldoCierre());
            aperturaCaja.setEstado("CeRrAdo");

            aperturaCajaService.guardar(aperturaCaja);

            attributes.addFlashAttribute("msgCaja", "Caja cerrada exitosamente !");
            

            return RD_FORM_VIEW;
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
