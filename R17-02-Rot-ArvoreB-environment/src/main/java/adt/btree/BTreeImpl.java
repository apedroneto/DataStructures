package adt.btree;

import java.util.LinkedList;

public class BTreeImpl<T extends Comparable<T>> implements BTree<T> {

	protected BNode<T> root;
	protected int order;

	public BTreeImpl(int order) {
		this.order = order;
		this.root = new BNode<T>(order);
	}

	@Override
	public BNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return this.root.isEmpty();
	}

	@Override
	public int height() {
		return height(this.root);
	}

	private int height(BNode<T> node) {
		int height = 0;
		
		if (node != null) {
			if (!node.isEmpty()) {
				while (node.getChildren().size() > 0) {
					height += 1;
					node = node.getChildren().get(0);
				}
			}
		} else {
			height = -1;
		}
		
		return height;
	}

	@SuppressWarnings("unchecked")
	@Override
	public BNode<T>[] depthLeftOrder() {
		BNode<T>[] res = new BNode[this.size()];
		if (!this.isEmpty())
			this.depthLeftOrder(this.root, res, 0);
		return res;
	}

	private void depthLeftOrder(BNode<T> node, BNode<T>[] res, int idx) {
		res[idx] = node;
		idx ++;
		if (node.getChildren().size() > 0) {
			for (int i = 0; i < node.getChildren().size(); i++) {
				this.depthLeftOrder(node.getChildren().get(i), res, idx);
			}
		}
	}

	@Override
	public int size() {
		int size = 0;
		if (!this.isEmpty()) {
			size = this.size(this.root);
		}
		
		return size;
	}

	private int size(BNode<T> node) {
		int size = 0;
		size += node.getElements().size();
		for (int i = 0; i < node.getChildren().size(); i++) {
			size += this.size(node.getChildren().get(i));
		}
		
		return size;
	}

	@Override
	public BNodePosition<T> search(T element) {
		BNodePosition<T> res = new BNodePosition<T>();
		if (element != null && !this.isEmpty()) {
			res = this.search(element, this.root);
		}
		
		return res;
	}
	
	private BNodePosition<T> search(T element, BNode<T> node) {
		BNodePosition<T> res = new BNodePosition<T>();

		int i = 0;
		LinkedList<T> elements = node.getElements();
		while (i < elements.size() && element.compareTo(elements.get(i)) > 1)
			i++;
		
		if (i < elements.size()) {
			if (element.compareTo(elements.get(i)) == 0){
				res = new BNodePosition<T>(node, i);
			} else {
				LinkedList<BNode<T>> children = node.getChildren();
				if (i < children.size()) {
					res = this.search(element, children.get(i));
				}
			}
		}
		
		return res;
	}

	@Override
	public void insert(T element) {
		// TODO Implement your code here
		throw new UnsupportedOperationException("Not Implemented yet!");

	}

	private void split(BNode<T> node) {
		// TODO Implement your code here
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

	private void promote(BNode<T> node) {
		// TODO Implement your code here
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

	// NAO PRECISA IMPLEMENTAR OS METODOS ABAIXO
	@Override
	public BNode<T> maximum(BNode<T> node) {
		// NAO PRECISA IMPLEMENTAR
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

	@Override
	public BNode<T> minimum(BNode<T> node) {
		// NAO PRECISA IMPLEMENTAR
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

	@Override
	public void remove(T element) {
		// NAO PRECISA IMPLEMENTAR
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

}
