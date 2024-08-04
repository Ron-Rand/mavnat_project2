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
	public HeapItem insert(int key, String info) {
	        //create heap with one element end meld it to our heap
	        BinomialHeap toMeld = new BinomialHeap();
	        toMeld.numTrees = 1;
	
	        //create our new element with the key and info
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
	        return i;
	    }

	/**
	 *
	 * Delete the minimal item
	 *
	 */
	public void deleteMin(){
	        //first non trivial case
	        if(this.size==0||this.size==1){
	            this.min=null;
	            this.size=0;
	            this.numTrees=0;
	            this.last=null;
	            return;
	        }
	
	        //second non trivial case
	        //if minimum has no children than we don't need to use meld just find the new munimu, and connect thr right pointers.
	        if(this.min.rank == 0){
	            HeapNode curr = this.min;
	            HeapNode newMin = this.min.next;
	            do{
	                if(curr.next.item.key<newMin.item.key&&curr.next!=this.min){
	                    newMin = curr.next;
	                }
	                curr = curr.next;
	            }while(curr.next!=this.min);
	            curr.next = this.min.next;
	            this.min = newMin;
	            this.numTrees-=1;
	            this.size-=1;
	            return;
	        }
	
	        BinomialHeap toMeld = new BinomialHeap();
	
	        // find the heap that will be melded parameters
	        HeapNode toMeldLast = this.min.child;
	        int toMeldSize = (int)Math.pow(2,this.min.rank)-1;
	        int toMeldNumTrees = this.min.rank;
	
	        //find our new heap minimum
	        HeapNode curr = toMeldLast;
	        HeapNode toMeldMin = curr;
	        do{
	            curr.parent=null;
	            if(curr.item.key<toMeldMin.item.key){
	                toMeldMin = curr;
	            }
	            curr = curr.next;
	        }while (curr!=toMeldLast);
	
	        //set all parameters of the new heap
	        toMeld.size = toMeldSize;
	        toMeld.numTrees = toMeldNumTrees;
	        toMeld.min = toMeldMin;
	        toMeld.last = toMeldLast;
	
	        //third non trivial case
	        //if heap has one tree after creating our second heap it will have none so we set the parameters accordingly
	        //otherwise update our heap to be ready to meld with our second heap
	        if(this.numTrees>1) {
	
	            //correct the current heap stats
	            this.size -= (toMeldSize + 1);
	            this.numTrees -= 1;
	
	            //find new minimum after remove and new last node if changed
	            curr = this.min;
	            HeapNode newMin = curr.next;
	            HeapNode newLast = curr.next;
	            do {
	                curr = curr.next;
	                if (curr.item.key < newMin.item.key) {
	                    newMin = curr;
	                }
	                if (curr.rank > newLast.rank) {
	                    newLast = curr;
	                }
	            } while (curr.next != this.min);
	
	            //set the correct parameters
	            curr.next = this.min.next;
	            this.min = newMin;
	            this.last = newLast;
	        }
	        else {
	            this.size=0;
	            this.numTrees=0;
	            this.min = null;
	            this.last = null;
	        }
	
	        this.meld(toMeld);
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
	public void decreaseKey(HeapItem item, int diff) {
	        //decrease the item's value than sift up to the correct place
	        item.key -= diff;
	        while (item.node.parent != null && item.key < item.node.parent.item.key){
	            HeapItem tempItem = item.node.parent.item;
	            HeapNode tempNode = item.node;
	            item.node.parent.item = item;
	            item.node = item.node.parent;
	            tempItem.node = tempNode;
	            tempNode.item = tempItem;
	        }
	
	        //if needed update minimum
	        if (item.key < this.min.item.key){
	            this.min = item.node;
	        }
	
	        return;
	    }
	/**
	 *
	 * Delete the item from the heap.
	 *
	 */
	public void delete(HeapItem item) {
	        //decrese key to be the new minimum and than delete the new minimum
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

			//connect node 2 as a child of this node
          		//node 2 is from rank 0 so its is the next of this.child if exists else node2 is this nodes child
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
