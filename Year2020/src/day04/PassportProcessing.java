package day04;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PassportProcessing {
	ArrayList<Passport> passports;
	
	private enum EYE_COLOR {
		amb, blu, brn, gry, grn, hzl, oth
	}
	
	public PassportProcessing() {}
	public PassportProcessing(String[] input) {
		ParsePassports(input);
	}
	
	public void ParsePassports(String[] input) {
		passports = new ArrayList<Passport>();
		Passport p = new Passport();
		for (String s : input) {
			s = s.trim();
			if (s.isEmpty()) {
				passports.add(p);
				p = new Passport();
			}
			else {
				p.ParseLine(s);
			}
		}
		passports.add(p);
	}
	
	public int ValidPassportCount() {
		int count = 0;
		int total = 0;
		for (Passport p : passports) {
			System.out.println("\tChecking passport " + total);
			total++;
			if (p.IsValid()) count++;
		}
		return count;
	}
	
	class Passport {
		public String BirthYear = ""; // byr
		public String IssueYear = ""; // iyr
		public String ExpirationYear = ""; // eyr
		public String Height = ""; // hgt
		public String HairColor = ""; // hcl
		public String EyeColor = ""; // ecl
		public String PassportId = ""; // pid
		public String CountryId = ""; // cid

		public void ParseLine(String line) {
			line = line.trim();
			String[] properties = line.split(" ");
			for (String p : properties) {
				String[] fieldValue = p.split(":");
				switch(fieldValue[0]) {
				case "byr":
					BirthYear = fieldValue[1];
					break;
				case "iyr": 
					IssueYear = fieldValue[1];
					break;
				case "eyr":
					ExpirationYear = fieldValue[1];
					break;
				case "hgt":
					Height = fieldValue[1];
					break;
				case "hcl":
					HairColor = fieldValue[1];
					break;
				case "ecl":
					EyeColor = fieldValue[1];
					break;
				case "pid":
					PassportId = fieldValue[1];
					break;
				case "cid":
					CountryId = fieldValue[1];
					break;
				}
			}
		}
		
		public boolean IsValid() {
			if (!ValidateYear(BirthYear, 4, 1920, 2002)) {
				System.out.println("\t\tFailed on Birth Year " + BirthYear);
				return false;
			}
			else if (!ValidateYear(IssueYear, 4, 2010, 2020)) {
				System.out.println("\t\tFailed on Issued Year " + IssueYear);
				return false;
			}
			else if (!ValidateYear(ExpirationYear, 4, 2020, 2030)) {
				System.out.println("\t\tFailed on Expiration Year " + ExpirationYear);
				return false;
			}
			else if (!ValidateHeight()) {
				System.out.println("\t\tFailed on Height " + Height);
				return false;
			}
			else if (!ValidateColor(HairColor)) {
				System.out.println("\t\tFailed on Hair Color " + HairColor);
				return false;
			}
			else if (!ValidateEyeColor()) {
				System.out.println("\t\tFailed on Eye Color " + EyeColor);
				return false;
			}
			else if (!ValidatePassportId()) {
				System.out.println("\t\tFailed on Passport ID " + PassportId);
				return false;
			}
			else {
				System.out.println("\t\tValid Passport");
				return true;
			}
		}
		
		private boolean ValidateYear(String value, int digits, int min, int max) {
			if (value.length() != digits) {
				return false;
			}
			else {
				int valueInt = Integer.parseInt(value);
				return min <= valueInt && max >= valueInt;
			}
		}
		
		private boolean ValidateHeight() {
			if (Height.isBlank() || Height.length() <= 2) {
				return false;
			}
			else {
				String unit = Height.substring(Height.length() - 2);
				int value = Integer.parseInt(Height.substring(0, Height.length() - 2));
				if (unit.contentEquals("cm")) {
					return value >= 150 && value <= 193;
				}
				else if (unit.contentEquals("in")) {
					return value >= 59 && value <= 76;
				}
				else {
					return false;
				}
			}
		}
		
		private boolean ValidateColor(String value) {
			Pattern color = Pattern.compile("^#[0-9a-f]{6}$");
			Matcher m = color.matcher(value);
			return m.find();
		}
		
		private boolean ValidateEyeColor() {
			try {
				EYE_COLOR.valueOf(EyeColor);
				return true;
			}
			catch (Exception e) {
				return false;
			}
		}
		
		private boolean ValidatePassportId() {
			Pattern nineDigits = Pattern.compile("^[0-9]{9}$");
			Matcher m = nineDigits.matcher(PassportId);
			return m.find();
		}
	}
}
