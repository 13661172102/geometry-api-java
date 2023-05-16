

package com.northpool.geometry;

//This is a stub
class OperatorProjectLocal extends OperatorProject {

	@Override
	public GeometryCursor execute(GeometryCursor inputGeoms,
			ProjectionTransformation transform, ProgressTracker progressTracker) {
		throw new GeometryException("not implemented");
	}

	public Geometry execute(Geometry inputGeom,
			ProjectionTransformation transform, ProgressTracker progressTracker) {
		throw new GeometryException("not implemented");
	}

	@Override
	public int transform(ProjectionTransformation transform, Point[] pointsIn,
			int count, Point[] pointsOut) {
		throw new GeometryException("not implemented");
	}

	public double[] transform(ProjectionTransformation transform,
			double[] coordsSrc, int pointCount) {
		throw new GeometryException("not implemented");
	}

	@Override
	public Geometry foldInto360RangeGeodetic(/* const */Geometry _geom, /* const */
	SpatialReference pannableSR, /* GeodeticCurveType */int curveType) {
		throw new GeometryException("not implemented");
	}

	@Override
	public Geometry foldInto360Range(/* const */Geometry geom, /* const */
	SpatialReference pannableSR) {
		throw new GeometryException("not implemented");
	}
}
