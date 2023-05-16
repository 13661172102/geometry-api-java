

package com.northpool.geometry;

/**
 * Clips geometries with Envelope2D.
 */
public abstract class OperatorClip extends Operator {
	public Type getType() {
		return Type.Clip;
	}

	/**
	 * Performs the Clip operation on the geometry set.
	 */
	public abstract GeometryCursor execute(GeometryCursor geoms,
			Envelope2D envelope, SpatialReference spatialRef,
			ProgressTracker progressTracker);

	/**
	 * Performs the Clip operation on a single geometry.
	 */
	public abstract Geometry execute(Geometry geom, Envelope2D envelope,
			SpatialReference spatialRef, ProgressTracker progressTracker);

	public static OperatorClip local() {
		return (OperatorClip) OperatorFactoryLocal.getInstance().getOperator(
				Type.Clip);
	}

}
