package Year2016.day12;

import java.util.ArrayList;

import Year2016.AssembunnyComputer;
import tools.Constants;
import tools.RunPuzzle;
import tools.TestCase;

public class LeonardosMonorail extends RunPuzzle {

	public LeonardosMonorail(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
		debug = false;
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<String[], Integer>(1, testInput, 42));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		System.out.println(Constants.resultIndent + (Integer)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		AssembunnyComputer computer = new AssembunnyComputer();
		computer.parseProgram((String[])input);
		if (section == 2) {
			computer.setRegister('c', 1);
		}
		computer.runProgram();
		return computer.getRegister('a');
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new LeonardosMonorail(12, "Leonardo's Monorail", puzzleInput);
		puzzle.run();
	}

	String[] testInput = {
			"cpy 41 a",
			"inc a   ",
			"inc a   ",
			"dec a   ",
			"jnz a 2 ",
			"dec a   "
	};
	
	static String[] puzzleInput = {
			"cpy 1 a  ",
			"cpy 1 b  ",
			"cpy 26 d ",
			"jnz c 2  ",
			"jnz 1 5  ",
			"cpy 7 c  ",
			"inc d    ",
			"dec c    ",
			"jnz c -2 ",
			"cpy a c  ",
			"inc a    ",
			"dec b    ",
			"jnz b -2 ",
			"cpy c b  ",
			"dec d    ",
			"jnz d -6 ",
			"cpy 18 c ",
			"cpy 11 d ",
			"inc a    ",
			"dec d    ",
			"jnz d -2 ",
			"dec c    ",
			"jnz c -5 "
	};
}
