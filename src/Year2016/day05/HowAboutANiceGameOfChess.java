package Year2016.day05;

import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import tools.MD5Hash;
import tools.RunPuzzle;
import tools.TestCase;

public class HowAboutANiceGameOfChess extends RunPuzzle {
	final String charset = "UTF-8";

	public HowAboutANiceGameOfChess(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<String, String>(1, "abc", "18f47a30"));
		tests.add(new TestCase<String, String>(2, "abc", "05ace8e3"));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		System.out.println("\t\t\t\t" + (String)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		int count = 0;
		StringBuffer password;
		if (section == 1) {
			password = new StringBuffer();
		}
		else {
			password = new StringBuffer("--------");
		}
		
		while (password.length() < 8 || password.indexOf("-") > -1) {
			String hash = "";
			do {
				count++;
				try {
					hash = MD5Hash.getHexHash((String)input + count);
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
			} while (!hash.startsWith("00000"));
			
			if (section == 1) {
				password.append(hash.charAt(5));
			}
			else {
				switch(hash.charAt(5)) {
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
					int i = Integer.parseInt("" + hash.charAt(5));
					if (password.charAt(i) == '-') {
						password.setCharAt(i, hash.charAt(6));
					}
				}
			}
		}
		
		return password.toString();
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new HowAboutANiceGameOfChess(5, "How About a Nice Game of Chess?", "ugkcyxxp");
		puzzle.run();
	}

}
