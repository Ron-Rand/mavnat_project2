import java.util.*;

public class BinomialHeap
{



	public int size;
	public HeapNode last;
	public HeapNode min;

	public BinomialHeap() {
		this.size = 0;
		this.min = null;
		this.last = null;

	}

	/**
	 *
	 * pre: key > 0
	 *
	 * Insert (key,info) into the heap and return the newly generated HeapItem.
	 *
	 */
	public HeapItem insert(int key, String info)
	{
		return null; // should be replaced by student code
	}

	/**
	 *
	 * Delete the minimal item
	 *
	 */
	public void deleteMin()
	{
		return; // should be replaced by student code

	}

	/**
	 *
	 * Return the minimal HeapItem, null if empty.
	 *
	 */
	public HeapItem findMin()
	{
		if (this.size != 0) return this.min.item;
		else return null;
	}

	/**
	 *
	 * pre: 0<diff<item.key
	 *
	 * Decrease the key of item by diff and fix the heap.
	 *
	 */
	public void decreaseKey(HeapItem item, int diff)
	{
		return; // should be replaced by student code
	}

	/**
	 *
	 * Delete the item from the heap.
	 *
	 */
	public void delete(HeapItem item)
	{
		this.decreaseKey(item,item.key-this.min.item.key+1);
		this.deleteMin();
	}

	/**
	 *
	 * Meld the heap with heap2
	 *
	 */
	public void meld(BinomialHeap heap2)
	{
		return; // should be replaced by student code
	}

	/**
	 *
	 * Return the number of elements in the heap
	 *
	 */
	public int size()
	{
		return this.size; // should be replaced by student code
	}

	/**
	 *
	 * The method returns true if and only if the heap
	 * is empty.
	 *
	 */
	public boolean empty()
	{
		return this.size == 0; // should be replaced by student code
	}

	/**
	 *
	 * Return the number of trees in the heap.
	 *
	 */
	public int numTrees()
	{
		String b = Integer.toBinaryString(size);
		int count = 0;
		for (int i = 0; i<b.length();i++){
			if (b.charAt(i) == '1'){
				count++;
			}
		}

		return count; // should be replaced by student code
	}

	/**
	 * Class implementing a node in a Binomial Heap.
	 *
	 */
	public static class HeapNode{
		public HeapItem item;
		public HeapNode child;
		public HeapNode next;
		public HeapNode parent;
		public int rank;
	}

	/**
	 * Class implementing an item in a Binomial Heap.
	 *
	 */
	public static class HeapItem{
		public HeapNode node;
		public int key;
		public String info;
	}
}
