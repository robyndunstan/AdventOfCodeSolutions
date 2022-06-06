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
		StringBuffer hex = new StringBuffer();
		while (outputBuffer.hasRemaining()) {
			StringBuffer temp = new StringBuffer(Integer.toHexString(outputBuffer.getInt()));
			while (temp.length() < 8) temp.insert(0, '0');
			hex.append(temp.toString());
		}
		return hex.toString();
	}
}
