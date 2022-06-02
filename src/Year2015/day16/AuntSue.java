package Year2015.day16;

import java.util.ArrayList;

import tools.Constants;
import tools.DataFile;
import tools.RunPuzzle;
import tools.TestCase;

public class AuntSue extends RunPuzzle {

	public AuntSue(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		return new ArrayList<TestCase>();
	}

	@Override
	public void printResult(Object result) {
		System.out.println(Constants.resultIndent + (Integer)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		DataFile file = new DataFile(2015, 16, (String)input);
		String[] data = file.getData();
		ArrayList<Aunt> aunts = new ArrayList<Aunt>();
			
		for (String line : data) {
			aunts.add(new Aunt(line));
		}
			
		Aunt auntData = new Aunt(3, 7, 2, 3, 0, 0, 5, 3, 2, 1);
			
		while (aunts.size() > 0 && !testAunt(section, auntData, aunts.get(0))) {
			aunts.remove(0);
		}
		if (aunts.size() > 0) return aunts.get(0).id;
		else return -1;
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new AuntSue(16, "Aunt Sue", puzzleFile);
		puzzle.run();
	}
	
	private static String puzzleFile = "puzzleFile";

	private class Aunt {
		int id, children, cats, samoyeds, pomeranians, akitas, vizslas, goldfish, trees, cars, perfumes;
		
		Aunt() {
			id = 0;
			children = -1;
			cats = -1;
			samoyeds = -1;
			pomeranians = -1;
			akitas = -1;
			vizslas = -1;
			goldfish = -1;
			trees = -1;
			cars = -1;
			perfumes = -1;
		}
		Aunt(String input) {
			this();
			parseInput(input);
		}
		Aunt(int children, int cats, int samoyeds, int pomeranians, int akitas, int vizslas, int goldfish, int trees, int cars, int perfumes) {
			this();
			this.children = children;
			this.cats = cats;
			this.samoyeds = samoyeds;
			this.pomeranians = pomeranians;
			this.akitas = akitas;
			this.vizslas = vizslas;
			this.goldfish = goldfish;
			this.trees = trees;
			this.cars = cars;
			this.perfumes = perfumes;
		}
		
		void parseInput(String input) {
			int start = input.indexOf(" ");
			int end = input.indexOf(":");
			this.id = Integer.parseInt(input.substring(start, end).trim());
			input = input.substring(end + 1).trim();
			
			String[] fields = input.split(",");
			for (String f : fields) {
				f = f.trim();
				start = f.indexOf(":");
				String name = f.substring(0, start).trim();
				int value = Integer.parseInt(f.substring(start + 1).trim());
				
				switch (name) {
				case "children": children = value; break;
				case "cats": cats = value; break;
				case "samoyeds": samoyeds = value; break;
				case "pomeranians": pomeranians = value; break;
				case "akitas": akitas = value; break;
				case "vizslas": vizslas = value; break;
				case "goldfish": goldfish = value; break;
				case "trees": trees = value; break;
				case "cars": cars = value; break;
				case "perfumes": perfumes = value; break;
				}
			}
		}
	}
	
	static boolean testAunt(int section, Aunt data, Aunt test) {
		if (test.children > -1 && data.children != test.children) {
			return false;
		}
		else if (test.cats > -1 && (section == 1 ? (data.cats != test.cats) : !(data.cats < test.cats))) {
			return false;
		}
		else if (test.samoyeds > -1 && data.samoyeds != test.samoyeds) {
			return false;
		}
		else if (test.pomeranians > -1 && (section == 1 ? (data.pomeranians != test.pomeranians) : !(data.pomeranians > test.pomeranians))) {
			return false;
		}
		else if (test.akitas > -1 && data.akitas != test.akitas) {
			return false;
		}
		else if (test.vizslas > -1 && data.vizslas != test.vizslas) {
			return false;
		}
		else if (test.goldfish > -1 && (section == 1 ? (data.goldfish != test.goldfish) : !(data.goldfish > test.goldfish))) {
			return false;
		}
		else if (test.trees > -1 && (section == 1 ? (data.trees != test.trees) : !(data.trees < test.trees))) {
			return false;
		}
		else if (test.cars > -1 && data.cars != test.cars) {
			return false;
		}
		else if (test.perfumes > -1 && data.perfumes != test.perfumes) {
			return false;
		}
		else {
			return true;
		}
	}
}
