package adt.btree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
		int height = -1;
		
		if (!node.isEmpty()) {
			while (!node.isLeaf()) {
				height += 1;
				node.getChildren().get(0);
			}
			height += 1;
		}
		
		return height;
	}

	@SuppressWarnings("unchecked")
	@Override
	public BNode<T>[] depthLeftOrder() {
		List<BNode<T>> res = new ArrayList<BNode<T>>();
		if (!this.isEmpty())
			this.depthLeftOrder(this.root, res);
		
		BNode<T>[] array = new BNode[res.size()];
		for (int i = 0; i < res.size(); i++) {
			array[i] = res.get(i);
		}
		
		return array;
	}

	private void depthLeftOrder(BNode<T> node, List<BNode<T>> res) {
		res.add(node);
		if (!node.isLeaf()) {
			for (BNode<T> n : node.getChildren()) {
				depthLeftOrder(n, res);
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
		if (element != null) {
			//caso 1: arvore vazia
			if (this.isEmpty()) {
				this.root.addElement(element);				
			} 
			//caso 2: arvore não vazia
			else {
				BNode<T> node = this.root;
				while (!node.isLeaf()) {
					int idx = 0;
					while (idx < node.getElements().size() && element.compareTo(node.getElementAt(idx)) > 0) {
						idx ++;
					}
					
					node = node.getChildren().get(idx);
				}
				
				if (node.isFull()) {
					node.addElement(element);
					this.split(node);
				} else {
					node.addElement(element);
				}
			}
		}
	}

	private void split(BNode<T> node) {
		// resgatando indice do meio e o elemento do meio
		int middleInx = node.getElements().size() / 2;
		
		// criando nó onde seram armazenados os elementos maiores que o middle
		BNode<T> newPage = new BNode<T>(this.root.getOrder());
		
		// alocando os elementos maiores que o middle para o newPage
		for (int i = middleInx + 1; i < node.getElements().size(); i ++) {
			newPage.addElement(node.getElementAt(i));
		}
		
		// setando o parent do novo nó
		newPage.setParent(node.getParent());
		
		this.promote(node, newPage);
		
		// removendo os elementos que foram copiados
		for (T e : newPage.getElements()) {
			node.removeElement(e);
		}
	}

	private void promote(BNode<T> node, BNode<T> newPage) {
		// resgatando indice do meio e o elemento do meio
		int middleInx = node.getElements().size() / 2;
		T elementPromote = node.getElementAt(middleInx);
		
		// caso o nó seja o root
		if (node.getParent() == null) {
			BNode<T> newPageRoot = new BNode<T>(this.root.getOrder());
			newPageRoot.addElement(elementPromote);
			newPageRoot.addChild(0, node);
			newPageRoot.addChild(1, newPage);
			
			//realocando filhos
			if (node.getChildren().size() > 0) {
				newPage.addChild(0, node.getChildren().get(middleInx + 1));
				newPage.addChild(1, node.getChildren().get(middleInx + 2));
				node.removeChild(node.getChildren().get(middleInx + 2));
				node.removeChild(node.getChildren().get(middleInx + 1));
			}
			
			this.root = newPageRoot;
		}
		// caso nó não seja um root
		else {
			BNode<T> parentPromote = node.getParent();
			parentPromote.addElement(elementPromote);
			int idxPromote = parentPromote.indexOfChild(node);
			parentPromote.addChild(idxPromote + 1, newPage);
			
			// caso o parent fique cheio depois da adição do novo elemento
			if (parentPromote.getElements().size() == this.order) {
				this.split(parentPromote);
			}
		}
		
		// Remove o elemento promovido do nó
		node.removeElement(middleInx);
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
