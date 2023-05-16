

package com.northpool.geometry;

/**
 * A runtime exception raised when a JSON related exception occurs.
 */
public class JsonGeometryException extends GeometryException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a Json Geometry Exception with the given error string/message.
	 *
	 * @param str
	 *            - The error string.
	 */
	public JsonGeometryException(String str) {
		super(str);
	}

	/**
	 * Constructs a Json Geometry Exception with the given another exception.
	 *
	 * @param ex
	 *            - The exception to copy the message from.
	 */
	public JsonGeometryException(Exception ex) {
		super(ex.getMessage());
	}
	
}

