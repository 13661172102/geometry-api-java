

package com.northpool.geometry;

/**
*Flags used by the OperatorImportFromEsriShape.
*/
public interface ShapeImportFlags {
	public static final int ShapeImportDefaults = 0;//!<Default import flags
	static final int ShapeImportNoSwap = 1;
	public static final int ShapeImportNonTrusted = 2;//!<Pass this flag to the import to indicate the shape can contain non-simple geometry.
	static final int ShapeImportAttach = 4;
}
