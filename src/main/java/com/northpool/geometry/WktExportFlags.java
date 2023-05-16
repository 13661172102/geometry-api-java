
package com.northpool.geometry;

/**
*Flags used by the OperatorExportToWkt
*/
public interface WktExportFlags {
	public static final int wktExportDefaults = 0;
	public static final int wktExportPoint = 1;
	public static final int wktExportMultiPoint = 2;
	public static final int wktExportLineString = 4;
	public static final int wktExportMultiLineString = 8;
	public static final int wktExportPolygon = 16;
	public static final int wktExportMultiPolygon = 32;
	public static final int wktExportStripZs = 64;
	public static final int wktExportStripMs = 128;
	public static final int wktExportFailIfNotSimple = 4096;
	public static final int wktExportPrecision16 = 0x2000;
	public static final int wktExportPrecision15 = 0x4000;
	public static final int wktExportPrecision14 = 0x6000;
	public static final int wktExportPrecision13 = 0x8000;
	public static final int wktExportPrecision12 = 0xa000;
	public static final int wktExportPrecision11 = 0xc000;
	public static final int wktExportPrecision10 = 0xe000;
}
