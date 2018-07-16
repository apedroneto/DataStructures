package adt.bt;

import adt.bst.BSTNode;

public class Util {


	/**
	 * A rotacao a esquerda em node deve subir e retornar seu filho a direita
	 * @param node
	 * @return
	 */
	public static <T extends Comparable<T>> BSTNode<T> leftRotation(BSTNode<T> node) {
		BSTNode<T> pivot = null;
		if (!node.isEmpty()) {
			pivot = (BSTNode<T>) node.getRight();

			BSTNode<T> pivotLeft = (BSTNode<T>) pivot.getLeft();
			
			node.setRight(pivotLeft);
			pivot.setLeft(node);
			pivot.setParent(node.getParent());
			node.setParent(pivot);
			
			if (pivot.getParent() != null) {
				if (pivot.getData().compareTo(pivot.parent.getData()) > 0) {
					pivot.getParent().setRight(pivot);
				} else {
					pivot.getParent().setLeft(pivot);
				}
			}
		}
		
		return pivot;
	}

	/**
	 * A rotacao a direita em node deve subir e retornar seu filho a esquerda
	 * @param node
	 * @return
	 */
	public static <T extends Comparable<T>> BSTNode<T> rightRotation(BSTNode<T> node) {
		BSTNode<T> pivot = null;
		if (!node.isEmpty()) {
			pivot = (BSTNode<T>) node.getLeft();
			BSTNode<T> pivotRight = (BSTNode<T>) pivot.getRight();

			node.setLeft(pivotRight);
			pivot.setRight(node);
			pivot.setParent(node.getParent());
			node.setParent(pivot);
			
			if (pivot.getParent() != null) {
				if (pivot.getData().compareTo(pivot.parent.getData()) > 0) {
					pivot.getParent().setRight(pivot);
				} else {
					pivot.getParent().setLeft(pivot);
				}
			}
		}
		
		return pivot;
	}

	public static <T extends Comparable<T>> T[] makeArrayOfComparable(int size) {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) new Comparable[size];
		return array;
	}
}
