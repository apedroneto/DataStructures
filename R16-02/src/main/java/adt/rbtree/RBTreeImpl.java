package adt.rbtree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.Util;
import adt.rbtree.RBNode.Colour;

public class RBTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements RBTree<T> {

   public RBTreeImpl() {
      this.root = new RBNode<T>();
   }

   protected int blackHeight() {
	   return this.blackHeight((RBNode<T>) this.root);
   }
   
   protected int blackHeight(RBNode<T> node) {
	   int res = -1;
	   	if (node != null) {
		   	int blackHeightLeft = this.blackHeight((RBNode<T>) node.getLeft());
		   	int blackHeightRight = this.blackHeight((RBNode<T>) node.getRight());

		   	res = Math.max(blackHeightLeft, blackHeightRight);

		   	if (node.getColour() == Colour.BLACK) {
		   		res++;
		   	}
	   	}
	   	return res;
   }

   protected boolean verifyProperties() {
      boolean resp = verifyNodesColour() && verifyNILNodeColour() && verifyRootColour() && verifyChildrenOfRedNodes()
            && verifyBlackHeight();

      return resp;
   }

   /**
    * The colour of each node of a RB tree is black or red. This is guaranteed
    * by the type Colour.
    */
   private boolean verifyNodesColour() {
      return true; // already implemented
   }

   /**
    * The colour of the root must be black.
    */
   private boolean verifyRootColour() {
      return ((RBNode<T>) root).getColour() == Colour.BLACK; // already
      // implemented
   }

   /**
    * This is guaranteed by the constructor.
    */
   private boolean verifyNILNodeColour() {
      return true; // already implemented
   }

   /**
    * Verifies the property for all RED nodes: the children of a red node must
    * be BLACK.
    */
   private boolean verifyChildrenOfRedNodes() {
      return verifyChildrenOfRedNodes((RBNode<T>) this.root);
   }

   	private boolean verifyChildrenOfRedNodes(RBNode<T> node) {
		boolean res = false;
		if (!node.isEmpty()) {
			
			RBNode<T> childLeft = (RBNode<T>) node.getLeft();
			RBNode<T> childRight = (RBNode<T>) node.getRight();
			
			if (node.getColour() == Colour.RED) {
				if (childLeft.getColour() == Colour.BLACK && childRight.getColour() == Colour.BLACK) {
					res = this.verifyChildrenOfRedNodes(childLeft) && this.verifyChildrenOfRedNodes(childRight);
				}
				
			} else {
				res = this.verifyChildrenOfRedNodes(childLeft) && this.verifyChildrenOfRedNodes(childRight);
			}
			
		} else {
			res = true;
		}
		
		return res;
	}

   /**
    * Verifies the black-height property from the root.
    */
   private boolean verifyBlackHeight() {
      return verifyBlackHeight((RBNode<T>) this.root);
   }

   private boolean verifyBlackHeight(RBNode<T> node) {
      boolean res = false;
      if (node != null) {
         int blackHeightLeft = this.blackHeight((RBNode<T>) node.getLeft());
         int blackHeightRight = this.blackHeight((RBNode<T>) node.getRight());

         res = blackHeightLeft == blackHeightRight;
      }
      return res;
   }

   @Override
   public void insert(T value) {
      if (value != null) {
         this.insertRB(value, (RBNode<T>) this.root, null);
      }
   }

   protected void insertRB(T element, RBNode<T> node, RBNode<T> parent) {
      if (element != null) {
         if (node.isEmpty()) {
            node.setData(element);
            node.setLeft(new RBNode<T>());
            node.setRight(new RBNode<T>());
            node.setParent(parent);
            node.setColour(Colour.RED);

            this.fixUpCase1(node);

         } else {
            int comparator = node.getData().compareTo(element);

            if (comparator > 0) {
               this.insertRB(element, (RBNode<T>) node.getLeft(), node);
            } else if (comparator < 0) {
               this.insertRB(element, (RBNode<T>) node.getRight(), node);
            }
         }

      }
   }
   
   
   @SuppressWarnings("unchecked")
   @Override
   public RBNode<T>[] rbPreOrder() {
	   	RBNode<T>[] preOrder = new RBNode[this.size()];
	   	this.rbPreOrder((RBNode<T>) this.root, preOrder, 0);
	   	return preOrder;
   }

   private int rbPreOrder(RBNode<T> node, RBNode<T>[] preOrder, int i) {
	   	preOrder[i] = (RBNode<T>) node;
		i++;

		if (!node.getLeft().isEmpty()) {
			i = rbPreOrder((RBNode<T>) node.getLeft(), preOrder, i);
		}

		if (!node.getRight().isEmpty()) {
			i = rbPreOrder((RBNode<T>) node.getRight(), preOrder, i);
		}

		return i;
   }

// FIXUP methods
   protected void fixUpCase1(RBNode<T> node) {
	   	if (node.getParent() == null) {
	   		node.setColour(Colour.BLACK);
	   	} else {
	   		this.fixUpCase2(node);
	   	}
   }

   protected void fixUpCase2(RBNode<T> node) {
      if (((RBNode<T>) node.getParent()).getColour() != Colour.BLACK) {
         this.fixUpCase3(node);
      }
   }

   protected void fixUpCase3(RBNode<T> node) {
      RBNode<T> parent = (RBNode<T>) node.getParent();
      RBNode<T> grandParent = (RBNode<T>) parent.getParent();
      RBNode<T> uncleLeft = (RBNode<T>) grandParent.getLeft();
      RBNode<T> uncleRight = (RBNode<T>) grandParent.getRight();
      RBNode<T> uncle;
      if (parent.equals(uncleRight)) {
         uncle = uncleLeft;
      } else {
         uncle = uncleRight;
      }

      if (uncle.getColour() == Colour.RED) {
         parent.setColour(Colour.BLACK);
         uncle.setColour(Colour.BLACK);
         grandParent.setColour(Colour.RED);
         this.fixUpCase1(grandParent);
      } else {
         this.fixUpCase4(node);
      }
   }

   protected void fixUpCase4(RBNode<T> node) {
      RBNode<T> parent = (RBNode<T>) node.getParent();
      RBNode<T> brotherLeft = (RBNode<T>) parent.getLeft();
      RBNode<T> brotherRight = (RBNode<T>) parent.getRight();
      RBNode<T> grandParent = (RBNode<T>) parent.getParent();
      RBNode<T> uncleLeft = (RBNode<T>) grandParent.getLeft();
      RBNode<T> uncleRight = (RBNode<T>) grandParent.getRight();

      RBNode<T> next = null;

      if (node.equals(brotherRight) && parent.equals(uncleLeft)) {
         Util.leftRotation(parent);
         next = (RBNode<T>) node.getLeft();
      } else if (node.equals(brotherLeft) && parent.equals(uncleRight)) {
         Util.rightRotation(parent);
         next = (RBNode<T>) node.getRight();
      }

      this.fixUpCase5(next);
   }

   protected void fixUpCase5(RBNode<T> node) {
      if (node != null) {
         RBNode<T> parent = (RBNode<T>) node.getParent();
         RBNode<T> grandParent = (RBNode<T>) parent.getParent();
         RBNode<T> brotherLeft = (RBNode<T>) parent.getLeft();

         parent.setColour(Colour.BLACK);
         grandParent.setColour(Colour.RED);

         if (node.equals(brotherLeft)) {
            Util.rightRotation(grandParent);
         } else {
            Util.leftRotation(grandParent);
         }
      }
   }
}
