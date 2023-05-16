
package com.northpool.geometry;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class ObjectCacheTable<K, T> {
	private Map<K, T> m_hashTable = Collections
			.synchronizedMap(new HashMap<K, T>());
	private Object[] m_lru;
	private boolean[] m_places;
	private int m_index;

	public ObjectCacheTable(int maxSize) {
		m_lru = new Object[maxSize];
		m_places = new boolean[maxSize];
		m_index = 0;
		for (int i = 0; i < maxSize; i++)
			m_places[i] = false;
	}

	boolean contains(K key) {
		return m_hashTable.containsKey(key);
	}

	T get(K key) {
		return m_hashTable.get(key);
	}

	void add(K key, T value) {
		if (m_places[m_index]) {// remove existing element from the cache
			m_places[m_index] = false;
			m_hashTable.remove(m_lru[m_index]);
		}

		m_hashTable.put(key, value);
		m_lru[m_index] = key;
		m_places[m_index] = true;
		m_index = (m_index + 1) % m_lru.length;
	}

}
