


package com.northpool.geometry;

import java.io.Serializable;

/**
 * 
 * Basic 3D point class.
 * 
 */
public final class Point3D implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public double x;
	public double y;
	public double z;

	public Point3D() {
	}

	public Point3D(Point3D other) {
		setCoords(other);
	}

	public Point3D(double x, double y, double z) {
		setCoords(x, y, z);
	}
	
	public static Point3D construct(double x, double y, double z) {
		Point3D pt = new Point3D();
		pt.setCoords(x, y, z);
		return pt;
	}

	public void setCoords(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void setCoords(Point3D other) {
		setCoords(other.x, other.y, other.z);
	}
	
	public void setZero() {
		x = 0.0;
		y = 0.0;
		z = 0.0;
	}

	public void normalize() {
		double len = length();
		if (len == 0) {
			x = 1.0;
			y = 0.0;
			z = 0.0;
		} else {
			x /= len;
			y /= len;
			z /= len;
		}
	}

	public double dotProduct(Point3D other) {
		return x * other.x + y * other.y + z * other.z;
	}

	public double sqrLength() {
		return x * x + y * y + z * z;
	}

	public double length() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	public void sub(Point3D other)
	{
		x -= other.x;
		y -= other.y;
		z -= other.z;
	}

	public void sub(Point3D p1, Point3D p2) {
		x = p1.x - p2.x;
		y = p1.y - p2.y;
		z = p1.z - p2.z;
	}

	public void scale(double f, Point3D other) {
		x = f * other.x;
		y = f * other.y;
		z = f * other.z;
	}

	public void mul(double factor) {
		x *= factor;
		y *= factor;
		z *= factor;
	}

	void _setNan() {
		x = NumberUtils.NaN();
		y = NumberUtils.NaN();
		z = NumberUtils.NaN();
	}

	boolean _isNan() {
		return NumberUtils.isNaN(x) || NumberUtils.isNaN(y) || NumberUtils.isNaN(z);
	}

	public boolean equals(Point3D other) {
		//note that for nan value this returns false.
		//this is by design for this class.
		return x == other.x && y == other.y && z == other.z;
	}
	
	@Override
	public boolean equals(Object other_) {
		if (other_ == this)
			return true;

		if (!(other_ instanceof Point3D))
			return false;
		
		Point3D other = (Point3D)other_;
		//note that for nan value this returns false.
		//this is by design for this class.
		return x == other.x && y == other.y && z == other.z;
	}

	@Override
	public int hashCode() {
		int hash = NumberUtils.hash(x);
		hash = NumberUtils.hash(hash, y);
		hash = NumberUtils.hash(hash, z);
		return hash;
	}
}
