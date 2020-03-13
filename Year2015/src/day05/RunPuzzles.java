package day05;

import java.util.ArrayList;

import tools.TestCase;

public class RunPuzzles {

	public static void main(String[] args) {
		ArrayList<TestCase<String, Integer>> testCases = createTestCases();
		System.out.println("Day 05: Doesn't He Have Intern-Elves For This?\r\tSection 1");
		int testCaseCount = 0;
		for (TestCase<String, Integer> test : testCases) {
			if (test.section == 1) {
				testCaseCount++;
				System.out.println("\t\tTest " + testCaseCount + "\tExpected: " + test.result + "\tActual: " + (test.input));
			}
		}
		System.out.println("\t\tPuzzle: " + (puzzleInput));
		
		System.out.println("\r\tSection 2");
		testCaseCount = 0;
		for (TestCase<String, Integer> test : testCases) {
			if (test.section == 2) {
				testCaseCount++;
				System.out.println("\t\tTest " + testCaseCount + "\tExpected: " + test.result + "\tActual: " + (test.input));
			}
		}
		System.out.println("\t\tPuzzle: " + (puzzleInput));
	}

	private static ArrayList<TestCase<String, Integer>> createTestCases() {
		ArrayList<TestCase<String, Integer>> tests = new ArrayList<TestCase<String, Integer>>();
		return tests;
	}

	static String puzzleInput = "bgvyzdsv";
}
