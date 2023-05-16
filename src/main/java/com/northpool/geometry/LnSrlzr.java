
package com.northpool.geometry;

import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.io.Serializable;

//This is a writeReplace class for Lin
public class LnSrlzr implements Serializable {
	private static final long serialVersionUID = 1L;
	double[] attribs;
	int descriptionBitMask;

	public Object readResolve() throws ObjectStreamException {
		Line ln = null;
		if (descriptionBitMask == -1)
			return null;
		
		try {
			VertexDescription vd = VertexDescriptionDesignerImpl
					.getVertexDescription(descriptionBitMask);
			ln = new Line(vd);
			if (attribs != null) {
				ln.setStartXY(attribs[0], attribs[1]);
				ln.setEndXY(attribs[2], attribs[3]);
				int index = 4;
				for (int i = 1, n = vd.getAttributeCount(); i < n; i++) {
					int semantics = vd.getSemantics(i);
					int comps = VertexDescription.getComponentCount(semantics);
					for (int ord = 0; ord < comps; ord++) {
						ln.setStartAttribute(semantics, ord, attribs[index++]);
						ln.setEndAttribute(semantics, ord, attribs[index++]);
					}
				}
			}
		} catch (Exception ex) {
			throw new InvalidObjectException("Cannot read geometry from stream");
		}

		return ln;
	}

	public void setGeometryByValue(Line ln) throws ObjectStreamException {
		try {
			attribs = null;
			if (ln == null) {
				descriptionBitMask = -1;
			}

			VertexDescription vd = ln.getDescription();
			descriptionBitMask = vd.m_semanticsBitArray;

			attribs = new double[vd.getTotalComponentCount() * 2];
			attribs[0] = ln.getStartX();
			attribs[1] = ln.getStartY();
			attribs[2] = ln.getEndX();
			attribs[3] = ln.getEndY();
			int index = 4;
			for (int i = 1, n = vd.getAttributeCount(); i < n; i++) {
				int semantics = vd.getSemantics(i);
				int comps = VertexDescription.getComponentCount(semantics);
				for (int ord = 0; ord < comps; ord++) {
					attribs[index++] = ln.getStartAttributeAsDbl(semantics, ord);
					attribs[index++] = ln.getEndAttributeAsDbl(semantics, ord);
				}
			}
		} catch (Exception ex) {
			throw new InvalidObjectException("Cannot serialize this geometry");
		}
	}
}
