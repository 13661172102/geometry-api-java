
package com.northpool.geometry;

class SimpleJsonReaderCursor extends JsonReaderCursor {

	JsonReader m_jsonParser;
	JsonReader[] m_jsonParserArray;

	int m_index;
	int m_count;

	public SimpleJsonReaderCursor(JsonReader jsonString) {
		m_jsonParser = jsonString;
		m_index = -1;
		m_count = 1;
	}

	public SimpleJsonReaderCursor(JsonReader[] jsonStringArray) {
		m_jsonParserArray = jsonStringArray;
		m_index = -1;
		m_count = jsonStringArray.length;
	}

	@Override
	public int getID() {
		return m_index;
	}

	@Override
	public JsonReader next() {
		if (m_index < m_count - 1) {
			m_index++;
			return m_jsonParser != null ? m_jsonParser
					: m_jsonParserArray[m_index];
		}

		return null;
	}

}
