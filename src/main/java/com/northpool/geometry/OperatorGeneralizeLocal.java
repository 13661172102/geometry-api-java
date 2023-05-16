
package com.northpool.geometry;

final class OperatorGeneralizeLocal extends OperatorGeneralize {

	@Override
	public GeometryCursor execute(GeometryCursor geoms, double maxDeviation,
			boolean bRemoveDegenerateParts, ProgressTracker progressTracker) {
		// TODO Auto-generated method stub

		return new OperatorGeneralizeCursor(geoms, maxDeviation,
				bRemoveDegenerateParts, progressTracker);
	}

	@Override
	public Geometry execute(Geometry geom, double maxDeviation,
			boolean bRemoveDegenerateParts, ProgressTracker progressTracker) {
		SimpleGeometryCursor inputGeomCurs = new SimpleGeometryCursor(geom);
		GeometryCursor geometryCursor = execute(inputGeomCurs, maxDeviation,
				bRemoveDegenerateParts, progressTracker);

		return geometryCursor.next();
	}

}
