
package com.northpool.geometry;

class SimpleJsonCursor extends JsonCursor {

	String m_jsonString;
	String[] m_jsonStringArray;

	int m_index;
	int m_count;

	public SimpleJsonCursor(String jsonString) {
		m_jsonString = jsonString;
		m_index = -1;
		m_count = 1;
	}

	public SimpleJsonCursor(String[] jsonStringArray) {
		m_jsonStringArray = jsonStringArray;
		m_index = -1;
		m_count = jsonStringArray.length;
	}

	@Override
	public int getID() {
		return m_index;
	}

	@Override
	public String next() {
		if (m_index < m_count - 1) {
			m_index++;
			return m_jsonString != null ? m_jsonString
					: m_jsonStringArray[m_index];
		}

		return null;
	}

}
