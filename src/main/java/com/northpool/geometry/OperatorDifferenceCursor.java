

package com.northpool.geometry;

class OperatorDifferenceCursor extends GeometryCursor {

	GeometryCursor m_inputGeoms;
	ProgressTracker m_progress_tracker;
	SpatialReference m_Spatial_reference;
	Geometry m_geomSubtractor;
	int m_index;
	boolean m_bEmpty;

	OperatorDifferenceCursor(GeometryCursor inputGeoms,
			GeometryCursor geomSubtractor, SpatialReference sr,
			ProgressTracker progress_tracker) {
		m_bEmpty = (geomSubtractor == null);
		m_index = -1;
		m_inputGeoms = inputGeoms;
		m_Spatial_reference = sr;
		m_geomSubtractor = geomSubtractor.next();
		m_progress_tracker = progress_tracker;
	}

	@Override
	public Geometry next() {
		if (m_bEmpty)
			return null;

		Geometry geom;
		if ((geom = m_inputGeoms.next()) != null) {
			m_index = m_inputGeoms.getGeometryID();
			return OperatorDifferenceLocal.difference(geom, m_geomSubtractor,
					m_Spatial_reference, m_progress_tracker);
		}
		return null;
	}

	@Override
	public int getGeometryID() {
		return m_index;
	}

}
