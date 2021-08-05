package net.aydini.sexel.exception;


/**
 * 
 * @author <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 */
public class SexelException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SexelException() {
		super();
	}

	public SexelException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
	public SexelException(String message) {
		super(message);
	}

}
