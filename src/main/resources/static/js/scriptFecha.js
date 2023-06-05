<script>
    window.addEventListener('DOMContentLoaded', function() {
    var fechaActual = new Date();
    var dia = agregarCeros(fechaActual.getDate());
    var mes = agregarCeros(fechaActual.getMonth() + 1);
    var anio = fechaActual.getFullYear();
    
    var fechaCompleta = dia + "/" + mes + "/" + anio;
    
    document.getElementById("fecha").textContent = fechaCompleta;
    });

    function agregarCeros(valor) {
    if (valor < 10) {
        return "0" + valor;
    }
    return valor;
    }
</script>