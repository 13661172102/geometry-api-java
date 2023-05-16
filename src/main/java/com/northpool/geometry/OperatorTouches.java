

package com.northpool.geometry;

/**
 * Relational operation Touches.
 * 
 */
public abstract class OperatorTouches extends OperatorSimpleRelation {
	@Override
	public Type getType() {
		return Type.Touches;
	}

	public static OperatorTouches local() {
		return (OperatorTouches) OperatorFactoryLocal.getInstance()
				.getOperator(Type.Touches);
	}

}
