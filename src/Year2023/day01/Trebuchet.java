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
		debug = true;
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<String, Integer>(1, "src\\Year2023\\day01\\data\\test1File", 142));
		tests.add(new TestCase<String, Integer>(2, "src\\Year2023\\day01\\data\\test2File", 281));
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
		
		Pattern p;
		if (section == 1) {
			p = Pattern.compile("\\d");
		}
		else {
			p = Pattern.compile("(\\d|(one)|(two)|(three)|(four)|(five)|(six)|(seven)|(eight)|(nine))");
		}
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
				if (section == 2) {
					firstDigit = convertWordToNumber(firstDigit);
					lastDigit = convertWordToNumber(lastDigit);
				}
				
				int thisNumber = Integer.parseInt(firstDigit + lastDigit);
				calibrationSum += thisNumber;
				
				if (debug && section == 2) System.out.println(line + " " + firstDigit + " " + lastDigit + " " + calibrationSum);
				
				line = file.readLine();
			}
			file.closeFile();
			return calibrationSum;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static String convertWordToNumber(String input) {
		switch(input) {
		case "zero":
			return "0";
		case "one":
			return "1";
		case "two":
			return "2";
		case "three":
			return "3";
		case "four":
			return "4";
		case "five":
			return "5";
		case "six":
			return "6";
		case "seven":
			return "7";
		case "eight":
			return "8";
		case "nine":
			return "9";
		default:
			return input;
		}
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new Trebuchet(1, "Trebuchet?!", "src\\Year2023\\day01\\data\\puzzleFile");
		puzzle.run();
	}

}
