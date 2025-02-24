package com.inventario.demo.model.dto;

import java.math.BigDecimal;

public class ProductoDTO {

	private Long id;
	private String name;
	private String description;
	private String category;
	private BigDecimal price;
	private String sku;

	public ProductoDTO(String name, String description, String category, BigDecimal price, String sku) {
		super();
		this.name = name;
		this.description = description;
		this.category = category;
		this.price = price;
		this.sku = sku;
	}
	
	public ProductoDTO() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}
	
	@Override
	public String toString() {
		return "Producto [id=" + id + ", name=" + name + ", description=" + description + ", category=" + category
				+ ", price=" + price + ", sku=" + sku + "]";
	}
}
