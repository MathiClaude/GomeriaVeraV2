package com.proyecto.is2.proyecto.controller;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.proyecto.is2.proyecto.controller.dto.ReporteCompraDTO;
import com.proyecto.is2.proyecto.controller.dto.ReporteStockDTO;
import com.proyecto.is2.proyecto.controller.dto.ReporteVentaDTO;
import com.proyecto.is2.proyecto.controller.dto.UsuarioDTO;
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
import com.proyecto.is2.proyecto.services.TipoProductoService;
import com.proyecto.is2.proyecto.services.ClienteServiceImp;
import com.proyecto.is2.proyecto.services.OperacionServiceImp;
import com.proyecto.is2.proyecto.repository.UsuarioRepository;
import com.proyecto.is2.proyecto.repository.AperturaCajaRepository;
import com.proyecto.is2.proyecto.repository.CajaRepository;
import com.proyecto.is2.proyecto.repository.ClienteRepository;
import com.proyecto.is2.proyecto.repository.OperacionRepository;
import com.proyecto.is2.proyecto.repository.ProductoRepository;
import com.proyecto.is2.proyecto.repository.ProveedorRepository;

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
@RequestMapping("/stock")
public class ReporteStockController {
    //final String VIEW = "reporte"; // identificador de la vista
    final String VIEW = "reporteStock"; // identificador de la vista que corresponde al permiso consulta-reporteStock

    final String VIEW_PATH = "reporte";
    String operacion = "";
    final String FORM_VIEW = VIEW_PATH + "/stock";
    final String FORM_NEW = VIEW_PATH + "/stockReporte";
    final String FORM_EDIT = VIEW_PATH + "/editar";
    final String RD_FORM_VIEW = "redirect:/stock";
    final String FALTA_PERMISO_VIEW = "falta-permiso";
    final String RD_FALTA_PERMISO_VIEW = "redirect:/" + FALTA_PERMISO_VIEW;
    final String ASIGNAR_ROL_VIEW = VIEW_PATH + "/asignar-rol";

    final String GRAFICO_ESTADISTICO = "reporte/stockReporte";
    final String REPORTE_ESPECIFICO = "reporte/stockReportEsp";
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
    TipoProductoService tipoProductoService;

    @Autowired
    AperturaCajaRepository aperturaCajaRepository;

    @Autowired
    ProveedorRepository proveedorRepository;

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
        //boolean crear = usuarioService.tienePermiso("crear-" + VIEW);
        //boolean eliminar = usuarioService.tienePermiso("eliminar-" + VIEW);
        //boolean actualizar = usuarioService.tienePermiso("actualizar-" + VIEW);
        //boolean seleccionar = usuarioService.tienePermiso("seleccionar-" + VIEW);



        if(consultar) {
            model.addAttribute("listProduct", productoService.listar());//lista los productos
            model.addAttribute("listServicio", servicioService.listar());//lista los productos
            model.addAttribute("listarCliente", clienteService.listar());//lista los clientes
            model.addAttribute("listarTipoProducto", tipoProductoService.listar());//lista los clientes
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
        //model.addAttribute("permisoCrear", crear);
        //model.addAttribute("permisoEliminar", eliminar);
        //model.addAttribute("permisoActualizar", actualizar);
        //model.addAttribute("permisoSeleccionar", seleccionar);
    

        return FORM_VIEW;
    }
    
    private List<ReporteStockDTO> parsearDatosReporteStock(List<Tuple> datosCrudo){        
        List<ReporteStockDTO> lista = new ArrayList<>();
        for (Tuple elemento : datosCrudo) {
            lista.add( new ReporteStockDTO(
                elemento.get(0).toString(),
                elemento.get(1).toString(),
                elemento.get(2).toString(),
                elemento.get(3).toString(),
                Integer.parseInt(elemento.get(4).toString()),
                elemento.get(5).toString(),
                new Float(elemento.get(6).toString())));
                // lista.add(
                // elemento.get(0).toString()+"-"+ elemento.get(1).toString());
        }
        return lista;
    }
    /*
    @GetMapping("/stockReporte")
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
    }*/

