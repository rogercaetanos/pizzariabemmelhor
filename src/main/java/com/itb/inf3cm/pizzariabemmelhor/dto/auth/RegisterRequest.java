package com.itb.inf3cm.pizzariabemmelhor.dto.auth;


import com.itb.inf3cm.pizzariabemmelhor.model.enums.TipoUsuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    private String nome;
    private String email;
    private String password;
    private TipoUsuario tipoUsuario;
}
