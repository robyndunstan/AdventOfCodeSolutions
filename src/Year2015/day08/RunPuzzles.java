package Year2015.day08;

import java.util.ArrayList;
import java.util.HashMap;

import Year2015.day07.SomeAssemblyRequired;
import Year2015.tools.TestCase;

public class RunPuzzles {

	public static void main(String[] args) {
		ArrayList<TestCase<String, Integer>> testCases = createTestCases();
		System.out.println("Day 08: Matchsticks\r\tSection 1");
		int testCaseCount = 0;
		Matchsticks file = new Matchsticks();
		for (TestCase<String, Integer> test : testCases) {
			if (test.section == 1) {
				testCaseCount++;
				System.out.println("\t\tTest " + testCaseCount);
				System.out.println("\t\t\tExpected: " + test.result);
				file.setFilename(test.input);
				System.out.println("\t\t\tActual: " + file.getAnswer(1));
			}
		}
		System.out.println("\t\tPuzzle: " );
		file.setFilename(puzzleFile);
		System.out.println("\t\t\t" + file.getAnswer(1));
		
		System.out.println("\r\tSection 2");
		testCaseCount = 0;
		for (TestCase<String, Integer> test : testCases) {
			if (test.section == 2) {
				testCaseCount++;
				System.out.println("\t\tTest " + testCaseCount);
				System.out.println("\t\t\tExpected: " + test.result);
				file.setFilename(test.input);
				System.out.println("\t\t\tActual: " + file.getAnswer(2));
			}
		}
		System.out.println("\t\tPuzzle: " );
		file.setFilename(puzzleFile);
		System.out.println("\t\t\t" + file.getAnswer(2));
	}

	private static ArrayList<TestCase<String, Integer>> createTestCases() {
		ArrayList<TestCase<String, Integer>> tests = new ArrayList<TestCase<String, Integer>>();
		tests.add(new TestCase<String, Integer>(1, "src\\Year2015\\day08\\data\\test1File", 12));
		tests.add(new TestCase<String, Integer>(2, "src\\\\Year2015\\\\day08\\\\data\\\\test1File", 19));
		return tests;
	}
	
	private static String puzzleFile = "src\\Year2015\\day08\\data\\puzzleFile";
}
