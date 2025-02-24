package com.inventario.demo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.inventario.demo.model.Inventario;
import com.inventario.demo.model.Producto;
import com.inventario.demo.model.dto.InventarioAlertDTO;
import com.inventario.demo.model.dto.InventarioDTO;
import com.inventario.demo.model.dto.InventarioStoreDTO;
import com.inventario.demo.model.dto.MovimientoSbySDTO;
import com.inventario.demo.model.dto.ProductoDTO;
import com.inventario.demo.model.dto.ProductoIdDTO;
import com.inventario.demo.response.InventoryResponse;
import com.inventario.demo.service.InventarioService;

class InventarioControllerTest {

	 @Autowired
	 private MockMvc mockMvc;
	 
	 private InventarioService inventarioService;
	 
	 @BeforeEach
	    public void setUp() {
	        inventarioService = mock(InventarioService.class);
	        InventarioController controller = new InventarioController(inventarioService);
	        mockMvc = MockMvcBuilders.standaloneSetup(controller)
	                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
	                .build();
	    }
	 
	@Test
	public void testListAllInventariosEndpoints() throws Exception {
		Long id = 1L;
		Long id2 = 1L;
		
		Pageable pageable = PageRequest.of(0, 5);
		Page<InventarioDTO> mockPage = new PageImpl<>(Arrays.asList(
            new InventarioDTO(id, id2, "Senzao", "store01", 10, 5),
            new InventarioDTO(id2, id, "Coca-cola", "store02", 10, 5)
        ),pageable, 2);
		
		when(inventarioService.listInventarios(any())).thenReturn(mockPage);
		
		mockMvc.perform(get("/api/stores/")
				.param("page", "0")
		        .param("size", "5"))
				//.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.message").value("Inventario de todas las tiendas"))
			.andExpect(jsonPath("$.data.content").isArray())
			.andExpect(validarDatos(0, id, id2, "Senzao", "store01", 10, 5))
			.andExpect(validarDatos(1, id2, id, "Coca-cola", "store02", 10, 5));
		
		verify(inventarioService, times(1)).listInventarios(any());
	}
	
	private ResultMatcher validarDatos(int index, Long id, Long productId, String productName, String storeId, int quantity, int minStock) {
		return result -> {
			jsonPath("$.data.content[" + index + "].id").value(id).match(result);
	        jsonPath("$.data.content[" + index + "].productId").value(productId).match(result);
	        jsonPath("$.data.content[" + index + "].productName").value(productName).match(result);
	        jsonPath("$.data.content[" + index + "].storeId").value(storeId).match(result);
	        jsonPath("$.data.content[" + index + "].quantity").value(quantity).match(result);
	        jsonPath("$.data.content[" + index + "].minStock").value(minStock).match(result);
		};
	}
	
	@Test
	public void testlistByTiendas() throws Exception {
		Long id = 1L;
		Long id2 = 1L;
		Long id3 = 1L;
		Long id4 = 1L;
		Pageable pageable = PageRequest.of(0, 5);
		Page<InventarioStoreDTO> mockPage = new PageImpl<>(Arrays.asList(
			new InventarioStoreDTO(id, "Senzao", "store01", 123),
			new InventarioStoreDTO(id, "Coca-Cola", "store01", 111),
			new InventarioStoreDTO(id, "Pepsi", "store01", 77),
			new InventarioStoreDTO(id, "Fanta", "store01", 45)
		),pageable, 2);
		
		when(inventarioService.inventarioById(any(), any())).thenReturn(mockPage);
		
		mockMvc.perform(get("/api/stores/{storeId}/inventory", "store01")
				.param("page", "0")
		        .param("size", "5"))
				.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.message").value("Inventario de tienda obtenido"))
			.andExpect(jsonPath("$.data.content").isArray())
			.andExpect(validarDatosByTienda(0, id, "Senzao", "store01", 123))
			.andExpect(validarDatosByTienda(1, id2, "Coca-Cola", "store01", 111))
			.andExpect(validarDatosByTienda(2, id3, "Pepsi", "store01", 77))
			.andExpect(validarDatosByTienda(3, id4, "Fanta", "store01", 45));
		
		verify(inventarioService, times(1)).inventarioById(any(), any());
	}
	
