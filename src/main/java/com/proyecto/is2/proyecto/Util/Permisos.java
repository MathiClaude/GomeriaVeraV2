package com.proyecto.is2.proyecto.Util;

public enum Permisos {
    CONNECTION_PRIVILEGE("CONNECTION_PRIVILEGE", "Permiso para ingresar al sistema."),

    WRITE_ADMINISTRATION_PRIVILEGE("WRITE_ADMINISTRATION_PRIVILEGE", "Permiso para agregar datos en el administrador."),
    READ_ADMINISTRATION_PRIVILEGE("READ_ADMINISTRATION_PRIVILEGE", "Permiso para solo lectura en el administrador."),

    WRITE_VENTAS_PRIVILEGE("WRITE_VENTAS_PRIVILEGE", "Permiso para agregar datos en las ventas."),
    READ_VENTAS_PRIVILEGE("READ_VENTAS_PRIVILEGE", "Permiso para solo lectura en las ventas."),

    WRITE_STOCK_PRIVILEGE("WRITE_STOCK_PRIVILEGE", "Permiso para agregar datos en el stock."),
    READ_STOCK_PRIVILEGE("READ_STOCK_PRIVILEGE", "Permiso para solo lectura en el stock."),

    WRITE_COMPRAS_PRIVILEGE("WRITE_COMPRAS_PRIVILEGE", "Permiso para agregar datos en las compras."),
    READ_COMPRAS_PRIVILEGE("READ_COMPRAS_PRIVILEGE", "Permiso para solo lectura en las compras."),
    ;

    public final String name;
    public final String desc;

    private Permisos(String privilege, String description) {
        this.name = privilege;
        this.desc = description;
    }
}
