package com.inventario.demo.exceptions;

public class CustomDataIntegrityViolationException extends CustomException {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int errorCode;
	 
	public CustomDataIntegrityViolationException(String message, int errorCode) {
		super(message, errorCode);
		this.errorCode = errorCode;
		// TODO Auto-generated constructor stub
	}

	public int getErrorCode() {
        return errorCode;
    }
}
 