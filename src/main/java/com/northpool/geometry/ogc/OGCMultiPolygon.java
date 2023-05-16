

package com.northpool.geometry.ogc;

import com.northpool.geometry.GeoJsonExportFlags;
import com.northpool.geometry.Geometry;
import com.northpool.geometry.GeometryEngine;
import com.northpool.geometry.Operator;
import com.northpool.geometry.OperatorExportToGeoJson;
import com.northpool.geometry.OperatorExportToWkb;
import com.northpool.geometry.OperatorFactoryLocal;
import com.northpool.geometry.Polygon;
import com.northpool.geometry.Polyline;
import com.northpool.geometry.SpatialReference;
import com.northpool.geometry.WkbExportFlags;
import com.northpool.geometry.WktExportFlags;

import java.nio.ByteBuffer;

import static com.northpool.geometry.SizeOf.SIZE_OF_OGC_MULTI_POLYGON;

public class OGCMultiPolygon extends OGCMultiSurface {
	static public String TYPE = "MultiPolygon";
	
	public OGCMultiPolygon(Polygon src, SpatialReference sr) {
		polygon = src;
		esriSR = sr;
	}

	public OGCMultiPolygon(SpatialReference sr) {
		polygon = new Polygon();
		esriSR = sr;
	}
	
	@Override
	public String asText() {
		return GeometryEngine.geometryToWkt(getEsriGeometry(),
				WktExportFlags.wktExportMultiPolygon);
	}

	@Override
	public ByteBuffer asBinary() {
		OperatorExportToWkb op = (OperatorExportToWkb) OperatorFactoryLocal
				.getInstance().getOperator(Operator.Type.ExportToWkb);
		return op.execute(WkbExportFlags.wkbExportMultiPolygon,
				getEsriGeometry(), null);
	}
	@Override
    public String asGeoJson() {
        OperatorExportToGeoJson op = (OperatorExportToGeoJson) OperatorFactoryLocal
                .getInstance().getOperator(Operator.Type.ExportToGeoJson);
        return op.execute(GeoJsonExportFlags.geoJsonExportPreferMultiGeometry, esriSR, getEsriGeometry());
    }
	@Override
	public int numGeometries() {
		return polygon.getExteriorRingCount();
	}

	@Override
	public OGCGeometry geometryN(int n) {
		int exterior = 0;
		for (int i = 0; i < polygon.getPathCount(); i++) {
			if (polygon.isExteriorRing(i))
				exterior++;

			if (exterior == n + 1) {
				return new OGCPolygon(polygon, i, esriSR);
			}
		}

		throw new IllegalArgumentException("geometryN: n out of range");
	}

	@Override
	public String geometryType() {
		return TYPE;
	}

	@Override
	public long estimateMemorySize()
	{
		return SIZE_OF_OGC_MULTI_POLYGON + (polygon != null ? polygon.estimateMemorySize() : 0);
	}

	@Override
	public OGCGeometry boundary() {
		Polyline polyline = new Polyline();
		polyline.add(polygon, true); // adds reversed path
		return (OGCMultiCurve) OGCGeometry.createFromEsriGeometry(polyline,
				esriSR, true);
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
		return polygon;
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
			return new OGCPolygon(new Polygon(polygon.getDescription()), 0, esriSR);
		}
		
		if (n == 1) {
			return geometryN(0);
		}
		
		return this;
	}
	
	Polygon polygon;
}
