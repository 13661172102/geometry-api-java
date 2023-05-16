
package com.northpool.geometry;

abstract class OperatorGeodesicBuffer extends Operator {

	@Override
	public Operator.Type getType() {
		return Operator.Type.GeodesicBuffer;
	}

	/**
	 * Creates a geodesic buffer around the input geometries
	 *
	 * @param inputGeometries The geometries to buffer.
	 * @param sr The Spatial_reference of the Geometries.
	 * @param curveType The geodetic curve type of the segments. If the curve_type is Geodetic_curve::shape_preserving, then the segments are densified in the projection where they are defined before
	 * buffering.
	 * @param distancesMeters The buffer distances in meters for the Geometries. If the size of the distances array is less than the number of geometries in the input_geometries, the last distance value
	 * is used for the rest of geometries.
	 * @param maxDeviationMeters The deviation offset to use for convergence. The geodesic arcs of the resulting buffer will be closer than the max deviation of the true buffer. Pass in NaN to use the
	 * default deviation.
	 * @param bReserved Must be false. Reserved for future development. Will throw an exception if not false.
	 * @param bUnion If True, the buffered geometries will be unioned, otherwise they wont be unioned.
	 * @param progressTracker Can be null. Allows to cancel lengthy operation.
	 * @return Geometry cursor over result buffers.
	 */
	abstract public GeometryCursor execute(GeometryCursor inputGeometries, SpatialReference sr, int curveType, double[] distancesMeters, double maxDeviationMeters, boolean bReserved, boolean bUnion, ProgressTracker progressTracker);

	/**
	 * Creates a geodesic buffer around the input geometry
	 *
	 * @param inputGeometry The geometry to buffer.
	 * @param sr The Spatial_reference of the Geometry.
	 * @param curveType The geodetic curve type of the segments. If the curve_type is Geodetic_curve::shape_preserving, then the segments are densified in the projection where they are defined before
	 * buffering.
	 * @param distanceMeters The buffer distance in meters for the Geometry.
	 * @param maxDeviationMeters The deviation offset to use for convergence. The geodesic arcs of the resulting buffer will be closer than the max deviation of the true buffer. Pass in NaN to use the
	 * default deviation.
	 * @param bReserved Must be false. Reserved for future development. Will throw an exception if not false.
	 * @param progressTracker Can be null. Allows to cancel lengthy operation.
	 * @return Returns result buffer.
	 */
	abstract public Geometry execute(Geometry inputGeometry, SpatialReference sr, int curveType, double distanceMeters, double maxDeviationMeters, boolean bReserved, ProgressTracker progressTracker);

	public static OperatorGeodesicBuffer local() {
		return (OperatorGeodesicBuffer) OperatorFactoryLocal.getInstance()
						.getOperator(Type.GeodesicBuffer);
	}
}
