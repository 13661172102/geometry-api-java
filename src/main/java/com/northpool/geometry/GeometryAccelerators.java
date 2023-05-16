
package com.northpool.geometry;

class GeometryAccelerators {

	private RasterizedGeometry2D m_rasterizedGeometry;
	private QuadTreeImpl m_quad_tree;
    private QuadTreeImpl m_quad_tree_for_paths;

	public RasterizedGeometry2D getRasterizedGeometry() {
		return m_rasterizedGeometry;
	}

	public QuadTreeImpl getQuadTree() {
		return m_quad_tree;
	}

	public QuadTreeImpl getQuadTreeForPaths() {
		return m_quad_tree_for_paths;
	}

	void _setRasterizedGeometry(RasterizedGeometry2D rg) {
		m_rasterizedGeometry = rg;
	}

	void _setQuadTree(QuadTreeImpl quad_tree) {
		m_quad_tree = quad_tree;
	}

	void _setQuadTreeForPaths(QuadTreeImpl quad_tree) { m_quad_tree_for_paths = quad_tree; }

	static boolean canUseRasterizedGeometry(Geometry geom) {
		if (geom.isEmpty()
				|| !(geom.getType() == Geometry.Type.Polyline || geom.getType() == Geometry.Type.Polygon)) {
			return false;
		}

		return true;
	}

	static boolean canUseQuadTree(Geometry geom) {
		if (geom.isEmpty()
				|| !(geom.getType() == Geometry.Type.Polyline || geom.getType() == Geometry.Type.Polygon)) {
			return false;
		}

		if (((MultiVertexGeometry) geom).getPointCount() < 20) {
			return false;
		}

		return true;
	}

    static boolean canUseQuadTreeForPaths(Geometry geom) {
        if (geom.isEmpty() || !(geom.getType() == Geometry.Type.Polyline || geom.getType() == Geometry.Type.Polygon))
            return false;

        if (((MultiVertexGeometry) geom).getPointCount() < 20)
            return false;

        return true;
    }

	public long estimateMemorySize()
	{
		return (m_rasterizedGeometry != null ? m_rasterizedGeometry.estimateMemorySize() : 0) +
			(m_quad_tree != null ? m_quad_tree.estimateMemorySize() : 0) +
			(m_quad_tree_for_paths != null ? m_quad_tree_for_paths.estimateMemorySize() : 0);
	}
}
