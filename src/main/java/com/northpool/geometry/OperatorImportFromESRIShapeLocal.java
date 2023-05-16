

package com.northpool.geometry;

import java.nio.ByteBuffer;

/**
 * OperatorImportFromESRIShapeLocal implementation.
 */
class OperatorImportFromESRIShapeLocal extends OperatorImportFromESRIShape {

	@Override
	GeometryCursor execute(int importFlags, Geometry.Type type,
			ByteBufferCursor shapeBuffers) {
		return new OperatorImportFromESRIShapeCursor(importFlags, type.value(),
				shapeBuffers);
	}

	@Override
	public Geometry execute(int importFlags, Geometry.Type type,
			ByteBuffer shapeBuffer) {
		SimpleByteBufferCursor byteBufferCursor = new SimpleByteBufferCursor(
				shapeBuffer);
		GeometryCursor geometryCursor = execute(importFlags, type,
				byteBufferCursor);

		return geometryCursor.next();
	}

}
