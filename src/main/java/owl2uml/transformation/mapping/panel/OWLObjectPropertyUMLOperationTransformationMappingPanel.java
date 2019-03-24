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
import owl2uml.graph.cell.UMLOperationGraphCell;
import owl2uml.transformation.mapping.OWLObjectPropertyUMLOperationTransformationMapping;

/**
 * @author Ozer Ozdikis
 * @version June 2006
 */
public class OWLObjectPropertyUMLOperationTransformationMappingPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	protected static String OK_COMMAND = "OK";
	protected static String CLOSE_COMMAND = "Close";
	protected JDialog ownerDialog;

	protected JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	protected JButton okButton = new JButton(new ImageIcon(GlobalVariables.ICON_SAVE));
	protected JButton closeButton = new JButton(new ImageIcon(GlobalVariables.ICON_CLOSE));

	protected ButtonActionListener buttonActionListener = new ButtonActionListener();
	protected Category tracer = Logger.getLogger(OWLObjectPropertyUMLOperationTransformationMappingPanel.class);

	private JLabel ifConditionLabel = new JLabel("if condition");
	private JPanel variablesPanel = new JPanel(new BorderLayout());
	private JLabel ownerClassLabel = new JLabel("Name of the UML class that owns the operation");
	private JComboBox<String> ownerClassIfConditionComboBox = new JComboBox<String>();
	private JTextField ownerClassIfConditionTextField = new JTextField();
	private JLabel parameterLabel = new JLabel("Operation parameters");
	private JComboBox<String> parameterComboBox = new JComboBox<String>();
	private JComboBox<String> parameterTypeComboBox = new JComboBox<String>();
	private JComboBox<String> parameterIfConditionComboBox = new JComboBox<String>();
	private JTextField parameterIfConditionTextField = new JTextField();

	private UMLOperationGraphCell umlOperationGraphCell;

	public OWLObjectPropertyUMLOperationTransformationMappingPanel(JDialog ownerDialog,
			UMLOperationGraphCell umlOperationGraphCell) {
		super(new BorderLayout());
		this.ownerDialog = ownerDialog;
		this.umlOperationGraphCell = umlOperationGraphCell;

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

	protected void decorateVariablesPanel() {
		JPanel variablesPanel1 = new JPanel();
		variablesPanel.add(variablesPanel1, BorderLayout.NORTH);
		variablesPanel.add(new JPanel(), BorderLayout.CENTER);
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		variablesPanel1.setLayout(layout);
		variablesPanel1.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "UML Construct Mapping"));

		ifConditionLabel.setPreferredSize(new Dimension(120, 20));
		ownerClassIfConditionComboBox.setPreferredSize(new Dimension(120, 20));
		ownerClassIfConditionTextField.setPreferredSize(new Dimension(120, 20));
		parameterIfConditionComboBox.setPreferredSize(new Dimension(120, 20));
		parameterIfConditionTextField.setPreferredSize(new Dimension(120, 20));
		ownerClassLabel.setPreferredSize(new Dimension(310, 20));
		parameterLabel.setPreferredSize(new Dimension(110, 20));
		parameterComboBox.setPreferredSize(new Dimension(150, 20));
		parameterTypeComboBox.setPreferredSize(new Dimension(50, 20));

		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridwidth = 1;
		c.insets.top = 0;
		c.insets.left = 5;
		c.insets.bottom = 2;
		c.insets.right = 0;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0;
		c.gridy = 0;
		c.gridx = 3;
		layout.setConstraints(ifConditionLabel, c);
		variablesPanel1.add(ifConditionLabel);

		c.gridy = 1;
		c.gridx = 0;
		c.gridwidth = 3;
		layout.setConstraints(ownerClassLabel, c);
		variablesPanel1.add(ownerClassLabel);
		c.gridx = 3;
		c.gridwidth = 1;
		layout.setConstraints(ownerClassIfConditionComboBox, c);
		variablesPanel1.add(ownerClassIfConditionComboBox);
		c.gridx = 4;
		layout.setConstraints(ownerClassIfConditionTextField, c);
		variablesPanel1.add(ownerClassIfConditionTextField);

		c.gridy = 2;
		c.gridx = 0;
		layout.setConstraints(parameterLabel, c);
		variablesPanel1.add(parameterLabel);
		c.gridx = 1;
		layout.setConstraints(parameterTypeComboBox, c);
		variablesPanel1.add(parameterTypeComboBox);
		c.gridx = 2;
		layout.setConstraints(parameterComboBox, c);
		variablesPanel1.add(parameterComboBox);
		c.gridx = 3;
		layout.setConstraints(parameterIfConditionComboBox, c);
		variablesPanel1.add(parameterIfConditionComboBox);
		c.gridx = 4;
		layout.setConstraints(parameterIfConditionTextField, c);
		variablesPanel1.add(parameterIfConditionTextField);
		this.add(variablesPanel, BorderLayout.CENTER);
	}

	protected void initializeVariables() {
		parameterComboBox.addItem(GlobalVariables.OWL_SUPERPROPERTY_NAME);
		parameterComboBox.addItem(GlobalVariables.OWL_DOMAIN);
		parameterComboBox.addItem(GlobalVariables.OWL_RANGE);

		parameterTypeComboBox.addItem(GlobalVariables.UML_PARAMETER_TYPE_IN);
		parameterTypeComboBox.addItem(GlobalVariables.UML_PARAMETER_TYPE_INOUT);
		parameterTypeComboBox.addItem(GlobalVariables.UML_PARAMETER_TYPE_OUT);
		parameterTypeComboBox.addItem(GlobalVariables.UML_PARAMETER_TYPE_RETURN);

		ownerClassIfConditionComboBox.addItem(GlobalVariables.IF_NO_CONDITION);
		ownerClassIfConditionComboBox.addItem(GlobalVariables.IF_REGULAR_EXPRESSION);
		ownerClassIfConditionComboBox.addItem(GlobalVariables.IF_STRING_EQUALS);
		ownerClassIfConditionComboBox.addItem(GlobalVariables.IF_STRING_BEGINS_WITH);
		ownerClassIfConditionComboBox.addItem(GlobalVariables.IF_STRING_ENDS_WITH);
		ownerClassIfConditionComboBox.addItem(GlobalVariables.IF_STRING_INCLUDES);
		ownerClassIfConditionComboBox.addItem(GlobalVariables.IF_STRING_NOT_EQUALS);
		ownerClassIfConditionComboBox.addItem(GlobalVariables.IF_STRING_NOT_BEGINS_WITH);
		ownerClassIfConditionComboBox.addItem(GlobalVariables.IF_STRING_NOT_ENDS_WITH);
		ownerClassIfConditionComboBox.addItem(GlobalVariables.IF_STRING_NOT_INCLUDES);

		parameterIfConditionComboBox.addItem(GlobalVariables.IF_NO_CONDITION);
		parameterIfConditionComboBox.addItem(GlobalVariables.IF_REGULAR_EXPRESSION);
		parameterIfConditionComboBox.addItem(GlobalVariables.IF_STRING_EQUALS);
		parameterIfConditionComboBox.addItem(GlobalVariables.IF_STRING_BEGINS_WITH);
		parameterIfConditionComboBox.addItem(GlobalVariables.IF_STRING_ENDS_WITH);
		parameterIfConditionComboBox.addItem(GlobalVariables.IF_STRING_INCLUDES);
		parameterIfConditionComboBox.addItem(GlobalVariables.IF_STRING_NOT_EQUALS);
		parameterIfConditionComboBox.addItem(GlobalVariables.IF_STRING_NOT_BEGINS_WITH);
		parameterIfConditionComboBox.addItem(GlobalVariables.IF_STRING_NOT_ENDS_WITH);
		parameterIfConditionComboBox.addItem(GlobalVariables.IF_STRING_NOT_INCLUDES);
	}

	public void readTransformationMapping(OWLObjectPropertyUMLOperationTransformationMapping mapping) {
		this.ownerClassIfConditionComboBox.setSelectedItem(mapping.getOwnerNameIfCondition());
		this.ownerClassIfConditionTextField.setText(mapping.getOwnerNameIfConditionValue());
		this.parameterComboBox.setSelectedItem(mapping.getParameterName());
		this.parameterTypeComboBox.setSelectedItem(mapping.getParameterType());
		this.parameterIfConditionComboBox.setSelectedItem(mapping.getParameterNameIfCondition());
		this.parameterIfConditionTextField.setText(mapping.getParameterNameIfConditionValue());
	}

	public void writeTransformationMapping() {
		OWLObjectPropertyUMLOperationTransformationMapping mapping = new OWLObjectPropertyUMLOperationTransformationMapping();
		tracer.debug("Creating new OWLObjectPropertyUMLOperationTransformationMapping instance");
		mapping.setOwnerNameIfCondition((String) ownerClassIfConditionComboBox.getSelectedItem());
		mapping.setOwnerNameIfConditionValue(ownerClassIfConditionTextField.getText());
		mapping.setParameterName((String) parameterComboBox.getSelectedItem());
		mapping.setParameterType((String) parameterTypeComboBox.getSelectedItem());
		mapping.setParameterNameIfCondition((String) parameterIfConditionComboBox.getSelectedItem());
		mapping.setParameterNameIfConditionValue(parameterIfConditionTextField.getText());
		umlOperationGraphCell.setOWLObjectPropertyUMLOperationTransformationMapping(mapping);
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
