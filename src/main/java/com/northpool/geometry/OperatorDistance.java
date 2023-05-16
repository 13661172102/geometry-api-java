

package com.northpool.geometry;

/**
 * Calculates distance between geometries.
 */
public abstract class OperatorDistance extends Operator {

	@Override
	public Type getType() {
		return Type.Distance;
	}

	/**
	 * Calculates distance between two geometries.
	 */
	public abstract double execute(Geometry geom1, Geometry geom2,
			ProgressTracker progressTracker);

	public static OperatorDistance local() {
		return (OperatorDistance) OperatorFactoryLocal.getInstance()
				.getOperator(Type.Distance);
	}

}
