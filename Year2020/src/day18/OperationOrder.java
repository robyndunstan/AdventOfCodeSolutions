package day18;

public class OperationOrder {
	public static long DoCalculation(String input) {
		StringBuilder sb = new StringBuilder(input);
		while (sb.indexOf("(") > -1) {
			// Extract the innermost section inside parentheses
			StringBuilder tempOuter = new StringBuilder();
			StringBuilder tempInner = new StringBuilder();
			int startIndex = sb.indexOf("(");
			int endIndex = sb.indexOf(")");
			int tempIndex = sb.indexOf("(", startIndex + 1);
			while (tempIndex != -1 && tempIndex < endIndex) {
				startIndex = tempIndex;
				tempIndex = sb.indexOf("(", startIndex + 1);
			}
			tempOuter.append(sb.substring(0, startIndex));
			tempInner.append(sb.substring(startIndex + 1, endIndex));
			tempOuter.append(DoCalculation(tempInner.toString()));
			tempOuter.append(sb.substring(endIndex + 1));
			sb = tempOuter;
		}
		sb = new StringBuilder(sb.toString().trim());
		int index = Integer.min(sb.indexOf(" ") > -1 ? sb.indexOf(" ") : Integer.MAX_VALUE,
				Integer.min(sb.indexOf("+") > -1 ? sb.indexOf("+") : Integer.MAX_VALUE,
						sb.indexOf("*") > -1 ? sb.indexOf("*") : Integer.MAX_VALUE));
		if (index == Integer.MAX_VALUE) {
			return Integer.parseInt(sb.toString());
		}
		else {
			long result = Integer.parseInt(sb.substring(0, index));
			sb.delete(0, index);
			while (sb.length() > 0) {
				sb = new StringBuilder(sb.toString().trim());
				boolean isAddition = sb.charAt(0) == '+';
				sb = new StringBuilder(sb.substring(1).trim());
				index = Integer.min(sb.indexOf(" ") > -1 ? sb.indexOf(" ") : Integer.MAX_VALUE,
						Integer.min(sb.indexOf("+") > -1 ? sb.indexOf("+") : Integer.MAX_VALUE,
								sb.indexOf("*") > -1 ? sb.indexOf("*") : Integer.MAX_VALUE));
				int nextNumber;
				if (index == Integer.MAX_VALUE) {
					nextNumber = Integer.parseInt(sb.toString());
				}
				else {
					nextNumber = Integer.parseInt(sb.substring(0, index));
				}
				result = isAddition ? result + nextNumber : result * nextNumber;
				sb.delete(0, index);
			}
			return result;
		}
	}
	
