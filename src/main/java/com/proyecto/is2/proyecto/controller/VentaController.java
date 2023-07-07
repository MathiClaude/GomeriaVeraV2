package com.proyecto.is2.proyecto.controller;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.proyecto.is2.proyecto.controller.dto.UsuarioDTO;
import com.proyecto.is2.proyecto.controller.dto.VentaDTO;
import com.proyecto.is2.proyecto.model.Rol;
import com.proyecto.is2.proyecto.model.Usuario;
import com.proyecto.is2.proyecto.model.Servicio;
import com.proyecto.is2.proyecto.model.Timbrado;
import com.proyecto.is2.proyecto.model.Cliente;
import com.proyecto.is2.proyecto.model.Operacion;
import com.proyecto.is2.proyecto.model.Producto;
import com.proyecto.is2.proyecto.model.Proveedor;
import com.proyecto.is2.proyecto.model.AperturaCaja;
import com.proyecto.is2.proyecto.model.Caja;
import com.proyecto.is2.proyecto.model.Venta;
import com.proyecto.is2.proyecto.model.VentaDetalle;
import com.proyecto.is2.proyecto.Util.ModelAttributes;
import com.proyecto.is2.proyecto.Util.Permisos;
import com.proyecto.is2.proyecto.services.RolServiceImp;
import com.proyecto.is2.proyecto.services.UsuarioServiceImp;
import com.proyecto.is2.proyecto.services.VentaDetalleService;
import com.proyecto.is2.proyecto.services.VentaDetalleServiceImp;
import com.proyecto.is2.proyecto.services.ProductoServiceImp;
import com.proyecto.is2.proyecto.services.ProveedorService;
import com.proyecto.is2.proyecto.services.AperturaCajaServiceImp;

import com.proyecto.is2.proyecto.services.VentaServiceImp;
import com.proyecto.is2.proyecto.services.ServicioServiceImp;
import com.proyecto.is2.proyecto.services.TimbradoServiceImp;
import com.proyecto.is2.proyecto.services.ClienteServiceImp;
import com.proyecto.is2.proyecto.services.OperacionServiceImp;

import com.proyecto.is2.proyecto.repository.UsuarioRepository;
import com.proyecto.is2.proyecto.repository.VentaRepository;
import com.proyecto.is2.proyecto.repository.AperturaCajaRepository;
import com.proyecto.is2.proyecto.repository.CajaRepository;
import com.proyecto.is2.proyecto.repository.ClienteRepository;
import com.proyecto.is2.proyecto.repository.OperacionRepository;
import com.proyecto.is2.proyecto.repository.ProductoRepository;
import com.proyecto.is2.proyecto.repository.ServicioRepository;
import com.proyecto.is2.proyecto.repository.TimbradoRepository;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controlador encargado de recibir las peticiones
 * para realizar venta
 */
@Controller
@RequestMapping("/realizarVenta")
public class VentaController {
    final String VIEW = "ventas"; // identificador de la vista
    final String VIEW_PATH = "ventas";
    String operacion = "";
    final String FORM_VIEW = VIEW_PATH + "/realizarVenta";
    final String FORM_NEW = VIEW_PATH + "/listaVentas";
    final String FORM_EDIT = VIEW_PATH + "/editar";
    //final String LISTA_VENTA = "/listaVentas";

    final String RD_FORM_VIEW = "redirect:/ventas";
    final String COMPROBANTE = "/comprobante/";
    //final String COMPROBANTE = "redirect:/comprobante/";

    

    final String FALTA_PERMISO_VIEW = "falta-permiso";
    final String RD_FALTA_PERMISO_VIEW = "redirect:/" + FALTA_PERMISO_VIEW;
    final String ASIGNAR_ROL_VIEW = VIEW_PATH + "/asignar-rol";
    //endpoint
    private final static String DATA_CREATE_URL = "/data-create";

//    final String RD_ASIGNAR_ROL_VIEW = "redirect:/" + ASIGNAR_ROL_VIEW;
    final String P_ASIGNAR_ROL = "asignar-rol-usuario";

    
    @Autowired
    private VentaServiceImp ventaService;

