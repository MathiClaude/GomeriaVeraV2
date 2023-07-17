console.log("Incluido tabla coimpra")
let listaProductosCompra =[];
function moverProductoCompra(idProducto,precio,impuesto,descripcion,codigo,elemento){
	if(elemento.checked){
		listaProductosCompra.push({"codigo":codigo,"idProducto":idProducto,"descripcion":descripcion,"precio":precio,"impuesto":impuesto,"cantidad":0})
	}else{
		listaProductosCompra = listaProductosCompra.filter((producto)=>producto.idProducto != idProducto)
	}
	// console.log(idProducto,precio,impuesto,"+","test")
	// console.log(listaProductosCompra)
}
function actualizarListaProductosCompras(){
	/*Swal.fire({
		title: "Actuzalizando..."
	});*/
	for(let elemento of listaProductosCompra){
		let cantidad = document.getElementById(elemento.idProducto).value
		let precioUnidad = document.getElementById(elemento.precio).value
		alert (cantidad)
		alert(precioUnidad)
		elemento.precio = precioUnidad
		elemento.cantidad = cantidad
	}
	actualizarCompra();
}
function actualizarCompra(){
	const TABLA = document.getElementById("productosSeleccionadosCompras");
	const TOTAL_PRODS = document.getElementById("totalProdsCompras");
	const TOTAL_PRODS2 = document.getElementById("totalPagarCompra");
	const TOTAL_IVA = document.getElementById("idIvaCompra");
	TABLA.innerHTML = "";
	let totalProds = 0;
	let totalIva = 0;

	for(let elemento of listaProductosCompra){
		let totalMonto = elemento.cantidad * elemento.precio
		let sumaIva = totalMonto*elemento.impuesto/100
		
		let fila = document.createElement("tr")
		let descripcion = document.createElement("td")
		descripcion.innerHTML = elemento.descripcion;
		let producto_id = document.createElement("td")
		producto_id.innerHTML = elemento.idProducto;
		let precio = document.createElement("td")
		precio.innerHTML = elemento.precio;
		/*let impuesto = document.createElement("td")
		impuesto.innerHTML = elemento.impuesto;*/
		let cantidad = document.createElement("td")
		cantidad.innerHTML = elemento.cantidad;
		let codigo = document.createElement("td")
		codigo.innerHTML = elemento.codigo;
		let total =document.createElement("td")
		total.innerHTML = totalMonto
		let totalImpuesto =document.createElement("td")
		totalImpuesto.innerHTML = sumaIva
		
		fila.appendChild(codigo)
		fila.appendChild(descripcion)
		fila.appendChild(cantidad)
		fila.appendChild(precio)
		//fila.appendChild(impuesto)
		fila.appendChild(total)
		fila.appendChild(totalImpuesto)
		TABLA.appendChild(fila)
		totalProds+=totalMonto;
		totalIva+=sumaIva;
	}

	
	TOTAL_PRODS.value = totalProds;
	TOTAL_PRODS2.value = totalProds;
	TOTAL_IVA.value = totalIva;
	
	}

function modificarCantidadesPrd(id,op){
	const campo = document.getElementById(id);
	console.log(campo.value)
	if(campo.value<1){
		campo.value = 0;
	}
	if(campo.value ==="" || !( campo.value<1 && op==="-")){
		campo.value = parseInt((campo.value===""?0:campo.value)) + (op=="+"?1:-1)
	}
}
function confirmarCompra(){
	Swal.fire({
		title: 'Finalizar Pedido?',
		text: "Al confirmar el pedido, ya no podra cambiar los datos",
		icon: 'warning',
		showCancelButton: true,
		cancelButtonText: 'Regresar al pedido',
		confirmButtonColor: '#40A33C',
		cancelButtonColor: '#003EFE',
		confirmButtonText: 'Finalizar'
	  }).then((result) => {
		if (result.isConfirmed) {
			guardarDatosCompra();
			Swal.fire(
			'Pedido Finalizado!',
			'Su pedido se registro correctamente',
			'success'
		  )
		}
	  })
}

function validarPedido(){
	var totalPagar = document.getElementById("totalProdsCompras").value;
	//alert(totalPagar)
	if(validarMonto(totalPagar) == 0 ){	
		Swal.fire({
			icon: 'error',
			text: 'Pedido no valido!',
		})
	}else{
		confirmarCompra();
	}

}

function validarProvyProd(){
	var prov = document.getElementById("InputProveedor").value;
	var prod = document.getElementById("totalProdsCompras").value;
	alert(prov);
	alert(prod);
	
	if( prov == "" ){

			if (validarMonto(prod) == 0) {
				Swal.fire({
					icon: 'error',
					text: 'Debe seleccionar Proveedor y/o Producto!',
				})
			}	
	}else{
		document.getElementById("divNC").style.display = "block";
	}
	obtenerNCProveedor();
	return mostrarNC;
}


