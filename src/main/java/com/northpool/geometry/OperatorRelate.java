

package com.northpool.geometry;

import com.northpool.geometry.Geometry.GeometryAccelerationDegree;

/**
 *Performs the Relation operation between two geometries using the DE-9IM matrix encoded as a string.
 *
 */
public abstract class OperatorRelate extends Operator {
	@Override
	public Type getType() {
		return Type.Relate;
	}

    /**
    *Performs the Relation operation between two geometries using the DE-9IM matrix encoded as a string.
    *@param inputGeom1 The first geometry in the relation.
    *@param inputGeom2 The second geometry in the relation.
    *@param sr The spatial reference of the geometries.
    *@param de_9im_string The DE-9IM matrix relation encoded as a string.
    *@return Returns True if the relation holds, False otherwise.
    */
	public abstract boolean execute(Geometry inputGeom1, Geometry inputGeom2,
			SpatialReference sr, String de_9im_string, ProgressTracker progressTracker);

	public static OperatorRelate local() {
		return (OperatorRelate) OperatorFactoryLocal.getInstance().getOperator(
				Type.Relate);
	}
	
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
