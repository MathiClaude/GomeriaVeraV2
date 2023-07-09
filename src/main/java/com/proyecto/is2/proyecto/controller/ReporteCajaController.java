package com.proyecto.is2.proyecto.controller;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.proyecto.is2.proyecto.controller.dto.DatoGraficoVentaDTO;
import com.proyecto.is2.proyecto.controller.dto.ParametrosDonaDTO;
import com.proyecto.is2.proyecto.controller.dto.ReporteVentaDTO;
import com.proyecto.is2.proyecto.controller.dto.ReporteVentaProductoDTO;
import com.proyecto.is2.proyecto.controller.dto.UsuarioDTO;
import com.proyecto.is2.proyecto.controller.dto.VentaDTO;
import com.proyecto.is2.proyecto.controller.dto.ReporteCajaDTO;

import com.proyecto.is2.proyecto.model.Rol;
import com.proyecto.is2.proyecto.model.Usuario;
import com.proyecto.is2.proyecto.model.Servicio;
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
import com.proyecto.is2.proyecto.services.CajaService;
import com.proyecto.is2.proyecto.services.VentaServiceImp;
import com.proyecto.is2.proyecto.services.ServicioServiceImp;
import com.proyecto.is2.proyecto.services.TipoProductoService;
import com.proyecto.is2.proyecto.services.ClienteServiceImp;
import com.proyecto.is2.proyecto.services.OperacionServiceImp;
import com.proyecto.is2.proyecto.repository.UsuarioRepository;
import com.proyecto.is2.proyecto.repository.VentaRepository;
import com.proyecto.is2.proyecto.repository.AperturaCajaRepository;
import com.proyecto.is2.proyecto.repository.CajaRepository;
import com.proyecto.is2.proyecto.repository.ClienteRepository;
import com.proyecto.is2.proyecto.repository.OperacionRepository;
import com.proyecto.is2.proyecto.repository.ProductoRepository;

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
import javax.persistence.Tuple;

/**
 * Controlador encargado de recibir las peticiones
 * para realizar venta
 */
@Controller
@RequestMapping("/caja")
public class ReporteCajaController {
    final String VIEW = "reporte"; // identificador de la vista
    final String VIEW_PATH = "reporte";
    String operacion = "";
    final String FORM_VIEW = VIEW_PATH + "/caja";
    final String FORM_NEW = VIEW_PATH + "/cajaReporte";
    final String FORM_EDIT = VIEW_PATH + "/editar";
    final String RD_FORM_VIEW = "redirect:/caja";
    final String FALTA_PERMISO_VIEW = "falta-permiso";
    final String RD_FALTA_PERMISO_VIEW = "redirect:/" + FALTA_PERMISO_VIEW;
    final String ASIGNAR_ROL_VIEW = VIEW_PATH + "/asignar-rol";
    
    final String GRAFICO_ESTADISTICO = "/reporte/cajaChart";
    final String REPORTE_ESPECIFICO = "/reporte/cajaReportEsp";
    final String REPORTE_HISTORIAL = "/reporte/ventaHistorial";



    //endpoint
    private final static String DATA_CREATE_URL = "/data-create";

//    final String RD_ASIGNAR_ROL_VIEW = "redirect:/" + ASIGNAR_ROL_VIEW;
    final String P_ASIGNAR_ROL = "asignar-rol-usuario";

    
    @Autowired
    private VentaServiceImp ventaService;

    @Autowired
    private OperacionServiceImp operacionMov  ;
    
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    ProveedorService proveedorService;

    @Autowired
    CajaService cajaService;

    @Autowired
    VentaRepository ventaRepository;

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
        List<Tuple> listaCruda = usuarioService.listarUsuariosBuscador();
        List<UsuarioDTO> usuarios = new ArrayList<>();
        for (Tuple user : listaCruda) {
            usuarios.add(new UsuarioDTO(user.get(1).toString(),Integer.parseInt(user.get(0).toString()),user.get(3).toString()));
        }

