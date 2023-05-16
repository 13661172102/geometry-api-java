
package com.northpool.geometry;

import java.util.LinkedList;

/**
 * 
 * A GeometryCursor implementation that allows pushing geometries into it.
 * 
 * To be used with aggregating operations, OperatorUnion and OperatorConvexHull,
 * when the geometries are not available at the time of the execute method call,
 * but are coming in a stream.
 */
public final class ListeningGeometryCursor extends GeometryCursor {

	LinkedList<Geometry> m_geomList = new LinkedList<Geometry>();
	int m_index = -1;

	public ListeningGeometryCursor() {
	}

	@Override
	public int getGeometryID() {
		return m_index;
	}

	@Override
	public Geometry next() {
		if (m_geomList != null && !m_geomList.isEmpty()) {
			m_index++;
			return m_geomList.pollFirst();
		}

		m_geomList = null;//prevent the class from being used again
		return null;
	}

	/**
	 * Call this method to add geometry to the cursor. After this method is
	 * called, call immediately the tock() method on the GeometryCursor returned
	 * by the OperatorUnion (or OperatorConvexHull with b_merge == true). Call
	 * next() on the GeometryCursor returned by the OperatorUnion when done
	 * listening to incoming geometries to finish the union operation.
	 * 
	 * @param geom The geometry to be pushed into the cursor.
	 */
	public void tick(Geometry geom) {
		m_geomList.add(geom);
	}
}
