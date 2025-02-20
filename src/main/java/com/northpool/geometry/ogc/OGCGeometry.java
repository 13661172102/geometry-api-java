

package com.northpool.geometry.ogc;

import com.northpool.geometry.Envelope;
import com.northpool.geometry.Envelope1D;
import com.northpool.geometry.Geometry;
import com.northpool.geometry.GeometryCursor;
import com.northpool.geometry.GeometryCursorAppend;
import com.northpool.geometry.GeometryEngine;
import com.northpool.geometry.JsonParserReader;
import com.northpool.geometry.MapGeometry;
import com.northpool.geometry.MapOGCStructure;
import com.northpool.geometry.MultiPoint;
import com.northpool.geometry.NumberUtils;
import com.northpool.geometry.OGCStructure;
import com.northpool.geometry.Operator;
import com.northpool.geometry.OperatorBuffer;
import com.northpool.geometry.OperatorCentroid2D;
import com.northpool.geometry.OperatorConvexHull;
import com.northpool.geometry.OperatorExportToGeoJson;
import com.northpool.geometry.OperatorExportToWkb;
import com.northpool.geometry.OperatorFactoryLocal;
import com.northpool.geometry.OperatorImportFromESRIShape;
import com.northpool.geometry.OperatorImportFromGeoJson;
import com.northpool.geometry.OperatorImportFromWkb;
import com.northpool.geometry.OperatorImportFromWkt;
import com.northpool.geometry.OperatorIntersection;
import com.northpool.geometry.OperatorSimplify;
import com.northpool.geometry.OperatorSimplifyOGC;
import com.northpool.geometry.OperatorUnion;
import com.northpool.geometry.Point;
import com.northpool.geometry.Point2D;
import com.northpool.geometry.Polygon;
import com.northpool.geometry.Polyline;
import com.northpool.geometry.SimpleGeometryCursor;
import com.northpool.geometry.SpatialReference;
import com.northpool.geometry.VertexDescription;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * OGC Simple Feature Access specification v.1.2.1
 * 
 */
public abstract class OGCGeometry {
	public int dimension() {
		return getEsriGeometry().getDimension();
	}

	public int coordinateDimension() {
		int d = 2;
		if (getEsriGeometry().getDescription().hasAttribute(
				VertexDescription.Semantics.M))
			d++;
		if (getEsriGeometry().getDescription().hasAttribute(
				VertexDescription.Semantics.Z))
			d++;

		return d;
	}

	abstract public String geometryType();

	/**
	 * Returns an estimate of this object size in bytes.
	 * <p>
	 * This estimate doesn't include the size of the {@link SpatialReference} object
	 * because instances of {@link SpatialReference} are expected to be shared among
	 * geometry objects.
	 *
	 * @return Returns an estimate of this object size in bytes.
	 */
	public abstract long estimateMemorySize();

	public int SRID() {
		if (esriSR == null)
			return 0;

		return esriSR.getID();
	}

	public OGCGeometry envelope() {
		Envelope env = new Envelope();
		getEsriGeometry().queryEnvelope(env);
		Polygon polygon = new Polygon();
		polygon.addEnvelope(env, false);
		return new OGCPolygon(polygon, esriSR);
	}

	public String asText() {
		return GeometryEngine.geometryToWkt(getEsriGeometry(), 0);
	}

	public ByteBuffer asBinary() {
		OperatorExportToWkb op = (OperatorExportToWkb) OperatorFactoryLocal
				.getInstance().getOperator(Operator.Type.ExportToWkb);
		return op.execute(0, getEsriGeometry(), null);
	}

	public String asGeoJson() {
		OperatorExportToGeoJson op = (OperatorExportToGeoJson) OperatorFactoryLocal
				.getInstance().getOperator(Operator.Type.ExportToGeoJson);
		return op.execute(esriSR, getEsriGeometry());
	}

	String asGeoJsonImpl(int export_flags) {
		OperatorExportToGeoJson op = (OperatorExportToGeoJson) OperatorFactoryLocal.getInstance().getOperator(Operator.Type.ExportToGeoJson);
		return op.execute(export_flags, esriSR, getEsriGeometry());
	}
	
