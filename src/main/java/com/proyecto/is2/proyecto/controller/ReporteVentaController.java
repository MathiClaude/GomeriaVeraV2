package com.proyecto.is2.proyecto.controller;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.proyecto.is2.proyecto.controller.dto.DatoGraficoVentaDTO;
import com.proyecto.is2.proyecto.controller.dto.ParametrosDonaDTO;
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
@RequestMapping("/venta")
public class ReporteVentaController {
    final String VIEW = "reporte"; // identificador de la vista
    final String VIEW_PATH = "reporte";
    String operacion = "";
    final String FORM_VIEW = VIEW_PATH + "/venta";
    final String FORM_NEW = VIEW_PATH + "/ventaReporte";
    final String FORM_EDIT = VIEW_PATH + "/editar";
    final String RD_FORM_VIEW = "redirect:/venta";
    final String FALTA_PERMISO_VIEW = "falta-permiso";
    final String RD_FALTA_PERMISO_VIEW = "redirect:/" + FALTA_PERMISO_VIEW;
    final String ASIGNAR_ROL_VIEW = VIEW_PATH + "/asignar-rol";
    
    final String GRAFICO_ESTADISTICO = "/reporte/ventaChart";
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

    @GetMapping("/ventaReporte/c/{cliente_id}")
    public String reporteVentaCliente(Model model,@PathVariable String cliente_id) {
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);
        //boolean asignarRol = usuarioService.tienePermiso("asignar-rol-" + VIEW);
        List<Tuple> datosVenta = ventaRepository.findVentasByClienteNative(new Long(cliente_id));
        List<ReporteVentaDTO> listaDatos = this.parsearDatosReporteVenta(datosVenta);
        Float total = new Float(0);
        for (ReporteVentaDTO venta : listaDatos) {
            total += Float.parseFloat(venta.getMontoTotal());
        }
        //java.time.LocalDate.now().toString()
        model.addAttribute("datos", listaDatos);
        model.addAttribute("cantidadDetalles", listaDatos.size());
        model.addAttribute("totalMonto", total);
        // para cabecera del reporte
        model.addAttribute("tituloReporte","Reporte de venta por cliente");
        model.addAttribute("descripcionReporte","Este reporte de venta se basa en todas las ventas realizadas al cliente seleccionado, el mismo cuenta con los siguientes parámetros:");
        model.addAttribute("pCU","Cliente: ");
        model.addAttribute("pCU","");
        model.addAttribute("pDesHas","Desde: ");
        model.addAttribute("pFechaEmision","Fecha emisión: ");

        


