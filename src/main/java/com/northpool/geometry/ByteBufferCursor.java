
package com.northpool.geometry;

import java.nio.ByteBuffer;

/**
 * An abstract ByteBuffer Cursor class.
 */
abstract class ByteBufferCursor {

	/**
	 * Moves the cursor to the next ByteBuffer. Returns null when reached the
	 * end.
	 */
	public abstract ByteBuffer next();

	/**
	 * Returns the ID of the current ByteBuffer. The ID is propagated across the
	 * operations (when possible).
	 * 
	 * Returns an ID associated with the current Geometry. The ID is passed
	 * along and is returned by some operators to preserve relationship between
	 * the input and output geometry classes. It is not always possible to
	 * preserve an ID during an operation.
	 */
	public abstract int getByteBufferID();

}
