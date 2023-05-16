
package com.northpool.geometry;

public abstract class OperatorOverlaps extends OperatorSimpleRelation {
	@Override
	public Type getType() {
		return Type.Equals;
	}

	public static OperatorOverlaps local() {
		return (OperatorOverlaps) OperatorFactoryLocal.getInstance()
				.getOperator(Type.Overlaps);
	}

}
