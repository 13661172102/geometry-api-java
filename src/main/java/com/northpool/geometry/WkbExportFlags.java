

package com.northpool.geometry;

/**
*Flags used by the OperatorExpotToWkb.
*/
public interface WkbExportFlags {
	public static final int wkbExportDefaults = 0;//!<Default flags
	public static final int wkbExportPoint = 1;
	public static final int wkbExportMultiPoint = 2;
	public static final int wkbExportLineString = 4;
	public static final int wkbExportMultiLineString = 8;
	public static final int wkbExportPolygon = 16;
	public static final int wkbExportMultiPolygon = 32;
	public static final int wkbExportStripZs = 64;
	public static final int wkbExportStripMs = 128;
	public static final int wkbExportFailIfNotSimple = 4096;
}
