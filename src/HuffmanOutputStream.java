import java.io.*;

/*
 * Class used to output the Huffman Tree
 */
public class HuffmanOutputStream {
	private DataOutputStream d;
	private int b; // used to build a byte
	private int count; // counts the number of bits that have been put into b
	// since the last time b was written to the file

	public HuffmanOutputStream(String filename, String tree, int totalChars) {

		try {
			d = new DataOutputStream(new FileOutputStream(filename));
			d.writeUTF(tree);
			d.writeInt(totalChars);
			b = 0;
			count = 0;
		} catch (IOException e) {
		}
	}

	public void writeBit(char bit) {
		// PRE:bit == '0' || bit == '1'
		try {
			b = 2 * b + (bit - '0');
			count++;
			if (count == 8) {
				d.writeByte(b);
				b = 0;
				count = 0;
			}
		} catch (IOException e) {
		}
	}

	public void close() {
		// write final byte (if needed)
		// close the DataOutputStream
		try {
			if (count != 0) {
				b = b << (8 - count);
				d.writeByte(b);
			}
			d.close();
		} catch (IOException e) {
		}
	}
}
