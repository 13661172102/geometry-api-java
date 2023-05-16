

package com.northpool.geometry;

//This is a stub
class OperatorGeodesicBufferLocal extends OperatorGeodesicBuffer {

	@Override
	public GeometryCursor execute(GeometryCursor inputGeometries,
			SpatialReference sr, int curveType, double[] distancesMeters,
			double maxDeviationMeters, boolean bReserved, boolean bUnion,
			ProgressTracker progressTracker) {
		throw new GeometryException("not implemented");
	}

	@Override
	public Geometry execute(Geometry inputGeometry, SpatialReference sr,
			int curveType, double distanceMeters, double maxDeviationMeters,
			boolean bReserved, ProgressTracker progressTracker) {
		throw new GeometryException("not implemented");
	}
}
