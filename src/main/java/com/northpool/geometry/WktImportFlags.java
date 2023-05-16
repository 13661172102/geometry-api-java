
package com.northpool.geometry;

/**
*Flags used by the OperatorImportFromWkt.
*/
public interface WktImportFlags {
	public static final int wktImportDefaults = 0;//!<Default import flags
	public static final int wktImportNonTrusted = 2;//!<Pass this flag to the import to indicate the shape can contain non-simple geometry.
}
