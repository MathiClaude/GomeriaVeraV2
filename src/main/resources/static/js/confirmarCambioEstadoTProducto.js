function confirmarCambioEstadoTProducto() {
  Swal.fire({
    title: 'ADVERTENCIA',
    text: 'Esto podría inhabilitar para todos los productos que pertenezcan a dicha categoría. ¿Estás seguro que quieres cambiar el estado? ',
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#d33',
    confirmButtonText: 'Sí, cambiar estado',
    cancelButtonText: 'Cancelar'
  }).then((result) => {
    if (result.isConfirmed) {
      // Realizar el cambio de estado aquí
      // ...
      // Habilitar o deshabilitar el select según el estado
      habilitarInput('inputSelectEstado', 'checkboxEstado');
    } else {
      // Restaurar el estado del checkbox si se cancela la operación
      document.getElementById('checkboxEstado').checked = false;
    }
  });
}