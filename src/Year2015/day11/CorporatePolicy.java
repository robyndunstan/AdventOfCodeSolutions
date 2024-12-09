package Year2015.day11;

import java.util.ArrayList;

import tools.RunPuzzle;
import tools.TestCase;

public class CorporatePolicy extends RunPuzzle {
	static final int aVal = 'a';
	static final int zVal = 'z';

	public CorporatePolicy(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<String, String>(1, "abcdefgh", "abcdffaa"));
		tests.add(new TestCase<String, String>(1, "ghijklmn", "ghjaabcc"));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		log("\t\t\t\t" + (String)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		String oldPassword = (String)input;
		String newPassword = getNewPassword(oldPassword);
		while (!isValid(newPassword) && !newPassword.equals(oldPassword)) {
			newPassword = getNewPassword(newPassword);
		}
		if (section == 1) {
			return newPassword;
		}
		else {
			oldPassword = newPassword;
			newPassword = getNewPassword(oldPassword);
			while (!isValid(newPassword) && !newPassword.equals(oldPassword)) {
				newPassword = getNewPassword(newPassword);
			}
			return newPassword;
		}
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new CorporatePolicy(11, "Corporate Policy", puzzleInput);
		puzzle.run();
	}
	
	private String getNewPassword(String oldPw) {
		return iterateChar(oldPw, 7);
	}
	
	private String iterateChar(String pw, int i) {
		StringBuffer newPw = new StringBuffer(pw);
		int newChar = newPw.charAt(i) + 1;
		if (newChar > zVal) {
			newChar = aVal;
			newPw.setCharAt(i, (char)newChar);
			if (i > 0) {
				newPw = new StringBuffer(iterateChar(newPw.toString(), i - 1));
			}
		}
		else {
			newPw.setCharAt(i, (char)newChar);
		}
		return newPw.toString();
	}
	
	private boolean isValid(String pw) {
		int prevChar = -1;
		int doublePrev = -1;
		boolean hasStraight = false;
		boolean hasForbidden = false;
		int numDoubles = 0;
		int indDouble = -2;
		
		for (int i = 0; i < pw.length(); i++) {
			char c = pw.charAt(i);
			int cVal = c;
			if (cVal == prevChar + 1 && cVal == doublePrev + 2) {
				hasStraight = true;
			}
			if (c == 'i' || c == 'o' || c== 'l') {
				hasForbidden = true;
			}
			if (cVal == prevChar && i > indDouble + 1) {
				indDouble = i;
				numDoubles++;
			}
			doublePrev = prevChar;
			prevChar = cVal;
		}
		
		if (hasStraight && !hasForbidden && numDoubles >= 2) {
			return true;
		}
		else {
			return false;
		}
	}

	static String puzzleInput = "cqjxjnds";
}
