

package com.northpool.geometry;

class OperatorClipLocal extends OperatorClip {

	@Override
	public GeometryCursor execute(GeometryCursor geoms, Envelope2D envelope,
			SpatialReference spatialRef, ProgressTracker progressTracker) {
		return new OperatorClipCursor(geoms, envelope, spatialRef,
				progressTracker);
	}

	@Override
	public Geometry execute(Geometry geom, Envelope2D envelope,
			SpatialReference spatialRef, ProgressTracker progressTracker) {
		SimpleGeometryCursor inputCursor = new SimpleGeometryCursor(geom);

		GeometryCursor outputCursor = execute(inputCursor, envelope,
				spatialRef, progressTracker);
		return outputCursor.next();
	}

}
