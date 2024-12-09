package tools;

import java.io.IOException;
import java.util.ArrayList;

public abstract class RunPuzzle {
	ArrayList<TestCase> testCases;
	int dayNumber;
	String dayTitle;
	Object puzzleInput;
	protected String defaultOutputIndent = "\t\t\t\t";
	protected boolean debug = false;
	FileController logFile;
	
	public abstract ArrayList<TestCase> createTestCases();
	public abstract void printResult(Object result);
	public abstract Object doProcessing(int section, Object input);
	
	public RunPuzzle(int dayNumber, String dayTitle, Object puzzleInput) {
		this.dayNumber = dayNumber;
		this.dayTitle = dayTitle;
		this.puzzleInput = puzzleInput;
		this.logFile = new FileController();
	}

	public void setLogFile(String filename) throws IOException {
            try {
                this.logFile.closeFile();
            } catch (IOException ex) {}
		this.logFile.setFile(filename);
		this.logFile.openOutput();
	}

	public void log(String line) {
		if (this.logFile.isOpenOutput()) {
			this.logFile.writeLine(line);
		}
		System.out.println(line);
	}

	public void logDebug(String line) {
		if (debug && this.logFile.isOpenOutput()) {
			this.logFile.writeLine(line);
		}
		else if (debug) {
			System.out.println(line);
		}
	}
	
	public void run() {
		testCases = createTestCases();
		
		log("Day " + dayNumber + ": " + dayTitle);
		log("\tSection 1");
		
		int testCaseCount = 0;
		for (TestCase test : testCases) {
			if (test.section == 1) {
				log("\t\tTest " + ++testCaseCount);
				log("\t\t\tExpected: ");
				printResult(test.result);
				log("\t\t\tActual: ");
				printResult(doProcessing(1, test.input));
			}
		}
		log("\t\tPuzzle: ");
		printResult(doProcessing(1, puzzleInput));

		log("\tSection 2");
		
		testCaseCount = 0;
		for (TestCase test : testCases) {
			if (test.section == 2) {
				log("\t\tTest " + ++testCaseCount);
				log("\t\t\tExpected: ");
				printResult(test.result);
				log("\t\t\tActual: ");
				printResult(doProcessing(2, test.input));
			}
		}
		log("\t\tPuzzle: ");
		printResult(doProcessing(2, puzzleInput));
	}
}