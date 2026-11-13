package com.edticsa.demoCrud.controller;

import com.edticsa.demoCrud.dto.RespuestaProducto;
import com.edticsa.demoCrud.dto.SolicitudProducto;
import com.edticsa.demoCrud.service.ServicioProducto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/productos")
public class ControladorProducto {

    private final ServicioProducto servicioProducto;

    public ControladorProducto(ServicioProducto servicioProducto) {
        this.servicioProducto = servicioProducto;
    }

    @GetMapping
    public ResponseEntity<Page<RespuestaProducto>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable paginacion = PageRequest.of(page, size);
        return ResponseEntity.ok(servicioProducto.listarPaginado(paginacion));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaProducto> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(servicioProducto.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<RespuestaProducto> crear(@Valid @RequestBody SolicitudProducto solicitud) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicioProducto.crear(solicitud));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespuestaProducto> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody SolicitudProducto solicitud
    ) {
        return ResponseEntity.ok(servicioProducto.actualizar(id, solicitud));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        servicioProducto.eliminar(id);
        return ResponseEntity.ok(Map.of("mensaje", "Producto eliminado correctamente"));
    }
}