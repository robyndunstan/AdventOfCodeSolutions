package Year2015.day08;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import tools.RunPuzzle;
import tools.TestCase;

public class Matchsticks extends tools.RunPuzzle {
	public Matchsticks(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
	}
	
	public static void main(String[] args) {
		RunPuzzle puzzle = new Matchsticks(8, "Matchsticks", puzzleFile);
		puzzle.run();
	}
	
	private String filename;
	private int codeLength, dataLength, encodedLength;

	public void setFilename(String filename) {
		this.filename = filename;
		reset();
		processFile();
	}
	
	private void reset() {
		codeLength = 0;
		dataLength = 0;
		encodedLength = 0;
	}
	
	public void processFile() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			
			String line = br.readLine();
			do {
				codeLength += line.length();
				dataLength += getDataLength(line);
				encodedLength += getEncodedLength(line);
				line = br.readLine();
			} while (line != null);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			br.close();
		}
	}
	
	public int getAnswer(int section) {
		if (section == 1) {
			return codeLength - dataLength;
		}
		else if (section == 2) {
			return encodedLength - codeLength;
		}
		else {
			return Integer.MIN_VALUE;
		}
	}
	
	static int getDataLength(String s) {
		int l = s.length();
		
		// " at beginning and end
		l -= 2;
		
		int prevInd = 0;
		while (s.indexOf('\\', prevInd) > -1) {
			if (s.charAt(s.indexOf('\\', prevInd) + 1) == 'x') {
				l -= 3;
				prevInd = s.indexOf('\\', prevInd) + 4;
			}
			else {
				l -= 1;
				prevInd = s.indexOf('\\', prevInd) + 2;
			}
		}
		
		return l;
	}
	
	static int getEncodedLength(String s) {
		int l = s.length();
		
		// "\ at beginning and end
		l += 4;
		
		int prevInd = 0;
		while (s.indexOf('\\', prevInd) > -1) {
			if (s.charAt(s.indexOf('\\', prevInd) + 1) == 'x') {
				l += 1;
				prevInd = s.indexOf('\\', prevInd) + 4;
			}
			else {
				l += 2;
				prevInd = s.indexOf('\\', prevInd) + 2;
			}
		}
		
		return l;
	}
	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<String, Integer>(1, "src\\Year2015\\day08\\data\\test1File", 12));
		tests.add(new TestCase<String, Integer>(2, "src\\Year2015\\day08\\data\\test1File", 19));
		return tests;
	}
	@Override
	public void printResult(Object result) {
		System.out.println("\t\t\t\t" + (Integer)result);
	}
	@Override
	public Object doProcessing(int section, Object input) {
		String file = (String)input;
		setFilename(file);
		if (section == 1) {
			return getAnswer(1);
		}
		else {
			return getAnswer(2);
		}
	}
	
	private static String puzzleFile = "src\\Year2015\\day08\\data\\puzzleFile";
}
