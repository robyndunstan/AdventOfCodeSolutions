package tools;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Hash {
	final static String charset = "UTF-8";

	public MD5Hash() {
		// TODO Auto-generated constructor stub
	}

	public static String getHexHash(String input) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] inputByte = input.getBytes();
		byte[] outputByte = md.digest(inputByte);
		ByteBuffer outputBuffer = ByteBuffer.wrap(outputByte);
		StringBuffer hex = new StringBuffer(Integer.toHexString(outputBuffer.getInt()));
		while (hex.length() < 8) hex.insert(0, '0');
		return hex.toString();
	}
}
