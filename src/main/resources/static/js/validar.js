<script>
    function validar(){
            window.onerror = function(message, url, line, column, error) {
                // Verificar si el mensaje de error contiene "404" o el código de estado HTTP 404
                if (message.includes("500") || (error && error.status === 500)) {
                // Personalizar el manejo del error 404 aquí
                console.log("No ha llenado ningun campo");
                // Puedes redirigir al usuario a una página de error personalizada, mostrar un mensaje, etc.
                }
            }
        }
</script>             