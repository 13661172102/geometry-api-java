

package com.northpool.geometry.ogc;

import com.northpool.geometry.MultiPoint;

public abstract class OGCCurve extends OGCGeometry {
	public abstract double length();

	public abstract OGCPoint startPoint();

	public abstract OGCPoint endPoint();

	public abstract boolean isClosed();

	public boolean isRing() {
		return isSimple() && isClosed();
	}

	@Override
	public OGCGeometry boundary() {
		if (isEmpty())
			return new OGCMultiPoint(this.getEsriSpatialReference());
		
		if (isClosed())
			return new OGCMultiPoint(new MultiPoint(getEsriGeometry()
					.getDescription()), esriSR);// return empty multipoint;
		else
			return new OGCMultiPoint(startPoint(), endPoint());
	}
}
