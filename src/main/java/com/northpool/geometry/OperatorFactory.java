


package com.northpool.geometry;

import com.northpool.geometry.Operator.Type;

/**
 *An abstract class that represent the basic OperatorFactory interface.
 */
public abstract class OperatorFactory {
	/**
	 *Returns True if the given operator exists. The type is one of the Operator::Type values or a user defined value.
	 */
	public abstract boolean isOperatorSupported(Type type);

	/**
	 *Returns an operator of the given type. Throws an exception if the operator is not supported.
	 */
	public abstract Operator getOperator(Type type);
}
