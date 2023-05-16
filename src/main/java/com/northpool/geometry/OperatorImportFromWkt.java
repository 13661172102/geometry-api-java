
package com.northpool.geometry;

public abstract class OperatorImportFromWkt extends Operator {
	@Override
	public Type getType() {
		return Type.ImportFromWkb;
	}

	/**
	 * Performs the ImportFromWkt operation.
	 * @param import_flags Use the {@link WktImportFlags} interface.
	 * @param type Use the {@link Geometry.Type} enum. 
	 * @param wkt_string The string holding the Geometry in wkt format.
	 * @return Returns the imported Geometry.
	 */
	public abstract Geometry execute(int import_flags, Geometry.Type type,
			String wkt_string, ProgressTracker progress_tracker);

	/**
	 * Performs the ImportFromWkt operation.
	 * @param import_flags Use the {@link WktImportFlags} interface.
	 * @param wkt_string The string holding the Geometry in wkt format.
	 * @return Returns the imported OGCStructure.
	 */
	public abstract OGCStructure executeOGC(int import_flags,
			String wkt_string, ProgressTracker progress_tracker);

	public static OperatorImportFromWkt local() {
		return (OperatorImportFromWkt) OperatorFactoryLocal.getInstance()
				.getOperator(Type.ImportFromWkt);
	}

}
