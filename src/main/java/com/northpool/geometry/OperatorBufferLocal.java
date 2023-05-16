

package com.northpool.geometry;

class OperatorBufferLocal extends OperatorBuffer {

	@Override
	public GeometryCursor execute(GeometryCursor inputGeometries,
			SpatialReference sr, double[] distances, boolean bUnion,
			ProgressTracker progressTracker) {
		return execute(inputGeometries, sr, distances, NumberUtils.NaN(), 96,
				bUnion, progressTracker);
	}

	@Override
	public Geometry execute(Geometry inputGeometry, SpatialReference sr,
			double distance, ProgressTracker progressTracker) {
		SimpleGeometryCursor inputCursor = new SimpleGeometryCursor(
				inputGeometry);
		double[] distances = new double[1];
		distances[0] = distance;
		GeometryCursor outputCursor = execute(inputCursor, sr, distances,
				false, progressTracker);

		return outputCursor.next();
	}

	@Override
	public GeometryCursor execute(GeometryCursor inputGeometries,
			SpatialReference sr, double[] distances, double max_deviation,
			int max_vertices_in_full_circle, boolean b_union,
			ProgressTracker progressTracker) {
		if (b_union) {
			OperatorBufferCursor cursor = new OperatorBufferCursor(
					inputGeometries, sr, distances, max_deviation,
					max_vertices_in_full_circle, false, progressTracker);
			return OperatorUnion.local().execute(cursor, sr, progressTracker);// (int)Operator_union::Options::enum_disable_edge_dissolver
		} else {
			return new OperatorBufferCursor(inputGeometries, sr, distances,
					max_deviation, max_vertices_in_full_circle, false,
					progressTracker);
		}
	}
}
