function confirmarRegreso() {
    if (confirm("¿Estás seguro de que deseas volver atrás?")) {
        history.back();
    }
}