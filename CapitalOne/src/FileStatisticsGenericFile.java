import javax.swing.JTextArea;

public class FileStatisticsGenericFile extends FileStatistics{

	public FileStatisticsGenericFile () {
		
		super();
	}
	
	/**
	 * Initialize Occurrences to search for
	 */
	public void intializeOccurences() {
		
		super.intializeOccurences();
	}
		
	/**
	 * @param: String
	 * Searches line for specific characters
	 */
	public void getStatistics(String nextLine) {
		
		String line = nextLine.replaceAll("\\s+","");
		line = line.replace("\t", "");
		
		occurences.put(LINES, occurences.get(LINES) + 1);
		
		if (line.startsWith("/*") || line.startsWith("*/") || line.startsWith("*")) {
			occurences.put(TOTAL_COMMENTS, occurences.get(TOTAL_COMMENTS) + 1);
			occurences.put(BLOCK_COMMENTS, occurences.get(BLOCK_COMMENTS) + 1);
		}
		
		if (line.contains("TODO")) {
			occurences.put(TODO, occurences.get(TODO) + 1);
			occurences.put(SINGLE, occurences.get(SINGLE) + 1);
			occurences.put(TOTAL_COMMENTS, occurences.get(TOTAL_COMMENTS) + 1);
			return;
		}
		
		else if (line.startsWith("//")) {
			occurences.put(TOTAL_COMMENTS, occurences.get(TOTAL_COMMENTS) + 1);
			occurences.put(SINGLE, occurences.get(SINGLE) + 1);
		}
		
		else if (line.contains("//")) {
			occurences.put(TOTAL_COMMENTS, occurences.get(TOTAL_COMMENTS) + 1);
			occurences.put(SINGLE, occurences.get(SINGLE) + 1);
			occurences.put(SINGLE_BLOCK_LINE_COMMENTS, occurences.get(SINGLE_BLOCK_LINE_COMMENTS) + 1);
		}
	}
	
	/**
	 * @param JTextarea
	 * Given hashmap of occurences, prints them to the text area
	 */
	public void printStatistics(JTextArea printScreen) {
		
		for (String delimeter :occurences.keySet()) {
			printScreen.append("Total # of " + delimeter + ": " + occurences.get(delimeter));
			printScreen.append("\n");
		}
	}
}
