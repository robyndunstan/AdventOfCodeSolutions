package day02;

public class PasswordPhilosophy {
	public int CountGoodPasswords(String[] input, int policyVersion) {
		int goodPasswordCount = 0;
		for (String s : input) {
			s = s.trim();
			PasswordPolicy policy = new PasswordPolicy(s);
			String password = s.substring(s.lastIndexOf(' ') + 1);
			if (policy.CheckPassword(password, policyVersion)) {
				goodPasswordCount++;
			}
		}
		return goodPasswordCount;
	}
	
	class PasswordPolicy {
	  int index1, index2;
	  char letter;
	  
	  public PasswordPolicy() {}
	  public PasswordPolicy(String input) {
		  ParseInput(input);
	  }
	  
	  public void ParseInput(String input) {
		  int index = input.indexOf('-');
		  index1 = Integer.parseInt(input.substring(0, index));
		  input = input.substring(index + 1);
		  
		  index = input.indexOf(' ');
		  index2 = Integer.parseInt(input.substring(0, index));
		  input = input.substring(index + 1);
		  
		  letter = input.charAt(0);
	  }
	  
	  public boolean CheckPassword(String password, int policyVersion) {
		  if (policyVersion == 1) {
			  int charCount = 0;
			  for (char c : password.toCharArray()) {
				  if (c == letter) {
					  charCount++;
				  }
			  }
			  return charCount >= index1 && charCount <= index2;
		  }
		  else if (policyVersion == 2) {
			  return (password.charAt(index1 - 1) == letter || password.charAt(index2 - 1) == letter)
					  && password.charAt(index1 - 1) != password.charAt(index2 - 1);
		  }
		  else {
			  return false;
		  }
	  }
  }
}
