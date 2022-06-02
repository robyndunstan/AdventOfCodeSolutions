package Year2016.day04;

import java.util.ArrayList;

import tools.Constants;
import tools.DataFile;
import tools.RunPuzzle;
import tools.TestCase;

public class SecurityThroughObscurity extends RunPuzzle {
	boolean debug = false;

	public SecurityThroughObscurity(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<String[], Integer>(1, test1Input, 1514));
		tests.add(new TestCase<String[], Integer>(1, test2Input, 343));
		tests.add(new TestCase<String[], Integer>(2, test2Input, -1));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		System.out.println("\t\t\t\t" + (Integer)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		ArrayList<Room> rooms = new ArrayList<Room>();
		for (String s : (String[])input) {
			rooms.add(new Room(s));
		}
		
		if (section == 1) {
			int sum = 0;
			for (Room r : rooms) {
				if (r.isValidChecksum()) sum+= r.sectionId;
			}
			return sum;
		}
		else {
			for (Room r : rooms) {
				if (r.isValidChecksum()) {
					String name = r.decryptName();
					if (debug) System.out.println(r.sectionId + ": " + name);
					if (name.indexOf("north") > -1 && name.indexOf("pole") > -1) return r.sectionId;
				}
			}
			return -1;
		}
	}

	public static void main(String[] args) {
		DataFile data = new DataFile(2016, 4, puzzleFile);
		RunPuzzle puzzle = new SecurityThroughObscurity(4, "Security Through Obscurity", data.getData());
		puzzle.run();
	}
	
	class Room {
		String encryptedName, checksum;
		int sectionId;
		
		public Room(String input) {
			int startIndex = input.indexOf("[");
			int endIndex = input.indexOf("]");
			checksum = input.substring(startIndex + 1, endIndex);
			input = input.substring(0, startIndex);
			startIndex = input.lastIndexOf("-");
			sectionId = Integer.parseInt(input.substring(startIndex + 1));
			encryptedName = input.substring(0, startIndex);
		}
		
		public boolean isValidChecksum() {
			int[] letterCount = new int[26];
			int maxCount = 0;
			
			for (char c : encryptedName.toCharArray()) {
				if (c != '-') {
					int index = ((int)c) % Constants.aValue;
					letterCount[index]++;
					maxCount = Math.max(maxCount, letterCount[index]);
				}
			}
			
			StringBuffer cs = new StringBuffer();
			int count = maxCount;
			while (cs.length() < 5) {
				for (int i = 0; i < 26; i++) {
					if (letterCount[i] == count) {
						cs.append((char)(Constants.aValue + i));
					}
				}
				count--;
			}
			String testChecksum = cs.substring(0, 5);
			
			return testChecksum.equals(checksum);
		}
		
		public String decryptName() {
			int asciiA = (int)'a';
			StringBuffer name = new StringBuffer();
			
			for (char c : encryptedName.toCharArray()) {
				if (c == '-') name.append(' ');
				else {
					int cIndex = (int)c - asciiA;
					int newIndex = (cIndex + sectionId) % 26;
					char newLetter = (char)(Constants.aValue + newIndex);
					name.append(newLetter);
				}
			}
			
			return name.toString();
		}
	}

	static String puzzleFile = "puzzleInput";
	String[] test1Input = {
			"aaaaa-bbb-z-y-x-123[abxyz]",
			"a-b-c-d-e-f-g-h-987[abcde]",
			"not-a-real-room-404[oarel]",
			"totally-real-room-200[decoy]"
	};
	String[] test2Input = {
			"qzmt-zixmtkozy-ivhz-343[zimth]"
	};
}
