// HuffmanCodes.java
//
// Date: 11/3/2020
//
// Author: Dakota Kallas

import java.io.*;
import java.util.*;

/*
 * This class is used to encode a text file using a Huffman Tree.
 */
public class HuffmanCodes {
	// Implements the main flow of your program
	// Add private methods and instance variables as needed

	private int[] frequencies = new int[128];	// Array that holds the frequencies of each ASCII value
	private int counter = 0;					// Counter that keeps track of the total amount of characters
	private PriorityQueue<Item> q;				// Global PriorityQueue class that sorts the characters priorities
	
	/*
	 * Constructor used to encode a text file and output it to another file
	 */
	public HuffmanCodes(String in, String out) throws IOException {
		BufferedReader input = new BufferedReader(new FileReader(in));

		findFrequency(input); // Find the frequencies of each character in the file
			
		HuffmanTree huffmanTree = buildHuff(); // Builds the final Huffman Tree

		toOutput(huffmanTree, in, out); // Create an output file with the desired info inside of it
	}
	
	/*
	 * Helper method used to generate an output of the encoded HuffmanTree representation
	 */
	private void toOutput(HuffmanTree h, String infile, String outfile) throws IOException {
		String output = h.toString();

		// Create an output stream that is used to encode each character to the output file
		HuffmanOutputStream out = new HuffmanOutputStream(outfile, output, counter);
		writeOut(infile, h.getPath(), out);
		
		out.close();
	}
	
	/*
	 * Helper method used to write bits to the output file in an encoded representation
	 */
	private void writeOut(String infile, String [] array, HuffmanOutputStream h) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(infile));
		int chr = in.read();
		
		// While there are still characters to read...
		while(chr != -1) {
			// For each char in the file, write out its corresponding bit encodings
			for(int i = 0; i < array[chr].length(); i++) {
				h.writeBit(array[chr].charAt(i));
			}
			chr = in.read();
		}
		
		// Close the streams
		h.close();
		in.close();
	}

	/*
	 * Find the frequency of each character in the input file.
	 */
	private void findFrequency(BufferedReader in) throws IOException {
		
		int chr = in.read();
		
		// While there are still characters to read, keep counting their frequencies
		while(chr != -1) {
			frequencies[chr]++;
			counter++;
			chr = in.read();
		}
		
		// Close the BufferedReader
		in.close();
	}

	/*
	 * Build a Huffman tree from the frequency data and return a final representation of the data in
	 * a Huffman Tree.
	 */
	private HuffmanTree buildHuff() {
		q = new PriorityQueue<>();
		
		// A loop that goes and creates each single HuffmanNode for each ASCII code in the file
		for(int i = 0; i < frequencies.length; i++) {
			// If the ASCII value appears at least once in the file, create a Huffman Tree object for it
			if(frequencies[i] != 0) {
				HuffmanTree t = new HuffmanTree((char)i);
				Item hTree = new Item(frequencies[i], t);
				q.add(hTree);
			}
		}
		
		// A loop that goes through the item queue and builds a final Huffman Tree based off of their priorities
		while(q.size() > 1) {
			Item x = q.poll();	// Retrieve the item with the highest priority (lowest frequency) and then remove it from the queue
			
			Item y = q.poll();	// Retrieve the item with the 2nd highest priority (lowest frequency) and then remove it from the queue

			HuffmanTree t = new HuffmanTree(x.data, (char)128, y.data);
			Item n = new Item(x.pri + y.pri, t);
			
			q.add(n);	// Add n back to the priority queue
		}
		
		Item f = q.peek();
		HuffmanTree fin = f.data;
		return fin;	// Return the final representation of the Huffman Tree
	}

	/*
	 * PriorityQueue class meant to help determine what order to build the Huffman
	 * Tree in.
	 */
	private class Item implements Comparable<Item> {
		private int pri;
		private HuffmanTree data;
		
		private Item(int p, HuffmanTree d) {
			pri = p;
			data = d;	
		}
		public int compareTo(Item x) {
			return pri - x.pri;
		}
	}
}