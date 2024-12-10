package Year2015.day19;

import java.util.ArrayList;

import tools.RunPuzzle;
import tools.TestCase;

public class MedicineForRudolph extends RunPuzzle {
	private boolean debug = false;
	
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
		log(defaultOutputIndent + (Integer)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		PuzzleInput p = (PuzzleInput)input;
		ArrayList<Reaction> reactions = new ArrayList<Reaction>();
		for (String s : p.reactions) {
			reactions.add(new Reaction(s));
		}
		
		if (section == 1) {
			String startMolecule = p.molecule;
			ArrayList<String> molecules = new ArrayList<String>();
			
			for (Reaction rx : reactions) {
				int startIndex = 0;
				while (startMolecule.indexOf(rx.input, startIndex) > -1) {
					startIndex = startMolecule.indexOf(rx.input, startIndex);
					StringBuffer testMolecule = new StringBuffer(startMolecule).replace(startIndex, startIndex + rx.input.length(), rx.output);
					startIndex = startIndex + rx.input.length();
					
					boolean foundMatch = false;
					for (String m : molecules) {
						if (m.equals(testMolecule.toString())) {
							foundMatch = true;
							break;
						}
					}
					if (!foundMatch) molecules.add(testMolecule.toString());
				}
			}
			
			return molecules.size();
		}
		else {
			int steps = 0;
			StringBuilder molecule = new StringBuilder(p.molecule);
			while (molecule.length() > 0) {
				logDebug("Molecule: " + molecule.toString());
				for (Reaction rx : reactions) {
					if (rx.input.equals("e") && rx.output.equals(molecule.toString())) {
						logDebug("Step " + (1 + steps) + ": Do reaction " + rx.output + " => " + rx.input);
						return ++steps;
					}
					else if (!rx.input.equals("e")) {
						int startIndex = molecule.indexOf(rx.output);
						while (startIndex > -1) {
							steps++;
							logDebug("Step " + steps + ": Do reaction " + rx.output + " => " + rx.input);
							molecule.replace(startIndex, startIndex + rx.output.length(), rx.input);
							logDebug("\tNew molecule: " + molecule.toString());
							startIndex = molecule.indexOf(rx.output, startIndex);
						}
					}
				}
			}
			return steps;
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
