
package com.northpool.geometry;

public interface GeoJsonImportFlags {
	public static final int geoJsonImportDefaults = 0;
	@Deprecated static final int geoJsonImportNonTrusted = 2;
	/**
	 * If set, the import will skip CRS.
	 */
	public static final int geoJsonImportSkipCRS = 8;
	/**
	 * If set, and the geojson does not have a spatial reference, the result geometry will not have one too, otherwise
	 * it'll assume WGS84.
	 */
	public static final int geoJsonImportNoWGS84Default = 16;	
}
