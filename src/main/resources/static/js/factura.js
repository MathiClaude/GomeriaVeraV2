var SUCCESS_CODE = "200";
// Start counting from the third row
var counter = 1;
let listaDocumentos =[];

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

    $("#insertDocRow").on("click", function (event) {
        event.preventDefault();

        var newRow = $("<tr>");
        var cols = '';

        // Table columns
        cols += '<th scrope="row">' + counter + '</th>';
        cols += '<td><input class="form-control rounded-0" type="text" name="c-documentos[]" placeholder="Documento"></td>';
        cols += '<td><input class="form-control rounded-0" type="text" name="c-tipos[]" placeholder="Tipo Doc."></td>';
        cols += '<td><input class="form-control rounded-0" type="text" name="c-montos[]" placeholder="Monto"></td>';
        cols += '<td><button class="btn btn-danger rounded-0" id ="deleteDocRow"><i class="fa fa-trash"></i></button</td>';

        // Insert the columns inside a row
        newRow.append(cols);

        // Insert the row inside a table
        $("table").append(newRow);

        // Increase counter after each row insertion
        counter++;
    });

    // Remove row when delete btn is clicked
    $("table").on("click", "#deleteDocRow", function (event) {
        $(this).closest("tr").remove();
        counter -= 1
    });
});

function addCellValuesInvoice(doc, tipo, monto, counter) {
        var newRow = $("<tr>");
        var cols = '';

        // Table columns
        cols += '<th scrope="row">' + counter + '</th>';
        cols += '<td><input class="form-control rounded-0" type="text" name="c-documentos[]" placeholder="Documento" value="' + doc + '"></td>';
        cols += '<td><input class="form-control rounded-0" type="text" name="c-tipos[]" placeholder="Tipo Doc." value="' + tipo + '"></td>';
        cols += '<td><input class="form-control rounded-0" type="text" name="c-montos[]" placeholder="Monto" value="' + monto + '"></td>';
       cols += '<td><button class="btn btn-danger rounded-0" id ="deleteDocRow"><i class="fa fa-trash"></i></button</td>';

        // Insert the columns inside a row
        newRow.append(cols);

        // Insert the row inside a table
        $("table").append(newRow);

}

function getCellsValues() {
    var table = document.getElementById('documentTable');
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



/*function agregarDocumentos(elemento){
    if(elemento.checked){
		if(parseInt(precio) < 1 ){
			Swal.fire({
				title: "No se puede seleccionar un producto con precio 0 "
			});
			elemento.checked=false;
			return;
		}
		listaDocumentos.push({"codigo":codigo,"idProducto":idProducto,"descripcion":descripcion,"precio":precio,"impuesto":impuesto,"cantidad":0})
	}else{
		listaDocumentos = listaDocumentos.filter((producto)=>producto.idProducto != idProducto)
	}

}*/

/*
async function guardarDatosPedido(){

	const TOTAL_EFECTIVO = document.getElementById('inputMontoCliente');
	const cliente = document.getElementById('InputCliente')
	if (cliente.value == ""){

		Swal.fire({
			title: "Debe elegir un cliente"
		});
		return;
	}
	let totalVenta = 0 ;
	listaProductos.forEach((prod)=>{
		totalVenta+=prod.cantidad*prod.precio
	})
	if(totalVenta == 0){
		Swal.fire({
			title: "Debe elegir al menos un producto para vender"
		});
		return;

	}
	
	let listaEnviar = "";
	for(let a of listaProductos){
		listaEnviar +=`${a.cantidad};${a.idProducto};${a.precio}|`
	}
	//VALIDACION DEL MONTO INGRESADO POR EL CLIENTE 
	validarMonto(removeNonNumeric(TOTAL_EFECTIVO.value));
	if(parseInt(removeNonNumeric(TOTAL_EFECTIVO.value)) < 1 || removeNonNumeric(TOTAL_EFECTIVO.value) == ''  ){
		return;
	} 
	console.log(TOTAL_EFECTIVO.value,'test');
	if((parseInt(removeNonNumeric(TOTAL_EFECTIVO.value)) - parseInt(removeNonNumeric(totalVenta))) <0) {
		Swal.fire({
			icon: 'error',
			title: 'Algo salió mal...',
			text: 'Monto ingresado menor al valor de la venta!',
		})
		console.log("testing")
		return;
	}
	const formData = new FormData();

	formData.append("idCliente", cliente.value );
	formData.append("montoVenta", removeNonNumeric(totalVenta));
	formData.append("totalVenta", removeNonNumeric(totalVenta));
	formData.append("ventaDetalle", listaEnviar);

	console.log(formData)
	//enviamos la cabecera
	let resp = await fetch('http://localhost:8080/realizarVenta/crear',{
		method:'POST',
		body: formData,
	})
	let respParseado = await resp.json();
	console.log(respParseado)
	let idVenta = respParseado.path.split("/")[2];
	console.log("http://localhost:8080/comprobante/"+idVenta+"/pdf");
	document.getElementById("datoComprobante").src="http://localhost:8080/comprobante/"+idVenta+"/pdf";
	const myModalAlternative = new bootstrap.Modal('#cobroModal',{})
	myModalAlternative.show(document.getElementById('cobroModal'));

}*/