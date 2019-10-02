import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

public class FileChecker extends Frame {

	private static final long serialVersionUID = 1L;
	private static JTextArea printScreen;

	private static void createAndShowGUI() {

		// Create and set up the window.
		final JFrame frame = new JFrame("Centered");

		// Display the window.
		frame.setSize(500, 500);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		// set flow layout for the frame
		frame.getContentPane().setLayout(new FlowLayout());

		JButton button = new JButton("Choose file/directory");

		printScreen = new JTextArea(400, 400);
		JScrollPane vertical = new JScrollPane(printScreen);
		vertical.setPreferredSize(new Dimension(400, 400));
	    vertical.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    vertical.setVisible(true);
	    
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createFileChooser(frame);
			}
		});

		frame.getContentPane().add(button);
		frame.getContentPane().add(vertical);
	}

	private static void createFileChooser(final JFrame frame) {

		String filename = File.separator + "tmp";
		JFileChooser fileChooser = new JFileChooser(new File(filename));
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY); // only files
		
		// pop up an "Open File" file chooser dialog
		File selectedFile = null;
		int result = fileChooser.showOpenDialog(frame);
		
		if (result == JFileChooser.APPROVE_OPTION) {
			selectedFile = fileChooser.getSelectedFile();
		} 
		else {
			printScreen.setText("Invalid: File not selected.");
			return;
		}
		
		mainProgram(selectedFile);
	}

	/**
	 * Runs a continuous program that keeps prompting for a new file
	 */
	public static void mainProgram(File selectedFile) {

		printScreen.removeAll();
		FileStatistics fileStats = null;
		Optional<String> extension = getExtensionByStringHandling(selectedFile.getName());

		// Files get sorted via extension into different statistic parsers
		if (extension.isPresent()) {
			switch (extension.toString()) {
			case "java":
				fileStats = new FileStatisticsJava();
				break;
			case "py":
				fileStats = new FileStatisticsPython();
				break;
			default:
				fileStats = new FileStatisticsGenericFile();
			}
		} else {
			printScreen.setText("Invalid: File does not contain an extension.");
		}
		fileStats.checkFile(selectedFile);
		fileStats.printStatistics(printScreen);
	}

	/**
	 * 
	 * @param filename
	 * @return extension of the file. Null if no extension exists
	 */
	public static Optional<String> getExtensionByStringHandling(String filename) {
		return Optional.ofNullable(filename).filter(f -> f.contains("."))
				.map(f -> f.substring(filename.lastIndexOf(".") + 1));
	}

	public static void main(String[] args) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
