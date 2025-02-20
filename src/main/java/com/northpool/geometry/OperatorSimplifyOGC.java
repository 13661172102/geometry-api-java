
package com.northpool.geometry;

/**
 * Simplifies the geometry or determines if the geometry is simple. Follows the OGC specification for the Simple Feature Access
 * v. 1.2.1 (06-103r4).
 * Uses tolerance to determine equal vertices or points of intersection. 
 *
 */
public abstract class OperatorSimplifyOGC extends Operator {
	@Override
	public Operator.Type getType() {
		return Operator.Type.SimplifyOGC;
	}

	/**
	 *Tests if the Geometry is simple for OGC specification.
	 *@param geom The Geometry to be tested.
	 *@param spatialRef Spatial reference to obtain the tolerance from. When null, the tolerance
	 *will be derived individually from geometry bounds.
	 *@param bForceTest When True, the Geometry will be tested regardless of the IsKnownSimple flag.
	 *@param progressTracker Allows cancellation of a long operation. Can be null.
	 *
	 *Note: As other methods in the OperatorSimplifyOGC, this method uses tolerance from the spatial reference.
	 *Points that are within the tolerance are considered equal.
	 *
	 *When this method returns true, the OperatorSimplify.isSimpleAsFeature will return true also (this does not necessary happen the other way around). 
	 */
	public abstract boolean isSimpleOGC(Geometry geom,
			SpatialReference spatialRef, boolean bForceTest,
			NonSimpleResult result, ProgressTracker progressTracker);

	/**
	 * Processes geometry cursor to ensure its geometries are simple for OGC specification.
	 * @param geoms Geometries to be simplified.
	 * @param sr Spatial reference to obtain the tolerance from. When null, the tolerance
	 * will be derived individually for each geometry from its bounds.
	 * @param bForceSimplify When True, the Geometry will be simplified regardless of the internal IsKnownSimple flag.
	 * @param progressTracker Allows cancellation of a long operation. Can be null.
	 * @return Returns a GeometryCursor of simplified geometries.
	 * 
	 * The isSimpleOGC returns true after this call.
	 */
	public abstract GeometryCursor execute(GeometryCursor geoms,
			SpatialReference sr, boolean bForceSimplify,
			ProgressTracker progressTracker);

	/**
	 * Processes geometry to ensure it is simple for OGC specification.
	 * @param geom The geometry to be simplified.
	 * @param sr Spatial reference to obtain the tolerance from. When null, the tolerance
	 * will be derived individually from geometry bounds.
	 * @param bForceSimplify When True, the Geometry will be simplified regardless of the internal IsKnownSimple flag.
	 * @param progressTracker Allows cancellation of a long operation. Can be null.
	 * @return Returns a simple Geometry that should be visually equivalent to the input geometry.
	 * 
	 * The isSimpleOGC returns true after this call.
	 */
	public abstract Geometry execute(Geometry geom, SpatialReference sr,
			boolean bForceSimplify, ProgressTracker progressTracker);

	public static OperatorSimplifyOGC local() {
		return (OperatorSimplifyOGC) OperatorFactoryLocal.getInstance()
				.getOperator(Type.SimplifyOGC);
	}

}
