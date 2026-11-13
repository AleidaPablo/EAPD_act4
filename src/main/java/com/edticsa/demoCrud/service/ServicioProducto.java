package com.edticsa.demoCrud.service;

import com.edticsa.demoCrud.dto.RespuestaProducto;
import com.edticsa.demoCrud.dto.SolicitudProducto;
import com.edticsa.demoCrud.model.Producto;
import com.edticsa.demoCrud.repository.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServicioProducto {

    private final ProductoRepository repositorioProducto;

    public Page<RespuestaProducto> listarPaginado(Pageable paginacion) {
        return repositorioProducto.findAll(paginacion).map(this::convertirARespuesta);
    }

    public RespuestaProducto obtenerPorId(Long id) {
        Producto producto = repositorioProducto.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con ID: " + id));
        return convertirARespuesta(producto);
    }

    public RespuestaProducto crear(SolicitudProducto solicitud) {
        Producto producto = Producto.builder()
                .nombre(solicitud.getNombre())
                .descripcion(solicitud.getDescripcion())
                .precio(solicitud.getPrecio())
                .stock(solicitud.getStock())
                .build();
        return convertirARespuesta(repositorioProducto.save(producto));
    }

    public RespuestaProducto actualizar(Long id, SolicitudProducto solicitud) {
        Producto producto = repositorioProducto.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con ID: " + id));

        producto.setNombre(solicitud.getNombre());
        producto.setDescripcion(solicitud.getDescripcion());
        producto.setPrecio(solicitud.getPrecio());
        producto.setStock(solicitud.getStock());

        return convertirARespuesta(repositorioProducto.save(producto));
    }

    public void eliminar(Long id) {
        if (!repositorioProducto.existsById(id)) {
            throw new EntityNotFoundException("Producto no encontrado con ID: " + id);
        }
        repositorioProducto.deleteById(id);
    }

    private RespuestaProducto convertirARespuesta(Producto producto) {
        return RespuestaProducto.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .precio(producto.getPrecio())
                .stock(producto.getStock())
                .build();
    }
}