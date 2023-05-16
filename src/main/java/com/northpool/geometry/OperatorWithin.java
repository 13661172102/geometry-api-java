

package com.northpool.geometry;

/**
 * 
 * Relational operation Within.
 * 
 */
public abstract class OperatorWithin extends OperatorSimpleRelation {
	@Override
	public Type getType() {
		return Type.Within;
	}

	public static OperatorWithin local() {
		return (OperatorWithin) OperatorFactoryLocal.getInstance().getOperator(
				Type.Within);
	}

}