        if(crear) {
            return FORM_NEW;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @GetMapping("/ventaReporte/u/{usuario_id}")
    public String reporteVentaUsuario(Model model,@PathVariable String usuario_id) {
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);
        //boolean asignarRol = usuarioService.tienePermiso("asignar-rol-" + VIEW);

        List<Tuple> datosVenta = ventaRepository.findVentasByUsuarioNative(new Long(usuario_id));
        List<ReporteVentaDTO> listaDatos = this.parsearDatosReporteVenta(datosVenta);
        model.addAttribute("datos", listaDatos);
        Float total = new Float(0);
        for (ReporteVentaDTO venta : listaDatos) {
            total += Float.parseFloat(venta.getMontoTotal());
        }
        model.addAttribute("datos", listaDatos);
        model.addAttribute("cantidadDetalles", listaDatos.size());
        model.addAttribute("totalMonto", total);

        // para cabecera del reporte
        model.addAttribute("tituloReporte","Reporte de venta por vendedor");
        model.addAttribute("descripcionReporte","Este reporte de venta se basa en todas las ventas realizadas por el vendedor/cajero seleccionado, el mismo cuenta con los siguientes parámetros:");
        model.addAttribute("pCU","Vendedor: ");
        model.addAttribute("pCU","");
        model.addAttribute("pDesHas","Desde: ");
        model.addAttribute("pFechaEmision","Fecha emisión: ");

        if(crear) {
            return FORM_NEW;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @GetMapping("/ventaReporte/usuarioCliente/{usuario_id}/{cliente_id}")
    public String reporteVentaUsuarioCliente(Model model,@PathVariable String usuario_id, @PathVariable String cliente_id) {
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);
        //boolean asignarRol = usuarioService.tienePermiso("asignar-rol-" + VIEW);

        List<Tuple> datosVenta = ventaRepository.findVentasUsuarioClienteByNative(new Long(usuario_id), new Long(cliente_id));
        List<ReporteVentaDTO> listaDatos = this.parsearDatosReporteVenta(datosVenta);
        Float total = new Float(0);
        for (ReporteVentaDTO venta : listaDatos) {
            total += Float.parseFloat(venta.getMontoTotal());
        }
        model.addAttribute("datos", listaDatos);
        model.addAttribute("cantidadDetalles", listaDatos.size());
        model.addAttribute("totalMonto", total);

        // para cabecera del reporte
        model.addAttribute("tituloReporte","Reporte de venta por vendedor");
        model.addAttribute("descripcionReporte","Este reporte de venta se basa en todas las ventas realizadas por el vendedor/cajero seleccionado para con el cliente respectivamente, el mismo cuenta con los siguientes parámetros:");
        model.addAttribute("pCU","Vendedor: ");
        model.addAttribute("pCU2","Cliente: ");
        model.addAttribute("pDesHas","Desde: ");
        model.addAttribute("pFechaEmision","Fecha emisión: ");


        if(crear) {
            return FORM_NEW;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @GetMapping("/ventaReporte/rangoFecha/{fechaDesde}/{fechaHasta}")
    public String reporteVentaFecha(Model model,@PathVariable String fechaDesde, @PathVariable String fechaHasta) {
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);

        List<Tuple> datosVenta = ventaRepository.findVentasByRangoNative(fechaDesde,fechaHasta);

        List<ReporteVentaDTO> listaDatos = this.parsearDatosReporteVenta(datosVenta);

        Float total = new Float(0);
        for (ReporteVentaDTO venta : listaDatos) {
            total += Float.parseFloat(venta.getMontoTotal());
        }
        model.addAttribute("datos", listaDatos);
        model.addAttribute("cantidadDetalles", listaDatos.size());
        model.addAttribute("totalMonto", total);

        // para cabecera del reporte
        model.addAttribute("tituloReporte","Reporte de venta por Fecha");
        model.addAttribute("descripcionReporte","Este reporte de venta se basa en el rango de fechas seleccionadas, el mismo cuenta con los siguientes parámetros:");
        model.addAttribute("pCU","");
        model.addAttribute("pCU2","");
        model.addAttribute("pDesHas","");
        model.addAttribute("fI","Fecha Inicial: ");
        model.addAttribute("fF","Fecha Fin: ");
        model.addAttribute("pFechaEmision","Fecha emisión: ");


        if(crear) {
            return FORM_NEW;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @GetMapping("/ventaReporte/rangoCliente/{fechaDesde}/{fechaHasta}/{cliente_id}")
    public String reporteVentaFechaCliente(Model model, @PathVariable String fechaDesde,@PathVariable String fechaHasta, @PathVariable String cliente_id) {
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);

        List<Tuple> datosVenta = ventaRepository.findVentasByRangoClienteNative(new Long(cliente_id), fechaDesde, fechaHasta);

        List<ReporteVentaDTO> listaDatos = this.parsearDatosReporteVenta(datosVenta);

        Float total = new Float(0);
        for (ReporteVentaDTO venta : listaDatos) {
            total += Float.parseFloat(venta.getMontoTotal());
        }
        model.addAttribute("datos", listaDatos);
        model.addAttribute("cantidadDetalles", listaDatos.size());
        model.addAttribute("totalMonto", total);

       // para cabecera del reporte
        model.addAttribute("tituloReporte","Reporte de venta por Cliente y Fecha seleccionada");
        model.addAttribute("descripcionReporte","Este reporte de venta se basa en el rango de fechas seleccionadas con respecto al cliente, el mismo cuenta con los siguientes parámetros:");
        model.addAttribute("pCU","Cliente: ");
        model.addAttribute("pCU2","");
        model.addAttribute("pDesHas","");
        model.addAttribute("fI","Fecha Inicial: ");
        model.addAttribute("fF","Fecha Fin: ");
        model.addAttribute("pFechaEmision","Fecha emisión: ");



        if(crear) {
            return FORM_NEW;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @GetMapping("/ventaReporte/rangoUsuario/{fechaDesde}/{fechaHasta}/{usuario_id}")
    public String reporteVentaFechaUsuario(Model model,@PathVariable String usuario_id, @PathVariable String fechaDesde, @PathVariable String fechaHasta) {
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);

        List<Tuple> datosVenta = ventaRepository.findVentasByRangoUsuarioNative(new Long(usuario_id), fechaDesde, fechaHasta);

        List<ReporteVentaDTO> listaDatos = this.parsearDatosReporteVenta(datosVenta);

        Float total = new Float(0);
        for (ReporteVentaDTO venta : listaDatos) {
            total += Float.parseFloat(venta.getMontoTotal());
        }
        model.addAttribute("datos", listaDatos);
        model.addAttribute("cantidadDetalles", listaDatos.size());
        model.addAttribute("totalMonto", total);

        // para cabecera del reporte
        model.addAttribute("tituloReporte","Reporte de venta por Vendedor y Fecha seleccionada");
        model.addAttribute("descripcionReporte","Este reporte de venta se basa en el rango de fechas seleccionadas con respecto al vendedor, el mismo cuenta con los siguientes parámetros:");
        model.addAttribute("pCU","");
        model.addAttribute("pCU2","Vendedor:");
        model.addAttribute("pDesHas","");
        model.addAttribute("fI","Fecha Inicial: ");
        model.addAttribute("fF","Fecha Fin: ");
        model.addAttribute("pFechaEmision","Fecha emisión: ");

        if(crear) {
            return FORM_NEW;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @GetMapping("/ventaReporte/rangoAll/{fechaDesde}/{fechaHasta}/{usuario_id}/{cliente_id}")
    public String reporteVentaRangoAll(Model model,@PathVariable String usuario_id, @PathVariable String cliente_id,@PathVariable String fechaDesde, @PathVariable String fechaHasta) {
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);

        List<Tuple> datosVenta = ventaRepository.findVentasByRangoAllNative(new Long(usuario_id), new Long(cliente_id),fechaDesde, fechaHasta);

        List<ReporteVentaDTO> listaDatos = this.parsearDatosReporteVenta(datosVenta);

        Float total = new Float(0);
        for (ReporteVentaDTO venta : listaDatos) {
            total += Float.parseFloat(venta.getMontoTotal());
        }
        model.addAttribute("datos", listaDatos);
        model.addAttribute("cantidadDetalles", listaDatos.size());
        model.addAttribute("totalMonto", total);
        // para cabecera del reporte
        model.addAttribute("tituloReporte","Reporte General de venta");
        model.addAttribute("descripcionReporte","Este reporte de venta se basa en el vendedor, cliente y rango de fecha seleccionados, sería un reporte completo de acuerdo a lo seleccionado, el mismo cuenta con los siguientes parámetros:");
        model.addAttribute("pCU","Cliente: ");
        model.addAttribute("pCU2","Vendedor:");
        model.addAttribute("pDesHas","");
        model.addAttribute("fI","Fecha Inicial: ");
        model.addAttribute("fF","Fecha Fin: ");
        model.addAttribute("pFechaEmision","Fecha emisión: ");


        if(crear) {
            return FORM_NEW;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }
    
    private List<ReporteVentaDTO> parsearDatosReporteVenta(List<Tuple> datosCrudo){
        List<ReporteVentaDTO> lista = new ArrayList<>();
        for (Tuple elemento : datosCrudo) {
            lista.add( new ReporteVentaDTO(elemento.get(0).toString(),elemento.get(1).toString(),elemento.get(2).toString(),elemento.get(3).toString()) );
            // lista.add(elemento.get(0).toString()+"-"+ elemento.get(1).toString());
        }
        return lista;
    }



    @GetMapping("/graficoDona")
    public String verReporte(Model model) {
        String[] meses = {"","Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
        this.operacion = "crear-";
        String username = SecurityContextHolder.getContext().getAuthentication().getName(); //Obtener datos del usuario logueado[Basico]
        Usuario usuario = usuarioRepository.findByEmail(username);// Obtener todos los datos del usuario 
        List<AperturaCaja> cajaApertura = aperturaCajaRepository.findByIdUsuarioOrderByIdAperturaCajaDesc(usuario.getIdUsuario());
        // List<Operacion> ultMov = operacionRepository.findByIdCajaOrderByIdOperacionDesc(cajaApertura.get(0).getIdCaja());
        List<Tuple> datosGrafico = ventaRepository.findGraphNative();
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

    @GetMapping("/graficoDona/cajeros")
    public String verReporteCajeros(Model model) {
        String[] meses = {"","Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
        this.operacion = "crear-";
        String username = SecurityContextHolder.getContext().getAuthentication().getName(); //Obtener datos del usuario logueado[Basico]
        Usuario usuario = usuarioRepository.findByEmail(username);// Obtener todos los datos del usuario 
        List<AperturaCaja> cajaApertura = aperturaCajaRepository.findByIdUsuarioOrderByIdAperturaCajaDesc(usuario.getIdUsuario());
        // List<Operacion> ultMov = operacionRepository.findByIdCajaOrderByIdOperacionDesc(cajaApertura.get(0).getIdCaja());
        List<Tuple> datosGrafico = ventaRepository.findGraphCajeroNative();
        List<DatoGraficoVentaDTO> lista = new ArrayList<>();

        for (Tuple elemento : datosGrafico) {
            lista.add(new DatoGraficoVentaDTO(elemento.get(0).toString(), elemento.get(1).toString().split("\\.")[0] ));
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

    @GetMapping("/graficoDona/clientes")
    public String verReporteClientes(Model model) {
        String[] meses = {"","Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
        this.operacion = "crear-";
        String username = SecurityContextHolder.getContext().getAuthentication().getName(); //Obtener datos del usuario logueado[Basico]
        Usuario usuario = usuarioRepository.findByEmail(username);// Obtener todos los datos del usuario 
        
        // List<Operacion> ultMov = operacionRepository.findByIdCajaOrderByIdOperacionDesc(cajaApertura.get(0).getIdCaja());
        List<Tuple> datosGrafico = ventaRepository.findGraphClienteNative();
        List<DatoGraficoVentaDTO> lista = new ArrayList<>();

        for (Tuple elemento : datosGrafico) {
            lista.add(new DatoGraficoVentaDTO(elemento.get(0).toString(), elemento.get(1).toString().split("\\.")[0]));
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
        List<Tuple> datosGrafico = ventaRepository.findGraphProductoNative();
        List<DatoGraficoVentaDTO> lista = new ArrayList<>();

        for (Tuple elemento : datosGrafico) {
            lista.add(new DatoGraficoVentaDTO(elemento.get(0).toString(), elemento.get(1).toString().split("\\.")[0]));
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


    

}
