

package com.northpool.geometry;

import java.nio.ByteBuffer;

/**
 *Import from WKB format.
 */
public abstract class OperatorImportFromWkb extends Operator {

	@Override
	public Type getType() {
		return Type.ImportFromWkb;
	}

	/**
	 * Performs the ImportFromWKB operation.
	 * @param importFlags Use the {@link WkbImportFlags} interface.
	 * @param type Use the {@link Geometry.Type} enum. 
	 * @param wkbBuffer The buffer holding the Geometry in wkb format.
	 * @return Returns the imported Geometry.
	 */
	public abstract Geometry execute(int importFlags, Geometry.Type type,
			ByteBuffer wkbBuffer, ProgressTracker progress_tracker);

	/**
	 * Performs the ImportFromWkb operation.
	 * @param importFlags Use the {@link WkbImportFlags} interface.
	 * @param wkbBuffer The buffer holding the Geometry in wkb format.
	 * @return Returns the imported OGCStructure.
	 */
	public abstract OGCStructure executeOGC(int importFlags,
			ByteBuffer wkbBuffer, ProgressTracker progress_tracker);

	public static OperatorImportFromWkb local() {
		return (OperatorImportFromWkb) OperatorFactoryLocal.getInstance()
				.getOperator(Type.ImportFromWkb);
	}

}
