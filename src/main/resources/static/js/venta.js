
    var table;
    var tablaDatos;  
    var items = []; // SE USA PARA EL INPUT DE AUTOCOMPLETE
    var itemProducto = 1;

    function cargarTablaOrdenCompra() {

        /* ======================================================================================
        TRAER EL NRO DE BOLETA
        ======================================================================================*/
        // CargarNroBoleta();

        /* ======================================================================================
        EVENTO PARA VACIAR EL CARRITO DE COMPRAS
        =========================================================================================*/
         $("#btnVaciarListado").on('click', function() {
             vaciarListado();
         })

        /* ======================================================================================
        INICIALIZAR LA TABLA DE VENTAS
        ======================================================================================*/
        table = new DataTable('#lstProductosOrdenCompra', {
            columnDefs: [
                {
                    targets: 0,
                    visible: false
                },
                {
                    targets: 2,
                    visible: false
                },
            ],
            "order": [
                [0, 'desc']
            ],
            language: {
                url: '//cdn.datatables.net/plug-ins/1.13.4/i18n/es-ES.json',
            },
            "columns": [
                {
                    "data": "id"
                },
                {
                    "data": "codigo_producto"
                },
                {
                    "data": "nombre_categoria"
                },
                {
                    "data": "descripcion_producto"
                },
                {
                    "data": "cantidad"
                },
                {
                    "data": "precio_compra_producto"
                },
                {
                    "data": "total"
                },
                {
                    "data": "acciones"
                },
            ],
        });


        /* ======================================================================================
		TRAER LISTADO DE PRODUCTOS PARA INPUT DE AUTOCOMPLETADO
		======================================================================================*/
        $.ajax({
            async: false,
            url: appname + "productos/listar",
            method: "GET",
            dataType: 'json',
            success: function(respuesta) {

                for (let i = 0; i < respuesta.length; i++) {
                    var name = 'Código: ' + respuesta[i]['idProducto'] + ', Producto: ' + respuesta[i]['nombre'] + ', Últ. Costo: Gs. ' + respuesta[i]['precioCompra'];
                    items.push( {value: respuesta[i]['idProducto'], label: name} );
                }

                $("#iptCodigoVenta").autocomplete({

                    source: items,
                    select: function(event, ui) {

                        cargarProductos(ui.item.value);

                        $("#iptCodigoVenta").val("");

                        $("#iptCodigoVenta").focus();

                        return false;
                    }
                })

            }
        });


        /* ======================================================================================
        EVENTO QUE REGISTRA EL PRODUCTO EN EL LISTADO CUANDO SE INGRESA EL CODIGO DE BARRAS
        // ======================================================================================*/
        // $("#iptCodigoVenta").change(function() {
        //     cargarProductos();
        // });

        /* ======================================================================================
        EVENTO PARA ELIMINAR UN PRODUCTO DEL LISTADO
        ======================================================================================*/
         $('#lstProductosOrdenCompra tbody').on('click', '.btnEliminarproducto', function() {
             table.row($(this).parents('tr')).remove().draw();
             recalcularTotales();
         });

        /* ======================================================================================
        EVENTO PARA AUMENTAR LA CANTIDAD DE UN PRODUCTO DEL LISTADO
        ====================================================================================== */
         $('#lstProductosOrdenCompra tbody').on('click', '.btnAumentarCantidad', function() {
            var fila = $(this).parents('tr');

            var cantidad = fila.find('.iptCantidad').val();
            var precio = fila.find('.iptPrecioCompra').val();

            cantidad = parseInt(cantidad) + 1;

            fila.find('.iptCantidad').val(cantidad);
            fila.find('.precio-total').text(cantidad * precio);

            recalcularTotales();

         });

        /* ======================================================================================
        EVENTO PARA DESMINUIR LA CANTIDAD DE UN PRODUCTO DEL LISTADO
        ======================================================================================*/
         $('#lstProductosOrdenCompra tbody').on('click', '.btnDisminuirCantidad ', function() {

            var fila = $(this).parents('tr');
            var cantidad = $(this).parents('tr').find('.iptCantidad').val();

            if (cantidad >= 2) { // para cantidad 1 se debe utilizar el boton eliminar item
                var cantidad = fila.find('.iptCantidad').val();
                var precio = fila.find('.iptPrecioCompra').val();

                cantidad = parseInt(cantidad) - 1;

                fila.find('.iptCantidad').val(cantidad);
                fila.find('.precio-total').text(cantidad * precio);

            }

            recalcularTotales();
         });

        /* ======================================================================================
        EVENTO PARA INICIAR EL REGISTRO DE LA ORDE DE COMPRA
        ====================================================================================== */
         $("#btnIniciarOperacion").on('click', function() {
             var urlPath = $("#urlDestination").val();
             realizarOperacion(urlPath);
         })

        /* ======================================================================================
        EVENTO PARA INICIAR EL LA MODIFICACION DE LA ORDE DE COMPRA
        ====================================================================================== */
        $("#btnGuardar").on('click', function() {
            var urlPath = $("#urlDestination").val();
            realizarOperacion(urlPath);
        })


    }

    /*===================================================================*/
    //FUNCION PARA LIMPIAR TOTALMENTE EL CARRITO DE VENTAS
    /*===================================================================*/
     function vaciarListado() {
         table.clear().draw();
         LimpiarInputs();
     }

    /*===================================================================*/
    //FUNCION PARA LIMPIAR LOS INPUTS DE LA BOLETA Y LABELS QUE TIENEN DATOS
    /*===================================================================*/
    function LimpiarInputs() {
        $("#montoTotal").html("0");
        $("#totalRegistrar").html("0");
        $("#boleta_total").html("0");
        $("#iptEfectivoRecibido").val("");
        $("#EfectivoEntregado").html("0");
        $("#Vuelto").html("0");
        $("#chkEfectivoExacto").prop('checked', false);
        $("#boleta_subtotal").html("0");
        $("#boleta_igv").html("0")
    } /* FIN LimpiarInputs */

    /*===================================================================*/
    //FUNCION PARA RECALCULAR LOS TOTALES DE VENTA
    /*===================================================================*/
     function recalcularTotales() {

        var filas = document.getElementsByTagName('tr');
        var montoTotal = 0;

        // Iterar por todas las filas excepto la primera (encabezados)
        for (var i = 1; i < filas.length; i++) {
            var fila = filas[i];
            var codigoSpan = fila.querySelector('.codigo-producto');

            if(filas.length == 2 && codigoSpan == null) {
                break;
            }

            var monto = fila.querySelector('.precio-total').textContent;
            montoTotal += parseInt(monto);
        } 

        $("#montoTotal").html("");
        $("#montoTotal").html(montoTotal.toFixed(0));
        $("#totalRegistrar").html(montoTotal.toFixed(0));

         var montoTotal = $("#montoTotal").html();

         $("#iptCodigoVenta").val("");
     }


    /*===================================================================*/
    //FUNCION PARA CARGAR PRODUCTOS EN EL DATATABLE
    /*===================================================================*/
    function cargarProductos(producto = "") {

        if (producto != "") {
            var codigo_producto = producto;
        } else {
            var codigo_producto = $("#iptCodigoVenta").val();
        }

        // codigo_producto = $.trim(codigo_producto.split('/')[0]);
        console.log('Producto: ' + codigo_producto);

        // return;

        var producto_repetido = 0;

        /*===================================================================*/
        // AUMENTAMOS LA CANTIDAD SI EL PRODUCTO YA EXISTE EN EL LISTADO
        /*===================================================================*/
        var filas = document.getElementsByTagName('tr');
        for (var i = 1; i < filas.length; i++) {
            var fila = filas[i];
            var codigoSpan = fila.querySelector('.codigo-producto');
            var cantidadInput = fila.querySelector('.iptCantidad');
            var precioInput = fila.querySelector('.iptPrecioCompra');
                    
            if(filas.length == 2 && codigoSpan == null) {
                break;
            }

            if (codigo_producto == codigoSpan.textContent) {
                var cantidad_a_comprar = parseInt(cantidadInput.value) + 1;
                var precio_a_comprar = parseInt(precioInput.value);
                producto_repetido = 1;

               // AUMENTAR EN 1 EL VALOR DE LA CANTIDAD
                cantidadInput.value = cantidad_a_comprar;
                
               // ACTUALIZAR EL NUEVO PRECIO DEL ITEM DEL LISTADO DE VENTA
                fila.querySelector('.precio-total').textContent = precio_a_comprar * cantidad_a_comprar;

               // RECALCULAMOS TOTALES
               recalcularTotales();
               break;
            }             
        }

        // return;

        if (producto_repetido == 1) {
            return;
        }

        console.log('Cargar producto: ' + codigo_producto);

        $.ajax({
            url: appname + "productos/buscar",
            method: "POST",
            data: {
                //BUSCAR PRODUCTOS POR SU CODIGO DE BARRAS
                'codigo_producto': codigo_producto
            },
            dataType: 'json',
            success: function(respuesta) {

                console.log(respuesta);
                /*===================================================================*/
                //SI LA RESPUESTA ES VERDADERO, TRAE ALGUN DATO
                /*===================================================================*/
                if (respuesta != null) {

                    var montoTotal = 0;

                    loadRowTable(respuesta);

                    itemProducto = itemProducto + 1;

                    // Recalculamos el total de la venta
                    recalcularTotales();

                    // agregar evento para calcular total de forma automatica
                    addFieldsEvents();

                    /*===================================================================*/
                    //SI LA RESPUESTA ES FALSO, NO TRAE ALGUN DATO
                    /*===================================================================*/
                } else {

                    // mensajeToast('error', 'EL PRODUCTO NO EXISTE O NO TIENE STOCK');
                    showAlert('warning', 'EL PRODUCTO NO EXISTE');

                    $("#iptCodigoVenta").val("");
                    $("#iptCodigoVenta").focus();
                }

            }
        });

    } /* FIN CargarProductos */

      /*===================================================================*/
    //FUNCION PARA CARGAR PRODUCTO EN EL DATATABLE
    /*===================================================================*/
    function procesarProducto(producto = "", cantidad = 0, costo = 0) {

        if (producto != "") {
            var codigo_producto = producto;
        } else {
            var codigo_producto = $("#iptCodigoVenta").val();
        }

        $.ajax({
            url: appname + "productos/buscar",
            method: "POST",
            data: {
                //BUSCAR PRODUCTOS POR SU CODIGO DE BARRAS
                'codigo_producto': codigo_producto
            },
            dataType: 'json',
            success: function(respuesta) {

                console.log(respuesta);
                /*===================================================================*/
                //SI LA RESPUESTA ES VERDADERO, TRAE ALGUN DATO
                /*===================================================================*/
                if (respuesta != null) {

                    var montoTotal = 0;

                    respuesta['precioCompra'] = costo;
                    respuesta['total'] = parseInt(costo) * parseInt(cantidad);
                    respuesta['cantidad'] = cantidad;
                    loadRowTable(respuesta);

                    itemProducto = itemProducto + 1;

                    // Recalculamos el total de la venta
                    recalcularTotales();

                    /*===================================================================*/
                    //SI LA RESPUESTA ES FALSO, NO TRAE ALGUN DATO
                    /*===================================================================*/
                } else {
                    showAlert('warning', 'EL PRODUCTO NO EXISTE');

                    $("#iptCodigoVenta").val("");
                    $("#iptCodigoVenta").focus();
                }

            }
        });

    } /* FIN procesarProducto */

    /*===================================================================*/
    //REALIZAR OPERACION
    /*===================================================================*/
     function realizarOperacion(urlPath) {

         var count = 0;
         var montoTotal = $("#montoTotal").html();
         var proveedor = $('#selProveedor').val();
         var moneda = $("#selMoneda").val();
         var tipoPago = $("#selTipoPago").val();
         var direccion = $("#iptDireccion").val();
         var observacion = $("#iptObservacion").val();

         count = table.rows()['0'].length;

         //$("#myTable3 tbody tr").each(function(){  console.log($(this)[0].cells[0].innerHTML + "-" + $(this)[0].cells[2].innerHTML); });


         //$("#myTable3 tbody tr").length

         var index = 0;
         var arr = [];
         //cargo datos de detalles
         $("#myTable3 tbody tr").each(function(){  
            arr[index] =  $(this)[0].cells[index].innerHTML + "-" + $(this)[0].cells[index].innerHTML;
            index ++; 
        });
        console.log("arr:" + arr);

        //cargo datos de cabecera
        var cliente = $("#buscadorCliente").val();
        var montoTotal = $("#montoTotal").html();
        var proveedor = $('#selProveedor').val();
        var moneda = $("#selMoneda").val();
        var tipoPago = $("#selTipoPago").val();
        var direccion = $("#iptDireccion").val();
        var observacion = $("#iptObservacion").val();

        //


         if (count > 0) {

                var formData = new FormData();
                var arr = [];

                var filas = document.getElementsByTagName('tr');
                for (var i = 1; i < filas.length; i++) {
                    var fila = filas[i];
                    var codigoSpan = fila.querySelector('.codigo-producto');

                    if(filas.length == 2 && codigoSpan == null) {
                        break;
                    }

                    var codigo = fila.querySelector('.codigo-producto').textContent;
                    var cantidad = fila.querySelector('.iptCantidad').value;
                    var precioCompra = fila.querySelector('.iptPrecioCompra').value;
                    
                    // arr[i - 1] = data['codigo_producto'] + "," + parseFloat($.parseHTML(data['cantidad'])[0]['value']) + "," + data['precioCompra'];
                    arr[i - 1] = codigo + "-" + cantidad + "-" + precioCompra;

                    formData.append('arr[]', arr[i - 1]);                    
                }


                 formData.append('moneda', moneda);
                 formData.append('proveedor', proveedor);
                 formData.append('tipo_pago', tipoPago);
                 formData.append('total_operacion', parseFloat(montoTotal));
                 formData.append('direccion', direccion);
                 formData.append('observacion', observacion);

                 var nroFactura = $("#iptNroFactura").val();
                 if(nroFactura != null) {
                    formData.append('nroFactura', nroFactura);
                    formData.append('condicion', $("#selCondicion").val());
                    formData.append('fechaEmision', $("#iptFechaEmsion").val());
                    formData.append('fchValidezTimbrado', $("#iptTimbrado").val());
                    formData.append('timbrado', $("#iptTimbrado").val());
                 }

                 var fechaEntrega = $("#iptFechaEntrega").val();
                 if(fechaEntrega != null) {
                    formData.append('fechaEntrega', fechaEntrega);
                 }

                $.ajax({
                     url: urlPath,
                     method: "POST",
                     data: formData,
                     cache: false,
                     contentType: false,
                     processData: false,
                     success: function(respuesta) {


                        // mensajeToast('success', respuesta);
                         showAlert('success', respuesta);

                         table.clear().draw();

                         LimpiarInputs();

                        // CargarNroBoleta();

                         window.location.pathname = appname + "orden-compra";

                     }
                });


//             } else {
//
//                 mensajeToast('error', 'INGRESE EL MONTO EN EFECTIVO');
//             }

         } else {

//             mensajeToast('error', 'NO HAY PRODUCTOS EN EL LISTADO');
             showAlert('warning', 'NO HAY PRODUCTOS EN EL LISTADO');

         }

         $("#iptCodigoVenta").focus();

     } /* FIN realizarOperacion */

