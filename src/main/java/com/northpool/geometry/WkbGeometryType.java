

package com.northpool.geometry;

interface WkbGeometryType {
	public static final int wkbPoint = 1;

	public static final int wkbPointZ = 1001;

	public static final int wkbPointM = 2001;

	public static final int wkbPointZM = 3001;

	public static final int wkbLineString = 2;

	public static final int wkbLineStringZ = 1002;

	public static final int wkbLineStringM = 2002;

	public static final int wkbLineStringZM = 3002;

	public static final int wkbPolygon = 3;

	public static final int wkbPolygonZ = 1003;

	public static final int wkbPolygonM = 2003;

	public static final int wkbPolygonZM = 3003;

	public static final int wkbMultiPoint = 4;

	public static final int wkbMultiPointZ = 1004;

	public static final int wkbMultiPointM = 2004;

	public static final int wkbMultiPointZM = 3004;

	public static final int wkbMultiLineString = 5;

	public static final int wkbMultiLineStringZ = 1005;

	public static final int wkbMultiLineStringM = 2005;

	public static final int wkbMultiLineStringZM = 3005;

	public static final int wkbMultiPolygon = 6;

	public static final int wkbMultiPolygonZ = 1006;

	public static final int wkbMultiPolygonM = 2006;

	public static final int wkbMultiPolygonZM = 3006;

	public static final int wkbGeometryCollection = 7;

	public static final int wkbGeometryCollectionZ = 1007;

	public static final int wkbGeometryCollectionM = 2007;

	public static final int wkbGeometryCollectionZM = 3007;

	public static final int wkbMultiPatch = 8;
}
