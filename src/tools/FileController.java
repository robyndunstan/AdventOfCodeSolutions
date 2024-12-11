package tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileController {
	private File f;
	private boolean exists;
	private int lineCount;
	
	private BufferedReader br;
	
	private PrintWriter pw;
	
	/**
	 * constructors
	 */
	public FileController() {}
	public FileController(File f) {
		setFile(f);
	}
	public FileController(String fname) {
		setFile(fname);
	}
	
	/**
	 * File getters/setters
	 */
	public final void setFile(File f) {
		if (f == null) {
			try {
				closeFile();
			} 
			catch (IOException e) {}
		}
		else if (!f.exists()) {
			exists = false;
			this.f = f;
		}
		else if (f.isFile()) {
			exists = true;
			this.f = f;
		}
		else {
			exists = false;
			this.f = null;
			System.out.println(f.getAbsolutePath() + " is not a valid file");
		}
	}
	public final void setFile(String fname) {
		File tempF = new File(fname);
		setFile(tempF);
	}
	public File getFile() {
		return f;
	}
	
	/**
	 * Open file
	 */
	public void openInput() throws FileNotFoundException {
		if (f == null) {
			System.out.println("No file selected");
		}
		else if (!exists) {
			br = null;
			System.out.println(f.getAbsolutePath() + " does not exist");
		}
		else {
			br = new BufferedReader(new FileReader(f));
			lineCount = 0;
		}
	}
	public void openOutput() throws IOException {
		if (f == null) {
			System.out.println("No file selected");
		}
		else {
			openPrintWriter();
		}
	}
	private void openPrintWriter() throws IOException {
		// This function assumes that the file check has already been performed elsewhere
		pw = new PrintWriter(new FileWriter(f));
		lineCount = 0;
		exists = true;
	}
	
	/**
	 * Read file
	 */
	public String readLine() throws IOException {
		String line = br.readLine();
		lineCount++;
		return line;
	}
	public int readCharacter() throws IOException {
		return br.read();
	}
	
	/**
	 * Write to file
	 */
	public void writeLine(String line) {
		pw.println(line);
		pw.flush();
		lineCount++;
	}
	public void write(String text) {
		pw.print(text);
		pw.flush();
	}
	public void writeCharacter(int c) {
		pw.write(c);
		pw.flush();
	}
	
	/**
	 * Close file
	 */
	public void closeFile() throws IOException {
		if (br != null) {
			br.close();
			br = null;
		}
		if (pw != null) {
			pw.flush();
			pw.close();
			pw = null;
		}
		lineCount = 0;
	}

	/**
	 * Delete file
	 */
	public boolean deleteFile() {
            try {
                closeFile();
            } catch (IOException ex) {}
		if (f.delete()) {
			exists = false;
			return true;
		}
		else return false;
	}
	
	/**
	 * File Status
	 */
	public boolean isOpenOutput() {
            return pw != null;
	}
	public boolean isOpenInput() {
		return br != null;
	}
	public int getLineNumber() {
		return lineCount;
	}
	public boolean isSet() {
		try {
			return f.getAbsolutePath() != null && f.getAbsolutePath().length() > 0;
		}
		catch (Exception e) {
			return false;
		}
	}
}