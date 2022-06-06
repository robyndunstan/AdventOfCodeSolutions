package Year2016.day18;

import java.util.ArrayList;

import tools.Constants;
import tools.RunPuzzle;
import tools.TestCase;

public class LikeARogue extends RunPuzzle {

	public LikeARogue(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
		debug = false;
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<Input, Integer>(1, new Input("..^^.", 3, 0), 6));
		tests.add(new TestCase<Input, Integer>(1, new Input(".^^.^.^^^^", 10, 0), 38));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		System.out.println(Constants.resultIndent + (Integer)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		Input i = (Input)input;
		int rows = section == 1 ? i.numRows1 : i.numRows2;
		String firstRow = i.firstRow;
		
		boolean[] safeTiles = new boolean[firstRow.length()];
		int countSafe = 0;
		for (int j = 0; j < firstRow.length(); j++) {
			if (firstRow.charAt(j) == '.') {
				safeTiles[j] = true;
				countSafe++;
			}
			else safeTiles[j] = false;
		}
		
		for (int j = 1; j < rows; j++) {
			boolean[] prevRow = safeTiles;
			safeTiles = new boolean[prevRow.length];
			for (int k = 0; k < prevRow.length; k++) {
				if (k == 0) {
					safeTiles[k] = isSafe(true, prevRow[0], prevRow[1]);
				}
				else if (k == prevRow.length - 1) {
					safeTiles[k] = isSafe(prevRow[k - 1], prevRow[k], true);
				}
				else {
					safeTiles[k] = isSafe(prevRow[k - 1], prevRow[k], prevRow[k + 1]);
				}
				if (safeTiles[k]) countSafe++;
			}
		}
		
		return countSafe;
	}
	
	boolean isSafe(boolean left, boolean center, boolean right) {
		return left == right;
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new LikeARogue(18, "Like a Rogue", new Input(".^^^.^.^^^.^.......^^.^^^^.^^^^..^^^^^.^.^^^..^^.^.^^..^.^..^^...^.^^.^^^...^^.^.^^^..^^^^.....^....", 40, 400000));
		puzzle.run();
	}

	static class Input {
		String firstRow;
		int numRows1, numRows2;
		Input(String s, int i, int j) {
			firstRow = s;
			numRows1 = i;
			numRows2 = j;
		}
	}
}
