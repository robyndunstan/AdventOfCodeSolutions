package tools;

import java.util.ArrayList;

public abstract class RunPuzzle {
	ArrayList<TestCase> testCases;
	int dayNumber;
	String dayTitle;
	Object puzzleInput;
	
	public abstract ArrayList<TestCase> createTestCases();
	public abstract void printResult(Object result);
	public abstract Object doProcessing(int section, Object input);
	
	public RunPuzzle(int dayNumber, String dayTitle, Object puzzleInput) {
		this.dayNumber = dayNumber;
		this.dayTitle = dayTitle;
		this.puzzleInput = puzzleInput;
	}
	
	public void run() {
		testCases = createTestCases();
		
		System.out.println("Day " + dayNumber + ": " + dayTitle);
		System.out.println("\tSection 1");
		
		int testCaseCount = 0;
		for (TestCase test : testCases) {
			if (test.section == 1) {
				System.out.println("\t\tTest " + ++testCaseCount);
				System.out.println("\t\t\tExpected: ");
				printResult(test.result);
				System.out.println("\t\t\tActual: ");
				printResult(doProcessing(1, test.input));
			}
		}
		System.out.println("\t\tPuzzle: ");
		printResult(doProcessing(1, puzzleInput));

		System.out.println("\tSection 2");
		
		testCaseCount = 0;
		for (TestCase test : testCases) {
			if (test.section == 2) {
				System.out.println("\t\tTest " + ++testCaseCount);
				System.out.println("\t\t\tExpected: ");
				printResult(test.result);
				System.out.println("\t\t\tActual: ");
				printResult(doProcessing(2, test.input));
			}
		}
		System.out.println("\t\tPuzzle: ");
		printResult(doProcessing(2, puzzleInput));
	}
}
