package Year2015.day04;

import java.security.MessageDigest;
import java.util.ArrayList;

import tools.RunPuzzle;
import tools.TestCase;

public class TheIdealStockingStuffer extends tools.RunPuzzle {
	static String charset = "UTF-8";
	
	public TheIdealStockingStuffer(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new TheIdealStockingStuffer(4, "The Ideal Stocking Stuffer", puzzleInput);
		puzzle.run();
	}
	
	private ByteBuffer getMd5Hash(String input) {
		byte[] inputByte = null;
		byte[] outputByte = null;
		ByteBuffer outputBuffer = null;

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
	
	private int getFiveInitialZeros(String input) {
		int count = 0;
		ByteBuffer buffer = null;
		do {
			count++;
			buffer = getMd5Hash(input + count);
		} while (Integer.toHexString(buffer.getInt()).length() != 3);
		return count;
	}
	
	private int getSixInitialZeros(String input) {
		int count = 0;
		ByteBuffer buffer = null;
		do {
			count++;
			buffer = getMd5Hash(input + count);
		} while (Integer.toHexString(buffer.getInt()).length() != 2);
		return count;
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<String, Integer>(1, "abcdef", 609043));
		tests.add(new TestCase<String, Integer>(1, "pqrstuv", 1048970));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		System.out.println("\t\t\t\t" + (Integer)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		String i = (String)input;
		if (section == 1) {
			return getFiveInitialZeros(i);
		}
		else {
			return getSixInitialZeros(i);
		}
	}
	
	static String puzzleInput = "bgvyzdsv";
}
