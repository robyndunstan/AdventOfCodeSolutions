package Year2022.day10;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import tools.RunPuzzle;
import tools.TestCase;

public class CathodeRayTube extends RunPuzzle {

	public CathodeRayTube(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
		this.debug = false;
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<String, Long>(1, test1File, -720l));
		tests.add(new TestCase<String, Long>(1, test2File, 13140l));
		tests.add(new TestCase<String, Long>(2, test2File, 0l));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		System.out.println(this.defaultResultIndent + (Long)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		String filename = (String)input;
		Crt crt = new Crt();
		Cpu cpu = new Cpu(crt, this.debug);
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line = br.readLine();
			do {
				cpu.doInstruction(line);
				line = br.readLine();
			} while (line != null);
			cpu.endInstructions();
			if (section == 2) crt.printScreen();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cpu.signalStrength;
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new CathodeRayTube(10, "Cathode-Ray Tube", puzzleFile);
		puzzle.run();
	}
	
	public static class Crt {
		boolean[][] screen = new boolean[40][6];
		
		public void setPixel(int cycle, int x) {
			int xPixel = (cycle - 1) % 40;
			int yPixel = (cycle - 1) / 40;
			if (Math.abs(xPixel - x) <= 1) screen[xPixel][yPixel] = true;
		}
		
		public void printScreen() {
			for (int j = 0; j < 6; j++) {
				for (int i = 0; i < 40; i++) {
					System.out.print(screen[i][j] ? "#" : ".");
				}
				System.out.println();
			}
		}
	}
	
	public static class Cpu {
		int cycle, x;
		static int[] signalCycles = {20, 60, 100, 140, 180, 220};
		long signalStrength;
		private boolean debug;
		private Crt crt;
		
		public Cpu(Crt crt, boolean debug) {
			reset();
			this.debug = debug;
			this.crt = crt;
		}
		
		public void reset() {
			cycle = 0;
			x = 1;
			signalStrength = 0l;
		}
		
		public void doInstruction(String i) {
			if (i.startsWith("noop")) {
				cycle++;
				checkSignalStrength();
			}
			else if (i.startsWith("addx")) {
				cycle++;
				checkSignalStrength();
				cycle++;
				checkSignalStrength();
				x += Integer.parseInt(i.substring("addx ".length()));
			}
		}
		
		public void endInstructions() {
			for (int c : signalCycles) {
				if (c > cycle) {
					signalStrength += c * x;
				}
			}
		}
		
		private void checkSignalStrength() {
			for (int c : signalCycles) {
				if (c == cycle) {
					signalStrength += cycle * x;
					if (this.debug) System.out.println("Cycle " + cycle + " X " + x + " Strength " + signalStrength);
				}
			}
			crt.setPixel(cycle, x);
		}
	}

	static String test1File = "src\\Year2022\\day10\\data\\test1File";
	static String test2File = "src\\Year2022\\day10\\data\\test2File";
	static String puzzleFile = "src\\Year2022\\day10\\data\\puzzleFile";

}
