package com.edticsa.demoCrud.service;

import com.edticsa.demoCrud.dto.RespuestaAutenticacion;
import com.edticsa.demoCrud.dto.SolicitudLogin;
import com.edticsa.demoCrud.dto.SolicitudRegistro;
import com.edticsa.demoCrud.model.Usuario;
import com.edticsa.demoCrud.repository.UsuarioRepository;
import com.edticsa.demoCrud.security.JwtServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServicioAutenticacion {

    private final UsuarioRepository repositorioUsuario;
    private final PasswordEncoder codificadorContrasena;
    private final JwtServicio servicioJwt;
    private final AuthenticationManager gestorAutenticacion;

    public RespuestaAutenticacion registrar(SolicitudRegistro solicitud) {
        if (repositorioUsuario.existsByCorreo(solicitud.getCorreo())) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        Usuario nuevoUsuario = Usuario.builder()
                .nombre(solicitud.getNombre())
                .correo(solicitud.getCorreo())
                .contrasena(codificadorContrasena.encode(solicitud.getContrasena()))
                .build();

        repositorioUsuario.save(nuevoUsuario);
        String tokenJwt = servicioJwt.generarToken(nuevoUsuario);

        return RespuestaAutenticacion.builder()
                .token(tokenJwt)
                .nombre(nuevoUsuario.getNombre())
                .correo(nuevoUsuario.getCorreo())
                .build();
    }

    public RespuestaAutenticacion iniciarSesion(SolicitudLogin solicitud) {
        gestorAutenticacion.authenticate(
                new UsernamePasswordAuthenticationToken(
                        solicitud.getCorreo(),
                        solicitud.getContrasena()
                )
        );

        Usuario usuario = repositorioUsuario.findByCorreo(solicitud.getCorreo())
                .orElseThrow(() -> new IllegalArgumentException("Credenciales inválidas"));

        String tokenJwt = servicioJwt.generarToken(usuario);

        return RespuestaAutenticacion.builder()
                .token(tokenJwt)
                .nombre(usuario.getNombre())
                .correo(usuario.getCorreo())
                .build();
    }
}