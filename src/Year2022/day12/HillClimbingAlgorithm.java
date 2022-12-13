package Year2022.day12;

import java.awt.Point;
import java.util.ArrayList;

import tools.RunPuzzle;
import tools.TestCase;

public class HillClimbingAlgorithm extends RunPuzzle {

	public HillClimbingAlgorithm(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
		this.debug = false;
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<String[], Integer>(1, test1Input, 31));
		tests.add(new TestCase<String[], Integer>(2, test1Input, 29));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		System.out.println(this.defaultResultIndent + (Integer)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		String[] inputMap = (String[])input;
		HeightMap map = new HeightMap(inputMap, this.debug);
		
		if (section == 1) {
			while (!map.foundEnd) {
				map.doStep();
			}
			
			return map.getEndSteps();
		}
		else {
			int minSteps = Integer.MAX_VALUE;
			ArrayList<Point> startingPoints = map.getStartingPoints();
			
			for (Point p : startingPoints) {
				map.setStartingPoint(p);
				while (!map.foundEnd) {
					map.doStep();
				}
				minSteps = Math.min(minSteps, map.getEndSteps());
			}
			
			return minSteps;
		}
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new HillClimbingAlgorithm(12, "Hill Climbing Algorithm", puzzleInput);
		puzzle.run();
	}
	
	static class HeightMap {
		int currentSteps;
		Square[][] map;
		boolean foundEnd = false;
		boolean debug = false;
		
		public static class Square {
			public char height;
			public int minStepsTo;
			
			public Square() {
				minStepsTo = Integer.MAX_VALUE;
			}
			public Square(char c) {
				this();
				height = c;
			}
			
			public char getHeight() {
				switch(height) {
				case 'S':
					return 'a';
				case 'E':
					return 'z';
				default:
					return height;
				}
			}
			
			public boolean isStart() {
				return height == 'S';
			}
			
			public boolean isEnd() {
				return height == 'E';
			}
		}
		
		public HeightMap(String[] input, boolean debug) {
			map = new Square[input[0].length()][input.length];
			for (int i = 0; i < input[0].length(); i++) {
				for (int j = 0; j < input.length; j++) {
					char c = input[j].charAt(i);
					map[i][j] = new Square(c);
					if (c == 'S') map[i][j].minStepsTo = 0;
				}
			}
			currentSteps = 0;
			foundEnd = false;
			this.debug = debug;
		}
		
