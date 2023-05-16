
package com.northpool.geometry;

public abstract class OperatorOffset extends Operator {
	@Override
	public Operator.Type getType() {
		return Operator.Type.Offset;
	}

	/**
	 * Join types for the offset operation.
	 */
	public enum JoinType {
		Round, Bevel, Miter, Square,
	};

	/**
	 * Creates offset version of the input geometries.
	 * 
	 * The offset operation creates a geometry that is a constant distance from
	 * an input polyline or polygon. It is similar to buffering, but produces a
	 * one sided result. If offsetDistance greater than 0, then the offset geometry is
	 * constructed to the right of the oriented input geometry, otherwise it is
	 * constructed to the left. For a simple polygon, the orientation of outer
	 * rings is clockwise and for inner rings it is counter clockwise. So the
	 * "right side" of a simple polygon is always its inside. The bevelRatio is
	 * multiplied by the offset distance and the result determines how far a
	 * mitered offset intersection can be from the input curve before it is
	 * beveled.
	 * 
	 * @param inputGeometries
	 *            The geometries to calculate offset for. Point and MultiPoint
	 *            are not supported.
	 * @param sr
	 *            The SpatialReference of the Geometries.
	 * @param distance
	 *            The offset distance for the Geometries.
	 * @param joins
	 *            The join type of the offset geometry.
	 * @param bevelRatio
	 *            The ratio used to produce a bevel join instead of a miter join
	 *            (used only when joins is Miter)
	 * @param flattenError
	 *            The maximum distance of the resulting segments compared to the
	 *            true circular arc (used only when joins is Round). If
	 *            flattenError is 0, tolerance value is used. Also, the
	 *            algorithm never produces more than around 180 vertices for
	 *            each round join.
	 * @return Returns the result of the offset operation.
	 */
	public abstract GeometryCursor execute(GeometryCursor inputGeometries,
			SpatialReference sr, double distance, JoinType joins,
			double bevelRatio, double flattenError,
			ProgressTracker progressTracker);

	/**
	 * Creates offset version of the input geometry.
	 * 
	 * The offset operation creates a geometry that is a constant distance from
	 * an input polyline or polygon. It is similar to buffering, but produces a
	 * one sided result. If offsetDistance greater than 0, then the offset geometry is
	 * constructed to the right of the oriented input geometry, otherwise it is
	 * constructed to the left. For a simple polygon, the orientation of outer
	 * rings is clockwise and for inner rings it is counter clockwise. So the
	 * "right side" of a simple polygon is always its inside. The bevelRatio is
	 * multiplied by the offset distance and the result determines how far a
	 * mitered offset intersection can be from the input curve before it is
	 * beveled.
	 * 
	 * @param inputGeometry
	 *            The geometry to calculate offset for. Point and MultiPoint are
	 *            not supported.
	 * @param sr
	 *            The SpatialReference of the Geometries.
	 * @param distance
	 *            The offset distance for the Geometries.
	 * @param joins
	 *            The join type of the offset geometry.
	 * @param bevelRatio
	 *            The ratio used to produce a bevel join instead of a miter join
	 *            (used only when joins is Miter)
	 * @param flattenError
	 *            The maximum distance of the resulting segments compared to the
	 *            true circular arc (used only when joins is Round). If
	 *            flattenError is 0, tolerance value is used. Also, the
	 *            algorithm never produces more than around 180 vetices for each
	 *            round join.
	 * @return Returns the result of the offset operation.
	 */
	public abstract Geometry execute(Geometry inputGeometry,
			SpatialReference sr, double distance, JoinType joins,
			double bevelRatio, double flattenError,
			ProgressTracker progressTracker);

	public static OperatorOffset local() {
		return (OperatorOffset) OperatorFactoryLocal.getInstance().getOperator(
				Type.Offset);
	}

}
