package day10;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterArray {
	ArrayList<Integer> adapters;
	
	public AdapterArray() {
		adapters = new ArrayList<Integer>();
	}
	public AdapterArray(String[] input) {
		ParseData(input);
	}
	
	public void ParseData(String[] data) {
		adapters = new ArrayList<Integer>();
		for (String s : data) {
			s = s.trim();
			adapters.add(Integer.parseInt(s));
		}
		SortAdapters();
	}
	
	private void SortAdapters() {
		ArrayList<Integer> newAdapters = new ArrayList<Integer>();
		while (adapters.size() > 0) {
			int minValue = adapters.get(0);
			int minIndex = 0;
			for (int i = 0; i < adapters.size(); i++) {
				if (adapters.get(i) < minValue) {
					minValue = adapters.get(i);
					minIndex = i;
				}
			}
			newAdapters.add(minValue);
			adapters.remove(minIndex);
		}
		adapters = newAdapters;
	}
	
	public int CountJoltDifferences(int diff) {
		int prevValue = 0;
		int diffCount = 0;
		for (int i = 0; i < adapters.size(); i++) {
			int currentValue = adapters.get(i);
			if (currentValue - prevValue == diff) {
				diffCount++;
			}
			prevValue = currentValue;
		}
		if (diff == 3) {
			diffCount++;
		}
		return diffCount;
	}
	
	public long CountValidArrangements() {
		HashMap<Integer, Long> lastAdapter = new HashMap<Integer, Long>();
		lastAdapter.put(0, 1l);
		for (int i = 0; i < adapters.size(); i++) {
			int currentAdapter = adapters.get(i);
			HashMap<Integer, Long> nextAdapter = new HashMap<Integer, Long>();
			for (int a : lastAdapter.keySet()) {
				if (currentAdapter - a <= 3) {
					AddToAdapterCount(nextAdapter, a, lastAdapter.get(a));
					AddToAdapterCount(nextAdapter, currentAdapter, lastAdapter.get(a));
				}
			}
			lastAdapter = nextAdapter;
		}
		int finalJolt = adapters.get(adapters.size() - 1) + 3;
		long finalCount = 0;
		for (int a : lastAdapter.keySet()) {
			if (finalJolt - a <= 3) {
				finalCount += lastAdapter.get(a);
			}
		}
		return finalCount;
	}
	
	public HashMap<Integer, Long> AddToAdapterCount(HashMap<Integer, Long> adapterCount, int adapter, long count) {
		if (adapterCount.containsKey(adapter)) {
			adapterCount.put(adapter, adapterCount.get(adapter) + count);
		}
		else {
			adapterCount.put(adapter, count);
		}
		return adapterCount;
	}
}
