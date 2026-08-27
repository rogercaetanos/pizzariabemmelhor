package com.itb.inf3cm.pizzariabemmelhor.auth;


import com.itb.inf3cm.pizzariabemmelhor.dto.auth.AuthenticationRequest;
import com.itb.inf3cm.pizzariabemmelhor.dto.auth.AuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


// Método para realizar o lOGIN, retorna o access_token que deve ser utilizado no Authorization das requisições protegidas

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }
}
