

package com.northpool.geometry;

import java.util.Comparator;

class Proximity2DResultComparator implements Comparator<Proximity2DResult> {

	public int compare(Proximity2DResult v1, Proximity2DResult v2) {
		if (v1.m_distance < v2.m_distance)
			return -1;
		if (v1.m_distance == v2.m_distance)
			return 0;

		return 1;
	}

}