function addFieldsEvents() {
    var filas = document.getElementsByTagName('tr');

    // Iterar por todas las filas excepto la primera (encabezados)
    for (var i = 1; i < filas.length; i++) {
        var fila = filas[i];
        var codigoSpan = fila.querySelector('.codigo-producto');
        var cantidadInput = fila.querySelector('.iptCantidad');
        var precioInput = fila.querySelector('.iptPrecioCompra');

        if(filas.length == 2 && codigoSpan == null) {
            break;
        }
  
        // Agregar un evento de escucha al cambio en los inputs de cantidad y precio
        cantidadInput.addEventListener('input', calcularPrecioTotal);
        precioInput.addEventListener('input', calcularPrecioTotal);
    }
}

// Función para calcular el precio total
function calcularPrecioTotal() {
    var fila = this.parentNode.parentNode; // Obtener la fila actual
    var cantidad = parseFloat(fila.querySelector('.iptCantidad').value);
    var precio = parseFloat(fila.querySelector('.iptPrecioCompra').value);

    // Verificar si los valores de cantidad y precio son válidos
    if (!isNaN(cantidad) && !isNaN(precio)) {
      var precioTotal = cantidad * precio;
      fila.querySelector('.precio-total').textContent = precioTotal.toFixed(0);
    } else {
      fila.querySelector('.precio-total').textContent = '0';
    }
    recalcularTotales();
}