        if(consultar) {
            // model.addAttribute("listProduct", productoService.listar());//lista los productos
            // model.addAttribute("listServicio", servicioService.listar());//lista los productos
            model.addAttribute("listarCliente", clienteService.listar());//lista los clientes
            model.addAttribute("listarCaja", cajaService.listar());//lista los clientes

            model.addAttribute("listarUsuario",usuarios );//lista los clientes
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

    private List<ReporteCajaDTO> parsearDatosReporteCaja(List<Tuple> datosCrudo){        
        List<ReporteCajaDTO> lista = new ArrayList<>();
        for (Tuple elemento : datosCrudo) {
            lista.add( new ReporteCajaDTO(elemento.get(0).toString(),elemento.get(1).toString(),elemento.get(2).toString(),elemento.get(3).toString(),elemento.get(4).toString()));
            // lista.add(elemento.get(0).toString()+"-"+ elemento.get(1).toString());
        }
        return lista;
    }
    
    @GetMapping("/stockReporte/caja/{id_caja}")
    public String reporteVentaCliente(Model model,@PathVariable String id_caja) {
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);
        //boolean asignarRol = usuarioService.tienePermiso("asignar-rol-" + VIEW);

        String username = SecurityContextHolder.getContext().getAuthentication().getName(); //Obtener datos del usuario logueado[Basico]
        Usuario usuario = usuarioRepository.findByEmail(username);// Obtener todos los datos del usuario 

        List<Tuple> datosCaja = cajaRepository.findHistorialOperacionesByidCajaNative(new Long(id_caja));

        List<ReporteCajaDTO> listaDatos = this.parsearDatosReporteCaja(datosCaja);

        Cliente cliente = clienteRepository.findByIdCliente(new Long(id_caja));
        Caja caja = cajaRepository.findByIdCaja(new Long(id_caja));


        BigDecimal total = new BigDecimal(0);
        BigDecimal totalIva = new BigDecimal(0);
        Integer totalItems = 0;
        /*for (ReporteCajaDTO venta : listaDatos) {
            total = total.add( new BigDecimal(venta.getMonto()));
            totalIva = totalIva.add( new BigDecimal(venta.getImpuesto()));
            totalItems ++;
        }*/
        //java.time.LocalDate.now().toString()
        model.addAttribute("datos", listaDatos);
        model.addAttribute("cantidadDetalles", listaDatos.size());
        model.addAttribute("totalMonto", total);
        model.addAttribute("totalIva", totalIva);
        model.addAttribute("totalItems", totalItems);


        // para cabecera del reporte
        //título
        model.addAttribute("tRCaja","Reporte de stock por caja");
        model.addAttribute("tRUsuario","");        
        model.addAttribute("tRCajaUser","");
        model.addAttribute("tRFecha","");
        model.addAttribute("tRFechaCaja","");
        model.addAttribute("tRFechaUser","");
        model.addAttribute("tRepAll","");

        //descripción del reporte
        model.addAttribute("dRCaja","Este reporte de venta se basa en todas operaciones registradas por la caja seleccionado, el mismo cuenta con los siguientes parámetros:");        
        model.addAttribute("dRUsuario",""); // título reporte usuario
        model.addAttribute("dRCajaUser",""); // título reporte usuario y cliente
        model.addAttribute("dRFecha",""); //título fecha
        model.addAttribute("dRFechaCaja",""); //título fecha y cliente
        model.addAttribute("dRFechaUser",""); //título fecha y usuario
        model.addAttribute("dRAll","");  //título fecha, cliente y usuario

        //parámetros que serán utilizados para el reporte
        model.addAttribute("pCaja","Caja: " + caja.getDescripcion());
        model.addAttribute("pUsuario","");
        model.addAttribute("fI","");
        
        

        model.addAttribute("Generado por: " + usuario.getUsername()); 
        model.addAttribute("pFechaEmision","Fecha emisión: "+java.time.LocalDate.now().toString());

        


        if(crear) {
            return FORM_NEW;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @GetMapping("/cajaReporte/user/{usuario_id}")
    public String reporteVentaUsuario(Model model,@PathVariable String usuario_id) {
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);
        //boolean asignarRol = usuarioService.tienePermiso("asignar-rol-" + VIEW);

        
        
        List<Tuple> datosCaja = cajaRepository.findHistorialOperacionesByidUsuarioNative(new Long(usuario_id));
        List<ReporteCajaDTO> listaDatos = this.parsearDatosReporteCaja(datosCaja);

        Usuario usuario = usuarioRepository.findByIdUsuario(new Long(usuario_id));
        
        BigDecimal total = new BigDecimal(0);
        BigDecimal totalIva = new BigDecimal(0);
        Integer totalItems = 0;
        /*for (ReporteCajaDTO venta : listaDatos) {
            total = total.add( new BigDecimal(venta.getMonto()));
            totalIva = totalIva.add( new BigDecimal(venta.getImpuesto()));
            totalItems ++;
        }*/
        //java.time.LocalDate.now().toString()
        model.addAttribute("datos", listaDatos);
        model.addAttribute("cantidadDetalles", listaDatos.size());
        model.addAttribute("totalMonto", total);
        model.addAttribute("totalIva", totalIva);
        model.addAttribute("totalItems", totalItems);

        // para cabecera del reporte
        //título
        model.addAttribute("tRCaja","");
        model.addAttribute("tRUsuario","Reporte de stock por vendedor");        
        model.addAttribute("tRCajaUser","");
        model.addAttribute("tRFecha","");
        model.addAttribute("tRFechaCaja","");
        model.addAttribute("tRFechaUser","");
        model.addAttribute("tRepAll","");

        //descripción del reporte
        model.addAttribute("dRCaja","");        
        model.addAttribute("dRUsuario","Este reporte de stock se basa en todas operaciones registradas por el vendedor seleccionado, el mismo cuenta con los siguientes parámetros:"); // título reporte usuario
        model.addAttribute("dRCajaUser",""); // título reporte usuario y cliente
        model.addAttribute("dRFecha",""); //título fecha
        model.addAttribute("dRFechaCaja",""); //título fecha y cliente
        model.addAttribute("dRFechaUser",""); //título fecha y usuario
        model.addAttribute("dRAll","");  //título fecha, cliente y usuario

        //parámetros que serán utilizados para el reporte
        model.addAttribute("pCaja","");
        model.addAttribute("pUsuario: " +  usuario.getUsername());
        model.addAttribute("fI","");
        
        

        model.addAttribute("Generado por: ", ""); 
        model.addAttribute("pFechaEmision","Fecha emisión: "+java.time.LocalDate.now().toString());


        if(crear) {
            return FORM_NEW;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @GetMapping("/cajaReporte/usuarioCaja/{id_caja}/{id_usuario}")
    public String reporteVentaUsuarioCliente(Model model,@PathVariable String id_caja, @PathVariable String id_usuario) {
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);
        //boolean asignarRol = usuarioService.tienePermiso("asignar-rol-" + VIEW);

        List<Tuple> datosCaja = cajaRepository.findHistorialOperacionesByidCajaUsuarioNative(new Long(id_caja), new Long(id_usuario));
        List<ReporteCajaDTO> listaDatos = this.parsearDatosReporteCaja(datosCaja);
        Caja caja = cajaRepository.findByIdCaja(new Long(id_caja));

        Usuario usuario = usuarioRepository.findByIdUsuario(new Long(id_usuario));
        
        
         BigDecimal total = new BigDecimal(0);
        BigDecimal totalIva = new BigDecimal(0);
        Integer totalItems = 0;
        /*for (ReporteCajaDTO venta : listaDatos) {
            total = total.add( new BigDecimal(venta.getMonto()));
            totalIva = totalIva.add( new BigDecimal(venta.getImpuesto()));
            totalItems ++;
        }*/
        //java.time.LocalDate.now().toString()
        model.addAttribute("datos", listaDatos);
        model.addAttribute("cantidadDetalles", listaDatos.size());
        model.addAttribute("totalMonto", total);
        model.addAttribute("totalIva", totalIva);
        model.addAttribute("totalItems", totalItems);


       // para cabecera del reporte
        //título
        model.addAttribute("tRCaja","");
        model.addAttribute("tRUsuario","");        
        model.addAttribute("tRCajaUser","Reporte de stock por caja y vendedor");
        model.addAttribute("tRFecha","");
        model.addAttribute("tRFechaCaja","");
        model.addAttribute("tRFechaUser","");
        model.addAttribute("tRepAll","");

        //descripción del reporte
        model.addAttribute("dRCaja","");        
        model.addAttribute("dRUsuario",""); // descripción reporte usuario
        model.addAttribute("dRCajaUser","Este reporte de stock se basa en todas operaciones registradas por el vendedor y caja seleccionada, el mismo cuenta con los siguientes parámetros:"); // descripción reporte usuario y cliente
        model.addAttribute("dRFecha",""); //descripción fecha
        model.addAttribute("dRFechaCaja",""); //descripción fecha y cliente
        model.addAttribute("dRFechaUser",""); //descripción fecha y usuario
        model.addAttribute("dRAll","");  //descripción fecha, cliente y usuario

        //parámetros que serán utilizados para el reporte
        model.addAttribute("pCaja" + caja.getDescripcion());
        model.addAttribute("pUsuario: " +  usuario.getUsername());
        model.addAttribute("fI","");
        
        

        model.addAttribute("Generado por: ", ""); 
        model.addAttribute("pFechaEmision","Fecha emisión: "+java.time.LocalDate.now().toString());


        if(crear) {
            return FORM_NEW;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @GetMapping("/cajaReporte/rangoFecha/{fechaDesde}/{fechaHasta}")
    public String reporteRangoFecha(Model model,@PathVariable String fechaDesde, @PathVariable String fechaHasta) {
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);

        String username = SecurityContextHolder.getContext().getAuthentication().getName(); //Obtener datos del usuario logueado[Basico]
        Usuario usuario = usuarioRepository.findByEmail(username);// Obtener todos los datos del usuario 

        List<Tuple> datosCaja = cajaRepository.findHistorialOperacionesByRangoNative(fechaDesde,fechaHasta);

        List<ReporteCajaDTO> listaDatos = this.parsearDatosReporteCaja(datosCaja);

         BigDecimal total = new BigDecimal(0);
        BigDecimal totalIva = new BigDecimal(0);
        Integer totalItems = 0;
        /*for (ReporteCajaDTO venta : listaDatos) {
            total = total.add( new BigDecimal(venta.getMonto()));
            totalIva = totalIva.add( new BigDecimal(venta.getImpuesto()));
            totalItems ++;
        }*/
        //java.time.LocalDate.now().toString()
        model.addAttribute("datos", listaDatos);
        model.addAttribute("cantidadDetalles", listaDatos.size());
        model.addAttribute("totalMonto", total);
        model.addAttribute("totalIva", totalIva);
        model.addAttribute("totalItems", totalItems);

        // para cabecera del reporte
        //título
        model.addAttribute("tRCaja","");
        model.addAttribute("tRUsuario","");        
        model.addAttribute("tRCajaUser","");
        model.addAttribute("tRFecha","Reporte de stock por fecha");
        model.addAttribute("tRFechaCaja","");
        model.addAttribute("tRFechaUser","");
        model.addAttribute("tRepAll","");

        //descripción del reporte
        model.addAttribute("dRCaja","");        
        model.addAttribute("dRUsuario",""); // descripción reporte usuario
        model.addAttribute("dRCajaUser",""); // descripción reporte usuario y cliente
        model.addAttribute("dRFecha","Este reporte de stock se basa en todas operaciones registradas en el rango de fecha seleccionada, el mismo cuenta con los siguientes parámetros:"); //descripción fecha
        model.addAttribute("dRFechaCaja",""); //descripción fecha y cliente
        model.addAttribute("dRFechaUser",""); //descripción fecha y usuario
        model.addAttribute("dRAll","");  //descripción fecha, cliente y usuario

        //parámetros que serán utilizados para el reporte
        model.addAttribute("pCaja", "");
        model.addAttribute("pUsuario: ", "");
        model.addAttribute("fI" +fechaDesde + " - " + fechaHasta+"");
        
        

        model.addAttribute("Generado por: " +  usuario.getUsername()); 
        model.addAttribute("pFechaEmision","Fecha emisión: "+java.time.LocalDate.now().toString());


        if(crear) {
            return FORM_NEW;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @GetMapping("/cajaReporte/rangoCaja/{fechaDesde}/{fechaHasta}/{id_caja}")
    public String reporteVentaFechaCliente(Model model, @PathVariable String fechaDesde,@PathVariable String fechaHasta, @PathVariable String id_caja) {
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);

        String username = SecurityContextHolder.getContext().getAuthentication().getName(); //Obtener datos del usuario logueado[Basico]
        Usuario usuario = usuarioRepository.findByEmail(username);// Obtener todos los datos del usuario 

        List<Tuple> datosCaja = cajaRepository.findHistorialOperacionesByidCajaRangoNative(new Long(id_caja), fechaDesde, fechaHasta);

        List<ReporteCajaDTO> listaDatos = this.parsearDatosReporteCaja(datosCaja);
        Caja caja = cajaRepository.findByIdCaja(new Long(id_caja));

        BigDecimal total = new BigDecimal(0);
        BigDecimal totalIva = new BigDecimal(0);
        Integer totalItems = 0;
        /*for (ReporteCajaDTO venta : listaDatos) {
            total = total.add( new BigDecimal(venta.getMonto()));
            totalIva = totalIva.add( new BigDecimal(venta.getImpuesto()));
            totalItems ++;
        }*/
        //java.time.LocalDate.now().toString()
        model.addAttribute("datos", listaDatos);
        model.addAttribute("cantidadDetalles", listaDatos.size());
        model.addAttribute("totalMonto", total);
        model.addAttribute("totalIva", totalIva);
        model.addAttribute("totalItems", totalItems);

        // para cabecera del reporte
        //título
        model.addAttribute("tRCaja","");
        model.addAttribute("tRUsuario","");        
        model.addAttribute("tRCajaUser","");
        model.addAttribute("tRFecha","");
        model.addAttribute("tRFechaCaja","Reporte de stock por caja y fecha");
        model.addAttribute("tRFechaUser","");
        model.addAttribute("tRepAll","");

        //descripción del reporte
        model.addAttribute("dRCaja","");        
        model.addAttribute("dRUsuario",""); // descripción reporte usuario
        model.addAttribute("dRCajaUser",""); // descripción reporte usuario y cliente
        model.addAttribute("dRFecha",""); //descripción fecha
        model.addAttribute("dRFechaCaja","Este reporte de stock se basa en todas operaciones registradas en el rango de fecha y caja seleccionada, el mismo cuenta con los siguientes parámetros:"); //descripción fecha y cliente
        model.addAttribute("dRFechaUser",""); //descripción fecha y usuario
        model.addAttribute("dRAll","");  //descripción fecha, cliente y usuario

        //parámetros que serán utilizados para el reporte
        model.addAttribute("pCaja", caja.getDescripcion());
        model.addAttribute("pUsuario: ", "");
        model.addAttribute("fI" +fechaDesde + " - " + fechaHasta+"");
        
        

        model.addAttribute("Generado por: " +  usuario.getUsername()); 
        model.addAttribute("pFechaEmision","Fecha emisión: "+java.time.LocalDate.now().toString());


        if(crear) {
            return FORM_NEW;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @GetMapping("/cajaReporte/rangoVendedor/{fechaDesde}/{fechaHasta}/{id_usuario}")
    public String reporteVentaFechaUsuario(Model model,@PathVariable String id_usuario, @PathVariable String fechaDesde, @PathVariable String fechaHasta) {
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);

        List<Tuple> datosCaja = cajaRepository.findHistorialOperacionesByidUsuarioRangoNative(new Long(id_usuario), fechaDesde, fechaHasta);

        List<ReporteCajaDTO> listaDatos = this.parsearDatosReporteCaja(datosCaja);

        Usuario usuario = usuarioRepository.findByIdUsuario(new Long(id_usuario));        
        

        BigDecimal total = new BigDecimal(0);
        BigDecimal totalIva = new BigDecimal(0);
        Integer totalItems = 0;
        /*for (ReporteCajaDTO venta : listaDatos) {
            total = total.add( new BigDecimal(venta.getMonto()));
            totalIva = totalIva.add( new BigDecimal(venta.getImpuesto()));
            totalItems ++;
        }*/
        //java.time.LocalDate.now().toString()
        model.addAttribute("datos", listaDatos);
        model.addAttribute("cantidadDetalles", listaDatos.size());
        model.addAttribute("totalMonto", total);
        model.addAttribute("totalIva", totalIva);
        model.addAttribute("totalItems", totalItems);


       // para cabecera del reporte
        //título
        model.addAttribute("tRCaja","");
        model.addAttribute("tRUsuario","");        
        model.addAttribute("tRCajaUser","");
        model.addAttribute("tRFecha","");
        model.addAttribute("tRFechaCaja","");
        model.addAttribute("tRFechaUser","Reporte de stock por vendedor y fecha");
        model.addAttribute("tRepAll","");

        //descripción del reporte
        model.addAttribute("dRCaja","");        
        model.addAttribute("dRUsuario",""); // descripción reporte usuario
        model.addAttribute("dRCajaUser",""); // descripción reporte usuario y cliente
        model.addAttribute("dRFecha",""); //descripción fecha
        model.addAttribute("dRFechaCaja",""); //descripción fecha y cliente
        model.addAttribute("dRFechaUser","Este reporte de stock se basa en todas operaciones registradas en el rango de fecha y vendedor seleccionado, el mismo cuenta con los siguientes parámetros:"); //descripción fecha y usuario
        model.addAttribute("dRAll","");  //descripción fecha, cliente y usuario

        //parámetros que serán utilizados para el reporte
        model.addAttribute("pCaja", "");
        model.addAttribute("pUsuario: " + usuario.getUsername());
        model.addAttribute("fI" +fechaDesde + " - " + fechaHasta+"");
        
        

        model.addAttribute("Generado por: "); 
        model.addAttribute("pFechaEmision","Fecha emisión: "+java.time.LocalDate.now().toString());
        if(crear) {
            return FORM_NEW;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @GetMapping("/cajaReporte/allReport/{fechaDesde}/{fechaHasta}/{id_usuario}/{id_caja}")
    public String reporteVentaRangoAll(Model model ,@PathVariable String fechaDesde, @PathVariable String fechaHasta,@PathVariable String id_usuario, @PathVariable String id_caja) {
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);

        List<Tuple> datosCaja = cajaRepository.findHistorialOperacionesByidCajaRangoNative(new Long(id_caja), new Long(id_usuario),fechaDesde, fechaHasta);

        List<ReporteCajaDTO> listaDatos = this.parsearDatosReporteCaja(datosCaja);

        Usuario usuario = usuarioRepository.findByIdUsuario(new Long(id_usuario));
        Caja caja = cajaRepository.findByIdCaja(new Long(id_caja));

        BigDecimal total = new BigDecimal(0);
        BigDecimal totalIva = new BigDecimal(0);
        Integer totalItems = 0;
        /*for (ReporteCajaDTO venta : listaDatos) {
            total = total.add( new BigDecimal(venta.getMonto()));
            totalIva = totalIva.add( new BigDecimal(venta.getImpuesto()));
            totalItems ++;
        }*/
        //java.time.LocalDate.now().toString()
        model.addAttribute("datos", listaDatos);
        model.addAttribute("cantidadDetalles", listaDatos.size());
        model.addAttribute("totalMonto", total);
        model.addAttribute("totalIva", totalIva);
        model.addAttribute("totalItems", totalItems);


// para cabecera del reporte
        //título
        model.addAttribute("tRCaja","");
        model.addAttribute("tRUsuario","");        
        model.addAttribute("tRCajaUser","");
        model.addAttribute("tRFecha","");
        model.addAttribute("tRFechaCaja","");
        model.addAttribute("tRFechaUser","");
        model.addAttribute("tRepAll","Reporte de general de stock");

        //descripción del reporte
        model.addAttribute("dRCaja","");        
        model.addAttribute("dRUsuario",""); // descripción reporte usuario
        model.addAttribute("dRCajaUser",""); // descripción reporte usuario y cliente
        model.addAttribute("dRFecha",""); //descripción fecha
        model.addAttribute("dRFechaCaja",""); //descripción fecha y cliente
        model.addAttribute("dRFechaUser",""); //descripción fecha y usuario
        model.addAttribute("dRAll","Este reporte de stock se basa en todas operaciones registradas en el rango de fecha, respecto al vendedor y caja seleccionada, el mismo cuenta con los siguientes parámetros:");  //descripción fecha, cliente y usuario

        //parámetros que serán utilizados para el reporte
        model.addAttribute("pCaja" + caja.getDescripcion());
        model.addAttribute("pUsuario: " + usuario.getUsername());
        model.addAttribute("fI" +fechaDesde + " - " + fechaHasta+"");
        
        model.addAttribute("Generado por: ", ""); 
        model.addAttribute("pFechaEmision","Fecha emisión: "+java.time.LocalDate.now().toString());

        if(crear) {
            return FORM_NEW;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }
    
    /* FUNCION PARA PARSEAR LOS DATOS PARA REPORTES ESPECIFICOS DE PRODUCTOS*/
     private List<ReporteVentaProductoDTO> parsearDatosReporteProducto(List<Tuple> datosCrudo){        
        List<ReporteVentaProductoDTO> lista = new ArrayList<>();
        for (Tuple elemento : datosCrudo) {
            lista.add( new ReporteVentaProductoDTO(elemento.get(0).toString(),elemento.get(1).toString(),elemento.get(2).toString(),elemento.get(3).toString()) );
            // lista.add(elemento.get(0).toString()+"-"+ elemento.get(1).toString());
        }
        return lista;
    }

    @GetMapping("/cajaReportEsp")
    public String reporteProductoCantidad(Model model) {
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);

        List<Tuple> datosVenta = ventaRepository.findInformeProductoCantNative();

        List<ReporteVentaProductoDTO> listaDatos = this.parsearDatosReporteProducto(datosVenta);

        String username = SecurityContextHolder.getContext().getAuthentication().getName(); //Obtener datos del usuario logueado[Basico]
        Usuario usuario = usuarioRepository.findByEmail(username);// Obtener todos los datos del usuario 

 
        BigDecimal total = new BigDecimal(0);
        Float cant = new Float(0);
        for (ReporteVentaProductoDTO prod : listaDatos) {
            total = total.add( new BigDecimal(prod.getMonto()));
            cant += Float.parseFloat(prod.getCantProducto());
        }
        model.addAttribute("datos", listaDatos);
        model.addAttribute("cantidadDetalles", listaDatos.size());
        model.addAttribute("totalMonto", total);
        model.addAttribute("cant", cant);



        // para cabecera del reporte
        //título
        model.addAttribute("tRProdCant"," Reporte de productos más vendidos respecto a la cantidad");
        model.addAttribute("tRProdMont","");        

        //descripción del reporte
        model.addAttribute("dRProdCant","En este reporte se visualizan los 10 productos más vendidos respecto a la cantidad que se vendió");        
        model.addAttribute("dRProdMont",""); 
        

        //parámetros que serán utilizados para el reporte
        model.addAttribute("pUsuarioRepor", "Generado por: " + usuario.getUsername());       
        model.addAttribute("pFechaEmision","Fecha emisión: "+ java.time.LocalDate.now().toString());

        if(crear) {
            return REPORTE_ESPECIFICO;
        } else {
            return REPORTE_ESPECIFICO;
        }
    }

    
    /* 
    @GetMapping("/ventaReportEsp/historial")
    public String verHistorialVentas(Model model) {
        this.operacion = "crear-";
        String username = SecurityContextHolder.getContext().getAuthentication().getName(); //Obtener datos del usuario logueado[Basico]
        Usuario usuario = usuarioRepository.findByEmail(username);// Obtener todos los datos del usuario 
        
        // List<Operacion> ultMov = operacionRepository.findByIdCajaOrderByIdOperacionDesc(cajaApertura.get(0).getIdCaja());
        List<Tuple> datosVenta = ventaRepository.findInformeHistorialNative();
        List<ReporteVentaDTO> listaDatos = this.parsearDatosReporteVenta(datosVenta);


        // para cabecera del reporte
        //título
        model.addAttribute("tHistorial","Historial de Ventas");


        //descripción del gráfico
        model.addAttribute("dHistorial"," Este reporte representa las últimas 10 ventas realizadas hasta la fecha"); // descripción gráfico producto


        //parámetros que serán utilizados para el reporte
        model.addAttribute("pHUser","Generado por: " + usuario.getUsername());        
        model.addAttribute("pFE","Fecha emisión: "+ java.time.LocalDate.now().toString());

        

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            model.addAttribute("titulos", "['Red', 'Orange', 'Yellow', 'Green', 'Blue']");
            model.addAttribute("datos", listaDatos);

            return REPORTE_HISTORIAL;
        } else {
            return REPORTE_HISTORIAL;
        }
    }
    */
    

}
