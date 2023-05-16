

package com.northpool.geometry;

import com.northpool.geometry.Geometry.GeometryAccelerationDegree;

/**
 * A base class for simple relation operators.
 */
public abstract class OperatorSimpleRelation extends Operator {

	/**
	 * Performs the given relation operation between two geometries.
	 * 
	 * @return Returns True if the relation holds, False otherwise.
	 */
	public abstract boolean execute(Geometry inputGeom1, Geometry inputGeom2,
			SpatialReference sr, ProgressTracker progressTracker);

	@Override
	public boolean canAccelerateGeometry(Geometry geometry) {
		return RelationalOperations.Accelerate_helper
				.can_accelerate_geometry(geometry);
	}

	@Override
	public boolean accelerateGeometry(Geometry geometry,
			SpatialReference spatialReference,
			GeometryAccelerationDegree accelDegree) {
		return RelationalOperations.Accelerate_helper.accelerate_geometry(
				geometry, spatialReference, accelDegree);
	}
}