function loadRowTable(respuesta) {
    table.row.add({
        'id': itemProducto,
        'codigo_producto': '<span class="codigo-producto">' + respuesta['codigoProducto'] + '</span>',
        'nombre_categoria': respuesta['nombreCategoria'],
        'descripcion_producto': respuesta['descripcionProducto'],
        'cantidad': '<input type="number" style="width:80px;" codigoProducto = "' + respuesta['codigoProducto'] + '" class="form-control text-center iptCantidad p-0 m-0" value="' + respuesta['cantidad'] + '">',
        'precio_compra_producto': '<input type="text" style="width:80px;" codigoProducto = "' + respuesta['codigoProducto'] + '" class="form-control text-center iptPrecioCompra p-0 m-0" value="' + respuesta['precioCompra'] + '">',
        'total': '<span class="precio-total">' + respuesta['total'] + '</span>',
        'acciones': "<center>" +
             "<span class='btnAumentarCantidad text-success px-1' style='cursor:pointer;' data-bs-toggle='tooltip' data-bs-placement='top' title='Aumentar Stock'> " +
             "<i class='fas fa-cart-plus fs-5'></i> " +
             "</span> " +
             "<span class='btnDisminuirCantidad text-warning px-1' style='cursor:pointer;' data-bs-toggle='tooltip' data-bs-placement='top' title='Disminuir Stock'> " +
             "<i class='fas fa-cart-arrow-down fs-5'></i> " +
             "</span> " +
            "<span class='btnEliminarproducto text-danger px-1'style='cursor:pointer;' data-bs-toggle='tooltip' data-bs-placement='top' title='Eliminar producto'> " +
            "   <i class='fas fa-trash fs-5'> </i> " +
            "</span>" +
            "</center>",
    }).draw();

    addFieldsEvents();
}