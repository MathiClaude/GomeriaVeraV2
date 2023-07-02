function convertirNumerosALetras(numero) {
  const unidades = ['', 'uno', 'dos', 'tres', 'cuatro', 'cinco', 'seis', 'siete', 'ocho', 'nueve'];
  const especiales = ['', 'once', 'doce', 'trece', 'catorce', 'quince', 'dieciséis', 'diecisiete', 'dieciocho', 'diecinueve'];
  const decenas = ['', 'diez', 'veinte', 'treinta', 'cuarenta', 'cincuenta', 'sesenta', 'setenta', 'ochenta', 'noventa'];
  const centenas = ['', 'ciento', 'doscientos', 'trescientos', 'cuatrocientos', 'quinientos', 'seiscientos', 'setecientos', 'ochocientos', 'novecientos'];

  if (numero === 0) {
    return 'cero';
  }

  if (numero < 0 || numero >= 1000000) {
    return 'Número fuera de rango';
  }

  let resultado = '';

  if (numero >= 1000) {
    const miles = Math.floor(numero / 1000);
    resultado += convertirNumerosALetras(miles) + ' mil ';
    numero %= 1000;
  }

  if (numero >= 100) {
    const centena = Math.floor(numero / 100);
    resultado += centenas[centena] + ' ';
    numero %= 100;
  }

  if (numero >= 20) {
    const decena = Math.floor(numero / 10);
    resultado += decenas[decena] + ' ';
    numero %= 10;
  }

  if (numero > 0) {
    if (numero >= 10 && numero <= 15) {
      resultado += especiales[numero - 10] + ' ';
    } else {
      resultado += unidades[numero] + ' ';
    }
  }

  return resultado.trim();
}
