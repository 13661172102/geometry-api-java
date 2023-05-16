

package com.northpool.geometry;

import com.northpool.geometry.Geometry.GeometryAccelerationDegree;

class OperatorIntersectionLocal extends OperatorIntersection {

	@Override
	public GeometryCursor execute(GeometryCursor inputGeometries,
			GeometryCursor intersector, SpatialReference sr,
			ProgressTracker progressTracker) {

		return new OperatorIntersectionCursor(inputGeometries, intersector, sr,
				progressTracker, -1);
	}

	@Override
	public GeometryCursor execute(GeometryCursor input_geometries,
			GeometryCursor intersector, SpatialReference sr,
			ProgressTracker progress_tracker, int dimensionMask) {
		return new OperatorIntersectionCursor(input_geometries, intersector,
				sr, progress_tracker, dimensionMask);
	}

	@Override
	public Geometry execute(Geometry inputGeometry, Geometry intersector,
			SpatialReference sr, ProgressTracker progressTracker) {
		SimpleGeometryCursor inputGeomCurs = new SimpleGeometryCursor(
				inputGeometry);
		SimpleGeometryCursor intersectorCurs = new SimpleGeometryCursor(
				intersector);
		GeometryCursor geometryCursor = execute(inputGeomCurs, intersectorCurs,
				sr, progressTracker);

		return geometryCursor.next();
	}

	@Override
	public boolean accelerateGeometry(Geometry geometry,
			SpatialReference spatialReference,
			GeometryAccelerationDegree accelDegree) {
		if (!canAccelerateGeometry(geometry))
			return false;

		double tol = InternalUtils.calculateToleranceFromGeometry(spatialReference, geometry, false);
		boolean accelerated = ((MultiVertexGeometryImpl) geometry._getImpl())
				._buildQuadTreeAccelerator(accelDegree);
		accelerated |= ((MultiVertexGeometryImpl) geometry._getImpl())
				._buildRasterizedGeometryAccelerator(tol, accelDegree);
		return accelerated;
	}

	@Override
	public boolean canAccelerateGeometry(Geometry geometry) {
		return RasterizedGeometry2D.canUseAccelerator(geometry);
	}

}
