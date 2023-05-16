

package com.northpool.geometry;

/**
 * A base class for the ExportToESRIShape Operator.
 */
abstract class OperatorGeodeticArea extends Operator {

	@Override
	public Type getType() {
		return Type.GeodeticArea;
	}

	/**
	 * Calculates the geodetic area of each geometry in the geometry cursor.
	 * 
	 * @param geoms
	 *            The geometry cursor to be iterated over to perform the
	 *            Geodetic Area calculation.
	 * @param sr
	 *            The SpatialReference of the geometries.
	 * @param geodeticCurveType
	 *            Use the {@link GeodeticCurveType} interface to choose the
	 *            interpretation of a line connecting two points.
	 * @param progressTracker
	 * @return Returns an array of the geodetic areas of the geometries.
	 */
	public abstract double[] execute(GeometryCursor geoms, SpatialReference sr,
			int geodeticCurveType, ProgressTracker progressTracker);

	/**
	 * Calculates the geodetic area of the input Geometry.
	 * 
	 * @param geom
	 *            The input Geometry for the geodetic area calculation.
	 * @param sr
	 *            The SpatialReference of the Geometry.
	 * @param geodeticCurveType
	 *            Use the {@link GeodeticCurveType} interface to choose the
	 *            interpretation of a line connecting two points.
	 * @param progressTracker
	 * @return Returns the geodetic area of the Geometry.
	 */
	public abstract double execute(Geometry geom, SpatialReference sr,
			int geodeticCurveType, ProgressTracker progressTracker);

	public static OperatorGeodeticArea local() {
		return (OperatorGeodeticArea) OperatorFactoryLocal.getInstance()
				.getOperator(Type.GeodeticArea);
	}

}
