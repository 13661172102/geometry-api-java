
package com.northpool.geometry;

class UserCancelException extends GeometryException {
	private static final long serialVersionUID = 1L;

	public UserCancelException() {
		super("user cancel");
	}
}
