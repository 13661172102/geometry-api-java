

package com.northpool.geometry;

/**
*Flags used by the OperatorImportFromWkb.
*/
public interface WkbImportFlags {
	public static final int wkbImportDefaults = 0;//!<Default import flags
	public static final int wkbImportNonTrusted = 2;//!<Pass this flag to the import to indicate the shape can contain non-simple geometry.
}