    @Autowired
    private OperacionServiceImp operacionMov;

    @Autowired
    private TimbradoServiceImp timbradoService;
    
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    ServicioRepository servicioRepository;
    

    @Autowired
    VentaRepository ventaRepository;
    
    @Autowired
    TimbradoRepository timbradoRepository;

    @Autowired
    ProveedorService proveedorService;

     @Autowired
    VentaDetalleService ventaDetalleService;

    @Autowired
    AperturaCajaRepository aperturaCajaRepository;

    @Autowired
    CajaRepository cajaRepository;

    @Autowired
    OperacionRepository operacionRepository;

     // llamada a los servicios de venta
    @Autowired
    private ProductoServiceImp productoService; // llamada a los servicios de producto
    @Autowired
    private ServicioServiceImp servicioService; // llamada a los servicios de servicio
    @Autowired
    private ClienteServiceImp clienteService; // llamada a los servicios de cliente

    @Autowired
    private UsuarioServiceImp usuarioService; // llamada a los servicios de cliente

    @Autowired
    private AperturaCajaServiceImp aperturaCajaService; // llamada a los servicios de cliente
    
    @Autowired
    private RolServiceImp rolService;//llamada a servicios de roles

    /**
     * Instancia un UsuarioDTO para rellenar con datos
     * del formulario para luego mapearlo a un objeto
     * Usuario.
     * @return
     */
    @ModelAttribute("venta")
    public VentaDTO instanciarObjetoDTO() {
        return new VentaDTO();
    }

    @GetMapping
    public String mostrarGrilla(Model model, RedirectAttributes attributes) {

        boolean consultar = usuarioService.tienePermiso("consultar-" + VIEW);
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);
        boolean eliminar = usuarioService.tienePermiso("eliminar-" + VIEW);
        boolean actualizar = usuarioService.tienePermiso("actualizar-" + VIEW);
        boolean seleccionar = usuarioService.tienePermiso("seleccionar-" + VIEW);
        Timbrado timbradoActual = timbradoRepository.findByEstado("ACTIVO");


        if(consultar) {
            model.addAttribute("listProduct", productoService.listar());//lista los productos
            model.addAttribute("listServicio", servicioService.listar());//lista los productos
            model.addAttribute("listarCliente", clienteService.listar());//lista los clientes
            model.addAttribute("timbradoActual", timbradoActual);//lista las cajas

            String username = SecurityContextHolder.getContext().getAuthentication().getName(); //Obtener datos del usuario logueado[Basico]
            Usuario usuario = usuarioRepository.findByEmail(username);// Obtener todos los datos del usuario 

            model.addAttribute("nombreCajero", usuario.getUsername());//Nombre del cajero

            List<AperturaCaja> cajaApertura = aperturaCajaRepository.findByIdUsuarioOrderByIdAperturaCajaDesc(usuario.getIdUsuario());
            

            if(cajaApertura.size()>0){
                if((cajaApertura.get(0).getEstado().toUpperCase().equals("ABRIDO"))){
                    // model.addAttribute("aperturaCaja", caja.get(0).getIdAperturaCaja().toString());//lista los clientes
                    model.addAttribute("aperturaCaja","true"); // CAJA ESTA ABIERTA 
                    
                    Caja cajaActual = cajaRepository.findByIdCaja(cajaApertura.get(0).getIdCaja());
                    model.addAttribute("cajaActual", cajaActual.getDescripcion());//lista las cajas
                    model.addAttribute("fechaApertura", cajaApertura.get(0).getFechaApertura() );//fecha de apertura caja : v
                }else{
                    model.addAttribute("aperturaCaja","false"); // CAJA NO ESTA ABIERTA :v

                }
            }else{
                model.addAttribute("aperturaCaja","false"); // CAJA NO ESTA ABIERTA :v
            }
            



        } else {
            return FALTA_PERMISO_VIEW;
        }
        /*PARA TIRAR ALERT EN CASO DE QUE EXISTA CAJA SIN CERRAR O FALTA ABRIR
        if(consultar) {
            model.addAttribute(ModelAttributes.ALERT_CAJA_CERRAR, success);


        } else {
            return FALTA_PERMISO_VIEW;
        }*/

