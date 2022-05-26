package Year2015.day19;

import java.util.ArrayList;

import tools.RunPuzzle;
import tools.TestCase;

public class MedicineForRudolph extends RunPuzzle {
	private boolean debug = true;
	
	public MedicineForRudolph(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<PuzzleInput, Integer>(1, new PuzzleInput(test1Reactions, test1Molecule), 4));
		tests.add(new TestCase<PuzzleInput, Integer>(1, new PuzzleInput(test1Reactions, test2Molecule), 7));
		tests.add(new TestCase<PuzzleInput, Integer>(2, new PuzzleInput(test2Reactions, test1Molecule), 3));
		tests.add(new TestCase<PuzzleInput, Integer>(2, new PuzzleInput(test2Reactions, test2Molecule), 6));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		System.out.println("\t\t\t\t" + (Integer)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		PuzzleInput p = (PuzzleInput)input;
		ArrayList<Reaction> reactions = new ArrayList<Reaction>();
		for (String s : p.reactions) {
			reactions.add(new Reaction(s));
		}
		
		if (section == 1) {
			StringBuffer startMolecule = new StringBuffer(p.molecule);
			ArrayList<StringBuffer> molecules = new ArrayList<StringBuffer>();
			
			for (Reaction rx : reactions) {
				int startIndex = 0;
				while (startMolecule.indexOf(rx.input, startIndex) > -1) {
					startIndex = startMolecule.indexOf(rx.input, startIndex);
					StringBuffer testMolecule = new StringBuffer(startMolecule).replace(startIndex, startIndex + rx.input.length(), rx.output);
					startIndex = startIndex + rx.input.length();
					
					boolean foundMatch = false;
					for (StringBuffer m : molecules) {
						if (m.compareTo(testMolecule) == 0) {
							foundMatch = true;
							break;
						}
					}
					if (!foundMatch) molecules.add(testMolecule);
				}
			}
			
			return molecules.size();
		}
		else {
			int step = 0;
			ArrayList<StringBuffer> testMolecules = new ArrayList<StringBuffer>();
			testMolecules.add(new StringBuffer(p.molecule));
			
			while (testMolecules.size() > 0) {
				step++;
				ArrayList<StringBuffer> newMolecules = new ArrayList<StringBuffer>();
				
				for (StringBuffer m : testMolecules) {
					for (Reaction rx : reactions) {
						int startIndex = 0;
						while (m.indexOf(rx.output, startIndex) > -1) {
							startIndex = m.indexOf(rx.output, startIndex);
							StringBuffer newMolecule = new StringBuffer(m).replace(startIndex, startIndex + rx.output.length(), rx.input);
							if (newMolecule.compareTo(new StringBuffer("e")) == 0) return step;
							startIndex = startIndex + rx.output.length();
							
							boolean foundMatch = false;
							for (StringBuffer n: newMolecules) {
								if (m.compareTo(newMolecule) == 0) {
									foundMatch = true;
									break;
								}
							}
							if (!foundMatch) newMolecules.add(newMolecule);
						}
					}
				}
				testMolecules = newMolecules;
				if (debug) System.out.println(step + ": " + newMolecules.size());
				if (step > 500) break;
			}
			return -1;
		}
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new MedicineForRudolph(19, "Medicine for Rudolph", new PuzzleInput(puzzleReactions, puzzleMolecule));
		puzzle.run();
	}
	
	class Reaction {
		String input, output;
		Reaction(String input) {
			int index = input.indexOf("=>");
			this.input = input.substring(0, index).trim();
			this.output = input.substring(index + 2).trim();
		}
	}

	static class PuzzleInput {
		String[] reactions;
		String molecule;
		PuzzleInput(String[] reactions, String molecule) {
			this.reactions = reactions;
			this.molecule = molecule;
		}
	}
	
	String[] test1Reactions = {
			"H => HO",
			"H => OH",
			"O => HH"
	};
	String[] test2Reactions = {
			"e => H   ",
			"e => O   ",
			"H => HO  ",
			"H => OH  ",
			"O => HH  "
	};
	String test1Molecule = "HOH";
	String test2Molecule = "HOHOHO";
	
	static String[] puzzleReactions = {
			"Al => ThF          ",
			"Al => ThRnFAr      ",
			"B => BCa           ",
			"B => TiB           ",
			"B => TiRnFAr       ",
			"Ca => CaCa         ",
			"Ca => PB           ",
			"Ca => PRnFAr       ",
			"Ca => SiRnFYFAr    ",
			"Ca => SiRnMgAr     ",
			"Ca => SiTh         ",
			"F => CaF           ",
			"F => PMg           ",
			"F => SiAl          ",
			"H => CRnAlAr       ",
			"H => CRnFYFYFAr    ",
			"H => CRnFYMgAr     ",
			"H => CRnMgYFAr     ",
			"H => HCa           ",
			"H => NRnFYFAr      ",
			"H => NRnMgAr       ",
			"H => NTh           ",
			"H => OB            ",
			"H => ORnFAr        ",
			"Mg => BF           ",
			"Mg => TiMg         ",
			"N => CRnFAr        ",
			"N => HSi           ",
			"O => CRnFYFAr      ",
			"O => CRnMgAr       ",
			"O => HP            ",
			"O => NRnFAr        ",
			"O => OTi           ",
			"P => CaP           ",
			"P => PTi           ",
			"P => SiRnFAr       ",
			"Si => CaSi         ",
			"Th => ThCa         ",
			"Ti => BP           ",
			"Ti => TiTi         ",
			"e => HF            ",
			"e => NAl           ",
			"e => OMg           "
	};
	static String puzzleMolecule = "CRnSiRnCaPTiMgYCaPTiRnFArSiThFArCaSiThSiThPBCaCaSiRnSiRnTiTiMgArPBCaPMgYPTiRnFArFArCaSiRnBPMgArPRnCaPTiRnFArCaSiThCaCaFArPBCaCaPTiTiRnFArCaSiRnSiAlYSiThRnFArArCaSiRnBFArCaCaSiRnSiThCaCaCaFYCaPTiBCaSiThCaSiThPMgArSiRnCaPBFYCaCaFArCaCaCaCaSiThCaSiRnPRnFArPBSiThPRnFArSiRnMgArCaFYFArCaSiRnSiAlArTiTiTiTiTiTiTiRnPMgArPTiTiTiBSiRnSiAlArTiTiRnPMgArCaFYBPBPTiRnSiRnMgArSiThCaFArCaSiThFArPRnFArCaSiRnTiBSiThSiRnSiAlYCaFArPRnFArSiThCaFArCaCaSiThCaCaCaSiRnPRnCaFArFYPMgArCaPBCaPBSiRnFYPBCaFArCaSiAl";
}
