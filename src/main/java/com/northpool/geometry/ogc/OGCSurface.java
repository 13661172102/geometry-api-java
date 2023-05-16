

package com.northpool.geometry.ogc;

public abstract class OGCSurface extends OGCGeometry {
	public double area() {
		return getEsriGeometry().calculateArea2D();
	}

	public OGCPoint pointOnSurface() {
		// TODO: support this (need to port OperatorLabelPoint)
		throw new UnsupportedOperationException();
	}

	public abstract OGCMultiCurve boundary();

}
