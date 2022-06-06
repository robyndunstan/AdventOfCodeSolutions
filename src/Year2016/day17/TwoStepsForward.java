package Year2016.day17;

import java.awt.Point;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import tools.Constants;
import tools.MD5Hash;
import tools.RunPuzzle;
import tools.TestCase;

public class TwoStepsForward extends RunPuzzle {

	public TwoStepsForward(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
		debug = false;
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<String, String>(1, "ihgpwlah", "DDRRRD"));
		tests.add(new TestCase<String, String>(1, "kglvqrro", "DDUDRLRRUDRD"));
		tests.add(new TestCase<String, String>(1, "ulqzkmiv", "DRURDRUDDLLDLUURRDULRLDUUDDDRR"));
		tests.add(new TestCase<String, String>(2, "ihgpwlah", createStringLength(370)));
		tests.add(new TestCase<String, String>(2, "kglvqrro", createStringLength(492)));
		tests.add(new TestCase<String, String>(2, "ulqzkmiv", createStringLength(830)));
		return tests;
	}
	
	private String createStringLength(int length) {
		StringBuffer s = new StringBuffer();
		while (s.length() < length) {
			s.append('X');
		}
		return s.toString();
	}

	@Override
	public void printResult(Object result) {
		String path = (String)result;
		System.out.println(Constants.resultIndent + path.length() + " steps: " + path);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		String passcode = (String)input;
		ArrayList<String> paths = new ArrayList<String>();
		paths.add("00");
		String minPath = "";
		String maxPath = "";
		
		while (paths.size() > 0) {
			String testPath = paths.remove(0);
			Point p = new Point(Integer.parseInt("" + testPath.charAt(0)), Integer.parseInt("" + testPath.charAt(1)));
			String hashPath = testPath.substring(2);
			if (p.x == 3 && p.y == 3) {
				if (minPath.length() == 0 || minPath.length() > hashPath.length()) minPath = hashPath;
				if (maxPath.length() < hashPath.length()) maxPath = hashPath;
				continue;
			}
			else if (section == 1 && minPath.length() > 0 && hashPath.length() > minPath.length()) {
				continue;
			}
			try {
				String doorHash = MD5Hash.getHexHash(passcode + hashPath);
				for (int i = 0; i < 4; i++) {
					char doorChar = doorHash.charAt(i);
					if (doorChar >= 'b' && doorChar <= 'f') {
						switch(i) {
						case 0: // up
							if (p.y > 0) 
								paths.add(p.x + "" + (p.y - 1) + hashPath + "U");
							break;
						case 1: // down
							if (p.y < 3)
								paths.add(p.x + "" + (p.y + 1) + hashPath + "D");
							break;
						case 2: //left
							if (p.x > 0)
								paths.add((p.x - 1) + "" + p.y + hashPath + "L");
							break;
						case 3: // right
							if (p.x < 3)
								paths.add((p.x + 1) + "" + p.y + hashPath + "R");
							break;
						}
					}
				}
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		
		if (section == 1) return minPath;
		else return maxPath;
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new TwoStepsForward(17, "Two Steps Forward", "awrkjxxr");
		puzzle.run();
	}

}
