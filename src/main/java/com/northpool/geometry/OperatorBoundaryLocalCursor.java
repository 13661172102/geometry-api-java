
package com.northpool.geometry;

final class OperatorBoundaryLocalCursor extends GeometryCursor {
	ProgressTracker m_progress_tracker;
	boolean m_b_done;
	GeometryCursor m_inputGeometryCursor;
	int m_index;

	OperatorBoundaryLocalCursor(GeometryCursor inputGeoms,
			ProgressTracker tracker) {
		m_inputGeometryCursor = inputGeoms;
		m_progress_tracker = tracker;
		m_b_done = false;
		m_index = -1;
	}

	@Override
	public Geometry next() {
		if (!m_b_done) {
			Geometry geometry = m_inputGeometryCursor.next();
			if (geometry != null) {
				m_index = m_inputGeometryCursor.getGeometryID();
				return calculate_boundary(geometry, m_progress_tracker);
			}

			m_b_done = true;
		}

		return null;
	}

	@Override
	public int getGeometryID() {
		return m_index;
	}

	static Geometry calculate_boundary(Geometry geom,
			ProgressTracker progress_tracker) {
		Geometry res = Boundary.calculate(geom, progress_tracker);
		if (res == null)
			return new Point(geom.getDescription());// cannot return null
		else
			return res;
	}

}
