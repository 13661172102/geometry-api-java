

package com.northpool.geometry;

/**
 * Finds closest vertices of the Geometry.
 */
public abstract class OperatorProximity2D extends Operator {
	@Override
	public Type getType() {
		return Type.Proximity2D;
	}

	/**
	 *Returns the nearest coordinate on the Geometry to the given input point.
	 *@param geom The input Geometry.
	 *@param inputPoint The query point.
	 *@param bTestPolygonInterior When true and geom is a polygon, the function will test if the input_point is inside of the polygon. Points that are
	 *inside of the polygon have zero distance to the polygon. When false, the function will not check if the point is inside of the polygon,
	 *but only determine proximity to the boundary.
	 *@param bCalculateLeftRightSide The function will calculate left/right side of polylines or polygons when the parameter is True.
	 *\return Returns the result of proximity calculation. See Proximity_2D_result.
	 */
	public abstract Proximity2DResult getNearestCoordinate(Geometry geom,
			Point inputPoint, boolean bTestPolygonInterior,
			boolean bCalculateLeftRightSide);

	/**
	 *Returns the nearest coordinate on the Geometry to the given input point.
	 *@param geom The input Geometry.
	 *@param inputPoint The query point.
	 *@param bTestPolygonInterior When true and geom is a polygon, the function will test if the input_point is inside of the polygon. Points that are
	 *inside of the polygon have zero distance to the polygon. When false, the function will not check if the point is inside of the polygon,
	 *but only determine proximity to the boundary.
	 *\return Returns the result of proximity calculation. See Proximity_2D_result.
	 */
	public abstract Proximity2DResult getNearestCoordinate(Geometry geom,
			Point inputPoint, boolean bTestPolygonInterior);

	/**
	 * Returns the nearest vertex of the Geometry to the given input point.
	 */
	public abstract Proximity2DResult getNearestVertex(Geometry geom,
			Point inputPoint);

	/**
	 * Returns vertices of the Geometry that are closer to the given point than
	 * the given radius.
	 * 
	 * @param geom
	 *            The input Geometry.
	 * @param inputPoint
	 *            The query point.
	 * @param searchRadius
	 *            The maximum distance to the query point of the vertices.
	 * @param maxVertexCountToReturn
	 *            The maximum vertex count to return. The function returns no
	 *            more than this number of vertices.
	 * @return The array of vertices that are in the given search radius to the
	 *         point. The array is sorted by distance to the queryPoint with the
	 *         closest point first. When there are more than the
	 *         maxVertexCountToReturn vertices to return, it returns the closest
	 *         vertices. The array will be empty when geom is empty.
	 */
	public abstract Proximity2DResult[] getNearestVertices(Geometry geom,
			Point inputPoint, double searchRadius, int maxVertexCountToReturn);

	public static OperatorProximity2D local() {
		return (OperatorProximity2D) OperatorFactoryLocal.getInstance()
				.getOperator(Type.Proximity2D);
	}

	interface ProxResultInfo {
		static final int rightSide = 0x1;
	}

}
