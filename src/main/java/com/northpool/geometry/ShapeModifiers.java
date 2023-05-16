

package com.northpool.geometry;

interface ShapeModifiers {
	public static final int ShapeHasZs = 0x80000000;
	public static final int ShapeHasMs = 0x40000000;
	public static final int ShapeHasCurves = 0x20000000;
	public static final int ShapeHasIDs = 0x10000000;
	public static final int ShapeHasNormals = 0x08000000;
	public static final int ShapeHasTextures = 0x04000000;
	public static final int ShapeHasPartIDs = 0x02000000;
	public static final int ShapeHasMaterials = 0x01000000;
	public static final int ShapeIsCompressed = 0x00800000;
	public static final int ShapeModifierMask = 0xFF000000;
	public static final int ShapeMultiPatchModifierMask = 0x0F00000;
	public static final int ShapeBasicTypeMask = 0x000000FF;
	public static final int ShapeBasicModifierMask = 0xC0000000;
	public static final int ShapeNonBasicModifierMask = 0x3F000000;
	public static final int ShapeExtendedModifierMask = 0xDD000000;
}
