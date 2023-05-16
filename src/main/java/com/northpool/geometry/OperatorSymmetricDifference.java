
package com.northpool.geometry;

/**
 * Symmetric difference (XOR) operation between geometries. 
 *
 */
public abstract class OperatorSymmetricDifference extends Operator implements CombineOperator {
	@Override
	public Type getType() {
		return Type.Difference;
	}

	/**
	 *Performs the Symmetric Difference operation on the geometry set.
	 *@param inputGeometries is the set of Geometry instances to be XOR'd by rightGeometry.
	 *@param rightGeometry is the Geometry being XOR'd with the inputGeometies.
	 *@return Returns the result of the symmetric difference.
	 *
	 *The operator XOR's every geometry in inputGeometries with rightGeometry.
	 */
	public abstract GeometryCursor execute(GeometryCursor inputGeometries,
			GeometryCursor rightGeometry, SpatialReference sr,
			ProgressTracker progressTracker);

	/**
	 *Performs the Symmetric Difference operation on the two geometries.
	 *@param leftGeometry is one of the Geometry instances in the XOR operation.
	 *@param rightGeometry is one of the Geometry instances in the XOR operation.
	 *@return Returns the result of the symmetric difference.
	 */
	public abstract Geometry execute(Geometry leftGeometry,
			Geometry rightGeometry, SpatialReference sr,
			ProgressTracker progressTracker);

	public static OperatorSymmetricDifference local() {
		return (OperatorSymmetricDifference) OperatorFactoryLocal.getInstance()
				.getOperator(Type.SymmetricDifference);
	}

}
