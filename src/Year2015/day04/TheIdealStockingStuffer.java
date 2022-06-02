package Year2015.day04;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import Year2015.day03.PerfectlySphericalHousesInAVacuum;
import tools.Constants;
import tools.MD5Hash;
import tools.RunPuzzle;
import tools.TestCase;

public class TheIdealStockingStuffer extends tools.RunPuzzle {
	public TheIdealStockingStuffer(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new TheIdealStockingStuffer(4, "The Ideal Stocking Stuffer", puzzleInput);
		puzzle.run();
	}

	private int getFiveInitialZeros(String input) {
		int count = 0;
		String hash = "";
		do {
			count++;
			try {
				hash = MD5Hash.getHexHash(input + count);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		} while (!hash.startsWith("00000"));
		return count;
	}
	
	private int getSixInitialZeros(String input) {
		int count = 0;
		String hash = "";
		do {
			count++;
			try {
				hash = MD5Hash.getHexHash(input + count);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		} while (!hash.startsWith("000000"));
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
		System.out.println(Constants.resultIndent + (Integer)result);
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
