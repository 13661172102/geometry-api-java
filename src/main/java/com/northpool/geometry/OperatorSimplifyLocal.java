
package com.northpool.geometry;

class OperatorSimplifyLocal extends OperatorSimplify {

	// Reviewed vs. Feb 8 2011
	@Override
	public GeometryCursor execute(GeometryCursor geoms,
			SpatialReference spatialRef, boolean bForceSimplify,
			ProgressTracker progressTracker) {
		return new OperatorSimplifyCursor(geoms, spatialRef, bForceSimplify,
				progressTracker);
	}

	// Reviewed vs. Feb 8 2011
	@Override
	public boolean isSimpleAsFeature(Geometry geom,
			SpatialReference spatialRef, boolean bForceTest,
			NonSimpleResult result, ProgressTracker progressTracker) {
		int res = OperatorSimplifyLocalHelper.isSimpleAsFeature(geom,
				spatialRef, bForceTest, result, progressTracker);
		return res > 0;
	}

	// Reviewed vs. Feb 8 2011
	@Override
	public Geometry execute(Geometry geom, SpatialReference spatialRef,
			boolean bForceSimplify, ProgressTracker progressTracker) {
		SimpleGeometryCursor inputCursor = new SimpleGeometryCursor(geom);
		GeometryCursor outputCursor = execute(inputCursor, spatialRef,
				bForceSimplify, progressTracker);

		return outputCursor.next();
	}
}
