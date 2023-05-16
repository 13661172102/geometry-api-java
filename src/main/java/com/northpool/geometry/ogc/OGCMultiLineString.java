

package com.northpool.geometry.ogc;

import com.northpool.geometry.GeoJsonExportFlags;
import com.northpool.geometry.Geometry;
import com.northpool.geometry.GeometryEngine;
import com.northpool.geometry.Operator;
import com.northpool.geometry.OperatorBoundary;
import com.northpool.geometry.OperatorExportToGeoJson;
import com.northpool.geometry.OperatorExportToWkb;
import com.northpool.geometry.OperatorFactoryLocal;
import com.northpool.geometry.Polyline;
import com.northpool.geometry.SpatialReference;
import com.northpool.geometry.WkbExportFlags;
import com.northpool.geometry.WktExportFlags;

import java.nio.ByteBuffer;

import static com.northpool.geometry.SizeOf.SIZE_OF_OGC_MULTI_LINE_STRING;

public class OGCMultiLineString extends OGCMultiCurve {
	static public String TYPE = "MultiLineString";
	
	public OGCMultiLineString(Polyline poly, SpatialReference sr) {
		polyline = poly;
		esriSR = sr;
	}

	public OGCMultiLineString(SpatialReference sr) {
		polyline = new Polyline();
		esriSR = sr;
	}
	
	@Override
	public String asText() {
		return GeometryEngine.geometryToWkt(getEsriGeometry(),
				WktExportFlags.wktExportMultiLineString);
	}

	@Override
	public String asGeoJson() {
		OperatorExportToGeoJson op = (OperatorExportToGeoJson) OperatorFactoryLocal.getInstance()
				.getOperator(Operator.Type.ExportToGeoJson);
		return op.execute(GeoJsonExportFlags.geoJsonExportPreferMultiGeometry, esriSR, getEsriGeometry());
	}
	
	@Override
	public ByteBuffer asBinary() {
		OperatorExportToWkb op = (OperatorExportToWkb) OperatorFactoryLocal
				.getInstance().getOperator(Operator.Type.ExportToWkb);
		return op.execute(WkbExportFlags.wkbExportMultiLineString,
				getEsriGeometry(), null);
	}

	@Override
	public OGCGeometry geometryN(int n) {
		OGCLineString ls = new OGCLineString(polyline, n, esriSR);
		return ls;
	}

	@Override
	public String geometryType() {
		return TYPE;
	}

	@Override
	public long estimateMemorySize()
	{
		return SIZE_OF_OGC_MULTI_LINE_STRING + (polyline != null ? polyline.estimateMemorySize() : 0);
	}

	@Override
	public OGCGeometry boundary() {
		OperatorBoundary op = (OperatorBoundary) OperatorFactoryLocal
				.getInstance().getOperator(Operator.Type.Boundary);
		Geometry g = op.execute(polyline, null);
		return OGCGeometry.createFromEsriGeometry(g, esriSR, true);
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
		return polyline;
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
			return new OGCLineString(new Polyline(polyline.getDescription()), 0, esriSR);
		}
		
		if (n == 1) {
			return geometryN(0);
		}
		
		return this;
	}
	
	Polyline polyline;
}