        model.addAttribute("permisoVer", consultar);
        model.addAttribute("permisoCrear", crear);
        model.addAttribute("permisoEliminar", eliminar);
        model.addAttribute("permisoActualizar", actualizar);
        model.addAttribute("permisoSeleccionar", seleccionar);
    

        return FORM_VIEW;
    }

    @GetMapping("/nuevo")
    public String formNuevo(Model model) {
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);
        boolean asignarRol = usuarioService.tienePermiso("asignar-rol-" + VIEW);
        List<Venta> ventas = ventaRepository.findAll();

        if(asignarRol) {
            model.addAttribute("roles", rolService.listar());
            model.addAttribute("listVentas",ventas);
        }
        model.addAttribute("permisoAsignarRol", asignarRol);

        if(crear) {
            return FORM_NEW;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @PostMapping("/crear")
    public String crearObjeto(@ModelAttribute("venta") VentaDTO objetoDTO,
            @RequestParam(value="ventaDetalle") String ventaDetalle,
                              RedirectAttributes attributes) {
        this.operacion = "crear-";
        //OBTENER LOS DATOS DEL USUARIO LOGUEADO ACTUALMENTE 
        String username = SecurityContextHolder.getContext().getAuthentication().getName(); //Obtener datos del usuario logueado[Basico]
        Usuario usuario = usuarioRepository.findByEmail(username);// Obtener todos los datos del usuario 
        
        //OBTENER LOS DATOS DE LA CAJA 
        List<AperturaCaja> cajaApertura = aperturaCajaRepository.findByIdUsuarioOrderByIdAperturaCajaDesc(usuario.getIdUsuario());
        List<Operacion> ultMov = operacionRepository.findByIdCajaOrderByIdOperacionDesc(cajaApertura.get(0).getIdCaja());
        BigDecimal montoVenta = new BigDecimal(objetoDTO.getMontoVenta());
        BigDecimal montoImpuesto= new BigDecimal(objetoDTO.getMontoImpuesto().toString());
        Timbrado timbradoActual = timbradoRepository.findByEstado("ACTIVO");

        
        String[] arrVentaDetalle = ventaDetalle.split("\\|");

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            Venta venta = new Venta();
            Cliente cliente = clienteRepository.findByIdCliente(objetoDTO.getIdCliente());
            venta.setCliente(cliente);
            venta.setUsuario(usuario);
            venta.setTimbrado(timbradoActual);
            venta.setNroFactura(new BigDecimal(timbradoActual.getNro_actual()));
            venta.setMontoImpuesto(montoImpuesto);
            venta.setFechaVenta(java.time.LocalDate.now().toString());
            venta.setMontoTotal(montoVenta);
            venta.setMontoVenta(montoVenta.toString());

            //GUARDAR LA VENTA
            Venta nuevaVenta = ventaService.guardar(venta);
            // OBTENER EL ID DE LA VENTA 
            Long idVenta = nuevaVenta.getIdVenta();
            
            for (String detCrudo : arrVentaDetalle) {
                String[] elementos= detCrudo.split(";");
                VentaDetalle vtaDet = new VentaDetalle();


                if(elementos[3].equals("S")){
                    Servicio  serv = servicioRepository.findByIdServicio(Long.parseLong(elementos[1]));
                    vtaDet.setServicio(serv);
                }else{
                    Producto  prod = productoRepository.findByIdProducto(Long.parseLong(elementos[1]));
                    vtaDet.setProducto(prod);
                }
                
                vtaDet.setVenta(nuevaVenta);
                vtaDet.setCantidad(new Float(elementos[0]));
                vtaDet.setPrecio(new Float(elementos[2]));
                ventaService.guardarDetalle(vtaDet);
    
            }
            // CREAR ESTRUCTURA PARA LA OPERACION A GUARDAR
            Operacion opEstructura = new Operacion();

            BigDecimal saldoAnterior = null;
            if(ultMov.size()>0){
                saldoAnterior = new BigDecimal(ultMov.get(0).getSaldoPosterior());
            }else{
                saldoAnterior = new BigDecimal("0");

            }
            
            opEstructura.setConcepto("Venta de Productos");
            opEstructura.setIdCaja(cajaApertura.get(0).getIdCaja());
            opEstructura.setIdUsuario(usuario.getIdUsuario());
            opEstructura.setMonto(objetoDTO.getMontoVenta());
            opEstructura.setFechaOperacion(LocalDate.now().toString());
            opEstructura.setSaldoAnterior(saldoAnterior.toString());
            opEstructura.setSaldoPosterior(saldoAnterior.add( montoVenta).toString());

            operacionMov.guardar(opEstructura);

            timbradoActual.setNro_actual((Integer.parseInt(timbradoActual.getNro_actual())+1) + "");
            timbradoService.guardar(timbradoActual);
            attributes.addFlashAttribute("message", "¡Venta creada exitosamente!");
            attributes.addFlashAttribute("valor", idVenta);
            //return "http://localhost:8080/comprobante/"+idVenta+"/pdf";

            // return new Respuesta("http://localhost:8080/comprobante/"+idVenta+"/pdf");
            return RD_FORM_VIEW+"/"+idVenta;
            
        } else {
            // return new Respuesta("Error");
            return RD_FALTA_PERMISO_VIEW;
        }
    }

    @GetMapping("/{id}")
    public String formEditar(@PathVariable String id, Model model) {
        boolean eliminar = usuarioService.tienePermiso("eliminar-" + VIEW);
        boolean asignarRol = usuarioService.tienePermiso("asignar-rol-" + VIEW);
        Venta venta;

        // validar el id
        try {
            Long idVenta = Long.parseLong(id);
            venta = ventaService.existeVenta(idVenta);
        } catch(Exception e) {
            return RD_FORM_VIEW;
        }

        model.addAttribute("user", venta);

        // validar si puede cambiar de rol
        if(asignarRol) {
            model.addAttribute("roles", rolService.listar());
        }
        model.addAttribute("permisoAsignarRol", asignarRol);

        if(eliminar) {
            return FORM_EDIT;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @PostMapping("/{id}")
    public String actualizarObjeto(@PathVariable Long id, @ModelAttribute("venta") VentaDTO objetoDTO,
                                   BindingResult result, RedirectAttributes attributes) {
        this.operacion = "actualizar-";
        Venta venta;

        if (result.hasErrors()) {
        //    studentDTO.setId(id);
            return RD_FORM_VIEW;
        }

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            venta = ventaService.existeVenta(id);
            if(venta != null) {
                ventaService.convertirDTO(venta, objetoDTO);

                // si tiene permiso se le asigna el rol con id del formulario
                // sino se queda con el mismo rol.
                /*if(ventaService.tienePermiso(P_ASIGNAR_ROL)) {
                    venta.setRol(rolService.existeRol(objetoDTO.getIdRol().longValue()));
                }*/

                attributes.addFlashAttribute("message", "¡Venta actualizado correctamente!");
                ventaService.guardar(venta);
                return RD_FORM_VIEW;
            }
        }
        return RD_FALTA_PERMISO_VIEW;
    }

    @GetMapping("/{id}/delete")
        public String eliminarObjeto(@PathVariable String id, RedirectAttributes attributes  ) {
        this.operacion = "eliminar-";
        Long idVenta;

        try {
            idVenta = Long.parseLong(id);
        } catch (Exception e) {
            return RD_FORM_VIEW;
        }

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            Venta venta = ventaService.obtenerVenta(idVenta);
            ventaService.eliminarVenta(venta);
            attributes.addFlashAttribute("message", "¡Venta eliminado correctamente!");
            return RD_FORM_VIEW;
        } else {
            return RD_FALTA_PERMISO_VIEW;
        }
    }

}

class Respuesta{
    String mensaje;

    public Respuesta(String msg){
        this.mensaje=msg;
    }
}