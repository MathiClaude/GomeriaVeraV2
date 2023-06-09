	console.log("Incluido buscador de proveedor")
/*Datos para buscador*/

window.onload=()=>{
	mostrarBuscador(false)
	let opciones = document.getElementsByClass("opcionesProveedor");
	console.log(opciones);
	for(let opcion of opciones){
		opcion.addEventListener("click",()=>console.log("test"));
		
	}
}

// Funcion para buscar y seleccionar un proveedor del buscador

function mostrarBuscador(estado){
	let opciones = document.getElementsByClassName("opcionesProveedor");
	let display = (estado)?"":"none";
	// console.log(opciones);
	for(let opcion of opciones){
		opcion.style.display=display
		
	}
}
function seleccionar(a){
	console.log(a.getAttribute('data-value'))
	document.getElementById("opcionesProveedor").value=a.innerHTML
	document.getElementById("InputProveedor").value= a.getAttribute('data-value')
}
function funcionFiltrar(valor) {
	if(datos == []){
		
	}
	let datosFiltrados =[]
	let listaOpciones = document.getElementsByClassName("opcionesProveedor");
	if(valor==""){
		datosFiltrados = datos
	}else{
		datosFiltrados = datos.filter((elemento)=>{return ((elemento.ruc.toString()+""+elemento.nombre.toUpperCase()+" "+elemento.razonSocial.toUpperCase()).includes(valor.toUpperCase()) )})
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
		
		listaOpciones[i].setAttribute("data-value",`${datosFiltrados[i].idProveedor}`)
		listaOpciones[i].innerHTML = `${datosFiltrados[i].ruc} - ${datosFiltrados[i].nombre} ${datosFiltrados[i].razonSocial}`;
		listaOpciones[i].style.display="";
		listaOpciones[i].setAttribute("onclick",seleccionar);
	}
	
	
}