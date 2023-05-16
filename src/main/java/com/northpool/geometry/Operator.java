
package com.northpool.geometry;

import com.northpool.geometry.Geometry.GeometryAccelerationDegree;

/**
 * The base class for Geometry Operators.
 */
public abstract class Operator {
	/**
	 * The operator type enum.
	 */
	public enum Type {
		Project,

		ExportToJson, ImportFromJson,
		ExportToESRIShape, ImportFromESRIShape,

		Union, Difference,

		Proximity2D, Centroid2D,

		Relate, Equals, Disjoint, Intersects, Within, Contains, Crosses, Touches, Overlaps,

		Buffer, Distance, Intersection, Clip, Cut, DensifyByLength,
		DensifyByAngle, LabelPoint,

		GeodesicBuffer, GeodeticDensifyByLength, ShapePreservingDensify, GeodeticLength, GeodeticArea,

		Simplify, SimplifyOGC, Offset, Generalize,

		ExportToWkb, ImportFromWkb, ExportToWkt, ImportFromWkt, ImportFromGeoJson, ExportToGeoJson, SymmetricDifference, ConvexHull, Boundary

	}

	public abstract Type getType();

	/**
	 * Processes Geometry to accelerate operations on it. The Geometry and it's
	 * copies remain accelerated until modified. The acceleration of Geometry
	 * can be a time consuming operation. The accelerated geometry also takes
	 * more memory. Some operators share the same accelerator, some require
	 * a different one. If the accelerator is built for the given parameters,
	 * the method returns immediately.
	 * 
	 * @param geometry
	 *            The geometry to be accelerated
	 * @param spatialReference
	 *            The spatial reference of that geometry
	 * @param accelDegree The acceleration degree for geometry.
	 */
	public boolean accelerateGeometry(Geometry geometry,
			SpatialReference spatialReference,
			GeometryAccelerationDegree accelDegree) {
		// Override at specific Operator level
		return false;
	}

	/**
	 * Returns true if the geometry can be accelerated.
	 * 
	 * @param geometry
	 * @return true for geometries that can be accelerated, false for geometries
	 *         that cannot
	 */
	public boolean canAccelerateGeometry(Geometry geometry) {
		// Override at specific Operator level
		return false;
	}

	/**
	 * Removes accelerators from given geometry.
	 * @param geometry The geometry instance to remove accelerators from.
	 */
	public static void deaccelerateGeometry(Geometry geometry) {
		Geometry.Type gt = geometry.getType();
		if (Geometry.isMultiVertex(gt.value()))
		{
			GeometryAccelerators accel = ((MultiVertexGeometryImpl) geometry
					._getImpl())._getAccelerators();
			if (accel != null){
				accel._setRasterizedGeometry(null);
				accel._setQuadTree(null);
			}
		}
	}

}
