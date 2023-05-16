
package com.northpool.geometry;

class OperatorDensifyByLengthLocal extends OperatorDensifyByLength {

	@Override
	public GeometryCursor execute(GeometryCursor inputGeometries,
			double maxLength, ProgressTracker progressTracker) {
		if (maxLength <= 0)
			// TODO fix geometry exception to match native implementation
			throw new IllegalArgumentException();// GEOMTHROW(invalid_argument);

		return new OperatorDensifyByLengthCursor(inputGeometries, maxLength,
				progressTracker);
	}

	@Override
	public Geometry execute(Geometry inputGeometry, double maxLength,
			ProgressTracker progressTracker) {
		SimpleGeometryCursor inputCursor = new SimpleGeometryCursor(
				inputGeometry);
		GeometryCursor outputCursor = execute(inputCursor, maxLength,
				progressTracker);
		return outputCursor.next();
	}
}
