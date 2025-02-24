package com.inventario.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductoIdDTO {

	@JsonProperty
	private Long id;

	public ProductoIdDTO(Long id) {
		this.id = id;
	}
	
	public ProductoIdDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
