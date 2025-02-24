package com.inventario.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.inventario.demo.model.Producto;

public interface ProductoService {
	
	public Page<?> listProductos(Pageable pageable, String categoria, Double precioMax, Double precioMin, String stock);
	public Page<?> listProductoStock(Pageable pageable, String stock);
	public Producto productoById(Long id);
	public Producto guardarProducto(Producto producto);
	public Producto editarProducto(Long id, Producto producto);
	public void eliminarProducto(Long id);

}
