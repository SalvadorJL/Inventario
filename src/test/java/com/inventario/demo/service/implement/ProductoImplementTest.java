package com.inventario.demo.service.implement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.inventario.demo.exceptions.EntityNotFoundException;
import com.inventario.demo.model.Producto;
import com.inventario.demo.repository.ProductoRepository;

@ExtendWith(MockitoExtension.class)
class ProductoImplementTest {

	@Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoImplement productoImplement;
    
    @Test
	void testListProductos() {
    	// Arrange
        Pageable pageable = PageRequest.of(0, 5);
        List<Producto> productos = Arrays.asList(
            new Producto("Producto 1", "Descripción 1", "Categoría 1", BigDecimal.valueOf(100), "SKU1"),
            new Producto("Producto 2", "Descripción 2", "Categoría 2", BigDecimal.valueOf(200), "SKU2")
        );
        Page<Producto> productPage = new PageImpl<>(productos);

        when(productoRepository.findAll(any(Specification.class), eq(pageable)))
            .thenReturn(productPage);

        // Act
        Page<?> result = productoImplement.listProductos(pageable, null, null, null, null);

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.getContent().size());
	}
    
    @Test
    void testListProductos_ThrowsException() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 5);
        Page<Producto> emptyPage = new PageImpl<>(Collections.emptyList());

        when(productoRepository.findAll(any(Specification.class), eq(pageable)))
            .thenReturn(emptyPage);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> productoImplement.listProductos(pageable, null, null, null, null));
    }
    
    @Test
	void testProductoById() {
    	// Arrange
        Long id = 1L;
        Producto mockProducto = new Producto();
        mockProducto.setId(id);
        when(productoRepository.findById(id)).thenReturn(Optional.of(mockProducto));

        // Act
        Producto result = productoImplement.productoById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
	}
	
    @Test
    void testProductoById_NotFound() {
        // Arrange
        Long id = 1L;
        when(productoRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> productoImplement.productoById(id));
    }
    
    @Test
    void testGuardarProducto() {
        // Arrange
        Producto producto = new Producto();
        producto.setName("Senzao");
        producto.setDescription("Gaseosa de guarana");
        producto.setCategory("Bebidas");
        producto.setPrice(BigDecimal.valueOf(18.00));
        producto.setSku("sku00001");
        when(productoRepository.save(producto)).thenReturn(producto);

        // Act
        Producto result = productoImplement.guardarProducto(producto);

        // Assert
        assertNotNull(result);
        assertEquals(producto, result);
    }
    
    @Test
    void testEditarProducto() {
        // Arrange
        Long id = 1L;
        Producto existingProducto = new Producto();
        existingProducto.setId(id);
        existingProducto.setName("Fanta Limon");
        existingProducto.setDescription("Gaseosa de limon");
        existingProducto.setCategory("Bebidas");
        existingProducto.setPrice(BigDecimal.valueOf(18.00));
        existingProducto.setSku("sku00002");

        Producto updatedProducto = new Producto();
        updatedProducto.setId(id);
        updatedProducto.setName("Fanta Fresa");
        updatedProducto.setDescription("Gaseosa de fresa");
        updatedProducto.setCategory("Bebidas");
        updatedProducto.setPrice(BigDecimal.valueOf(18.00));
        updatedProducto.setSku("sku00002");

        when(productoRepository.findById(id)).thenReturn(Optional.of(existingProducto));
        when(productoRepository.save(existingProducto)).thenReturn(updatedProducto);

        // Act
        Producto result = productoImplement.editarProducto(id, updatedProducto);

        // Assert
        assertNotNull(result);
        assertEquals(updatedProducto, result);
    }

    @Test
    void testEditarProducto_NotFound() {
        // Arrange
        Long id = 1L;
        Producto updatedProducto = new Producto();
        when(productoRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> productoImplement.editarProducto(id, updatedProducto));
    }
    
    @Test
    void testEliminarProducto() {
        // Arrange
        Long id = 1L;
        Producto producto = new Producto();
        producto.setId(id);
        producto.setName("Fanta Fresa");
        producto.setDescription("Gaseosa de fresa");
        producto.setCategory("Bebidas");
        producto.setPrice(BigDecimal.valueOf(18.00));
        producto.setSku("sku00002");
        
        when(productoRepository.findById(id)).thenReturn(Optional.of(producto));

        // Act
        assertDoesNotThrow(() -> productoImplement.eliminarProducto(id));

        // Assert
        verify(productoRepository, times(1)).deleteById(id);
    }

    @Test
    void testEliminarProducto_NotFound() {
        // Arrange
        Long id = 1L;
        when(productoRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> productoImplement.eliminarProducto(id));
    }
}
