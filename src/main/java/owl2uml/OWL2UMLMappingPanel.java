package owl2uml;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicBorders.RolloverButtonBorder;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;
import org.eclipse.eodm.owl.OWLOntology;
import org.eclipse.eodm.owl.resource.parser.OWLDocument;
import org.eclipse.eodm.owl.resource.parser.OWLParser;
import org.eclipse.eodm.owl.resource.parser.impl.OWLDocumentImpl;
import org.eclipse.eodm.owl.resource.parser.impl.OWLParserImpl;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphModel;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import owl2uml.graph.ATransformationGraph;
import owl2uml.graph.OWLClassTransformationGraph;
import owl2uml.graph.OWLDatatypePropertyTransformationGraph;
import owl2uml.graph.OWLObjectPropertyTransformationGraph;
import owl2uml.transformation.processor.MainTransformationProcessor;
import owl2uml.umlcomponents.UMLModel;

/**
 * Main panel in the frame. In includes all the buttons, design panels etc...
 * 
 * @author Ozer Ozdikis
 * @version June 2006
 */
public class OWL2UMLMappingPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	public static final String OPEN_RULE_TEXT = "Open Rule";
	public static final String SAVE_RULE_TEXT = "Save Rule";
	public static final String NEW_CLASS_TAB_TEXT = "New Class Tab";
	public static final String NEW_DATATYPE_PROPERTY_TAB_TEXT = "New DatatypeProperty Tab";
	public static final String NEW_OBJECT_PROPERTY_TAB_TEXT = "New ObjectProperty Tab";
	public static final String REMOVE_TAB_TEXT = "Remove Tab";
	public static final String RUN_TEXT = "Run";
	public static final String CONFIGURE_TEXT = "Configure";

	private JPanel upperPanel = new JPanel();
	private JTabbedPane rulesTabbedPane = new JTabbedPane();
	private JButton openButton = new JButton(new ImageIcon(GlobalVariables.ICON_OPEN));
	private JButton saveButton = new JButton(new ImageIcon(GlobalVariables.ICON_SAVE));
	private JLabel separatorLabel1 = new JLabel(new ImageIcon(GlobalVariables.ICON_SEPARATOR));
	private JButton newClassRuleButton = new JButton(new ImageIcon(GlobalVariables.ICON_NEW_CLASS_RULE));
	private JButton newDatatypePropertyRuleButton = new JButton(new ImageIcon(GlobalVariables.ICON_NEW_DATATYPE_PROPERTY_RULE));
	private JButton newObjectPropertyRuleButton = new JButton(new ImageIcon(GlobalVariables.ICON_NEW_OBJECT_PROPERTY_RULE));
	private JButton removeRuleButton = new JButton(new ImageIcon(GlobalVariables.ICON_REMOVE_RULE));
	private JButton configureButton = new JButton(new ImageIcon(GlobalVariables.ICON_CONFIGURE));
	private JButton runButton = new JButton(new ImageIcon(GlobalVariables.ICON_RUN));

	private OWL2UMLMappingFrame frame;
	private Category tracer = Logger.getLogger(OWL2UMLMappingPanel.class);

	/**
	 * constructor
	 */
	public OWL2UMLMappingPanel(OWL2UMLMappingFrame frame) {
		super();
		this.frame = frame;
		setLayout(new BorderLayout());
		this.add(upperPanel, BorderLayout.NORTH);
		this.add(rulesTabbedPane, BorderLayout.CENTER);

		createUpperPanel();

		openButton.setActionCommand(OPEN_RULE_TEXT);
		openButton.addActionListener(this);
		openButton.setToolTipText("Load Configuration");
		saveButton.setActionCommand(SAVE_RULE_TEXT);
		saveButton.addActionListener(this);
		saveButton.setToolTipText("Save Configuration");
		newClassRuleButton.setActionCommand(NEW_CLASS_TAB_TEXT);
		newClassRuleButton.addActionListener(this);
		newClassRuleButton.setToolTipText("Add OWLClass Tab");
		newDatatypePropertyRuleButton.setActionCommand(NEW_DATATYPE_PROPERTY_TAB_TEXT);
		newDatatypePropertyRuleButton.addActionListener(this);
		newDatatypePropertyRuleButton.setToolTipText("Add OWLDatatypeProperty Tab");
		newObjectPropertyRuleButton.setActionCommand(NEW_OBJECT_PROPERTY_TAB_TEXT);
		newObjectPropertyRuleButton.addActionListener(this);
		newObjectPropertyRuleButton.setToolTipText("Add OWLObjectProperty Tab");
		removeRuleButton.setActionCommand(REMOVE_TAB_TEXT);
		removeRuleButton.addActionListener(this);
		removeRuleButton.setToolTipText("Remove Tab");
		runButton.setActionCommand(RUN_TEXT);
		runButton.addActionListener(this);
		runButton.setToolTipText("Run Transformation");
		configureButton.setActionCommand(CONFIGURE_TEXT);
		configureButton.addActionListener(this);
		configureButton.setToolTipText("Configure IO Files");
	}

	/**
	 * ActionListener for new rule, remove rule, close window and run transformation
	 * actions.
	 */
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getActionCommand().equals(NEW_CLASS_TAB_TEXT))
			addNewOWLClassRuleTab();
		else if (arg0.getActionCommand().equals(NEW_DATATYPE_PROPERTY_TAB_TEXT))
			addNewOWLDatatypePropertyRuleTab();
		else if (arg0.getActionCommand().equals(NEW_OBJECT_PROPERTY_TAB_TEXT))
			addNewOWLObjectPropertyRuleTab();
		else if (arg0.getActionCommand().equals(REMOVE_TAB_TEXT))
			removeActiveRuleTab();
		else if (arg0.getActionCommand().equals(CONFIGURE_TEXT))
			configureInputOutputFiles();
		else if (arg0.getActionCommand().equals(RUN_TEXT))
			runTransformation();
		else if (arg0.getActionCommand().equals(OPEN_RULE_TEXT))
			frame.loadConfiguration();
		else if (arg0.getActionCommand().equals(SAVE_RULE_TEXT))
			frame.saveConfiguration();
		else
			tracer.error("Unhandled ActionEvent in OWL2UMLMappingPanel");
	}

	/**
	 * Saves the transformation configuration. After adding <Rules> and <tabCounter>
	 * tags, delegates the saving to each TransformationGraph instance in each Tab.
	 */
	public void saveRulesConfiguration(PrintStream printStream) {
		tracer.debug("Saving transformation configuration");
		printStream.println("<Rules>");
		printStream.println("	<tabCounter>" + ATransformationGraph.getTabCounter() + "</tabCounter>");
		printStream.println("	<inputOWLFileName>" + frame.getInputFileName() + "</inputOWLFileName>");
		printStream.println("	<outputUMLFileName>" + frame.getOutputFileName() + "</outputUMLFileName>");
		for (int i = 0; i < rulesTabbedPane.getTabCount(); i++) {
			tracer.debug("Saving tab : " + i);
			ATransformationGraph graph = (ATransformationGraph) rulesTabbedPane.getComponentAt(i);
			graph.saveRulesConfiguration(printStream);
		}
		printStream.println("</Rules>");
	}

	/**
	 * Loads the transformation configuration. After reading <Rules>, <tabCounter>
	 * and <ruleCounter> tags, for each <owlClassTab>, <owlDatatypePropertyTab> and
	 * <owlObjectPropertyTab> tag, reads the <tabTitle> and adds a new Tab with
	 * tabTitle for a TransformationGraph instance and delegates the loading it.
	 */
	public void loadRulesConfiguration(Element element) {
		tracer.debug("Loading transformation configuration");
		rulesTabbedPane.removeAll();

		NodeList tabCounterNodes = element.getElementsByTagName("tabCounter");
		Node tabCounterNode = tabCounterNodes.item(0);
		ATransformationGraph.setTabCounter((new Integer(tabCounterNode.getTextContent())).intValue());
		NodeList inputFileNameNodes = element.getElementsByTagName("inputOWLFileName");
		Node inputFileNameNode = inputFileNameNodes.item(0);
		frame.setInputFileName(inputFileNameNode.getTextContent());
		NodeList outputFileNameNodes = element.getElementsByTagName("outputUMLFileName");
		Node outputFileNameNode = outputFileNameNodes.item(0);
		frame.setOutputFileName(outputFileNameNode.getTextContent());

		NodeList tabNodes = element.getElementsByTagName("owlClassTab");
		for (int i = 0; i < tabNodes.getLength(); i++) {
			tracer.debug("Loading owlClassTab : " + i);
			Element tabNode = (Element) tabNodes.item(i);
			NodeList titleNodes = tabNode.getElementsByTagName("title");
			Node titleNode = titleNodes.item(0);
			Node titleNodeContent = titleNode.getFirstChild();
			ATransformationGraph graphPanel = addNewOWLClassRuleTab(titleNodeContent.getTextContent());
			graphPanel.loadRulesConfiguration(tabNode);
		}

		tabNodes = element.getElementsByTagName("owlDatatypePropertyTab");
		for (int i = 0; i < tabNodes.getLength(); i++) {
			tracer.debug("Loading owlDatatypePropertyTab : " + i);
			Element tabNode = (Element) tabNodes.item(i);
			NodeList titleNodes = tabNode.getElementsByTagName("title");
			Node titleNode = titleNodes.item(0);
			Node titleNodeContent = titleNode.getFirstChild();
			ATransformationGraph graphPanel = addNewOWLDatatypePropertyRuleTab(titleNodeContent.getTextContent());
			graphPanel.loadRulesConfiguration(tabNode);
		}

		tabNodes = element.getElementsByTagName("owlObjectPropertyTab");
		for (int i = 0; i < tabNodes.getLength(); i++) {
			tracer.debug("Loading owlObjectPropertyTab : " + i);
			Element tabNode = (Element) tabNodes.item(i);
			NodeList titleNodes = tabNode.getElementsByTagName("title");
			Node titleNode = titleNodes.item(0);
			Node titleNodeContent = titleNode.getFirstChild();
			ATransformationGraph graphPanel = addNewOWLObjectPropertyRuleTab(titleNodeContent.getTextContent());
			graphPanel.loadRulesConfiguration(tabNode);
		}
	}

	/**
	 * creates the panel that includes the buttons.
	 *
	 */
	private void createUpperPanel() {
		tracer.debug("createUpperPanel entered");
		openButton.setBorder(new RolloverButtonBorder(Color.BLACK, Color.BLACK, Color.GRAY, Color.GRAY));
		openButton.setPreferredSize(new Dimension(20, 20));
		openButton.setFocusPainted(false);
		saveButton.setBorder(new RolloverButtonBorder(Color.BLACK, Color.BLACK, Color.GRAY, Color.GRAY));
		saveButton.setPreferredSize(new Dimension(20, 20));
		saveButton.setFocusPainted(false);
		newClassRuleButton.setBorder(new RolloverButtonBorder(Color.BLACK, Color.BLACK, Color.GRAY, Color.GRAY));
		newClassRuleButton.setPreferredSize(new Dimension(20, 20));
		newClassRuleButton.setFocusPainted(false);
		newDatatypePropertyRuleButton
				.setBorder(new RolloverButtonBorder(Color.BLACK, Color.BLACK, Color.GRAY, Color.GRAY));
		newDatatypePropertyRuleButton.setPreferredSize(new Dimension(20, 20));
		newDatatypePropertyRuleButton.setFocusPainted(false);
		newObjectPropertyRuleButton
				.setBorder(new RolloverButtonBorder(Color.BLACK, Color.BLACK, Color.GRAY, Color.GRAY));
		newObjectPropertyRuleButton.setPreferredSize(new Dimension(20, 20));
		newObjectPropertyRuleButton.setFocusPainted(false);
		removeRuleButton.setBorder(new RolloverButtonBorder(Color.BLACK, Color.BLACK, Color.GRAY, Color.GRAY));
		removeRuleButton.setPreferredSize(new Dimension(20, 20));
		removeRuleButton.setFocusPainted(false);
		configureButton.setBorder(new RolloverButtonBorder(Color.BLACK, Color.BLACK, Color.GRAY, Color.GRAY));
		configureButton.setPreferredSize(new Dimension(20, 20));
		configureButton.setFocusPainted(false);
		runButton.setBorder(new RolloverButtonBorder(Color.BLACK, Color.BLACK, Color.GRAY, Color.GRAY));
		runButton.setPreferredSize(new Dimension(20, 20));
		runButton.setFocusPainted(false);
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		upperPanel.setLayout(new BorderLayout());
		JPanel subPanel = new JPanel(layout);
		upperPanel.add(subPanel, BorderLayout.WEST);

		c.gridy = 0;
		c.gridx = 0;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridwidth = 1;
		c.insets.top = 2;
		c.insets.left = 1;
		c.insets.bottom = 2;
		c.insets.right = 1;
		c.fill = GridBagConstraints.NONE;
		c.weighty = 0;
		c.weightx = 1;
		layout.setConstraints(openButton, c);
		subPanel.add(openButton);

		c.gridx = 1;
		layout.setConstraints(saveButton, c);
		subPanel.add(saveButton);

		c.gridx = 2;
		c.insets.left = 5;
		c.insets.right = 5;
		layout.setConstraints(separatorLabel1, c);
		subPanel.add(separatorLabel1);

		c.gridx = 3;
		c.insets.left = 1;
		c.insets.right = 1;
		layout.setConstraints(newClassRuleButton, c);
		subPanel.add(newClassRuleButton);

		c.gridx = 4;
		layout.setConstraints(newDatatypePropertyRuleButton, c);
		subPanel.add(newDatatypePropertyRuleButton);

		c.gridx = 5;
		layout.setConstraints(newObjectPropertyRuleButton, c);
		subPanel.add(newObjectPropertyRuleButton);

		c.gridx = 6;
		layout.setConstraints(removeRuleButton, c);
		subPanel.add(removeRuleButton);

		c.gridx = 7;
		layout.setConstraints(configureButton, c);
		subPanel.add(configureButton);

		c.gridx = 8;
		layout.setConstraints(runButton, c);
		subPanel.add(runButton);
	}

	/**
	 * Adds a new tab of TransformationGraph
	 */
	private ATransformationGraph addNewOWLClassRuleTab() {
		tracer.debug("Adding new tab");
		String tabTitle = "Rule" + ATransformationGraph.getTabCounter();
		ATransformationGraph.setTabCounter(ATransformationGraph.getTabCounter() + 1);
		ATransformationGraph graphPanel = addNewOWLClassRuleTab(tabTitle);
		return graphPanel;
	}

	/**
	 * Adds a new tab of TransformationGraph
	 */
	private ATransformationGraph addNewOWLClassRuleTab(String tabTitle) {
		tracer.debug("Adding tab with title : " + tabTitle);
		GraphModel graphModel;
		ATransformationGraph graphPanel;
		graphModel = new DefaultGraphModel();
		graphPanel = new OWLClassTransformationGraph(graphModel, tabTitle, frame);
		graphPanel.setCloneable(true);
		graphPanel.setInvokesStopCellEditing(true);
		graphPanel.setJumpToDefaultPort(true);
		rulesTabbedPane.addTab(graphPanel.getTabTitle(), graphPanel);
		rulesTabbedPane.setSelectedIndex(rulesTabbedPane.getComponentCount() - 1);
		graphPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		return graphPanel;
	}

	/**
	 * Adds a new tab of OWLDatatypePropertyTransformationGraph
	 */
	private ATransformationGraph addNewOWLDatatypePropertyRuleTab() {
		tracer.debug("Adding new tab");
		String tabTitle = "Rule" + ATransformationGraph.getTabCounter();
		ATransformationGraph.setTabCounter(ATransformationGraph.getTabCounter() + 1);
		ATransformationGraph graphPanel = addNewOWLDatatypePropertyRuleTab(tabTitle);
		return graphPanel;
	}

	/**
	 * Adds a new tab of OWLDatatypePropertyTransformationGraph
	 */
	private ATransformationGraph addNewOWLDatatypePropertyRuleTab(String tabTitle) {
		tracer.debug("Adding tab with title : " + tabTitle);
		GraphModel graphModel;
		ATransformationGraph graphPanel;
		graphModel = new DefaultGraphModel();
		graphPanel = new OWLDatatypePropertyTransformationGraph(graphModel, tabTitle, frame);
		graphPanel.setCloneable(true);
		graphPanel.setInvokesStopCellEditing(true);
		graphPanel.setJumpToDefaultPort(true);
		rulesTabbedPane.addTab(graphPanel.getTabTitle(), graphPanel);
		rulesTabbedPane.setSelectedIndex(rulesTabbedPane.getComponentCount() - 1);
		graphPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		return graphPanel;
	}

	/**
	 * Adds a new tab of OWLDatatypePropertyTransformationGraph
	 */
	private ATransformationGraph addNewOWLObjectPropertyRuleTab() {
		tracer.debug("Adding new tab");
		String tabTitle = "Rule" + ATransformationGraph.getTabCounter();
		ATransformationGraph.setTabCounter(ATransformationGraph.getTabCounter() + 1);
		ATransformationGraph graphPanel = addNewOWLObjectPropertyRuleTab(tabTitle);
		return graphPanel;
	}

	/**
	 * Adds a new tab of OWLDatatypePropertyTransformationGraph
	 */
	private ATransformationGraph addNewOWLObjectPropertyRuleTab(String tabTitle) {
		tracer.debug("Adding tab with title : " + tabTitle);
		GraphModel graphModel;
		ATransformationGraph graphPanel;
		graphModel = new DefaultGraphModel();
		graphPanel = new OWLObjectPropertyTransformationGraph(graphModel, tabTitle, frame);
		graphPanel.setCloneable(true);
		graphPanel.setInvokesStopCellEditing(true);
		graphPanel.setJumpToDefaultPort(true);
		rulesTabbedPane.addTab(graphPanel.getTabTitle(), graphPanel);
		rulesTabbedPane.setSelectedIndex(rulesTabbedPane.getComponentCount() - 1);
		graphPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		return graphPanel;
	}

	/**
	 * Removes the tab, which is active, from the tabbed pane.
	 */
	private void removeActiveRuleTab() {
		tracer.debug("Removing active tab");
		int selectedTabIndex = rulesTabbedPane.getSelectedIndex();
		if (selectedTabIndex == -1) {
			tracer.debug("No active tab to remove");
		} else {
			tracer.debug("Removing tab at index : " + selectedTabIndex);
			rulesTabbedPane.remove(selectedTabIndex);
		}
	}

	private void configureInputOutputFiles() {
		tracer.debug("Configure Input/Output Files Button clicked...");
		JDialog dialog = new JDialog(frame, "Configure Input/Output Files");
		ConfigureInputOutputFilesPanel configurePanel = new ConfigureInputOutputFilesPanel(dialog, frame);
		dialog.getContentPane().add(configurePanel);
		dialog.setResizable(false);
		dialog.pack();
		dialog.setModal(true);
		dialog.setVisible(true);
	}

	/**
	 * Asks for input ecore, output xmi file names. Reads the ecore file, creates an
	 * XMIModel instance and sends these to each transformationGraph. xmiModel is
	 * filled in transformationGraphs. Finally xmiModel is saved to the file.
	 */
	private void runTransformation() {
		tracer.debug("Running transformation...");
		if ((frame.getInputFileName().equals("")) || (frame.getOutputFileName().equals(""))) {
			JOptionPane.showMessageDialog(this,
					"Input or Output file is empty. Make the configuration before running transformation.",
					"Configure Input/Output files...", JOptionPane.ERROR_MESSAGE);
			return;
		}
		OWLParser parser = new OWLParserImpl();
		OWLDocument owlDoc = new OWLDocumentImpl(frame.getInputFileName(), null, true);
		parser.addOWLDocument(owlDoc);
		OWLOntology ontology = parser.parseOWLDocument(owlDoc);
		UMLModel umlModel = new UMLModel(UMLModel.generateModelID(), ontology.getLocalName(), "Java Code Model");
		Vector<ATransformationGraph> transformationGraphs = new Vector<ATransformationGraph>();
		for (int i = 0; i < rulesTabbedPane.getTabCount(); i++) {
			ATransformationGraph graphPanel = (ATransformationGraph) rulesTabbedPane.getComponentAt(i);
			transformationGraphs.add(graphPanel);
		}
		MainTransformationProcessor transformationProcessor = new MainTransformationProcessor(ontology, umlModel,
				transformationGraphs);
		transformationProcessor.runTransformation();
		try {
			FileOutputStream fileStream = new FileOutputStream(frame.getOutputFileName());
			PrintStream printStream = new PrintStream(fileStream);
			umlModel.print(printStream);
		} catch (IOException e) {
			tracer.error("Error in writing to XMI file", e);
		}
		JOptionPane.showMessageDialog(this, "Transformation is done.", "Done...", JOptionPane.INFORMATION_MESSAGE);
	}

}
