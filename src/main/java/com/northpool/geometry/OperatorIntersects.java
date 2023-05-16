

package com.northpool.geometry;

public abstract class OperatorIntersects extends OperatorSimpleRelation {
	@Override
	public Type getType() {
		return Type.Intersects;
	}

	public static OperatorIntersects local() {
		return (OperatorIntersects) OperatorFactoryLocal.getInstance()
				.getOperator(Type.Intersects);
	}

}