		public void doStep() {
			if (this.debug) System.out.println("Step " + currentSteps);
			boolean foundCurrentStep = false;
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[i].length; j++) {
					Square thisSquare = map[i][j];
					if (thisSquare.minStepsTo == currentSteps) {
						foundCurrentStep = true;
						// +x
						if (i < map.length - 1 && map[i + 1][j].getHeight() - map[i][j].getHeight() <= 1) {
							if (this.debug) System.out.println(map[i][j].getHeight() + " -> " + map[i + 1][j].getHeight() + ": " + (int)(map[i + 1][j].getHeight() - map[i][j].getHeight())); 
							map[i + 1][j].minStepsTo = Math.min(map[i + 1][j].minStepsTo, currentSteps + 1);
							if (map[i + 1][j].isEnd()) foundEnd = true;
						}
						
						// -y
						if (j > 0 && map[i][j - 1].getHeight() - map[i][j].getHeight() <= 1) {
							map[i][j - 1].minStepsTo = Math.min(map[i][j - 1].minStepsTo, currentSteps + 1);
							if (map[i][j - 1].isEnd()) foundEnd = true;
						}
						
						// -x
						if (i > 0 && map[i - 1][j].getHeight() - map[i][j].getHeight() <= 1) {
							map[i - 1][j].minStepsTo = Math.min(map[i - 1][j].minStepsTo, currentSteps + 1);
							if (map[i - 1][j].isEnd()) foundEnd = true;
						}
						
						// +y
						if (j < map[i].length - 1 && map[i][j + 1].getHeight() - map[i][j].getHeight() <= 1) {
							map[i][j + 1].minStepsTo = Math.min(map[i][j + 1].minStepsTo, currentSteps + 1);
							if (map[i][j + 1].isEnd()) foundEnd = true;
						}
					}
				}
			}
			currentSteps++;
			if (!foundCurrentStep) this.foundEnd = true; // If stuck, stop map
			if (this.debug && currentSteps % 100 == 0) printMap(); 
		}
		
		public int getEndSteps() {
			for (Square[] col : map) {
				for (Square s : col) {
					if (s.isEnd()) return s.minStepsTo;
				}
			}
			return Integer.MAX_VALUE;
		}
		
		public void printMap() {
			for (int j = 0; j < map[0].length; j++) {
				for (int i = 0; i < map.length; i++) {
					System.out.print("\t" + (map[i][j].minStepsTo < Integer.MAX_VALUE ? map[i][j].minStepsTo : map[i][j].height));
				}
				System.out.println();
			}
		}
		
		public void resetMap() {
			for (Square[] col : map) {
				for (Square s : col) {
					s.minStepsTo = Integer.MAX_VALUE;
				}
			}
			currentSteps = 0;
			foundEnd = false;
		}
		
		public ArrayList<Point> getStartingPoints() {
			ArrayList<Point> startingPoints = new ArrayList<Point>();
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[i].length; j++) {
					if (map[i][j].getHeight() == 'a') {
						startingPoints.add(new Point(i, j));
					}
				}
			}
			return startingPoints;
		}
		
		public void setStartingPoint(Point p) {
			resetMap();
			map[p.x][p.y].minStepsTo = 0; 
		}
	}

	static String[] test1Input = {
			"Sabqponm",
			"abcryxxl",
			"accszExk",
			"acctuvwj",
			"abdefghi"
	};
	
	static String[] puzzleInput = {
			"abccccccccaaaaaaaccaaaaaaaaaaaaaaaaccccccccccccccccccccccccccccccccccccaaaaaa",
			"abccccccccaaaaaaaccaaaaaaaaaaaaaaaaccccccccccccccccccccccccccccccccccccaaaaaa",
			"abccccccccccaaaaaaccaaaaaaaaaaaaaaaaccccccccccccccccacccccccccccccccccccaaaaa",
			"abcccccaaaacaaaaaaccaaaaaaaaaaaaaaaaacccccccccccccccaaaccccaccccccccccccccaaa",
			"abccccaaaaacaaccccccaaaaaacaaacaacaaaaaaacccccccccccaaaacccaacccccccccccccaaa",
			"abaaccaaaaaaccccaaacaaaacacaaacaaccaaaaaacccccccccccaklaccccccccccccccccccaac",
			"abaaccaaaaaaccaaaaaacccccccaaacccaaaaaaaccccccccccckkkllllccccccccccccccccccc",
			"abaaccaaaaaaccaaaaaacccccccaaaaacaaaaaaacccccccccckkkklllllcccccccaaaccaccccc",
			"abacccccaacccccaaaaacccccccaaaaaccaaaaaaacccccccckkkkpppllllccccccaaaaaaccccc",
			"abacccccccccccaaaaacccccccccaaaacccaaaaaaccccccckkkkpppppplllccccddddaaaccccc",
			"abccccccccccccaaaaaccccccccccaaaccaaaccccccccccckkkppppppppllllldddddddaccccc",
			"abccacccccccccccccccccccccccccccccaaccccccccccckkkopppupppplllmmmmdddddaacccc",
			"abccaaacaaaccccccccccccccccccccaaaaaaaaccccccckkkkopuuuuupppllmmmmmmddddacccc",
			"abccaaaaaaaccccccccccccccccccccaaaaaaaacccccjjkkkooouuuuuuppqqqqqmmmmddddcccc",
			"abccaaaaaacccccccccccccccaaccccccaaaacccccjjjjjjoooouuxuuuppqqqqqqmmmmdddcccc",
			"abcaaaaaaaacccccccccccccaaacccccaaaaaccccjjjjoooooouuuxxuuvvvvvqqqqmmmdddcccc",
			"abaaaaaaaaaacccccccaaaaaaacaacccaacaaacccjjjooooouuuuxxxxvvvvvvvqqqmmmdddcccc",
			"abaaaaaaaaaacccaaacaaaaaaaaaacccacccaaccjjjooootttuuuxxxyyvyyvvvqqqmmmeeecccc",
			"abcccaaacaaacccaaaaaaacaaaaaccccccccccccjjjooottttxxxxxxyyyyyyvvqqqmmmeeccccc",
			"abcccaaacccccccaaaaaacaaaaaccccaaccaacccjjjnnntttxxxxxxxyyyyyvvvqqqnneeeccccc",
			"SbccccaacccccccaaaaaaaaacaaacccaaaaaacccjjjnnntttxxxEzzzzyyyyvvqqqnnneeeccccc",
			"abcccccccccccccaaaaaaaaacaaccccaaaaaccccjjjnnnttttxxxxyyyyyvvvrrrnnneeecccccc",
			"abcccaacccccccaaaaaaaaaccccccccaaaaaacccciiinnnttttxxxyyyyywvvrrrnnneeecccccc",
			"abcccaaaaaaccaaaaaaaacccccccccaaaaaaaaccciiiinnnttttxyyywyyywvrrrnnneeecccccc",
			"abcccaaaaaaccaaaaaaaacccccccccaaaaaaaacccciiinnnntttxwywwyyywwwrrnnneeecccccc",
			"abcaaaaaaaccaaaaaaaaaccccccccccccaacccccccciiinnnttwwwwwwwwwwwwrrnnneeecccccc",
			"abcaaaaaaaccaaaaaacccccccccccccccaaccccccaaiiiinnttwwwwwwwwwwwrrrnnnffecccccc",
			"abcccaaaaaaccaaaaaccccccccccccccccccccaaaaaciiinnssswwwssssrwwrrrnnnfffcccccc",
			"abaacaaccaaccaaaccccccccaacccccccccccccaaaaaiiinnssssssssssrrrrrronnfffcccccc",
			"abaccaaccaacccccccccaaacaacccccccccccccaaaaaiiimmmssssssmoosrrrrooonffaaacccc",
			"abaaaccccaaaaaaccccccaaaaaccccccccccccaaaaaccihmmmmsssmmmoooooooooofffaaacccc",
			"abaaaccccaaaaaacccccccaaaaaacccccccccccccaacchhhmmmmmmmmmoooooooooffffaaccccc",
			"abaacccaaaaaaaccccccaaaaaaaaccccaaccccccccccchhhhmmmmmmmgggggooofffffaaaccccc",
			"abaacccaaaaaaaccccccaaaaaaaccccaaaaccccccccccchhhhmmmmhggggggggfffffaaaaccccc",
			"abccccccaaaaaaacccccaacaaaaacccaaaaccccccccccchhhhhhhhggggggggggfffaacaaccccc",
			"abccaacccaaaaaaccccccccaaaaaccaaaaacccccccccccchhhhhhhggaaaaaaccccccccccccccc",
			"abccaaaccaaccccccccccccccaaaaaaaaaccccccccccccccchhhhaaaccaaaacccccccccccccaa",
			"abaaaaaaaccccccccccccccccaaaaaaaaccccccccccccccccccccaaaccccaaccccccccccccaaa",
			"abaaaaaaaccccccccaaaccccacaaaaaacccccccccccccccccccccaaaccccccccccccccccccaaa",
			"abaaaaaacccccccaaaaacaaaaaaaaaaacccccccccccccccccccccaaccccccccccccccccaaaaaa",
			"abaaaaaacccccccaaaaaaaaaaaaaaaaaaacccccccccccccccccccccccccccccccccccccaaaaaa"
	};
}
