package Year2016.day06;

import java.util.ArrayList;

import tools.Constants;
import tools.DataFile;
import tools.RunPuzzle;
import tools.TestCase;

public class SignalsAndNoise extends RunPuzzle {

	public SignalsAndNoise(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<String, String>(1, "testInput", "easter"));
		tests.add(new TestCase<String, String>(2, "testInput", "advent"));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		System.out.println(Constants.resultIndent + (String)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		DataFile data = new DataFile(2016, 6, (String)input);
		String[] messages = data.getData();
		int[][] counts = new int[messages[0].length()][26];
		
		for (String m : messages) {
			for (int i = 0; i < m.length(); i++) {
				int letterIndex = m.charAt(i) - Constants.aValue;
				counts[i][letterIndex]++;
			}
		}
		
		StringBuffer message = new StringBuffer();
		for (int i = 0; i < counts.length; i++) {
			int extremeCount = counts[i][0];
			int extremeIndex = 0;
			for (int j = 1; j < 26; j++) {
				if (section == 1 && counts[i][j] > extremeCount) {
					extremeCount = counts[i][j];
					extremeIndex = j;
				}
				else if (section == 2 && counts[i][j] < extremeCount) {
					extremeCount = counts[i][j];
					extremeIndex = j;
				}
			}
			message.append((char)(Constants.aValue + extremeIndex));
		}
		
		return message.toString();
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new SignalsAndNoise(6, "Signals and Noise", "puzzleInput");
		puzzle.run();
	}

}
