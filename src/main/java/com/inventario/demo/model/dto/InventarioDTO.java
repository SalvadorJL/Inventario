package com.inventario.demo.model.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.inventario.demo.model.Inventario;

public class InventarioDTO{

	
	private Long id;
	private Long productId;
	private String productName;
	private String storeId;
	private Integer quantity;
	private Integer minStock;
	
	public InventarioDTO(Long id, Long productId, String productName, String storeId, Integer quantity, Integer minStock) {
		this.id = id;
		this.productId = productId;
		this.productName = productName;
		this.storeId = storeId;
		this.quantity = quantity;
		this.minStock = minStock;
	}
	
	public InventarioDTO(Inventario inventario) {
		this.id = inventario.getId();
		this.productId = inventario.getProductId().getId();
		this.productName = inventario.getProductId().getName();
		this.storeId = inventario.getStoreId();
		this.quantity = inventario.getQuantity();
		this.minStock = inventario.getMinStock();
	}
	
	public InventarioDTO() { }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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
