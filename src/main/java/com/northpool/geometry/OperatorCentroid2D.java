
package com.northpool.geometry;

public abstract class OperatorCentroid2D extends Operator {
	@Override
	public Type getType() {
		return Type.Centroid2D;
	}

	public abstract Point2D execute(Geometry geometry, ProgressTracker progressTracker);

	public static OperatorCentroid2D local() {
		return (OperatorCentroid2D) OperatorFactoryLocal.getInstance().getOperator(Type.Centroid2D);
	}
}
