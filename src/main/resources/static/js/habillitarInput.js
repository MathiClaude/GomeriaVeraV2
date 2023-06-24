//función para los input de reportes
function habilitarInput(inputId, checkboxId) {
	var input = document.getElementById(inputId);
	var checkbox = document.getElementById(checkboxId);
	
	if (checkbox.checked) {
	  input.disabled = false; // Habilitar el input
	} else {
	  input.disabled = true; // Deshabilitar el input
	}
  }
  