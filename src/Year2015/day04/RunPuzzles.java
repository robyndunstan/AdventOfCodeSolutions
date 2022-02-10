package Year2015.day04;

import java.util.ArrayList;

import Year2015.tools.TestCase;

public class RunPuzzles {

	public static void main(String[] args) {
		ArrayList<TestCase<String, Integer>> testCases = createTestCases();
		System.out.println("Day 04: The Ideal Stocking Stuffer\r\tSection 1");
		int testCaseCount = 0;
		for (TestCase<String, Integer> test : testCases) {
			if (test.section == 1) {
				testCaseCount++;
				System.out.println("\t\tTest " + testCaseCount + "\tExpected: " + test.result + "\tActual: " + TheIdealStockingStuffer.getFiveInitialZeros(test.input));
			}
		}
		System.out.println("\t\tPuzzle: " + TheIdealStockingStuffer.getFiveInitialZeros(puzzleInput));
		
		System.out.println("\r\tSection 2");
		testCaseCount = 0;
		for (TestCase<String, Integer> test : testCases) {
			if (test.section == 2) {
				testCaseCount++;
				System.out.println("\t\tTest " + testCaseCount + "\tExpected: " + test.result + "\tActual: " + TheIdealStockingStuffer.getSixInitialZeros(test.input));
			}
		}
		System.out.println("\t\tPuzzle: " + TheIdealStockingStuffer.getSixInitialZeros(puzzleInput));
	}

	private static ArrayList<TestCase<String, Integer>> createTestCases() {
		ArrayList<TestCase<String, Integer>> tests = new ArrayList<TestCase<String, Integer>>();
		tests.add(new TestCase<String, Integer>(1, "abcdef", 609043));
		tests.add(new TestCase<String, Integer>(1, "pqrstuv", 1048970));
		return tests;
	}

	static String puzzleInput = "bgvyzdsv";
}
