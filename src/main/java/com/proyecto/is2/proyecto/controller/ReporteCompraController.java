package com.proyecto.is2.proyecto.controller;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.proyecto.is2.proyecto.controller.dto.UsuarioDTO;
import com.proyecto.is2.proyecto.controller.dto.DatoGraficoVentaDTO;
import com.proyecto.is2.proyecto.controller.dto.ReporteCompraDTO;
import com.proyecto.is2.proyecto.controller.dto.ReporteVentaDTO;
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
    VentaDetalleService ventaDetalleService;

    @Autowired
    AperturaCajaRepository aperturaCajaRepository;

    @Autowired
    CajaRepository cajaRepository;

    @Autowired
    OperacionRepository operacionRepository;

    @Autowired
    CompraRepository ventaRepository;

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



        if(consultar) {
            model.addAttribute("listProduct", productoService.listar());//lista los productos
            model.addAttribute("listServicio", servicioService.listar());//lista los productos
            model.addAttribute("listarCliente", clienteService.listar());//lista los clientes
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
            lista.add( new ReporteCompraDTO(elemento.get(0).toString(),elemento.get(1).toString(),elemento.get(2).toString(),elemento.get(3).toString()) );
            // lista.add(elemento.get(0).toString()+"-"+ elemento.get(1).toString());
        }
        return lista;
    }

    @GetMapping("/compraReporte/{proveedor_id}")
    public String reporteCompraProveedor(Model model,@PathVariable String proveedor_id) {
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);
        //boolean asignarRol = usuarioService.tienePermiso("asignar-rol-" + VIEW);
        List<Tuple> datosCompra = ventaRepository.findComprasByProveedorNative(new Long(proveedor_id));
        List<ReporteCompraDTO> listaDatos = this.parsearDatosReporteCompra(datosCompra);

        Cliente cliente = clienteRepository.findByIdCliente(new Long(proveedor_id));

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
        model.addAttribute("tRC","Reporte de Compra por proveedor");
        model.addAttribute("tRU","");        
        model.addAttribute("tRCU","");
        model.addAttribute("tRF","");
        model.addAttribute("tRFC","");
        model.addAttribute("tRFU","");
        model.addAttribute("tRepAll","");

        //descripción del reporte
        model.addAttribute("dRC","Este reporte de compra se basa en todas las ventas realizadas al cliente seleccionado, el mismo cuenta con los siguientes parámetros:");        
        model.addAttribute("dRU",""); // título reporte usuario
        model.addAttribute("dRCU",""); // título reporte usuario y cliente
        model.addAttribute("dRF",""); //título fecha
        model.addAttribute("dRFC",""); //título fecha y cliente
        model.addAttribute("dRFU",""); //título fecha y usuario
        model.addAttribute("dRAll","");  //título fecha, cliente y usuario

        //parámetros que serán utilizados para el reporte
        model.addAttribute("pCU","Cliente: " + cliente.getName() +" "+cliente.getLastName() );
        model.addAttribute("pCU2","");
        model.addAttribute("pDesHas","Desde: ");
        model.addAttribute("fI","");
       
        model.addAttribute("pFechaEmision","Fecha emisión: "+java.time.LocalDate.now().toString());

        


        if(crear) {
            return FORM_NEW;
        } else {
            return FALTA_PERMISO_VIEW;
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

    @PostMapping("/crear")
    public String crearObjeto(@ModelAttribute("venta") VentaDTO objetoDTO,
            @RequestParam(value="ventaDetalle") String ventaDetalle,
                              RedirectAttributes attributes) {
        this.operacion = "crear-";
        String username = SecurityContextHolder.getContext().getAuthentication().getName(); //Obtener datos del usuario logueado[Basico]
        Usuario usuario = usuarioRepository.findByEmail(username);// Obtener todos los datos del usuario 
        List<AperturaCaja> cajaApertura = aperturaCajaRepository.findByIdUsuarioOrderByIdAperturaCajaDesc(usuario.getIdUsuario());
        List<Operacion> ultMov = operacionRepository.findByIdCajaOrderByIdOperacionDesc(cajaApertura.get(0).getIdCaja());
        BigDecimal montoVenta = new BigDecimal(objetoDTO.getMontoVenta());

        String[] arrVentaDetalle = ventaDetalle.split("\\|");

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            Venta venta = new Venta();
            Cliente cliente = clienteRepository.findByIdCliente(objetoDTO.getIdCliente());
            venta.setCliente(cliente);
            venta.setMontoTotal(montoVenta);
            venta.setMontoVenta(montoVenta.toString());

            //GUARDAR LA VENTA
            Venta nuevaVenta = ventaService.guardar(venta);
            // OBTENER EL ID DE LA VENTA 
            Long idVenta = nuevaVenta.getIdVenta();
            
            for (String detCrudo : arrVentaDetalle) {
                String[] elementos= detCrudo.split(";");
                Optional<Producto>  prod = productoRepository.findById(Long.parseLong(elementos[1]));

                VentaDetalle vtaDet = new VentaDetalle();
                vtaDet.setVenta(nuevaVenta);
                vtaDet.setCantidad(new Float(elementos[0]));
                vtaDet.setProducto(prod.get());
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

            attributes.addFlashAttribute("message", "¡Venta creada exitosamente!");

            return RD_FORM_VIEW+"/a"+arrVentaDetalle.length+"-e";
        } else {
            return RD_FALTA_PERMISO_VIEW;
        }
    }

    

}