	/**
	 * 
	 * @return Convert to REST JSON.
	 */
	public String asJson() {
		return GeometryEngine.geometryToJson(esriSR, getEsriGeometry());
	}

	
	public boolean isEmpty() {
		return getEsriGeometry().isEmpty();
	}

	public double MinZ() {
		Envelope1D e = getEsriGeometry().queryInterval(
				VertexDescription.Semantics.Z, 0);
		return e.vmin;
	}

	public double MaxZ() {
		Envelope1D e = getEsriGeometry().queryInterval(
				VertexDescription.Semantics.Z, 0);
		return e.vmax;
	}

	public double MinMeasure() {
		Envelope1D e = getEsriGeometry().queryInterval(
				VertexDescription.Semantics.M, 0);
		return e.vmin;
	}

	public double MaxMeasure() {
		Envelope1D e = getEsriGeometry().queryInterval(
				VertexDescription.Semantics.M, 0);
		return e.vmax;
	}

	/**
	 * Returns true if this geometric object has no anomalous geometric points,
	 * such as self intersection or self tangency. See the
	 * "Simple feature access - Part 1" document (OGC 06-103r4) for meaning of
	 * "simple" for each geometry type.
	 * 
	 * The method has O(n log n) complexity when the input geometry is simple.
	 * For non-simple geometries, it terminates immediately when the first issue
	 * is encountered.
	 * 
	 * @return True if geometry is simple and false otherwise.
	 * 
	 * Note: If isSimple is true, then isSimpleRelaxed is true too. 
	 */
	public boolean isSimple() {
		return OperatorSimplifyOGC.local().isSimpleOGC(getEsriGeometry(),
				esriSR, true, null, null);
	}

	/**
	 * Extension method - checks if geometry is simple for Geodatabase.
	 * 
	 * @return Returns true if geometry is simple, false otherwise.
	 * 
	 * Note: If isSimpleRelaxed is true, then isSimple is either true or false. Geodatabase has more relaxed requirements for simple geometries than OGC.
	 */
	public boolean isSimpleRelaxed() {
		OperatorSimplify op = (OperatorSimplify) OperatorFactoryLocal
				.getInstance().getOperator(Operator.Type.Simplify);
		return op.isSimpleAsFeature(getEsriGeometry(), esriSR, true, null, null);
	}

	@Deprecated
	/**
	 * Use makeSimpleRelaxed instead.
	 */
	public OGCGeometry MakeSimpleRelaxed(boolean forceProcessing) {
		return makeSimpleRelaxed(forceProcessing);
	}
	/**
	 * Makes a simple geometry for Geodatabase.
	 * 
	 * @return Returns simplified geometry.
	 * 
	 * Note: isSimpleRelaxed should return true after this operation.
	 */
	public OGCGeometry makeSimpleRelaxed(boolean forceProcessing) {
		OperatorSimplify op = (OperatorSimplify) OperatorFactoryLocal
				.getInstance().getOperator(Operator.Type.Simplify);
		return OGCGeometry.createFromEsriGeometry(
				op.execute(getEsriGeometry(), esriSR, forceProcessing, null),
				esriSR);
	}
	
	/**
	 * Resolves topological issues in this geometry and makes it Simple according to OGC specification.
	 * 
	 * @return Returns simplified geometry.
	 * 
	 * Note: isSimple and isSimpleRelaxed should return true after this operation. 
	 */
	public OGCGeometry makeSimple() {
		return simplifyBunch_(getEsriGeometryCursor());
	}

	public boolean is3D() {
		return getEsriGeometry().getDescription().hasAttribute(
				VertexDescription.Semantics.Z);
	}

	public boolean isMeasured() {
		return getEsriGeometry().getDescription().hasAttribute(
				VertexDescription.Semantics.M);
	}

	abstract public OGCGeometry boundary();

	/**
	 * OGC equals. Performs topological comparison with tolerance.
	 * This is different from equals(Object), that uses exact comparison.
	 */
	public boolean Equals(OGCGeometry another) {
		if (this == another)
			return !isEmpty();
		
		if (another == null)
			return false;
		
		if (another.geometryType() == OGCConcreteGeometryCollection.TYPE) {
			return another.Equals(this);
		}
		
		Geometry geom1 = getEsriGeometry();
		Geometry geom2 = another.getEsriGeometry();
		return GeometryEngine.equals(geom1, geom2,
				getEsriSpatialReference());
	}

