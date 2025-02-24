package com.inventario.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import com.inventario.demo.model.Inventario;
import com.inventario.demo.model.Movimiento;
import com.inventario.demo.model.dto.InventarioAlertDTO;
import com.inventario.demo.model.dto.InventarioDTO;
import com.inventario.demo.model.dto.InventarioStoreDTO;
import com.inventario.demo.model.dto.MovimientoSbySDTO;

public interface InventarioService{

	public Page<InventarioDTO> listInventarios(Pageable pageable);
	public Page<InventarioStoreDTO> inventarioById(Pageable pageable,  String storeId);
	public Page<InventarioAlertDTO> listLowStock(Pageable pageable);
	public MovimientoSbySDTO guardarTransferencias(MovimientoSbySDTO movimiento);
}
