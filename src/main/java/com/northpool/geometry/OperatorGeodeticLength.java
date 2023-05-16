

package com.northpool.geometry;

/**
 * 
 * Geodetic length calculation.
 * 
 */
abstract class OperatorGeodeticLength extends Operator {

	@Override
	public Type getType() {
		return Operator.Type.GeodeticLength;
	}

	/**
	 * Calculates the geodetic length of the input Geometry.
	 * 
	 * @param geom
	 *            The input Geometry for the geodetic length calculation.
	 * @param sr
	 *            The SpatialReference of the Geometry.
	 * @param geodeticCurveType
	 *            Use the {@link GeodeticCurveType} interface to choose the
	 *            interpretation of a line connecting two points.
	 * @param progressTracker
	 * @return Returns the geoetic length of the Geometry.
	 */
	public abstract double execute(Geometry geom, SpatialReference sr,
			int geodeticCurveType, ProgressTracker progressTracker);

	public static OperatorGeodeticLength local() {
		return (OperatorGeodeticLength) OperatorFactoryLocal.getInstance()
				.getOperator(Type.GeodeticLength);
	}

}
