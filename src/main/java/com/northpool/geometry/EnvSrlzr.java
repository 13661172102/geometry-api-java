
package com.northpool.geometry;

import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.io.Serializable;

//This is a writeReplace class for Envelope
public class EnvSrlzr implements Serializable {
	private static final long serialVersionUID = 1L;
	double[] attribs;
	int descriptionBitMask;

	public Object readResolve() throws ObjectStreamException {
		Envelope env = null;
		if (descriptionBitMask == -1)
			return null;
		
		try {
			VertexDescription vd = VertexDescriptionDesignerImpl
					.getVertexDescription(descriptionBitMask);
			env = new Envelope(vd);
			if (attribs != null) {
				env.setCoords(attribs[0], attribs[1], attribs[2], attribs[3]);
				int index = 4;
				for (int i = 1, n = vd.getAttributeCount(); i < n; i++) {
					int semantics = vd.getSemantics(i);
					int comps = VertexDescription.getComponentCount(semantics);
					for (int ord = 0; ord < comps; ord++) {
						env.setInterval(semantics, ord, attribs[index++], attribs[index++]);
					}
				}
			}
		} catch (Exception ex) {
			throw new InvalidObjectException("Cannot read geometry from stream");
		}

		return env;
	}

	public void setGeometryByValue(Envelope env) throws ObjectStreamException {
		try {
			attribs = null;
			if (env == null) {
				descriptionBitMask = -1;
			}

			VertexDescription vd = env.getDescription();
			descriptionBitMask = vd.m_semanticsBitArray;
			if (env.isEmpty()) {
				return;
			}

			attribs = new double[vd.getTotalComponentCount() * 2];
			attribs[0] = env.getXMin();
			attribs[1] = env.getYMin();
			attribs[2] = env.getXMax();
			attribs[3] = env.getYMax();
			int index = 4;
			for (int i = 1, n = vd.getAttributeCount(); i < n; i++) {
				int semantics = vd.getSemantics(i);
				int comps = VertexDescription.getComponentCount(semantics);
				for (int ord = 0; ord < comps; ord++) {
					Envelope1D e = env.queryInterval(semantics, ord);
					attribs[index++] = e.vmin;
					attribs[index++] = e.vmax;
				}
			}
		} catch (Exception ex) {
			throw new InvalidObjectException("Cannot serialize this geometry");
		}
	}
}
