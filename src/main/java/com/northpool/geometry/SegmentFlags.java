

package com.northpool.geometry;

interface SegmentFlags {
	public static final int enumLineSeg = 1;
	public static final int enumBezierSeg = 2;
	public static final int enumArcSeg = 4;
	public static final int enumNonlinearSegmentMask = 6;
	public static final int enumSegmentMask = 7;
	public static final int enumDensified = 8; // set for segments that have
												// been produced from a
												// densified non-linear segment.
}
