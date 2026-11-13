package com.edticsa.demoCrud.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaAutenticacion {
    private String token;
    private String nombre;
    private String correo;
}