	@Deprecated
	public boolean equals(OGCGeometry another) {
		return Equals(another);
	}
	
	public boolean disjoint(OGCGeometry another) {
		if (another.geometryType() == OGCConcreteGeometryCollection.TYPE) {
			return another.disjoint(this);
		}
		
		Geometry geom1 = getEsriGeometry();
		Geometry geom2 = another.getEsriGeometry();
		return GeometryEngine.disjoint(geom1, geom2,
				getEsriSpatialReference());
	}

	public boolean intersects(OGCGeometry another) {
		return !disjoint(another);
	}

	public boolean touches(OGCGeometry another) {
		if (another.geometryType() == OGCConcreteGeometryCollection.TYPE) {
			//TODO
			throw new UnsupportedOperationException();
		}
		
		Geometry geom1 = getEsriGeometry();
		Geometry geom2 = another.getEsriGeometry();
		return GeometryEngine.touches(geom1, geom2,
				getEsriSpatialReference());
	}

	public boolean crosses(OGCGeometry another) {
		if (another.geometryType() == OGCConcreteGeometryCollection.TYPE) {
			//TODO
			throw new UnsupportedOperationException();
		}
		
		Geometry geom1 = getEsriGeometry();
		Geometry geom2 = another.getEsriGeometry();
		return GeometryEngine.crosses(geom1, geom2,
				getEsriSpatialReference());
	}

	public boolean within(OGCGeometry another) {
		return another.contains(this);
	}

	public boolean contains(OGCGeometry another) {
		if (another.geometryType() == OGCConcreteGeometryCollection.TYPE) {
			return new OGCConcreteGeometryCollection(this, esriSR).contains(another);
		}
		
		Geometry geom1 = getEsriGeometry();
		Geometry geom2 = another.getEsriGeometry();
		return GeometryEngine.contains(geom1, geom2,
				getEsriSpatialReference());
	}

	public boolean overlaps(OGCGeometry another) {
		if (another.geometryType() == OGCConcreteGeometryCollection.TYPE) {
			// TODO
			throw new UnsupportedOperationException();
		}
		
		Geometry geom1 = getEsriGeometry();
		Geometry geom2 = another.getEsriGeometry();
		return GeometryEngine.overlaps(geom1, geom2,
				getEsriSpatialReference());
	}

	public boolean relate(OGCGeometry another, String matrix) {
		if (another.geometryType() == OGCConcreteGeometryCollection.TYPE) {
			//TODO
			throw new UnsupportedOperationException();
		}
		
		Geometry geom1 = getEsriGeometry();
		Geometry geom2 = another.getEsriGeometry();
		return GeometryEngine.relate(geom1, geom2,
				getEsriSpatialReference(), matrix);
	}

	abstract public OGCGeometry locateAlong(double mValue);

	abstract public OGCGeometry locateBetween(double mStart, double mEnd);

	// analysis
	public double distance(OGCGeometry another) {
		if (this == another) {
			return isEmpty() ? Double.NaN : 0;
		}
		
		if (another.geometryType() == OGCConcreteGeometryCollection.TYPE) {
			return another.distance(this);
		}

		Geometry geom1 = getEsriGeometry();
		Geometry geom2 = another.getEsriGeometry();
		return GeometryEngine.distance(geom1, geom2,
				getEsriSpatialReference());
	}

