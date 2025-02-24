package com.inventario.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventario")
public class Inventario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("id")
	private Long id;
	@ManyToOne
    @JoinColumn(name = "productid", nullable = false)
	private Producto producto;
	@Column(nullable = false, length = 50)
	private String storeid;
	@Column(nullable = false)
	private Integer quantity;
	@Column(nullable = false)
	private Integer minstock;
	
	public Inventario(Producto producto, String storeid, Integer quantity, Integer minstock) {
		this.producto = producto;
		this.storeid = storeid;
		this.quantity = quantity;
		this.minstock = minstock;
	}
	
	public Inventario(Long id, Producto producto, String storeid, Integer quantity, Integer minstock) {
		this.id = id;
		this.producto = producto;
		this.storeid = storeid;
		this.quantity = quantity;
		this.minstock = minstock;
	}
	
	public Inventario() { }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Producto getProductId() {
		return producto;
	}

	public void setProductId(Producto producto) {
		this.producto = producto;
	}

	public String getStoreId() {
		return storeid;
	}

	public void setStoreId(String storeid) {
		this.storeid = storeid;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getMinStock() {
		return minstock;
	}

	public void setMinStock(Integer minstock) {
		this.minstock = minstock;
	}

	@Override
	public String toString() {
		return "Inventario [id=" + id + ", producto=" + producto + ", storeid=" + storeid + ", quantity=" + quantity
				+ ", minstock=" + minstock + "]";
	}
}
