

package com.northpool.geometry;

import java.util.Arrays;

/**
 * This factory class allows to describe and create a VertexDescription
 * instance.
 */
final class VertexDescriptionDesignerImpl {
	static VertexDescription getVertexDescription(int descriptionBitMask) {
		return VertexDescriptionHash.getInstance()
				.FindOrAdd(descriptionBitMask);
	}

	static VertexDescription getMergedVertexDescription(
			VertexDescription descr1, VertexDescription descr2) {
		int mask = descr1.m_semanticsBitArray | descr2.m_semanticsBitArray;
		if ((mask & descr1.m_semanticsBitArray) == mask) {
			return descr1;
		} else if ((mask & descr2.m_semanticsBitArray) == mask) {
			return descr2;
		}

		return getVertexDescription(mask);
	}

	static VertexDescription getMergedVertexDescription(
			VertexDescription descr, int semantics) {
		int mask = descr.m_semanticsBitArray | (1 << semantics);
		if ((mask & descr.m_semanticsBitArray) == mask) {
			return descr;
		}

		return getVertexDescription(mask);
	}

	static VertexDescription removeSemanticsFromVertexDescription(
			VertexDescription descr, int semanticsToRemove) {
		int mask = (descr.m_semanticsBitArray | (1 << (int) semanticsToRemove))
				- (1 << (int) semanticsToRemove);
		if (mask == descr.m_semanticsBitArray) {
			return descr;
		}

		return getVertexDescription(mask);
	}

	static VertexDescription getDefaultDescriptor2D() {
		return VertexDescriptionHash.getInstance().getVD2D();
	}

	static VertexDescription getDefaultDescriptor3D() {
		return VertexDescriptionHash.getInstance().getVD3D();
	}

	static int[] mapAttributes(VertexDescription src, VertexDescription dest) {
		int[] srcToDst = new int[src.getAttributeCount()];
		Arrays.fill(srcToDst, -1);
		for (int i = 0, nsrc = src.getAttributeCount(); i < nsrc; i++) {
			srcToDst[i] = dest.getAttributeIndex(src.getSemantics(i));
		}
		return srcToDst;
	}
}
