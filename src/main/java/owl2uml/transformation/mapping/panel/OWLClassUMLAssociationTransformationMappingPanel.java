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
import javax.swing.JCheckBox;
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
import owl2uml.transformation.mapping.OWLClassUMLAssociationTransformationMapping;

/**
 * @author Ozer Ozdikis
 * @version June 2006
 */
public class OWLClassUMLAssociationTransformationMappingPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	protected static String OK_COMMAND = "OK";
	protected static String CLOSE_COMMAND = "Close";
	protected JDialog ownerDialog;

	protected JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	protected JButton okButton = new JButton(new ImageIcon(GlobalVariables.ICON_SAVE));
	protected JButton closeButton = new JButton(new ImageIcon(GlobalVariables.ICON_CLOSE));

	protected ButtonActionListener buttonActionListener = new ButtonActionListener();
	protected Category tracer = Logger.getLogger(OWLClassUMLAssociationTransformationMappingPanel.class);

	private JPanel variablesPanel = new JPanel(new BorderLayout());
	private JLabel umlConstructLabel = new JLabel("Association Type");
	private JComboBox<String> umlConstructComboBox = new JComboBox<String>();
	private JPanel umlMultiplicityPanel = new JPanel();
	private JLabel umlMultiplicityLabel = new JLabel("Use Multiplicity");
	private JCheckBox umlMultiplicityCheckBox = new JCheckBox();
	private JComboBox<String> umlMultiplicityTypeComboBox = new JComboBox<String>();
	private JComboBox<String> umlMultiplicityEndComboBox = new JComboBox<String>();
	private JLabel owlConstructLabel1 = new JLabel("OWL Construct");
	private JComboBox<String> owlConstructComboBox1 = new JComboBox<String>();
	private JComboBox<String> owlConstructComboBox2 = new JComboBox<String>();
	private JComboBox<String> owlConstructComboBox3 = new JComboBox<String>();
	private JLabel ifConditionLabel1 = new JLabel("if condition");
	private JComboBox<String> ifConditionComboBox1 = new JComboBox<String>();
	private JTextField ifConditionValueTextField1 = new JTextField();
	private JComboBox<String> ifConditionComboBox2 = new JComboBox<String>();
	private JTextField ifConditionValueTextField2 = new JTextField();

	private UMLAssociationGraphCell umlAssociationGraphCell;
	private UMLConstructActionListener umlConstructActionListener = new UMLConstructActionListener();
	private OWLConstructActionListener owlConstructActionListener = new OWLConstructActionListener();

	public OWLClassUMLAssociationTransformationMappingPanel(JDialog ownerDialog,
			UMLAssociationGraphCell umlAssociationGraphCell) {
		super(new BorderLayout());
		this.ownerDialog = ownerDialog;
		this.umlAssociationGraphCell = umlAssociationGraphCell;

		initializeVariables();
		decorateVariablesPanel();
		decorateButtonsPanel();

		umlMultiplicityPanel.setVisible(false);
		owlConstructComboBox3.setVisible(false);
		ifConditionComboBox2.setVisible(false);
		ifConditionValueTextField2.setVisible(false);
		owlConstructComboBox2.setVisible(false);
		umlConstructComboBox.addActionListener(umlConstructActionListener);
		owlConstructComboBox1.addActionListener(owlConstructActionListener);
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
		umlMultiplicityLabel.setPreferredSize(new Dimension(80, 20));
		umlMultiplicityCheckBox.setPreferredSize(new Dimension(20, 20));
		umlMultiplicityTypeComboBox.setPreferredSize(new Dimension(60, 20));
		umlMultiplicityEndComboBox.setPreferredSize(new Dimension(60, 20));
		owlConstructLabel1.setPreferredSize(new Dimension(200, 20));
		owlConstructComboBox1.setPreferredSize(new Dimension(200, 20));
		owlConstructComboBox2.setPreferredSize(new Dimension(200, 20));
		owlConstructComboBox3.setPreferredSize(new Dimension(200, 20));
		ifConditionLabel1.setPreferredSize(new Dimension(120, 20));
		ifConditionComboBox1.setPreferredSize(new Dimension(120, 20));
		ifConditionValueTextField1.setPreferredSize(new Dimension(120, 20));
		ifConditionComboBox2.setPreferredSize(new Dimension(120, 20));
		ifConditionValueTextField2.setPreferredSize(new Dimension(120, 20));

		umlMultiplicityPanel.setLayout(layout);
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
		layout.setConstraints(umlMultiplicityLabel, c);
		umlMultiplicityPanel.add(umlMultiplicityLabel);
		c.gridx = 1;
		layout.setConstraints(umlMultiplicityCheckBox, c);
		umlMultiplicityPanel.add(umlMultiplicityCheckBox);
		c.gridx = 2;
		layout.setConstraints(umlMultiplicityTypeComboBox, c);
		umlMultiplicityPanel.add(umlMultiplicityTypeComboBox);
		c.gridx = 3;
		layout.setConstraints(umlMultiplicityEndComboBox, c);
		umlMultiplicityPanel.add(umlMultiplicityEndComboBox);

		c.gridy = 0;
		c.gridx = 0;
		c.insets.top = 5;
		layout.setConstraints(umlConstructLabel, c);
		variablesPanel1.add(umlConstructLabel);
		c.gridx = 1;
		layout.setConstraints(owlConstructLabel1, c);
		variablesPanel1.add(owlConstructLabel1);
		c.gridx = 2;
		layout.setConstraints(ifConditionLabel1, c);
		variablesPanel1.add(ifConditionLabel1);
		c.gridy = 1;
		c.gridx = 0;
		c.insets.top = 0;
		layout.setConstraints(umlConstructComboBox, c);
		variablesPanel1.add(umlConstructComboBox);
		c.gridx = 1;
		layout.setConstraints(owlConstructComboBox1, c);
		variablesPanel1.add(owlConstructComboBox1);
		c.gridx = 2;
		layout.setConstraints(ifConditionComboBox1, c);
		variablesPanel1.add(ifConditionComboBox1);
		c.gridx = 3;
		layout.setConstraints(ifConditionValueTextField1, c);
		variablesPanel1.add(ifConditionValueTextField1);
		c.gridy = 2;
		c.gridx = 1;
		c.insets.top = 0;
		layout.setConstraints(owlConstructComboBox2, c);
		variablesPanel1.add(owlConstructComboBox2);
		c.gridx = 2;
		layout.setConstraints(ifConditionComboBox2, c);
		variablesPanel1.add(ifConditionComboBox2);
		c.gridx = 3;
		layout.setConstraints(ifConditionValueTextField2, c);
		variablesPanel1.add(ifConditionValueTextField2);
		c.gridy = 3;
		c.gridx = 0;
		c.insets.top = 0;
		layout.setConstraints(umlMultiplicityPanel, c);
		variablesPanel1.add(umlMultiplicityPanel);
		c.gridx = 1;
		layout.setConstraints(owlConstructComboBox3, c);
		variablesPanel1.add(owlConstructComboBox3);
		this.add(variablesPanel, BorderLayout.CENTER);
	}

	protected void initializeVariables() {
		umlConstructComboBox.addItem(GlobalVariables.UML_EMPTY);
		umlConstructComboBox.addItem(GlobalVariables.UML_ASSOCIATION_DIRECTED);
		umlConstructComboBox.addItem(GlobalVariables.UML_ASSOCIATION_AGGREGATION);
		umlConstructComboBox.addItem(GlobalVariables.UML_ASSOCIATION_COMPOSITION);

		umlMultiplicityTypeComboBox.addItem(GlobalVariables.UML_ASSOCIATION_TYPE_MAX);
		umlMultiplicityTypeComboBox.addItem(GlobalVariables.UML_ASSOCIATION_TYPE_MIN);
		umlMultiplicityTypeComboBox.addItem(GlobalVariables.UML_ASSOCIATION_TYPE_EQUAL);

		umlMultiplicityEndComboBox.addItem(GlobalVariables.UML_ASSOCIATION_MULTIPLICITY_END1);
		umlMultiplicityEndComboBox.addItem(GlobalVariables.UML_ASSOCIATION_MULTIPLICITY_END2);

		owlConstructComboBox1.addItem(GlobalVariables.OWL_SUPERCLASS_NAME);
		owlConstructComboBox1.addItem(GlobalVariables.OWL_COMPLEMENT_OF);
		owlConstructComboBox1.addItem(GlobalVariables.OWL_INTERSECTION_OF);
		owlConstructComboBox1.addItem(GlobalVariables.OWL_UNION_OF);
		owlConstructComboBox1.addItem(GlobalVariables.OWL_MINCARDINALITY_ONPROPERTY);
		owlConstructComboBox1.addItem(GlobalVariables.OWL_MINCARDINALITY_VALUE);
		owlConstructComboBox1.addItem(GlobalVariables.OWL_MAXCARDINALITY_ONPROPERTY);
		owlConstructComboBox1.addItem(GlobalVariables.OWL_MAXCARDINALITY_VALUE);
		owlConstructComboBox1.addItem(GlobalVariables.OWL_ALLVALUESFROM_ONPROPERTY);
		owlConstructComboBox1.addItem(GlobalVariables.OWL_ALLVALUESFROM_VALUE);
		owlConstructComboBox1.addItem(GlobalVariables.OWL_HASVALUE_ONPROPERTY);
		owlConstructComboBox1.addItem(GlobalVariables.OWL_HASVALUE_VALUE);
		owlConstructComboBox1.addItem(GlobalVariables.OWL_SOMEVALUESFROM_ONPROPERTY);
		owlConstructComboBox1.addItem(GlobalVariables.OWL_SOMEVALUESFROM_VALUE);
		owlConstructComboBox1.addItem(GlobalVariables.OWL_ONE_OF);

		ifConditionComboBox1.addItem(GlobalVariables.IF_NO_CONDITION);
		ifConditionComboBox1.addItem(GlobalVariables.IF_REGULAR_EXPRESSION);
		ifConditionComboBox1.addItem(GlobalVariables.IF_STRING_EQUALS);
		ifConditionComboBox1.addItem(GlobalVariables.IF_STRING_BEGINS_WITH);
		ifConditionComboBox1.addItem(GlobalVariables.IF_STRING_ENDS_WITH);
		ifConditionComboBox1.addItem(GlobalVariables.IF_STRING_INCLUDES);
		ifConditionComboBox1.addItem(GlobalVariables.IF_STRING_NOT_EQUALS);
		ifConditionComboBox1.addItem(GlobalVariables.IF_STRING_NOT_BEGINS_WITH);
		ifConditionComboBox1.addItem(GlobalVariables.IF_STRING_NOT_ENDS_WITH);
		ifConditionComboBox1.addItem(GlobalVariables.IF_STRING_NOT_INCLUDES);

		ifConditionComboBox2.addItem(GlobalVariables.IF_NO_CONDITION);
		ifConditionComboBox2.addItem(GlobalVariables.IF_REGULAR_EXPRESSION);
		ifConditionComboBox2.addItem(GlobalVariables.IF_STRING_EQUALS);
		ifConditionComboBox2.addItem(GlobalVariables.IF_STRING_BEGINS_WITH);
		ifConditionComboBox2.addItem(GlobalVariables.IF_STRING_ENDS_WITH);
		ifConditionComboBox2.addItem(GlobalVariables.IF_STRING_INCLUDES);
		ifConditionComboBox2.addItem(GlobalVariables.IF_STRING_NOT_EQUALS);
		ifConditionComboBox2.addItem(GlobalVariables.IF_STRING_NOT_BEGINS_WITH);
		ifConditionComboBox2.addItem(GlobalVariables.IF_STRING_NOT_ENDS_WITH);
		ifConditionComboBox2.addItem(GlobalVariables.IF_STRING_NOT_INCLUDES);
	}

	public void readTransformationMapping(OWLClassUMLAssociationTransformationMapping mapping) {
		this.umlConstructComboBox.setSelectedItem(mapping.getUmlVariable());
		this.owlConstructComboBox1.setSelectedItem(mapping.getOwlVariable1());
		this.owlConstructComboBox2.setSelectedItem(mapping.getOwlVariable2());
		this.ifConditionComboBox1.setSelectedItem(mapping.getIfCondition1());
		this.ifConditionValueTextField1.setText(mapping.getIfConditionValue1());
		this.ifConditionComboBox2.setSelectedItem(mapping.getIfCondition2());
		this.ifConditionValueTextField2.setText(mapping.getIfConditionValue2());
		this.umlMultiplicityCheckBox.setSelected(mapping.isMultiplicityIncluded());
		this.umlMultiplicityTypeComboBox.setSelectedItem(mapping.getMultiplicityType());
		this.umlMultiplicityEndComboBox.setSelectedItem(mapping.getMultiplicityEnd());
		this.owlConstructComboBox3.setSelectedItem(mapping.getOwlVariable3());
	}

	public void writeTransformationMapping() {
		OWLClassUMLAssociationTransformationMapping mapping = new OWLClassUMLAssociationTransformationMapping();
		mapping.setUmlVariable((String) umlConstructComboBox.getSelectedItem());
		mapping.setOwlVariable1((String) owlConstructComboBox1.getSelectedItem());
		mapping.setOwlVariable2((String) owlConstructComboBox2.getSelectedItem());
		mapping.setIfCondition1((String) ifConditionComboBox1.getSelectedItem());
		mapping.setIfConditionValue1(ifConditionValueTextField1.getText());
		mapping.setIfCondition2((String) ifConditionComboBox2.getSelectedItem());
		mapping.setIfConditionValue2(ifConditionValueTextField2.getText());
		mapping.setMultiplicityIncluded(umlMultiplicityCheckBox.isSelected());
		mapping.setMultiplicityType((String) umlMultiplicityTypeComboBox.getSelectedItem());
		mapping.setMultiplicityEnd((String) umlMultiplicityEndComboBox.getSelectedItem());
		mapping.setOwlVariable3((String) owlConstructComboBox3.getSelectedItem());
		umlAssociationGraphCell.setOwlClassUmlAssociationTransformationMapping(mapping);
	}

	private class UMLConstructActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			String selectedOWLItem = (String) owlConstructComboBox1.getSelectedItem();
			if (selectedOWLItem.startsWith(GlobalVariables.OWL_ALLVALUESFROM)
					|| selectedOWLItem.startsWith(GlobalVariables.OWL_SOMEVALUESFROM)
					|| selectedOWLItem.startsWith(GlobalVariables.OWL_HASVALUE)
					|| selectedOWLItem.startsWith(GlobalVariables.OWL_MINCARDINALITY)
					|| selectedOWLItem.startsWith(GlobalVariables.OWL_MAXCARDINALITY)) {
				owlConstructComboBox2.setVisible(true);
				ifConditionComboBox2.setVisible(true);
				ifConditionValueTextField2.setVisible(true);
			} else {
				owlConstructComboBox2.setVisible(false);
				ifConditionComboBox2.setVisible(false);
				ifConditionValueTextField2.setVisible(false);
			}

			if (selectedOWLItem.startsWith(GlobalVariables.OWL_MINCARDINALITY)
					|| selectedOWLItem.startsWith(GlobalVariables.OWL_MAXCARDINALITY)) {
				umlMultiplicityPanel.setVisible(true);
				owlConstructComboBox3.setVisible(true);
			} else {
				umlMultiplicityPanel.setVisible(false);
				owlConstructComboBox3.setVisible(false);
			}
			ownerDialog.pack();
		}
	}

	private class OWLConstructActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			String selectedItem = (String) owlConstructComboBox1.getSelectedItem();

			owlConstructComboBox2.removeAllItems();
			if (selectedItem.equals(GlobalVariables.OWL_MINCARDINALITY_ONPROPERTY)) {
				owlConstructComboBox2.addItem(GlobalVariables.OWL_MINCARDINALITY_VALUE);
			} else if (selectedItem.equals(GlobalVariables.OWL_MINCARDINALITY_VALUE)) {
				owlConstructComboBox2.addItem(GlobalVariables.OWL_MINCARDINALITY_ONPROPERTY);
			} else if (selectedItem.equals(GlobalVariables.OWL_MAXCARDINALITY_ONPROPERTY)) {
				owlConstructComboBox2.addItem(GlobalVariables.OWL_MAXCARDINALITY_VALUE);
			} else if (selectedItem.equals(GlobalVariables.OWL_MAXCARDINALITY_VALUE)) {
				owlConstructComboBox2.addItem(GlobalVariables.OWL_MAXCARDINALITY_ONPROPERTY);
			} else if (selectedItem.equals(GlobalVariables.OWL_SOMEVALUESFROM_ONPROPERTY)) {
				owlConstructComboBox2.addItem(GlobalVariables.OWL_SOMEVALUESFROM_VALUE);
			} else if (selectedItem.equals(GlobalVariables.OWL_SOMEVALUESFROM_VALUE)) {
				owlConstructComboBox2.addItem(GlobalVariables.OWL_SOMEVALUESFROM_ONPROPERTY);
			} else if (selectedItem.equals(GlobalVariables.OWL_ALLVALUESFROM_ONPROPERTY)) {
				owlConstructComboBox2.addItem(GlobalVariables.OWL_ALLVALUESFROM_VALUE);
			} else if (selectedItem.equals(GlobalVariables.OWL_ALLVALUESFROM_VALUE)) {
				owlConstructComboBox2.addItem(GlobalVariables.OWL_ALLVALUESFROM_ONPROPERTY);
			} else if (selectedItem.equals(GlobalVariables.OWL_HASVALUE_ONPROPERTY)) {
				owlConstructComboBox2.addItem(GlobalVariables.OWL_HASVALUE_VALUE);
			} else if (selectedItem.equals(GlobalVariables.OWL_HASVALUE_VALUE)) {
				owlConstructComboBox2.addItem(GlobalVariables.OWL_HASVALUE_ONPROPERTY);
			}

			if (selectedItem.startsWith(GlobalVariables.OWL_ALLVALUESFROM)
					|| selectedItem.startsWith(GlobalVariables.OWL_SOMEVALUESFROM)
					|| selectedItem.startsWith(GlobalVariables.OWL_HASVALUE)
					|| selectedItem.startsWith(GlobalVariables.OWL_MINCARDINALITY)
					|| selectedItem.startsWith(GlobalVariables.OWL_MAXCARDINALITY)) {
				owlConstructComboBox2.setVisible(true);
				ifConditionComboBox2.setVisible(true);
				ifConditionValueTextField2.setVisible(true);
			} else {
				owlConstructComboBox2.setVisible(false);
				ifConditionComboBox2.setVisible(false);
				ifConditionValueTextField2.setVisible(false);
			}

			owlConstructComboBox3.removeAllItems();
			if (selectedItem.equals(GlobalVariables.OWL_MINCARDINALITY_ONPROPERTY))
				owlConstructComboBox3.addItem(GlobalVariables.OWL_MINCARDINALITY_VALUE);
			else if (selectedItem.equals(GlobalVariables.OWL_MINCARDINALITY_VALUE))
				owlConstructComboBox3.addItem(GlobalVariables.OWL_MINCARDINALITY_ONPROPERTY);
			else if (selectedItem.equals(GlobalVariables.OWL_MAXCARDINALITY_ONPROPERTY))
				owlConstructComboBox3.addItem(GlobalVariables.OWL_MAXCARDINALITY_VALUE);
			else if (selectedItem.equals(GlobalVariables.OWL_MAXCARDINALITY_VALUE))
				owlConstructComboBox3.addItem(GlobalVariables.OWL_MAXCARDINALITY_ONPROPERTY);

			String selectedOWLItem = (String) owlConstructComboBox1.getSelectedItem();
			if (selectedOWLItem.startsWith(GlobalVariables.OWL_MINCARDINALITY)
					|| selectedOWLItem.startsWith(GlobalVariables.OWL_MAXCARDINALITY)) {
				umlMultiplicityPanel.setVisible(true);
				owlConstructComboBox3.setVisible(true);
			} else {
				umlMultiplicityPanel.setVisible(false);
				owlConstructComboBox3.setVisible(false);
				umlMultiplicityCheckBox.setSelected(false);
			}
			ownerDialog.pack();
		}
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
