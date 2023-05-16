
package com.northpool.geometry;

class OperatorSymmetricDifferenceCursor extends GeometryCursor {

	GeometryCursor m_inputGeoms;
	ProgressTracker m_progress_tracker;
	SpatialReference m_spatial_reference;
	Geometry m_rightGeom;
	int m_index;
	boolean m_bEmpty;

	OperatorSymmetricDifferenceCursor(GeometryCursor inputGeoms,
			GeometryCursor rightGeom, SpatialReference sr,
			ProgressTracker progress_tracker) {
		m_bEmpty = rightGeom == null;
		m_index = -1;
		m_inputGeoms = inputGeoms;
		m_spatial_reference = sr;
		m_rightGeom = rightGeom.next();
		m_progress_tracker = progress_tracker;
	}

	@Override
	public Geometry next() {
		if (m_bEmpty)
			return null;

		Geometry leftGeom;
		if ((leftGeom = m_inputGeoms.next()) != null) {
			m_index = m_inputGeoms.getGeometryID();
			return OperatorSymmetricDifferenceLocal.symmetricDifference(
					leftGeom, m_rightGeom, m_spatial_reference,
					m_progress_tracker);
		}
		return null;
	}

	@Override
	public int getGeometryID() {
		return m_index;
	}
}
