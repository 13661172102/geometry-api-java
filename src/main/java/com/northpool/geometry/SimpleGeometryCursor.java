
package com.northpool.geometry;

import java.util.Arrays;
import java.util.List;

/**
 * A simple GeometryCursor implementation that wraps a single Geometry or
 * an array of Geometry classes
 */
public class SimpleGeometryCursor extends GeometryCursor {

	Geometry m_geom;
	List<Geometry> m_geomArray;

	int m_index;
	int m_count;

	public SimpleGeometryCursor(Geometry geom) {
		m_geom = geom;
		m_index = -1;
		m_count = 1;
	}

	public SimpleGeometryCursor(Geometry[] geoms) {
		m_geomArray = Arrays.asList(geoms);
		m_index = -1;
		m_count = geoms.length;
	}

	public SimpleGeometryCursor(List<Geometry> geoms) {
		m_geomArray = geoms;
		m_index = -1;
		m_count = geoms.size();
	}

	@Override
	public int getGeometryID() {
		return m_index;
	}

	@Override
	public Geometry next() {
		if (m_index < m_count - 1) {
			m_index++;
			return m_geom != null ? m_geom : m_geomArray.get(m_index);
		}

		return null;
	}
}
