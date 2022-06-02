package Year2016.day08;

import java.util.ArrayList;

import tools.Constants;
import tools.DataFile;
import tools.RunPuzzle;
import tools.TestCase;

public class TwoFactorAuthentication extends RunPuzzle {

	public TwoFactorAuthentication(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
		debug = false;
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<String[], Boolean[][]>(1, testInput, testResult));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		Boolean[][] screen = (Boolean[][])result;
		
		int count = 0;
		for (int y = 0; y < screen[0].length; y++) {
			System.out.print(Constants.resultIndent);
			for (int x = 0; x < screen.length; x++) {
				if (screen[x][y]) {
					System.out.print("#");
					count++;
				}
				else {
					System.out.print(".");
				}
			}
			System.out.println();
		}
		System.out.println(Constants.resultIndent + count);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		if (section == 1) {
			Boolean[][] screen = new Boolean[50][6];
			for (int i = 0; i < 50; i++)
				for (int j = 0; j < 6; j++)
					screen[i][j] = false;
			String[] instructions = (String[])input;
			
			for (String s : instructions) {
				if (s.startsWith("rect")) {
					int dx = Integer.parseInt(s.substring(s.indexOf(" "), s.indexOf("x")).trim());
					int dy = Integer.parseInt(s.substring(s.indexOf("x") + 1).trim());
					for (int i = 0; i < dx; i++) {
						for (int j = 0; j < dy; j++) {
							screen[i][j] = true;
						}
					}
				}
				else if (s.startsWith("rotate row")) {
					int y = Integer.parseInt(s.substring(s.indexOf("=") + 1, s.indexOf("by")).trim());
					int dx = Integer.parseInt(s.substring(s.indexOf("by") + 2).trim());
					boolean[] row = new boolean[50];
					for (int i = 0; i < 50; i++) {
						row[i] = screen[i][y];
					}
					for (int i = 0; i < 50; i++) {
						int index = (i + dx) % 50;
						screen[index][y] = row[i];
					}
				}
				else if (s.startsWith("rotate column")) {
					int x = Integer.parseInt(s.substring(s.indexOf("=") + 1, s.indexOf("by")).trim());
					int dy = Integer.parseInt(s.substring(s.indexOf("by") + 2).trim());
					boolean[] col = new boolean[6];
					for (int i = 0; i < 6; i++) {
						col[i] = screen[x][i];
					}
					for (int i = 0; i < 6; i++) {
						int index = (i + dy) % 6;
						screen[x][index] = col[i];
					}
				}
			}
			return screen;
		}
		else {
			return new Boolean[][] {{false}};
		}
	}

	public static void main(String[] args) {
		DataFile file = new DataFile(2016, 8, "puzzleInput");
		RunPuzzle puzzle = new TwoFactorAuthentication(8, "Two-Factor Authentication", file.getData());
		puzzle.run();
	}

	String[] testInput = {
		"rect 3x2",
		"rotate column x=1 by 1",
		"rotate row y=0 by 4",
		"rotate column x=1 by 1"
	};
	// Needs to be the same format as the puzzle results, but we only care that the sum of lights is 6
	Boolean[][] testResult = {
			{ true, true, true, true, true, true }
	};
}
