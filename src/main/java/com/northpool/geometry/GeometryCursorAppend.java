
package com.northpool.geometry;

public class GeometryCursorAppend extends GeometryCursor {

	private GeometryCursor m_cur1;
	private GeometryCursor m_cur2;
	private GeometryCursor m_cur;

	public GeometryCursorAppend(GeometryCursor cur1, GeometryCursor cur2) {
		m_cur1 = cur1;
		m_cur2 = cur2;
		m_cur = m_cur1;
	}

	@Override
	public Geometry next() {
		Geometry g = m_cur.next();
		if (g == null && m_cur != m_cur2) {
			m_cur = m_cur2;
			return m_cur.next();
		}
		return g;
	}

	@Override
	public int getGeometryID() {
		return m_cur.getGeometryID();
	}
}
