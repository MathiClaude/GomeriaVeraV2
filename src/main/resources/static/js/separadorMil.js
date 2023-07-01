//función para validar los separadores de mil en venta
function formatNumber() {
    var campoTotal = document.getElementById("totalPagar");
    var valor = campoTotal.value.replace(/,/g, ""); // Eliminar los separadores de miles actuales
    var valorFormateado = new Intl.NumberFormat().format(valor); // Formatear el valor con separadores de miles
    campoTotal.value = valorFormateado;
  }