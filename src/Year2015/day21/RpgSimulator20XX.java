package Year2015.day21;

import java.util.ArrayList;

import tools.RunPuzzle;
import tools.TestCase;

public class RpgSimulator20XX extends RunPuzzle {

	public RpgSimulator20XX(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		return new ArrayList<TestCase>();
	}

	@Override
	public void printResult(Object result) {
		log(defaultOutputIndent + (Integer)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		Character boss = (Character)input;
		Character me = new Character(100, 0, 0);
		
		ArrayList<Item> weapons = new ArrayList<Item>();
		weapons.add(new Item(8, 4, 0));
		weapons.add(new Item(10, 5, 0));
		weapons.add(new Item(25, 6, 0));
		weapons.add(new Item(40, 7, 0));
		weapons.add(new Item(74, 8, 0));
		
		ArrayList<Item> armor = new ArrayList<Item>();
		armor.add(new Item(0, 0, 0));
		armor.add(new Item(13, 0, 1));
		armor.add(new Item(31, 0, 2));
		armor.add(new Item(53, 0, 3));
		armor.add(new Item(75, 0, 4));
		armor.add(new Item(102, 0, 5));
		
		ArrayList<Item> rings = new ArrayList<Item>();
		rings.add(new Item(0, 0, 0));
		rings.add(new Item(0, 0, 0));
		rings.add(new Item(25, 1, 0));
		rings.add(new Item(50, 2, 0));
		rings.add(new Item(100, 3, 0));
		rings.add(new Item(20, 0, 1));
		rings.add(new Item(40, 0, 2));
		rings.add(new Item(80, 0, 3));
		
		int extremeCost = -1;
		for (Item w : weapons) {
			for (Item a : armor) {
				for (int i = 0; i < rings.size() - 1; i++) {
					for (int j = i + 1; j < rings.size(); j++) {
						me.items = new ArrayList<Item>();
						me.items.add(w);
						me.items.add(a);
						me.items.add(rings.get(i));
						me.items.add(rings.get(j));
						
						if (section == 1 && me.battleOpponent(boss)) {
							if (extremeCost == -1) extremeCost = me.getCost();
							else extremeCost = Math.min(extremeCost, me.getCost());
						}
						else if (section == 2 && !me.battleOpponent(boss)) {
							if (extremeCost == -1) extremeCost = me.getCost();
							else extremeCost = Math.max(extremeCost, me.getCost());
						}
					}
				}
			}
		}
		return extremeCost;
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new RpgSimulator20XX(21, "RPG Simulator 20XX", new Character(109, 8, 2));
		puzzle.run();
	}

	static class Character {
		int hitPoints, damage, armor;
		ArrayList<Item> items;
		Character(int hitPoints, int damage, int armor) {
			this.hitPoints = hitPoints;
			this.damage  = damage;
			this.armor = armor;
			items = new ArrayList<Item>();
		}
		
		int getDamage() {
			int totalDamage = damage;
			for (Item i : items) {
				totalDamage += i.damage;
			}
			return totalDamage;
		}
		
		int getArmor() {
			int totalArmor = armor;
			for (Item i : items) {
				totalArmor += i.armor;
			}
			return totalArmor;
		}
		
		int getCost() {
			int cost = 0;
			for (Item i : items) {
				cost += i.cost;
			}
			return cost;
		}
		
		boolean battleOpponent(Character boss) {
			int myAttack = Math.max(this.getDamage() - boss.getArmor(), 1);
			int bossAttack = Math.max(boss.getDamage() - this.getArmor(), 1);
			int myWin = (int)Math.ceil((double)boss.hitPoints / myAttack);
			int bossWin = (int)Math.ceil((double)this.hitPoints / bossAttack);
			return myWin <= bossWin;
		}
	}
	
	class Item {
		int cost, damage, armor;
		Item(int cost, int damage, int armor) {
			this.cost = cost;
			this.damage = damage;
			this.armor = armor;
		}
	}
}
