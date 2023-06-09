console.log("Incluido mostrar fecha")
function mostrarFecha() {
    var fecha = new Date();
    var dia = fecha.getDate();
    var mes = fecha.getMonth() + 1; // Los meses en JavaScript van de 0 a 11
    var anio = fecha.getFullYear();

    var fechaCompleta = dia + "/" + mes + "/" + anio;

    document.getElementById("date").innerText = fechaCompleta;
}

  // Actualiza la fecha cada segundo
  setInterval(mostrarFecha, 1000);