package Year2015.day10;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.IOException;
import java.util.ArrayList;

import tools.RunPuzzle;
import tools.TestCase;

public class ElvesLookElvesSay extends RunPuzzle {

	public ElvesLookElvesSay(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<Input, Integer>(1, new Input("1", 5), 6));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		System.out.println("\t\t\t\t" + (Integer)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		Input in = (Input)input;
		if (section == 2) in.iterations = 50;
		
		// Make a unique copy of the input so it remains the same for section 2
		String seq = (new StringBuffer(in.sequence)).toString();
		for (int i = 1; i <= in.iterations; i++) {
			seq = read(seq);
		}
		
		return seq.length();
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new ElvesLookElvesSay(10, "Elves Look, Elves Say", new Input(puzzleInput, 40));
		puzzle.run();
	}
	
	private String read(String input) {
		BufferedReader br = new BufferedReader(new CharArrayReader(input.toCharArray()));
		StringBuffer output = new StringBuffer();
		
		char countingChar;
		try {
			countingChar = (char)br.read();
			int numOfChar = 1;
			while (countingChar != (char)(-1)) {
				char currChar = (char)br.read();
				if (currChar == countingChar) {
					numOfChar++;
				}
				else {
					output.append(numOfChar);
					output.append(countingChar);
					countingChar = currChar;
					numOfChar = 1;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return output.toString();
	}

	private static class Input {
		public String sequence;
		public int iterations;
		
		public Input(String sequence, int iterations) {
			this.sequence = sequence;
			this.iterations = iterations;
		}
	}
	
	static String puzzleInput = "3113322113";
}