	private ResultMatcher validarDatosByTienda(int index, Long id, String productName, String tiendaId, int cantidad) {
		return result -> {
			jsonPath("$.data.content[" + index + "].productoId").value(id).match(result);
	        jsonPath("$.data.content[" + index + "].productoName").value(productName).match(result);
	        jsonPath("$.data.content[" + index + "].tiendaId").value(tiendaId).match(result);
	        jsonPath("$.data.content[" + index + "].cantidad").value(cantidad).match(result);
		};
	}
	
	@Test
	public void listLowStock () throws Exception {
		
		Pageable pegeable = PageRequest.of(0,5);
		Page<InventarioAlertDTO> mockPage = new PageImpl<>(Arrays.asList(
			new InventarioAlertDTO(1L, "Senzao", "store01", 7, 7),
			new InventarioAlertDTO(1L, "Coca-Cola", "store02", 5, 5),
			new InventarioAlertDTO(1L, "Pepsi", "store01", 3, 3),
			new InventarioAlertDTO(1L, "Fanta", "store02", 6, 6),
			new InventarioAlertDTO(1L, "7Up", "store01", 3, 3)
		), pegeable, 2);
		
		when(inventarioService.listLowStock(any())).thenReturn(mockPage);
		
		mockMvc.perform(get("/api/inventory/alerts")
				.param("page", "0")
				.param("size", "5"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.message").value("Productos con bajo stock"))
			.andExpect(jsonPath("$.data.content").isArray())
			.andExpect(validarDatosLowStock(0, 1L, "Senzao","store01", 7, 7))
			.andExpect(validarDatosLowStock(1, 1L, "Coca-Cola", "store02", 5, 5))
			.andExpect(validarDatosLowStock(2, 1L, "Pepsi", "store01", 3, 3))
			.andExpect(validarDatosLowStock(3, 1L, "Fanta", "store02", 6, 6))
			.andExpect(validarDatosLowStock(4, 1L, "7Up","store01", 3, 3));
		
		verify(inventarioService, times(1)).listLowStock(any());
	}
	
	private ResultMatcher validarDatosLowStock(int index, Long id, String nameProdcut, String storeId, int quantity, int minStock) {
		return result -> {
			jsonPath("$.data.content[" + index + "].id").value(id).match(result);
	        jsonPath("$.data.content[" + index + "].nameProdcut").value(nameProdcut).match(result);
	        jsonPath("$.data.content[" + index + "].storeId").value(storeId).match(result);
	        jsonPath("$.data.content[" + index + "].quantity").value(quantity).match(result);
	        jsonPath("$.data.content[" + index + "].minStock").value(minStock).match(result);
		};
	}
	
	@Test
	public void testGuardarTransferencia() throws Exception {
		ProductoIdDTO mockProductoIdDTO = new ProductoIdDTO();
		mockProductoIdDTO.setId(1L);
		
		MovimientoSbySDTO mockMovimientoSbySDTO = new MovimientoSbySDTO();
		mockMovimientoSbySDTO.setProductoid(mockProductoIdDTO);
		mockMovimientoSbySDTO.setSourcestoreid("store01");
		mockMovimientoSbySDTO.setTargetstoreid("store02");
		mockMovimientoSbySDTO.setQuantity(6);
		mockMovimientoSbySDTO.setType("TRANSFER");
		
		when(inventarioService.guardarTransferencias(any())).thenReturn(mockMovimientoSbySDTO);
		
		ObjectMapper objectMapper = new ObjectMapper();
	    String requestBody = objectMapper.writeValueAsString(mockMovimientoSbySDTO);
		
		mockMvc.perform(post("/api/inventory/transfer")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.message").value("Se realizo una transferencia"))
			.andExpect(jsonPath("$.data.productoid.id").value(mockMovimientoSbySDTO.getProductoid().getId()))
			.andExpect(jsonPath("$.data.sourcestoreid").value(mockMovimientoSbySDTO.getSourcestoreid()))
			.andExpect(jsonPath("$.data.targetstoreid").value(mockMovimientoSbySDTO.getTargetstoreid()))
			.andExpect(jsonPath("$.data.quantity").value(mockMovimientoSbySDTO.getQuantity()))
			.andExpect(jsonPath("$.data.type").value(mockMovimientoSbySDTO.getType()));
		
		verify(inventarioService, times(1)).guardarTransferencias(any());
	}
}