async function obtenerNCProveedor(){
	var idProveedor = document.getElementById("InputProveedor").value;
  	var url = "http://localhost:8080/realizarCompra/" + idProveedor + "/notaCredito";
	var montoTotalPedido = document.getElementById("totalProdsCompras").value;
	

  try {
    let response = await fetch(url);
    let facturaList = await response.json();

    var selectElement = document.getElementById("ncSelectId");
    selectElement.innerHTML = ""; // Limpiar opciones existentes

    facturaList.forEach(function (factura) {
	if (factura.monto < montoTotalPedido){
		var option = document.createElement("option");
		option.value = factura.idFactura;
		option.text = factura.tipo + "-" + factura.monto;
		selectElement.appendChild(option);
	}
    });

	if(selectElement.options.length === 0){
			
		//alert('no hay no existe')
		Swal.fire({
			icon: 'warning',
			text: 'No hay Notas de Creditos disponibles para el monto total del Pedido ',
		})

		document.getElementById("divNC").style.display = "none";
	}

  } catch (error) {
    console.error(error);
  }
}

async function ValidarMontoNC(selectElement) {
	//se llama nuevamente a esta funcion para que traiga el monto original, ya que, si ya me calculó el 
	//monto total y al final quiero usar otra NC, realiza la validación con el monto total que ya tiene 
	//el descuento de la NC anterior y no con el monto original
	actualizarCompra();

	var montoTotalPedidoElement = document.getElementById("totalProdsCompras");
	var montoTotalPedido = parseFloat(montoTotalPedidoElement.value);
	var montoSeleccionado = parseFloat(selectElement.options[selectElement.selectedIndex].text.split("-")[1]);
	
	alert(montoSeleccionado)

	if (montoSeleccionado > montoTotalPedido) {
	  alert("El monto seleccionado no puede ser mayor que el monto total del pedido.");
	  obtenerNCProveedor();
	} else {
	  var nuevoMontoTotal = montoTotalPedido - montoSeleccionado;
  
	  montoTotalPedidoElement.value = nuevoMontoTotal;
  
	  alert(nuevoMontoTotal); 
  

	}
  }


async function obtenerNC(){
	var idProveedor = document.getElementById("ncSelectId").value;
	formData.append("idProveedor", proveedor.value );
	console.log(idProveedor)

	console.log(formData)
	//enviamos la cabecera
	let resp = await fetch('http://localhost:8080/realizarCompra/notaCredito/'+idProveedor,{
		method:'POST',
		//body: formData,
	})
}

async function guardarDatosCompra(){
	//Obtenemos la cabecera
	/*private String montoVenta;
    private String fechaVenta;
    private String esActual;
    private BigDecimal montoTotal;
	cliente ;
	usuario;
	*/
	const proveedor = document.getElementById('InputProveedor')
	if (proveedor.value == ""){
		//alert('Debe elegir un proveedor');//cambiar
		Swal.fire({
			title: "Debe elegir un proveedor"
		});
	}
	let totalVenta = 0 ;
	listaProductosCompra.forEach((prod)=>{
		totalVenta+=prod.cantidad*prod.precio
	})
	// if(totalVenta == 0){
	// 	alert('Debe elegir al menos un producto para vender')
	// }
	let listaEnviar = "";
	for(let a of listaProductosCompra){
		listaEnviar +=`${a.cantidad};${a.idProducto};${a.precio}|`
	}

	let  totalPagar = document.getElementById("totalProdsCompras").value;
	alert(totalPagar)
	const formData = new FormData();
	formData.append("idProveedor", proveedor.value );
	formData.append("montoCompra", totalVenta);
	formData.append("totalCompra", totalPagar);
	//formData.append("totalCompra", totalVenta);
	formData.append("compraDetalle", listaEnviar);

	console.log(formData)
	//enviamos la cabecera
	let resp = await fetch('http://localhost:8080/realizarCompra/crear',{
		method:'POST',
		body: formData,
	})
	console.log(resp)
	console.log(resp.text())

}

function validarPago(monto){
	var totalPagar = document.getElementById("totalPagarCompra").value;
	//alert(totalPagar)
	if(validarMonto(monto) == 0){
		document.getElementById('inputPago').value = totalPagar;
	}else{
		if(monto < totalPagar){
			//retirar para multipagos
			Swal.fire({
				icon: 'Error',
				text: 'Monto ingresado inferior al monto total!',
			})
			document.getElementById('inputPago').value = totalPagar;
		}
		
	}

}



function validarEstadoPedido(){
	//console.log("ESTRAAAAAAA")
	var estadoFinal = document.getElementById('inputSelectEstado').value;
	//alert(estadoFinal);
	if(estadoFinal == 'RECEPCIONADO' || estadoFinal == 'ANULADO'){
       //
		//alert("RECEPCIONADO");
		Swal.fire({
			title: 'Cambiar estado?',
			text: "Al cambiar a ese estado,no se podra revertir el cambio",
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: 'Cambiar'
		  }).then((result) => {
			if (result.isConfirmed) {
				
			  Swal.fire(
				'Pedido Guardado!',
				'El pedido fue guardado exitosamente',
				'success'
			  )
			  $("#form-pedido-update").submit();
			}
		  })

	}
	document.getElementById('inputEstado').innerHTML = estadoFinal;
	return;
}