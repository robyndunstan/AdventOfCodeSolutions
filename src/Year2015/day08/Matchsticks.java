package Year2015.day08;

import java.io.BufferedReader;
import java.io.FileReader;

public class Matchsticks {
	private String filename;
	private int codeLength, dataLength, encodedLength;
	
	public Matchsticks() {
		reset();
	}
	public Matchsticks(String filename) {
		setFilename(filename);
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
		reset();
		processFile();
	}
	
	private void reset() {
		codeLength = 0;
		dataLength = 0;
		encodedLength = 0;
	}
	
	public void processFile() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			
			String line = br.readLine();
			do {
				codeLength += line.length();
				dataLength += getDataLength(line);
				encodedLength += getEncodedLength(line);
				line = br.readLine();
			} while (line != null);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getAnswer(int section) {
		if (section == 1) {
			return codeLength - dataLength;
		}
		else if (section == 2) {
			return encodedLength - codeLength;
		}
		else {
			return Integer.MIN_VALUE;
		}
	}
	
	static int getDataLength(String s) {
		int l = s.length();
		
		// " at beginning and end
		l -= 2;
		
		int prevInd = 0;
		while (s.indexOf('\\', prevInd) > -1) {
			if (s.charAt(s.indexOf('\\', prevInd) + 1) == 'x') {
				l -= 3;
				prevInd = s.indexOf('\\', prevInd) + 4;
			}
			else {
				l -= 1;
				prevInd = s.indexOf('\\', prevInd) + 2;
			}
		}
		
		return l;
	}
	
	static int getEncodedLength(String s) {
		int l = s.length();
		
		// "\ at beginning and end
		l += 4;
		
		int prevInd = 0;
		while (s.indexOf('\\', prevInd) > -1) {
			if (s.charAt(s.indexOf('\\', prevInd) + 1) == 'x') {
				l += 1;
				prevInd = s.indexOf('\\', prevInd) + 4;
			}
			else {
				l += 2;
				prevInd = s.indexOf('\\', prevInd) + 2;
			}
		}
		
		return l;
	}
}