	public long DoCalculationWithPrecedence(String input) {
		input = input.trim();
		//System.out.println("\tPre-process input: '" + input + "'");
		while (input.indexOf("(") > -1) {
			//System.out.println("\t\t'(' found");
			// Extract the innermost section inside parentheses
			int startIndex = input.indexOf("(");
			//System.out.println("\t\t\tInitial start index: " + startIndex);
			int endIndex = input.indexOf(")");
			//System.out.println("\t\t\tEnd index: " + endIndex);
			int tempIndex = input.indexOf("(", startIndex + 1);
			//System.out.println("\t\t\tInitial temp index: " + tempIndex);
			while (tempIndex != -1 && tempIndex < endIndex) {
				startIndex = tempIndex;
				//System.out.println("\t\t\t\tUpdate start index: " + startIndex);
				tempIndex = input.indexOf("(", startIndex + 1);
				//System.out.println("\t\t\t\tUpdate temp index: " + tempIndex);
			}
			String tempOuter = input.substring(0, startIndex);
			//System.out.println("\t\t\tTemp outer start: " + tempOuter);
			String tempInner = input.substring(startIndex + 1, endIndex);
			//System.out.println("\t\t\tTemp inner: " + tempInner);
			tempOuter += DoCalculationWithPrecedence(tempInner);
			//System.out.println("\t\t\tAfter inner calculation: " + tempOuter);
			if (endIndex < input.length() - 1) {
				tempOuter += input.substring(endIndex + 1);
			}
			input = tempOuter;
			//System.out.println("\t\t\tFinal modified input: " + input);
		}
		input = input.trim();
		//System.out.println("\t\tAll () resolved, input: " + input);
		int index = Integer.min(input.indexOf(" ") > -1 ? input.indexOf(" ") : Integer.MAX_VALUE,
				Integer.min(input.indexOf("+") > -1 ? input.indexOf("+") : Integer.MAX_VALUE,
						input.indexOf("*") > -1 ? input.indexOf("*") : Integer.MAX_VALUE));
		//System.out.println("\t\tEnd of first number index: " + index);
		if (index == Integer.MAX_VALUE) {
			//System.out.println("\t\tOnly a number here, returning value now");
			return Long.parseLong(input);
		}
		else {
			//System.out.println("\t\tOperators found");
			while (input.indexOf("+") > -1) {
				//System.out.println("\t\t\tAddition found");
				index = input.indexOf("+");
				//System.out.println("\t\t\tAddition operator index: " + index);
				
				String tempBefore = input.substring(0, index).trim();
				//System.out.println("\t\t\tSplit on +, before: " + tempBefore);
				int indexBefore = Integer.max(tempBefore.lastIndexOf(" "), 
						Integer.max(tempBefore.lastIndexOf("+"), tempBefore.lastIndexOf("*")));
				long numBefore = 0;
				if (indexBefore == -1) {
					numBefore = Long.parseLong(tempBefore);
					tempBefore = "";
				}
				else {
					numBefore = Long.parseLong(tempBefore.substring(indexBefore + 1));
					tempBefore = tempBefore.substring(0, indexBefore + 1);
				}
				//System.out.println("\t\t\tNumber before: " + numBefore);
				//System.out.println("\t\t\tRemaining expression before: " + tempBefore);
				
				String tempAfter = input.substring(index + 1).trim();
				//System.out.println("\t\t\tSplit on +, after: " + tempAfter);
				int indexAfter = Integer.min(tempAfter.indexOf(" ") > -1 ? tempAfter.indexOf(" ") : Integer.MAX_VALUE,
						Integer.min(tempAfter.indexOf("+") > -1 ? tempAfter.indexOf("+") : Integer.MAX_VALUE,
								tempAfter.indexOf("*") > -1 ? tempAfter.indexOf("*") : Integer.MAX_VALUE));
				long numAfter = 0;
				if (indexAfter == Integer.MAX_VALUE) {
					numAfter = Long.parseLong(tempAfter);
					tempAfter = "";
				}
				else {
					numAfter = Long.parseLong(tempAfter.substring(0, indexAfter));
					tempAfter = tempAfter.substring(indexAfter + 1);
				}
				//System.out.println("\t\t\tNumber after: " + numAfter);
				//System.out.println("\t\t\tRemaining expression after: " + tempAfter);
				
				input = tempBefore + (numBefore + numAfter) + tempAfter;
				//System.out.println("\t\t\tModified input after calculation: " + input);
			}
			//System.out.println("\t\tAll + resolved, input: " + input);
			index = Integer.min(input.indexOf(" ") > -1 ? input.indexOf(" ") : Integer.MAX_VALUE,
							input.indexOf("*") > -1 ? input.indexOf("*") : Integer.MAX_VALUE);
			if (index == Integer.MAX_VALUE) {
				//System.out.println("\t\tOnly a number here, returning value now");
				return Long.parseLong(input);
			}
			else {
				long result = Long.parseLong(input.substring(0, index));
				//System.out.println("\t\t* present, parse first number: " + result);
				input = input.substring(index).trim();
				//System.out.println("\t\tRemaining expression after first number: " + input);
				while (input.length() > 0) {
					input = input.substring(1).trim();
					//System.out.println("\t\t\tTrim off operator: " + input);
					index = Integer.min(input.indexOf(" ") > -1 ? input.indexOf(" ") : Integer.MAX_VALUE,
							input.indexOf("*") > -1 ? input.indexOf("*") : Integer.MAX_VALUE);
					long nextNumber;
					if (index == Integer.MAX_VALUE) {
						nextNumber = Long.parseLong(input);
						input = "";
					}
					else {
						nextNumber = Long.parseLong(input.substring(0, index));
						input = input.substring(index).trim();
					}
					//System.out.println("\t\t\tNext number: " + nextNumber);
					//System.out.println("\t\t\tRemaining expression: " + input);
					result *= nextNumber;
					//System.out.println("\t\t\tProduct: " + result);
				}
				//System.out.println("\t\tFinal result: " + result);
				return result;
			}
		}
	}
}
