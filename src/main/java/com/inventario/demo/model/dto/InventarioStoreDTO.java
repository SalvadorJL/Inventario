package com.inventario.demo.model.dto;

public class InventarioStoreDTO {

	private Long productoId;
	private String productoName;
	private String tiendaId;
	private Integer cantidad;
	
	public InventarioStoreDTO(Long productoId, String productoName, String tiendaId, Integer cantidad) {
		this.productoId = productoId;
		this.productoName = productoName;
		this.tiendaId = tiendaId;
		this.cantidad = cantidad;
	}
	
	public InventarioStoreDTO() {};

	public Long getProductoId() {
		return productoId;
	}

	public void setProductoId(Long productoId) {
		this.productoId = productoId;
	}

	public String getProductoName() {
		return productoName;
	}

	public void setProductoName(String productoName) {
		this.productoName = productoName;
	}

	public String getTiendaId() {
		return tiendaId;
	}

	public void setTiendaId(String tiendaId) {
		this.tiendaId = tiendaId;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
}
