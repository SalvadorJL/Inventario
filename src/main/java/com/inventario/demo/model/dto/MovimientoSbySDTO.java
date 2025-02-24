package com.inventario.demo.model.dto;

public class MovimientoSbySDTO {
	
	private ProductoIdDTO productoid;
	private String sourcestoreid;
	private String targetstoreid;
	private Integer quantity;
	private String type;
	
	public MovimientoSbySDTO(ProductoIdDTO productoid, String sourcestoreid, String targetstoreid, Integer quantity, String type) {
		this.productoid = productoid;
		this.sourcestoreid = sourcestoreid;
		this.targetstoreid = targetstoreid;
		this.quantity = quantity;
		this.type = type;
	}
	
	public MovimientoSbySDTO() {
	}

	public ProductoIdDTO getProductoid() {
		return productoid;
	}

	public void setProductoid(ProductoIdDTO productoid) {
		this.productoid = productoid;
	}

	public String getSourcestoreid() {
		return sourcestoreid;
	}

	public void setSourcestoreid(String sourcestoreid) {
		this.sourcestoreid = sourcestoreid;
	}

	public String getTargetstoreid() {
		return targetstoreid;
	}

	public void setTargetstoreid(String targetstoreid) {
		this.targetstoreid = targetstoreid;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "MovimientoSbySDTO [productoid=" + productoid + ", sourcestoreid=" + sourcestoreid + ", targetstoreid="
				+ targetstoreid + ", quantity=" + quantity + ", type=" + type + "]";
	}
}
