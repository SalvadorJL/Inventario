package com.inventario.demo.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inventario.demo.model.Producto;
import com.inventario.demo.model.dto.ProductoStockDTO;
import com.inventario.demo.response.InventoryResponse;
import com.inventario.demo.service.ProductoService;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

	private final ProductoService productoService;

	public ProductoController(ProductoService productoService) {
		this.productoService = productoService;
	}
	
	@GetMapping("")
	public ResponseEntity<InventoryResponse<Page<?>>> listarProductos(
			@PageableDefault(page = 0,size = 5) Pageable pageable, 
			@RequestParam(required = false) String categoria,
			@RequestParam(required = false) Double precioMax,
			@RequestParam(required = false) Double precioMin,
			@RequestParam(required = false) String stock
			) {
		Page<?> productos = productoService.listProductos(pageable, categoria, precioMax, precioMin, stock);
		InventoryResponse<Page<?>> response = new InventoryResponse<>("Lista de productos obtenida", productos);
		return ResponseEntity.ok(response);
	};
	
//	@GetMapping("")
//	public ResponseEntity<InventoryResponse<Page<?>>> listarProductos(
//			@PageableDefault(page = 0,size = 5) Pageable pageable, 
//			@RequestParam(required = false) String stock
//			) {
//		Page<?> productos = productoService.listProductoStock(pageable, stock);
//		InventoryResponse<Page<?>> response = new InventoryResponse<>("Lista de productos obtenida", productos);
//		return ResponseEntity.ok(response);	
//	};
	
	@GetMapping("/{id}")
	public ResponseEntity<InventoryResponse<Producto>> obtenerProducto(@PathVariable Long id) {
		Producto producto = productoService.productoById(id);
		InventoryResponse<Producto> response = new InventoryResponse<>("producto obtenido", producto);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("")
	public ResponseEntity<InventoryResponse<Producto>> guardarProducto(@RequestBody Producto producto) {
		Producto nuevoProducto = productoService.guardarProducto(producto);
		InventoryResponse<Producto> response = new InventoryResponse<>("producto agregado", nuevoProducto);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<InventoryResponse<Producto>> actualizarProducto(@PathVariable Long id, @RequestBody Producto productoEd) {
        Producto productoActualizado = productoService.editarProducto(id, productoEd);
        InventoryResponse<Producto> response = new InventoryResponse<>("producto actualizado", productoActualizado);
        return ResponseEntity.ok(response);
	}
	
	 @DeleteMapping("/{id}")
	 public ResponseEntity<InventoryResponse<Void>> borrarProducto(@PathVariable Long id){
		productoService.eliminarProducto(id);
		InventoryResponse<Void> response = new InventoryResponse<>("producto borrado", null);
		return ResponseEntity.ok(response);
	 }
}
