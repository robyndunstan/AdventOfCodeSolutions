package Year2022.day13;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import tools.RunPuzzle;
import tools.TestCase;

public class DistressSignal extends RunPuzzle {

	public DistressSignal(int dayNumber, String dayTitle, Object puzzleInput) {
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
		String filename = (String)input;
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String packet1 = br.readLine();
			String packet2 = br.readLine();
			int packetIndex = 1;
			do {
				
				br.readLine(); // blank line
				packet1 = br.readLine();
				packet2 = br.readLine();
				packetIndex++;
			} while (packet1 != null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new DistressSignal(13, "Distress Signal", puzzleFile);
		puzzle.run();
	}
	
	public boolean isPacketOrderCorrect(String p1, String p2) {
		return false;
	}
	
	static class IntegerList {
		int value;
		boolean hasValue;
		ArrayList<IntegerList> contents;
		
		public String parseItem(String item) {
			if (item.charAt(0) == '[') {
				item = item.substring(1);
				hasValue = false;
				contents = new ArrayList<IntegerList>();
				
				while (item.length() > 0 && item.charAt(0) != ']') {
					IntegerList newItem = new IntegerList();
					item = newItem.parseItem(item);
					contents.add(newItem);
				}
				
				if (item.charAt(0) == ']') item = item.substring(1);
			}
			else if (Character.isDigit(item.charAt(0))) {
				String numberString = "";
				// parse number
			}
			
			if (item.charAt(0) == ',') item = item.substring(1);
			return item;
		}
	}

	static String test1File = "src\\Year2022\\day13\\data\\test1File";
	static String puzzleFile = "src\\Year2022\\day13\\data\\puzzleFile";

}
