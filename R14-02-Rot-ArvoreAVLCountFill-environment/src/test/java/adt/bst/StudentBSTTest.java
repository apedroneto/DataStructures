package adt.bst;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import adt.bst.BSTImpl;
import adt.bt.BTNode;

public class StudentBSTTest {

	private BSTImpl<Integer> tree;
	private BTNode<Integer> NIL = new BTNode<Integer>();

	private void fillTree() {
		Integer[] array = { 6, 23, -34, 5, 9, 2, 0, 76, 12, 67, 232, -40 };
		for (int i : array) {
			tree.insert(i);
		}
	}

	@Before
	public void setUp() {
		tree = new BSTImpl<>();
	}

	@Test
	public void testInit() {
		assertTrue(tree.isEmpty());
		assertEquals(0, tree.size());
		assertEquals(-1, tree.height());

		assertEquals(NIL, tree.getRoot());

		assertArrayEquals(new Integer[] {}, tree.order());
		assertArrayEquals(new Integer[] {}, tree.preOrder());
		assertArrayEquals(new Integer[] {}, tree.postOrder());

		assertEquals(NIL, tree.search(12));
		assertEquals(NIL, tree.search(-23));
		assertEquals(NIL, tree.search(0));

		assertEquals(null, tree.minimum());
		assertEquals(null, tree.maximum());

		assertEquals(null, tree.sucessor(12));
		assertEquals(null, tree.sucessor(-23));
		assertEquals(null, tree.sucessor(0));

		assertEquals(null, tree.predecessor(12));
		assertEquals(null, tree.predecessor(-23));
		assertEquals(null, tree.predecessor(0));
	}

	@Test
	public void testMinMax() {
		tree.insert(6);
		assertEquals(new Integer(6), tree.minimum().getData());
		assertEquals(new Integer(6), tree.maximum().getData());

		tree.insert(23);
		assertEquals(new Integer(6), tree.minimum().getData());
		assertEquals(new Integer(23), tree.maximum().getData());

		tree.insert(-34);
		assertEquals(new Integer(-34), tree.minimum().getData());
		assertEquals(new Integer(23), tree.maximum().getData());

		tree.insert(5);
		assertEquals(new Integer(-34), tree.minimum().getData());
		assertEquals(new Integer(23), tree.maximum().getData());

		tree.insert(9);
		assertEquals(new Integer(-34), tree.minimum().getData());
		assertEquals(new Integer(23), tree.maximum().getData());
	}

	@Test
	public void testSucessorPredecessor() {

		fillTree(); // -40 -34 0 2 5 6 9 12 23 67 76 232

		assertEquals(null, tree.predecessor(-40));
		assertEquals(new Integer(-34), tree.sucessor(-40).getData());

		assertEquals(new Integer(-40), tree.predecessor(-34).getData());
		assertEquals(new Integer(0), tree.sucessor(-34).getData());

		assertEquals(new Integer(-34), tree.predecessor(0).getData());
		assertEquals(new Integer(2), tree.sucessor(0).getData());

		Integer s = tree.predecessor(2).getData();
		assertEquals(new Integer(0), tree.predecessor(2).getData());
		assertEquals(new Integer(5), tree.sucessor(2).getData());
	}

	@Test
	public void testSize() {
		fillTree(); // -40 -34 0 2 5 6 9 12 23 67 76 232

		int size = 12;
		assertEquals(size, tree.size());

		while (!tree.isEmpty()) {
			tree.remove(tree.getRoot().getData());
			assertEquals(--size, tree.size());
		}
	}

	@Test
	public void testHeight() {
		fillTree(); // -40 -34 0 2 5 6 9 12 23 67 76 232

		Integer[] preOrder = new Integer[] { 6, -34, -40, 5, 2, 0, 23, 9, 12,
				76, 67, 232 };
		assertArrayEquals(preOrder, tree.preOrder());
		assertEquals(4, tree.height());

		tree.remove(0);
		assertEquals(3, tree.height());

		tree.remove(2);
		assertEquals(3, tree.height());
	}

