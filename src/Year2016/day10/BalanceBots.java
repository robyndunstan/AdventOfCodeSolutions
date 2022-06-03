package Year2016.day10;

import java.util.ArrayList;

import tools.Constants;
import tools.DataFile;
import tools.RunPuzzle;
import tools.TestCase;

public class BalanceBots extends RunPuzzle {

	public BalanceBots(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
		debug = false;
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<Input, Integer>(1, testInput, 2));
		tests.add(new TestCase<Input, Integer>(2, testInput, 30));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		System.out.println(Constants.resultIndent + (Integer)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		Input details = (Input)input;
		DataFile data = new DataFile(2016, 10, details.filename);
		String[] instructions = data.getData();
		
		ArrayList<ChipHolder> bots = new ArrayList<ChipHolder>();
		ArrayList<ChipHolder> outputs = new ArrayList<ChipHolder>();
		for (String i : instructions) {
			i = i.trim();
			if (i.startsWith("value")) {
				// value X goes to bot Y
				int startIndex = "value".length();
				int endIndex = i.indexOf("goes");
				int chipValue = Integer.parseInt(i.substring(startIndex, endIndex).trim());
				
				startIndex = i.lastIndexOf(" ");
				int botId = Integer.parseInt(i.substring(startIndex).trim());
				
				Bot b = (Bot)findTarget(botId, bots);
				if (b == null) {
					b = new Bot(botId);
					bots.add(b);
				}
				b.takeChip(chipValue);
			}
			else if (i.startsWith("bot")) {
				// bot X gives low to bot/output Y and high to bot/output Z
				int startIndex = "bot".length();
				int endIndex = i.indexOf("gives");
				int thisBotId = Integer.parseInt(i.substring(startIndex, endIndex).trim());
				
				Bot b = (Bot)findTarget(thisBotId, bots);
				if (b == null) {
					b = new Bot(thisBotId);
					bots.add(b);
				}
				
				if (i.indexOf("low to bot") > -1) {
					startIndex = i.indexOf("low to bot") + "low to bot".length();
					endIndex = i.indexOf("and");
					int lowBotId = Integer.parseInt(i.substring(startIndex, endIndex).trim());
					
					b.lowTarget = (Bot)findTarget(lowBotId, bots);
					if (b.lowTarget == null) {
						b.lowTarget = new Bot(lowBotId);
						bots.add(b.lowTarget);
					}
				}
				else {
					startIndex = i.indexOf("low to output") + "low to output".length();
					endIndex = i.indexOf("and");
					int lowBinId = Integer.parseInt(i.substring(startIndex, endIndex).trim());
					
					b.lowTarget = (OutputBin)findTarget(lowBinId, outputs);
					if (b.lowTarget == null) {
						b.lowTarget = new OutputBin(lowBinId);
						outputs.add(b.lowTarget);
					}
				}
				
				startIndex = i.lastIndexOf(" ");
				if (i.indexOf("high to bot") > -1) {
					int highBotId = Integer.parseInt(i.substring(startIndex).trim());
					
					b.highTarget = (Bot)findTarget(highBotId, bots);
					if (b.highTarget == null) {
						b.highTarget = new Bot(highBotId);
						bots.add(b.highTarget);
					}
				}
				else {
					int highBinId = Integer.parseInt(i.substring(startIndex).trim());
					
					b.highTarget = (OutputBin)findTarget(highBinId, outputs);
					if (b.highTarget == null) {
						b.highTarget = new OutputBin(highBinId);
						outputs.add(b.highTarget);
					}
				}
			}
		}
		if (debug) System.out.println("Created " + bots.size() + " bots and " + outputs.size() + " output bins");
		
		while (true) {
			// Check if we have an answer
			if (section == 1) {
				for (ChipHolder c : bots) {
					Bot b = (Bot)c;
					if (b.chips.size() == 2
							&& (b.chips.get(0) == details.chip1 || b.chips.get(1) == details.chip1)
							&& (b.chips.get(0) == details.chip2 || b.chips.get(1) == details.chip2)) {
						return b.id;
					}
				}
			}
			else {
				OutputBin o0 = (OutputBin)findTarget(0, outputs);
				OutputBin o1 = (OutputBin)findTarget(1, outputs);
				OutputBin o2 = (OutputBin)findTarget(2, outputs);
				if (o0.chips.size() > 0 && o1.chips.size() > 0 && o2.chips.size() > 0) {
					return o0.chips.get(0) * o1.chips.get(0) * o2.chips.get(0);
				}
			}
			
			// Distribute chips
			for (ChipHolder c : bots) {
				Bot b = (Bot)c;
				b.distributeChips();
				
				if (section == 1 && b.lowTarget.chips.size() == 2
						&& (b.lowTarget.chips.get(0) == details.chip1 || b.lowTarget.chips.get(1) == details.chip1)
						&& (b.lowTarget.chips.get(0) == details.chip2 || b.lowTarget.chips.get(1) == details.chip2)) {
					return b.lowTarget.id;
				}
				else if (section == 1 && b.highTarget.chips.size() == 2
						&& (b.highTarget.chips.get(0) == details.chip1 || b.highTarget.chips.get(1) == details.chip1)
						&& (b.highTarget.chips.get(0) == details.chip2 || b.highTarget.chips.get(1) == details.chip2)) {
					return b.highTarget.id;
				}
			}
		}
	}
	
	ChipHolder findTarget(int id, ArrayList<ChipHolder> list) {
		for (ChipHolder c : list) {
			if (c.id == id) {
				return c;
			}
		}
		return null;
	}
	
	abstract class ChipHolder {
		int id;
		ArrayList<Integer> chips;
		
		public ChipHolder() {
			chips = new ArrayList<Integer>();
		}
		public ChipHolder(int id) {
			this();
			this.id = id;
		}
		public void takeChip (Integer c) {
			chips.add(c);
		}
	}
	class Bot extends ChipHolder {
		ChipHolder lowTarget, highTarget;
		Bot () {
			super();
		}
		Bot (int id) {
			super(id);
		}
		public void distributeChips() {
			if (this.chips.size() == 2) {
				if (chips.get(0) < chips.get(1)) {
					lowTarget.takeChip(chips.get(0));
					highTarget.takeChip(chips.get(1));
				}
				else {
					highTarget.takeChip(chips.get(0));
					lowTarget.takeChip(chips.get(1));
				}
				chips = new ArrayList<Integer>();
			}
		}
		@Override
		public void takeChip(Integer c) {
			super.takeChip(c);
			if (debug) System.out.println("Bot " + this.id + " takes chip " + c);
		}
	}
	class OutputBin extends ChipHolder {
		OutputBin() {
			super();
		}
		OutputBin(int id) {
			super(id);
		}
		@Override
		public void takeChip(Integer c) {
			super.takeChip(c);
			if (debug) System.out.println("Output Bin " + this.id + " takes chip " + c);
		}
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new BalanceBots(10, "Balance Bots", puzzleInput);
		puzzle.run();
	}

	static class Input {
		String filename;
		int chip1, chip2;
		Input(String filename, int chip1, int chip2) {
			this.filename = filename;
			this.chip1 = chip1;
			this.chip2 = chip2;
		}
	}
	Input testInput = new Input("testInput", 5, 2);
	static Input puzzleInput = new Input("puzzleInput", 61, 17);
}
