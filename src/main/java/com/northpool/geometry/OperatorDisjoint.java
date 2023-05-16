

package com.northpool.geometry;

/**
 * Relational operation Disjoint.
 */
public abstract class OperatorDisjoint extends OperatorSimpleRelation {

	@Override
	public Type getType() {
		return Type.Disjoint;
	}

	public static OperatorDisjoint local() {
		return (OperatorDisjoint) OperatorFactoryLocal.getInstance()
				.getOperator(Type.Disjoint);
	}

}
