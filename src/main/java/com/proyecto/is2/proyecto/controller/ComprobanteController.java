package com.proyecto.is2.proyecto.controller;
import com.proyecto.is2.proyecto.controller.dto.UsuarioDTO;
import com.proyecto.is2.proyecto.controller.dto.ServicioDTO;

import com.proyecto.is2.proyecto.model.Rol;
import com.proyecto.is2.proyecto.model.Usuario;
import com.proyecto.is2.proyecto.model.Venta;
import com.proyecto.is2.proyecto.model.VentaDetalle;
import com.proyecto.is2.proyecto.model.Servicio;
import com.proyecto.is2.proyecto.model.AperturaCaja;
import com.proyecto.is2.proyecto.model.Caja;
import com.proyecto.is2.proyecto.model.Timbrado;

import com.proyecto.is2.proyecto.services.RolServiceImp;
import com.proyecto.is2.proyecto.services.ServicioServiceImp;

import com.proyecto.is2.proyecto.repository.VentaRepository;
import com.proyecto.is2.proyecto.repository.VentaDetalleRepository;
import com.proyecto.is2.proyecto.repository.UsuarioRepository;
import com.proyecto.is2.proyecto.repository.AperturaCajaRepository;
import com.proyecto.is2.proyecto.repository.CajaRepository;
import com.proyecto.is2.proyecto.repository.TimbradoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.TemplateEngine;
import org.springframework.security.core.context.SecurityContextHolder;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;

import java.util.ArrayList;
import java.util.List;
import java.io.ByteArrayOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Controlador encargado de recibir las peticiones
 * para realizar venta
 */
@Controller
@RequestMapping("/comprobante")
public class ComprobanteController {
        final String VIEW = "comprobante"; // identificador de la vista
    final String VIEW_PATH = "comprobante";
    String operacion = "";
    final String FORM_VIEW = VIEW_PATH + "/comprobante";
    final String COMPROBANTE_VIEW = VIEW_PATH + "/prueba";
    final String FORM_NEW = VIEW_PATH + "/nuevo";
    final String FORM_EDIT = VIEW_PATH + "/editar";
    final String RD_FORM_VIEW = "redirect:/comprobante";
    final String FALTA_PERMISO_VIEW = "falta-permiso";
    final String RD_FALTA_PERMISO_VIEW = "redirect:/" + FALTA_PERMISO_VIEW;
    final String ASIGNAR_ROL_VIEW = VIEW_PATH + "/asignar-rol";
//    final String RD_ASIGNAR_ROL_VIEW = "redirect:/" + ASIGNAR_ROL_VIEW;
    final String P_ASIGNAR_ROL = "asignar-rol-usuario";
    
    @Autowired
    private ServicioServiceImp servicioService; // llamada a los servicios de servicio

    @Autowired
    private RolServiceImp rolService;//llamada a servicios de roles

    /**
     * Instancia un UsuarioDTO para rellenar con datos
     * del formulario para luego mapearlo a un objeto
     * Usuario.
     * @return
     */

     @Autowired
    ServletContext servletContext;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    AperturaCajaRepository aperturaCajaRepository;

    @Autowired
    TimbradoRepository timbradoRepository;

    @Autowired
    VentaRepository ventaRepository;

    @Autowired
    VentaDetalleRepository ventaDetalleRepository;

    @Autowired
    CajaRepository cajaRepository;

    private final TemplateEngine templateEngine;

    public ComprobanteController(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }
    
    @ModelAttribute("servicio")
    public ServicioDTO instanciarObjetoDTO() {
        return new ServicioDTO();
    }

    @GetMapping
    public String mostrarGrilla(Model model) {

        boolean consultar = servicioService.tienePermiso("consultar-" + VIEW);
        boolean crear = servicioService.tienePermiso("crear-" + VIEW);
        boolean eliminar = servicioService.tienePermiso("eliminar-" + VIEW);
        boolean actualizar = servicioService.tienePermiso("actualizar-" + VIEW);

        //obtenerdatos de venta 
        // Venta ventaObtenida = ventaRepository.findByIdVenta(Long.parseLong(id));

        if(consultar) {
            model.addAttribute("listServicios", servicioService.listar());//lista los servicios
            // model.addAttribute("datosVenta", ventaObtenida);//lista los servicios

        } else {
            return FALTA_PERMISO_VIEW;
        }

        model.addAttribute("permisoVer", consultar);
        model.addAttribute("permisoCrear", crear);
        model.addAttribute("permisoEliminar", eliminar);
        model.addAttribute("permisoActualizar", actualizar);

        return FORM_VIEW;
    }


    @GetMapping("/{id}/pdf")
    public String generarPdfReporte(@PathVariable String id,Model model) {

        boolean consultar = servicioService.tienePermiso("consultar-" + VIEW);
        boolean crear = servicioService.tienePermiso("crear-" + VIEW);
        boolean eliminar = servicioService.tienePermiso("eliminar-" + VIEW);
        boolean actualizar = servicioService.tienePermiso("actualizar-" + VIEW);
        String username = SecurityContextHolder.getContext().getAuthentication().getName(); //Obtener datos del usuario logueado[Basico]
        Usuario usuario = usuarioRepository.findByEmail(username);// Obtener todos los datos del usuario 
        Timbrado timbradoActual = timbradoRepository.findByEstado("ACTIVO");


        //obtenerdatos de venta 
        Venta ventaObtenida = ventaRepository.findByIdVenta(Long.parseLong(id));
        List<VentaDetalle> detalleVenta = ventaDetalleRepository.findByVenta(ventaObtenida);
        model.addAttribute("nombreCajero", usuario.getUsername());//Nombre del cajero
        List<AperturaCaja> cajaApertura = aperturaCajaRepository.findByIdUsuarioOrderByIdAperturaCajaDesc(usuario.getIdUsuario());


        if(consultar) {
            model.addAttribute("listServicios", servicioService.listar());//lista los servicios
            model.addAttribute("datosVenta", ventaObtenida);//lista los servicios
            model.addAttribute("datosVentaDetalle", detalleVenta);//lista los servicios
            model.addAttribute("cantidadDetalle",detalleVenta.size());
            Caja cajaActual = cajaRepository.findByIdCaja(cajaApertura.get(0).getIdCaja());
            model.addAttribute("cajaActual", cajaActual.getDescripcion());//lista las cajas
            model.addAttribute("timbradoActual", timbradoActual);//lista las cajas
            model.addAttribute("fechaApertura", cajaApertura.get(0).getFechaApertura() );//fecha de apertura caja : v

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
        boolean crear = servicioService.tienePermiso("crear-" + VIEW);
        boolean asignarRol = servicioService.tienePermiso("asignar-rol-" + VIEW);

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

        // if (result.hasErrors()) {
        //     return FORM_NEW;
        // }

        if(servicioService.tienePermiso(operacion + VIEW)) {
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
        boolean eliminar = servicioService.tienePermiso("eliminar-" + VIEW);
        boolean asignarRol = servicioService.tienePermiso("asignar-rol-" + VIEW);
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

        if(servicioService.tienePermiso(operacion + VIEW)) {
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

        if(servicioService.tienePermiso(operacion + VIEW)) {
            Servicio servicio = servicioService.obtenerServicio(idServicio);
            servicioService.eliminarServicio(servicio);
            attributes.addFlashAttribute("message", "¡Servicio eliminado correctamente!");
            return RD_FORM_VIEW;
        } else {
            return RD_FALTA_PERMISO_VIEW;
        }
    }
}
