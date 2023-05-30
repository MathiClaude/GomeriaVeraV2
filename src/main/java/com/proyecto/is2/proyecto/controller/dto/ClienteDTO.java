package com.proyecto.is2.proyecto.controller.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class ClienteDTO {
    private Long idCliente;
    private String name;
    private String lastName;
    private Long documentType;
    private String documentNumber;
    private String email;
    private String telephone;


}
