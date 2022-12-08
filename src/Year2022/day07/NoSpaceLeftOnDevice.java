package Year2022.day07;

import java.util.ArrayList;

import tools.RunPuzzle;
import tools.TestCase;

public class NoSpaceLeftOnDevice extends RunPuzzle {

	public NoSpaceLeftOnDevice(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<String, Long>(1, test1File, 95437l));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		System.out.println(this.defaultResultIndent + (Long)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new NoSpaceLeftOnDevice(7, "No Space Left On Device", puzzleFile);
		puzzle.run();
	}

	static String test1File = "src\\Year2022\\day07\\data\\test1File";
	static String puzzleFile = "src\\Year2022\\day07\\data\\puzzleFile";

}
