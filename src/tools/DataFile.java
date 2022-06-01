package tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DataFile {
	private String filename;
	private File file;
	private BufferedReader br;
	private ArrayList<String> data;

	public DataFile() { }
	public DataFile(String filename) {
		this();
		setFilename(filename);
	}

	public boolean setFilename(String filename) {
		this.filename = filename;
		file = new File(filename);
		return file.isFile();
	}
	
	public String[] getData() {
		if (file == null || !file.isFile()) {
			return null;
		}
		else if (data != null) {
			return data.toArray(new String[0]);
		}
		else {
			try {
				br = new BufferedReader(new FileReader(file));
				data = new ArrayList<String>();
				String line = br.readLine();
				while (line != null) {
					data.add(line);
					line = br.readLine();
				}
				br.close();
				return data.toArray(new String[0]);
			}
			catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}
	
	public void resetData() {
		data = null;
	}
	public void resetFile() {
		resetData();
		file = null;
		filename = "";
		try {
			br.close();
			br = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
