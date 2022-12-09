package Year2022.day07;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import tools.RunPuzzle;
import tools.TestCase;

public class NoSpaceLeftOnDevice extends RunPuzzle {
	
	private File root;

	public NoSpaceLeftOnDevice(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
		this.debug = false;
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<String, Long>(1, test1File, 95437l));
		tests.add(new TestCase<String, Long>(2, test1File, 24933642l));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		System.out.println(this.defaultResultIndent + (Long)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		String filename = (String)input;
		parseFileSystem(filename);
		
		if (section == 1) {
			return sumFoldersLessThanLimit(root, 100000l, 0l);
		}
		else {
			long totalSpace = 70000000l;
			long neededSpace = 30000000l;
			long usedSpace = root.getSize();
			long availableSpace = totalSpace - usedSpace;
			long minFolderSize = neededSpace - availableSpace;
			return minFolderGreaterThanLimit(root, minFolderSize, usedSpace);
		}
	}
	
	private long minFolderGreaterThanLimit(File currentFolder, long limit, long currentMin) {
		long thisFolderSize = currentFolder.getSize();
		if (thisFolderSize >= limit) currentMin = Math.min(currentMin, thisFolderSize);
		
		for (File f : currentFolder.contents) {
			if (f.isFolder) {
				currentMin = minFolderGreaterThanLimit(f, limit, currentMin);
			}
		}
		return currentMin;
	}
	
	private long sumFoldersLessThanLimit(File currentFolder, long limit, long currentTotal) {
		long thisFolderSize = currentFolder.getSize();
		if (thisFolderSize <= limit) currentTotal += thisFolderSize;
		
		for (File f : currentFolder.contents) {
			if (f.isFolder) {
				currentTotal = sumFoldersLessThanLimit(f, limit, currentTotal);
			}
		}
		return currentTotal;
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new NoSpaceLeftOnDevice(7, "No Space Left On Device", puzzleFile);
		puzzle.run();
	}
	
	void parseFileSystem(String filename) {
		root = new File();
		root.name = "/";
		root.isFolder = true;
		
		try {
			StringBuilder currentPath = new StringBuilder();
			File currentFolder = root;
			
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line = br.readLine();
			do {
				if (this.debug) System.out.println("line : " + line);
				if (line.startsWith("$ cd ")) {
					String newFolderName = line.substring("$ cd ".length());
					if (newFolderName.equals("/")) {
						currentFolder = root;
						currentPath = new StringBuilder("/");
						if (this.debug) System.out.println("\tSet path: " + currentPath);
					}
					else if (newFolderName.equals("..")) {
						int slashIndex = currentPath.lastIndexOf("/");
						if (slashIndex > 0) {
							currentPath.delete(slashIndex, currentPath.length());
							currentFolder = root.findFile(currentPath.toString());
							if (this.debug) System.out.println("\tSet path: " + currentPath);
						}
						else if (slashIndex == 0) {
							currentFolder = root;
							currentPath = new StringBuilder("/");
							if (this.debug) System.out.println("\tSet path: " + currentPath);
						}
					}
					else {
						currentPath.append((currentPath.toString().equals("/") ? "" : "/") + newFolderName);
						File newFolder = currentFolder.findFile(newFolderName);
						if (newFolder == null) {
							newFolder = new File();
							newFolder.isFolder = true;
							newFolder.name = newFolderName;
							currentFolder.contents.add(newFolder);
						}
						currentFolder = newFolder;
						if (this.debug) System.out.println("\tSet path: " + currentPath);
					}
				}
				else if (line.startsWith("dir ")) {
					String newFolderName = line.substring("dir ".length());
					File newFolder = currentFolder.findFile(newFolderName);
					if (newFolder == null) {
						newFolder = new File();
						newFolder.isFolder = true;
						newFolder.name = newFolderName;
						currentFolder.contents.add(newFolder);
					}
					if (this.debug) System.out.println("\tCreate folder " + currentPath + (currentPath.toString().equals("/") ? "" : "/") + newFolderName);
				}
				else if (!line.startsWith("$")) { // should only be "#### filename"
					int spaceIndex = line.indexOf(" ");
					long size = Long.parseLong(line.substring(0, spaceIndex));
					String newFileName = line.substring(spaceIndex + 1);
					File newFile = currentFolder.findFile(newFileName);
					if (newFile == null) {
						newFile = new File();
						newFile.isFolder = false;
						newFile.name = newFileName;
						newFile.setSize(size);
						currentFolder.contents.add(newFile);
					}
					if (this.debug) System.out.println("\tCreate file " + currentPath + (currentPath.toString().equals("/") ? "" : "/") + newFileName);
				}
				// ignore "$ ls"
				
				line = br.readLine();
			} while (line != null);
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
