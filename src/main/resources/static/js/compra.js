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
			icon: 'Error',
			text: 'Pedido no valido!',
		})
	}else{
		confirmarCompra();
	}

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


	const formData = new FormData();
	formData.append("idProveedor", proveedor.value );
	formData.append("montoCompra", totalVenta);
	formData.append("totalCompra", totalVenta);
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