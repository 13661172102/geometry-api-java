

package com.northpool.geometry.ogc;

public abstract class OGCMultiSurface extends OGCGeometryCollection {
	public double area() {
		return getEsriGeometry().calculateArea2D();
	}

	public OGCPoint pointOnSurface() {
		// TODO
		throw new UnsupportedOperationException();
	}
}
