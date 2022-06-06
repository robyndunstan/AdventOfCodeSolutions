package Year2016.day16;

import java.util.ArrayList;

import tools.Constants;
import tools.RunPuzzle;
import tools.TestCase;

public class DragonChecksum extends RunPuzzle {

	public DragonChecksum(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
		debug = false;
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<Input, String>(1, new Input("10000", 20, 0), "01100"));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		System.out.println(Constants.resultIndent + (String)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		Input i = (Input)input;
		
		StringBuffer data = new StringBuffer(i.startingData);
		int targetSize = section == 1 ? i.discSize1 : i.discSize2;
		
		// Create data
		while (data.length() < targetSize) {
			StringBuffer copy = new StringBuffer(data.toString());
			copy.reverse();
			String s = copy.toString();
			s = s.replaceAll("0", "2");
			s = s.replaceAll("1", "0");
			s = s.replaceAll("2", "1");
			data.append("0");
			data.append(s);
		}
		data = new StringBuffer(data.substring(0, targetSize));
		
		// Calculate checksum
		StringBuffer checksum;
		do {
			checksum = new StringBuffer();
			for (int j = 0; j < data.length() / 2; j++) {
				if (data.charAt(2 * j) == data.charAt(2 * j + 1)) checksum.append('1');
				else checksum.append('0');
			}
			data = checksum;
		} while (checksum.length() % 2 == 0);
		
		return checksum.toString();
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new DragonChecksum(16, "Dragon Checksum", new Input("10011111011011001", 272, 35651584));
		puzzle.run();
	}

	static class Input {
		int discSize1, discSize2;
		String startingData;
		Input(String data, int size1, int size2) {
			this.startingData = data;
			this.discSize1 = size1;
			this.discSize2 = size2;
		}
	}
}
