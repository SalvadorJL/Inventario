package com.inventario.demo.exceptions;

public class DeletionException extends CustomException {
	
	public DeletionException(String message) {
        super(message, 500); // Código 500 para errores internos
    }
}
