package day06;

import java.util.ArrayList;

public class CustomCustoms {
	ArrayList<GroupAnswers> groups;
	
	public CustomCustoms() { groups = new ArrayList<GroupAnswers>(); }
	public CustomCustoms(String[] input) {
		ParseInput(input);
	}
	
	public void PrintAnswers() {
		for (GroupAnswers g : groups) {
			System.out.println("\t" + g.toString());
		}
	}
	
	public int SumTrueAnswers() {
		int count = 0;
		for (GroupAnswers g : groups) {
			count += g.GetTrueAnswerCount();
		}
		return count;
	}
	
	public void ParseInput(String[] input) {
		groups = new ArrayList<GroupAnswers>();
		GroupAnswers currentGroup = new GroupAnswers();
		for (String s : input) {
			s = s.trim();
			if (s.length() > 0) {
				currentGroup.AddAnswers(s);
			}
			else {
				groups.add(currentGroup);
				currentGroup = new GroupAnswers();
			}
		}
		groups.add(currentGroup);
	}
	
	public void ParseInput2(String[] input) {
		groups = new ArrayList<GroupAnswers>();
		GroupAnswers currentGroup = new GroupAnswers();
		for (String s : input) {
			s = s.trim();
			if (s.length() == 0) {
				groups.add(currentGroup);
				currentGroup = new GroupAnswers();
			}
			else if (!currentGroup.init) {
				currentGroup.AddAnswers(s);
			}
			else {
				currentGroup.IntersectAnswers(s);
			}
		}
		groups.add(currentGroup);
	}
	
	class GroupAnswers {
		boolean[] answeredTrue;
		public boolean init;
		
		public GroupAnswers() {
			answeredTrue = new boolean[26];
			init = false;
		}
		
		public String toString() {
			StringBuilder b = new StringBuilder();
			for (int i = 0; i < 26; i++) {
				if (answeredTrue[i]) b.append((char)('a' + i));
			}
			return b.toString();
		}

		public void AddAnswers(String answers) {
			for (char c : answers.toCharArray()) {
				answeredTrue[c - 'a'] = true;
			}
			init = true;
		}
		
		public void IntersectAnswers(String answers) {
			boolean[] answers1 = answeredTrue;
			boolean[] answers2 = new boolean[26];
			for (char c : answers.toCharArray()) {
				answers2[c - 'a'] = true;
			}
			answeredTrue = new boolean[26];
			for (int i = 0; i < 26; i++) {
				answeredTrue[i] = answers1[i] && answers2[i];
			}
		}
		
		public int GetTrueAnswerCount() {
			int count = 0;
			for (boolean b : answeredTrue) {
				if (b) count++;
			}
			return count;
		}
	}
}
