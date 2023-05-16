

package com.northpool.geometry.ogc;

import com.northpool.geometry.Geometry;
import com.northpool.geometry.GeometryEngine;
import com.northpool.geometry.MultiPath;
import com.northpool.geometry.Operator;
import com.northpool.geometry.OperatorExportToWkb;
import com.northpool.geometry.OperatorFactoryLocal;
import com.northpool.geometry.Polyline;
import com.northpool.geometry.SpatialReference;
import com.northpool.geometry.WkbExportFlags;
import com.northpool.geometry.WktExportFlags;

import java.nio.ByteBuffer;

import static com.northpool.geometry.SizeOf.SIZE_OF_OGC_LINE_STRING;

public class OGCLineString extends OGCCurve {
	static public String TYPE = "LineString";
	
	/**
	 * The number of Points in this LineString.
	 */
	public int numPoints() {
		if (multiPath.isEmpty())
			return 0;
		int d = multiPath.isClosedPath(0) ? 1 : 0;
		return multiPath.getPointCount() + d;
	}

	@Override
	public String asText() {
		return GeometryEngine.geometryToWkt(getEsriGeometry(),
				WktExportFlags.wktExportLineString);
	}

	@Override
	public ByteBuffer asBinary() {
		OperatorExportToWkb op = (OperatorExportToWkb) OperatorFactoryLocal
				.getInstance().getOperator(Operator.Type.ExportToWkb);
		return op.execute(WkbExportFlags.wkbExportLineString,
				getEsriGeometry(), null);
	}

	/**
	 * Returns the specified Point N in this LineString.
	 * @param n The 0 based index of the Point.
	 */
	public OGCPoint pointN(int n) {
		int nn;
		if (multiPath.isClosedPath(0) && n == multiPath.getPathSize(0)) {
			nn = multiPath.getPathStart(0);
		} else
			nn = n + multiPath.getPathStart(0);

		return (OGCPoint) createFromEsriGeometry(
				multiPath.getPoint(nn), esriSR);
	}

	@Override
	public boolean isClosed() {
		if (isEmpty())
			return false;

		return multiPath.isClosedPathInXYPlane(0);
	}

	public OGCLineString(MultiPath mp, int pathIndex, SpatialReference sr) {
		multiPath = new Polyline();
		if (!mp.isEmpty())
			multiPath.addPath(mp, pathIndex, true);
		esriSR = sr;
	}

	public OGCLineString(MultiPath mp, int pathIndex, SpatialReference sr,
			boolean reversed) {
		multiPath = new Polyline();
		if (!mp.isEmpty())
			multiPath.addPath(mp, pathIndex, !reversed);
		esriSR = sr;
	}

	@Override
	public double length() {
		return multiPath.calculateLength2D();
	}

	@Override
	public OGCPoint startPoint() {
		return pointN(0);
	}

	@Override
	public OGCPoint endPoint() {
		return pointN(numPoints() - 1);
	}

	@Override
	public String geometryType() {
		return TYPE;
	}

	@Override
	public long estimateMemorySize()
	{
		return SIZE_OF_OGC_LINE_STRING + (multiPath != null ? multiPath.estimateMemorySize() : 0);
	}

	@Override
	public OGCGeometry locateAlong(double mValue) {
		throw new UnsupportedOperationException();
	}

	@Override
	public OGCGeometry locateBetween(double mStart, double mEnd) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Geometry getEsriGeometry() {
		return multiPath;
	}

	@Override
	public OGCGeometry convertToMulti()
	{
		return new OGCMultiLineString((Polyline)multiPath, esriSR);
	}
	
	@Override
	public OGCGeometry reduceFromMulti() {
		return this;
	}
	
	MultiPath multiPath;
}
