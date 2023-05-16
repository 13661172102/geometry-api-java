
package com.northpool.geometry;

//An internal helper class. Do not use.
public class OGCStructureInternal {
	private static class EditShapeCursor extends GeometryCursor {
		EditShape m_shape;
		int m_geom;
		int m_index;
		
		EditShapeCursor(EditShape shape, int index) {
			m_shape = shape;
			m_geom = -1;
			m_index = index;
		}
		@Override
		public Geometry next() {
			if (m_shape != null) {
				if (m_geom == -1)
					m_geom = m_shape.getFirstGeometry();
				else
					m_geom = m_shape.getNextGeometry(m_geom);
				
				if (m_geom == -1) {
					m_shape = null;
				}
				else {
					return m_shape.getGeometry(m_geom);
				}
					
			}
			
			return null;
		}

		@Override
		public int getGeometryID() {
			return m_shape.getGeometryUserIndex(m_geom, m_index);
		}
		
	};
	
	public static GeometryCursor prepare_for_ops_(GeometryCursor geoms, SpatialReference sr) {
		assert(geoms != null);
		EditShape editShape = new EditShape();
		int geomIndex = editShape.createGeometryUserIndex();
		for (Geometry g = geoms.next(); g != null; g = geoms.next()) {
			int egeom = editShape.addGeometry(g);
			editShape.setGeometryUserIndex(egeom, geomIndex, geoms.getGeometryID());
		}

		Envelope2D env = editShape.getEnvelope2D();
		double tolerance = InternalUtils.calculateToleranceFromGeometry(sr,
				env, true);

		CrackAndCluster.execute(editShape, tolerance, null, true);
		return OperatorSimplifyOGC.local().execute(new EditShapeCursor(editShape, geomIndex), sr, false, null);
	}
}

