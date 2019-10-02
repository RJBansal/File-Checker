import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTextArea;

public abstract class FileStatistics {

	protected final String LINES = "lines";
	protected final String SINGLE = "single line comments";
	protected final String TOTAL_COMMENTS = "comment lines";
	protected final String BLOCK_COMMENTS = "comment lines within block comments";
	protected final String SINGLE_BLOCK_LINE_COMMENTS = "block line comments";
	protected final String TODO = "TODO's";
	
	protected Map<String, Integer> occurences;
	
	public FileStatistics () {
		
		occurences = new HashMap<String, Integer>();
		intializeOccurences();	
	}
	
	/**
	 * Initialize occurrences adds keywords to search for inside occurrences hashmap
	 */
	protected void intializeOccurences() {
			
		occurences.put(TOTAL_COMMENTS, 0);
		occurences.put(SINGLE, 0);
		occurences.put(BLOCK_COMMENTS, 0);
		occurences.put(SINGLE_BLOCK_LINE_COMMENTS, 0);
		occurences.put(TODO, 0);
		occurences.put(LINES, 0);
	}
	
	/**
	 * 
	 * @param file
	 * Searches context of file for specific delimiters and keywords
	 */
	public void checkFile(File file) {	

		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader br = new BufferedReader(fileReader);
			String line = br.readLine();			
			
			while (line != null) {		
	 
				getStatistics(line);	
				line = br.readLine();
			}
			
			fileReader.close();
			
		} catch (FileNotFoundException e1) {
			System.out.println("Not found");
		} catch (IOException e) {
			System.out.println("IO Exception");
		}
	}
	
	protected abstract void getStatistics(String line);	
	protected abstract void printStatistics(JTextArea printScreen);
}
