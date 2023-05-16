

package com.northpool.geometry;

/**
 * Difference of geometries.
 */
public abstract class OperatorDifference extends Operator implements CombineOperator {

	@Override
	public Type getType() {
		return Type.Difference;
	}

	/**
	 * Performs the Topological Difference operation on the geometry set.
	 * 
	 * @param inputGeometries
	 *            is the set of Geometry instances to be subtracted by the
	 *            subtractor
	 * @param subtractor
	 *            is the Geometry being subtracted.
	 * @return Returns the result of the subtraction.
	 * 
	 *         The operator subtracts subtractor from every geometry in
	 *         inputGeometries.
	 */
	public abstract GeometryCursor execute(GeometryCursor inputGeometries,
			GeometryCursor subtractor, SpatialReference sr,
			ProgressTracker progressTracker);

	/**
	 * Performs the Topological Difference operation on the two geometries.
	 * 
	 * @param inputGeometry
	 *            is the Geometry instance on the left hand side of the
	 *            subtraction.
	 * @param subtractor
	 *            is the Geometry on the right hand side being subtracted.
	 * @return Returns the result of subtraction.
	 */
	public abstract Geometry execute(Geometry inputGeometry,
			Geometry subtractor, SpatialReference sr,
			ProgressTracker progressTracker);

	public static OperatorDifference local() {
		return (OperatorDifference) OperatorFactoryLocal.getInstance()
				.getOperator(Type.Difference);
	}

}
