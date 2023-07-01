function convertirNumeroALetras(numero) {
  var unidades = [
    "", "Uno", "Dos", "Tres", "Cuatro", "Cinco", "Seis", "Siete", "Ocho", "Nueve", "Diez",
    "Once", "Doce", "Trece", "Catorce", "Quince", "Dieciséis", "Diecisiete", "Dieciocho", "Diecinueve"
  ];
  var decenas = [
    "", "", "Veinte", "Treinta", "Cuarenta", "Cincuenta", "Sesenta", "Setenta", "Ochenta", "Noventa"
  ];
  var centenas = [
    "", "Ciento", "Doscientos", "Trescientos", "Cuatrocientos", "Quinientos", "Seiscientos",
    "Setecientos", "Ochocientos", "Novecientos"
  ];
  
  if (numero === 0) {
    return "Cero";
  }
  
  if (numero < 0 || numero > 999999) {
    return "Número fuera de rango";
  }
  
  var letras = "";
  
  if (numero >= 1000 && numero <= 999999) {
    letras += convertirNumeroALetras(Math.floor(numero / 1000)) + " Mil ";
    numero %= 1000;
  }
  
  if (numero >= 100 && numero <= 999) {
    letras += centenas[Math.floor(numero / 100)] + " ";
    numero %= 100;
  }
  
  if (numero >= 20 && numero <= 99) {
    letras += decenas[Math.floor(numero / 10)] + " ";
    numero %= 10;
  }
  
  if (numero >= 1 && numero <= 19) {
    letras += unidades[numero] + " ";
  }
  
  return letras.trim();
}

// Ejemplo de uso
//var numero = 1234;
//var resultado = convertirNumeroALetras(numero);
//console.log(resultado);  // Salida: "Mil Doscientos Treinta y Cuatro"
// Obtén el valor numérico desde el atributo data-monto
var montoTotal = parseFloat(document.getElementById("totalPagarComprobante").dataset.monto);
// Convierte el valor numérico en letras utilizando la función convertirNumeroALetras
var montoTotalEnLetras = convertirNumeroALetras(montoTotal);
// Actualiza el contenido del elemento <span> con el valor convertido
document.getElementById("totalPagarComprobante").innerText = montoTotalEnLetras;
