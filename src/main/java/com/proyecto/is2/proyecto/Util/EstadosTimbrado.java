package com.proyecto.is2.proyecto.Util;

public enum EstadosTimbrado {
    ACTIVO("Activo"),
    INACTIVO("Inactivo");

    private String name;

    private EstadosTimbrado(String name) {
        this.name = name;
    }
}
