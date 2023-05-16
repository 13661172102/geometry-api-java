
package com.northpool.geometry;

import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.io.Serializable;

//This is a writeReplace class for Point
public class PtSrlzr implements Serializable {
	private static final long serialVersionUID = 1L;
	double[] attribs;
	int descriptionBitMask;

	public Object readResolve() throws ObjectStreamException {
		Point point = null;
		try {
			VertexDescription vd = VertexDescriptionDesignerImpl
					.getVertexDescription(descriptionBitMask);
			point = new Point(vd);
			if (attribs != null) {
				point.setXY(attribs[0], attribs[1]);
				int index = 2;
				for (int i = 1, n = vd.getAttributeCount(); i < n; i++) {
					int semantics = vd.getSemantics(i);
					int comps = VertexDescription.getComponentCount(semantics);
					for (int ord = 0; ord < comps; ord++) {
						point.setAttribute(semantics, ord, attribs[index++]);
					}
				}
			}
		} catch (Exception ex) {
			throw new InvalidObjectException("Cannot read geometry from stream");
		}

		return point;
	}

	public void setGeometryByValue(Point point) throws ObjectStreamException {
		try {
			attribs = null;
			if (point == null) {
				descriptionBitMask = 1;
			}

			VertexDescription vd = point.getDescription();
			descriptionBitMask = vd.m_semanticsBitArray;
			if (point.isEmpty()) {
				return;
			}

			attribs = new double[vd.getTotalComponentCount()];
			attribs[0] = point.getX();
			attribs[1] = point.getY();
			int index = 2;
			for (int i = 1, n = vd.getAttributeCount(); i < n; i++) {
				int semantics = vd.getSemantics(i);
				int comps = VertexDescription.getComponentCount(semantics);
				for (int ord = 0; ord < comps; ord++) {
					attribs[index++] = point.getAttributeAsDbl(semantics, ord);
				}
			}
		} catch (Exception ex) {
			throw new InvalidObjectException("Cannot serialize this geometry");
		}
	}
}
