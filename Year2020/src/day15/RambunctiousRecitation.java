package day15;

import java.util.ArrayList;
import java.util.HashMap;

public class RambunctiousRecitation {
	HashMap<Integer, ArrayList<Integer>> history;

	public void ParseStartingNumbers(String input) {
		history = new HashMap<Integer, ArrayList<Integer>>();
		int index = 1;
		String[] numbers = input.split(",");
		for (String n : numbers) {
			AddValue(index, Integer.parseInt(n));
			index++;
		}
	}

	public int GetNumber(int index) {
		int prevValue = GetLastNumber();
		ArrayList<Integer> prevIndexArray = history.get(prevValue);
		int prevIndex = prevIndexArray.size() == 1 ? prevIndexArray.get(0) : prevIndexArray.get(1);
		if (prevIndex > index) {
			return (int)-1;
		}
		else {
			while (prevIndex < index) {
				int nextIndex = prevIndex + 1;
				int nextValue;
				if (prevIndexArray.size() == 1) {
					nextValue = 0;
				}
				else {
					nextValue = prevIndexArray.get(1) - prevIndexArray.get(0);
				}
				AddValue(nextIndex, nextValue);
				prevIndex = nextIndex;
				prevValue = nextValue;
				prevIndexArray = history.get(prevValue);
			}
			return prevValue;
		}
	}
	
	private void AddValue(int index, int value) {
		ArrayList<Integer> indexArray = history.get(value);
		if (indexArray == null) {
			indexArray = new ArrayList<Integer>();
		}
		else if (indexArray.size() == 2) {
			indexArray.remove(0);
		}
		indexArray.add(index);
		history.put(value, indexArray);
	}
	
	private int GetLastNumber() {
		int index = 0;
		int value = 0;
		for (int k : history.keySet()) {
			ArrayList<Integer> indexArray = history.get(k);
			int testIndex;
			if (indexArray.size() == 1) {
				testIndex = indexArray.get(0);
			}
			else {
				testIndex = indexArray.get(1);
			}
			if (testIndex > index) {
				index = testIndex;
				value = k;
			}
		}
		return value;
	}
}
