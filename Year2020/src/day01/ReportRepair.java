package day01;

import java.util.ArrayList;

public class ReportRepair {
	private ArrayList<Integer> input;
	
	public ReportRepair(int[] input) {
		this.input = new ArrayList<Integer>();
		for (int i : input) {
			this.input.add(i);
		}
	}
	
	public int ProcessReport2Entries() {
		for (int i = 0; i < input.size(); i++) {
			for (int j = i + 1; j < input.size(); j++) {
				if (input.get(i) + input.get(j) == 2020) {
					return input.get(i) * input.get(j);
				}
			}
		}
		
		return 0;
	}
	
	public int ProcessReport3Entries() {
		for (int i = 0; i < input.size(); i++) {
			for (int j = i + 1; j < input.size(); j++) {
				for (int k = j + 1; k < input.size(); k++) {
					if (input.get(i) + input.get(j) + input.get(k) == 2020) {
						return input.get(i) * input.get(j) * input.get(k);
					}
				}
			}
		}
		
		return 0;
	}
}