	// This method firstly groups geometries by dimension (points, lines,
	// areas),
	// then simplifies each group such that each group is reduced to a single
	// geometry.
	// As a result there are at most three geometries, each geometry is Simple.
	// Afterwards
	// it produces a single OGCGeometry.
	private OGCGeometry simplifyBunch_(GeometryCursor gc) {
		// Combines geometries into multipoint, polyline, and polygon types,
		// simplifying them and unioning them,
		// then produces OGCGeometry from the result.
		// Can produce OGCConcreteGoemetryCollection
		MultiPoint dstMultiPoint = null;
		ArrayList<Geometry> dstPolylines = new ArrayList<Geometry>();
		ArrayList<Geometry> dstPolygons = new ArrayList<Geometry>();
		for (Geometry g = gc.next(); g != null; g = gc
				.next()) {
			switch (g.getType()) {
			case Point:
				if (dstMultiPoint == null)
					dstMultiPoint = new MultiPoint();
				dstMultiPoint.add((Point) g);
				break;
			case MultiPoint:
				if (dstMultiPoint == null)
					dstMultiPoint = new MultiPoint();
				dstMultiPoint.add((MultiPoint) g, 0, -1);
				break;
			case Polyline:
				dstPolylines.add((Polyline) g.copy());
				break;
			case Polygon:
				dstPolygons.add((Polygon) g.copy());
				break;
			default:
				throw new UnsupportedOperationException();
			}
		}

		ArrayList<Geometry> result = new ArrayList<Geometry>(3);
		if (dstMultiPoint != null) {
			Geometry resMP = OperatorSimplifyOGC.local().execute(dstMultiPoint,
					esriSR, true, null);
			result.add(resMP);
		}

		if (dstPolylines.size() > 0) {
			if (dstPolylines.size() == 1) {
				Geometry resMP = OperatorSimplifyOGC.local().execute(
						dstPolylines.get(0), esriSR, true, null);
				result.add(resMP);
			} else {
				GeometryCursor res = OperatorUnion.local().execute(
						new SimpleGeometryCursor(dstPolylines), esriSR, null);
				Geometry resPolyline = res.next();
				Geometry resMP = OperatorSimplifyOGC.local().execute(
						resPolyline, esriSR, true, null);
				result.add(resMP);
			}
		}

		if (dstPolygons.size() > 0) {
			if (dstPolygons.size() == 1) {
				Geometry resMP = OperatorSimplifyOGC.local().execute(
						dstPolygons.get(0), esriSR, true, null);
				result.add(resMP);
			} else {
				GeometryCursor res = OperatorUnion.local().execute(
						new SimpleGeometryCursor(dstPolygons), esriSR, null);
				Geometry resPolygon = res.next();
				Geometry resMP = OperatorSimplifyOGC.local().execute(
						resPolygon, esriSR, true, null);
				result.add(resMP);
			}
		}

		return OGCGeometry.createFromEsriCursor(
				new SimpleGeometryCursor(result), esriSR);
	}

	public OGCGeometry buffer(double distance) {
		OperatorBuffer op = (OperatorBuffer) OperatorFactoryLocal.getInstance()
				.getOperator(Operator.Type.Buffer);
		if (distance == 0) {// when distance is 0, return self (maybe we should
							// create a copy instead).
			return this;
		}

		double d[] = { distance };
		GeometryCursor cursor = op.execute(
				getEsriGeometryCursor(), getEsriSpatialReference(), d, true,
				null);
		return OGCGeometry.createFromEsriGeometry(cursor.next(), esriSR);
	}

	public OGCGeometry centroid() {
		OperatorCentroid2D op = (OperatorCentroid2D) OperatorFactoryLocal.getInstance()
				.getOperator(Operator.Type.Centroid2D);

		Point2D centroid = op.execute(getEsriGeometry(), null);
		if (centroid == null) {
			return OGCGeometry.createFromEsriGeometry(new Point(), esriSR);
		}
		return OGCGeometry.createFromEsriGeometry(new Point(centroid), esriSR);
	}

	public OGCGeometry convexHull() {
		GeometryCursor cursor = OperatorConvexHull.local().execute(
				getEsriGeometryCursor(), false, null);
		return OGCGeometry.createFromEsriCursor(cursor, esriSR);
	}

	public OGCGeometry intersection(OGCGeometry another) {
		if (another.geometryType() == OGCConcreteGeometryCollection.TYPE) {
			return (new OGCConcreteGeometryCollection(this, esriSR)).intersection(another);
		}

		OperatorIntersection op = (OperatorIntersection) OperatorFactoryLocal
				.getInstance().getOperator(Operator.Type.Intersection);
		GeometryCursor cursor = op.execute(
				getEsriGeometryCursor(), another.getEsriGeometryCursor(),
				getEsriSpatialReference(), null, 7);
		return OGCGeometry.createFromEsriCursor(cursor, esriSR, true);
	}

