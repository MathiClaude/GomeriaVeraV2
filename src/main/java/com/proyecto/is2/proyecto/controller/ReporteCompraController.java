package com.proyecto.is2.proyecto.controller;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.proyecto.is2.proyecto.controller.dto.UsuarioDTO;
import com.proyecto.is2.proyecto.controller.dto.CompraDTO;
import com.proyecto.is2.proyecto.controller.dto.DatoGraficoCompraDTO;
import com.proyecto.is2.proyecto.controller.dto.DatoGraficoVentaDTO;
import com.proyecto.is2.proyecto.controller.dto.ReporteCompraDTO;
import com.proyecto.is2.proyecto.controller.dto.ReporteVentaDTO;
import com.proyecto.is2.proyecto.controller.dto.ReporteVentaProductoDTO;
import com.proyecto.is2.proyecto.controller.dto.VentaDTO;

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

import com.proyecto.is2.proyecto.services.VentaServiceImp;
import com.proyecto.is2.proyecto.services.ServicioServiceImp;
import com.proyecto.is2.proyecto.services.ClienteServiceImp;
import com.proyecto.is2.proyecto.services.OperacionServiceImp;
import com.proyecto.is2.proyecto.repository.UsuarioRepository;
import com.proyecto.is2.proyecto.repository.AperturaCajaRepository;
import com.proyecto.is2.proyecto.repository.CajaRepository;
import com.proyecto.is2.proyecto.repository.ClienteRepository;
import com.proyecto.is2.proyecto.repository.OperacionRepository;
import com.proyecto.is2.proyecto.repository.ProductoRepository;
import com.proyecto.is2.proyecto.repository.ProveedorRepository;
import com.proyecto.is2.proyecto.repository.VentaRepository;
import com.proyecto.is2.proyecto.repository.CompraRepository;


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
@RequestMapping("/compra")
public class ReporteCompraController {
    final String VIEW = "reporte"; // identificador de la vista
    final String VIEW_PATH = "reporte";
    String operacion = "";
    final String FORM_VIEW = VIEW_PATH + "/compra";
    final String FORM_NEW = VIEW_PATH + "/compraReporte";
    final String FORM_EDIT = VIEW_PATH + "/editar";
    final String RD_FORM_VIEW = "redirect:/compra";
    final String FALTA_PERMISO_VIEW = "falta-permiso";
    final String RD_FALTA_PERMISO_VIEW = "redirect:/" + FALTA_PERMISO_VIEW;
    final String ASIGNAR_ROL_VIEW = VIEW_PATH + "/asignar-rol";

    final String GRAFICO_ESTADISTICO = "/reporte/compraChart";
    final String REPORTE_ESPECIFICO = "/reporte/compraReportEsp";
    final String REPORTE_HISTORIAL = "/reporte/compraHistorial";



   

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
    VentaDetalleService ventaDetalleService;

    @Autowired
    AperturaCajaRepository aperturaCajaRepository;

    @Autowired
    CajaRepository cajaRepository;

    @Autowired
    OperacionRepository operacionRepository;

    @Autowired
    ProveedorRepository proveedorRepository;

    @Autowired
    CompraRepository compraRepository;

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
    @ModelAttribute("compra")
    public CompraDTO instanciarObjetoDTO() {
        return new CompraDTO();
    }

    @GetMapping
    public String mostrarGrilla(Model model, RedirectAttributes attributes) {

        boolean consultar = usuarioService.tienePermiso("consultar-" + VIEW);
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);
        boolean eliminar = usuarioService.tienePermiso("eliminar-" + VIEW);
        boolean actualizar = usuarioService.tienePermiso("actualizar-" + VIEW);
        boolean seleccionar = usuarioService.tienePermiso("seleccionar-" + VIEW);



