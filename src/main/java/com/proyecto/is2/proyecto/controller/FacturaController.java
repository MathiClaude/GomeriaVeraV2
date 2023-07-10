package com.proyecto.is2.proyecto.controller;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.proyecto.is2.proyecto.controller.dto.CompraDTO;
import com.proyecto.is2.proyecto.controller.dto.ProveedorDTO;
import com.proyecto.is2.proyecto.controller.dto.UsuarioDTO;
import com.proyecto.is2.proyecto.controller.dto.VentaDTO;
import com.proyecto.is2.proyecto.model.Rol;
import com.proyecto.is2.proyecto.model.Usuario;
import com.proyecto.is2.proyecto.model.Servicio;
import com.proyecto.is2.proyecto.model.Cliente;
import com.proyecto.is2.proyecto.model.Compra;
import com.proyecto.is2.proyecto.model.CompraDetalle;
import com.proyecto.is2.proyecto.model.Contacto;
import com.proyecto.is2.proyecto.model.Factura;
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
import com.proyecto.is2.proyecto.services.CompraDetalleServiceImp;
import com.proyecto.is2.proyecto.services.CompraService;
import com.proyecto.is2.proyecto.services.CompraServiceImp;
import com.proyecto.is2.proyecto.services.FacturaServiceImp;
import com.proyecto.is2.proyecto.services.OperacionServiceImp;
import com.proyecto.is2.proyecto.repository.UsuarioRepository;
import com.proyecto.is2.proyecto.repository.VentaDetalleRepository;
import com.proyecto.is2.proyecto.repository.AperturaCajaRepository;
import com.proyecto.is2.proyecto.repository.CajaRepository;
import com.proyecto.is2.proyecto.repository.ClienteRepository;
import com.proyecto.is2.proyecto.repository.CompraDetalleRepository;
import com.proyecto.is2.proyecto.repository.CompraRepository;
import com.proyecto.is2.proyecto.repository.FacturaRepository;
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

/**
 * Controlador encargado de recibir las peticiones
 * para realizar venta
 */
@Controller
@RequestMapping("/gestionarFacturas")
public class FacturaController {
    //final String VIEW = "factura"; // identificador de la vista
    final String VIEW = "inventario"; // identificador de la vista de permisos jeje

    final String VIEW_PATH = "factura";
    String operacion = "";
    final String FORM_VIEW = VIEW_PATH + "/gestionarFacturas";
    final String FORM_NEW = VIEW_PATH + "/ventaReporte";
    final String FORM_EDIT = VIEW_PATH + "/editar";
    final String RD_FORM_VIEW = "redirect:/gestionarFacturas";
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
    FacturaRepository facturaRepository;

    @Autowired
    CajaRepository cajaRepository;

    @Autowired
    CompraRepository compraRepository;

    @Autowired
    CompraDetalleRepository compraDetalleRepository;

    @Autowired
    OperacionRepository operacionRepository;

    // llamada a los servicios de venta
    @Autowired
    private CompraServiceImp compraService; // llamada a los servicios de producto
    @Autowired
    private CompraDetalleServiceImp compraDetalleService; // llamada a los servicios de producto
    @Autowired
    private ProductoServiceImp productoService; // llamada a los servicios de producto
    @Autowired
    private FacturaServiceImp facturaService; // llamada a los servicios de factura
    @Autowired
    private ServicioServiceImp servicioService; // llamada a los servicios de servicio
    @Autowired
    private ClienteServiceImp clienteService; // llamada a los servicios de cliente
    @Autowired
    private UsuarioServiceImp usuarioService; // llamada a los servicios de usuario
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
            List<Compra> compra = compraRepository.findByEstado("PENDIENTE");
            List<Compra> compraPar = compraRepository.findByEstado("PARCIALMENTE ENTREGADO");
            List<Compra> comprasCombinadas = new ArrayList<>();
            comprasCombinadas.addAll(compra);
            comprasCombinadas.addAll(compraPar);
            model.addAttribute("listCr", comprasCombinadas);//lista los productos
            model.addAttribute("listProduct", productoService.listar());//lista los productos
            model.addAttribute("listServicio", servicioService.listar());//lista los productos
            model.addAttribute("listarCliente", clienteService.listar());//lista los clientes
            String username = SecurityContextHolder.getContext().getAuthentication().getName(); //Obtener datos del usuario logueado[Basico]
            Usuario usuario = usuarioRepository.findByEmail(username);// Obtener todos los datos del usuario 

