package Year2022.day09;

import java.util.ArrayList;

import tools.RunPuzzle;
import tools.TestCase;

public class RopeBridge extends RunPuzzle {

	public RopeBridge(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<String, Integer>(1, test1File, 13));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		System.out.println(this.defaultResultIndent + (Integer)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new RopeBridge(9, "Rope Bridge", puzzleFile);
		puzzle.run();
	}

	static String test1File = "src\\Year2022\\day09\\data\\test1File";
	static String puzzleFile = "src\\Year2022\\day09\\data\\puzzleFile";

}
