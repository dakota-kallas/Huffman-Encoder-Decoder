// HuffmanTree.java
//
// Date: 11/3/2020
//
// Author: Dakota Kallas

import java.util.*;

/*
 * A class that creates a Huffman Tree data structure. It also provides methods
 * to manipulate the data structure in various different ways.
 */
public class HuffmanTree {

	private class Node {
		private Node left;
		private char data;
		private Node right;
		private Node parent;

		private Node(Node L, char d, Node R, Node P) {
			left = L;
			data = d;
			right = R;
			parent = P;
		}
	}

	private Node root;
	private Node current;

	public HuffmanTree() {
		root = null;
		current = null;
	}

	/*
	 * Create a Binary Tree with a single node and its frequency
	 */
	public HuffmanTree(char d) {
		root = new Node(null, d, null, null);
		current = root;
	}

	/*
	 * Rebuilds the Huffman Tree based off of the entered string value.
	 */
	public HuffmanTree(String t, char nonLeaf) {
		// Create a stack and use HuffmanInputStream to read the bytes of the
		// data that are given
		Stack<HuffmanTree> s = new Stack<>();
		
		// Instance of HuffmanTree that is used to rebuild it based off of
		// the String value given as an argument
		HuffmanTree tree = new HuffmanTree();
		
		// Loop used to traverse through the argument String t and create
		// a HuffmanTree from that
		for(int i = 0; i < t.length(); i++) {
			// If the current character is not a Leaf...
			if(t.charAt(i) == nonLeaf) {
				// Pop the stack
				HuffmanTree right = s.pop();
				HuffmanTree left = s.pop();
				
				// Merge the existing trees and push it onto the stack
				tree = new HuffmanTree(left, nonLeaf, right);
				s.add(tree);
			}
			// If the current character is a Leaf...
			else {
				// Create a new HuffmanTree and push it onto the stack
				tree = new HuffmanTree(t.charAt(i));
				s.add(tree);
			}
		}
		
		// Pop the tree off of the stack and set current to equal root
		root = s.pop().root;
		current = root;
	}
	
	/*
	 * Merge both Binary Trees b1 AND b2 with a common root with data d
	 */
	public HuffmanTree(HuffmanTree b1, char d, HuffmanTree b2) {
		root = new Node(b1.root, d, b2.root, null);
		current = root;
	}

	private String[] codes = new String[128];

	/*
	 * Method used to return an array full of the ascii codes and their respective
	 * huffman codes.
	 */
	public String[] getPath() {
//		getPathHelp(root, "");
//		return codes;
		
		int freq = 128;
		
		for(int i = 0; i < freq; i++) {
			codes[i] = findPaths(root, i, "");
		}
		return codes;
	}

	/*
	 * Helper method to return a String that holds the path in the HuffmanTree to the respective ASCII code.
	 */
	private String findPaths(Node r, int code, String path) {
		// Check to see if the current Node is NULL
		if( r == null) {
			return "";
		}
		// Check if the current Node is a leaf in the Tree
		if(r.left == null && r.right == null) {
			if((int)r.data == code) {
				return path;
			}
			
			else {
				return "";
			}
		}
		// Return the path the current ASCII value
		return findPaths(r.left, code, path + "0") + findPaths(r.right, code, path + "1");
	}

	/*
	 * Change current to reference the root of the tree
	 */
	public void moveToRoot() {
		current = root;
	}

	/*
	 * PRE: the current node is not a leaf Change current to reference the left
	 * child of the current node
	 */
	public void moveToLeft() {
		current = current.left;
	}

	/*
	 * PRE: the current node is not a leaf Change current to reference the right
	 * child of the current node
	 */
	public void moveToRight() {
		current = current.right;
	}

	/*
	 * PRE: the current node is not the root Change current to reference the parent
	 * of the current node
	 */
	public void moveToParent() {
		current = current.parent;
	}

	/*
	 * Returns true if the current node is the root otherwise returns false
	 */
	public boolean atRoot() {
		if(current == root)
			return true;
		return false;
	}

	/*
	 * Returns true if current references a leaf other wise returns false
	 */
	public boolean atLeaf() {
		if(current.left == null && current.right == null)
			return true;
		return false;
	}

	/*
	 * Returns the data value in the node referenced by current
	 */
	public char current() {
		return current.data;
	}

	/*
	 * Returns an array of strings with all paths from the root to the leaves each
	 * value in the array contains a leaf value followed by a sequence of 0s and 1s.
	 * The 0s and 1s represent the path from the root to the node containing the
	 * leaf value. You should have created something like this in homework 4a
	 */
	public String[] pathsToLeaves() {

		getPathHelp(root, ""); // Call the method to get the paths for each char

		int length = 0;
		// Get the amount of different chars exist in the file
		for (int i = 0; i < codes.length; i++) {
			if (codes[i] != null)
				length++;
		}

		String[] paths = new String[length];
		int counter = 0;

		// Fill the String array with the chars and their respective paths
		for (int i = 0; i < codes.length; i++) {
			if (codes[i] != null && counter < length) {
				paths[counter] = (char) i + ": " + codes[i];
				counter++;
			}
		}
		return paths;
	}
	
	/*
	 * Helper method to return an array full of the ascii codes and their respective
	 * huffman codes. Ascii codes that are not in the Binary Tree are left null in
	 * the array.
	 */
	private void getPathHelp(Node n, String s) {
		if (n == null)
			return;
		if (n.left == null && n.right == null) {
			codes[n.data] = s;
			return;
		}
		getPathHelp(n.left, s + "0");
		getPathHelp(n.right, s + "1");
	}

	private String cur; // Represents the String of the Binary Tree in post order
	
	/*
	 * Returns a string representation of the tree using the post-order format.
	 */
	public String toString() {
		cur = "";
		postOrder(root);
		return cur;
	}
	
	/*
	 * Private recursive helper method to handle processing the Binary tree in post order format
	 */
	private String postOrder(Node r) {
		// If the data at root is null, add the empty tree String to the print string
		if(r == null) {
			return "";
		}
		
		// Perform the print in post order format (left tree, right tree, root)
		postOrder(r.left);
		postOrder(r.right);
		
		return cur = cur + r.data;
	}
}