    @GetMapping("/stockReporte/p/{proveedor_id}") // REPORTE DE COMPRA CONDICIONADO POR PROVEEDOR
    public String reporteStockProveedor(Model model,@PathVariable String proveedor_id) {
        boolean consultar = usuarioService.tienePermiso("consultar-" + VIEW);
        //boolean asignarRol = usuarioService.tienePermiso("asignar-rol-" + VIEW);
        List<Tuple> datosStock = productoRepository.findStockByProveedorNative(new Long(proveedor_id));

        List<ReporteStockDTO> listaDatos = this.parsearDatosReporteStock(datosStock);


        String username = SecurityContextHolder.getContext().getAuthentication().getName(); //Obtener datos del usuario logueado[Basico]
        Usuario usuario = usuarioRepository.findByEmail(username);// Obtener todos los datos del usuario 

        Proveedor proveedor = proveedorRepository.findByIdProveedor(new Long(proveedor_id));

        Float total = new Float(0);
        Integer cant= 0;
        for (ReporteStockDTO compra : listaDatos) {
            total += compra.getPrecio();
            cant+= compra.getCantidad();
        }
        //java.time.LocalDate.now().toString()
        model.addAttribute("datos", listaDatos);
        model.addAttribute("cant", cant);
        //java.time.LocalDate.now().toString()
        model.addAttribute("datos", listaDatos);
        model.addAttribute("cantidadDetalles", listaDatos.size());
        model.addAttribute("totalMonto", total);


        // para cabecera del reporte
        //título
        model.addAttribute("tRPro","Reporte de stock por proveedor"); 
        model.addAttribute("tRProd","");   
        model.addAttribute("tRProdu","");      
        model.addAttribute("tRProduc","");        


        //descripción del reporte
        model.addAttribute("dRPro","Este reporte de stock se basa en los proveedores, el mismo cuenta con los siguientes parámetros:");        
        model.addAttribute("dRProd",""); // título reporte tipoProducto
        model.addAttribute("dRProdu",""); // título reporte tipoProducto
        model.addAttribute("dRProduc","");        

       

        //parámetros que serán utilizados para el reporte
       model.addAttribute("pUser", "Generado por: " + usuario.getUsername()); 
       
       model.addAttribute("pProveedor", "Proveedor: " + proveedor.getNombre());
       model.addAttribute("pTiPro", "");
       model.addAttribute("pFechaEmision","Fecha emisión: "+java.time.LocalDate.now().toString());

        


        if(consultar) {
            return FORM_NEW;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }
    
    @GetMapping("/stockReporte/tP/{id_tipoProducto}") // REPORTE DE STOCK CONDICIONADO POR TIPO DE PRODUCTO
    public String reporteStockTP(Model model,@PathVariable String id_tipoProducto) {
        boolean consultar = usuarioService.tienePermiso("consultar-" + VIEW);
        //boolean asignarRol = usuarioService.tienePermiso("asignar-rol-" + VIEW);
        List<Tuple> datosCompra = productoRepository.findStockByTipoProNative(new Long(id_tipoProducto));
        List<ReporteStockDTO> listaDatos = this.parsearDatosReporteStock(datosCompra);

        String username = SecurityContextHolder.getContext().getAuthentication().getName(); //Obtener datos del usuario logueado[Basico]
        Usuario usuario = usuarioRepository.findByEmail(username);// Obtener todos los datos del usuario 

        Float total = new Float(0);
        Integer cant= 0;
        for (ReporteStockDTO compra : listaDatos) {
            total += compra.getPrecio();
            cant+= compra.getCantidad();
        }
        //java.time.LocalDate.now().toString()
        model.addAttribute("datos", listaDatos);
        model.addAttribute("cant", cant);
        model.addAttribute("totalMonto", total);

        // para cabecera del reporte
        //título
        model.addAttribute("tRPro",""); 
        model.addAttribute("tRProd","Reporte de stock por tipo de producto");        
        model.addAttribute("tRProdu","");
        model.addAttribute("tRProduc","");
        



        //descripción del reporte
        model.addAttribute("dRPro","");        
        model.addAttribute("dRProd","Este reporte de stock se basa en los tipo de productos, el mismo cuenta con los siguientes parámetros:"); // título reporte tipoProducto
        model.addAttribute("dRProdu","");  
        model.addAttribute("dRProduc","");        
      

       

       //parámetros que serán utilizados para el reporte
       model.addAttribute("pUser", "Generado por: " + usuario.getUsername()); 
       
       model.addAttribute("pProveedor", "");
       model.addAttribute("pTiPro", "" ); //falta
       model.addAttribute("pFechaEmision","Fecha emisión: "+java.time.LocalDate.now().toString());

        


        if(consultar) {
            return FORM_NEW;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }


    @GetMapping("/stockReporte/tProdProv/{id_tipoProducto}/{proveedor_id}") // REPORTE DE COMPRA CONDICIONADO POR ESTADO
    public String reporteCompraAll(Model model,@PathVariable String id_tipoProducto, @PathVariable String proveedor_id) {
        boolean consultar = usuarioService.tienePermiso("consultar-" + VIEW);
        //boolean asignarRol = usuarioService.tienePermiso("asignar-rol-" + VIEW);
        List<Tuple> datosCompra = productoRepository.findStockByTipoProProveedorNative(new Long(id_tipoProducto),new Long(proveedor_id));
        List<ReporteStockDTO> listaDatos = this.parsearDatosReporteStock(datosCompra);

        String username = SecurityContextHolder.getContext().getAuthentication().getName(); //Obtener datos del usuario logueado[Basico]
        Usuario usuario = usuarioRepository.findByEmail(username);// Obtener todos los datos del usuario 

        Proveedor proveedor = proveedorRepository.findByIdProveedor(new Long(proveedor_id));

        Float total = new Float(0);
        Integer cant= 0;
        for (ReporteStockDTO compra : listaDatos) {
            total += compra.getPrecio();
            cant+= compra.getCantidad();
        }
        //java.time.LocalDate.now().toString()
        model.addAttribute("datos", listaDatos);
        model.addAttribute("cant", cant);
        model.addAttribute("totalMonto", total);

        // para cabecera del reporte
        //título
        model.addAttribute("tRPro",""); 
        model.addAttribute("tRProd","");        
        model.addAttribute("tRProdu","");
        model.addAttribute("tRProduc","Reporte de stock por tipo producto y proveedor");        
        
        //descripción del reporte
        model.addAttribute("dRPro","");        
        model.addAttribute("dRProd",""); // título reporte tipoProducto
        model.addAttribute("dRProdu",""); 
        model.addAttribute("dRProduc","En este reporte se visualiza los productos en stock basado al tipo de producto y el proveedor"); 


       

       //parámetros que serán utilizados para el reporte
       model.addAttribute("pUser", "Generado por: " + usuario.getUsername()); 
       
       model.addAttribute("pProveedor", "");
       model.addAttribute("pTiPro", "" ); //falta
       model.addAttribute("pFechaEmision","Fecha emisión: "+java.time.LocalDate.now().toString());


        


        if(consultar) {
            return FORM_NEW;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @GetMapping("/stockRepEsp/cantMinima")
    public String reporteCantidadMinima(Model model) {
        boolean consultar = usuarioService.tienePermiso("consultar-" + VIEW);
        
        List<Tuple> datosStock = productoRepository.findStockByCantMinimaNative();

        List<ReporteStockDTO> listaDatos = this.parsearDatosReporteStock(datosStock);

        String username = SecurityContextHolder.getContext().getAuthentication().getName(); //Obtener datos del usuario logueado[Basico]
        Usuario usuario = usuarioRepository.findByEmail(username);// Obtener todos los datos del usuario 

 
        BigDecimal total = new BigDecimal(0);
        Integer cant = 0;
        for (ReporteStockDTO prod : listaDatos) {
            total = total.add( new BigDecimal(prod.getPrecio()));
            cant += prod.getCantidad();
        }
        model.addAttribute("datos", listaDatos);
        model.addAttribute("cantidadDetalles", listaDatos.size());
        model.addAttribute("totalMonto", total);
        model.addAttribute("cant", cant);



        // para cabecera del reporte
        //título
        model.addAttribute("tRPro",""); 
        model.addAttribute("tRProd","");
        model.addAttribute("tRProdu","Reporte de stock con cantidad mínima");        
        model.addAttribute("tRProduc","");



        //descripción del reporte
        model.addAttribute("dRPro","");        
        model.addAttribute("dRProd",""); // título reporte tipoProducto
        model.addAttribute("dRProdu","Este reporte de stock se basa en los productos con cantidad por debajo del mínimo"); // título reporte especifico cantidad mínima
        model.addAttribute("dRProduc",""); // título reporte tipoProducto

       

       //parámetros que serán utilizados para el reporte
       model.addAttribute("pUser", ""); 
       
       model.addAttribute("pProveedor", "");

       model.addAttribute("pTiPro", "" ); //falta
       model.addAttribute("pFechaEmision","Fecha emisión: "+ java.time.LocalDate.now().toString());


        if(consultar) {
            return FORM_NEW;
        } else {
            return FORM_NEW;
        }
    }
    @GetMapping("/stockRepEsp/listado")
    public String reporteProductoCantidad(Model model) {
        boolean consultar = usuarioService.tienePermiso("consultar-" + VIEW);
        
        List<Producto> datosStock = productoRepository.findAll();

        String username = SecurityContextHolder.getContext().getAuthentication().getName(); //Obtener datos del usuario logueado[Basico]
        Usuario usuario = usuarioRepository.findByEmail(username);// Obtener todos los datos del usuario 

 
        BigDecimal total = new BigDecimal(0);
        Integer cant = 0;
        for (Producto prod : datosStock) {
            total = total.add( new BigDecimal(prod.getPrecio()));
            cant += prod.getCantidad();
        }
        model.addAttribute("datos", datosStock);
        model.addAttribute("cantidadDetalles", datosStock.size());
        model.addAttribute("totalMonto", total);
        model.addAttribute("cant", cant);



        // para cabecera del reporte
        //título
     
        model.addAttribute("tRPro2","Reporte General de stock");

        

        //descripción del reporte
       
        model.addAttribute("dRPro2","En este reporte se visualizará un listado completo del stock actual");          
        
        

        //parámetros que serán utilizados para el reporte
        model.addAttribute("pUsuarioRepor", "Generado por: " + usuario.getUsername());       
        model.addAttribute("pFechaEmision","Fecha emisión: "+ java.time.LocalDate.now().toString());

        if(consultar) {
            return REPORTE_ESPECIFICO;
        } else {
            return REPORTE_ESPECIFICO;
        }
    }




}
