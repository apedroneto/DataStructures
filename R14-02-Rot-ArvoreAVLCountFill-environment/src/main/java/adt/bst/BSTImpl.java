package adt.bst;

public class BSTImpl<T extends Comparable<T>> implements BST<T> {

	protected BSTNode<T> root;

	public BSTImpl() {
		root = new BSTNode<T>();
	}

	public BSTNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return root.isEmpty();
	}

	@Override
	public int height() {
		return this.height(this.root);
	}
	
	protected int height(BSTNode<T> node) {
		int res;
		if (node != null && !node.isEmpty()) {
			int left = this.height((BSTNode<T>) node.getLeft());
			int right = this.height((BSTNode<T>) node.getRight());
			res = Math.max(left, right) + 1;
		} else {
			res = -1;
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public BSTNode<T> search(T element) {
		BSTNode<T> res = (BSTNode<T>) new BSTNode.Builder<T>().build();
		
		if (!this.isEmpty() && element != null) {
			res = this.search(element, this.root);
		}
		
		return res;
	}

	@SuppressWarnings("unchecked")
	protected BSTNode<T> search(T element, BSTNode<T> node) {
		BSTNode<T> res = (BSTNode<T>) new BSTNode.Builder<T>().build();
		
		if (node.getData().equals(element)) {
			res = node;
			
		} else if (!node.isLeaf()) {
			
			if (node.getData().compareTo(element) > 0) {
				BSTNode<T> leftNode = (BSTNode<T>) node.getLeft();
			
				if (!leftNode.isEmpty()) {
					res = this.search(element, leftNode);
				}
				
			} else if (node.getData().compareTo(element) < 0){
				BSTNode<T> rightNode = (BSTNode<T>) node.getRight();
				
				if (!rightNode.isEmpty()) {
					res = this.search(element, rightNode);
				}
			}
		}
		
		return res;
	}

	@Override
	public void insert(T element) {
		if (element != null) {
			this.insert(element, this.root, null);
		}
	}

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
			
		}
	}

	@Override
	public BSTNode<T> maximum() {
		BSTNode<T> res = null;
		
		if (!this.isEmpty()) {
			res = maximum(this.root);
		}
		
		return res;
	}
	
	protected BSTNode<T> maximum(BSTNode<T> node){
		BSTNode<T> res = null;
		
		if (!node.getRight().isEmpty()) {
			res = maximum((BSTNode<T>) node.getRight());
		} else {
			res = node;
		}
		
		return res;
	}
	
	@Override
	public BSTNode<T> minimum() {
		BSTNode<T> res = null;
		
		if (!this.isEmpty()) {
			res = minimum(this.root);
		}
		
		return res;
	}

	protected BSTNode<T> minimum(BSTNode<T> node) {
		BSTNode<T> res = null;
		
		if (!node.getLeft().isEmpty()) {
			res = minimum((BSTNode<T>) node.getLeft());
		} else {
			res = node;
		}
		
		return res;
	}

	@Override
	public BSTNode<T> sucessor(T element) {
		BSTNode<T> res = null;
		
		if (!this.isEmpty()) {
			BSTNode<T> node = this.search(element);
			
			if (!node.isEmpty()) {
				if (!node.getRight().isEmpty() ) {
					res = this.minimum((BSTNode<T>) node.getRight());
				
				} else {
					BSTNode<T> parent = (BSTNode<T>) node.getParent();
					
					while (parent != null && node.getData().equals(parent.getRight().getData())) {
						node = parent;
						parent = (BSTNode<T>) node.getParent();
					}
					
					if (parent != null) {
						res = parent;
					}
				}
			}
		}
		
		return res;
		
	}

	@Override
	public BSTNode<T> predecessor(T element) {
		BSTNode<T> res = null;
		
		if (!this.isEmpty()) {
			BSTNode<T> node = this.search(element);
			
			if (!node.isEmpty()) {
				if (!node.getLeft().isEmpty()) {
					res = this.maximum((BSTNode<T>) node.getLeft());
				
				} else {
					BSTNode<T> parent = (BSTNode<T>) node.getParent();
					
					while (parent != null && node.getData().equals(parent.getLeft().getData())) {
						node = parent;
						parent = (BSTNode<T>) node.getParent();
					}
					
					if (parent != null) {
						res = parent;
					}
				}
			}
		}
		
		return res;
	}

	protected int countChildren(BSTNode<T> node) {
		int res = -1;
		if (!node.isEmpty()) {
			if (node.isLeaf()) {
				res = 0;
			} else if (!node.getLeft().isEmpty() && !node.getRight().isEmpty()) {
				res = 2;
			} else {
				res = 1;
			}
		}
		
		return res;
	}
	
	@Override
	public void remove(T element) {
		BSTNode<T> node = this.search(element);
		
		if (!node.isEmpty()) {
			this.remove(node);
		}
	}
	
	

	protected void remove(BSTNode<T> node) {
		if (node.isLeaf()) {
			node.setData(null);
			node.setLeft(null);
			node.setRight(null);
		
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

	@SuppressWarnings("unchecked")
	@Override
	public T[] preOrder() {
		T[] preOrder = (T[]) new Comparable[this.size()];
		if (!this.isEmpty()) {
			preOrder(this.root, preOrder, 0);
		}
		return preOrder;
	}

	protected int preOrder(BSTNode<T> node, T[] preOrder, int i) {
		preOrder[i] = node.getData();
		i++;

		if (!node.getLeft().isEmpty()) {
			i = preOrder((BSTNode<T>) node.getLeft(), preOrder, i);
		}

		if (!node.getRight().isEmpty()) {
			i = preOrder((BSTNode<T>) node.getRight(), preOrder, i);
		}

		return i;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T[] order() {
		T[] inOrder = (T[]) new Comparable[this.size()];
		if (!this.isEmpty()) {
			inOrder(this.root, inOrder, 0);
		}
		return inOrder;
	}

	protected int inOrder(BSTNode<T> node, T[] inOrder, int i) {
		if (!node.getLeft().isEmpty()) {
			i = inOrder((BSTNode<T>) node.getLeft(), inOrder, i);
		}

		inOrder[i] = node.getData();
		i++;

		if (!node.getRight().isEmpty()) {
			i = inOrder((BSTNode<T>) node.getRight(), inOrder, i);
		}

		return i;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T[] postOrder() {
		T[] posOrder = (T[]) new Comparable[this.size()];
		if (!this.isEmpty()) {
			posOrder(this.root, posOrder, 0);
		}
		return posOrder;
	}

	protected int posOrder(BSTNode<T> node, T[] posOrder, int i) {
		if (!node.getLeft().isEmpty()) {
			i = posOrder((BSTNode<T>) node.getLeft(), posOrder, i);
		}

		if (!node.getRight().isEmpty()) {
			i = posOrder((BSTNode<T>) node.getRight(), posOrder, i);
		}

		posOrder[i] = node.getData();
		i++;
		
		return i;
	}

	/**
	 * This method is already implemented using recursion. You must understand
	 * how it work and use similar idea with the other methods.
	 */
	@Override
	public int size() {
		return size(root);
	}

	private int size(BSTNode<T> node) {
		int result = 0;
		// base case means doing nothing (return 0)
		if (!node.isEmpty()) { // indusctive case
			result = 1 + size((BSTNode<T>) node.getLeft())
					+ size((BSTNode<T>) node.getRight());
		}
		return result;
	}
	
}
