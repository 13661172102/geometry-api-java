

package com.northpool.geometry;

/**
 * 
 * Union of geometries.
 *
 */
public abstract class OperatorUnion extends Operator implements CombineOperator{
	@Override
	public Type getType() {
		return Type.Union;
	}

	/**
	 *Performs the Topological Union operation on the geometry set.
	 *@param inputGeometries is the set of Geometry instances to be unioned.
	 *
	 */
	public abstract GeometryCursor execute(GeometryCursor inputGeometries,
			SpatialReference sr, ProgressTracker progressTracker);

	/**
	 *Performs the Topological Union operation on two geometries.
	 *@param geom1 and geom2 are the geometry instances to be unioned.
	 *
	 */
	public abstract Geometry execute(Geometry geom1, Geometry geom2,
			SpatialReference sr, ProgressTracker progressTracker);

	public static OperatorUnion local() {
		return (OperatorUnion) OperatorFactoryLocal.getInstance().getOperator(
				Type.Union);
	}

}