	public OGCGeometry union(OGCGeometry another) {
		String thisType = geometryType();
		String anotherType = another.geometryType();
		if (thisType != anotherType || thisType == OGCConcreteGeometryCollection.TYPE) {
			//heterogeneous union.
			//We make a geometry collection, then process to union parts and remove overlaps.
			ArrayList<OGCGeometry> geoms = new ArrayList<OGCGeometry>();
			geoms.add(this);
			geoms.add(another);
			OGCConcreteGeometryCollection geomCol = new OGCConcreteGeometryCollection(geoms, esriSR);
			return geomCol.flattenAndRemoveOverlaps().reduceFromMulti();
		}
		
		OperatorUnion op = (OperatorUnion) OperatorFactoryLocal.getInstance()
				.getOperator(Operator.Type.Union);
		GeometryCursorAppend ap = new GeometryCursorAppend(
				getEsriGeometryCursor(), another.getEsriGeometryCursor());
		GeometryCursor cursor = op.execute(ap,
				getEsriSpatialReference(), null);
		return OGCGeometry.createFromEsriCursor(cursor, esriSR);
	}

	public OGCGeometry difference(OGCGeometry another) {
		if (another.geometryType() == OGCConcreteGeometryCollection.TYPE) {
			return (new OGCConcreteGeometryCollection(this, esriSR)).difference(another);
		}
		
		Geometry geom1 = getEsriGeometry();
		Geometry geom2 = another.getEsriGeometry();
		return createFromEsriGeometry(
				GeometryEngine.difference(geom1, geom2,
						getEsriSpatialReference()), esriSR);
	}

	public OGCGeometry symDifference(OGCGeometry another) {
		if (another.geometryType() == OGCConcreteGeometryCollection.TYPE) {
			// TODO
			throw new UnsupportedOperationException();
		}

		Geometry geom1 = getEsriGeometry();
		Geometry geom2 = another.getEsriGeometry();
		return createFromEsriGeometry(
				GeometryEngine.symmetricDifference(
						geom1, geom2, getEsriSpatialReference()), esriSR);
	}

	public abstract Geometry getEsriGeometry();

	public GeometryCursor getEsriGeometryCursor() {
		return new SimpleGeometryCursor(getEsriGeometry());
	}

	public SpatialReference getEsriSpatialReference() {
		return esriSR;
	}

	/**
	 * Create an OGCGeometry instance from the GeometryCursor.
	 * 
	 * @param gc
	 * @param sr
	 * @return Geometry instance created from the geometry cursor.
	 */
	public static OGCGeometry createFromEsriCursor(GeometryCursor gc,
			SpatialReference sr) {
		return createFromEsriCursor(gc, sr, false);
	}

	public static OGCGeometry createFromEsriCursor(GeometryCursor gc,
			SpatialReference sr, boolean skipEmpty) {
		ArrayList<OGCGeometry> geoms = new ArrayList<OGCGeometry>(10);
		Geometry emptyGeom = null;
		for (Geometry g = gc.next(); g != null; g = gc.next()) {
			emptyGeom = g;
			if (!skipEmpty || !g.isEmpty())
				geoms.add(createFromEsriGeometry(g, sr));
		}

		if (geoms.size() == 1) {
			return geoms.get(0);
		} else if (geoms.size() == 0)
			return createFromEsriGeometry(emptyGeom, sr);
		else
			return new OGCConcreteGeometryCollection(geoms, sr);
	}

	public static OGCGeometry fromText(String text) {
		OperatorImportFromWkt op = (OperatorImportFromWkt) OperatorFactoryLocal
				.getInstance().getOperator(Operator.Type.ImportFromWkt);
		OGCStructure ogcStructure = op.executeOGC(0, text, null);
		return OGCGeometry.createFromOGCStructure(ogcStructure,
				SpatialReference.create(4326));
	}

	public static OGCGeometry fromBinary(ByteBuffer binary) {
		OperatorImportFromWkb op = (OperatorImportFromWkb) OperatorFactoryLocal
				.getInstance().getOperator(Operator.Type.ImportFromWkb);
		OGCStructure ogcStructure = op.executeOGC(0, binary, null);
		return OGCGeometry.createFromOGCStructure(ogcStructure,
				SpatialReference.create(4326));
	}

