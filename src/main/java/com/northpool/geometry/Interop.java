

package com.northpool.geometry;

class Interop {
	public static double translateFromAVNaN(double n) {
		return (n < -1.0e38) ? NumberUtils.NaN() : n;
	}

	public static double translateToAVNaN(double n) {
		return (NumberUtils.isNaN(n)) ? -Double.MAX_VALUE : n;
	}
}
