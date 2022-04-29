package Year2015.day15;

import java.util.ArrayList;

import tools.RunPuzzle;
import tools.TestCase;

public class ScienceForHungryPeople extends RunPuzzle {
	private boolean debug = true;
	private int maxScore;

	public ScienceForHungryPeople(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<String[], Integer>(1, testInput, 62842880));
		tests.add(new TestCase<String[], Integer>(2, testInput, 57600000));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		System.out.println("\t\t\t\t" + (Integer)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		maxScore = 0;
		
		ArrayList<Ingredient> recipe = new ArrayList<Ingredient>();
		for (String s : (String[])input) {
			recipe.add(new Ingredient(s));
		}
		
		useIngredient(section, recipe, 0, 100);
		
		return maxScore;
	}
	
	private void useIngredient(int section, ArrayList<Ingredient> ingredients, int ingredientIndex, int remainingRecipe) {
		if (ingredientIndex < ingredients.size() - 1) {
			for (int i = 0; i < remainingRecipe; i++) {
				ingredients.get(ingredientIndex).quantity = i;
				useIngredient(section, ingredients, ingredientIndex + 1, remainingRecipe - i);
			}
		}
		else if (ingredientIndex == ingredients.size() - 1) {
			ingredients.get(ingredientIndex).quantity = remainingRecipe;
			useIngredient(section, ingredients, ingredientIndex + 1, 0);
		}
		else {
			int capacity = 0;
			int durability = 0;
			int flavor = 0;
			int texture = 0;
			int calories = 0;
			for (Ingredient i : ingredients) {
				capacity += i.capacity * i.quantity;
				durability += i.durability * i.quantity;
				flavor += i.flavor * i.quantity;
				texture += i.texture * i.quantity;
				calories += i.calories * i.calories;
			}
			
			if (section == 2 && calories != 500) return;
			
			int score = capacity * durability * flavor * texture;
			if (score > maxScore) {
				maxScore = score;
				if (debug) {
					System.out.println("New score " + score);
					for (Ingredient i : ingredients) {
						System.out.println("\t" + i.name + " " + i.quantity);
					}
				}
			}
			maxScore = Math.max(capacity * durability * flavor * texture, maxScore);
		}
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new ScienceForHungryPeople(15, "Science for Hungry People", puzzleInput);
		puzzle.run();
	}

	private class Ingredient {
		public int capacity, durability, flavor, texture, calories, quantity;
		public String name;
		
		public Ingredient() {
			capacity = 0;
			durability = 0;
			flavor = 0;
			texture = 0;
			calories = 0;
			quantity = 0;
			name = "Ingredient";
		}
		public Ingredient(String input) {
			this();
			parseInput(input);
		}
		
		private void parseInput(String input) {
			int endIndex = input.indexOf(":");
			name = input.substring(0, endIndex).trim();
			
			int startIndex = input.indexOf("capacity");
			endIndex = input.indexOf(",", startIndex);
			capacity = Integer.parseInt(input.substring(startIndex + "capacity".length(), endIndex).trim());
			
			startIndex = input.indexOf("durability");
			endIndex = input.indexOf(",", startIndex);
			durability = Integer.parseInt(input.substring(startIndex + "durability".length(), endIndex).trim());
			
			startIndex = input.indexOf("flavor");
			endIndex = input.indexOf(",", startIndex);
			flavor = Integer.parseInt(input.substring(startIndex + "flavor".length(), endIndex).trim());
			
			startIndex = input.indexOf("texture");
			endIndex = input.indexOf(",", startIndex);
			texture = Integer.parseInt(input.substring(startIndex + "texture".length(), endIndex).trim());
			
			startIndex = input.indexOf("calories");
			calories = Integer.parseInt(input.substring(startIndex + "calories".length()).trim());
		}
	}
	
	static String[] testInput = {
			"Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8",
			"Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3"
	};
	
	static String[] puzzleInput = {
			"Sprinkles: capacity 2, durability 0, flavor -2, texture 0, calories 3          ",
			"Butterscotch: capacity 0, durability 5, flavor -3, texture 0, calories 3       ",
			"Chocolate: capacity 0, durability 0, flavor 5, texture -1, calories 8          ",
			"Candy: capacity 0, durability -1, flavor 0, texture 5, calories 8              "
	};
}
