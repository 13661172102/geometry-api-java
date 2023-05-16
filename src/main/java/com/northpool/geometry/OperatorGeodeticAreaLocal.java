

package com.northpool.geometry;

//This is a stub
class OperatorGeodeticAreaLocal extends OperatorGeodeticArea {
	@Override
	public double[] execute(GeometryCursor geoms, SpatialReference sr,
			int geodeticCurveType, ProgressTracker progressTracker) {
		throw new GeometryException("not implemented");
	}

	@Override
	public double execute(Geometry geom, SpatialReference sr,
			int geodeticCurveType, ProgressTracker progressTracker) {
		throw new GeometryException("not implemented");
	}
}
