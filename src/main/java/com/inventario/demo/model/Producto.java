package com.inventario.demo.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "producto")
public class Producto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("id")
	private Long id;
	
	@Column(nullable = false, length = 255)
	private String name;
	
	@Column(nullable = false, columnDefinition = "TEXT")
	private String description;
	
	@Column(nullable = false, length = 100)
	private String category;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal price;
	
	@Column(nullable = false, unique = true, length = 50)
	private String sku;
	
	// Relación uno-a-muchos con Inventario
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Inventario> inventarios;

    // Relación uno-a-muchos con Movimiento
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Movimiento> movimientos = new ArrayList<>();

	public Producto(String name, String description, String category, BigDecimal price, String sku) {
		super();
		this.name = name;
		this.description = description;
		this.category = category;
		this.price = price;
		this.sku = sku;
	}
	
	@JsonCreator
	public Producto(@JsonProperty("id") Long id) {
        this.id = id;
    }
	
	public Producto() {}

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
	
	

	public List<Inventario> getInventarios() {
		return inventarios;
	}

	public void setInventarios(List<Inventario> inventarios) {
		this.inventarios = inventarios;
	}

	public List<Movimiento> getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(List<Movimiento> movimientos) {
		this.movimientos = movimientos;
	}

	@Override
	public String toString() {
		return "Producto [id=" + id + ", name=" + name + ", description=" + description + ", category=" + category
				+ ", price=" + price + ", sku=" + sku + "]";
	}
	
}
