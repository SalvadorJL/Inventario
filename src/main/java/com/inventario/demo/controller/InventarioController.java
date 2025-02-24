package com.inventario.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventario.demo.model.Inventario;
import com.inventario.demo.model.Movimiento;
import com.inventario.demo.model.dto.InventarioAlertDTO;
import com.inventario.demo.model.dto.InventarioDTO;
import com.inventario.demo.model.dto.InventarioStoreDTO;
import com.inventario.demo.model.dto.MovimientoSbySDTO;
import com.inventario.demo.response.InventoryResponse;
import com.inventario.demo.service.InventarioService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class InventarioController {

	private final InventarioService inventarioService;

	public InventarioController(InventarioService inventarioService) {
		this.inventarioService = inventarioService;
	}
	
	@GetMapping("stores/")
	public ResponseEntity<InventoryResponse<Page<InventarioDTO>>> listAllInventarios (@PageableDefault(page = 0,size = 5) Pageable pageable) {
		Page<InventarioDTO> inventarios = inventarioService.listInventarios(pageable);
		InventoryResponse<Page<InventarioDTO>> response = new InventoryResponse<>("Inventario de todas las tiendas", inventarios);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("stores/{storeId}/inventory")
	public ResponseEntity<InventoryResponse<Page<InventarioStoreDTO>>> listByTiendas(@PageableDefault(page = 0,size = 5) Pageable pageable, @PathVariable String storeId) {
		Page<InventarioStoreDTO> inventario = inventarioService.inventarioById(pageable, storeId);
		InventoryResponse<Page<InventarioStoreDTO>> response = new InventoryResponse<>("Inventario de tienda obtenido", inventario);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("inventory/alerts")
	public ResponseEntity<InventoryResponse<Page<InventarioAlertDTO>>> getMethodName(@PageableDefault(page = 0,size = 5) Pageable pageable) {
		Page<InventarioAlertDTO> lowStock = inventarioService.listLowStock(pageable);
		InventoryResponse<Page<InventarioAlertDTO>> response = new InventoryResponse<>("Productos con bajo stock", lowStock);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("inventory/transfer")
	public ResponseEntity<InventoryResponse<MovimientoSbySDTO>> guardarTransferencia(@RequestBody MovimientoSbySDTO movimiento) {
		MovimientoSbySDTO movimientos = inventarioService.guardarTransferencias(movimiento);
		InventoryResponse<MovimientoSbySDTO> response = new InventoryResponse<>("Se realizo una transferencia", movimientos);
		return ResponseEntity.ok(response);
	}
	
	
}
