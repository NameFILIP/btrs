package com.infinitiessoft.btrs.exceptions;

import javax.ejb.ApplicationException;

import org.jboss.seam.annotations.exception.Redirect;

@Redirect(viewId="/error.xhtml")
@ApplicationException(rollback=true)
public class BtrsRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -6573619616191047010L;

	public BtrsRuntimeException(String message) {
		super(message);
	}
	
	public BtrsRuntimeException() {
	}

	public BtrsRuntimeException(Throwable cause) {
		super(cause);
	}

	public BtrsRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
