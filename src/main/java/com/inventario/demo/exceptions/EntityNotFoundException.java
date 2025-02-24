package com.inventario.demo.exceptions;

public class EntityNotFoundException extends CustomException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EntityNotFoundException(String entityName, Long entityId) {
        super("No se encontró el" + entityName + " con ID: " + entityId, 404);
    }
	
	public EntityNotFoundException() {
        super("La lista esta vacia", 200);
    }
}
