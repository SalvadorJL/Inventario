package com.inventario.demo.exceptions;

public class ValidationException extends CustomException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ValidationException(String message) {
        super(message, 400); // Código 400 para errores de validación
    }
}
