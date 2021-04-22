// HuffmanDecode.java
//
// Date: 11/3/2020
//
// Author: Dakota Kallas

import java.io.*;

/*
 * This class is used to decode a text file from a Huffman Tree representation.
 */
public class HuffmanDecode {
	
	private HuffmanTree tree; // A private instance variable used as a representation of the HuffmanTree
	
	public HuffmanDecode(String in, String out) throws IOException{
		// Create an input stream and use "in", IOException
		HuffmanInputStream input = new HuffmanInputStream(in);
		
		// Instantiate tree using .getTree, char 128
		tree = new HuffmanTree(input.getTree(), (char)128);
		
		// call helper method using input stream and out
		Decode(input, out);
	}
	
	/*
	 * Method used to decode the HuffmanInputStream and build a String representation
	 * of the decoded file.
	 */
	private void Decode(HuffmanInputStream in, String out) throws IOException {
		
		// Create a BufferedWriter to write towards the output (close at end)
		BufferedWriter write = new BufferedWriter(new FileWriter(out));
		
		int totalChars = in.getTotalChars();
	
		// While there are still characters to read...
		while(totalChars != 0) {		
			// If the tree is at a leaf, then add that leaf's character value
			// to the output String and move back to the root
			if(tree.atLeaf()) {
				totalChars -= 1;
				write.write(tree.current());
				tree.moveToRoot();
			}
			
			// If the current bit is 0 move the current position to the left
			if(in.readBit() == 0) {
				tree.moveToLeft();
			}
			// If the current bit is 1 move the current position to the right
			else {
				tree.moveToRight();
			}
		}
		
		write.close();
		in.close();
	}
}