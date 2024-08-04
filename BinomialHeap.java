import java.util.*;

public class BinomialHeap
{



	public int size;
	public HeapNode last;
	public HeapNode min;
	public int numTrees;

	public BinomialHeap() {
		this.size = 0;
		this.min = null;
		this.last = null;
		this.numTrees = 0;

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
		BinomialHeap toMeld = new BinomialHeap();
		toMeld.numTrees = 1;

		HeapItem i = new HeapItem();
		i.key = key;
		i.info = info;
		i.node = new HeapNode();
		HeapNode n = i.node;
		n.next=n;
		n.item = i;
		n.rank = 0;
		n.child = null;
		n.parent = null;

		toMeld.last = n;
		toMeld.min = n;
		toMeld.size = 1;

		this.meld(toMeld);



		return i; // should be replaced by student code
	}

	/**
	 *
	 * Delete the minimal item
	 *
	 */
	public void deleteMin(){
		if (this.size == 0 || this.size == 1){
			this.min = null;
			this.size = 0;
			this.numTrees = 0;
			this.last = null;
		}
		else if (this.numTrees == 1){

			HeapNode newLast = this.min.child;
			HeapNode newMin = this.min.child;

			HeapNode curr = this.min.child.next;
			do{
				curr.parent = null;

				if(newMin.item.key >= curr.item.key){
					newMin = curr;
				}

				curr = curr.next;

			}while (curr.rank != 0);


			this.numTrees = this.min.rank;
			this.size = this.size - 1;
			this.min = newMin;
			this.last = newLast;

		}
		else if( this.min.rank == 0){

			HeapNode newMin = this.min.next;
			HeapNode curr = this.min.next;
			while (curr != this.min){
				if (newMin.item.key >= curr.item.key){
					newMin = curr;
				}
				curr = curr.next;
			}

			this.last.next = this.min.next;
			this.size -= 1;
			this.numTrees -= 1;
			this.min = newMin;

		}
		else {

			BinomialHeap toMeld = new BinomialHeap();

			HeapNode toMeldLast = this.min.child;
			HeapNode toMeldMin = this.min.child;

			HeapNode curr = this.min.child.next;
			do{
				curr.parent = null;

				if(toMeldMin.item.key >= curr.item.key){
					toMeldMin = curr;
				}

				curr = curr.next;

			}while (curr.rank != 0);

			toMeld.last = toMeldLast;
			toMeld.min = toMeldMin;
			toMeld.size = (int)Math.pow(2,this.min.rank)-1;
			toMeld.numTrees = this.min.rank;

			HeapNode nextLast = this.min.next;
			HeapNode nextMin = this.min.next;

			curr = this.min;

			while (curr.next != this.min){

				curr = curr.next;

				if(curr.rank > nextLast.rank){
					nextLast = curr;
				}
				if(curr.item.key < nextMin.item.key){
					nextMin = curr;
				}

			}



			curr.next = this.min.next;
			this.size -= Math.pow(2,this.min.rank);
			this.last= nextLast;
			this.min = nextMin;
			this.numTrees -= 1;

			this.meld(toMeld);



		}
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

			HeapItem tempItem = item.node.parent.item;
			HeapNode tempNode = item.node;
			item.node.parent.item = item;
			item.node = item.node.parent;
			tempItem.node = tempNode;
			tempNode.item = tempItem;


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
	public void meld(BinomialHeap heap2){

		if (heap2 == null) return;
		if (heap2.size == 0){
			return;
		}
		if (this.size == 0){

			this.size = heap2.size;
			this.last = heap2.last;
			this.min = heap2.min;
			//num trees change
			this.numTrees = heap2.numTrees;
			return;
		}

		this.size+= heap2.size();
		if (heap2.min.item.key < this.min.item.key){
			this.min = heap2.min;
		}

		HeapNode currHeap1 = this.last.next;
		HeapNode prevHeap1 = this.last;
		HeapNode currHeap2 = heap2.last.next;

		for (int i = 0; i<heap2.numTrees();i++){

			HeapNode next = currHeap2.next;
			while (currHeap2.rank > currHeap1.rank){
				if (currHeap1 == this.last){
					HeapNode temp = currHeap1.next;
					currHeap1.next = currHeap2;
					heap2.last.next = temp;
					this.last = heap2.last;
					//num trees change
					this.numTrees += heap2.numTrees()-i;
					return;
				}
				prevHeap1 = currHeap1;
				currHeap1 = currHeap1.next;
			}
			currHeap2.next = currHeap2;

			if (currHeap2.rank < currHeap1.rank){
				prevHeap1.next = currHeap2;
				currHeap2.next = currHeap1;
				prevHeap1 = currHeap2;
				//num trees change
				this.numTrees++;

			}
			else if (currHeap2.rank == currHeap1.rank){

				while (currHeap2.rank == currHeap1.rank && currHeap1 != currHeap1.next){
					prevHeap1.next=currHeap1.next;
					currHeap1.next = currHeap1;
					//num trees change
					this.numTrees--;

					boolean isLast = currHeap1 == this.last;




					//three trees with same rank
					if (i+1 < heap2.numTrees && currHeap2.rank == next.rank){
						HeapNode tempNext = next.next;
						if (next.item.key > currHeap2.item.key && currHeap1.item.key > currHeap2.item.key){

							currHeap2.next = prevHeap1.next;
							prevHeap1.next = currHeap2;
							prevHeap1 = currHeap2;

							next.next = null;
							currHeap2 = next;


						}
						else if (next.item.key > currHeap1.item.key && currHeap2.item.key > currHeap1.item.key) {

							currHeap1.next = prevHeap1.next;
							prevHeap1.next = currHeap1;
							prevHeap1 = currHeap1;

							next.next = null;
							currHeap1 = next;

						}
						else {
							next.next = prevHeap1.next;
							prevHeap1.next = next;
							prevHeap1 = next;
						}

						this.numTrees++;
						i++;
						next = tempNext;

					}







					if (currHeap2.item.key >= currHeap1.item.key){
						currHeap1.connect(currHeap2);
						//del
						BinomialHeap.numOfLinkes++;
						if (currHeap2 == this.min){
							this.min = currHeap1;
						}
						if (isLast){
							this.last = currHeap1;
						}
						currHeap1 = prevHeap1.next;
						currHeap2 = currHeap2.parent;
					}
					else {
						currHeap2.connect(currHeap1);
						//del
						BinomialHeap.numOfLinkes++;
						if (isLast){
							this.last = currHeap2;
						}
						if (currHeap1 == this.min){
							this.min = currHeap2;
						}
						currHeap1 = prevHeap1.next;
					}

				}
				if (currHeap2.rank != currHeap1.rank){
					currHeap2.next = prevHeap1.next;
					prevHeap1.next = currHeap2;
					currHeap1 = currHeap2;
					//num trees change
					this.numTrees++;
				}
				else{
					if (currHeap2.item.key >= currHeap1.item.key){
						currHeap1.connect(currHeap2);
						//del
						BinomialHeap.numOfLinkes++;
						if (currHeap2 == this.min){
							this.min = currHeap1;
						}

					}
					else {
						currHeap2.connect(currHeap1);
						//del
						BinomialHeap.numOfLinkes++;


						this.min = currHeap2;
						this.last = currHeap2;
						currHeap1 = currHeap2;
						prevHeap1 = currHeap2;

					}
				}


			}


			currHeap2 = next;

		}

		return;
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
	public int numTrees(){
		return this.numTrees;
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

		//gets 2 nodes with same rank and connect node2 to node1 such that the rank of node1 increase
		private void connect(HeapNode node2){


			if (this.child != null){
				HeapNode temp2 = this.child.next;
				this.child.next = node2;
				node2.next = temp2;
			}
			else{
				node2.next = node2;
			}
			node2.parent = this;
			this.child = node2;

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
