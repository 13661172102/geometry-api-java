
package com.northpool.geometry;

/**
 * Densifies the line segments by length, making them run along specified geodetic curves.
 * 
* Use this operator to construct geodetic curves.
 */
abstract class OperatorGeodeticDensifyByLength extends Operator {

	@Override
	public Type getType() {
		return Type.GeodeticDensifyByLength;
	}

	/**
	 * Densifies input geometries. Attributes are interpolated along the scalar t-values of the input segments obtained from the length ratios along the densified segments.
	 *
	 * @param geoms The geometries to be densified.
	 * @param maxSegmentLengthMeters The maximum segment length (in meters) allowed. Must be a positive value.
	 * @param sr The SpatialReference of the Geometry.
	 * @param curveType The interpretation of a line connecting two points.
	 * @return Returns the densified geometries (It does nothing to geometries with dim less than 1, but simply passes them along).
	 *
	 * Note the behavior is not determined for any geodetic curve segments that connect two poles, or for loxodrome segments that connect to any pole.
	 */
	public abstract GeometryCursor execute(GeometryCursor geoms, double maxSegmentLengthMeters, SpatialReference sr, int curveType, ProgressTracker progressTracker);

	/**
	 * Same as above, but works with a single geometry.
	 */
	public abstract Geometry execute(Geometry geom, double maxSegmentLengthMeters, SpatialReference sr, int curveType, ProgressTracker progressTracker);

	public static OperatorGeodeticDensifyByLength local() {
		return (OperatorGeodeticDensifyByLength) OperatorFactoryLocal.getInstance()
						.getOperator(Type.GeodeticDensifyByLength);
	}
}
