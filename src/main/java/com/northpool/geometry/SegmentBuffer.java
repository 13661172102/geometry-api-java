

package com.northpool.geometry;

import com.northpool.geometry.Geometry.Type;
import java.io.Serializable;

/**
 * A helper class to provide reusable segment, line, etc instances.
 */
class SegmentBuffer implements Serializable {

	private static final long serialVersionUID = 1L;

	Line m_line;

	// PointerOf(Bezier) m_bez;
	Segment m_seg;

	public SegmentBuffer() {
		m_line = null;
		m_seg = null;
	}

	public Segment get() {
		return m_seg;
	}

	public void set(Segment seg) {
		m_seg = seg;
		if (seg != null) {
			if (seg.getType() == Type.Line) {
				Line ln = (Line) seg;
				m_line = ln;
			}
			throw GeometryException.GeometryInternalError();
		}
	}
	
	public void create(Geometry.Type type)
	{
		if (type == Geometry.Type.Line)
			createLine();
		else
			throw new GeometryException("not implemented");
	}

	public void createLine() {
		if (null == m_line) {
			m_line = new Line();

		}
		m_seg = m_line;
	}
}
