package Year2022.day02;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import tools.RunPuzzle;
import tools.TestCase;

public class RockPaperScissors extends RunPuzzle {

	public RockPaperScissors(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<String, Integer>(1, test1File, 15));
		tests.add(new TestCase<String, Integer>(2, test1File, 12));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		System.out.println("\t\t\t\t" + (Integer)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		int score = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader((String)input));
			
			String line = br.readLine();
			do {
				Choice opp = parseChoice(line.charAt(0));
				
				Choice me;
				if (section == 1) {
					me = parseChoice(line.charAt(2));
				}
				else {
					switch(line.charAt(2)) {
					case 'X': // lose
						me = getChoice((opp.ordinal() - 1 + 3) % 3);
						break;
					case 'Y': // draw
						me = opp;
						break;
					case 'Z': // win
						me = getChoice((opp.ordinal() + 1) % 3);
						break;
					default:
						throw new Exception("Invalid outcome " + line.charAt(2));
					}
				}
				score += scoreRound(opp, me);
				line = br.readLine();
			} while (line != null);
			
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return score;
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new RockPaperScissors(2, "Rock Paper Scissors", puzzleFile);
		puzzle.run();
	}
	
	private static enum Choice {
		Rock, 
		Paper, 
		Scissors
	}
	
	private Choice parseChoice(char c) throws Exception {
		switch (c) {
		case 'A':
		case 'X':
			return Choice.Rock;
		case 'B':
		case 'Y':
			return Choice.Paper;
		case 'C':
		case 'Z':
			return Choice.Scissors;
		default:
			throw new Exception("Invalid choice " + c);
		}
	}
	
	private Choice getChoice(int i) throws Exception {
		switch (i) {
		case 0:
			return Choice.Rock;
		case 1:
			return Choice.Paper;
		case 2:
			return Choice.Scissors;
		default:
			throw new Exception("Invalid choice " + i);
		}
	}
	
	private int scoreRound(Choice opp, Choice me) {
		int score = me.ordinal() + 1;
		
		if (opp == me) { // draw
			score += 3;
		}
		else if ((me.ordinal() - opp.ordinal() + 3) % 3 == 1) { // win
			score += 6;
		}
		return score;
	}

	static String test1File = "src\\Year2022\\day02\\data\\test1File";
	static String puzzleFile = "src\\Year2022\\day02\\data\\puzzleFile";
}
