console.log("Incluido buscador de permiso")
/*Datos para buscador*/

window.onload=()=>{
	mostrarBuscadorPermiso(false)
	let opciones = document.getElementsByClassName("opcionesPermiso");
	console.log(opciones);
	for(let opcion of opciones){
		opcion.addEventListener("click",()=>console.log("test"));
		
	}
}

// Funcion para buscar y seleccionar un caja del buscador

function mostrarBuscadorPermiso(estado){
	let opciones = document.getElementsByClassName("opcionesPermiso");
	let display = (estado)?"":"none";
	// console.log(opciones);
	for(let opcion of opciones){
		opcion.style.display=display
		
	}
}
function seleccionarPermiso(a){
	console.log(a.getAttribute('data-value'))
	document.getElementById("buscadorPermiso").value=a.innerHTML
	document.getElementById("InputPermiso").value= a.getAttribute('data-value')
}
function funcionFiltrarPermiso(valor) {
	if(datosPermiso == []){
		
	}
	let datosFiltrados =[]
	let listaOpciones = document.getElementsByClassName("opcionesPermiso");
	if(valor==""){
		datosFiltrados = datosPermiso
	}else{
		datosFiltrados = datosPermiso.filter((elemento)=>{return (( elemento.idRol.toString() + ""+ elemento.descripcion.toUpperCase()).includes(valor.toUpperCase()) )})
	} 
	// console.log(datosFiltrados)
	// console.log(listaOpciones)
	
	for (let i = 0; i < listaOpciones.length; i++) {
		listaOpciones[i].removeAttribute("data-value")
		listaOpciones[i].innerHTML = "";
		listaOpciones[i].addEventListener("click",seleccionarPermiso);
		
	} 
	mostrarBuscadorPermiso(false)
	for (let i = 0; i < listaOpciones.length; i++) {
		if(!datosFiltrados[i]){
			// console.log("test")
			break
		}
		// listaOpciones[i].removeEventListener("click",seleccionar);
		
		listaOpciones[i].setAttribute("data-value",`${datosFiltrados[i].idCaja}`)
		listaOpciones[i].innerHTML = `${datosFiltrados[i].descripcion}`;
		listaOpciones[i].style.display="";
		listaOpciones[i].setAttribute("onclick",seleccionarPermiso);
	}
	
	
}

