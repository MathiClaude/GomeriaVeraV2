package com.proyecto.is2.proyecto.controller;
import com.proyecto.is2.proyecto.controller.dto.UsuarioDTO;
import com.proyecto.is2.proyecto.controller.dto.CompraDTO;
import com.proyecto.is2.proyecto.controller.dto.CompraDTO;
import com.proyecto.is2.proyecto.model.Rol;
import com.proyecto.is2.proyecto.model.Usuario;
import com.proyecto.is2.proyecto.model.Compra;
import com.proyecto.is2.proyecto.model.CompraDetalle;
import com.proyecto.is2.proyecto.model.Factura;
import com.proyecto.is2.proyecto.repository.AperturaCajaRepository;
import com.proyecto.is2.proyecto.repository.CajaRepository;
import com.proyecto.is2.proyecto.repository.ClienteRepository;
import com.proyecto.is2.proyecto.repository.FacturaRepository;
import com.proyecto.is2.proyecto.repository.OperacionRepository;
import com.proyecto.is2.proyecto.repository.ProductoRepository;
import com.proyecto.is2.proyecto.repository.ProveedorRepository;
import com.proyecto.is2.proyecto.model.Servicio;
import com.proyecto.is2.proyecto.model.Proveedor;
import com.proyecto.is2.proyecto.model.AperturaCaja;
import com.proyecto.is2.proyecto.model.Caja;
import com.proyecto.is2.proyecto.model.Cliente;
import com.proyecto.is2.proyecto.model.Compra;
import com.proyecto.is2.proyecto.model.Operacion;
import com.proyecto.is2.proyecto.model.Producto;
import com.proyecto.is2.proyecto.Util.ModelAttributes;

import com.proyecto.is2.proyecto.services.RolServiceImp;
import com.proyecto.is2.proyecto.services.ProveedorServiceImp;
import com.proyecto.is2.proyecto.services.ProductoServiceImp;
import com.proyecto.is2.proyecto.services.AperturaCajaServiceImp;

import com.proyecto.is2.proyecto.repository.UsuarioRepository;
import com.proyecto.is2.proyecto.repository.AperturaCajaRepository;
import com.proyecto.is2.proyecto.repository.CajaRepository;


import com.proyecto.is2.proyecto.services.CompraServiceImp;
import com.proyecto.is2.proyecto.services.OperacionServiceImp;
import com.proyecto.is2.proyecto.services.ServicioServiceImp;
import com.proyecto.is2.proyecto.services.UsuarioServiceImp;
import com.proyecto.is2.proyecto.services.ClienteServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Controlador encargado de recibir las peticiones
 * para realizar compra
 */
@Controller
@RequestMapping("/realizarCompra")
public class CompraController {
    final String VIEW = "compra"; // identificador de la vista
    final String VIEW_PATH = "compra";
    String operacion = "";
    final String FORM_VIEW = VIEW_PATH + "/realizarCompra";
    final String FORM_NEW = VIEW_PATH + "/nuevo";
    final String FORM_EDIT = VIEW_PATH + "/editar";
    final String RD_FORM_VIEW = "redirect:/compra";
    final String FALTA_PERMISO_VIEW = "falta-permiso";
    final String RD_FALTA_PERMISO_VIEW = "redirect:/" + FALTA_PERMISO_VIEW;
    final String ASIGNAR_ROL_VIEW = VIEW_PATH + "/asignar-rol";
//    final String RD_ASIGNAR_ROL_VIEW = "redirect:/" + ASIGNAR_ROL_VIEW;
    final String P_ASIGNAR_ROL = "asignar-rol-usuario";

    @Autowired
    private CompraServiceImp compraService;

     // llamada a los servicios de compra
     @Autowired
     private ProductoServiceImp productoService; // llamada a los servicios de producto
     
     @Autowired
    private UsuarioServiceImp usuarioService;

     @Autowired
     private ProveedorServiceImp proveedorService; // llamada a los servicios de cliente

