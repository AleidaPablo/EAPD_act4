package com.edticsa.demoCrud.config;

import com.edticsa.demoCrud.security.JwtFiltroAutenticacion;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class ConfiguracionSeguridad {

    private final JwtFiltroAutenticacion jwtFiltroAutenticacion;
    private final AuthenticationProvider proveedorAutenticacion;

    public ConfiguracionSeguridad(JwtFiltroAutenticacion jwtFiltroAutenticacion, AuthenticationProvider proveedorAutenticacion) {
        this.jwtFiltroAutenticacion = jwtFiltroAutenticacion;
        this.proveedorAutenticacion = proveedorAutenticacion;
    }

    @Bean
    public SecurityFilterChain cadenaFiltrosSeguridad(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(autorizacion -> autorizacion
                .requestMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement(sesion -> sesion
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authenticationProvider(proveedorAutenticacion)
            .addFilterBefore(jwtFiltroAutenticacion, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(excepciones -> excepciones
                .authenticationEntryPoint((peticion, respuesta, excepcionAuth) -> {
                    respuesta.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    respuesta.setContentType("application/json;charset=UTF-8");
                    respuesta.getWriter().write("{\"error\": \"No autorizado. Falta el token JWT o no es válido.\"}");
                })
            );

        return http.build();
    }
}