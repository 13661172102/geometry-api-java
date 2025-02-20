

package com.northpool.geometry;

import junit.framework.TestCase;
import org.junit.Test;

public class TestTreap extends TestCase {
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public static void test1() {
		Point2D[] pts = new Point2D[10];

		for (int i = 0; i < 10; i++) {
			Point2D pt = new Point2D();
			pt.x = i;
			pt.y = 0;

			pts[i] = pt;
		}

		TreapComparatorForTesting c = new TreapComparatorForTesting(pts);
		Treap treap = new Treap();
		treap.setComparator(c);

		int[] nodes = new int[10];
		for (int i = 0; i < 10; i++)
			nodes[i] = treap.addElement(i, -1);

		for (int i = 1; i < 10; i++) {
			assertTrue(treap.getPrev(nodes[i]) == nodes[i - 1]);
		}

		for (int i = 0; i < 9; i++) {
			assertTrue(treap.getNext(nodes[i]) == nodes[i + 1]);
		}

		treap.deleteNode(nodes[0], -1);
		treap.deleteNode(nodes[2], -1);
		treap.deleteNode(nodes[4], -1);
		treap.deleteNode(nodes[6], -1);
		treap.deleteNode(nodes[8], -1);

		assertTrue(treap.getPrev(nodes[3]) == nodes[1]);
		assertTrue(treap.getPrev(nodes[5]) == nodes[3]);
		assertTrue(treap.getPrev(nodes[7]) == nodes[5]);
		assertTrue(treap.getPrev(nodes[9]) == nodes[7]);

		assertTrue(treap.getNext(nodes[1]) == nodes[3]);
		assertTrue(treap.getNext(nodes[3]) == nodes[5]);
		assertTrue(treap.getNext(nodes[5]) == nodes[7]);
		assertTrue(treap.getNext(nodes[7]) == nodes[9]);
	}
}

final class TreapComparatorForTesting extends Treap.Comparator {
	Point2D[] m_pts;

	TreapComparatorForTesting(Point2D[] pts) {
		m_pts = pts;
	}

	@Override
	int compare(Treap treap, int elm, int node) {
		int elm2 = treap.getElement(node);
		Point2D pt1 = m_pts[elm];
		Point2D pt2 = m_pts[elm2];

		if (pt1.x < pt2.x)
			return -1;

		return 1;
	}
}
