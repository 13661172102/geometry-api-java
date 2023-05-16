

package com.northpool.geometry;

interface PathFlags {
	public static final int enumClosed = 1;
	public static final int enumHasNonlinearSegments = 2;// set when the given
															// part has
															// non-linear
															// segments
	public static final int enumOGCStartPolygon = 4;// set at the start of a
													// Polygon when viewed as an
													// OGC MultiPolygon
	public static final int enumCalcMask = 4;// mask of flags that are obtained
												// by calculation and depend on
												// the order of MultiPath parts.

}
