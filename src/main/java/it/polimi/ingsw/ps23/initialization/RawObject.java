package it.polimi.ingsw.ps23.initialization;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.opencsv.CSVReader;

public class RawObject {
	
	private List<String[]> raw;
	
	private Logger logger;
	
	public RawObject(String path) {
		logger = Logger.getLogger(this.getClass().getName());
		try {
			this.raw = parseCSVFile(path);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error loading " + path + "file.", e);
		}
	}
	
	private List<String[]> parseCSVFile(String path) throws IOException {
		CSVReader reader = new CSVReader(new FileReader(path));
		List<String[]> read = reader.readAll();
		reader.close();
		return read;
	}
	
	public List<String[]> getRawObject() {
		return raw;
	}
	
}
