

package com.northpool.geometry;

import java.nio.ByteBuffer;

class SimpleByteBufferCursor extends ByteBufferCursor {

	ByteBuffer m_byteBuffer;
	int m_index;
	int m_count;

	public SimpleByteBufferCursor(ByteBuffer byteBuffer) {
		m_byteBuffer = byteBuffer;
		m_index = -1;
		m_count = 1;
	}

	@Override
	public int getByteBufferID() {
		return m_index;
	}

	@Override
	public ByteBuffer next() {
		if (m_index < m_count - 1) {
			m_index++;
			return m_byteBuffer;
		}

		return null;
	}

}
