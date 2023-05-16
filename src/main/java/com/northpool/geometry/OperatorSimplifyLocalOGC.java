
package com.northpool.geometry;

class OperatorSimplifyLocalOGC extends OperatorSimplifyOGC {

	@Override
	public GeometryCursor execute(GeometryCursor geoms,
			SpatialReference spatialRef, boolean bForceSimplify,
			ProgressTracker progressTracker) {
		return new OperatorSimplifyCursorOGC(geoms, spatialRef, bForceSimplify,
				progressTracker);
	}

	@Override
	public boolean isSimpleOGC(Geometry geom, SpatialReference spatialRef,
			boolean bForceTest, NonSimpleResult result,
			ProgressTracker progressTracker) {
		int res = OperatorSimplifyLocalHelper.isSimpleOGC(geom, spatialRef,
				bForceTest, result, progressTracker);
		return res > 0;
	}

	@Override
	public Geometry execute(Geometry geom, SpatialReference spatialRef,
			boolean bForceSimplify, ProgressTracker progressTracker) {
		SimpleGeometryCursor inputCursor = new SimpleGeometryCursor(geom);
		GeometryCursor outputCursor = execute(inputCursor, spatialRef,
				bForceSimplify, progressTracker);

		return outputCursor.next();
	}
}
