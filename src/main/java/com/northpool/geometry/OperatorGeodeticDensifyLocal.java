

package com.northpool.geometry;

//This is a stub
class OperatorGeodeticDensifyLocal extends
		OperatorGeodeticDensifyByLength {

	@Override
	public GeometryCursor execute(GeometryCursor geoms,
			double maxSegmentLengthMeters, SpatialReference sr, int curveType,
			ProgressTracker progressTracker) {
		throw new GeometryException("not implemented");
	}

	@Override
	public Geometry execute(Geometry geom, double maxSegmentLengthMeters,
			SpatialReference sr, int curveType, ProgressTracker progressTracker) {
		throw new GeometryException("not implemented");
	}
}
