
package com.northpool.geometry;

class OperatorOffsetLocal extends OperatorOffset {

	@Override
	public GeometryCursor execute(GeometryCursor inputGeometries,
			SpatialReference sr, double distance, JoinType joins,
			double bevelRatio, double flattenError,
			ProgressTracker progressTracker) {
		return new OperatorOffsetCursor(inputGeometries, sr, distance, joins,
				bevelRatio, flattenError, progressTracker);
	}

	@Override
	public Geometry execute(Geometry inputGeometry, SpatialReference sr,
			double distance, JoinType joins, double bevelRatio,
			double flattenError, ProgressTracker progressTracker) {
		SimpleGeometryCursor inputCursor = new SimpleGeometryCursor(
				inputGeometry);
		GeometryCursor outCursor = execute(inputCursor, sr, distance, joins,
				bevelRatio, flattenError, progressTracker);
		return outCursor.next();
	}

}