	public static OGCGeometry fromEsriShape(ByteBuffer buffer) {
		OperatorImportFromESRIShape op = (OperatorImportFromESRIShape) OperatorFactoryLocal
				.getInstance().getOperator(Operator.Type.ImportFromESRIShape);
		Geometry g = op.execute(0, Geometry.Type.Unknown, buffer);
		return OGCGeometry.createFromEsriGeometry(g,
				SpatialReference.create(4326));
	}

	public static OGCGeometry fromJson(String string) {
		MapGeometry mapGeom = GeometryEngine.jsonToGeometry(JsonParserReader.createFromString(string));
		return OGCGeometry.createFromEsriGeometry(mapGeom.getGeometry(),
				mapGeom.getSpatialReference());
	}

	public static OGCGeometry fromGeoJson(String string) {
		OperatorImportFromGeoJson op = (OperatorImportFromGeoJson) OperatorFactoryLocal
				.getInstance().getOperator(Operator.Type.ImportFromGeoJson);
		MapOGCStructure mapOGCStructure = op.executeOGC(0, string, null);
		return OGCGeometry.createFromOGCStructure(
				mapOGCStructure.m_ogcStructure,
				mapOGCStructure.m_spatialReference);
	}

	public static OGCGeometry createFromEsriGeometry(Geometry geom,
			SpatialReference sr) {
		return createFromEsriGeometry(geom, sr, false);
	}

	public static OGCGeometry createFromEsriGeometry(Geometry geom,
			SpatialReference sr, boolean multiType) {
		if (geom == null)
			return null;
		Geometry.Type t = geom.getType();
		if (t == Geometry.Type.Polygon) {
			if (!multiType && ((Polygon) geom).getExteriorRingCount() == 1)
				return new OGCPolygon((Polygon) geom, sr);
			else
				return new OGCMultiPolygon((Polygon) geom, sr);
		}
		if (t == Geometry.Type.Polyline) {
			if (!multiType && ((Polyline) geom).getPathCount() == 1)
				return new OGCLineString((Polyline) geom, 0, sr);
			else
				return new OGCMultiLineString((Polyline) geom, sr);
		}
		if (t == Geometry.Type.MultiPoint) {
			if (!multiType && ((MultiPoint) geom).getPointCount() <= 1) {
				if (geom.isEmpty())
					return new OGCPoint(new Point(), sr);
				else
					return new OGCPoint(((MultiPoint) geom).getPoint(0), sr);
			} else
				return new OGCMultiPoint((MultiPoint) geom, sr);
		}
		if (t == Geometry.Type.Point) {
			if (!multiType) {
				return new OGCPoint((Point) geom, sr);
			} else {
				return new OGCMultiPoint((Point) geom, sr);
			}
		}
		if (t == Geometry.Type.Envelope) {
			Polygon p = new Polygon();
			p.addEnvelope((Envelope) geom, false);
			return createFromEsriGeometry(p, sr, multiType);
		}

		throw new UnsupportedOperationException();
	}

