package it.polimi.ingsw.ps23.server.model.initialization;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.opencsv.CSVReader;

/**
 * Provides all needed for read a CSV file and make it a list of strings to be
 * easily used in the code.
 * @author Giuseppe Mascellaro
 *
 */
public class RawObject {
	
	private List<String[]> raw;
	
	/**
	 * Opens a CSV file at the specified path location and stores
	 * its content into a list of strings.
	 * <p>
	 * Each string is a field and each set of strings is a raw in the CSV file.
	 * @param path - system path in a valid format
	 */
	public RawObject(String path) {
		try {
			raw = parseCSVFile(path);
		} catch (IOException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error loading " + path + " file.", e);
		}	
	}
	
	public List<String[]> getRawObject() {
		return raw;
	}
	
	private List<String[]> parseCSVFile(String path) throws IOException {
		CSVReader reader = new CSVReader(new FileReader(path));
		List<String[]> read = reader.readAll();
		reader.close();
		return read;
	}

}
