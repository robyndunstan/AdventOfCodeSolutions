package Year2023.day01;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tools.FileController;
import tools.RunPuzzle;
import tools.TestCase;

public class Trebuchet extends RunPuzzle {

	public Trebuchet(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<String, Integer>(1, "src\\Year2023\\day01\\data\\test1File", 142));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		System.out.println(defaultOutputIndent + (Integer)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		String fileName = (String)input;
		FileController file = new FileController(fileName);
		
		Pattern p = Pattern.compile("\\d");
		int calibrationSum = 0;
		try {
			file.openInput();
			String line = file.readLine().trim();
			while (line != null) {
				Matcher m = p.matcher(line);
				String firstDigit = null, lastDigit = null;
				while (m.find()) {
					String thisGroup = m.group();
					if (firstDigit == null) {
						firstDigit = thisGroup;
					}
					lastDigit = thisGroup;
				}
				int thisNumber = Integer.parseInt(firstDigit + lastDigit);
				calibrationSum += thisNumber;
				
				line = file.readLine();
			}
			file.closeFile();
			return calibrationSum;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new Trebuchet(1, "Trebuchet?!", "src\\Year2023\\day01\\data\\puzzleFile");
		puzzle.run();
	}

}
