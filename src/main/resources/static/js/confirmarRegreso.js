function confirmarRegreso() {
    Swal.fire({
      title: 'Confirmación',
      text: '¿Estás seguro que quieres volver atrás?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        history.back();
      }
    });
  }
  function confirmarRegresoCaja() {
    Swal.fire({
      title: 'Confirmación',
      text: 'Está por abrir una caja ¿Estás seguro que quieres volver atrás?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        history.back();
      }
    });
  }