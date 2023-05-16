
package com.northpool.geometry;

/**
 *An abstract Geometry Cursor class.
 */
public abstract class GeometryCursor {
	/**
	 *Moves the cursor to the next Geometry. Returns null when reached the end.
	 *The behavior of the cursor is undefined after the method returns null.
	 */
	public abstract Geometry next();

	/**
	 *Returns the ID of the current geometry. The ID is propagated across the operations (when possible).
	 *
	 *Returns an ID associated with the current Geometry. The ID is passed along and is returned by some operators to preserve relationship between the input and output geometry classes.
	 *It is not always possible to preserve an ID during an operation.
	 */
	public abstract int getGeometryID();
	/**
	 *Executes a unit of work on the cursor.
	 *@return Returns true, if there is a geometry ready to be pulled using next().
	 *
	 *This method is to be used together with the tick() method on the ListeningGeometryCursor.
	 *Call tock() for each tick() on the ListeningGeometryCursor.
	 */
	public boolean tock() { return true; }
}
