package adt.avltree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.Util;

/**
 * 
 * Performs consistency validations within a AVL Tree instance
 * 
 * @author Claudio Campelo
 *
 * @param <T>
 */
public class AVLTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements
		AVLTree<T> {
	
	@SuppressWarnings("unchecked")
	protected void insert(T element, BSTNode<T> node, BSTNode<T> parent) {
		if (element != null) {
			
			if (node.isEmpty()) {
				node.setData(element);
				node.setLeft(new BSTNode.Builder<T>().build());
				node.setRight(new BSTNode.Builder<T>().build());
				node.setParent(parent);
				
			} else {
				int comparator = node.getData().compareTo(element);
				
				if (comparator > 0) {
					this.insert(element, (BSTNode<T>) node.getLeft(), node);
				} else if (comparator < 0) {
					this.insert(element, (BSTNode<T>) node.getRight(), node);
				}
				
			}
			
			this.rebalance(node);
		}
	}
	
	protected void remove(BSTNode<T> node) {
		if (node.isLeaf()) {
			node.setData(null);
			node.setLeft(null);
			node.setRight(null);
			
			this.rebalanceUp(node);
		
		} else if (this.countChildren(node) == 1) {
			
			if (!node.getData().equals(this.root.getData())) {
				BSTNode<T> parent = (BSTNode<T>) node.getParent();
				
				if (node.getData().equals(node.getParent().getLeft().getData())) {
					
					if (!node.getLeft().isEmpty()) {
						BSTNode<T> left = (BSTNode<T>) node.getLeft();
						left.setParent(parent);
						parent.setLeft(left);
						
					} else {
						BSTNode<T> right = (BSTNode<T>) node.getRight();
						right.setParent(parent);
						parent.setLeft(right);
					}
				
				} else {
					
					if (!node.getLeft().isEmpty()) {
						BSTNode<T> left = (BSTNode<T>) node.getLeft();
						left.setParent(parent);
						parent.setRight(left);
						
					} else {
						BSTNode<T> right = (BSTNode<T>) node.getRight();
						right.setParent(parent);
						parent.setRight(right);
					}
				}
			} else {
				
				if (!this.root.getLeft().isEmpty()) {
					this.root = (BSTNode<T>) this.root.getLeft();
				} else {
					this.root = (BSTNode<T>) this.root.getRight();
				}
				this.root.setParent(null);
			}
			
			
		} else {
			BSTNode<T> sucessor = this.sucessor(node.getData());
			if (sucessor == null || sucessor.getData().equals(node.getData())) {
				sucessor = this.predecessor(node.getData());
			}
			T element = sucessor.getData();
			remove(sucessor);
 			node.setData(element);
		}
	}

	// AUXILIARY
	// Although there is no verification, this method will only be 
	// called when the input is valid.
	protected int calculateBalance(BSTNode<T> node) {
		int heightRightTree = this.height((BSTNode<T>) node.getRight());
		int heightLeftTree = this.height((BSTNode<T>) node.getLeft());
		
		return heightLeftTree - heightRightTree;
	}
	
	/*
	 * 0: case LL
	 * 1: case RR
	 * 2: case LR
	 * 3: case RL
	 */
	protected int caseReBalance(BSTNode<T> node, int balance) {
		int res = -1;
		int balanceLeft = this.calculateBalance((BSTNode<T>) node.getLeft());
		int balanceRight = this.calculateBalance((BSTNode<T>) node.getRight());
		
		if (balance > 1 && balanceLeft >= 0) {
			res = 0;
		} else if (balance < -1 && balanceRight <= 0) {
			res = 1;
		} else if (balance > 1 && balanceLeft < 0) {
			res = 2;
		} else if (balance < -1 && balanceRight > 0) {
			res = 3;
		}
		
		return res;
	}
	
	protected void rotateLeft(BSTNode<T> node) {
		BSTNode<T> noDireito = (BSTNode<T>) node.getRight();

		node.setRight(noDireito.getLeft());

		noDireito.getLeft().setParent(node);
		noDireito.setLeft(node);
		noDireito.setParent(node.getParent());
		node.setParent(noDireito);

		if (node != this.getRoot()) {
			if (noDireito.getParent().getLeft() == node) {
				noDireito.getParent().setLeft(noDireito);
			} else {
				noDireito.getParent().setRight(noDireito);
			}
		} else {
				this.root = (BSTNode<T>) noDireito;
		}
	}
	
	protected void rotateRight(BSTNode<T> node) {
		BSTNode<T> noEsquerdo = (BSTNode<T>) node.getLeft();

		node.setLeft(noEsquerdo.getRight());

		noEsquerdo.getRight().setParent(node);
		noEsquerdo.setRight(node);
		noEsquerdo.setParent(node.getParent());
		node.setParent(noEsquerdo);

		if (node != this.getRoot()) {
			if (noEsquerdo.getParent().getLeft() == node) {
				noEsquerdo.getParent().setLeft(noEsquerdo);
			} else {
				noEsquerdo.getParent().setRight(noEsquerdo);
			}
		} else {
			this.root = (BSTNode<T>) noEsquerdo;
		}
	}
	
	// AUXILIARY
	protected void rebalance(BSTNode<T> node) {
		if (node != null && !node.isEmpty()) {
			int balance = this.calculateBalance(node);
			if (Math.abs(balance) > 1) {
				int caseBalance = this.caseReBalance(node, balance);
				switch(caseBalance) {
				case 0:
					this.rotateRight(node);
					break;
				case 1:
					this.rotateLeft(node);
					break;
				case 2:
					this.rotateLeft((BSTNode<T>) node.getLeft());
					this.rotateRight(node);
					break;
				case 3:
					this.rotateRight((BSTNode<T>) node.getRight());
					this.rotateLeft(node);
					break;
				}
			}
		}
	}

	// AUXILIARY
	protected void rebalanceUp(BSTNode<T> node) {
		BSTNode<T> parent = (BSTNode<T>) node.getParent();
		while (parent != null) {
			this.rebalance(parent);
			parent = (BSTNode<T>) parent.getParent();
		}
	}
}
