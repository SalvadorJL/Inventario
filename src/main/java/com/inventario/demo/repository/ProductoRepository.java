package com.inventario.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.inventario.demo.model.Producto;
import com.inventario.demo.model.dto.ProductoStockDTO;

public interface ProductoRepository extends JpaRepository<Producto, Long>, JpaSpecificationExecutor<Producto> {

	@Query("select new com.inventario.demo.model.dto.ProductoStockDTO(producto.id, producto.name, sum(inventario.quantity)) "
			+ "from Producto producto "
			+ "join producto.inventarios inventario "
			+ "group by producto.id, producto.name "
			+ "order by sum(inventario.quantity) desc")
	Page<ProductoStockDTO> findAllFilterByStock (Pageable pageable);
}
