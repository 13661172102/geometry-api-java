


package com.northpool.geometry;

/**
 * A runtime exception raised when a geometry related exception occurs.
 */
public class GeometryException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a Geometry Exception with the given error string/message.
	 * 
	 * @param str
	 *            - The error string.
	 */
	public GeometryException(String str) {
		super(str);
	}

	static GeometryException GeometryInternalError() {
		return new GeometryException("internal error");
	}
}
