

package com.northpool.geometry;

import static sun.misc.Unsafe.ARRAY_BYTE_BASE_OFFSET;
import static sun.misc.Unsafe.ARRAY_BYTE_INDEX_SCALE;
import static sun.misc.Unsafe.ARRAY_CHAR_BASE_OFFSET;
import static sun.misc.Unsafe.ARRAY_CHAR_INDEX_SCALE;
import static sun.misc.Unsafe.ARRAY_DOUBLE_BASE_OFFSET;
import static sun.misc.Unsafe.ARRAY_DOUBLE_INDEX_SCALE;
import static sun.misc.Unsafe.ARRAY_FLOAT_BASE_OFFSET;
import static sun.misc.Unsafe.ARRAY_FLOAT_INDEX_SCALE;
import static sun.misc.Unsafe.ARRAY_INT_BASE_OFFSET;
import static sun.misc.Unsafe.ARRAY_INT_INDEX_SCALE;
import static sun.misc.Unsafe.ARRAY_LONG_BASE_OFFSET;
import static sun.misc.Unsafe.ARRAY_LONG_INDEX_SCALE;
import static sun.misc.Unsafe.ARRAY_OBJECT_BASE_OFFSET;
import static sun.misc.Unsafe.ARRAY_OBJECT_INDEX_SCALE;
import static sun.misc.Unsafe.ARRAY_SHORT_BASE_OFFSET;
import static sun.misc.Unsafe.ARRAY_SHORT_INDEX_SCALE;

public final class SizeOf {
	public static final int SIZE_OF_ATTRIBUTE_STREAM_OF_FLOAT = 24;

	public static final int SIZE_OF_ATTRIBUTE_STREAM_OF_DBL = 24;

	public static final int SIZE_OF_ATTRIBUTE_STREAM_OF_INT8 = 24;

	public static final int SIZE_OF_ATTRIBUTE_STREAM_OF_INT16 = 24;

	public static final int SIZE_OF_ATTRIBUTE_STREAM_OF_INT32 = 24;

	public static final int SIZE_OF_ATTRIBUTE_STREAM_OF_INT64 = 24;

	public static final int SIZE_OF_ENVELOPE = 32;

	public static final int SIZE_OF_ENVELOPE2D = 48;

	public static final int SIZE_OF_LINE = 56;

	public static final int SIZE_OF_MULTI_PATH = 24;

	public static final int SIZE_OF_MULTI_PATH_IMPL = 112;

	public static final int SIZE_OF_MULTI_POINT = 24;

	public static final int SIZE_OF_MULTI_POINT_IMPL = 56;

	public static final int SIZE_OF_POINT = 40;

	public static final int SIZE_OF_POLYGON = 24;

	public static final int SIZE_OF_POLYLINE = 24;

	public static final int SIZE_OF_OGC_CONCRETE_GEOMETRY_COLLECTION = 24;

	public static final int SIZE_OF_OGC_LINE_STRING = 24;

	public static final int SIZE_OF_OGC_MULTI_LINE_STRING = 24;

	public static final int SIZE_OF_OGC_MULTI_POINT = 24;

	public static final int SIZE_OF_OGC_MULTI_POLYGON = 24;

	public static final int SIZE_OF_OGC_POINT = 24;

	public static final int SIZE_OF_OGC_POLYGON = 24;
	
	public static final int SIZE_OF_MAPGEOMETRY = 24;

	public static final int SIZE_OF_RASTERIZED_GEOMETRY_2D_IMPL = 112;

	public static final int SIZE_OF_SCAN_CALLBACK_IMPL = 32;

	public static final int SIZE_OF_TRANSFORMATION_2D = 64;

	public static final int SIZE_OF_SIMPLE_RASTERIZER = 64;

	public static final int SIZE_OF_EDGE = 48;

	public static final int SIZE_OF_QUAD_TREE_IMPL = 48;

	public static final int SIZE_OF_DATA = 24;

	public static final int SIZE_OF_STRIDED_INDEX_TYPE_COLLECTION = 48;

	public static long sizeOfByteArray(int length) {
		return ARRAY_BYTE_BASE_OFFSET + (((long) ARRAY_BYTE_INDEX_SCALE) * length);
	}

	public static long sizeOfShortArray(int length) {
		return ARRAY_SHORT_BASE_OFFSET + (((long) ARRAY_SHORT_INDEX_SCALE) * length);
	}

	public static long sizeOfCharArray(int length) {
		return ARRAY_CHAR_BASE_OFFSET + (((long) ARRAY_CHAR_INDEX_SCALE) * length);
	}

	public static long sizeOfIntArray(int length) {
		return ARRAY_INT_BASE_OFFSET + (((long) ARRAY_INT_INDEX_SCALE) * length);
	}

	public static long sizeOfLongArray(int length) {
		return ARRAY_LONG_BASE_OFFSET + (((long) ARRAY_LONG_INDEX_SCALE) * length);
	}

	public static long sizeOfFloatArray(int length) {
		return ARRAY_FLOAT_BASE_OFFSET + (((long) ARRAY_FLOAT_INDEX_SCALE) * length);
	}

	public static long sizeOfDoubleArray(int length) {
		return ARRAY_DOUBLE_BASE_OFFSET + (((long) ARRAY_DOUBLE_INDEX_SCALE) * length);
	}

	public static long sizeOfObjectArray(int length)
	{
		return ARRAY_OBJECT_BASE_OFFSET + (((long) ARRAY_OBJECT_INDEX_SCALE) * length);
	}

	private SizeOf() {
	}
}