     @Autowired
     private AperturaCajaServiceImp aperturaCajaService; // llamada a los servicios de cliente

    @Autowired
    private OperacionServiceImp operacionMov;

     @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    FacturaRepository facturaRepository;

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    OperacionRepository operacionRepository;

    @Autowired
    ProveedorRepository proveedorRepository;

    @Autowired
    private RolServiceImp rolService;//llamada a servicios de roles
        @Autowired
    AperturaCajaRepository aperturaCajaRepository;

    @Autowired
    CajaRepository cajaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;
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

        } else{
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
    public String crearObjeto(@ModelAttribute("compra") CompraDTO objetoDTO,
            @RequestParam(value="compraDetalle") String compraDetalle,
                              RedirectAttributes attributes) {
        this.operacion = "crear-";

        String username = SecurityContextHolder.getContext().getAuthentication().getName(); //Obtener datos del usuario logueado[Basico]
        Usuario usuario = usuarioRepository.findByEmail(username);// Obtener todos los datos del usuario 
        List<AperturaCaja> cajaApertura = aperturaCajaRepository.findByIdUsuarioOrderByIdAperturaCajaDesc(usuario.getIdUsuario());
        List<Operacion> ultMov = operacionRepository.findByIdCajaOrderByIdOperacionDesc(cajaApertura.get(0).getIdCaja());
        BigDecimal montoCompra = new BigDecimal(objetoDTO.getMontoCompra());

        String[] arrCompraDetalle = compraDetalle.split("\\|");
        Date fechaHoraActual = new Date();//para persistir la fecha de compra
        SimpleDateFormat formatoFechaHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fechaHoraFormateada = formatoFechaHora.format(fechaHoraActual);

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            Compra compra = new Compra();
            Proveedor proveedor = proveedorRepository.findByIdProveedor(objetoDTO.getIdProveedor());
            //Proveedor proveedor = proveedorService.obtenerInstancia(objetoDTO.getIdProveedor());
            compra.setProveedor(proveedor);
            compra.setMontoTotal(montoCompra);
            compra.setEstado("PENDIENTE");
            compra.setMontoCompra(montoCompra.toString());
            compra.setFechaCompra(fechaHoraFormateada);

            //GUARDAR LA VENTA
            Compra nuevaCompra = compraService.guardar(compra);
            // OBTENER EL ID DE LA VENTA 
            Long idCompra = nuevaCompra.getIdCompra();
            
            for (String detCrudo : arrCompraDetalle) {
                String[] elementos= detCrudo.split(";");
                Optional<Producto>  prod = productoRepository.findById(Long.parseLong(elementos[1]));

                CompraDetalle vtaDet = new CompraDetalle();
                vtaDet.setCompra(nuevaCompra);
                vtaDet.setCantidad(new Float(elementos[0]));
                vtaDet.setProducto(prod.get());
                vtaDet.setPrecio(new Float(elementos[2]));
                compraService.guardarDetalle(vtaDet);
    
            }
            // CREAR ESTRUCTURA PARA LA OPERACION A GUARDAR
            Operacion opEstructura = new Operacion();

            BigDecimal saldoAnterior = null;
            if(ultMov.size()>0){
                saldoAnterior = new BigDecimal(ultMov.get(0).getSaldoPosterior());
            }else{
                saldoAnterior = new BigDecimal("0");

            }
            
            opEstructura.setConcepto("Compra de Productos");
            opEstructura.setIdCaja(cajaApertura.get(0).getIdCaja());
            opEstructura.setIdUsuario(usuario.getIdUsuario());
            opEstructura.setMonto(objetoDTO.getMontoCompra());
            opEstructura.setFechaOperacion(LocalDate.now().toString());
            opEstructura.setSaldoAnterior(saldoAnterior.toString());
            opEstructura.setSaldoPosterior(saldoAnterior.add( montoCompra).toString());

            //Aun no guarda
            System.out.println("llega hasta la guardacion");
            operacionMov.guardar(opEstructura);

            attributes.addFlashAttribute("message", "¡Compra creada exitosamente!");

            return RD_FORM_VIEW+"/a"+arrCompraDetalle.length+"-e";
        } else {
            return RD_FALTA_PERMISO_VIEW;
        }
    }

    @GetMapping("/notaCredito/{id}")
    public String formNotaDeCredito(@PathVariable String id, Model model) {
        boolean eliminar = usuarioService.tienePermiso("eliminar-" + VIEW);
        boolean asignarRol = usuarioService.tienePermiso("asignar-rol-" + VIEW);
        Compra compra;
        System.out.println("Entra hasta aca");
        Proveedor proveedor = proveedorRepository.findByIdProveedor(Long.parseLong(id));
        List<Factura> listaFactura = facturaRepository.findByEstadoAndProveedorAndTipo("PENDIENTE", proveedor, "Nota de Credito");
        // validar el id
       /* try {
            Long idCompra = Long.parseLong(id);
            compra = compraService.existeCompra(idCompra);
        } catch(Exception e) {
            return RD_FORM_VIEW;
        }*/

        model.addAttribute("NotaList", listaFactura);

        // validar si puede cambiar de rol
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

    @PostMapping("/notaCredito/{id}")
    public String formNotaCredito(@PathVariable String id, Model model) {
        boolean eliminar = usuarioService.tienePermiso("eliminar-" + VIEW);
        boolean asignarRol = usuarioService.tienePermiso("asignar-rol-" + VIEW);
        Compra compra;
        Proveedor proveedor = proveedorRepository.findByIdProveedor(Long.parseLong(id));
        List<Factura> listaFactura = facturaRepository.findByEstadoAndProveedorAndTipo("PENDIENTE", proveedor, "Nota de Credito");

        model.addAttribute("NotaList", listaFactura);

        if(eliminar) {
            return FORM_EDIT;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @GetMapping("/{id}")
    public String formEditar(@PathVariable String id, Model model) {
        boolean eliminar = usuarioService.tienePermiso("eliminar-" + VIEW);
        boolean asignarRol = usuarioService.tienePermiso("asignar-rol-" + VIEW);
        Compra compra;

        // validar el id
        try {
            Long idCompra = Long.parseLong(id);
            compra = compraService.existeCompra(idCompra);
        } catch(Exception e) {
            return RD_FORM_VIEW;
        }

        model.addAttribute("user", compra);

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
    public String actualizarObjeto(@PathVariable Long id, @ModelAttribute("compra") CompraDTO objetoDTO,
                                   BindingResult result, RedirectAttributes attributes) {
        this.operacion = "actualizar-";
        Compra compra;

        if (result.hasErrors()) {
            // studentDTO.setId(id);
            return RD_FORM_VIEW;
        }

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            compra = compraService.existeCompra(id);
            if(compra != null) {
                compraService.convertirDTO(compra, objetoDTO);

                // si tiene permiso se le asigna el rol con id del formulario
                // sino se queda con el mismo rol.
                /*if(ventaService.tienePermiso(P_ASIGNAR_ROL)) {
                    venta.setRol(rolService.existeRol(objetoDTO.getIdRol().longValue()));
                }*/

                attributes.addFlashAttribute("message", "¡Compra actualizado correctamente!");
                compraService.guardar(compra);
                return RD_FORM_VIEW;
            }
        }
        return RD_FALTA_PERMISO_VIEW;
    }

    @GetMapping("/{id}/delete")
        public String eliminarObjeto(@PathVariable String id, RedirectAttributes attributes  ) {
        this.operacion = "eliminar-";
        Long idCompra;

        try {
            idCompra = Long.parseLong(id);
        } catch (Exception e) {
            return RD_FORM_VIEW;
        }

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            Compra compra = compraService.obtenerCompra(idCompra);
            compraService.eliminarCompra(compra);
            attributes.addFlashAttribute("message", "¡Compra eliminado correctamente!");
            return RD_FORM_VIEW;
        } else {
            return RD_FALTA_PERMISO_VIEW;
        }
    }

}
