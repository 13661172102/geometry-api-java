

package com.northpool.geometry;

class OperatorCutLocal extends OperatorCut {
	public static interface Side {
		public static final int Left = 0;
		public static final int Right = 1;
		public static final int Coincident = 2;
		public static final int Undefined = 3;
		public static final int Uncut = 4;
	}

	public static class CutPair {
		public CutPair(Geometry geometry, int side, int ipartCuttee,
				int ivertexCuttee, double scalarCuttee, int sidePrev,
				int ipartCutteePrev, int ivertexCutteePrev,
				double scalarCutteePrev, int ipartCutter, int ivertexCutter,
				double scalarCutter, int ipartCutterPrev,
				int ivertexCutterPrev, double scalarCutterPrev) {
			m_geometry = geometry;
			m_side = side;
			m_ipartCuttee = ipartCuttee;
			m_ivertexCuttee = ivertexCuttee;
			m_scalarCuttee = scalarCuttee;
			m_sidePrev = sidePrev;
			m_ipartCutteePrev = ipartCutteePrev;
			m_ivertexCutteePrev = ivertexCutteePrev;
			m_scalarCutteePrev = scalarCutteePrev;
			m_ipartCutter = ipartCutter;
			m_ivertexCutter = ivertexCutter;
			m_scalarCutter = scalarCutter;
			m_ipartCutterPrev = ipartCutterPrev;
			m_ivertexCutterPrev = ivertexCutterPrev;
			m_scalarCutterPrev = scalarCutterPrev;
		}

		Geometry m_geometry;
		int m_side;
		int m_ipartCuttee;
		int m_ivertexCuttee;
		double m_scalarCuttee;
		int m_sidePrev;
		int m_ipartCutteePrev;
		int m_ivertexCutteePrev;
		double m_scalarCutteePrev;
		int m_ipartCutter;
		int m_ivertexCutter;
		double m_scalarCutter;
		int m_ipartCutterPrev;
		int m_ivertexCutterPrev;
		double m_scalarCutterPrev;
	};

	@Override
	public GeometryCursor execute(boolean bConsiderTouch, Geometry cuttee,
			Polyline cutter, SpatialReference spatialReference,
			ProgressTracker progressTracker) {
		return new OperatorCutCursor(bConsiderTouch, cuttee, cutter,
				spatialReference, progressTracker);
	}
}
