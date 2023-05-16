
package com.northpool.geometry;

/**
 * Densifies geometries preserving the shape of the segments in a given spatial reference by length and/or deviation. The elliptic arc lengths of the resulting line segments are no longer than the
 * given max length, and the line segments will be closer than the given max deviation to both the original segment curve and the joining elliptic arcs.
 */
abstract class OperatorShapePreservingDensify extends Operator {

	@Override
	public Type getType() {
		return Type.ShapePreservingDensify;
	}

	/**
	 * Performs the Shape Preserving Densify operation on the geometry set. Attributes are interpolated along the scalar t-values of the input segments obtained from the length ratios along the
	 * densified segments.
	 *
	 * @param geoms The geometries to be densified.
	 * @param sr The spatial reference of the geometries.
	 * @param maxLengthMeters The maximum segment length allowed. Must be a positive value to be used. Pass zero or NaN to disable densification by length.
	 * @param maxDeviationMeters The maximum deviation. Must be a positive value to be used. Pass zero or NaN to disable densification by deviation.
	 * @param reserved Must be 0 or NaN. Reserved for future use. Throws and exception if not NaN or 0.
	 * @return Returns the densified geometries (It does nothing to geometries with dim less than 1, but simply passes them along).
	 *
	 * The operation always starts from the lowest point on the segment, thus guaranteeing that topologically equal segments are always densified exactly the same.
	 */
	public abstract GeometryCursor execute(GeometryCursor geoms, SpatialReference sr, double maxLengthMeters, double maxDeviationMeters, double reserved, ProgressTracker progressTracker);

	/**
	 * Performs the Shape Preserving Densify operation on the geometry. Attributes are interpolated along the scalar t-values of the input segments obtained from the length ratios along the densified
	 * segments.
	 *
	 * @param geom The geometry to be densified.
	 * @param sr The spatial reference of the geometry.
	 * @param maxLengthMeters The maximum segment length allowed. Must be a positive value to be used. Pass zero or NaN to disable densification by length.
	 * @param maxDeviationMeters The maximum deviation. Must be a positive value to be used. Pass zero or NaN to disable densification by deviation.
	 * @param reserved Must be 0 or NaN. Reserved for future use. Throws and exception if not NaN or 0.
	 * @return Returns the densified geometries (It does nothing to geometries with dim less than 1, but simply passes them along).
	 *
	 * The operation always starts from the lowest point on the segment, thus guaranteeing that topologically equal segments are always densified exactly the same.
	 */
	public abstract Geometry execute(Geometry geom, SpatialReference sr, double maxLengthMeters, double maxDeviationMeters, double reserved, ProgressTracker progressTracker);

	public static OperatorShapePreservingDensify local() {
		return (OperatorShapePreservingDensify) OperatorFactoryLocal.getInstance()
						.getOperator(Type.ShapePreservingDensify);
	}
}