	@Test
	public void testRemove() {
		fillTree(); // -40 -34 0 2 5 6 9 12 23 67 76 232

		Integer[] order = { -40, -34, 0, 2, 5, 6, 9, 12, 23, 67, 76, 232 };
		assertArrayEquals(order, tree.order());

		tree.remove(6);
		order = new Integer[] { -40, -34, 0, 2, 5, 9, 12, 23, 67, 76, 232 };
		assertArrayEquals(order, tree.order());

		tree.remove(9);
		order = new Integer[] { -40, -34, 0, 2, 5, 12, 23, 67, 76, 232 };
		assertArrayEquals(order, tree.order());

		assertEquals(NIL, tree.search(6));
		assertEquals(NIL, tree.search(9));

	}

	@Test
	public void testSearch() {

		fillTree(); // -40 -34 0 2 5 6 9 12 23 67 76 232

		assertEquals(new Integer(-40), tree.search(-40).getData());
		assertEquals(new Integer(-34), tree.search(-34).getData());
		assertEquals(NIL, tree.search(2534));
	}
	
	@Test
    public void war_haha() {
        tree.insert(6); tree.insert(-31); tree.insert(-15); tree.insert(11); tree.insert(47);
        tree.insert(46); tree.insert(-1); tree.insert(1); tree.insert(50); tree.insert(8);
        //tree.insert(null);
       
        assertEquals(10, tree.size());
        assertEquals(4, tree.height());
       
       
        assertArrayEquals(new Integer[] {6, -31, -15, -1, 1, 11, 8, 47, 46, 50}, tree.preOrder());
        assertArrayEquals(new Integer[] {-31, -15, -1, 1, 6, 8, 11, 46, 47, 50}, tree.order());
        assertArrayEquals(new Integer[] {1, -1, -15, -31, 8, 46, 50, 47, 11, 6}, tree.postOrder());
       
        assertEquals(new Integer(-31), tree.search(-31).getData()); assertEquals(new Integer(6), tree.search(6).getData());
        assertEquals(new Integer(8), tree.search(8).getData()); assertEquals(new Integer(-1), tree.search(-1).getData());
        assertEquals(NIL, tree.search(5));
       
 
        tree.remove(6);
               
        assertArrayEquals(new Integer[] {8, -31, -15, -1, 1, 11, 47, 46, 50}, tree.preOrder());
        assertArrayEquals(new Integer[] {-31, -15, -1, 1, 8, 11, 46, 47, 50}, tree.order());
        assertArrayEquals(new Integer[] {1, -1, -15, -31, 46, 50, 47, 11, 8}, tree.postOrder());
       
        assertEquals(new Integer(-31), tree.search(-31).getData()); assertEquals(new Integer(1), tree.search(1).getData());
        assertEquals(new Integer(8), tree.search(8).getData()); assertEquals(new Integer(-1), tree.search(-1).getData());
        assertEquals(NIL, tree.search(5)); assertEquals(NIL, tree.search(6));
       
       
        tree.remove(-1); tree.remove(47);
       
        assertArrayEquals(new Integer[] {8, -31, -15, 1, 11, 50, 46}, tree.preOrder());
        assertArrayEquals(new Integer[] {-31, -15, 1, 8, 11, 46, 50}, tree.order());
        assertArrayEquals(new Integer[] {1, -15, -31, 46, 50, 11, 8}, tree.postOrder());
       
        assertEquals(new Integer(-31), tree.search(-31).getData()); assertEquals(new Integer(1), tree.search(1).getData());
        assertEquals(new Integer(8), tree.search(8).getData()); assertEquals(new Integer(11), tree.search(11).getData());
        assertEquals(NIL, tree.search(5)); assertEquals(NIL, tree.search(6)); assertEquals(NIL, tree.search(-1));      
       
        assertEquals(7, tree.size());
        assertEquals(3, tree.height());
        
        assertEquals(new BSTNode.Builder<Integer>().build(), tree.search(-777));
    }
}