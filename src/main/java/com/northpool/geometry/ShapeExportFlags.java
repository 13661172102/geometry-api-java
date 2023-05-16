

package com.northpool.geometry;

/**
*Flags used by the OperatorExportToEsriShape.
*/
public interface ShapeExportFlags {
	public static final int ShapeExportDefaults = 0;//!<Default export flags
	static final int ShapeExportNoSwap = 1;
	static final int ShapeExportAngularDensify = 2;
	static final int ShapeExportDistanceDensify = 4;
	static final int ShapeExportTrueNaNs = 8;
	public static final int ShapeExportStripZs = 16;
	public static final int ShapeExportStripMs = 32;
	public static final int ShapeExportStripIDs = 64;
	static final int ShapeExportStripTextures = 128;
	static final int ShapeExportStripNormals = 256;
	static final int ShapeExportStripMaterials = 512;
	static final int ShapeExportNewArcFormat = 1024;
	static final int ShapeExportNoCompress = 2048;
}
