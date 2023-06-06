var SUCCESS_CODE = "200";
// Start counting from the third row
var counter = 1;


function functProveedorUpdate() {
    let isValid = true;
    // documento
    let documento = $("#ruc");
    if(documento == null || documento.val() == undefined || documento.val() === "") {
        documento.removeClass("is-valid");
        documento.addClass("is-invalid");
        isValid = false;
    } else {
        documento.removeClass("is-invalid");
        documento.addClass("is-valid");
    }

    // nombre
    let nombre = $("#nombre");
    if(nombre == null || nombre.val() == undefined || nombre.val() === "") {
        nombre.removeClass("is-valid");
        nombre.addClass("is-invalid");
        isValid = false;
    } else {
        nombre.removeClass("is-invalid");
        nombre.addClass("is-valid");
    }

    // correo
    let correo = $("#correo");
    if(correo == null || correo.val() == undefined || correo.val() === "") {
        correo.removeClass("is-valid");
        correo.addClass("is-invalid");
        $("#error-correo").text("Este campo es obligatorio.");
        isValid = false;
    } else {
        if(!validEmail(correo.val())) {
            correo.removeClass("is-valid");
            correo.addClass("is-invalid");
            $("#error-correo").text("El formato del correo es inválido.");
            isValid = false;
        } else {
            correo.removeClass("is-invalid");
            correo.addClass("is-valid");
        }
    }

    $("#telefono").addClass("is-valid");
    $("#direccion").addClass("is-valid");

    if(!isValid) return false;

    //let id = $("#idPersona").val();
    /*let tipoDocumento = $("#tipoDocumento").val();

    $.ajax({
        type: 'GET',
        url: appname + "personas/validar-form",
        data: { 'id': id, 'documento' : documento.val(), 'tipoDocumento' : tipoDocumento, 'correo' : correo.val() },
        async: true,
    }).done(function(data) {
        console.log(data);
        if(data.code !== SUCCESS_CODE) {
            showAlert('danger', data.message );
        } else {
            $("#form-proveedor-update").submit();
        }

    }).fail(function(request, status, error) {
        showAlert('danger', "Ha ocurrido un error inesperado." );
    });*/

    $("#form-proveedor-update").submit();

}

// valida si tiene formato de correo
function validEmail(email) {
  var validRegex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;

  return (email.match(validRegex) !== null);
}

$(function () {

    $("#insertRow").on("click", function (event) {
        event.preventDefault();

        var newRow = $("<tr>");
        var cols = '';

        // Table columns
        cols += '<th scrope="row">' + counter + '</th>';
        cols += '<td><input class="form-control rounded-0" type="text" name="c-nombres[]" placeholder="Nombre"></td>';
        cols += '<td><input class="form-control rounded-0" type="text" name="c-telefonos[]" placeholder="Teléfono"></td>';
        cols += '<td><input class="form-control rounded-0" type="text" name="c-correos[]" placeholder="Correo"></td>';
        cols += '<td><button class="btn btn-danger rounded-0" id ="deleteRow"><i class="fa fa-trash"></i></button</td>';

        // Insert the columns inside a row
        newRow.append(cols);

        // Insert the row inside a table
        $("table").append(newRow);

        // Increase counter after each row insertion
        counter++;
    });

    // Remove row when delete btn is clicked
    $("table").on("click", "#deleteRow", function (event) {
        $(this).closest("tr").remove();
        counter -= 1
    });
});

function addCellValues(nombre, telefono, correo, counter) {
        var newRow = $("<tr>");
        var cols = '';

        // Table columns
        cols += '<th scrope="row">' + counter + '</th>';
        cols += '<td><input class="form-control rounded-0" type="text" name="c-nombres[]" placeholder="Nombre" value="' + nombre + '"></td>';
        cols += '<td><input class="form-control rounded-0" type="text" name="c-telefonos[]" placeholder="Teléfono" value="' + telefono + '"></td>';
        cols += '<td><input class="form-control rounded-0" type="text" name="c-correos[]" placeholder="Correo" value="' + correo + '"></td>';
        cols += '<td><button class="btn btn-danger rounded-0" id ="deleteRow"><i class="fa fa-trash"></i></button</td>';

        // Insert the columns inside a row
        newRow.append(cols);

        // Insert the row inside a table
        $("table").append(newRow);

}

function getCellsValues() {
    var table = document.getElementById('contactsTable');
    var lista = [];

    for(let i = 1; i < table.rows.length; i++) {
        let tmp = [];
        // excluir columna de numeracion y boton
        for(let k = 1; k < table.rows[i].cells.length - 1; k++) {
            let value = table.rows[i].cells[k].getElementsByTagName('input')[0].value;
            console.log(value);
            tmp.push(value);
        }
        lista.push(tmp);
    }
    return lista;``
}