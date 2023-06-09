console.log("Incluido buscador de caja")
/*Datos para buscador*/

window.onload=()=>{
	mostrarBuscador(false)
	let opciones = document.getElementsByClassName("opcionesCaja");
	console.log(opciones);
	for(let opcion of opciones){
		opcion.addEventListener("click",()=>console.log("test"));
		
	}
}

// Funcion para buscar y seleccionar un caja del buscador

function mostrarBuscador(estado){
	let opciones = document.getElementsByClassName("opcionesCaja");
	let display = (estado)?"":"none";
	// console.log(opciones);
	for(let opcion of opciones){
		opcion.style.display=display
		
	}
}
function seleccionar(a){
	console.log(a.getAttribute('data-value'))
	document.getElementById("buscadorCaja").value=a.innerHTML
	document.getElementById("idCaja").value= a.getAttribute('data-value')
}
function funcionFiltrar(valor) {
	if(datos == []){
		
	}
	let datosFiltrados =[]
	let listaOpciones = document.getElementsByClassName("opcionesCaja");
	if(valor==""){
		datosFiltrados = datos
	}else{
		datosFiltrados = datos.filter((elemento)=>{return ((elemento.descripcion.toUpperCase()).includes(valor.toUpperCase()) )})
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
		
		listaOpciones[i].setAttribute("data-value",`${datosFiltrados[i].idCaja}`)
		listaOpciones[i].innerHTML = `${datosFiltrados[i].descripcion}`;
		listaOpciones[i].style.display="";
		listaOpciones[i].setAttribute("onclick",seleccionar);
	}
	
	
}

