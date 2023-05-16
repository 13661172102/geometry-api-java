

package com.northpool.geometry;

/**
 *Export to GeoJson format.
 */
public abstract class OperatorExportToGeoJson extends Operator {
	@Override
	public Type getType() {
		return Type.ExportToGeoJson;
	}

	/**
	 * Performs the ExportToGeoJson operation
	 * @param spatialReference The SpatialReference of the Geometry. Will be written as "crs":null if the spatialReference is null.
	 * @param geometryCursor The cursor of geometries to write as GeoJson.
	 * @return Returns a JsonCursor.
	 */
	public abstract JsonCursor execute(SpatialReference spatialReference, GeometryCursor geometryCursor);

	/**
	 * Performs the ExportToGeoJson operation
	 * @param spatialReference The SpatialReference of the Geometry. Will be written as "crs":null if the spatialReference is null.
	 * @param geometry The Geometry to write as GeoJson.
	 * @return Returns a string in GeoJson format.
	 */
	public abstract String execute(SpatialReference spatialReference, Geometry geometry);

	/**
	 * Performs the ExportToGeoJson operation
	 * @param exportFlags Use the {@link GeoJsonExportFlags} interface.
	 * @param spatialReference The SpatialReference of the Geometry. Will be written as "crs":null if the spatialReference is null.
	 * @param geometry The Geometry to write as GeoJson.
	 * @return Returns a string in GeoJson format.
	 */
	public abstract String execute(int exportFlags, SpatialReference spatialReference, Geometry geometry);

	/**
	 * Performs the ExportToGeoJson operation. Will not write out a spatial reference or crs tag. Assumes the geometry is in wgs84.
	 * @param geometry The Geometry to write as GeoJson.
	 * @return Returns a string in GeoJson format.
	 */
	public abstract String execute(Geometry geometry);

	/**
	 * Performs the ExportToGeoJson operation on a spatial reference.
	 *
	 * @param export_flags      The flags used for the export.
	 * @param spatial_reference The spatial reference being exported. Cannot be null.
	 * @return Returns the crs value object.
	 */
	public abstract String exportSpatialReference(int export_flags, SpatialReference spatial_reference);

	public static OperatorExportToGeoJson local() {
		return (OperatorExportToGeoJson) OperatorFactoryLocal.getInstance().getOperator(Type.ExportToGeoJson);
	}
}