            //model.addAttribute("nombreCajero", usuario.getUsername());//Nombre del cajero

            //List<AperturaCaja> cajaApertura = aperturaCajaRepository.findByIdUsuarioOrderByIdAperturaCajaDesc(usuario.getIdUsuario());
            

            /*if(cajaApertura.size()>0){
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
            }*/
            



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

    @GetMapping("/gestionarFacturas")
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

    @GetMapping("/{id}")
    public String formEditar(@PathVariable String id, Model model) {
        boolean eliminar = usuarioService.tienePermiso("eliminar-" + VIEW);
        boolean asignarRol = usuarioService.tienePermiso("asignar-rol-" + VIEW);
        //Venta venta;
        Compra compra;
        CompraDTO compraDTO = new CompraDTO();

        //System.out.println("SE ESTA REALIZANDO LA ENTRADA DE LA FACTURA");  

        // validar el id
        try {
            model.addAttribute("edit", true);
            Long idCompra = Long.parseLong(id);
            compra = compraService.obtenerCompra(idCompra);
            compraService.getDTO(compra, compraDTO);
            model.addAttribute("compra", compra);
            //solo tenia esta linea de abajo antes
            //compra = compraService.existeCompra(idCompra);
        } catch(Exception e) {
            return RD_FORM_VIEW;
        }

       // model.addAttribute("compra", compra);

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

    /*@PostMapping("/{id}")
    public String actualizarObjeto(@PathVariable Long id, @ModelAttribute("compra") CompraDTO objetoDTO,
                                @RequestParam(value="factura") String factura,
                                   BindingResult result, RedirectAttributes attributes) {
        this.operacion = "actualizar-";
        //Compra venta;
        Compra compra;
        String[] arrDocumento = factura.split("\\|");

        //System.out.println("SE ESTA REALIZANDO LA ACTUALIZACION DE LA FACTURA");                            

        if (result.hasErrors()) {
//            studentDTO.setId(id);
            return RD_FORM_VIEW;
        }

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            compra = compraService.existeCompra(id);
            if(compra != null) {
                compraService.convertirDTO(compra, objetoDTO);
                // si tiene permiso se le asigna el rol con id del formulario
                // sino se queda con el mismo rol.
                //if(ventaService.tienePermiso(P_ASIGNAR_ROL)) {
                    //venta.setRol(rolService.existeRol(objetoDTO.getIdRol().longValue()));
                //}
                attributes.addFlashAttribute("message", "¡Compra actualizado correctamente!");
                //compraService.guardar(compra);
                //se actualiza la compra
                Compra compraActual = compraService.guardar(compra);

                for (String docCrudo : arrDocumento) {
                    String[] elementos= docCrudo.split(";");

                    Factura docDet = new Factura();
                    docDet.setCompra(compraActual);
                    docDet.setNroDocumento(new String(elementos[0]));
                    docDet.setTipo(new String(elementos[1]));
                    docDet.setMonto(new String(elementos[2]));
                    compraService.guardarFactura(docDet);


                }

                return RD_FORM_VIEW;
            }
        }
        return RD_FALTA_PERMISO_VIEW;
    }*/

    @PostMapping("/{id}")
    public String actualizarObjeto(@PathVariable Long id,@ModelAttribute("compra") CompraDTO objetoDTO,
                              @RequestParam(value = "c-documentos[]", required = false) String[] documentos,
                              @RequestParam(value = "c-tipos[]", required = false) String[] tipos,
                              @RequestParam(value = "c-montos[]", required = false) String[] montos,
                              RedirectAttributes attributes, Model model, BindingResult result) {
        if (result.hasErrors()) {
            return DATA_CREATE_URL;
        }

       // boolean privillege = usuarioService.tienePermiso(Permisos.WRITE_ADMINISTRATION_PRIVILEGE.name());

        //f(privillege) {
            System.out.println("el ide aca es");
            System.out.println(objetoDTO.getIdCompra());
           // System.out.println("el idProveedor es");
           // System.out.println(objetoDTO.getIdProveedor());
            try {
                Compra compra = compraService.existeCompra(id);
                compra.setEstado(objetoDTO.getEstado());
                Proveedor proveedor = proveedorService.existeProveedor(compra.getProveedor().getIdProveedor());
                List<CompraDetalle> detalleCompra = compraDetalleRepository.findByCompra(compra);

                

                System.out.println("el idProveedor es");
                System.out.println(compra.getProveedor().getIdProveedor());
                System.out.println("el idProveedorOBJ es");
                System.out.println(proveedor.getIdProveedor());
                System.out.println(proveedor.getNombre());
                //Persona persona = personaService.obtenerInstancia(objetoDTO.getPersona().getIdPersona());

                //List<Factura> listaDocs = null;
                List<Factura> listaDocs = new ArrayList<>(); 
                List<Factura> todasLasFacturas = new ArrayList<>(); 
                todasLasFacturas = facturaService.listarTodos();

                for (Factura factura : todasLasFacturas) {
                    if (factura.getCompra() == compra) {
                        listaDocs.add(factura);
                    }
                }               

                //if(objetoDTO.getIdCompra() == null && persona != null) {
                /*if(objetoDTO.getIdCompra() == null) {
                    throw new Exception("No se puede crear la persona, porque ya existe.");
                //} else if(objetoDTO.getIdCompra() == null && persona == null) {
                } else if(objetoDTO.getIdCompra() == null) {
                    //persona = new Persona();
                    compra = new Compra();
                //} else if(objetoDTO.getIdCompra() != null && persona == null) {
                } else if(objetoDTO.getIdCompra() != null) {
                    throw new Exception("No se ha podido encontrar la persona asociada al compra.");
                }*/

                //compra.setPersona(persona);
                compraService.convertirDTO(compra, objetoDTO);
                //persona = personaService.guardar(compra.getPersona());
                //compra.setPersona(persona);
                //listaDocs = compra.getContactos();

                if(listaDocs == null) {
                    // si no tiene listaDocs
                    listaDocs = new ArrayList<>();
                } else if(documentos == null) {
                    facturaRepository.deleteAll(listaDocs);
                    listaDocs.clear();
                } else {
                    List<Factura> newList = new ArrayList<>();
                    // listaDocs eliminados o modificados se eliminan
                    for(int k=0; k < listaDocs.size(); k++) {
                        boolean flat = false;
                        for(int i=0; i < documentos.length; i++) {
                            if(listaDocs.get(k).getNroDocumento().equals(documentos[i]) &&
                                    listaDocs.get(k).getMonto().equals(montos[i]) &&
                                    listaDocs.get(k).getTipo().equals(tipos[i])) {
                                newList.add(listaDocs.get(k));
                                flat = true;
                                break;
                            }
                        }
                        if(!flat) {
                            //compraRespository.delete(listaDocs.get(k));
                            listaDocs.remove(listaDocs.get(k));
                        }
                    }
                    // elementos agregados o no modificados se agregan a la nueva lista
                    for(int i=0; i < documentos.length; i++) {
                        boolean flat = false;
                        for(int k=0; k < listaDocs.size(); k++) {
                            if(documentos[i].equals(listaDocs.get(k).getNroDocumento()) &&
                                    tipos[i].equals(listaDocs.get(k).getTipo()) &&
                                    montos[i].equals(listaDocs.get(k).getMonto())) {
                                newList.add(listaDocs.get(k));
                                flat = true;
                                break;
                            }
                        }
                        if(!flat) {
                            Factura tmpFactura = new Factura();
                            tmpFactura.setNroDocumento(documentos[i]);
                            tmpFactura.setTipo(tipos[i]);
                            tmpFactura.setMonto(montos[i]);
                            tmpFactura.setCompra(compra);
                            tmpFactura.setProveedor(proveedor);
                            //compraService.guardarFactura(tmpFactura);//comentado
                            newList.add(facturaRepository.save(tmpFactura));
                        }
                    }
                    listaDocs = newList;
                }
                if(compra.getEstado().contentEquals("RECEPCIONADO")){
                    //System.out.println("RECEPCIONADO");          
                    //CompraDetalle compraDetalle = compraDetalleService.obtenerInstancia(compra.getIdCompra());
                    Producto pr;
                    for (CompraDetalle compraDetalle : detalleCompra) {
                        pr = compraDetalle.getProducto();

                        /*System.out.println("Codigo de Prod:");    
                        System.out.println( pr.getCodigo());
                        System.out.println("Cantidad Original:");    
                        System.out.println( pr.getCantidad());
                        System.out.println("Cantidad a sumar:");    
                        System.out.println( compraDetalle.getCantidad());*/

                        float cantidadFloat = compraDetalle.getCantidad();
                        int cantidadInt = Math.round(cantidadFloat);
                        pr.setCantidad(pr.getCantidad() + cantidadInt);
                        pr.setPrecioCompra(Math.round(compraDetalle.getPrecio()));
                        productoService.guardar(pr);
                        
                    }
                }

                System.out.println("Hasta aca todo bien 1");
                compra.setFacturas(listaDocs);
                compraService.guardar(compra);

                System.out.println("Hasta aca todo bien 2");

                if(objetoDTO.getIdCompra() == null) {
                    attributes.addFlashAttribute("message", "¡Compra creado correctamente!");
                } else {
                    attributes.addFlashAttribute("message", "¡Compra actualizado correctamente!");
                }
                System.out.println("Hasta aca todo bien 3");
                //attributes.addFlashAttribute(ModelAttributes.ALERT_TYPE, ModelAttributes.ALERT_SUCCESS);
            //} catch (AuthorizationServiceException e) {
            } catch (Exception e) {
                /*model.addAttribute(ModelAttributes.ERROR_CODE, ModelAttributes.CODE_PRIVILLEGE);
                model.addAttribute(ModelAttributes.ERROR_MSG, ModelAttributes.MSG_403);
                model.addAttribute(ModelAttributes.ERROR_TITLE, ModelAttributes.TITLE_403);
                return ERROR_VIEW;*/
                return RD_FALTA_PERMISO_VIEW;
            }/*catch (Exception e) {
                model.addAttribute(ModelAttributes.ERROR_CODE, ModelAttributes.ERROR_GENERIC);
                model.addAttribute(ModelAttributes.ERROR_MSG, "");
                model.addAttribute(ModelAttributes.ERROR_TITLE, e.getMessage());
                return ERROR_VIEW;
            }*/
       /*  } else {
//            attributes.addFlashAttribute(ModelAttributes.ALERT_MESSAGE, ModelAttributes.TITLE_403);
//            attributes.addFlashAttribute(ModelAttributes.ALERT_TYPE, ModelAttributes.ALERT_NOTIFICATION);
            model.addAttribute(ModelAttributes.ERROR_CODE, ModelAttributes.CODE_PRIVILLEGE);
            model.addAttribute(ModelAttributes.ERROR_MSG, ModelAttributes.MSG_403);
            model.addAttribute(ModelAttributes.ERROR_TITLE, ModelAttributes.TITLE_403);
            return ERROR_VIEW;
        }*/
        //return REDIRECT_VIEW + PROVEERS_URL;
        return RD_FORM_VIEW;
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

    @Transactional
    @PostMapping(DATA_CREATE_URL)
    public String crearObjeto(@RequestParam(name = "arr[]") String[] arr,
                              @RequestParam(name = "moneda", required = false) String[] moneda,
                              @RequestParam(name = "proveedor", required = false) Long proveedor,
                              @RequestParam(name = "tipo_pago", required = false) String tipoPago,
                              @RequestParam(name = "total_operacion", required = false) String total,
                              @RequestParam(name = "direccion", required = false) String direccion,
                              @RequestParam(name = "observacion", required = false) String observacion,
                              Model model, RedirectAttributes attributes) {

        boolean privillege = usuarioService.tienePermiso(Permisos.WRITE_COMPRAS_PRIVILEGE.name);
        for(int i=0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
        if (false) {

            try {
                Venta obj = ventaService.guardar(new Venta());
                List<Item> listItems = new ArrayList<>();
                Proveedor objProveedor = proveedorService.obtenerProveedor(proveedor);
                String username = SecurityContextHolder.getContext().getAuthentication().getName();
                Usuario usuario = usuarioService.existeUsuario(username);

                for(int i=0; i < arr.length; i++) {
                    String[] items = arr[i].split("-");
                    Long idProducto = Long.valueOf(items[0]);
                    Float cantidad = Float.valueOf(items[1]);
                    Float costo = Float.valueOf(items[2]);

                    VentaDetalle item = new VentaDetalle();
                    item.setProducto(productoService.obtenerProducto(idProducto));
                    item.setCantidad(cantidad);
                   // item.setCosto(costo);
                   // item.setOrdenCompra(obj);
                   // item = itemService.guardar(item);
                    //listItems.add(item);
                }

                /*obj.setItems(listItems);
                obj.setProveedor(objProveedor);
                obj.setRegistradoPor(usuario);
                obj.setObservacion(observacion);
                obj.setEstado(Estados.PENDIENTE.name());
                obj.setFechaEntrega(null);
                obj.setDireccionEntrega(direccion);
                obj.setFechaEmision(LocalDate.now());*/

                ventaService.guardar(obj);

                attributes.addFlashAttribute(ModelAttributes.ALERT_MESSAGE, "Compra registrada correctamente!");
                attributes.addFlashAttribute(ModelAttributes.ALERT_TYPE, ModelAttributes.ALERT_SUCCESS);

                //return REDIRECT_VIEW + ORDEN_COMPRA_URL;
                return VIEW;

            } catch (AuthorizationServiceException e) {
                model.addAttribute(ModelAttributes.ERROR_CODE, ModelAttributes.CODE_PRIVILLEGE);
                model.addAttribute(ModelAttributes.ERROR_MSG, ModelAttributes.MSG_403);
                model.addAttribute(ModelAttributes.ERROR_TITLE, ModelAttributes.TITLE_403);
                //return ERROR_VIEW;
                return VIEW;
            } catch (Exception e) {
                model.addAttribute(ModelAttributes.ERROR_CODE, ModelAttributes.ERROR_GENERIC);
                model.addAttribute(ModelAttributes.ERROR_MSG, "");
                model.addAttribute(ModelAttributes.ERROR_TITLE, e.getMessage());
                //return ERROR_VIEW;
                return VIEW;
            }
        } else {
            model.addAttribute(ModelAttributes.ERROR_CODE, ModelAttributes.CODE_PRIVILLEGE);
            model.addAttribute(ModelAttributes.ERROR_MSG, ModelAttributes.MSG_403);
            model.addAttribute(ModelAttributes.ERROR_TITLE, ModelAttributes.TITLE_403);
            //return ERROR_VIEW;
            return VIEW;
        }
    }

}