        if(consultar) {
            model.addAttribute("listProduct", productoService.listar());//lista los productos
            model.addAttribute("listServicio", servicioService.listar());//lista los productos
            model.addAttribute("listarCliente", clienteService.listar());//lista los clientes

            model.addAttribute("listarProveedor", proveedorService.listar());//lista los clientes

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

    private List<ReporteCompraDTO> parsearDatosReporteCompra(List<Tuple> datosCrudo){        
        List<ReporteCompraDTO> lista = new ArrayList<>();
        for (Tuple elemento : datosCrudo) {
            lista.add( new ReporteCompraDTO(elemento.get(0).toString(),elemento.get(1).toString(),elemento.get(2).toString(),elemento.get(3).toString(),elemento.get(4).toString(),elemento.get(5).toString()));
            // lista.add(elemento.get(0).toString()+"-"+ elemento.get(1).toString());
        }
        return lista;
    }

    @GetMapping("/compraReporte/p/{proveedor_id}") // REPORTE DE COMPRA CONDICIONADO POR PROVEEDOR
    public String reporteCompraProveedor(Model model,@PathVariable String proveedor_id) {
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);
        //boolean asignarRol = usuarioService.tienePermiso("asignar-rol-" + VIEW);
        List<Tuple> datosCompra = compraRepository.findComprasByProveedorNative(new Long(proveedor_id));
        List<ReporteCompraDTO> listaDatos = this.parsearDatosReporteCompra(datosCompra);

        Proveedor proveedor = proveedorRepository.findByIdProveedor(new Long(proveedor_id));

        Float total = new Float(0);
        for (ReporteCompraDTO compra : listaDatos) {
            total += Float.parseFloat(compra.getMontoTotal());
        }
        //java.time.LocalDate.now().toString()
        model.addAttribute("datos", listaDatos);
        model.addAttribute("cantidadDetalles", listaDatos.size());
        model.addAttribute("totalMonto", total);


        // para cabecera del reporte
        //título
        model.addAttribute("tRC","Reporte de compra por proveedor"); 
        model.addAttribute("tRU","");        
        model.addAttribute("tRCU","");
        model.addAttribute("tRF","");
        model.addAttribute("tRFC","");
        model.addAttribute("tRFU","");
        model.addAttribute("tRepAll","");

        //descripción del reporte
        model.addAttribute("dRC","Este reporte de compra se basa en todas las compras realizadas a determinado proveedor, el mismo cuenta con los siguientes parámetros:");        
        model.addAttribute("dRU",""); // título reporte estado
        model.addAttribute("dRCU",""); // título reporte estado y proveedor
        model.addAttribute("dRF",""); //título fecha
        model.addAttribute("dRFC",""); //título fecha y proveedor
        model.addAttribute("dRFU",""); //título fecha y estado
        model.addAttribute("dRAll","");  //título fecha, proveedor y estado

        //parámetros que serán utilizados para el reporte
        model.addAttribute("pCU", "Proveedor: " + proveedor.getNombre());
        model.addAttribute("pCU2",""); //estado  
        model.addAttribute("pDesHas","Desde: ");
        model.addAttribute("fI", "");
       
        model.addAttribute("pFechaEmision","Fecha emisión: "+java.time.LocalDate.now().toString());

        


        if(crear) {
            return FORM_NEW;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @GetMapping("/compraReporte/e/{estado}") // REPORTE DE COMPRA CONDICIONADO POR ESTADO
    public String reporteCompraEstado(Model model,@PathVariable String estado) {
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);
        //boolean asignarRol = usuarioService.tienePermiso("asignar-rol-" + VIEW);
        List<Tuple> datosCompra = compraRepository.findComprasByEstadoNative(estado);
        List<ReporteCompraDTO> listaDatos = this.parsearDatosReporteCompra(datosCompra);

        Float total = new Float(0);
        for (ReporteCompraDTO compra : listaDatos) {
            total += Float.parseFloat(compra.getMontoTotal());
        }
        //java.time.LocalDate.now().toString()
        model.addAttribute("datos", listaDatos);
        model.addAttribute("cantidadDetalles", listaDatos.size());
        model.addAttribute("totalMonto", total);

        // para cabecera del reporte
        //título
        model.addAttribute("tRC",""); 
        model.addAttribute("tRU","Reporte de compra por estado");        
        model.addAttribute("tRCU","");
        model.addAttribute("tRF","");
        model.addAttribute("tRFC","");
        model.addAttribute("tRFU","");
        model.addAttribute("tRepAll","");

        //descripción del reporte
        model.addAttribute("dRC","");        
        model.addAttribute("dRU","Este reporte de compra se basa en todas las compras realizadas basados en el estado seleccionado, el mismo cuenta con los siguientes parámetros:"); // título reporte estado
        model.addAttribute("dRCU",""); // título reporte estado y proveedor
        model.addAttribute("dRF",""); //título fecha
        model.addAttribute("dRFC",""); //título fecha y proveedor
        model.addAttribute("dRFU",""); //título fecha y estado
        model.addAttribute("dRAll","");  //título fecha, proveedor y estado

        //parámetros que serán utilizados para el reporte
        model.addAttribute("pCU", "");
        model.addAttribute("pCU2","Estado: " + estado); //estado  
        model.addAttribute("pDesHas","Desde: ");
        model.addAttribute("fI", "");
       
        model.addAttribute("pFechaEmision","Fecha emisión: "+java.time.LocalDate.now().toString());// fecha de emisión del reporte

        


        if(crear) {
            return FORM_NEW;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    

    @GetMapping("/compraReporte/estadoProveedor/{estado}/{proveedor_id}") // REPORTE DE COMPRA CONDICIONADO POR PROVEEDOR Y RANGO
    public String estadoProveedor(Model model,@PathVariable String estado,@PathVariable String proveedor_id) {
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);
        //boolean asignarRol = usuarioService.tienePermiso("asignar-rol-" + VIEW);
        List<Tuple> datosCompra = compraRepository.findComprasByEstadoProveedorNative(estado, new Long(proveedor_id));
        List<ReporteCompraDTO> listaDatos = this.parsearDatosReporteCompra(datosCompra);



        Float total = new Float(0);
        for (ReporteCompraDTO compra : listaDatos) {
            total += Float.parseFloat(compra.getMontoTotal());
        }

        Proveedor proveedor = proveedorRepository.findByIdProveedor(new Long(proveedor_id));
        //java.time.LocalDate.now().toString()
        model.addAttribute("datos", listaDatos);
        model.addAttribute("cantidadDetalles", listaDatos.size());
        model.addAttribute("totalMonto", total);

        // para cabecera del reporte
        //título
        model.addAttribute("tRC",""); //proveedor
        model.addAttribute("tRU",""); //estado  
        model.addAttribute("tRCU","Reporte de Compra por proveedor y Estado");
        model.addAttribute("tRF","");
        model.addAttribute("tRFC","");
        model.addAttribute("tRFU","");
        model.addAttribute("tRepAll","");

        //descripción del reporte
        model.addAttribute("dRC","");        
        model.addAttribute("dRU",""); // título reporte estado
        model.addAttribute("dRCU","Este reporte de compra se basa en todas las compras realizadas por el proveedor seleccionado, el mismo cuenta con los siguientes parámetros:"); // título reporte estado y proveedor
        model.addAttribute("dRF",""); //título fecha
        model.addAttribute("dRFC",""); //título fecha y proveedor
        model.addAttribute("dRFU",""); //título fecha y estado
        model.addAttribute("dRAll","");  //título fecha, proveedor y estado

        //parámetros que serán utilizados para el reporte
        model.addAttribute("pCU", "Proveedor: " + proveedor.getNombre());
        model.addAttribute("pCU2","Estado: " + estado); //estado  
        model.addAttribute("pDesHas","Desde: ");
        model.addAttribute("fI", "");
       
        model.addAttribute("pFechaEmision","Fecha emisión: "+java.time.LocalDate.now().toString());

        


        if(crear) {
            return FORM_NEW;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }


    @GetMapping("/compraReporte/rango/{fDesde}/{fHasta}") // REPORTE DE COMPRA CONDICIONADO POR ESTADO
    public String reporteCompraRango(Model model,@PathVariable String fDesde,@PathVariable String fHasta) {
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);
        //boolean asignarRol = usuarioService.tienePermiso("asignar-rol-" + VIEW);
        List<Tuple> datosCompra = compraRepository.findComprasByRangoNative(fDesde,fHasta);
        List<ReporteCompraDTO> listaDatos = this.parsearDatosReporteCompra(datosCompra);

        Float total = new Float(0);
        for (ReporteCompraDTO compra : listaDatos) {
            total += Float.parseFloat(compra.getMontoTotal());
        }
        //java.time.LocalDate.now().toString()
        model.addAttribute("datos", listaDatos);
        model.addAttribute("cantidadDetalles", listaDatos.size());
        model.addAttribute("totalMonto", total);
        // para cabecera del reporte
        //título
        model.addAttribute("tRC","");
        model.addAttribute("tRU","");        
        model.addAttribute("tRCU","");
        model.addAttribute("tRF","Reporte de compra por Fecha");
        model.addAttribute("tRFC","");
        model.addAttribute("tRFU","");
        model.addAttribute("tRepAll","");

        //descripción del reporte
        model.addAttribute("dRC","");  //descripción reporte proveedor      
        model.addAttribute("dRU",""); // descripción reporte estado
        model.addAttribute("dRCU",""); // descripción reporte estado y proveedor
        model.addAttribute("dRF","Este reporte de compra se basa en el rango de fechas seleccionadas, el mismo cuenta con los siguientes parámetros:"); //descripción fecha
        model.addAttribute("dRFC",""); //descripción fecha y cliente
        model.addAttribute("dRFU",""); //descripción fecha y usuario
        model.addAttribute("dRAll","");  //descripción fecha, cliente y usuario

        //parámetros que serán utilizados para el reporte
        model.addAttribute("pCU", "Cantidad de proveedores: ");
        model.addAttribute("pCU2","Cantidad de estados: "); //estado  
        model.addAttribute("pDesHas","Desde: ");
        model.addAttribute("fI", ""+ fDesde + " - " + fHasta+"");
       
        model.addAttribute("pFechaEmision","Fecha emisión: "+java.time.LocalDate.now().toString());

        


        if(crear) {
            return FORM_NEW;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @GetMapping("/compraReporte/rangoProv/{fDesde}/{fHasta}/{proveedor_id}") // REPORTE DE COMPRA CONDICIONADO POR PROVEEDOR Y RANGO
    public String reporteCompraRangoProveedor(Model model,@PathVariable String fDesde,@PathVariable String fHasta,@PathVariable String proveedor_id) {
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);
        //boolean asignarRol = usuarioService.tienePermiso("asignar-rol-" + VIEW);
        List<Tuple> datosCompra = compraRepository.findComprasByRangoProveedorNative(new Long(proveedor_id), fDesde,fHasta);
        List<ReporteCompraDTO> listaDatos = this.parsearDatosReporteCompra(datosCompra);

        Proveedor proveedor = proveedorRepository.findByIdProveedor(new Long(proveedor_id));

        Float total = new Float(0);
        for (ReporteCompraDTO compra : listaDatos) {
            total += Float.parseFloat(compra.getMontoTotal());
        }
        //java.time.LocalDate.now().toString()
        model.addAttribute("datos", listaDatos);
        model.addAttribute("cantidadDetalles", listaDatos.size());
        model.addAttribute("totalMonto", total);
        // para cabecera del reporte
        //título
        model.addAttribute("tRC","");
        model.addAttribute("tRU","");        
        model.addAttribute("tRCU","");
        model.addAttribute("tRF","");
        model.addAttribute("tRFC","Reporte de compra por Proveedor y Fecha seleccionada");
        model.addAttribute("tRFU","");
        model.addAttribute("tRepAll","");

        //descripción del reporte
        model.addAttribute("dRC","");        
        model.addAttribute("dRU",""); // título reporte estado
        model.addAttribute("dRCU",""); // título reporte estado y proveedor
        model.addAttribute("dRF",""); //título fecha
        model.addAttribute("dRFC","Este reporte de compra se basa en el rango de fechas seleccionadas con respecto al proveedor, el mismo cuenta con los siguientes parámetros:"); //título fecha y proveedor
        model.addAttribute("dRFU",""); //título fecha y estado
        model.addAttribute("dRAll","");  //título fecha, proveedor y estado

        //parámetros que serán utilizados para el reporte
        model.addAttribute("pCU", "Proveedor: " + proveedor.getNombre());
        model.addAttribute("pCU2", ""); //estado  
        model.addAttribute("pDesHas","");
        model.addAttribute("fI", ""+ fDesde + " - " + fHasta+"");
       
        model.addAttribute("pFechaEmision","Fecha emisión: "+java.time.LocalDate.now().toString());
      


        if(crear) {
            return FORM_NEW;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @GetMapping("/compraReporte/rangoEst/{fDesde}/{fHasta}/{estado}") // REPORTE DE COMPRA CONDICIONADO POR PROVEEDOR Y RANGO
    public String reporteCompraRangoEstado(Model model,@PathVariable String fDesde,@PathVariable String fHasta,@PathVariable String estado) {
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);
        //boolean asignarRol = usuarioService.tienePermiso("asignar-rol-" + VIEW);
        List<Tuple> datosCompra = compraRepository.findComprasByRangoEstadoNative(estado, fDesde,fHasta);
        List<ReporteCompraDTO> listaDatos = this.parsearDatosReporteCompra(datosCompra);

        Float total = new Float(0);
        for (ReporteCompraDTO compra : listaDatos) {
            total += Float.parseFloat(compra.getMontoTotal());
        }
        //java.time.LocalDate.now().toString()
        model.addAttribute("datos", listaDatos);
        model.addAttribute("cantidadDetalles", listaDatos.size());
        model.addAttribute("totalMonto", total);
        // para cabecera del reporte
        //título
        model.addAttribute("tRC","");
        model.addAttribute("tRU","");        
        model.addAttribute("tRCU","");
        model.addAttribute("tRF","");
        model.addAttribute("tRFC","");
        model.addAttribute("tRFU","Reporte de compra por estado y Fecha seleccionada");
        model.addAttribute("tRepAll","");

        //descripción del reporte
        model.addAttribute("dRC","");        
        model.addAttribute("dRU",""); // título reporte estado
        model.addAttribute("dRCU",""); // título reporte estado y proveedor
        model.addAttribute("dRF",""); //título fecha
        model.addAttribute("dRFC",""); //título fecha y proveedor
        model.addAttribute("dRFU","Este reporte de compra se basa en el rango de fechas seleccionadas con respecto al estado de las compras realizas, el mismo cuenta con los siguientes parámetros:"); //título fecha y estado
        model.addAttribute("dRAll","");  //título fecha, proveedor y estado

        //parámetros que serán utilizados para el reporte
        model.addAttribute("pCU", "");
        model.addAttribute("pCU2","Estado: " + estado); //estado  
        model.addAttribute("pDesHas","");
        model.addAttribute("fI", ""+ fDesde + " - " + fHasta+"");
       
        model.addAttribute("pFechaEmision","Fecha emisión: "+java.time.LocalDate.now().toString());

        


        if(crear) {
            return FORM_NEW;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }


    @GetMapping("/compraReporte/all/{fDesde}/{fHasta}/{estado}/{proveedor_id}") // REPORTE DE COMPRA CONDICIONADO POR PROVEEDOR Y RANGO
    public String reporteCompraAll(Model model,@PathVariable String fDesde,@PathVariable String fHasta,@PathVariable String estado,@PathVariable String proveedor_id) {
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);
        //boolean asignarRol = usuarioService.tienePermiso("asignar-rol-" + VIEW);
        List<Tuple> datosCompra = compraRepository.findVentasByRangoAllNative( new Long(proveedor_id), estado, fDesde,fHasta);
        List<ReporteCompraDTO> listaDatos = this.parsearDatosReporteCompra(datosCompra);

        Proveedor proveedor = proveedorRepository.findByIdProveedor(new Long(proveedor_id));


        Float total = new Float(0);
        for (ReporteCompraDTO compra : listaDatos) {
            total += Float.parseFloat(compra.getMontoTotal());
        }
        //java.time.LocalDate.now().toString()
        model.addAttribute("datos", listaDatos);
        model.addAttribute("cantidadDetalles", listaDatos.size());
        model.addAttribute("totalMonto", total);
        // para cabecera del reporte
        //título
        model.addAttribute("tRC","");
        model.addAttribute("tRU","");        
        model.addAttribute("tRCU","");
        model.addAttribute("tRF","");
        model.addAttribute("tRFC","");
        model.addAttribute("tRFU","");
        model.addAttribute("tRepAll","Reporte General de compra");

        //descripción del reporte
        model.addAttribute("dRF",""); //descripción fecha
        model.addAttribute("dRFC",""); //descripción fecha y cliente
        model.addAttribute("dRFU",""); //descripción fecha y usuario
        model.addAttribute("dRAll","Este reporte de compra se basa en el estado, proveedor y rango de fechas seleccionadas, es un reporte general, el mismo cuenta con los siguientes parámetros:");  //descripción fecha, cliente y usuario

        //parámetros que serán utilizados para el reporte
        model.addAttribute("pCU", "Proveedor: " + proveedor.getNombre());
        model.addAttribute("pCU2","Estado: " + estado); //estado  
        model.addAttribute("pDesHas","");
        model.addAttribute("fI", ""+ fDesde + " - " + fHasta+"");
       
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

    @GetMapping("/compraReportEsp/pC")
    public String reporteProductoCantidad(Model model) {
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);

        List<Tuple> datosCompra = compraRepository.findInformeProductoCantNative();

        List<ReporteVentaProductoDTO> listaDatos = this.parsearDatosReporteProducto(datosCompra);

        String username = SecurityContextHolder.getContext().getAuthentication().getName(); //Obtener datos del usuario logueado[Basico]
        Usuario usuario = usuarioRepository.findByEmail(username);// Obtener todos los datos del usuario 

 
        BigDecimal total = new BigDecimal(0);
        Integer cant = 0;
        for (ReporteVentaProductoDTO compra : listaDatos) {
            total = total.add(new BigDecimal(compra.getMonto()));
            cant ++;
        }
        model.addAttribute("datos", listaDatos);
        model.addAttribute("cantidadDetalles", listaDatos.size());
        model.addAttribute("totalMonto", total);
        model.addAttribute("cantItems", cant);



        // para cabecera del reporte
        //título
        model.addAttribute("tRProdCant"," Reporte de productos más comprados respecto a la cantidad");
        model.addAttribute("tRProdMont","");        

        //descripción del reporte
        model.addAttribute("dRProdCant","En este reporte se visualizan los 10 productos más comprados");        
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


    

    @GetMapping("/graficoDona")
    public String verReporte(Model model) {
        String[] meses = {"","Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
        this.operacion = "crear-";
        String username = SecurityContextHolder.getContext().getAuthentication().getName(); //Obtener datos del usuario logueado[Basico]
        Usuario usuario = usuarioRepository.findByEmail(username);// Obtener todos los datos del usuario 
        List<AperturaCaja> cajaApertura = aperturaCajaRepository.findByIdUsuarioOrderByIdAperturaCajaDesc(usuario.getIdUsuario());
        // List<Operacion> ultMov = operacionRepository.findByIdCajaOrderByIdOperacionDesc(cajaApertura.get(0).getIdCaja());
        List<Tuple> datosGrafico = compraRepository.findGraphNative();
        List<DatoGraficoVentaDTO> lista = new ArrayList<>();

        for (Tuple elemento : datosGrafico) {
            Integer temp = Integer.parseInt(elemento.get(0).toString().split("\\.")[0]);
            lista.add(new DatoGraficoVentaDTO(meses[temp==0?1:temp], elemento.get(1).toString()));
            // lista.add(elemento.get(0).toString()+"-"+ elemento.get(1).toString());
        }

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            model.addAttribute("titulos", "['Red', 'Orange', 'Yellow', 'Green', 'Blue']");
            model.addAttribute("datos", lista);

            return GRAFICO_ESTADISTICO;
        } else {
            return GRAFICO_ESTADISTICO;
        }
    }

    @GetMapping("/graficoDona/productos")
    public String verReporteProductos(Model model) {
        String[] meses = {"","Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
        this.operacion = "crear-";
        String username = SecurityContextHolder.getContext().getAuthentication().getName(); //Obtener datos del usuario logueado[Basico]
        Usuario usuario = usuarioRepository.findByEmail(username);// Obtener todos los datos del usuario 
        
        // List<Operacion> ultMov = operacionRepository.findByIdCajaOrderByIdOperacionDesc(cajaApertura.get(0).getIdCaja());
        List<Tuple> datosGrafico = compraRepository.findInformeProductoCantNative();
        List<DatoGraficoCompraDTO> lista = new ArrayList<>();
        int c = 0;
        for (Tuple elemento : datosGrafico) {
            if(c>=10){
                break;
            }
            lista.add(new DatoGraficoCompraDTO(elemento.get(1).toString(), elemento.get(2).toString().split("\\.")[0]));
            c++;
            // lista.add(elemento.get(0).toString()+"-"+ elemento.get(1).toString());
        }


        // para cabecera del reporte
        //título
        model.addAttribute("tGProducto","Ranking productos más comprados");
        model.addAttribute("tGProveedor","");

  

        //descripción del gráfico
        model.addAttribute("dGProducto"," El gráfico de donas representa los productos más comprados hasta la fecha, el mismo está basado en la cantidad"); // descripción gráfico producto
        model.addAttribute("dGProveedor",""); // descripción gráfico producto

        //parámetros que serán utilizados para el reporte
        model.addAttribute("pGCajero","Generado por: " + usuario.getUsername());
        model.addAttribute("pGProveedor","" );

        model.addAttribute("pFechaEmision","Fecha emisión: "+ java.time.LocalDate.now().toString());


        // RESUMEN DEL REPORTE
        model.addAttribute("resumen","Producto");
        model.addAttribute("resumen","");


        if(usuarioService.tienePermiso(operacion + VIEW)) {
            model.addAttribute("titulos", "['Red', 'Orange', 'Yellow', 'Green', 'Blue']");
            model.addAttribute("datos", lista);

            return GRAFICO_ESTADISTICO;
        } else {
            return GRAFICO_ESTADISTICO;
        }
    }

    @GetMapping("/graficoDona/proveedor")
    public String verReporteProveedor(Model model) {
        String[] meses = {"","Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
        this.operacion = "crear-";
        String username = SecurityContextHolder.getContext().getAuthentication().getName(); //Obtener datos del usuario logueado[Basico]
        Usuario usuario = usuarioRepository.findByEmail(username);// Obtener todos los datos del usuario 
        
        // List<Operacion> ultMov = operacionRepository.findByIdCajaOrderByIdOperacionDesc(cajaApertura.get(0).getIdCaja());
        List<Tuple> datosGrafico = compraRepository.findComprasByProveedorRankingNative();
        List<DatoGraficoCompraDTO> lista = new ArrayList<>();
        int c = 0;
        for (Tuple elemento : datosGrafico) {
            if(c>=10){
                break;
            }
            lista.add(new DatoGraficoCompraDTO(elemento.get(0).toString(), elemento.get(1).toString().split("\\.")[0]));
            c++;
            // lista.add(elemento.get(0).toString()+"-"+ elemento.get(1).toString());
        }


        // para cabecera del reporte
        //título
        model.addAttribute("tGProducto","");
        model.addAttribute("tGProveedor","Ranking proveedores");

  

        //descripción del gráfico
        model.addAttribute("dGProducto",""); // descripción gráfico producto
        model.addAttribute("dGProveedor"," El gráfico de donas representa a los proveedores de donde se compran los productos hasta la fecha, el mismo está basado en la cantidad"); // descripción gráfico producto

        //parámetros que serán utilizados para el reporte
        model.addAttribute("pGCajero","Generado por: " + usuario.getUsername());
        // model.addAttribute("pGProveedor","Proveedor: " + proveedor.getName() );

        model.addAttribute("pFechaEmision","Fecha emisión: "+ java.time.LocalDate.now().toString());


        // RESUMEN DEL REPORTE
        model.addAttribute("resumen","Producto");


        if(usuarioService.tienePermiso(operacion + VIEW)) {
            model.addAttribute("titulos", "['Red', 'Orange', 'Yellow', 'Green', 'Blue']");
            model.addAttribute("datos", lista);

            return GRAFICO_ESTADISTICO;
        } else {
            return GRAFICO_ESTADISTICO;
        }
    }

    @GetMapping("/compraReportEsp/historial")
    public String verHistorialCompras(Model model) {
        this.operacion = "crear-";
        String username = SecurityContextHolder.getContext().getAuthentication().getName(); //Obtener datos del usuario logueado[Basico]
        Usuario usuario = usuarioRepository.findByEmail(username);// Obtener todos los datos del usuario 
        
        // List<Operacion> ultMov = operacionRepository.findByIdCajaOrderByIdOperacionDesc(cajaApertura.get(0).getIdCaja());
        List<Tuple> datosCompra = compraRepository.findInformeHistorialNative();
        List<ReporteCompraDTO> listaDatos = this.parsearDatosReporteCompra(datosCompra);


        // para cabecera del reporte
        //título
        model.addAttribute("tHistorial","Historial de compras");


        //descripción del gráfico
        model.addAttribute("dHistorial"," Este reporte representa las últimas 10 compras realizadas hasta la fecha"); // descripción gráfico producto


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

}
