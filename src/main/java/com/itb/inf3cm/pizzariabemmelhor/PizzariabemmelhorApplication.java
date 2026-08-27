package com.itb.inf3cm.pizzariabemmelhor;

import com.itb.inf3cm.pizzariabemmelhor.auth.AuthenticationService;
import com.itb.inf3cm.pizzariabemmelhor.dto.auth.RegisterRequest;
import com.itb.inf3cm.pizzariabemmelhor.model.enums.TipoUsuario;
import com.itb.inf3cm.pizzariabemmelhor.model.services.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PizzariabemmelhorApplication {

    private final UsuarioService usuarioService;
    private final AuthenticationService authenticationService;

    public PizzariabemmelhorApplication(UsuarioService usuarioService, AuthenticationService authenticationService) {
        this.usuarioService = usuarioService;
        this.authenticationService = authenticationService;
    }

	public static void main(String[] args) {
		SpringApplication.run(PizzariabemmelhorApplication.class, args);

	}

    @Bean
    CommandLineRunner run() {

        return args -> {

            try {
                String emailAdmin = "admin@pizzaria.com.br";
                usuarioService.findByEmail(emailAdmin);
                System.out.println("Admin padrão já está cadastrado");
            } catch (Exception e) {
                authenticationService.register(new RegisterRequest("Administrador", "admin@pizzaria.com.br", "123", TipoUsuario.ADMIN));
            }
        };
    }

}
