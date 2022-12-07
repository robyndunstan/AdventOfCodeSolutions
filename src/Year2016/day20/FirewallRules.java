package Year2016.day20;

import java.util.ArrayList;

import tools.Constants;
import tools.DataFile;
import tools.RunPuzzle;
import tools.TestCase;

public class FirewallRules extends RunPuzzle {

	public FirewallRules(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
		debug = false;
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<Input, Long>(1, testInput, 3l));
		tests.add(new TestCase<Input, Long>(2, testInput, 2l));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		System.out.println(Constants.resultIndent + (Long)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		DataFile data = new DataFile(2016, 20, "puzzleInput");
		String[] puzzleBlacklist = data.getData();
		Input puzzleInput = new Input(4294967295l, puzzleBlacklist);
		RunPuzzle puzzle = new FirewallRules(20, "Firewall Rules", puzzleInput);
		puzzle.run();
	}

	static class Input {
		long max;
		String[] blacklist;
		Input(long max, String[] blacklist) {
			this.max = max;
			this.blacklist = blacklist;
		}
	}
	Input testInput = new Input(9, new String[] {"5-8", "0-2", "4-7"});
}
