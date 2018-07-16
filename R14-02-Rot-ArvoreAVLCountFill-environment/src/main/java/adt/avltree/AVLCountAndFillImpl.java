package adt.avltree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import adt.bst.BSTNode;

public class AVLCountAndFillImpl<T extends Comparable<T>> extends AVLTreeImpl<T> implements AVLCountAndFill<T> {

   private int LLcounter;
   private int LRcounter;
   private int RRcounter;
   private int RLcounter;

   public AVLCountAndFillImpl() {
      this.LLcounter = 0;
      this.LRcounter = 0;
      this.RLcounter = 0;
      this.RRcounter = 0;
   }

   @Override
   public int LLcount() {
      return LLcounter;
   }

   @Override
   public int LRcount() {
      return LRcounter;
   }

   @Override
   public int RRcount() {
      return RRcounter;
   }

   @Override
   public int RLcount() {
      return RLcounter;
   }

   /*
    * 0: case LL
    * 1: case RR
    * 2: case LR
    * 3: case RL
    */
   @Override
   protected int caseReBalance(BSTNode<T> node, int balance) {
      int res = -1;
      int balanceLeft = this.calculateBalance((BSTNode<T>) node.getLeft());
      int balanceRight = this.calculateBalance((BSTNode<T>) node.getRight());

      if (balance > 1 && balanceLeft >= 0) {
         res = 0;
         this.LLcounter += 1;

      } else if (balance < -1 && balanceRight <= 0) {
         res = 1;
         this.RRcounter += 1;

      } else if (balance > 1 && balanceLeft < 0) {
         res = 2;
         this.LRcounter += 1;

      } else if (balance < -1 && balanceRight > 0) {
         res = 3;
         this.RLcounter += 1;
      }

      return res;
   }

   @Override
   public void fillWithoutRebalance(T[] array) {
	   	array = this.extractAllElements(array);
	   	Arrays.sort(array);
	   	List<T[]> res = new ArrayList<T[]>();
	   	res.add(array);
	   	fillWithout(res);
   }
   
   	private T[] partitionArray(T[] array, int i, int j) {
   		return Arrays.copyOfRange(array, i, j);
   }
   
	@SuppressWarnings("unchecked")
	private T[] extractAllElements(T[] array) {
		List<T> res = new ArrayList<T>();
		for (T element : array) {
			res.add(element);
		}
		while (!this.isEmpty()) {
			res.add(this.root.getData());
			this.remove(this.root.getData());
		}
      
		T[] extration = (T[]) new Comparable[res.size()];
		
		return res.toArray(extration);
	}

	private void fillWithout(List<T[]> partitions) {
		List<T[]> res = new ArrayList<T[]>();
		boolean ward = false;
		for (T[] array : partitions) {
			if (array.length > 0) {
				int mid = (array.length - 1) / 2;
				this.insert(array[mid]);
				res.add(this.partitionArray(array, 0, mid));
				res.add(this.partitionArray(array, mid + 1, array.length));
				ward = true;
			}
		}
		if (ward) {
			this.fillWithout(res);
		}
	}
}
