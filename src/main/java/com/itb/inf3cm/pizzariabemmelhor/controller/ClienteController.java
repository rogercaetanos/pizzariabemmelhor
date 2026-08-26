package com.itb.inf3cm.pizzariabemmelhor.controller;


import com.itb.inf3cm.pizzariabemmelhor.auth.AuthenticationService;
import com.itb.inf3cm.pizzariabemmelhor.dto.auth.AuthenticationResponse;
import com.itb.inf3cm.pizzariabemmelhor.dto.auth.RegisterRequest;
import com.itb.inf3cm.pizzariabemmelhor.model.enums.TipoUsuario;
import com.itb.inf3cm.pizzariabemmelhor.model.services.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {


    private final ClienteService clienteService;
    private final AuthenticationService authenticationService;


    public ClienteController(ClienteService clienteService, AuthenticationService authenticationService) {
        this.clienteService = clienteService;
        this.authenticationService = authenticationService;
    }


    @PostMapping
    public ResponseEntity<AuthenticationResponse> registerCliente(@RequestBody RegisterRequest registerRequest) {
        registerRequest.setTipoUsuario(TipoUsuario.CLIENTE);
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.register(registerRequest));

    }


}
