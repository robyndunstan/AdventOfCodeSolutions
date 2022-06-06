package Year2016.day14;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import tools.Constants;
import tools.MD5Hash;
import tools.RunPuzzle;
import tools.TestCase;

public class OneTimePad extends RunPuzzle {
	HashMap<Integer, String> hashes;

	public OneTimePad(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
		hashes = new HashMap<Integer, String>();
		debug = false;
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<String, Integer>(1, "abc", 22728));
		tests.add(new TestCase<String, Integer>(2, "abc", 22551));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		System.out.println(Constants.resultIndent + (Integer)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		String salt = (String)input;
		hashes = new HashMap<Integer, String>();
		
		int keyCount = 0;
		int index = -1;
		while (keyCount < 64) {
			index++;
			try {
				String testHash = getHash(salt, index, section);
				char c = findFirstTriple(testHash);
				if (c != ' ' && hasFiveRepeatsHash(salt, index, c, section)) {
					keyCount++;
				}
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		return index;
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new OneTimePad(14, "One-Time Pad", "jlmsuwbz");
		puzzle.run();
	}

	char findFirstTriple(String hash) {
		for (int i = 0; i < hash.length() - 2; i++) {
			if (hash.charAt(i) == hash.charAt(i + 1) && hash.charAt(i) == hash.charAt(i + 2))
				return hash.charAt(i);
		}
		return ' ';
	}
	
	boolean hasFiveRepeatsHash(String salt, int index, char repeat, int section) {
		String search = "" + repeat + repeat + repeat + repeat + repeat;
		for (int i = 1; i <= 1000; i++) {
			String hash = "";
			try {
				hash = getHash(salt, index + i, section);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			if (hash.indexOf(search) > -1) return true;
		}
		return false;
	}
	
	String getHash(String salt, int index, int section) throws NoSuchAlgorithmException {
		if (hashes.containsKey(index))
			return hashes.get(index);
		else {
			String input = salt + index;
			String hash = "";
			for (int i = 0; i < (section == 1 ? 1 : 2017); i++) {
				hash = MD5Hash.getHexHash(input);
				input = hash;
			}
			hashes.put(index, hash);
			return hash;
		}
	}
}
