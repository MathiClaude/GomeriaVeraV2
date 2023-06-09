console.log("Incluido mostrar hora")
function mostrarHora() {
  var fecha = new Date();
  var horas = fecha.getHours();
  var minutos = fecha.getMinutes();
  var segundos = fecha.getSeconds();

  var horaCompleta = horas + ":" + minutos + ":" + segundos;

  document.getElementById("clock").innerText = horaCompleta;
}

// Actualiza la hora cada segundo
setInterval(mostrarHora, 1000);