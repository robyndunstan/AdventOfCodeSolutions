package Year2022.day07;

import java.util.ArrayList;

import tools.RunPuzzle;
import tools.TestCase;

public class NoSpaceLeftOnDevice extends RunPuzzle {
	
	private File root;

	public NoSpaceLeftOnDevice(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<String, Long>(1, test1File, 95437l));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		System.out.println(this.defaultResultIndent + (Long)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new NoSpaceLeftOnDevice(7, "No Space Left On Device", puzzleFile);
		puzzle.run();
	}
	
	void parseFileSystem(String filename) {
		
	}
	
	private static class File {
		boolean isFolder;
		String name;
		ArrayList<File> contents;
		private long size;
		
		public File() {
			contents = new ArrayList<File>();
		}
		
		public long getSize() {
			if (isFolder) {
				long totalSize = 0l;
				for (File f : contents) {
					totalSize += f.getSize();
				}
				return totalSize;
			}
			else {
				return size;
			}
		}
		public void setSize(long size) {
			if (!isFolder) this.size = size;
		}
		
		public File findFile(String path) {
			if (path.charAt(0) == '/') {
				path = path.substring(1);
			}
			
			if (!isFolder) {
				return name.equals(path) ? this : null;
			}
			
			int slashIndex = path.indexOf("/");
			String searchName = slashIndex > -1 ? path.substring(0, slashIndex) : path;
			for (File f : contents) {
				if (f.name.equals(searchName)) {
					return slashIndex > -1 ? f.findFile(path.substring(slashIndex)) : f;
				}
			}
			return null;
		}
	}

	static String test1File = "src\\Year2022\\day07\\data\\test1File";
	static String puzzleFile = "src\\Year2022\\day07\\data\\puzzleFile";

}
