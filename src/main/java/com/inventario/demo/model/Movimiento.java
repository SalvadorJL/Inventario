package com.inventario.demo.model;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

@Entity
@Table(name = "movimiento")
public class Movimiento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
    @JoinColumn(name = "productid", nullable = false)
	private Producto producto;
	@Column(length = 50)
	@JsonProperty("sourcestoreid")
	private String sourcestoreid;
	@Column(length = 50)
	@JsonProperty("targetstoreid")
	private String targetstoreid;
	@Column(nullable = false)
	private Integer quantity;
	@Column(nullable = false)
	private LocalDateTime timestamp = LocalDateTime.now();
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, columnDefinition = "MOVEMENTTYPE")
	@Convert(converter = MovementTypeConverter.class)
    private MovementType type;

    // Enumerador para el campo "type"
    public enum MovementType {
        IN, OUT, TRANSFER
    }

	public Movimiento(Producto producto, String sourcestoreid, String targetstoreid, Integer quantity,
			LocalDateTime timestamp, MovementType type) {
		this.producto = producto;
		this.sourcestoreid = sourcestoreid;
		this.targetstoreid = targetstoreid;
		this.quantity = quantity;
		this.timestamp = timestamp;
		this.type = type;
	}

	public Movimiento() { }

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

	public String getSourceStoreId() {
		return sourcestoreid;
	}

	public void setSourceStoreId(String sourcestoreid) {
		this.sourcestoreid = sourcestoreid;
	}

	public String getTargetStoreId() {
		return targetstoreid;
	}

	public void setTargetStoreId(String targetstoreid) {
		this.targetstoreid = targetstoreid;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public MovementType getType() {
		return type;
	}

	public void setType(MovementType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Movimiento [producto=" + producto + ", sourcestoreid=" + sourcestoreid + ", targetstoreid="
				+ targetstoreid + ", quantity=" + quantity + ", timestamp=" + timestamp + ", type=" + type + "]";
	}
}
