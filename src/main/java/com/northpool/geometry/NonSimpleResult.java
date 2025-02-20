
package com.northpool.geometry;

/**
 * The result of the IsSimpleXXX.
 * 
 *
 */
public class NonSimpleResult {
	public enum Reason {
		/**
		*This value is returned if the geometry "knows" through an internal state that it is non-simple.
		*To make it determine the reason, use
		*bForceTest == True.
		*/		
		NotDetermined,
		/**
		 * non-simple, because the structure is bad (0 size path, for example).
		 */
		Structure, 
		/**
		 * Non-simple, because there are degenerate segments.
		 */
		DegenerateSegments,
		/**
		 * Non-simple, because not clustered properly, that is there are non-coincident vertices closer than tolerance.
		 */		
		Clustering,
		/**
		 * Non-simple, because not cracked properly (intersecting segments, overlaping segments)
		 */		
		Cracking,
		/**
		 * Non-simple, because there are crossovers (self intersections that are not cracking case).
		 */
		CrossOver,
		/**
		 * Non-simple, because holes or exteriors have wrong orientation.
		 */
		RingOrientation,
		/**
		 *The geometry is simple, but not strong-simple, because exteriors
		 *and holes are not in the correct order, and separation into sub polygons is not possible.
		 *Geometry needs to be resimplified with the bForceTest = true to fix this.
		 */		
		RingOrder,
		/**
		 * There is a self tangency or cross-over situation (strong simple, but not OGC simple)
		 * Only OperatorSimplifyOGC returns this.
		 */		
		OGCPolylineSelfTangency,
		/**
		 * There is a self tangency situation (strong simple, but not OGC simple)
		 * Only OperatorSimplifyOGC returns this.
		 */
		OGCPolygonSelfTangency,
		/**
		 * Touching interioir rings make a disconnected point set from polygon interior
		 * (strong simple, but not OGC simple).
		 * Only OperatorSimplifyOGC returns this.
		 */
		OGCDisconnectedInterior
	}

	public Reason m_reason;
	public int m_vertexIndex1;
	public int m_vertexIndex2;

	public NonSimpleResult() {
		m_reason = Reason.NotDetermined;
		m_vertexIndex1 = -1;
		m_vertexIndex2 = -1;
	}

	void Assign(NonSimpleResult src) {
		m_reason = src.m_reason;
		m_vertexIndex1 = src.m_vertexIndex1;
		m_vertexIndex2 = src.m_vertexIndex2;
	}

	NonSimpleResult(Reason reason, int index1, int index2) {
		m_reason = reason;
		m_vertexIndex1 = index1;
		m_vertexIndex2 = index2;
	}
}
