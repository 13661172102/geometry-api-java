
package com.northpool.geometry;

/**
 * A simple MapGeometryCursor implementation that wraps a single MapGeometry or
 * an array of MapGeometry classes
 */
class SimpleMapGeometryCursor extends MapGeometryCursor {

	MapGeometry m_geom;
	MapGeometry[] m_geomArray;

	int m_index;
	int m_count;

	public SimpleMapGeometryCursor(MapGeometry geom) {
		m_geom = geom;
		m_index = -1;
		m_count = 1;
	}

	public SimpleMapGeometryCursor(MapGeometry[] geoms) {
		m_geomArray = geoms;
		m_index = -1;
		m_count = geoms.length;
	}

	@Override
	public int getGeometryID() {
		return m_index;
	}

	@Override
	public MapGeometry next() {
		if (m_index < m_count - 1) {
			m_index++;
			return m_geom != null ? m_geom : m_geomArray[m_index];
		}

		return null;
	}
}
