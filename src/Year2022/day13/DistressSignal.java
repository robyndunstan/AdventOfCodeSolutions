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
		this.debug = true;
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
		int sumIndex = 0;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String packet1 = br.readLine();
			String packet2 = br.readLine();
			int packetIndex = 1;
			do {
				if (this.isPacketOrderCorrect(packet1, packet2)) sumIndex += packetIndex;
				
				br.readLine(); // blank line
				packet1 = br.readLine();
				packet2 = br.readLine();
				packetIndex++;
			} while (packet1 != null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sumIndex;
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new DistressSignal(13, "Distress Signal", puzzleFile);
		puzzle.run();
	}
	
	public boolean isPacketOrderCorrect(String p1, String p2) {
		IntegerList packet1 = new IntegerList();
		p1 = packet1.parseItem(p1);
		if (p1.length() > 0) System.out.println("Error parsing p1 - " + p1);
		
		IntegerList packet2 = new IntegerList();
		p2 = packet2.parseItem(p2);
		if (p2.length() > 0) System.out.println("Error parsing p2 - " + p2);
		
		int compare = packet1.compare(packet2);
		if (this.debug) {
			System.out.println("Compare");
			System.out.println("\t" + p1);
			System.out.println("\t" + p2);
			System.out.println("\tResult " + compare);
		}
		
		return compare == -1;
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
				while (Character.isDigit(item.charAt(0))) {
					numberString += item.charAt(0);
					item = item.substring(1);
				}
				value = Integer.parseInt(numberString);
				hasValue = true;
			}
			else {
				System.out.println("Problem: " + item);
			}
			
			if (item.length() > 0 && item.charAt(0) == ',') item = item.substring(1);
			return item;
		}
		
		public int compare(IntegerList il) {
			if (this.hasValue && il.hasValue) {
				if (this.value < il.value) return -1;
				else if (this.value == il.value) return 0;
				else return 1;
			}
			else if (!this.hasValue && !this.hasValue) {
				for (int i = 0; i < this.contents.size(); i++) {
					if (i >= il.contents.size()) return 1;
					int compare = this.contents.get(i).compare(il.contents.get(i));
					if (compare != 0) return compare;
				}
				return 0;
			}
			else if (this.hasValue && !il.hasValue) {
				this.convertToArray();
				return this.compare(il);
			}
			else if (!this.hasValue && il.hasValue) {
				il.convertToArray();
				return this.compare(il);
			}
			return 0;
		}
		
		private void convertToArray() {
			if (this.hasValue) {
				this.parseItem("[" + this.value + "]");
			}
		}
	}

	static String test1File = "src\\Year2022\\day13\\data\\test1File";
	static String puzzleFile = "src\\Year2022\\day13\\data\\puzzleFile";

}
