package it.polimi.ingsw.ps23.server.model.initialization;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.opencsv.CSVReader;

class RawObject {
	
	private List<String[]> raw;
	
	RawObject(String path) {
		try {
			this.raw = parseCSVFile(path);
		} catch (IOException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error loading " + path + " file.", e);
		}
	}
	
	List<String[]> parseCSVFile(String path) throws IOException {
		CSVReader reader = new CSVReader(new FileReader(path));
		List<String[]> read = reader.readAll();
		reader.close();
		return read;
	}
	
	List<String[]> getRawObject() {
		return raw;
	}
	
}
