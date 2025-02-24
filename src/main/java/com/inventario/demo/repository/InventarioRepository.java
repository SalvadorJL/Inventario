package com.inventario.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.inventario.demo.model.Inventario;
import com.inventario.demo.model.dto.InventarioAlertDTO;
import com.inventario.demo.model.dto.InventarioStoreDTO;

public interface InventarioRepository extends JpaRepository<Inventario, Integer> {

	@Query("select new com.inventario.demo.model.dto.InventarioStoreDTO(producto.id, producto.name, inventario.storeid, inventario.quantity) "
			+ "from Producto producto "
			+ "join producto.inventarios inventario "
			+ "where inventario.storeid = :storeid ")
	Page<InventarioStoreDTO> listInventariosById(Pageable pageable, @Param("storeid") String storeid);
	
	@Query("select new com.inventario.demo.model.dto.InventarioAlertDTO(producto.id, producto.name, inventario.storeid, inventario.quantity, inventario.minstock) "
			+ "from Producto producto "
			+ "join producto.inventarios inventario "
			+ "where inventario.quantity = inventario.minstock")
	Page<InventarioAlertDTO> listProductsWithLowStock(Pageable pageable);
	
	@Query("select producto.id, inventario.quantity "
			+ "from Producto producto "
			+ "join producto.inventarios inventario "
			+ "where inventario.storeid = :storeid "
			+ "and producto.id = :idProduct")
	List<?> contProductos(@Param("storeid") String storeid, @Param("idProduct") Long id);
}
