

package com.northpool.geometry;

import java.util.Random;

import junit.framework.TestCase;

import org.junit.Test;

public class TestPoint extends TestCase {
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testPt() {
		Point pt = new Point();
		assertTrue(pt.isEmpty());
		assertTrue(Double.isNaN(pt.getX()));
		assertTrue(Double.isNaN(pt.getY()));
		assertTrue(Double.isNaN(pt.getM()));
		assertTrue(pt.getZ() == 0);
		Point pt1 = new Point();
		assertTrue(pt.equals(pt1));
		int hash1 = pt.hashCode();
		pt.setXY(10, 2);
		assertFalse(pt.isEmpty());
		assertTrue(pt.getX() == 10);
		assertTrue(pt.getY() == 2);
		assertTrue(pt.getXY().equals(new Point2D(10, 2)));
		assertTrue(pt.getXYZ().x == 10);
		assertTrue(pt.getXYZ().y == 2);
		assertTrue(pt.getXYZ().z == 0);
		assertFalse(pt.equals(pt1));
		pt.copyTo(pt1);
		assertTrue(pt.equals(pt1));
		int hash2 = pt.hashCode();
		assertFalse(hash1 == hash2);
		pt.setZ(5);
		assertFalse(pt.equals(pt1));
		pt.copyTo(pt1);
		assertTrue(pt.equals(pt1));
		assertFalse(hash1 == pt.hashCode());
		assertFalse(hash2 == pt.hashCode());
		assertTrue(pt.hasZ());
		assertTrue(pt.getZ() == 5);
		assertTrue(pt.hasAttribute(VertexDescription.Semantics.Z));
		pt.toString();
	}

	@Test
	public void testEnvelope2000() {
		Point points[] = new Point[2000];
		Random random = new Random(69);
		for (int i = 0; i < 2000; i++) {
			points[i] = new Point();
			points[i].setX(random.nextDouble() * 100);
			points[i].setY(random.nextDouble() * 100);
		}
		for (int iter = 0; iter < 2; iter++) {
			final long startTime = System.nanoTime();
			Envelope geomExtent = new Envelope();
			Envelope fullExtent = new Envelope();
			for (int i = 0; i < 2000; i++) {
				points[i].queryEnvelope(geomExtent);
				fullExtent.merge(geomExtent);
			}
			long endTime = System.nanoTime();
		}
	}

	@Test
	public void testBasic() {
		assertTrue(Geometry.getDimensionFromType(Geometry.Type.Polygon.value()) == 2);
		assertTrue(Geometry
				.getDimensionFromType(Geometry.Type.Polyline.value()) == 1);
		assertTrue(Geometry
				.getDimensionFromType(Geometry.Type.Envelope.value()) == 2);
		assertTrue(Geometry.getDimensionFromType(Geometry.Type.Line.value()) == 1);
		assertTrue(Geometry.getDimensionFromType(Geometry.Type.Point.value()) == 0);
		assertTrue(Geometry.getDimensionFromType(Geometry.Type.MultiPoint
				.value()) == 0);

		assertTrue(Geometry.isLinear(Geometry.Type.Polygon.value()));
		assertTrue(Geometry.isLinear(Geometry.Type.Polyline.value()));
		assertTrue(Geometry.isLinear(Geometry.Type.Envelope.value()));
		assertTrue(Geometry.isLinear(Geometry.Type.Line.value()));
		assertTrue(!Geometry.isLinear(Geometry.Type.Point.value()));
		assertTrue(!Geometry.isLinear(Geometry.Type.MultiPoint.value()));

		assertTrue(Geometry.isArea(Geometry.Type.Polygon.value()));
		assertTrue(!Geometry.isArea(Geometry.Type.Polyline.value()));
		assertTrue(Geometry.isArea(Geometry.Type.Envelope.value()));
		assertTrue(!Geometry.isArea(Geometry.Type.Line.value()));
		assertTrue(!Geometry.isArea(Geometry.Type.Point.value()));
		assertTrue(!Geometry.isArea(Geometry.Type.MultiPoint.value()));

		assertTrue(!Geometry.isPoint(Geometry.Type.Polygon.value()));
		assertTrue(!Geometry.isPoint(Geometry.Type.Polyline.value()));
		assertTrue(!Geometry.isPoint(Geometry.Type.Envelope.value()));
		assertTrue(!Geometry.isPoint(Geometry.Type.Line.value()));
		assertTrue(Geometry.isPoint(Geometry.Type.Point.value()));
		assertTrue(Geometry.isPoint(Geometry.Type.MultiPoint.value()));

		assertTrue(Geometry.isMultiVertex(Geometry.Type.Polygon.value()));
		assertTrue(Geometry.isMultiVertex(Geometry.Type.Polyline.value()));
		assertTrue(!Geometry.isMultiVertex(Geometry.Type.Envelope.value()));
		assertTrue(!Geometry.isMultiVertex(Geometry.Type.Line.value()));
		assertTrue(!Geometry.isMultiVertex(Geometry.Type.Point.value()));
		assertTrue(Geometry.isMultiVertex(Geometry.Type.MultiPoint.value()));

		assertTrue(Geometry.isMultiPath(Geometry.Type.Polygon.value()));
		assertTrue(Geometry.isMultiPath(Geometry.Type.Polyline.value()));
		assertTrue(!Geometry.isMultiPath(Geometry.Type.Envelope.value()));
		assertTrue(!Geometry.isMultiPath(Geometry.Type.Line.value()));
		assertTrue(!Geometry.isMultiPath(Geometry.Type.Point.value()));
		assertTrue(!Geometry.isMultiPath(Geometry.Type.MultiPoint.value()));

		assertTrue(!Geometry.isSegment(Geometry.Type.Polygon.value()));
		assertTrue(!Geometry.isSegment(Geometry.Type.Polyline.value()));
		assertTrue(!Geometry.isSegment(Geometry.Type.Envelope.value()));
		assertTrue(Geometry.isSegment(Geometry.Type.Line.value()));
		assertTrue(!Geometry.isSegment(Geometry.Type.Point.value()));
		assertTrue(!Geometry.isSegment(Geometry.Type.MultiPoint.value()));
	}

