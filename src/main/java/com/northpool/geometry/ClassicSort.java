
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.northpool.geometry;

abstract class ClassicSort {
	public abstract void userSort(int begin, int end,
			AttributeStreamOfInt32 indices);

	public abstract double getValue(int index);
}
