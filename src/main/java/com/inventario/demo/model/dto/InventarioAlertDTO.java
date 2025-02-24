package com.inventario.demo.model.dto;

public class InventarioAlertDTO {

	private Long id;
	private String nameProdcut;
	private String storeId;
	private Integer quantity;
	private Integer minStock;
	
	public InventarioAlertDTO(Long id, String nameProdcut, String storeId, Integer quantity, Integer minStock) {
		this.id = id;
		this.nameProdcut = nameProdcut;
		this.storeId = storeId;
		this.quantity = quantity;
		this.minStock = minStock;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNameProdcut() {
		return nameProdcut;
	}

	public void setNameProdcut(String nameProdcut) {
		this.nameProdcut = nameProdcut;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getMinStock() {
		return minStock;
	}

	public void setMinStock(Integer minStock) {
		this.minStock = minStock;
	}
}
