package com.proyecto.is2.proyecto.controller.dto;

import lombok.Data;

@Data
public class UsuarioDTO {
    private String email;
    private String password;
    private String user;
    private Integer idRol;


    public UsuarioDTO(String user, Integer idRol , String email){
        this.email = email;
        this.user = user;
        this.idRol = idRol;
    }

    public UsuarioDTO(){
        
    }
}
