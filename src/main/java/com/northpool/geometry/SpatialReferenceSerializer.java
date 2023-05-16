

package com.northpool.geometry;

import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.io.Serializable;

final class SpatialReferenceSerializer implements Serializable {
	private static final long serialVersionUID = 10000L;
	String wkt = null;
	int wkid = 0;

	Object readResolve() throws ObjectStreamException {
		SpatialReference sr = null;
		try {
			if (wkid > 0)
				sr = SpatialReference.create(wkid);
			else
				sr = SpatialReference.create(wkt);
		} catch (Exception ex) {
			throw new InvalidObjectException(
					"Cannot read spatial reference from stream");
		}
		return sr;
	}

	public void setSpatialReferenceByValue(SpatialReference sr)
			throws ObjectStreamException {
		try {
			if (sr.getID() > 0)
				wkid = sr.getID();
			else
				wkt = sr.getText();
		} catch (Exception ex) {
			throw new InvalidObjectException("Cannot serialize this geometry");
		}
	}
}
