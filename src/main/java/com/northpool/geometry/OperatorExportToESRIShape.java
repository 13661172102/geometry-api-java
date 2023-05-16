

package com.northpool.geometry;

import java.nio.ByteBuffer;

/**
 *Export to ESRI shape format.
 */
public abstract class OperatorExportToESRIShape extends Operator {
	@Override
	public Type getType() {
		return Type.ExportToESRIShape;
	}

	/**
	 * Performs the ExportToESRIShape operation
	 * 
	 * @return Returns a ByteBufferCursor.
	 */
	abstract ByteBufferCursor execute(int exportFlags,
			GeometryCursor geometryCursor);

	/**
	 * Performs the ExportToESRIShape operation.
	 * @param exportFlags Use the {@link ShapeExportFlags} interface.
	 * @param geometry The Geometry being exported.
	 * @return Returns a ByteBuffer object containing the Geometry in ESRIShape format.
	 */
	public abstract ByteBuffer execute(int exportFlags, Geometry geometry);

	/**
	 * Performs the ExportToESRIShape operation.
	 * @param exportFlags Use the {@link ShapeExportFlags} interface.
	 * @param geometry The Geometry being exported.
	 * @param shapeBuffer The ByteBuffer to contain the exported Geometry in ESRIShape format.
	 * @return If the input buffer is null, then the size needed for the buffer is returned. Otherwise the number of bytes written to the buffer is returned.
	 */
	public abstract int execute(int exportFlags, Geometry geometry,
			ByteBuffer shapeBuffer);
	
	public static OperatorExportToESRIShape local() {
		return (OperatorExportToESRIShape) OperatorFactoryLocal.getInstance()
				.getOperator(Type.ExportToESRIShape);
	}	
}
