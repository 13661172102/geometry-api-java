
package com.northpool.geometry;

class OperatorConvexHullLocal extends OperatorConvexHull {
	@Override
	public GeometryCursor execute(GeometryCursor geoms, boolean b_merge,
			ProgressTracker progress_tracker) {
		return new OperatorConvexHullCursor(b_merge, geoms, progress_tracker);
	}

	@Override
	public Geometry execute(Geometry geometry, ProgressTracker progress_tracker) {
		return OperatorConvexHullCursor.calculateConvexHull_(geometry,
				progress_tracker);
	}

	@Override
	public boolean isConvex(Geometry geom, ProgressTracker progress_tracker) {
		return OperatorConvexHullCursor.isConvex_(geom, progress_tracker);
	}
}
