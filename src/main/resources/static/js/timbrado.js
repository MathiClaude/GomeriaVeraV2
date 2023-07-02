
function functTimbradoUpdate() {
    let isValid = true;
    // timbrado
    let timbrado = $("#nroTimbrado");
    if(timbrado.val() == null || timbrado.val() == undefined || timbrado.val() === "") {
        timbrado.removeClass("is-valid");
        timbrado.addClass("is-invalid");
        isValid = false;
    } else {
        timbrado.removeClass("is-invalid");
        timbrado.addClass("is-valid");
    }

    // nroDesde
    let nroDesde = $("#nroDesde");
    if(nroDesde.val() == null || nroDesde.val() == undefined || nroDesde.val() === "") {
        nroDesde.removeClass("is-valid");
        nroDesde.addClass("is-invalid");
        isValid = false;
    } else {
        nroDesde.removeClass("is-invalid");
        nroDesde.addClass("is-valid");
    }

    // nroHasta
    let nroHasta = $("#nroHasta");
    if(nroHasta.val() == null || nroHasta.val() == undefined || nroHasta.val() === "") {
        nroHasta.removeClass("is-valid");
        nroHasta.addClass("is-invalid");
        isValid = false;
    } else {
        nroHasta.removeClass("is-invalid");
        nroHasta.addClass("is-valid");
    }

    // fchDesde
    let fchDesde = $("#fchDesde");
    if(fchDesde.val() == null || fchDesde.val() == undefined || fchDesde.val() === "") {
        fchDesde.removeClass("is-valid");
        fchDesde.addClass("is-invalid");
        isValid = false;
    } else {
        fchDesde.removeClass("is-invalid");
        fchDesde.addClass("is-valid");
    }

    // fchHasta
    let fchHasta = $("#fchHasta");
    if(fchHasta == null || fchHasta.val() == undefined || fchHasta.val() === "") {
        fchHasta.removeClass("is-valid");
        fchHasta.addClass("is-invalid");
        isValid = false;
    } else {
        fchHasta.removeClass("is-invalid");
        fchHasta.addClass("is-valid");
    }

    if(!isValid) return false;

    //  $.ajax({
    //      type: 'POST',
    //      url: appname + "timbrado/timbradoCrear",
    //      data: { timbrado : timbrado.val(), fchDesde : fchDesde.val(), fchHasta : fchHasta.val() },
    //      async: true,
    //  }).done(function(data) {
    //      console.log(data);
    //      if(data.code !== "OK") {
    //          showAlert('danger', data.message );
    //      } else {
    //         Swal.fire({
    //           position: 'top-end',
    //           icon: 'success',
    //           title: data.message,
    //           showConfirmButton: false,
    //           timer: 2000
    //         });
    //         setTimeout(() => {
    //             location.reload();
    //         }, 2000);
    //      }

    //  }).fail(function(request, status, error) {
    //      showAlert('danger', "Ha ocurrido un error inesperado." );
    //  });
    return true;
}

function crearTimbrado() {

    $.ajax({
        url: appname + "timbrado/timbradoCrear",
        method: "GET",
        dataType: 'json',
        success: function(respuesta) {
            if(respuesta.code == "OK") {
                $("#timbradoModal").modal("show");
            } else {
                Swal.fire(
                    'Advertencia',
                    respuesta.message,
                    'warning'
                );
            }
        },
        error: function(error) {
            Swal.fire(
                'Error!',
                'Ha ocurrido un error inesperado.',
                'error'
            );
        }
    });
}

function editarTimbrado(nroTimbrado) {
    Swal.fire({
      title: '¿Estás seguro de desactivar el timbrado?',
      text: "¡Ya no podrás revertir esto!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, desactivar!'
    }).then((result) => {
      if (result.isConfirmed) {
        $.ajax({
            type: 'POST',
            url: appname + "ventas/timbrado-desactivar",
            data: { 'nroTimbrado': nroTimbrado },
            async: true,
        }).done(function(data) {
            console.log(data);
            if(data.code == "OK") {
                Swal.fire({
                  position: 'top-end',
                  icon: 'success',
                  title: 'El timbrado ha sido desactivado.',
                  showConfirmButton: false,
                  timer: 2000
                });
                setTimeout(() => {
                  location.reload();
                }, 2000);
            } else {
                Swal.fire(
                  '¡Advertencia!',
                  data.message,
                  'warning'
                )
            }
        }).fail(function(request, status, error) {
            Swal.fire(
              '¡Error!',
              'Ha ocurrido un error inesperado.',
              'error'
            )
        });
      }
    })
}