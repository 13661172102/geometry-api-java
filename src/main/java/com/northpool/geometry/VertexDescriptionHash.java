

package com.northpool.geometry;

import java.util.HashMap;

/**
 * A hash object singleton that stores all VertexDescription instances via
 * WeakReference. The purpose of the class is to keep track of created
 * VertexDescription instances to prevent duplicates.
 */
final class VertexDescriptionHash {
	HashMap<Integer, VertexDescription> m_map = new HashMap<Integer, VertexDescription>();

	private static VertexDescription m_vd2D = new VertexDescription(1);
	private static VertexDescription m_vd3D = new VertexDescription(3);

	private static final VertexDescriptionHash INSTANCE = new VertexDescriptionHash();

	private VertexDescriptionHash() {
		m_map.put(1, m_vd2D);
		m_map.put(3, m_vd3D);
	}

	public static VertexDescriptionHash getInstance() {
		return INSTANCE;
	}

	public final VertexDescription getVD2D() {
		return m_vd2D;
	}

	public final VertexDescription getVD3D() {
		return m_vd3D;
	}

	public final VertexDescription FindOrAdd(int bitSet) {
		if (bitSet == 1)
			return m_vd2D;
		if (bitSet == 3)
			return m_vd3D;

		synchronized (this) {
			VertexDescription vd = m_map.get(bitSet);
			if (vd == null) {
				vd = new VertexDescription(bitSet);
				m_map.put(bitSet, vd);
			}

			return vd;
		}
	}

}
