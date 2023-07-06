	console.log("Incluido buscador de tipo de producto")
/*Datos para buscador*/

window.onload=()=>{
	mostrarBuscador(false)
	let opciones = document.getElementsByClass("opcionesTipoProducto");
	console.log(opciones);
	for(let opcion of opciones){
		opcion.addEventListener("click",()=>console.log("test"));
		
	}
}

// Funcion para buscar y seleccionar un proveedor del buscador

function mostrarBuscador(estado){
	let opciones = document.getElementsByClassName("opcionesTipoProducto");
	let display = (estado)?"":"none";
	// console.log(opciones);
	for(let opcion of opciones){
		opcion.style.display=display
		
	}
}
function seleccionar(a){
	console.log(a.getAttribute('data-value'))
	document.getElementById("buscadorTipoProducto").value=a.innerHTML
	document.getElementById("InputTipoProducto").value= a.getAttribute('data-value')
}
function funcionFiltrar(valor) {
	if(datos == []){
		
	}
	let datosFiltrados =[]
	let listaOpciones = document.getElementsByClassName("opcionesTipoProducto");
	if(valor==""){
		datosFiltrados = datos
	}else{
		datosFiltrados = datos.filter((elemento)=>{return ((elemento.idProducto.toString()+""+elemento.descripcion.toUpperCase()) )})
	} 
	// console.log(datosFiltrados)
	// console.log(listaOpciones)
	
	for (let i = 0; i < listaOpciones.length; i++) {
		listaOpciones[i].removeAttribute("data-value")
		listaOpciones[i].innerHTML = "";
		listaOpciones[i].addEventListener("click",seleccionar);
		
	} 
	mostrarBuscador(false)
	for (let i = 0; i < listaOpciones.length; i++) {
		if(!datosFiltrados[i]){
			// console.log("test")
			break
		}
		// listaOpciones[i].removeEventListener("click",seleccionar);
		//que mira bobo
		
		listaOpciones[i].setAttribute("data-value",`${datosFiltrados[i].idTipoProducto}`)
		listaOpciones[i].innerHTML = `${datosFiltrados[i].descripcion} `;
		listaOpciones[i].style.display="";
		listaOpciones[i].setAttribute("onclick",seleccionar);
	}
	
	
}