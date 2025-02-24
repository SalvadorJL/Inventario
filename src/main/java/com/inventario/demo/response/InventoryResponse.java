package com.inventario.demo.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InventoryResponse<T> {
	
	private String message;
    private T data;
    
	public InventoryResponse(String message, T data) {
		this.message = message;
		this.data = data;
	}
	public InventoryResponse() {
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
