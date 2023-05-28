
$(document).ready(function() {
    // table grilla
    let existTable = document.querySelector("#myTable");
    if(existTable != null) {
        var table = new DataTable('#myTable', {
            language: {
//                search: "Buscar:",
//                lengthMenu: "Mostrar _MENU_ registros",
//                emptyTable: "No hay resultados",
//                zeroRecords: "No hay resultados",
                url: '//cdn.datatables.net/plug-ins/1.13.4/i18n/es-ES.json',
            },
//            paginate: {
//                "first": "Primero",
//                "last": "Ultimo",
//                "next": "Siguiente",
//                "previous": "Anterior"
//            },
            retrieve: true,
            "lengthChange": true,
            info: true,
            searching: true,
        });
    }

    // table con input
    let existTableList = document.querySelector("#myTableList");
    if(existTableList != null) {
        $('#myTableList').DataTable({
            paging: false,
            ordering: false,
            info: false,
            // select: true,
            scrollY: 200,
            language: {
                search: "Buscar:",
                lengthMenu: "Mostrar _MENU_ registros",
                emptyTable: "No hay resultados",
                zeroRecords: "No hay resultados",
            }
        });
    }

    // Example starter JavaScript for disabling form submissions if there are invalid fields
    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    var forms = document.querySelectorAll('.needs-validation')
    if(forms != null) {
        // Loop over them and prevent submission
        Array.prototype.slice.call(forms)
            .forEach(function (form) {
            form.addEventListener('submit', function (event) {
                if (!form.checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }

                form.classList.add('was-validated')
            }, false)
        })
    }

    // formateo de campos
    // $('.celular').samask("(0900)-000-000");

});

function showAlert(typeAlert, messageAlert) {
    let messageTitle = "";
    let alertContent = $("#idAlert");

    switch(typeAlert) {
        case "success":
            titleAlert = "¡Operación exitosa!";
            break;
        case "warning":
            titleAlert = "¡Advertencia!";
            break;
        case "danger":
            titleAlert = "¡Ha ocurrido un error!";
            break;
        default:
            titleAlert = "¡Aviso!";
            break;
    }

    $("#idTitleAlert").text(titleAlert);
    $("#idMessageAlert").text(messageAlert);
    alertContent.addClass("bg-" + typeAlert);
    $('html, body').animate({scrollTop: 0}, 800);

    setTimeout(function() {
        alertContent.fadeIn(1500);
    }, 0);

    setTimeout(function() {
        alertContent.fadeOut(1500);
    }, 8000);
}

function closeAlert() {
    setTimeout(function() {
        $("#idAlert").fadeOut(0);
    }, 0);
}

function generarPDF(id, name) {
    //window.jsPDF = window.jspdf.jsPDF;
    // Obtener el contenido del elemento HTML que deseas convertir a PDF
    const htmlElement = document.getElementById(id);

    // Opciones de configuración para html2pdf
    const options = {
        filename: name + '.pdf',
        margin: 10,
        html2canvas: { scale: 2 },
        jsPDF: { unit: 'mm', format: 'a4', orientation: 'portrait' }
    };

    // Generar el PDF
    html2pdf().set(options).from(htmlElement).save();
}