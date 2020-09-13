package org.lemon.color;

public class IllegalRGBValueException extends IllegalArgumentException {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = -16524315L;
	
	
	/**
	 * 
	 * Constructs an <p> IllegalRGBValueException </p> with the 
	 * specified detail message.
	 * 
	 * @param msg the detail message
	 * 
	 * */
	public IllegalRGBValueException( String msg ) {
		super( msg );
	}

}
