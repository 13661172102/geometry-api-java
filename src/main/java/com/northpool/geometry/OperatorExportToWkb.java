

package com.northpool.geometry;

import java.nio.ByteBuffer;

/**
 *Export to WKB format.
 */
public abstract class OperatorExportToWkb extends Operator {
	@Override
	public Type getType() {
		return Type.ExportToWkb;
	}

	/**
	 * Performs the ExportToWKB operation.
	 * @param exportFlags Use the {@link WkbExportFlags} interface.
	 * @param geometry The Geometry being exported.
	 * @return Returns a ByteBuffer object containing the Geometry in WKB format
	 */
	public abstract ByteBuffer execute(int exportFlags, Geometry geometry,
			ProgressTracker progressTracker);

	/**
	 * Performs the ExportToWKB operation.
	 * @param exportFlags Use the {@link WkbExportFlags} interface.
	 * @param geometry The Geometry being exported.
	 * @param wkbBuffer The ByteBuffer to contain the exported Geometry in WKB format.
	 * @return If the input buffer is null, then the size needed for the buffer is returned. Otherwise the number of bytes written to the buffer is returned.
	 */
	public abstract int execute(int exportFlags, Geometry geometry,
			ByteBuffer wkbBuffer, ProgressTracker progressTracker);

	public static OperatorExportToWkb local() {
		return (OperatorExportToWkb) OperatorFactoryLocal.getInstance()
				.getOperator(Type.ExportToWkb);
	}

}