	public static OGCGeometry createFromOGCStructure(OGCStructure ogcStructure,
			SpatialReference sr) {
		ArrayList<OGCConcreteGeometryCollection> collectionStack = new ArrayList<OGCConcreteGeometryCollection>(
				0);
		ArrayList<OGCStructure> structureStack = new ArrayList<OGCStructure>(0);
		ArrayList<Integer> indices = new ArrayList<Integer>(0);

		OGCGeometry[] geometries = new OGCGeometry[1];
		OGCConcreteGeometryCollection root = new OGCConcreteGeometryCollection(
				Arrays.asList(geometries), sr);

		structureStack.add(ogcStructure);
		collectionStack.add(root);
		indices.add(0);

		while (!structureStack.isEmpty()) {
			OGCStructure lastStructure = structureStack.get(structureStack
					.size() - 1);
			if (indices.get(indices.size() - 1) == lastStructure.m_structures
					.size()) {
				structureStack.remove(structureStack.size() - 1);
				collectionStack.remove(collectionStack.size() - 1);
				indices.remove(indices.size() - 1);
				continue;
			}

			OGCConcreteGeometryCollection lastCollection = collectionStack
					.get(collectionStack.size() - 1);
			OGCGeometry g;
			int i = indices.get(indices.size() - 1);

			int type = lastStructure.m_structures.get(i).m_type;

			switch (type) {
			case 1:
				g = new OGCPoint(
						(Point) lastStructure.m_structures.get(i).m_geometry,
						sr);
				lastCollection.geometries.set(i, g);
				indices.set(indices.size() - 1, i + 1);
				break;
			case 2:
				g = new OGCLineString(
						(Polyline) lastStructure.m_structures.get(i).m_geometry,
						0, sr);
				lastCollection.geometries.set(i, g);
				indices.set(indices.size() - 1, i + 1);
				break;
			case 3:
				g = new OGCPolygon(
						(Polygon) lastStructure.m_structures.get(i).m_geometry,
						0, sr);
				lastCollection.geometries.set(i, g);
				indices.set(indices.size() - 1, i + 1);
				break;
			case 4:
				g = new OGCMultiPoint(
						(MultiPoint) lastStructure.m_structures.get(i).m_geometry,
						sr);
				lastCollection.geometries.set(i, g);
				indices.set(indices.size() - 1, i + 1);
				break;
			case 5:
				g = new OGCMultiLineString(
						(Polyline) lastStructure.m_structures.get(i).m_geometry,
						sr);
				lastCollection.geometries.set(i, g);
				indices.set(indices.size() - 1, i + 1);
				break;
			case 6:
				g = new OGCMultiPolygon(
						(Polygon) lastStructure.m_structures.get(i).m_geometry,
						sr);
				lastCollection.geometries.set(i, g);
				indices.set(indices.size() - 1, i + 1);
				break;
			case 7:
				geometries = new OGCGeometry[lastStructure.m_structures.get(i).m_structures
						.size()];
				g = new OGCConcreteGeometryCollection(
						Arrays.asList(geometries), sr);
				lastCollection.geometries.set(i, g);
				indices.set(indices.size() - 1, i + 1);
				structureStack.add(lastStructure.m_structures.get(i));
				collectionStack.add((OGCConcreteGeometryCollection) g);
				indices.add(0);
				break;
			default:
				throw new UnsupportedOperationException();
			}
		}

		return root.geometries.get(0);
	}

	protected boolean isConcreteGeometryCollection() {
		return false;
	}

	/**
	 * SpatialReference of the Geometry.
	 */
	public SpatialReference esriSR;

	public void setSpatialReference(SpatialReference esriSR_) {
		esriSR = esriSR_;
	}

	/**
	 * Converts this Geometry to the OGCMulti* if it is not OGCMulti* or
	 * OGCGeometryCollection already.
	 * 
	 * @return OGCMulti* or OGCGeometryCollection instance.
	 */
	public abstract OGCGeometry convertToMulti();

	/**
	 * For the geometry collection types, when it has 1 or 0 elements, converts a MultiPolygon to Polygon, 
	 * MultiPoint to Point, MultiLineString to a LineString, and
	 * OGCConcretGeometryCollection to the reduced element it contains.
	 * 
	 * If OGCConcretGeometryCollection is empty, returns self.
	 * 
	 * @return A reduced geometry or this.
	 */
	public abstract OGCGeometry reduceFromMulti();
	
	@Override
	public String toString() {
		String snippet = asText();
		if (snippet.length() > 200) {
			snippet = snippet.substring(0, 197) + "...";
		}
		return String
				.format("%s: %s", this.getClass().getSimpleName(), snippet);
	}
	
	@Override
	public boolean equals(Object other)	{
		if (other == null)
			return false;

		if (other == this)
			return true;

		if (other.getClass() != getClass())
			return false;
		
		OGCGeometry another = (OGCGeometry)other;
		Geometry geom1 = getEsriGeometry();
		Geometry geom2 = another.getEsriGeometry();
		
		if (geom1 == null) {
			if (geom2 != null)
				return false;
		}
		else if (!geom1.equals(geom2)) {
			return false;
		}
		
		if (esriSR == another.esriSR) {
			return true;
		}
			
		if (esriSR != null && another.esriSR != null) {
			return esriSR.equals(another.esriSR);
		}
			
		return false;
	}
	
	@Override
	public int hashCode() {
		int hash = 1;
		Geometry geom1 = getEsriGeometry();
		if (geom1 != null)
			hash = geom1.hashCode();
		
		if (esriSR != null)
			hash = NumberUtils.hashCombine(hash, esriSR.hashCode());
		
		return hash;
	}
}
