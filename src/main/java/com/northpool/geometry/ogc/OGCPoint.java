

package com.northpool.geometry.ogc;

import com.northpool.geometry.GeometryEngine;
import com.northpool.geometry.*;
import com.northpool.geometry.OperatorExportToWkb;
import com.northpool.geometry.OperatorFactoryLocal;
import com.northpool.geometry.SpatialReference;
import com.northpool.geometry.WktExportFlags;

import java.nio.ByteBuffer;

import static com.northpool.geometry.SizeOf.SIZE_OF_OGC_POINT;

public final class OGCPoint extends OGCGeometry {
	public static String TYPE = "Point";
	
	public OGCPoint(Point pt, SpatialReference sr) {
		point = pt;
		esriSR = sr;
	}

	@Override
	public String asText() {
		return GeometryEngine.geometryToWkt(getEsriGeometry(),
				WktExportFlags.wktExportPoint);
	}

	@Override
	public ByteBuffer asBinary() {
		OperatorExportToWkb op = (OperatorExportToWkb) OperatorFactoryLocal
				.getInstance().getOperator(Operator.Type.ExportToWkb);
		return op.execute(WkbExportFlags.wkbExportPoint, getEsriGeometry(),
				null);
	}

	public double X() {
		return point.getX();
	}

	public double Y() {
		return point.getY();
	}

	public double Z() {
		return point.getZ();
	}

	public double M() {
		return point.getM();
	}

	@Override
	public String geometryType() {
		return TYPE;
	}

	@Override
	public long estimateMemorySize()
	{
		return SIZE_OF_OGC_POINT + (point != null ? point.estimateMemorySize() : 0);
	}

	@Override
	public OGCGeometry boundary() {
		return new OGCMultiPoint(new MultiPoint(getEsriGeometry()
				.getDescription()), esriSR);// return empty point
	}

	@Override
	public OGCGeometry locateAlong(double mValue) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public OGCGeometry locateBetween(double mStart, double mEnd) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public Geometry getEsriGeometry() {
		return point;
	}
	
	@Override
	public OGCGeometry convertToMulti()
	{
		return new OGCMultiPoint(point, esriSR);
	}
	
	@Override
	public OGCGeometry reduceFromMulti() {
		return this;
	}

	Point point;

}
