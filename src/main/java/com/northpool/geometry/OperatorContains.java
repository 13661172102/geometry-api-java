

package com.northpool.geometry;

/**
 * Relational operation Contains.
 */
public abstract class OperatorContains extends OperatorSimpleRelation {
	@Override
	public Type getType() {
		return Type.Contains;
	}

	public static OperatorContains local() {
		return (OperatorContains) OperatorFactoryLocal.getInstance()
				.getOperator(Type.Contains);
	}
}
