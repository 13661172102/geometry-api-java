

package com.northpool.geometry;

class OperatorIntersectsLocal extends OperatorIntersects {

	OperatorDisjoint m_disjoint = (OperatorDisjoint) OperatorFactoryLocal
			.getInstance().getOperator(Type.Disjoint);

	@Override
	public boolean execute(Geometry inputGeom1, Geometry inputGeom2,
			SpatialReference sr, ProgressTracker progressTracker) {
		return !m_disjoint.execute(inputGeom1, inputGeom2, sr, progressTracker);
	}

}
