

package com.northpool.geometry;

class OperatorClipCursor extends GeometryCursor {

	GeometryCursor m_inputGeometryCursor;
	SpatialReferenceImpl m_spatialRefImpl;
	Envelope2D m_envelope;
	double m_tolerance;
	int m_index;

	OperatorClipCursor(GeometryCursor geoms, Envelope2D envelope,
			SpatialReference spatial_ref, ProgressTracker progress_tracker) {
		m_index = -1;
		if (geoms == null)
			throw new IllegalArgumentException();

		m_envelope = envelope;
		m_inputGeometryCursor = geoms;
		m_spatialRefImpl = (SpatialReferenceImpl) spatial_ref;
		m_tolerance = InternalUtils.calculateToleranceFromGeometry(spatial_ref,
				envelope, false);
	}

	@Override
	public Geometry next() {
		Geometry geometry;
		if ((geometry = m_inputGeometryCursor.next()) != null) {
			m_index = m_inputGeometryCursor.getGeometryID();
			return Clipper.clip(geometry, m_envelope, m_tolerance, 0.0);
		}
		return null;
	}

	@Override
	public int getGeometryID() {
		return m_index;
	}

	// virtual bool IsRecycling() OVERRIDE { return false; }

}