	@Test
	public void testCopy() {
		Point pt = new Point();
		Point copyPt = (Point) pt.copy();
		assertTrue(copyPt.equals(pt));

		pt.setXY(11, 13);
		copyPt = (Point) pt.copy();
		assertTrue(copyPt.equals(pt));
		assertTrue(copyPt.getXY().isEqual(new Point2D(11, 13)));
		
		assertTrue(copyPt.getXY().equals((Object)new Point2D(11, 13)));
	}

	@Test
	public void testEnvelope2D_corners() {
		Envelope2D env = new Envelope2D(0, 1, 2, 3);
		assertFalse(env.equals(null));
		assertTrue(env.equals((Object)new Envelope2D(0, 1, 2, 3)));
		
		Point2D pt2D = env.getLowerLeft();
		assertTrue(pt2D.equals(Point2D.construct(0, 1)));
		pt2D = env.getUpperLeft();
		assertTrue(pt2D.equals(Point2D.construct(0, 3)));
		pt2D = env.getUpperRight();
		assertTrue(pt2D.equals(Point2D.construct(2, 3)));
		pt2D = env.getLowerRight();
		assertTrue(pt2D.equals(Point2D.construct(2, 1)));

		{
			Point2D[] corners = new Point2D[4];
			env.queryCorners(corners);
			assertTrue(corners[0].equals(Point2D.construct(0, 1)));
			assertTrue(corners[1].equals(Point2D.construct(0, 3)));
			assertTrue(corners[2].equals(Point2D.construct(2, 3)));
			assertTrue(corners[3].equals(Point2D.construct(2, 1)));
	
			env.queryCorners(corners);
			assertTrue(corners[0].equals(env.queryCorner(0)));
			assertTrue(corners[1].equals(env.queryCorner(1)));
			assertTrue(corners[2].equals(env.queryCorner(2)));
			assertTrue(corners[3].equals(env.queryCorner(3)));
		}
		
		{
			Point2D[] corners = new Point2D[4];
			env.queryCornersReversed(corners);
			assertTrue(corners[0].equals(Point2D.construct(0, 1)));
			assertTrue(corners[1].equals(Point2D.construct(2, 1)));
			assertTrue(corners[2].equals(Point2D.construct(2, 3)));
			assertTrue(corners[3].equals(Point2D.construct(0, 3)));
			
			env.queryCornersReversed(corners);
			assertTrue(corners[0].equals(env.queryCorner(0)));
			assertTrue(corners[1].equals(env.queryCorner(3)));
			assertTrue(corners[2].equals(env.queryCorner(2)));
			assertTrue(corners[3].equals(env.queryCorner(1)));
		}
		
		assertTrue(env.getCenter().equals(Point2D.construct(1, 2)));
		
		assertFalse(env.containsExclusive(env.getUpperLeft()));
		assertTrue(env.contains(env.getUpperLeft()));
		assertTrue(env.containsExclusive(env.getCenter()));
	}
	
	@Test
	public void testReplaceNaNs() {
		Envelope env = new Envelope();
		Point pt = new Point();
		pt.setXY(1, 2);
		pt.setZ(Double.NaN);
		pt.queryEnvelope(env);
		pt.replaceNaNs(VertexDescription.Semantics.Z, 5);
		assertTrue(pt.equals(new Point(1, 2, 5)));

		assertTrue(env.hasZ());
		assertTrue(env.queryInterval(VertexDescription.Semantics.Z, 0).isEmpty());
		env.replaceNaNs(VertexDescription.Semantics.Z, 5);
		assertTrue(env.queryInterval(VertexDescription.Semantics.Z, 0).equals(new Envelope1D(5, 5)));
	}	
}
