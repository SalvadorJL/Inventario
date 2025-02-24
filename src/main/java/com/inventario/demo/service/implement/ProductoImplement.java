package com.inventario.demo.service.implement;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.inventario.demo.exceptions.CustomDataIntegrityViolationException;
import com.inventario.demo.exceptions.DeletionException;
import com.inventario.demo.exceptions.EntityNotFoundException;
import com.inventario.demo.exceptions.ValidationException;
import com.inventario.demo.model.Inventario;
import com.inventario.demo.model.Producto;
import com.inventario.demo.model.dto.ProductoStockDTO;
import com.inventario.demo.repository.ProductoRepository;
import com.inventario.demo.service.ProductoService;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;


@Service
public class ProductoImplement implements ProductoService {
	
	private final ProductoRepository productoRepository;

	public ProductoImplement(ProductoRepository productoRepository) {
		this.productoRepository = productoRepository;
	}

	@Override
	public Page<?> listProductos(Pageable pageable, String categoria, Double precioMax, Double precioMin, String stock) {
		//final Pageable pageable = PageRequest.of(0,10);
		Specification<Producto> spec = Specification.where(null);
		Page<?> productos = null;

	    if (categoria != null && !categoria.isEmpty()) {
	        spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("category"), categoria));
	    }
	    if (precioMin != null) {
	        spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), precioMin));
	    }
	    if (precioMax != null) {
	        spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), precioMax));
	    }
	    if (stock != null && !stock.isEmpty()) {
			productos = productoRepository.findAllFilterByStock(pageable);
			return productos;
	    }
	    
	    productos = productoRepository.findAll(spec, pageable);

	    if (productos.isEmpty()) {
	        throw new EntityNotFoundException();
	    }
	    return productos;
	}
	
	
//	public Specification<Producto> listProductosStock() {
//		return Specification.where((root, query, criteriaBuilder) -> {
//            // Unión entre Producto e Inventario
//            Join<Producto, Inventario> inventarioJoin = root.join("inventarioList", JoinType.INNER);
//
//            // Expresión para sumar la cantidad de inventario
//            Expression<Integer> sumQuantity = criteriaBuilder.sum(inventarioJoin.get("quantity"));
//            query.multiselect( root.get("id"), root.get("name"), sumQuantity.alias("total"));
//            query.groupBy(root.get("id"));
//            query.orderBy(criteriaBuilder.desc(sumQuantity));
//
//            // No se necesita un predicado adicional, así que retornamos null
//            return null;
//        });
//	}

	@Override
	public Producto productoById(Long id) {
		
		return productoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Producto", id));
	}
	
	@Override
	public Page<ProductoStockDTO> listProductoStock(Pageable pageable, String stock) {
		Page<ProductoStockDTO> productos = null;
		if (stock != null && !stock.isEmpty()) {
			productos = productoRepository.findAllFilterByStock(pageable);
	    }
		if (productos.isEmpty() || productos == null) {
	        throw new EntityNotFoundException();
	    }
		return productos;
	}
	
	
	@Override
	public Producto guardarProducto(Producto producto) {
        ValidarDatos(producto);
        
        try {
        	Producto productoNuevo = productoRepository.save(producto);
        	return productoNuevo;
		} catch (DataIntegrityViolationException e) {
			if (e.getCause() instanceof ConstraintViolationException) {
                throw new CustomDataIntegrityViolationException("El SKU ya está registrado", 409);
            } throw e;
		}
	}

	@Override
	public Producto editarProducto(Long id, Producto productoEd) {
		Producto actual = productoById(id);
		ValidarDatos(productoEd);
		actual.setName(productoEd.getName());
		actual.setDescription(productoEd.getDescription());
		actual.setCategory(productoEd.getCategory());
		actual.setPrice(productoEd.getPrice());
		actual.setSku(productoEd.getSku());
		
		return productoRepository.save(actual);
	}

	@Override
	public void eliminarProducto(Long id) {
		productoById(id);
	    try {
	        productoRepository.deleteById(id);
	    } catch (Exception e) {
	        throw new DeletionException("Error al eliminar el producto con ID: " + id);
	    }
	}
	
	public void ValidarDatos(Producto producto) {
		
		if (producto.getName() == null || producto.getName().isEmpty()) {
            throw new ValidationException("El nombre del producto es requerido");
        }
		if (producto.getCategory() == null || producto.getCategory().isEmpty()) {
            throw new ValidationException("La categoria del producto es requerida");
        }
		if (producto.getDescription() == null || producto.getDescription().isEmpty()) {
            throw new ValidationException("La descripcion del producto es requerida");
        }
        if (producto.getPrice() == null || producto.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("El precio del producto es requerido y debe ser mayor 0");
        }
        if (producto.getSku() == null || producto.getSku().isEmpty()) {
            throw new ValidationException("El sku del producto es requerido");
        }
	}
}
