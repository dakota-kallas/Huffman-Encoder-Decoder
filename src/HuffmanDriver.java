// HuffmanDriver.java
//
// Date: 11/3/2020
//
// Author: Dakota Kallas

import java.util.Scanner;
import java.io.*;
/*
 * This class is used as a driver to encoded and decode .txt files
 * using Huffman Trees.
 */
public class HuffmanDriver {
	
	/**
	 * Driver function to ask user what .txt file they would like encoded
	 * and decoded using the Huffman method.
	 * @param args
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException{
		System.out.println("Welcome to the Huffman Encoder/Decoder!");
		Scanner in = new Scanner(System.in);
		System.out.print("Enter the name of the txt file to be encoded (Do not incuded .txt): ");
		String encodeFile = in.nextLine();
		File f = new File(encodeFile + ".txt");
		boolean exists = f.exists();
		
		// Ensure that the file exists before encoding and decoding it
		while(!exists) {
			System.out.println("Invalid file. (Does not exist)\n");
			System.out.print("Enter the name of the txt file to be encoded (Do not incuded .txt): ");
			encodeFile = in.nextLine();
			f = new File(encodeFile + ".txt");
			exists = f.exists();
		}
		
		System.out.println("Encoding...");
		HuffmanCodes enc = new HuffmanCodes(encodeFile + ".txt", encodeFile + ".enc");
		System.out.println("Decoding...");
		HuffmanDecode dec = new HuffmanDecode(encodeFile + ".enc", encodeFile + "NEW.txt");
		System.out.println("Complete!");
		in.close();
	}

}
