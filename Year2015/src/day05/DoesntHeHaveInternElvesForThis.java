package day05;

public class DoesntHeHaveInternElvesForThis {
	public static int sumNiceStrings(int specVersion, String[] input) {
		int niceSum = 0;
		for (String s : input) {
			if (IsNice(specVersion, s)) {
				niceSum++;
			}
		}
		return niceSum;
	}
	
	private static boolean IsNice(int specVersion, String input) {
		if (specVersion == 1) {
			int vowelCount = 0;
			boolean hasDouble = false;
			boolean hasForbidden = false;
			
			if (input.indexOf("ab") > -1 || input.indexOf("cd") > -1 || input.indexOf("pq") > -1 || input.indexOf("xy") > -1) {
				hasForbidden = true;
			}
			else {
				for (char c : input.toCharArray()) {
					if (vowelCount < 3 && "aeiou".indexOf(c) > -1) {
						vowelCount++;
					}
					
					if (!hasDouble && input.indexOf("" + c + c) > -1) {
						hasDouble = true;
					}
				}
			}
			
			if (vowelCount >= 3 && hasDouble && !hasForbidden) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			boolean hasDoubledPair = false;
			boolean hasABARepeat = false;
			
			for (int i = 0; i < input.length(); i++) {
				if (!hasDoubledPair && i < input.length() - 2) {
					String pair = input.substring(i, i + 2);
					if (input.indexOf(pair, i + 2) > -1) {
						hasDoubledPair = true;
					}
				}
				
				if (!hasABARepeat && i < input.length() - 2) {
					char c = input.charAt(i);
					if (c == input.charAt(i + 2)) {
						hasABARepeat = true;
					}
				}
			}
			
			if (hasDoubledPair && hasABARepeat) {
				return true;
			}
			else {
				return false;
			}
		}
	}
}
