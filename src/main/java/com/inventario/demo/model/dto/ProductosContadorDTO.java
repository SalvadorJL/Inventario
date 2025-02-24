package com.inventario.demo.model.dto;

public class ProductosContadorDTO {
	
	private Long idProducto;
	private Integer cantidad;
	
	public ProductosContadorDTO(Long idProducto, Integer cantidad) {
		this.idProducto = idProducto;
		this.cantidad = cantidad;
	}
	
	public Long getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
}
