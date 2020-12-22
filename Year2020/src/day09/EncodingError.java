package day09;

import java.util.ArrayList;

public class EncodingError {
	ArrayList<Long> data;
	public int preamble;

	public void ParseData(String[] input) {
		data = new ArrayList<Long>();
		for (String s : input) {
			s = s.trim();
			data.add(Long.parseLong(s));
		}
	}
	
	public long FindFirstInvalidNumber() {
		for (int i = preamble; i < data.size(); i++) {
			long sum = data.get(i);
			boolean foundSum = false;
			for (int j = i - 1; j >= i - preamble + 1; j--) {
				for (int k = j - 1; k >= i - preamble; k--) {
					if (data.get(j) + data.get(k) == sum) {
						foundSum = true;
						break;
					}
				}
				if (foundSum) break;
			}
			if (!foundSum) return sum;
		}
		return -1;
	}
	
	public long FindEncryptionWeakness() {
		long searchSum = FindFirstInvalidNumber();
		//System.out.println("\tSearch sum: " + searchSum);
		for (int startIndex = 0; startIndex < data.size() - 1; startIndex++) {
			long sum = data.get(startIndex);
			int endIndex = startIndex + 1;
			while (sum < searchSum && endIndex < data.size()) {
				sum += data.get(endIndex);
				//System.out.println("\t\tTemp sum: " + sum + " Indices: " + startIndex + ", " + endIndex);
				if (sum == searchSum) {
					long smallest = data.get(startIndex);
					long largest = data.get(startIndex);
					for (int i = startIndex; i <= endIndex; i++) {
						smallest = Long.min(smallest, data.get(i));
						largest = Long.max(largest, data.get(i));
					}
					return smallest + largest;
				}
				endIndex++;
			}
		}
		return 0;
	}
}
