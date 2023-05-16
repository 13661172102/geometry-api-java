
package com.northpool.geometry;

/**
 * An abstract Geometry Cursor class.
 */
abstract class MapGeometryCursor {

	/**
	 * Moves the cursor to the next ProjectedGeometry. Returns null when reached
	 * the end.
	 */
	public abstract MapGeometry next();

	/**
	 * Returns the ID of the current geometry. The ID is propagated across the
	 * operations (when possible).
	 * 
	 * Returns an ID associated with the current Geometry. The ID is passed
	 * along and is returned by some operators to preserve relationship between
	 * the input and output geometry classes. It is not always possible to
	 * preserve an ID during an operation.
	 */
	public abstract int getGeometryID();
}
