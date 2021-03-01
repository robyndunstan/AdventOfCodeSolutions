package day19;

import java.util.ArrayList;
import java.util.HashMap;

public class MonsterMessages {
	HashMap<Integer, Rule> rules;
	
	public void ParseRules(String[] input) {
		rules = new HashMap<Integer, Rule>();
		for (String s : input) {
			Rule r = new Rule();
			int rNum = r.ParseRule(s);
			rules.put(rNum, r);
		}
	}
	
	public int CountValidMessages(String[] input) {
		ArrayList<String> validMessages = rules.get(0).GetValidMessages();
		System.out.println("There are " + validMessages.size() + " valid messages");
		for (String s : validMessages) System.out.println(s);
		int validCount = 0;
		for (String s : input) {
			s = s.trim();
			boolean foundMatch = false;
			for (String v : validMessages) {
				if (v.equals(s)) {
					foundMatch = true;
					break;
				}
			}
			if (foundMatch) {
				validCount++;
			}
		}
		return validCount;
	}

	private class Rule {
		int number;
		String text;
		private ArrayList<String> validMessages;

		public int ParseRule(String input) {
			int index = input.indexOf(":");
			number = Integer.parseInt(input.substring(0, index).trim());
			text = input.substring(index + 1).trim();
			return number;
		}
		
		public ArrayList<String> GetValidMessages() {
			if (validMessages != null) {
				return validMessages;
			}
			else {
				validMessages = new ArrayList<String>();
				if (text.indexOf("\"") > -1) {
					validMessages.add(text.substring(text.indexOf("\"") + 1, text.lastIndexOf("\"")));
				}
				else {
					//System.out.println("Rule text: " + text);
					String[] options;
					if (text.indexOf("|") > -1) {
						options = text.split("\\|");
					}
					else {
						options = new String[1];
						options[0] = text;
					}
					//System.out.println("Number of options " + options.length);
					ArrayList<String> results = new ArrayList<String>();
					results.add("");
					for (String o : options) {
						o = o.trim();
						//System.out.println("This option " + o);
						String[] subrules;
						if (o.indexOf(" ") > -1) {
							subrules = o.split(" ");
						}
						else {
							subrules = new String[1];
							subrules[0] = o;
						}
						//System.out.println("Number of rules to combine for this option " + subrules.length);
						for (String s : subrules) {
							if (s.length() > 0) {
								int subNumber = Integer.parseInt(s.trim());
								ArrayList<String> newResults = new ArrayList<String>();
								//System.out.println("Rule " + subNumber + " has " + rules.get(subNumber).GetValidMessages().size() + " valid forms");
								for (String subResult : rules.get(subNumber).GetValidMessages()) {
									for (String r : results) {
										newResults.add(subResult + r);
									}
								}
								results = newResults;
							}
						}
						validMessages.addAll(results);
					}
				}
				return validMessages;
			}
		}
	}
}
