package Year2015.day04;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TheIdealStockingStuffer {
	static String charset = "UTF-8";
	
	private static ByteBuffer getMd5Hash(String input) {
		byte[] inputByte = null;
		byte[] outputByte = null;
		ByteBuffer outputBuffer = null;
		ByteBuffer outputDup = null;

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			inputByte = input.getBytes(charset);
			outputByte = md.digest(inputByte);
			outputBuffer = ByteBuffer.wrap(outputByte);
			outputDup = outputBuffer.duplicate();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		return outputBuffer;
	}
	
	public static int getFiveInitialZeros(String input) {
		int count = 0;
		ByteBuffer buffer = null;
		do {
			count++;
			if (count % 100000 == 0) {
				System.out.println("Trying " + count + "...");
			}
			buffer = getMd5Hash(input + count);
		} while (Integer.toHexString(buffer.getInt()).length() != 3);
		return count;
	}
	
	public static int getSixInitialZeros(String input) {
		int count = 0;
		ByteBuffer buffer = null;
		do {
			count++;
			if (count % 10000 == 0) {
				System.out.println("Trying " + count + "...");
			}
			buffer = getMd5Hash(input + count);
		} while (Integer.toHexString(buffer.getInt()).length() != 2);
		return count;
	}
}
