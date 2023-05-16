

package com.northpool.geometry;

import java.nio.ByteBuffer;

/**
 *Import from ESRI shape format.
 */
public abstract class OperatorImportFromESRIShape extends Operator {
	@Override
	public Type getType() {
		return Type.ImportFromESRIShape;
	}

	/**
	 * Performs the ImportFromESRIShape operation on a stream of shape buffers
	 * @param importFlags Use the {@link ShapeImportFlags} interface. The default is 0, which means geometry comes from a trusted source and is topologically simple.
	 * If the geometry comes from non-trusted source (that is it can be non-simple), pass ShapeImportNonTrusted.
	 * @param type The geometry type that you want to import. Use the {@link Geometry.Type} enum. It can be Geometry.Type.Unknown if the type of geometry has to be
	 * figured out from the shape buffer.
	 * @param shapeBuffers The cursor over shape buffers that hold the Geometries in ESRIShape format.
	 * @return Returns a GeometryCursor.
	 */
	abstract GeometryCursor execute(int importFlags, Geometry.Type type,
			ByteBufferCursor shapeBuffers);

	/**
	 * Performs the ImportFromESRIShape operation.
	 * @param importFlags Use the {@link ShapeImportFlags} interface. The default is 0, which means geometry comes from a trusted source and is topologically simple.
	 * If the geometry comes from non-trusted source (that is it can be non-simple), pass ShapeImportNonTrusted.
	 * @param type The geometry type that you want to import. Use the {@link Geometry.Type} enum. It can be Geometry.Type.Unknown if the type of geometry has to be
	 * figured out from the shape buffer.
	 * @param shapeBuffer The buffer holding the Geometry in ESRIShape format.
	 * @return Returns the imported Geometry.
	 */
	public abstract Geometry execute(int importFlags, Geometry.Type type,
			ByteBuffer shapeBuffer);

	public static OperatorImportFromESRIShape local() {
		return (OperatorImportFromESRIShape) OperatorFactoryLocal.getInstance()
				.getOperator(Type.ImportFromESRIShape);
	}

}
