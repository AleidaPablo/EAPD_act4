package com.edticsa.demoCrud.repository;

import com.edticsa.demoCrud.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}