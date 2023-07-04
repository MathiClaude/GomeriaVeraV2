	console.log("Incluido buscador de cajero")
/*Datos para buscador*/

window.addEventListener("load",()=>{
	mostrarBuscadorCajero(false)
	let opcionesCajero = document.getElementsByClassName("opcionesCajero");
	console.log(opcionesCajero);
	for(let opcion of opcionesCajero){
		opcion.addEventListener("click",()=>console.log("test"));
		
	}
})

// Funcion para buscar y seleccionarCajero un proveedor del buscador

function mostrarBuscadorCajero(estado){
	let opciones = document.getElementsByClassName("opcionesCajero");
	let display = (estado)?"":"none";
	// console.log(opciones);
	for(let opcion of opciones){
		opcion.style.display=display
		
	}
}
function seleccionarCajero(a){
	console.log(a.getAttribute('data-value'))
	document.getElementById("buscadorCajero").value=a.innerHTML
	document.getElementById("idCajero").value= a.getAttribute('data-value')
}
function funcionFiltrarCajero(valor) {
	if(datosUsuario == []){
		
	}
	let datosFiltrados =[]
	let listaOpciones = document.getElementsByClassName("opcionesCajero");
	if(valor==""){
		datosFiltrados = datosUsuario
	}else{
		datosFiltrados = datosUsuario.filter((elemento)=>{return (((elemento.user.toString()+""+elemento.email.toUpperCase()).includes(valor.toUpperCase() )))})
	} 
	// console.log(datosFiltrados)
	// console.log(listaOpciones)
	
	for (let i = 0; i < listaOpciones.length; i++) {
		listaOpciones[i].removeAttribute("data-value")
		listaOpciones[i].innerHTML = "";
		listaOpciones[i].addEventListener("click",seleccionarCajero);
		
	} 
	mostrarBuscadorCajero(false)
	for (let i = 0; i < listaOpciones.length; i++) {
		if(!datosFiltrados[i]){
			// console.log("test")
			break
		}
		// listaOpciones[i].removeEventListener("click",seleccionarCajero);
		//que mira bobo
		
		listaOpciones[i].setAttribute("data-value",`${datosFiltrados[i].idRol}`)
		listaOpciones[i].innerHTML = `${datosFiltrados[i].user} - ${datosFiltrados[i].email} `;
		listaOpciones[i].style.display="";
		listaOpciones[i].setAttribute("onclick",seleccionarCajero);
	}
	
	
}