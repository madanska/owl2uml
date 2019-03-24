package owl2uml.transformation.mapping.panel;

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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;

import owl2uml.GlobalVariables;
import owl2uml.graph.cell.UMLAssociationGraphCell;
import owl2uml.transformation.mapping.OWLObjectPropertyUMLAssociationTransformationMapping;

/**
 * @author Ozer Ozdikis
 * @version June 2006
 */
public class OWLObjectPropertyUMLAssociationTransformationMappingPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	protected static String OK_COMMAND = "OK";
	protected static String CLOSE_COMMAND = "Close";
	protected JDialog ownerDialog;

	protected JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	protected JButton okButton = new JButton(new ImageIcon(GlobalVariables.ICON_SAVE));
	protected JButton closeButton = new JButton(new ImageIcon(GlobalVariables.ICON_CLOSE));

	protected ButtonActionListener buttonActionListener = new ButtonActionListener();
	protected Category tracer = Logger.getLogger(OWLObjectPropertyUMLAssociationTransformationMappingPanel.class);

	private JPanel variablesPanel = new JPanel(new BorderLayout());
	private JLabel umlConstructLabel = new JLabel("Association Type");
	private JComboBox<String> umlConstructComboBox = new JComboBox<String>();
	private JLabel owlConstructLabel = new JLabel("OWL Construct");
	private JComboBox<String> owlConstructComboBox = new JComboBox<String>();
	private JLabel ifConditionLabel = new JLabel("if condition");
	private JComboBox<String> ifConditionComboBox = new JComboBox<String>();
	private JTextField ifConditionTextField = new JTextField();

	private UMLAssociationGraphCell umlAssociationGraphCell;

	public OWLObjectPropertyUMLAssociationTransformationMappingPanel(JDialog ownerDialog,
			UMLAssociationGraphCell umlAssociationGraphCell) {
		super(new BorderLayout());
		this.ownerDialog = ownerDialog;
		this.umlAssociationGraphCell = umlAssociationGraphCell;

		initializeVariables();
		decorateVariablesPanel();
		decorateButtonsPanel();

		okButton.addActionListener(buttonActionListener);
		closeButton.addActionListener(buttonActionListener);
	}

	protected void decorateButtonsPanel() {
		buttonsPanel.add(okButton);
		buttonsPanel.add(closeButton);
		okButton.setPreferredSize(new Dimension(20, 20));
		closeButton.setPreferredSize(new Dimension(20, 20));
		okButton.setActionCommand(OK_COMMAND);
		closeButton.setActionCommand(CLOSE_COMMAND);
		this.add(buttonsPanel, BorderLayout.SOUTH);
	}

	private void decorateVariablesPanel() {
		JPanel variablesPanel1 = new JPanel();
		variablesPanel.add(variablesPanel1, BorderLayout.NORTH);
		variablesPanel.add(new JPanel(), BorderLayout.CENTER);
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		variablesPanel1.setLayout(layout);
		variablesPanel1.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "UML Association Mapping"));

		umlConstructLabel.setPreferredSize(new Dimension(240, 20));
		umlConstructComboBox.setPreferredSize(new Dimension(240, 20));
		owlConstructLabel.setPreferredSize(new Dimension(200, 20));
		owlConstructComboBox.setPreferredSize(new Dimension(200, 20));
		ifConditionLabel.setPreferredSize(new Dimension(120, 20));
		ifConditionComboBox.setPreferredSize(new Dimension(120, 20));
		ifConditionTextField.setPreferredSize(new Dimension(120, 20));

		c.gridy = 0;
		c.gridx = 0;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridwidth = 1;
		c.insets.left = 5;
		c.insets.bottom = 2;
		c.insets.right = 0;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0;
		c.insets.top = 5;
		layout.setConstraints(umlConstructLabel, c);
		variablesPanel1.add(umlConstructLabel);
		c.gridx = 1;
		layout.setConstraints(owlConstructLabel, c);
		variablesPanel1.add(owlConstructLabel);
		c.gridx = 2;
		layout.setConstraints(ifConditionLabel, c);
		variablesPanel1.add(ifConditionLabel);
		c.gridy = 1;
		c.gridx = 0;
		c.insets.top = 0;
		layout.setConstraints(umlConstructComboBox, c);
		variablesPanel1.add(umlConstructComboBox);
		c.gridx = 1;
		layout.setConstraints(owlConstructComboBox, c);
		variablesPanel1.add(owlConstructComboBox);
		c.gridx = 2;
		layout.setConstraints(ifConditionComboBox, c);
		variablesPanel1.add(ifConditionComboBox);
		c.gridx = 3;
		layout.setConstraints(ifConditionTextField, c);
		variablesPanel1.add(ifConditionTextField);
		this.add(variablesPanel, BorderLayout.CENTER);
	}

	protected void initializeVariables() {
		umlConstructComboBox.addItem(GlobalVariables.UML_EMPTY);
		umlConstructComboBox.addItem(GlobalVariables.UML_ASSOCIATION_DIRECTED);
		umlConstructComboBox.addItem(GlobalVariables.UML_ASSOCIATION_AGGREGATION);
		umlConstructComboBox.addItem(GlobalVariables.UML_ASSOCIATION_COMPOSITION);

		owlConstructComboBox.addItem(GlobalVariables.OWL_SUPERPROPERTY_NAME);
		owlConstructComboBox.addItem(GlobalVariables.OWL_DOMAIN);
		owlConstructComboBox.addItem(GlobalVariables.OWL_RANGE);

		ifConditionComboBox.addItem(GlobalVariables.IF_NO_CONDITION);
		ifConditionComboBox.addItem(GlobalVariables.IF_REGULAR_EXPRESSION);
		ifConditionComboBox.addItem(GlobalVariables.IF_STRING_EQUALS);
		ifConditionComboBox.addItem(GlobalVariables.IF_STRING_BEGINS_WITH);
		ifConditionComboBox.addItem(GlobalVariables.IF_STRING_ENDS_WITH);
		ifConditionComboBox.addItem(GlobalVariables.IF_STRING_INCLUDES);
		ifConditionComboBox.addItem(GlobalVariables.IF_STRING_NOT_EQUALS);
		ifConditionComboBox.addItem(GlobalVariables.IF_STRING_NOT_BEGINS_WITH);
		ifConditionComboBox.addItem(GlobalVariables.IF_STRING_NOT_ENDS_WITH);
		ifConditionComboBox.addItem(GlobalVariables.IF_STRING_NOT_INCLUDES);
	}

	public void readTransformationMapping(OWLObjectPropertyUMLAssociationTransformationMapping mapping) {
		this.umlConstructComboBox.setSelectedItem(mapping.getUmlVariable());
		this.owlConstructComboBox.setSelectedItem(mapping.getOwlVariable());
		this.ifConditionComboBox.setSelectedItem(mapping.getIfCondition());
		this.ifConditionTextField.setText(mapping.getIfConditionValue());
	}

	public void writeTransformationMapping() {
		OWLObjectPropertyUMLAssociationTransformationMapping mapping = new OWLObjectPropertyUMLAssociationTransformationMapping();
		tracer.debug("Creating new OWLObjectPropertyUMLAssociationTransformationMapping instance");
		mapping.setUmlVariable((String) umlConstructComboBox.getSelectedItem());
		mapping.setOwlVariable((String) owlConstructComboBox.getSelectedItem());
		mapping.setIfCondition((String) ifConditionComboBox.getSelectedItem());
		mapping.setIfConditionValue(ifConditionTextField.getText());
		umlAssociationGraphCell.setOwlObjectPropertyUmlAssociationTransformationMapping(mapping);
	}

	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getActionCommand().equals(CLOSE_COMMAND))
				ownerDialog.dispose();
			else if (arg0.getActionCommand().equals(OK_COMMAND)) {
				writeTransformationMapping();
				ownerDialog.dispose();
			}
		}
	}
}
