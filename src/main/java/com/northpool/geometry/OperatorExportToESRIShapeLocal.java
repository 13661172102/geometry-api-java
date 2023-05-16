

package com.northpool.geometry;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * OperatorExportToESRIShape implementation.
 */
class OperatorExportToESRIShapeLocal extends OperatorExportToESRIShape {

	@Override
	ByteBufferCursor execute(int exportFlags, GeometryCursor geometryCursor) {
		return new OperatorExportToESRIShapeCursor(exportFlags, geometryCursor);
	}

	@Override
	public ByteBuffer execute(int exportFlags, Geometry geometry) {
		ByteBuffer shapeBuffer = null;
		int size = OperatorExportToESRIShapeCursor.exportToESRIShape(
				exportFlags, geometry, shapeBuffer);
		shapeBuffer = ByteBuffer.allocate(size).order(ByteOrder.LITTLE_ENDIAN);
		OperatorExportToESRIShapeCursor.exportToESRIShape(exportFlags,
				geometry, shapeBuffer);
		return shapeBuffer;
	}

	@Override
	public int execute(int exportFlags, Geometry geometry,
			ByteBuffer shapeBuffer) {
		shapeBuffer.order(ByteOrder.LITTLE_ENDIAN);
		return OperatorExportToESRIShapeCursor.exportToESRIShape(exportFlags,
				geometry, shapeBuffer);
	}
}
