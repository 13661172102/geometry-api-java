

package com.northpool.geometry;

/**
 * Relational operation Crosses.
 */
public abstract class OperatorCrosses extends OperatorSimpleRelation {
	@Override
	public Type getType() {
		return Type.Crosses;
	}

	public static OperatorCrosses local() {
		return (OperatorCrosses) OperatorFactoryLocal.getInstance()
				.getOperator(Type.Crosses);
	}

}
