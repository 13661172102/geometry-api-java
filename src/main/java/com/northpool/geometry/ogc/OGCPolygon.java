

package com.northpool.geometry.ogc;

import com.northpool.geometry.Geometry;
import com.northpool.geometry.GeometryEngine;
import com.northpool.geometry.Operator;
import com.northpool.geometry.OperatorExportToWkb;
import com.northpool.geometry.OperatorFactoryLocal;
import com.northpool.geometry.Polygon;
import com.northpool.geometry.Polyline;
import com.northpool.geometry.SpatialReference;
import com.northpool.geometry.WkbExportFlags;
import com.northpool.geometry.WktExportFlags;

import java.nio.ByteBuffer;

import static com.northpool.geometry.SizeOf.SIZE_OF_OGC_POLYGON;

public class OGCPolygon extends OGCSurface {
	public static String TYPE = "Polygon";
	
	public OGCPolygon(Polygon src, int exteriorRing, SpatialReference sr) {
		polygon = new Polygon();
		for (int i = exteriorRing, n = src.getPathCount(); i < n; i++) {
			if (i > exteriorRing && src.isExteriorRing(i))
				break;
			polygon.addPath(src, i, true);
		}
		esriSR = sr;
	}

	public OGCPolygon(Polygon geom, SpatialReference sr) {
		polygon = geom;
		if (geom.getExteriorRingCount() > 1)
			throw new IllegalArgumentException(
					"Polygon has to have one exterior ring. Simplify geom with OperatorSimplify.");
		esriSR = sr;
	}

	@Override
	public String asText() {
		return GeometryEngine.geometryToWkt(getEsriGeometry(),
				WktExportFlags.wktExportPolygon);
	}

	@Override
	public ByteBuffer asBinary() {
		OperatorExportToWkb op = (OperatorExportToWkb) OperatorFactoryLocal
				.getInstance().getOperator(Operator.Type.ExportToWkb);
		return op.execute(WkbExportFlags.wkbExportPolygon, getEsriGeometry(),
				null);
	}

	/**
	 * Returns the exterior ring of this Polygon.
	 * @return OGCLinearRing instance.
	 */
	public OGCLineString exteriorRing() {
		if (polygon.isEmpty())
			return new OGCLinearRing((Polygon) polygon.createInstance(), 0,
					esriSR, true);
		return new OGCLinearRing(polygon, 0, esriSR, true);
	}

	/**
	 * Returns the number of interior rings in this Polygon.
	 */
	public int numInteriorRing() {
		return polygon.getPathCount() - 1;
	}

	/**
	 * Returns the Nth interior ring for this Polygon as a LineString.
	 * @param n The 0 based index of the interior ring.
	 * @return OGCLinearRing instance.
	 */
	public OGCLineString interiorRingN(int n) {
		return new OGCLinearRing(polygon, n + 1, esriSR, true);
	}

	@Override
	public OGCMultiCurve boundary() {
		Polyline polyline = new Polyline();
		polyline.add(polygon, true); // adds reversed path
		return (OGCMultiCurve) createFromEsriGeometry(polyline,
				esriSR, true);
	}

	@Override
	public String geometryType() {
		return TYPE;
	}

	@Override
	public long estimateMemorySize()
	{
		return SIZE_OF_OGC_POLYGON + (polygon != null ? polygon.estimateMemorySize() : 0);
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
		return new OGCMultiPolygon(polygon, esriSR);
	}
	
	@Override
	public OGCGeometry reduceFromMulti() {
		return this;
	}
	
	Polygon polygon;
}
