

package com.northpool.geometry;

/**
 * Relational operation Equals.
 */
public abstract class OperatorEquals extends OperatorSimpleRelation {
	@Override
	public Type getType() {
		return Type.Equals;
	}

	public static OperatorEquals local() {
		return (OperatorEquals) OperatorFactoryLocal.getInstance().getOperator(
				Type.Equals);
	}

}
