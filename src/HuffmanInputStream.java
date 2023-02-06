import java.io.*;

/*
 * Class used to gather the Huffman Input Stream
 */
public class HuffmanInputStream {
	private String tree;
	private int totalChars;
	private DataInputStream d;
	private int count;
	private int bits[];

	public HuffmanInputStream(String filename) {

		try {
			d = new DataInputStream(new FileInputStream(filename));
			tree = d.readUTF();
			totalChars = d.readInt();
			count = 8;
			bits = new int[8];
		} catch (IOException e) {
		}
	}

	public int readBit() {
		// returns the next bit is the file
		// the value returmed will be either a 0 or a 1
		int b;
		int bit = 0;
		try {
			if (count == 8) {
				b = d.readUnsignedByte();
				for (int i = 7; i >= 0; i--) {
					bits[i] = b % 2;
					b = b / 2;
				}
				count = 0;
			}
			bit = bits[count];
			count++;
		} catch (IOException e) {
		}
		return bit;
	}

	public String getTree() {
		return tree;
	}

	public int getTotalChars() {
		// return the character count read from the file
		return totalChars;
	}

	public void close() throws IOException {
		// close the DataInputStream
		d.close();
	}
}