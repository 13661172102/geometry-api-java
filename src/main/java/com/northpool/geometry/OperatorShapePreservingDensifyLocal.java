

package com.northpool.geometry;

//This is a stub
class OperatorShapePreservingDensifyLocal extends
		OperatorShapePreservingDensify {

	@Override
	public GeometryCursor execute(GeometryCursor geoms, SpatialReference sr,
			double maxLengthMeters, double maxDeviationMeters, double reserved,
			ProgressTracker progressTracker) {
		throw new GeometryException("not implemented");
	}

	@Override
	public Geometry execute(Geometry geom, SpatialReference sr,
			double maxLengthMeters, double maxDeviationMeters, double reserved,
			ProgressTracker progressTracker) {
		throw new GeometryException("not implemented");
	}
}
