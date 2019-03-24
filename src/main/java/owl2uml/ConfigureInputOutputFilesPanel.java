package owl2uml;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;

/**
 * Panel that is used to set the input OWL file and output UML file. The
 * filenames defined here, are set in variables of the OWL2UML frame instance.
 * 
 * @author Ozer Ozdikis
 * @version June 2006
 */
public class ConfigureInputOutputFilesPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_PATH = ".";
	static private String OK_COMMAND = "OK";
	static private String CLOSE_COMMAND = "Close";
	static private String INPUT_FILE_COMMAND = "Input";
	static private String OUTPUT_FILE_COMMAND = "Output";
	private JDialog ownerDialog;
	private OWL2UMLMappingFrame frame;

	private JPanel fileNamesPanel = new JPanel(new BorderLayout());
	private JPanel inputFileNamePanel = new JPanel();
	private JPanel outputFileNamePanel = new JPanel();
	private JTextField inputFileNameTextField = new JTextField();
	private JTextField outputFileNameTextField = new JTextField();
	private JButton inputFileNameButton = new JButton("Select OWL File");
	private JButton outputFileNameButton = new JButton("Select UML File");

	private JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	private JButton okButton = new JButton(new ImageIcon(GlobalVariables.ICON_SAVE));
	private JButton closeButton = new JButton(new ImageIcon(GlobalVariables.ICON_CLOSE));

	private ButtonActionListener buttonActionListener = new ButtonActionListener(this);
	private Category tracer = Logger.getLogger(ConfigureInputOutputFilesPanel.class);

	/**
	 * constructor
	 */
	public ConfigureInputOutputFilesPanel(JDialog ownerDialog, OWL2UMLMappingFrame frame) {
		super(new BorderLayout());
		this.frame = frame;
		this.ownerDialog = ownerDialog;

		inputFileNameTextField.setText(frame.getInputFileName());
		outputFileNameTextField.setText(frame.getOutputFileName());

		this.add(fileNamesPanel, BorderLayout.NORTH);
		this.add(buttonsPanel, BorderLayout.SOUTH);
		decorateFileNamesPanel();
		decorateButtonsPanel();

		inputFileNameButton.addActionListener(buttonActionListener);
		outputFileNameButton.addActionListener(buttonActionListener);
		okButton.addActionListener(buttonActionListener);
		closeButton.addActionListener(buttonActionListener);
	}

	private void decorateFileNamesPanel() {
		fileNamesPanel.add(inputFileNamePanel, BorderLayout.NORTH);
		fileNamesPanel.add(outputFileNamePanel, BorderLayout.SOUTH);
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		inputFileNamePanel.setLayout(layout);
		inputFileNamePanel.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "Configure Input OWL File"));
		outputFileNamePanel.setLayout(layout);
		outputFileNamePanel.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "Configure Output UML File"));

		inputFileNameTextField.setPreferredSize(new Dimension(240, 20));
		outputFileNameTextField.setPreferredSize(new Dimension(240, 20));
		inputFileNameButton.setPreferredSize(new Dimension(160, 20));
		outputFileNameButton.setPreferredSize(new Dimension(160, 20));
		inputFileNameButton.setActionCommand(INPUT_FILE_COMMAND);
		outputFileNameButton.setActionCommand(OUTPUT_FILE_COMMAND);

		c.gridy = 0;
		c.gridx = 0;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridwidth = 1;
		c.insets.top = 0;
		c.insets.left = 5;
		c.insets.bottom = 2;
		c.insets.right = 0;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0;
		layout.setConstraints(inputFileNameTextField, c);
		inputFileNamePanel.add(inputFileNameTextField);
		c.gridx = 1;
		layout.setConstraints(inputFileNameButton, c);
		inputFileNamePanel.add(inputFileNameButton);

		c.gridx = 0;
		layout.setConstraints(outputFileNameTextField, c);
		outputFileNamePanel.add(outputFileNameTextField);
		c.gridx = 1;
		layout.setConstraints(outputFileNameButton, c);
		outputFileNamePanel.add(outputFileNameButton);
	}

	/**
	 * function that creates and places the buttons on the GUI
	 */
	private void decorateButtonsPanel() {
		buttonsPanel.add(okButton);
		buttonsPanel.add(closeButton);
		okButton.setPreferredSize(new Dimension(20, 20));
		closeButton.setPreferredSize(new Dimension(20, 20));
		okButton.setActionCommand(OK_COMMAND);
		closeButton.setActionCommand(CLOSE_COMMAND);
	}

	/**
	 * action listener for the buttons to set input OWL filename, output UML
	 * filename and OK/CANCEL buttons whether to send these configurations to the
	 * OWL2UMLFrame instance.
	 */
	private class ButtonActionListener implements ActionListener {
		private JPanel ownerPanel;

		public ButtonActionListener(JPanel ownerPanel) {
			this.ownerPanel = ownerPanel;
		}

		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getActionCommand().equals(CLOSE_COMMAND))
				ownerDialog.dispose();
			else if (arg0.getActionCommand().equals(OK_COMMAND)) {
				tracer.debug("Setting input file name : " + inputFileNameTextField.getText());
				tracer.debug("Setting output file name : " + outputFileNameTextField.getText());
				frame.setInputFileName(inputFileNameTextField.getText());
				frame.setOutputFileName(outputFileNameTextField.getText());
				ownerDialog.dispose();
			} else if (arg0.getActionCommand().equals(INPUT_FILE_COMMAND)) {
				JFileChooser ecoreFileChooser = new JFileChooser(DEFAULT_PATH);
				ecoreFileChooser.setDialogTitle("Select Input OWL File");
				int returnVal = ecoreFileChooser.showOpenDialog(ownerPanel);
				if (returnVal != JFileChooser.APPROVE_OPTION)
					return;
				tracer.debug("Selected input Ecore file = " + ecoreFileChooser.getSelectedFile().getAbsolutePath());
				inputFileNameTextField.setText(ecoreFileChooser.getSelectedFile().getAbsolutePath());
			} else if (arg0.getActionCommand().equals(OUTPUT_FILE_COMMAND)) {
				JFileChooser xmiFileChooser = new JFileChooser(DEFAULT_PATH);
				xmiFileChooser.setDialogTitle("Select Output UML File");
				int returnVal = xmiFileChooser.showOpenDialog(ownerPanel);
				if (returnVal != JFileChooser.APPROVE_OPTION)
					return;
				tracer.debug("Selected output XMI file = " + xmiFileChooser.getSelectedFile().getAbsolutePath());
				outputFileNameTextField.setText(xmiFileChooser.getSelectedFile().getAbsolutePath());
			}
		}
	}

}