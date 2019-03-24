package owl2uml;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.PropertyConfigurator;
import org.w3c.dom.Document;

/**
 * Main dialog frame of the tool
 * 
 * @author Ozer Ozdikis
 * @version June 2006
 */
public class OWL2UMLMappingFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_PATH = ".";
	private OWL2UMLMappingPanel mainPanel = new OWL2UMLMappingPanel(this);
	private JMenuBar menuBar = new JMenuBar();
	private JMenu fileMenu = new JMenu("File");
	private JMenuItem saveMenuItem = new JMenuItem("Save");
	private JMenuItem loadMenuItem = new JMenuItem("Load");
	private JMenuItem closeMenuItem = new JMenuItem("Close");
	private String inputFileName = "";
	private String outputFileName = "";

	public OWL2UMLMappingFrame() {
		super("OWL2UML");
		getContentPane().add(mainPanel);
		fileMenu.add(saveMenuItem);
		fileMenu.add(loadMenuItem);
		fileMenu.add(closeMenuItem);
		menuBar.add(fileMenu);
		setJMenuBar(menuBar);
		saveMenuItem.addActionListener(this);
		loadMenuItem.addActionListener(this);
		closeMenuItem.addActionListener(this);
	}

	/**
	 * main function
	 */
	public static void main(String[] args) {
		try {
			configureLog4j();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		OWL2UMLMappingFrame mappingFrame = new OWL2UMLMappingFrame();
		mappingFrame.setSize(new Dimension(600, 400));
		mappingFrame.setVisible(true);
		mappingFrame.repaint();
		mappingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	private static void configureLog4j() throws MalformedURLException {
		ClassLoader classLoader = OWL2UMLMappingFrame.class.getClassLoader();
		File file = new File(classLoader.getResource("log4j.properties").getFile());
		if (file.exists()) {
			URI uri = file.toURI();
			if (uri != null) {
				URL log4jURL = uri.toURL();
				PropertyConfigurator.configure(log4jURL);
			}
		}
	}

	/**
	 * Returns the OWL2UMLMappingPanel instance displayed on this frame
	 */
	public OWL2UMLMappingPanel getMainPanel() {
		return mainPanel;
	}

	/**
	 * Action Listener function for Save, Load and Close operations
	 */
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getActionCommand().equals("Save")) {
			saveConfiguration();
		} else if (arg0.getActionCommand().equals("Load")) {
			loadConfiguration();
		} else if (arg0.getActionCommand().equals("Close")) {
			this.dispose();
		}
	}

	/**
	 * Saves the current transformation configuration to a given file
	 */
	public void saveConfiguration() {
		JFileChooser rulesFileChooser = new JFileChooser(DEFAULT_PATH);
		rulesFileChooser.setDialogTitle("Save Rule Configuration");
		int returnVal = rulesFileChooser.showOpenDialog(this);
		if (returnVal != JFileChooser.APPROVE_OPTION)
			return;
		FileOutputStream fileStream;
		try {
			fileStream = new FileOutputStream(rulesFileChooser.getSelectedFile().getAbsolutePath());
			PrintStream printStream = new PrintStream(fileStream);
			mainPanel.saveRulesConfiguration(printStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads the transformation configuration in a given file to the tool.
	 */
	public void loadConfiguration() {
		JFileChooser rulesFileChooser = new JFileChooser(DEFAULT_PATH);
		rulesFileChooser.setDialogTitle("Save Rule Configuration");
		int returnVal = rulesFileChooser.showOpenDialog(this);
		if (returnVal != JFileChooser.APPROVE_OPTION)
			return;
		try {
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(rulesFileChooser.getSelectedFile().getAbsolutePath());
			mainPanel.loadRulesConfiguration(document.getDocumentElement());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Function that sets the name of the input owl file
	 * 
	 * @param fileName
	 *            owl file name
	 */
	public void setInputFileName(String fileName) {
		this.inputFileName = fileName;
	}

	/**
	 * Function that returns the name of the input owl file
	 * 
	 * @return owl file name
	 */
	public String getInputFileName() {
		return inputFileName;
	}

	/**
	 * Function that sets the name of the output uml file
	 * 
	 * @param fileName
	 *            uml file name
	 */
	public void setOutputFileName(String fileName) {
		this.outputFileName = fileName;
	}

	/**
	 * Function that returns the name of the output uml file
	 * 
	 * @return uml file name
	 */
	public String getOutputFileName() {
		return outputFileName;
	}
}
