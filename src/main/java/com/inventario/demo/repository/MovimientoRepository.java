package com.inventario.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventario.demo.model.Movimiento;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

}
