
package com.northpool.geometry;

class OperatorBoundaryLocal extends OperatorBoundary {

	@Override
	public GeometryCursor execute(GeometryCursor geoms,
			ProgressTracker progressTracker) {
		// TODO Auto-generated method stub
		return new OperatorBoundaryLocalCursor(geoms, progressTracker);
	}

	@Override
	public Geometry execute(Geometry geom, ProgressTracker progressTracker) {
		// TODO Auto-generated method stub
		GeometryCursor res = new OperatorBoundaryLocalCursor(
				new SimpleGeometryCursor(geom), progressTracker);
		return res.next();
	}

}
