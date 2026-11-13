package com.edticsa.demoCrud.controller;

import com.edticsa.demoCrud.dto.RespuestaAutenticacion;
import com.edticsa.demoCrud.dto.SolicitudLogin;
import com.edticsa.demoCrud.dto.SolicitudRegistro;
import com.edticsa.demoCrud.service.ServicioAutenticacion;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class ControladorAutenticacion {

    private final ServicioAutenticacion servicioAutenticacion;

    @PostMapping("/register")
    public ResponseEntity<RespuestaAutenticacion> registrar(@Valid @RequestBody SolicitudRegistro solicitud) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicioAutenticacion.registrar(solicitud));
    }

    @PostMapping("/login")
    public ResponseEntity<RespuestaAutenticacion> iniciarSesion(@Valid @RequestBody SolicitudLogin solicitud) {
        return ResponseEntity.ok(servicioAutenticacion.iniciarSesion(solicitud));
    }
}