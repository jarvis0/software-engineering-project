package it.polimi.ingsw.ps23.model;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.opencsv.CSVReader;

public class RawObject {
	
	private List<String[]> rawObject;
	
	public RawObject(String path) {
		try {
			this.rawObject = parseCSVFile(path);
		} catch (IOException e) {
			System.out.println("Error loading " + path + "file.");
		}
	}
	
	private List<String[]> parseCSVFile(String path) throws IOException {
		CSVReader reader = new CSVReader(new FileReader(path));
		List<String[]> read = reader.readAll();
		reader.close();
		return read;
	}
	
	public List<String[]> getRawObject() {
		return rawObject;
	}
	
}
