

package com.northpool.geometry;

class OperatorBufferCursor extends GeometryCursor {
	Bufferer m_bufferer = new Bufferer();
	private GeometryCursor m_inputGeoms;
	private SpatialReferenceImpl m_Spatial_reference;
	private ProgressTracker m_progress_tracker;
	private double[] m_distances;
	private Envelope2D m_currentUnionEnvelope2D;
    double m_max_deviation;
    int m_max_vertices_in_full_circle;
	private int m_index;
	private int m_dindex;

	OperatorBufferCursor(GeometryCursor inputGeoms, SpatialReference sr,
			double[] distances,
			double max_deviation,
			int max_vertices,
			boolean b_union,
			ProgressTracker progress_tracker) {
		m_index = -1;
		m_inputGeoms = inputGeoms;
		m_max_deviation = max_deviation;
		m_max_vertices_in_full_circle = max_vertices;
		m_Spatial_reference = (SpatialReferenceImpl) (sr);
		m_distances = distances;
		m_currentUnionEnvelope2D = new Envelope2D();
		m_currentUnionEnvelope2D.setEmpty();
		m_dindex = -1;
		m_progress_tracker = progress_tracker;
	}

	@Override
	public Geometry next() {
		{
			Geometry geom;
			while ((geom = m_inputGeoms.next()) != null) {
				m_index = m_inputGeoms.getGeometryID();
				if (m_dindex + 1 < m_distances.length)
					m_dindex++;

				return buffer(geom, m_distances[m_dindex]);
			}
			return null;
		}
	}

	@Override
	public int getGeometryID() {
		return m_index;
	}

	// virtual bool IsRecycling() OVERRIDE { return false; }
	Geometry buffer(Geometry geom, double distance) {
		return m_bufferer.buffer(geom, distance, m_Spatial_reference,
				m_max_deviation, m_max_vertices_in_full_circle, m_progress_tracker);
	}
}
