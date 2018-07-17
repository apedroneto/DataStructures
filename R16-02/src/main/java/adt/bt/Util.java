package adt.bt;

import adt.bst.BSTNode;

public class Util {


	/**
	 * A rotacao a esquerda em node deve subir e retornar seu filho a direita
	 * @param node
	 * @return
	 */
	public static <T extends Comparable<T>> BSTNode<T> leftRotation(BSTNode<T> node) {
		if (!node.isEmpty()) {
			BSTNode<T> pivot = (BSTNode<T>) node.getRight();
			BSTNode<T> rootLeft = (BSTNode<T>) node.getLeft();
			BSTNode<T> pivotLeft = (BSTNode<T>) pivot.getLeft();
			BSTNode<T> pivotRight = (BSTNode<T>) pivot.getRight();
			BSTNode<T> root = node;
			
			T aux = root.getData();
			root.setData(pivot.getData());
			pivot.setData(aux);
			
			root.setRight(pivotRight);
			pivotRight.setParent(root);
			
			root.setLeft(pivot);
			pivot.setLeft(rootLeft);
			pivot.setRight(pivotLeft);
		}
		
		return node;
	}

	/**
	 * A rotacao a direita em node deve subir e retornar seu filho a esquerda
	 * @param node
	 * @return
	 */
	public static <T extends Comparable<T>> BSTNode<T> rightRotation(BSTNode<T> node) {
		if (!node.isEmpty()) {
			BSTNode<T> pivot = (BSTNode<T>) node.getLeft();
			BSTNode<T> rootRight = (BSTNode<T>) node.getRight();
			BSTNode<T> pivotRight = (BSTNode<T>) pivot.getRight();
			BSTNode<T> pivotLeft = (BSTNode<T>) pivot.getLeft();
			BSTNode<T> root = node;
			
			T aux = root.getData();
			root.setData(pivot.getData());
			pivot.setData(aux);
			
			root.setLeft(pivotLeft);
			pivotLeft.setParent(root);
			
			root.setRight(pivot);
			pivot.setRight(rootRight);
			pivot.setLeft(pivotRight);
		}
		
		return node;
	}

	public static <T extends Comparable<T>> T[] makeArrayOfComparable(int size) {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) new Comparable[size];
		return array;
	}
}

