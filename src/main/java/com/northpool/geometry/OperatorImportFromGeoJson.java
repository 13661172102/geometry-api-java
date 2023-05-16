
package com.northpool.geometry;

public abstract class OperatorImportFromGeoJson extends Operator {

	@Override
	public Type getType() {
		return Type.ImportFromGeoJson;
	}

	/**
	 * Performs the ImportFromGeoJson operation.
	 *
	 * @param type Use the {@link Geometry.Type} enum.
	 * @param jsonReader The JSONReader.
	 * @return Returns the imported MapGeometry.
	 * @throws JsonGeometryException
	 */
	public abstract MapGeometry execute(int importFlags, Geometry.Type type, JsonReader jsonReader, ProgressTracker progressTracker);

	/**
	 * Deprecated, use version without import_flags.
	 * 
	 * Performs the ImportFromGeoJson operation.
	 *
	 * @param import_flags Use the {@link GeoJsonImportFlags} interface.
	 * @param type Use the {@link Geometry.Type} enum.
	 * @param geoJsonString The string holding the Geometry in geoJson format.
	 * @return Returns the imported MapGeometry.
	 * 
	 */
	public abstract MapGeometry execute(int import_flags, Geometry.Type type, String geoJsonString, ProgressTracker progress_tracker);

	/**
	 * 
	 * Performs the ImportFromGeoJson operation.
	 *
	 * @param import_flags Use the {@link GeoJsonImportFlags} interface.
	 * @param geoJsonString The string holding the Geometry in geoJson format.
	 * @return Returns the imported MapOGCStructure.
	 */
	public abstract MapOGCStructure executeOGC(int import_flags, String geoJsonString, ProgressTracker progress_tracker);

	public static OperatorImportFromGeoJson local() {
		return (OperatorImportFromGeoJson) OperatorFactoryLocal.getInstance().getOperator(Type.ImportFromGeoJson);
	}
}
