package day07;

import java.util.ArrayList;

public class HandyHaversacks {
	ArrayList<Bag> masterBagList;
	
	public HandyHaversacks() {
		masterBagList = new ArrayList<Bag>();
	}
	public HandyHaversacks(String[] rules) {
		ParseRules(rules);
	}
	
	public int CountContainedBags(String color) {
		int count = 0;
		ArrayList<Bag> ToProcess = new ArrayList<Bag>();
		ToProcess.add(FindMatchingBag(color));
		while (ToProcess.size() > 0) {
			Bag currentBag = ToProcess.remove(0);
			count += currentBag.contains.size();
			ToProcess.addAll(currentBag.contains);
		}
		return count;
	}
	
	public ArrayList<Bag> FindContainingBags(String color) {
		ArrayList<Bag> containers = new ArrayList<Bag>();
		ArrayList<Bag> checkNext = new ArrayList<Bag>();
		checkNext.add(FindMatchingBag(color));
		
		while (checkNext.size() > 0) {
			Bag currentBag = checkNext.remove(0);
			
			// check if already processed
			boolean processed = false;
			for (Bag b : containers) {
				if (b.equals(currentBag.color)) processed = true;
			}
			
			if (!processed) {
				if (!currentBag.equals(color)) containers.add(currentBag);
				
				for (Bag b : masterBagList) {
					if (b.contains(currentBag.color)) {
						checkNext.add(b);
					}
				}
			}
		}
		
		return containers;
	}
	
	public void ParseRules(String[] rules) {
		masterBagList = new ArrayList<Bag>();
		for (String s : rules) {
			ParseRule(s);
		}
	}
	
	// Rule: <modifier> <color> bags contain <no other bags>
	//                                       # <modifier> <color> bag(s).
	//                                       # <modififer> <color> bag(s), # <modifier> <color> bag(s), ... .
	private void ParseRule(String rule) {
		rule = rule.trim();
		String[] words = rule.split(" ");
		String outerColor = words[0] + " " + words[1];
		Bag outerBag = FindMatchingBag(outerColor);
		
		if (words.length > 7) {
			int startIndex = 4;
			while (startIndex < words.length) {
				int count = Integer.parseInt(words[startIndex]);
				String innerColor = words[startIndex + 1] + " " + words[startIndex + 2];
				Bag innerBag = FindMatchingBag(innerColor);
				for (int i = 0; i < count; i++) {
					outerBag.contains.add(innerBag);
				}
				startIndex += 4;
			}
		}
	}
	
	public Bag FindMatchingBag(String color) {
		for (Bag b : masterBagList) {
			if (b.equals(color)) return b;
		}
		Bag b = new Bag(color);
		masterBagList.add(b);
		return b;
	}
	
	public class Bag {
		public String color;
		ArrayList<Bag> contains;
		
		public Bag() {
			contains = new ArrayList<Bag>();
			color = "";
		}
		public Bag(String color) {
			this();
			this.color = color;
		}
		
		public boolean contains(String color) {
			for (Bag b : contains) {
				if (b.equals(color)) return true;
			}
			return false;
		}
		
		public boolean equals(String color) {
			return this.color.contentEquals(color);
		}
	}
}
