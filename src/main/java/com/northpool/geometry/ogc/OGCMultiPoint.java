

package com.northpool.geometry.ogc;

import com.northpool.geometry.Geometry;
import com.northpool.geometry.GeometryEngine;
import com.northpool.geometry.MultiPoint;
import com.northpool.geometry.Operator;
import com.northpool.geometry.OperatorExportToWkb;
import com.northpool.geometry.OperatorFactoryLocal;
import com.northpool.geometry.Point;
import com.northpool.geometry.SpatialReference;
import com.northpool.geometry.WkbExportFlags;
import com.northpool.geometry.WktExportFlags;

import java.nio.ByteBuffer;

import static com.northpool.geometry.SizeOf.SIZE_OF_OGC_MULTI_POINT;

public class OGCMultiPoint extends OGCGeometryCollection {
	public static String TYPE = "MultiPoint";
	
	public int numGeometries() {
		return multiPoint.getPointCount();
	}

	@Override
	public String asText() {
		return GeometryEngine.geometryToWkt(getEsriGeometry(),
				WktExportFlags.wktExportMultiPoint);
	}

	@Override
	public ByteBuffer asBinary() {
		OperatorExportToWkb op = (OperatorExportToWkb) OperatorFactoryLocal
				.getInstance().getOperator(Operator.Type.ExportToWkb);
		return op.execute(WkbExportFlags.wkbExportMultiPoint,
				getEsriGeometry(), null);
	}

	public OGCGeometry geometryN(int n) {
		return OGCGeometry.createFromEsriGeometry(multiPoint.getPoint(n),
				esriSR);
	}

	@Override
	public String geometryType() {
		return TYPE;
	}

	@Override
	public long estimateMemorySize()
	{
		return SIZE_OF_OGC_MULTI_POINT + (multiPoint != null ? multiPoint.estimateMemorySize() : 0);
	}

	/**
	 * 
	 * @param mp
	 *            MultiPoint instance will be referenced by this OGC class
	 */
	public OGCMultiPoint(MultiPoint mp, SpatialReference sr) {
		multiPoint = mp;
		esriSR = sr;
	}

	public OGCMultiPoint(Point startPoint, SpatialReference sr) {
		multiPoint = new MultiPoint();
		multiPoint.add((Point) startPoint);
		esriSR = sr;
	}

	public OGCMultiPoint(OGCPoint startPoint, OGCPoint endPoint) {
		multiPoint = new MultiPoint();
		multiPoint.add((Point) startPoint.getEsriGeometry());
		multiPoint.add((Point) endPoint.getEsriGeometry());
		esriSR = startPoint.esriSR;
	}

	public OGCMultiPoint(SpatialReference sr) {
		esriSR = sr;
		multiPoint = new MultiPoint();
	}

	@Override
	public OGCGeometry boundary() {
		return new OGCMultiPoint((MultiPoint) multiPoint.createInstance(),
				esriSR);// return empty multipoint
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
		return multiPoint;
	}

	@Override
	public OGCGeometry convertToMulti()
	{
		return this;
	}
	
	@Override
	public OGCGeometry reduceFromMulti() {
		int n = numGeometries();
		if (n == 0) {
			return new OGCPoint(new Point(multiPoint.getDescription()), esriSR);
		}
		
		if (n == 1) {
			return geometryN(0);
		}
		
		return this;
	}
	
	private MultiPoint multiPoint;
}
