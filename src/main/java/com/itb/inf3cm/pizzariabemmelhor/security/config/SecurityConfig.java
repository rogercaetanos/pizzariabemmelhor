package com.itb.inf3cm.pizzariabemmelhor.security.config;


import com.itb.inf3cm.pizzariabemmelhor.security.exceptions.CustomAccessDeniedHandler;
import com.itb.inf3cm.pizzariabemmelhor.security.exceptions.CustomAuthenticationEntryPoint;
import com.itb.inf3cm.pizzariabemmelhor.security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private static String[] WHITE_LIST = {
            "/api/v1/index",
            "/api/v2/api-docs",
            "/images/**"
    };

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter,
                          AuthenticationProvider authenticationProvider,
                          LogoutHandler logoutHandler,
                          CustomAuthenticationEntryPoint authenticationEntryPoint,
                          CustomAccessDeniedHandler accessDeniedHandler) {

        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
        this.logoutHandler = logoutHandler;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                //.cors(AbstractHttpConfigurer::disable)
                .cors(cors-> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests( req ->
                        req
                                .requestMatchers(WHITE_LIST).permitAll()

                                // PRODUTO
                                .requestMatchers(HttpMethod.GET,"/api/v1/produtos").permitAll()


                                // CLIENTE
                                .requestMatchers(HttpMethod.POST, "/api/v1/clientes").permitAll()

                                // PEDIDO

                                // CATEGORIA


                                .anyRequest().authenticated()
                        )
                .exceptionHandling(
                        exception ->
                                exception
                                        .authenticationEntryPoint(authenticationEntryPoint)
                                        .accessDeniedHandler(accessDeniedHandler)
                              )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

                .logout(logout -> logout
                        .logoutUrl("/api/v1/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                        );

        return http.build();
    }

    // Desenvolvimento local
    // Em produção, a configuração do CORS é feito no servidor de deploy, neste caso, Desconsiderar o código abaixo.

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // ORIGENS PERMITIDAS (ajuste conforme seu front-end)
        config.setAllowedOrigins(List.of(
                "http://localhost:8686", //  seu mobile por exemplo flutter
                "http://localhost:5173",  // seu web por exemplo react
                "http://localhost:5174"   // outro front-end
        ));

        // MÉTODOS HTTP PERMITIDOS
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // CABEÇALHOS E CREDENCIAIS
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        // REGISTRA A CONFIGURAÇÃO PARA TODOS OS ENDPOINTS
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}
