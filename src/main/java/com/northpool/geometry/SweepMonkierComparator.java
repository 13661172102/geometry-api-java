
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.northpool.geometry;

class SweepMonkierComparator extends Treap.MonikerComparator {
	protected EditShape m_shape;
	protected boolean m_b_intersection_detected;
	protected Point2D m_point_of_interest;
	protected Line m_line_1;
	protected Envelope1D m_env;
	protected int m_vertex_1;
	protected int m_current_node;
	protected double m_min_dist;
	protected double m_tolerance;

	SweepMonkierComparator(EditShape shape, double tol) {
		m_shape = shape;
		m_tolerance = tol;
		m_b_intersection_detected = false;
		m_vertex_1 = -1;
		m_env = new Envelope1D();
		m_point_of_interest = new Point2D();
		m_point_of_interest.setNaN();
		m_line_1 = new Line();
		m_current_node = -1;
		m_min_dist = NumberUtils.doubleMax();
	}

	int getCurrentNode() {
		return m_current_node;
	}

	// Makes the comparator to forget about the last detected intersection.
	// Need to be called after the intersection has been resolved.
	void clearIntersectionDetectedFlag() {
		m_b_intersection_detected = false;
		m_min_dist = NumberUtils.doubleMax();
	}

	// Returns True if there has been intersection detected during compare call.
	// Once intersection is detected subsequent calls to compare method do
	// nothing until clear_intersection_detected_flag is called.
	boolean intersectionDetected() {
		return m_b_intersection_detected;
	}

	void setPoint(Point2D pt) {
		m_point_of_interest.setCoords(pt);
	}

	// Compares the moniker, contained in the Moniker_comparator with the
	// element contained in the given node.
	@Override
	int compare(Treap treap, int node) {
		int vertex = treap.getElement(node);
		return compareVertex_(treap, node, vertex);
	}

	protected int compareVertex_(Treap treap, int node, int vertex) {
		boolean bCurve = m_shape.getSegment(vertex) != null;
		if (!bCurve) {
			m_shape.queryLineConnector(vertex, m_line_1);
			m_env.setCoordsNoNaN_(m_line_1.getStartX(), m_line_1.getEndX());
		}

		if (bCurve) {
			throw new GeometryException("not implemented");
		}

		if (m_point_of_interest.x + m_tolerance < m_env.vmin)
			return -1;

		if (m_point_of_interest.x - m_tolerance > m_env.vmax)
			return 1;

		if (m_line_1.getStartY() == m_line_1.getEndY()) {
			m_current_node = node;
			m_b_intersection_detected = true;
			return 0;
		}

		m_line_1.orientBottomUp_();
		Point2D start = m_line_1.getStartXY();
		Point2D vector = new Point2D();
		vector.sub(m_line_1.getEndXY(), start);
		vector.rightPerpendicular();
		Point2D v_2 = new Point2D();
		v_2.sub(m_point_of_interest, start);
		double dot = vector.dotProduct(v_2);
		dot /= vector.length();
		if (dot < -m_tolerance * 10)
			return -1;
		if (dot > m_tolerance * 10)
			return 1;

		if (m_line_1.isIntersecting(m_point_of_interest, m_tolerance)) {
			double absDot = Math.abs(dot);
			if (absDot < m_min_dist) {
				m_current_node = node;
				m_min_dist = absDot;
			}
			m_b_intersection_detected = true;
			if (absDot < 0.25 * m_tolerance)
				return 0;
		}

		return dot < 0 ? -1 : 1;
	}
}
