package com.itb.inf3cm.pizzariabemmelhor.auth;



import com.itb.inf3cm.pizzariabemmelhor.dto.auth.AuthenticationRequest;
import com.itb.inf3cm.pizzariabemmelhor.dto.auth.AuthenticationResponse;
import com.itb.inf3cm.pizzariabemmelhor.dto.auth.RegisterRequest;
import com.itb.inf3cm.pizzariabemmelhor.exceptions.BadRequest;
import com.itb.inf3cm.pizzariabemmelhor.exceptions.Unauthorized;
import com.itb.inf3cm.pizzariabemmelhor.model.entity.Usuario;
import com.itb.inf3cm.pizzariabemmelhor.model.repository.UsuarioRepository;
import com.itb.inf3cm.pizzariabemmelhor.security.jwt.JwtService;
import com.itb.inf3cm.pizzariabemmelhor.security.token.Token;
import com.itb.inf3cm.pizzariabemmelhor.security.token.TokenRepository;
import com.itb.inf3cm.pizzariabemmelhor.security.token.TokenType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Service
public class AuthenticationService {
    private final UsuarioRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UsuarioRepository repository, TokenRepository tokenRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(RegisterRequest request) {

        // lógica minha, pensando na herança
        //Usuario usuario;
        String nomeClasse = String.valueOf(request.getTipoUsuario());
        nomeClasse = nomeClasse.toLowerCase();
        char primeiroCharMaiusculo = Character.toUpperCase(nomeClasse.charAt(0));
        nomeClasse = nomeClasse.substring(1);
        nomeClasse = primeiroCharMaiusculo + nomeClasse;


        try {
            var usuarioDb = repository.findByEmail(request.getEmail());
            if (usuarioDb.isPresent()) {
                throw new BadRequest("Já existe este email cadastrado em nossa base de dados");
            }

            Class<?> clazz = Class.forName("com.itb.inf3cm.pizzariabemmelhor.model.entity." + nomeClasse); // Obtém a classe
            Usuario usuario = (Usuario) clazz.newInstance(); // Instancia o objeto

            usuario.setCodStatus(true);
            usuario.setNome(request.getNome());
            usuario.setEmail(request.getEmail());
            usuario.setPassword(passwordEncoder.encode(request.getPassword()));
            usuario.setTipoUsuario(request.getTipoUsuario());
            var savedUser = repository.save(usuario);
            var jwtToken = jwtService.generateToken(savedUser);
            var refreshToken = jwtService.generateRefreshToken(savedUser);

            saveUserToken(savedUser, jwtToken);
            return new AuthenticationResponse(jwtToken, refreshToken);

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequest("Email ou Password Incorreto");
        }
        var user = repository.findByEmail(request.getEmail()).get();
        if(!user.isCodStatus()){
            throw new Unauthorized("Conta inativa, por favor procurar o administrador da conta");
        }
        //.orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return new AuthenticationResponse(jwtToken, refreshToken);
    }

    private void saveUserToken(Usuario usuario, String jwtToken) {

        var token = new Token();
        token.setUsuario(usuario);
        token.setToken(jwtToken);
        token.setTokenType(TokenType.BEARER);
        token.setExpired(false);
        token.setRevoked(false);
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(Usuario usuario) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(usuario.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = new AuthenticationResponse(accessToken, refreshToken);
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}