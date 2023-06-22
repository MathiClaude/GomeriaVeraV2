console.log("Incluido tabla venta")
let listaProductos =[];
function moverProducto(idProducto,precio,impuesto,descripcion,codigo,elemento){
	if(elemento.checked){
		listaProductos.push({"codigo":codigo,"idProducto":idProducto,"descripcion":descripcion,"precio":precio,"impuesto":impuesto,"cantidad":0})
	}else{
		listaProductos = listaProductos.filter((producto)=>producto.idProducto != idProducto)
	}
	// console.log(idProducto,precio,impuesto,"+","test")
	// console.log(listaProductos)
}
function actualizarListaProductos(){
	for(let elemento of listaProductos){
		let cantidad = document.getElementById(elemento.idProducto).value
		elemento.cantidad = cantidad
	}
	actualizarTabla();
}
function actualizarTabla(){
	const TABLA = document.getElementById("productosSeleccionados");
	const TOTAL_PRODS = document.getElementById("totalProds");
	const TOTAL_PRODS2 = document.getElementById("totalPagar");
	const TOTAL_PRODS_COMPROBANTE = document.getElementById("totalPagarComprobante");
	const TOTAL_IVA = document.getElementById("idIva");
	
	TABLA.innerHTML = "";
	let totalProds = 0;
	let totalIva = 0;
	for(let elemento of listaProductos){
		let totalMonto = elemento.cantidad * elemento.precio
		let sumaIva = totalMonto*elemento.impuesto/100
		
		let fila = document.createElement("tr")

		let descripcion = document.createElement("td")
		descripcion.innerHTML = elemento.descripcion;

		let producto_id = document.createElement("td")
		producto_id.innerHTML = elemento.idProducto;

		let precio = document.createElement("td")
		precio.innerHTML = elemento.precio;

		let impuesto = document.createElement("td")
		impuesto.innerHTML = elemento.impuesto;

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
		fila.appendChild(impuesto)
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


function modificarCantidades(id,op){
	const campo = document.getElementById(id);
	console.log(campo.value)
	if(campo.value<1){
		campo.value = 0;
	}
	if(campo.value ==="" || !( campo.value<1 && op==="-")){
		campo.value = parseInt((campo.value===""?0:campo.value)) + (op=="+"?1:-1)
	}
}

function mostrarComprobante(){
	//Traer datos de cliente seleccionado
	const ID_CLIENTE_SELECCIONADO = document.getElementById('InputCliente').value
	const CLIENTE_SELECCIONADO = datos.filter((elemento)=>{return (elemento.idCliente.toString()==ID_CLIENTE_SELECCIONADO)})
	const RUC_COMPROBANTE = document.getElementById('rucComprobante')
	const DESC_CLIENTE_COMPROBANTE = document.getElementById('descClienteComprobante')

	RUC_COMPROBANTE.innerHTML = CLIENTE_SELECCIONADO[0].documentNumber
	DESC_CLIENTE_COMPROBANTE.innerHTML = CLIENTE_SELECCIONADO[0].name + " " + CLIENTE_SELECCIONADO[0].lastName

	//Cargar tabla de productos del comprobante 
	const TABLA_COMPROBANTE = document.getElementById("tablaProductosComprobante");
	const TOTAL_PRODS_COMPROBANTE = document.getElementById("totalPagarComprobante");
	const TOTAL_IVA = document.getElementById("totalIvaComprobante");
	const CANTIDAD_ITEMS = document.getElementById("cantidadItemsComprobante");
	const NETO_PAGAR = document.getElementById("netoPagarComprobante")
	const VUELTO = document.getElementById("vueltoComprobante")

	
	TABLA_COMPROBANTE.innerHTML = "";
	let totalProds = 0;
	let totalIva = 0;
	for(let elemento of listaProductos){
		let totalMonto = elemento.cantidad * elemento.precio
		let sumaIva = totalMonto*elemento.impuesto/100
		
		let fila = document.createElement("tr")
		let filaComprobante = document.createElement("tr")

		let descripcionComprobante = document.createElement("td")
		descripcionComprobante.innerHTML = elemento.descripcion;

		let precioComprobante = document.createElement("td")
		precioComprobante.innerHTML = elemento.precio;

		let cantidadComprobante = document.createElement("td")
		cantidadComprobante.innerHTML = elemento.cantidad;

		let totalComprobante =document.createElement("td")
		totalComprobante.innerHTML = totalMonto
		
		filaComprobante.appendChild(descripcionComprobante)
		filaComprobante.appendChild(cantidadComprobante)
		filaComprobante.appendChild(precioComprobante)
		filaComprobante.appendChild(totalComprobante)

		TABLA_COMPROBANTE.appendChild(filaComprobante)
		totalProds+=totalMonto;
		totalIva+=sumaIva;
	}
	TOTAL_PRODS_COMPROBANTE.innerHTML = totalProds;
	TOTAL_IVA.innerHTML = totalIva;
	CANTIDAD_ITEMS.innerHTML = listaProductos.length;
	NETO_PAGAR.innerHTML = totalProds;

}

async function guardarDatos(){
	//Obtenemos la cabecera
	/*private String montoVenta;
    private String fechaVenta;
    private String esActual;
    private BigDecimal montoTotal;
	cliente ;
	usuario;
	*/
	const cliente = document.getElementById('InputCliente')
	if (cliente.value == ""){
		//alert('debe elegir un cliente');
		Swal.fire({
			title: "Debe elegir un cliente"
		});
		return
	}
	let totalVenta = 0 ;
	listaProductos.forEach((prod)=>{
		totalVenta+=prod.cantidad*prod.precio
	})
	if(totalVenta == 0){
		Swal.fire({
			title: "Debe elegir al menos un producto para vender"
		});
		return
		// alert('Debe elegir al menos un producto para vender')
	}
	let listaEnviar = "";
	for(let a of listaProductos){
		listaEnviar +=`${a.cantidad};${a.idProducto};${a.precio}|`
	}


	const formData = new FormData();
	formData.append("idCliente", cliente.value );
	formData.append("montoVenta", totalVenta);
	formData.append("totalVenta", totalVenta);
	formData.append("ventaDetalle", listaEnviar);

	console.log(formData)
	//enviamos la cabecera
	let resp = await fetch('http://localhost:8080/realizarVenta/crear',{
		method:'POST',
		body: formData,
	})
	console.log(resp)
	console.log(resp.text())

}