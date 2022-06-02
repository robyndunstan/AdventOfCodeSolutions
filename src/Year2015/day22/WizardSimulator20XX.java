package Year2015.day22;

import java.util.ArrayList;

import tools.Constants;
import tools.RunPuzzle;
import tools.TestCase;

public class WizardSimulator20XX extends RunPuzzle {
	public WizardSimulator20XX(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
		debug = false;
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
		Character boss = (Character)input;
		Character me = new Character(50, 0, 0, 500);
		ArrayList<Effect> activeEffects = new ArrayList<Effect>();
		Spell[] castable = {Spell.Drain, Spell.Magic_Missile, Spell.Poison, Spell.Recharge, Spell.Shield};
		int minManaCost = Integer.MAX_VALUE;
		Effect levelHard = new Effect(Spell.Level_Hard, 1);
		
		ArrayList<Game> games = new ArrayList<Game>();
		Game start = new Game(activeEffects, me, boss);
		games.add(start);
		
		while (games.size() > 0) {
			if (debug) System.out.println(games.size() + " possible games");
			ArrayList<Game> nextTurn = new ArrayList<Game>();
			for (Game g : games) {
				if (section == 2) g.activeEffects.add(levelHard);
				g.doStandby();
				if (g.boss.hitPoints > 0) {
					ArrayList<Game> bossTurn = new ArrayList<Game>();
					for (Spell s : castable) {
						if (!g.isSpellActive(s)) {
							Game g2 = new Game(copyEffects(g.activeEffects), g.me.clone(), g.boss.clone());
							g2.castSpell(s);
							if (g2.me.mana >= 0 && g2.me.hitPoints > 0) {
								if (g2.boss.hitPoints > 0) {
									bossTurn.add(g2);
								}
								else {
									minManaCost = Math.min(g2.me.manaCost, minManaCost);
									if (debug) System.out.println("I win using " + g2.me.manaCost + " mana (min " + minManaCost + ")");
								}
							}
						}
					}
					
					for (Game g2 : bossTurn) {
						g2.doStandby();
						if (g2.boss.hitPoints > 0) {
							g2.doBossAttack();
							if (g2.me.hitPoints > 0) nextTurn.add(g2);
						}
						else {
							minManaCost = Math.min(g2.me.manaCost, minManaCost);
							if (debug) System.out.println("I win using " + g2.me.manaCost + " mana (min " + minManaCost + ")");
						}
					}
				}
				else {
					minManaCost = Math.min(g.me.manaCost, minManaCost);
					if (debug) System.out.println("I win using " + g.me.manaCost + " mana (min " + minManaCost + ")");
				}
			}
			games = nextTurn;
		}
		
		return minManaCost;
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new WizardSimulator20XX(22, "Wizard Simulator 20XX", new Character(71, 10, 0, 0));
		puzzle.run();
	}
	
	class Game {
		Character me, boss;
		ArrayList<Effect> activeEffects;
		Game(ArrayList<Effect> effects, Character me, Character boss) {
			this.activeEffects = effects;
			this.me = me;
			this.boss = boss;
		}
		
		void doStandby() {
			me.armor = 0; // Armor resets after Shield ends
			ArrayList<Effect> stillActive = new ArrayList<Effect>();
			for (Effect e : activeEffects) {
				doEffect(e);
				if (e.turns > 0) {
					stillActive.add(e);
				}
			}
			activeEffects = stillActive;
			
		}
		
		void doEffect(Effect e) {
			switch(e.spell) {
			case Level_Hard:
				me.hitPoints -= 1;
				break;
			case Poison:
				boss.hitPoints -= 3;
				break;
			case Recharge:
				me.mana += 101;
				break;
			case Shield:
				me.armor = 7;
				break;
			default:
				break;
			}
			e.turns -= 1;
		}
		
		boolean isSpellActive(Spell spell) {
			for (Effect e : activeEffects) {
				if (e.spell == spell) return true;
			}
			return false;
		}
		
		void castSpell(Spell spell) {
			switch(spell) {
			case Drain:
				me.mana -= 73;
				me.manaCost += 73;
				boss.hitPoints -= 2;
				me.hitPoints += 2;
				break;
			case Magic_Missile:
				me.mana -= 53;
				me.manaCost += 53;
				boss.hitPoints -= 4;
				break;
			case Poison:
				me.mana -= 173;
				me.manaCost += 173;
				activeEffects.add(new Effect(Spell.Poison, 6));
				break;
			case Recharge:
				me.mana -= 229;
				me.manaCost += 229;
				activeEffects.add(new Effect(Spell.Recharge, 5));
				break;
			case Shield:
				me.mana -= 113;
				me.manaCost += 113;
				activeEffects.add(new Effect(Spell.Shield, 6));
				break;
			default:
				break;
			}
		}
		
		void doBossAttack() {
			int bossAttack = Math.max(boss.damage - me.armor, 1);
			me.hitPoints -= bossAttack;
		}
	}

	static class Character{
		int hitPoints, damage, armor, mana, manaCost;
		Character(int hitPoints, int damage, int armor, int mana) {
			this.hitPoints = hitPoints;
			this.damage  = damage;
			this.armor = armor;
			this.mana = mana;
			this.manaCost = 0;
		}
		protected Character clone() {
			Character c = new Character(this.hitPoints, this.damage, this.armor, this.mana);
			c.manaCost = this.manaCost;
			return c;
		}
	}
	
	enum Spell {
		Magic_Missile, Drain, Shield, Poison, Recharge, Level_Hard
	}
	
	class Effect {
		Spell spell;
		int turns;
		Effect(Spell spell, int turns) {
			this.spell = spell;
			this.turns = turns;
		}
	}
	
	ArrayList<Effect> copyEffects(ArrayList<Effect> effects) {
		ArrayList<Effect> copy = new ArrayList<Effect>();
		for (Effect e : effects) {
			copy.add(new Effect(e.spell, e.turns));
		}
		return copy;
	}
}
