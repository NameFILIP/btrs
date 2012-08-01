package com.infinitiessoft.btrs.exceptions;

public class BtrsRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -6573619616191047010L;

	public BtrsRuntimeException() {
	}

	public BtrsRuntimeException(String message) {
		super(message);
	}

	public BtrsRuntimeException(Throwable cause) {
		super(cause);
	}

	public BtrsRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

}
