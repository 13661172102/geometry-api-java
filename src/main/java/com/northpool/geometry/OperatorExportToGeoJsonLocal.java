

package com.northpool.geometry;

class OperatorExportToGeoJsonLocal extends OperatorExportToGeoJson {
	@Override
	public JsonCursor execute(SpatialReference spatialReference, GeometryCursor geometryCursor) {
		return new OperatorExportToGeoJsonCursor(GeoJsonExportFlags.geoJsonExportDefaults, spatialReference, geometryCursor);
	}

	@Override
	public String execute(SpatialReference spatialReference, Geometry geometry) {
		return OperatorExportToGeoJsonCursor.exportToGeoJson(GeoJsonExportFlags.geoJsonExportDefaults, geometry, spatialReference);
	}

	@Override
	public String execute(int exportFlags, SpatialReference spatialReference, Geometry geometry) {
		return OperatorExportToGeoJsonCursor.exportToGeoJson(exportFlags, geometry, spatialReference);
	}

	@Override
	public String execute(Geometry geometry) {
		return OperatorExportToGeoJsonCursor.exportToGeoJson(GeoJsonExportFlags.geoJsonExportSkipCRS, geometry, null);
	}

	@Override
	public String exportSpatialReference(int export_flags, SpatialReference spatial_reference) {
		return OperatorExportToGeoJsonCursor.exportSpatialReference(export_flags, spatial_reference);
	}
}
