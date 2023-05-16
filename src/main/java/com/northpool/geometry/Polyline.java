


package com.northpool.geometry;

import java.io.Serializable;

import static com.northpool.geometry.SizeOf.SIZE_OF_POLYLINE;

/**
 * A polyline is a collection of one or many paths.
 * 
 */
public class Polyline extends MultiPath implements Serializable {

	private static final long serialVersionUID = 2L;// TODO:remove as we use
													// writeReplace and
													// GeometrySerializer

	/**
	 * Creates an empty polyline.
	 */
	public Polyline() {
		m_impl = new MultiPathImpl(false);
	}

	public Polyline(VertexDescription vd) {
		m_impl = new MultiPathImpl(false, vd);
	}

	/**
	 * Creates a polyline with one line segment.
	 */
	public Polyline(Point start, Point end) {
		m_impl = new MultiPathImpl(false, start.getDescription());
		startPath(start);
		lineTo(end);
	}

	@Override
	public Geometry createInstance() {
		return new Polyline(getDescription());
	}

	@Override
	public int getDimension() {
		return 1;
	}

	@Override
	public Geometry.Type getType() {
		return Type.Polyline;
	}

	@Override
	public long estimateMemorySize() {
		return SIZE_OF_POLYLINE + m_impl.estimateMemorySize();
	}

	/**
	 * Returns TRUE when this geometry has exactly same type, properties, and
	 * coordinates as the other geometry.
	 */
	@Override
	public boolean equals(Object other) {
		if (other == null)
			return false;

		if (other == this)
			return true;

		if (other.getClass() != getClass())
			return false;

		return m_impl.equals(((Polyline) other)._getImpl());
	}

	/**
	 * Returns the hash code for the polyline.
	 */

	@Override
	public int hashCode() {
		return m_impl.hashCode();
	}

	@Override
	public void addSegment(Segment segment, boolean bStartNewPath) {
		m_impl.addSegment(segment, bStartNewPath);
	}

	public void interpolateAttributes(int from_path_index,
			int from_point_index, int to_path_index, int to_point_index) {
		m_impl.interpolateAttributes(from_path_index, from_point_index,
				to_path_index, to_point_index);
	}

	public void interpolateAttributes(int semantics, int from_path_index,
			int from_point_index, int to_path_index, int to_point_index) {
		m_impl.interpolateAttributesForSemantics(semantics, from_path_index,
				from_point_index, to_path_index, to_point_index);
	}
}
