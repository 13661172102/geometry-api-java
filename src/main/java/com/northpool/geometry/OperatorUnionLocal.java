

package com.northpool.geometry;

class OperatorUnionLocal extends OperatorUnion {

	@Override
	public GeometryCursor execute(GeometryCursor inputGeometries,
			SpatialReference sr, ProgressTracker progressTracker) {
		return new OperatorUnionCursor(inputGeometries, sr, progressTracker);
	}

	@Override
	public Geometry execute(Geometry geom1, Geometry geom2,
			SpatialReference sr, ProgressTracker progressTracker) {
		Geometry[] geomArray = new Geometry[] { geom1, geom2 };

		SimpleGeometryCursor inputGeometries = new SimpleGeometryCursor(
				geomArray);
		GeometryCursor outputCursor = execute(inputGeometries, sr,
				progressTracker);

		return outputCursor.next();
	}

}
