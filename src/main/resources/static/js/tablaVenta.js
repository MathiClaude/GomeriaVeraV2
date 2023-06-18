console.log("Incluido tabla venta")
let listaProductos =[];
function moverProducto(idProducto,precio,impuesto,descripcion,elemento){
	if(elemento.checked){
		listaProductos.push({"idProducto":idProducto,"descripcion":descripcion,"precio":precio,"impuesto":impuesto,"cantidad":0})
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
		alert('debe elegir un cliente');
	}
	let totalVenta = 0 ;
	listaProductos.forEach((prod)=>{
		totalVenta+=prod.cantidad*prod.precio
	})
	// if(totalVenta == 0){
	// 	alert('Debe elegir al menos un producto para vender')
	// }
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