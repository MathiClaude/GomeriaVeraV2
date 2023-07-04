console.log("Incluido buscador de cliente")
/*Datos para buscador*/

window.addEventListener("load",()=>{
	mostrarBuscador(false)
	let opciones = document.getElementsByClassName("opcionesCliente");
	console.log(opciones);
	for(let opcion of opciones){
		opcion.addEventListener("click",()=>console.log("test"));
		
	}
})

// Funcion para buscar y seleccionar un cliente del buscador

function mostrarBuscador(estado){
	let opciones = document.getElementsByClassName("opcionesCliente");
	let display = (estado)?"":"none";
	// console.log(opciones);
	for(let opcion of opciones){
		opcion.style.display=display
		
	}
}

function seleccionar(a){
	console.log(a.getAttribute('data-value'))
	document.getElementById("buscadorCliente").value=a.innerHTML
	document.getElementById("InputCliente").value= a.getAttribute('data-value')
}

function funcionFiltrar(valor) {
	if(datos == []){
		
	}
	let datosFiltrados =[]
	let listaOpciones = document.getElementsByClassName("opcionesCliente");
	if(valor==""){
		datosFiltrados = datos
	}else{
		datosFiltrados = datos.filter((elemento)=>{return ((elemento.documentNumber.toString()+""+elemento.name.toUpperCase()+" "+elemento.lastName.toUpperCase()).includes(valor.toUpperCase()) )})
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
		
		listaOpciones[i].setAttribute("data-value",`${datosFiltrados[i].idCliente}`)
		listaOpciones[i].innerHTML = `${datosFiltrados[i].documentNumber} - ${datosFiltrados[i].name} ${datosFiltrados[i].lastName}`;
		listaOpciones[i].style.display="";
		listaOpciones[i].setAttribute("onclick",seleccionar);
	}
	
	
}