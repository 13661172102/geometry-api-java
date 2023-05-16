

package com.northpool.geometry;

class OperatorEqualsLocal extends OperatorEquals {
	@Override
	public boolean execute(Geometry inputGeom1, Geometry inputGeom2,
			SpatialReference sr, ProgressTracker progressTracker) {
		return RelationalOperations.relate(inputGeom1, inputGeom2, sr,
				RelationalOperations.Relation.equals, progressTracker);
	}

}
