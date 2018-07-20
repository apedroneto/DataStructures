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
		//verificar elemento a ser inserido (se é valido e se ainda não existe na arvore)
		if (element != null && this.search(element) == null) {
			//insersão para a arvore vazia
			if (this.isEmpty()) {
				this.root.addElement(element);
			}
			//insersão para a arvore não vazia
			else {
				//encontra o nó onde o elemento pode ser adicionado e o adiciona (sempre um nó folha)
				BNode<T> nodeInsertion = nodeInsertion(element, this.root);
				//verifica overflow da página
				if (nodeInsertion.getOrder() <= nodeInsertion.getElements().size()) {
					this.split(nodeInsertion);
				}
			}
		}
	}
	
	private BNode<T> nodeInsertion(T element, BNode<T> node) {
		BNode<T> res = node;
		
		if (!node.isLeaf()) {
			
			int i = 0;
			LinkedList<T> elements = node.getElements();
			while (i < elements.size() && element.compareTo(elements.get(i)) > 1)
				i++;
			
			res = this.nodeInsertion(element, node.getChildren().get(i));
			
		} else {
			node.addElement(element);
		}
		
		return res;
	}

	private void split(BNode<T> node) {
		int midIndex = node.getElements().size() / 2;
		T element = node.getElementAt(midIndex);
		
		this.promote(node);
		
		BNode<T> newPage = new BNode<T>(node.getOrder());
		
		LinkedList<T> elements = node.getElements();
		for (int i = midIndex + 1; i < elements.size(); i++) {
			newPage.addElement(elements.remove(i));
		}
		
		node.removeElement(midIndex);
		
		BNode<T> parent;
		if (node.getParent() != null) {
			newPage.setParent(node.getParent());
			
			parent = node.getParent();
			elements = parent.getElements();
		} else {
			newPage.setParent(this.root);
			
			elements = this.root.getElements();
			parent = this.root;
		}
		
		if (elements.get(0).compareTo(element) > 0) {
			parent.addChild(0, newPage);
		} else {
			parent.addChild(parent.getChildren().size(), newPage);
		}
	}

	private void promote(BNode<T> node) {
		int midIndex = node.getElements().size() / 2;
		T element = node.getElementAt(midIndex);
		
		if (node.getParent() != null) {
			node.getParent().addElement(element);
		} else {
			BNode<T> newPage = new BNode<T>(node.getOrder());
			newPage.addElement(element);
			this.root = newPage;
		}
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
