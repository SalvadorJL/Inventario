package com.inventario.demo.service.implement;

import java.lang.reflect.Field;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.inventario.demo.exceptions.EntityNotFoundException;
import com.inventario.demo.exceptions.ValidationException;
import com.inventario.demo.model.Inventario;
import com.inventario.demo.model.Movimiento;
import com.inventario.demo.model.Producto;
import com.inventario.demo.model.dto.InventarioAlertDTO;
import com.inventario.demo.model.dto.InventarioDTO;
import com.inventario.demo.model.dto.InventarioStoreDTO;
import com.inventario.demo.model.dto.MovimientoSbySDTO;
import com.inventario.demo.model.dto.ProductoIdDTO;
import com.inventario.demo.repository.InventarioRepository;
import com.inventario.demo.repository.MovimientoRepository;
import com.inventario.demo.service.InventarioService;

@Service
public class InventarioImplement implements InventarioService{

	private final InventarioRepository inventarioRepository;
	private final MovimientoRepository movimientoRepository;
	
	public InventarioImplement(InventarioRepository inventarioRepository, MovimientoRepository movimientoRepository) {
		this.inventarioRepository = inventarioRepository;
		this.movimientoRepository = movimientoRepository;
	}

	@Override
	public Page<InventarioDTO> listInventarios(Pageable pageable) {
		Page<Inventario> inventarios = inventarioRepository.findAll(pageable);
		if (inventarios.isEmpty()) {
	        throw new EntityNotFoundException();
	    }
		Page<InventarioDTO> inventarioDto = inventarios.map(InventarioDTO::new);
		return inventarioDto;
	}

	@Override
	public Page<InventarioStoreDTO> inventarioById(Pageable pageable, String storeId) {
		Page<InventarioStoreDTO> inventario = inventarioRepository.listInventariosById(pageable, storeId);
		if (inventario.isEmpty()) {
	        throw new EntityNotFoundException();
	    }
		return inventario;
	}

	@Override
	public Page<InventarioAlertDTO> listLowStock(Pageable pageable) {
		Page<InventarioAlertDTO> listaAlertas = inventarioRepository.listProductsWithLowStock(pageable);
		
		if(listaAlertas.isEmpty() || listaAlertas == null) {
			throw new EntityNotFoundException();
		}
		return listaAlertas;
	}

	@Override
	public MovimientoSbySDTO guardarTransferencias(MovimientoSbySDTO movimiento) {
		List<?> contados = inventarioRepository.contProductos(movimiento.getSourcestoreid(), movimiento.getProductoid().getId());
		
		Object[] datos = null;
		for (Object resultado : contados) { datos = (Object[]) resultado; }
		
		
		if((Integer)datos[1] <= 0) {
			throw new ValidationException("No hay suficiente stock disponible");
		}
		Movimiento movimientos = new Movimiento();
		movimientos.setProductId(new Producto(movimiento.getProductoid().getId()));
		movimientos.setSourceStoreId(movimiento.getSourcestoreid());
		movimientos.setTargetStoreId(movimiento.getTargetstoreid());
		movimientos.setQuantity(movimiento.getQuantity());
		movimientos.setType(Movimiento.MovementType.valueOf(movimiento.getType()));

		Movimiento transferencia = movimientoRepository.save(movimientos);
		MovimientoSbySDTO transferenciaFilter = new MovimientoSbySDTO();
		
		transferenciaFilter.setProductoid(new ProductoIdDTO(transferencia.getProductId().getId()));
		transferenciaFilter.setSourcestoreid(transferencia.getSourceStoreId());
		transferenciaFilter.setTargetstoreid(transferencia.getTargetStoreId());
		transferenciaFilter.setQuantity(transferencia.getQuantity());
		transferenciaFilter.setType(transferencia.getType().name());
		
		return transferenciaFilter;
	}
}
