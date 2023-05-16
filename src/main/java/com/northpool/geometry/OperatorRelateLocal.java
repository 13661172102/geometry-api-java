

package com.northpool.geometry;

class OperatorRelateLocal extends OperatorRelate {

	@Override
	public boolean execute(Geometry inputGeom1, Geometry inputGeom2,
			SpatialReference sr, String scl, ProgressTracker progress_tracker) {
		return RelationalOperationsMatrix.relate(inputGeom1, inputGeom2, sr,
				scl, progress_tracker);
	}

}
