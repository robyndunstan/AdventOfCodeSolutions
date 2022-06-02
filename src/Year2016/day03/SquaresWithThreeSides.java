package Year2016.day03;

import java.util.ArrayList;

import tools.Constants;
import tools.DataFile;
import tools.RunPuzzle;
import tools.TestCase;

public class SquaresWithThreeSides extends RunPuzzle {
	public SquaresWithThreeSides(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
		debug = false;
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<String[], Integer>(1, test1Input, 0));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		System.out.println(Constants.resultIndent + (Integer)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		String[] data = (String[])input;
		int valid = 0;
		int[][] sides = new int[3][3];
		for (int i = 0; i < data.length; i++) {
			String[] lengths = data[i].trim().split("\s+");
			if (section == 1) {
				if (debug) System.out.println(lengths[0] + "," + lengths[1] + "," + lengths[2]);
				for (int j = 0; j < 3; j++) sides[j][0] = Integer.parseInt(lengths[j]);
				int maxSide = Math.max(sides[0][0], Math.max(sides[1][0], sides[2][0]));
				if (sides[0][0] + sides[1][0] + sides[2][0] - maxSide > maxSide) valid++;
			}
			else {
				int row = i % 3;
				for (int j = 0; j < 3; j++) {
					sides[row][j] = Integer.parseInt(lengths[j]);
				}
				if (row == 2) {
					for (int col = 0; col < 3; col++) {
						if (debug) System.out.println("Checking triangle " + sides[0][col] + ", " + sides[1][col] + ", " + sides[2][col]);
						int maxSide = Math.max(sides[0][col], Math.max(sides[1][col], sides[2][col]));
						if (sides[0][col] + sides[1][col] + sides[2][col] - maxSide > maxSide) valid++;
					}
				}
			}
		}
		return valid;
	}

	public static void main(String[] args) {
		DataFile data = new DataFile(2016, 3, puzzleFile);
		RunPuzzle puzzle = new SquaresWithThreeSides(3, "Squares With Three Sides", data.getData());
		puzzle.run();
	}

	String[] test1Input = { "5 10 25" };
	static String puzzleFile = "puzzleInput";
}
