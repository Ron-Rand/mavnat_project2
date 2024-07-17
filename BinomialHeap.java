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
		item.key -= diff;
		while (item.node.parent != null && item.key < item.node.parent.item.key){
			int temp = item.key;
			item.key = item.node.parent.item.key;
			item.node.parent.item.key = temp;
			item = item.node.parent.item;
		}
		if (item.key < this.min.item.key){
			this.min = item.node;
		}

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
		if (heap2.size == 0){
			return;
		}
		this.size += heap2.size;
		if (this.min.item.key > heap2.min.item.key){
			this.min = heap2.min;
		}

		HeapNode heap1prev = this.last;
		HeapNode heap1Curr = this.last.next;
		HeapNode heap2Curr = heap2.last.next;

		boolean endFlag = false;

		for (int i = 0; i<heap2.numTrees() || !endFlag ;i++){
			while (heap1Curr.rank>= heap2Curr.rank || heap1Curr.next.rank < heap1prev.next.rank ){
				heap1prev = heap1prev.next;
				heap1Curr = heap1Curr.next;
			}
			if (heap1Curr.rank < heap2Curr.rank){
				heap1Curr.next = heap2Curr;
				heap2Curr.next = this.last.next;
				endFlag = true;
			}

			else if (heap1Curr.rank>heap2Curr.rank){
				heap1prev.next = heap2Curr;
				heap2Curr.next = heap2Curr;

				heap2Curr = heap2Curr.next;
			}
			else if (heap1Curr.rank == heap2Curr.rank){
				if (heap1Curr.item.key <= heap2Curr.item.key){
					heap1Curr.connect(heap2Curr);
					heap2Curr = heap2Curr.parent.next;
				}
				else{
					HeapNode temp = heap2Curr.next;

					heap1prev.next = heap2Curr;
					heap2Curr.next = heap1Curr.next;
					heap2Curr.connect(heap1Curr);

					heap2Curr = temp;
				}
			}
		}




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

		private void connect(HeapNode node2){
			HeapNode temp1 = this.child;
			node2.parent = this;
			this.child = node2;
			HeapNode temp2 = temp1.next;
			temp1.next = node2;
			node2.next = temp2;
			this.rank++;
			return;
		}
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
