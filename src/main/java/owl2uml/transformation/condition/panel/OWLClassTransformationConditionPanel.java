package owl2uml.transformation.condition.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

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
import owl2uml.transformation.condition.OWLClassTransformationCondition;

/**
 * @author Ozer Ozdikis
 * @version June 2006
 */
public class OWLClassTransformationConditionPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	static private String SAVE_COMMAND = "Save";
	static private String CLOSE_COMMAND = "Close";
	private JDialog ownerDialog;
	private OWLClassTransformationCondition transformationCondition;

	private JPanel conditionsPanel = new JPanel(new BorderLayout());
	private JLabel label1 = new JLabel("OWL Variable");
	private JLabel label2 = new JLabel("If condition");
	private JLabel label3 = new JLabel("If value");

	private JLabel classNameLabel = new JLabel(GlobalVariables.OWL_CLASS_NAME);
	private JLabel superClassLabel = new JLabel(GlobalVariables.OWL_SUPERCLASS_NAME);
	private JLabel complementOfLabel = new JLabel(GlobalVariables.OWL_COMPLEMENT_OF);
	private JLabel intersectionOfLabel = new JLabel(GlobalVariables.OWL_INTERSECTION_OF);
	private JLabel unionOfLabel = new JLabel(GlobalVariables.OWL_UNION_OF);
	private JLabel minCardinalityPropertyLabel = new JLabel(GlobalVariables.OWL_MINCARDINALITY_ONPROPERTY);
	private JLabel minCardinalityValueLabel = new JLabel(GlobalVariables.OWL_MINCARDINALITY_VALUE);
	private JLabel maxCardinalityPropertyLabel = new JLabel(GlobalVariables.OWL_MAXCARDINALITY_ONPROPERTY);
	private JLabel maxCardinalityValueLabel = new JLabel(GlobalVariables.OWL_MAXCARDINALITY_VALUE);
	private JLabel allValuesPropertyLabel = new JLabel(GlobalVariables.OWL_ALLVALUESFROM_ONPROPERTY);
	private JLabel allValuesValueLabel = new JLabel(GlobalVariables.OWL_ALLVALUESFROM_VALUE);
	private JLabel hasValuePropertyLabel = new JLabel(GlobalVariables.OWL_HASVALUE_ONPROPERTY);
	private JLabel hasValueValueLabel = new JLabel(GlobalVariables.OWL_HASVALUE_VALUE);
	private JLabel someValuesPropertyLabel = new JLabel(GlobalVariables.OWL_SOMEVALUESFROM_ONPROPERTY);
	private JLabel someValuesValueLabel = new JLabel(GlobalVariables.OWL_SOMEVALUESFROM_VALUE);
	private List<JComboBox<String>> ifConditionComboBoxes = new LinkedList<JComboBox<String>>();
	private JTextField[] ifConditionValues = new JTextField[15];
	private JCheckBox superClassAllLevelCheckBox = new JCheckBox();
	private JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	private JButton saveButton = new JButton(new ImageIcon(GlobalVariables.ICON_SAVE));
	private JButton closeButton = new JButton(new ImageIcon(GlobalVariables.ICON_CLOSE));
	private ButtonActionListener buttonActionListener = new ButtonActionListener();
	private Category tracer = Logger.getLogger(OWLClassTransformationConditionPanel.class);

	/**
	 * constructor
	 */
	public OWLClassTransformationConditionPanel(JDialog ownerDialog,
			OWLClassTransformationCondition transformationCondition) {
		super(new BorderLayout());
		this.transformationCondition = transformationCondition;
		this.ownerDialog = ownerDialog;

		initializeVariables();
		decorateButtonsPanel();
		decorateConditionsPanel();
		loadCondition();

		saveButton.addActionListener(buttonActionListener);
		closeButton.addActionListener(buttonActionListener);
	}

	/**
	 * prepares the buttons panel with save and close buttons
	 */
	private void decorateButtonsPanel() {
		saveButton.setPreferredSize(new Dimension(20, 20));
		closeButton.setPreferredSize(new Dimension(20, 20));
		saveButton.setActionCommand(SAVE_COMMAND);
		closeButton.setActionCommand(CLOSE_COMMAND);
		buttonsPanel.add(saveButton);
		buttonsPanel.add(closeButton);
		this.add(buttonsPanel, BorderLayout.SOUTH);
	}

	private void decorateConditionsPanel() {
		JPanel conditionsPanel1 = new JPanel();
		conditionsPanel.add(conditionsPanel1, BorderLayout.NORTH);
		conditionsPanel.add(new JPanel(), BorderLayout.CENTER);
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		conditionsPanel1.setLayout(layout);
		conditionsPanel1.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "Domain OWL Class Requirements"));
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridwidth = 1;
		c.insets.top = 0;
		c.insets.left = 5;
		c.insets.bottom = 2;
		c.insets.right = 5;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0;

		label1.setPreferredSize(new Dimension(180, 20));
		classNameLabel.setPreferredSize(new Dimension(180, 20));
		superClassLabel.setPreferredSize(new Dimension(180, 20));
		complementOfLabel.setPreferredSize(new Dimension(180, 20));
		intersectionOfLabel.setPreferredSize(new Dimension(180, 20));
		unionOfLabel.setPreferredSize(new Dimension(180, 20));
		minCardinalityPropertyLabel.setPreferredSize(new Dimension(180, 20));
		minCardinalityValueLabel.setPreferredSize(new Dimension(180, 20));
		maxCardinalityPropertyLabel.setPreferredSize(new Dimension(180, 20));
		maxCardinalityValueLabel.setPreferredSize(new Dimension(180, 20));
		allValuesPropertyLabel.setPreferredSize(new Dimension(180, 20));
		allValuesValueLabel.setPreferredSize(new Dimension(180, 20));
		hasValuePropertyLabel.setPreferredSize(new Dimension(180, 20));
		hasValueValueLabel.setPreferredSize(new Dimension(180, 20));
		someValuesPropertyLabel.setPreferredSize(new Dimension(180, 20));
		someValuesValueLabel.setPreferredSize(new Dimension(180, 20));

		label2.setPreferredSize(new Dimension(120, 20));
		for (int i = 0; i < ifConditionComboBoxes.size(); i++)
			ifConditionComboBoxes.get(i).setPreferredSize(new Dimension(120, 20));

		label3.setPreferredSize(new Dimension(120, 20));
		for (int i = 0; i < ifConditionValues.length; i++)
			ifConditionValues[i].setPreferredSize(new Dimension(120, 20));

		superClassAllLevelCheckBox.setPreferredSize(new Dimension(20, 20));
		superClassAllLevelCheckBox.setToolTipText("For all levels");

		c.gridy = 0;
		c.gridx = 0;
		layout.setConstraints(label1, c);
		conditionsPanel1.add(label1);
		c.gridx = 1;
		layout.setConstraints(label2, c);
		conditionsPanel1.add(label2);
		c.gridx = 2;
		layout.setConstraints(label3, c);
		conditionsPanel1.add(label3);

		c.gridy = 1;
		c.gridx = 0;
		layout.setConstraints(classNameLabel, c);
		conditionsPanel1.add(classNameLabel);
		c.gridy = 2;
		c.gridx = 0;
		layout.setConstraints(superClassLabel, c);
		conditionsPanel1.add(superClassLabel);
		c.gridy = 3;
		c.gridx = 0;
		layout.setConstraints(complementOfLabel, c);
		conditionsPanel1.add(complementOfLabel);
		c.gridy = 4;
		c.gridx = 0;
		layout.setConstraints(intersectionOfLabel, c);
		conditionsPanel1.add(intersectionOfLabel);
		c.gridy = 5;
		c.gridx = 0;
		layout.setConstraints(unionOfLabel, c);
		conditionsPanel1.add(unionOfLabel);
		c.gridy = 6;
		c.gridx = 0;
		layout.setConstraints(minCardinalityPropertyLabel, c);
		conditionsPanel1.add(minCardinalityPropertyLabel);
		c.gridy = 7;
		c.gridx = 0;
		layout.setConstraints(minCardinalityValueLabel, c);
		conditionsPanel1.add(minCardinalityValueLabel);
		c.gridy = 8;
		c.gridx = 0;
		layout.setConstraints(maxCardinalityPropertyLabel, c);
		conditionsPanel1.add(maxCardinalityPropertyLabel);
		c.gridy = 9;
		c.gridx = 0;
		layout.setConstraints(maxCardinalityValueLabel, c);
		conditionsPanel1.add(maxCardinalityValueLabel);
		c.gridy = 10;
		c.gridx = 0;
		layout.setConstraints(allValuesPropertyLabel, c);
		conditionsPanel1.add(allValuesPropertyLabel);
		c.gridy = 11;
		c.gridx = 0;
		layout.setConstraints(allValuesValueLabel, c);
		conditionsPanel1.add(allValuesValueLabel);
		c.gridy = 12;
		c.gridx = 0;
		layout.setConstraints(hasValuePropertyLabel, c);
		conditionsPanel1.add(hasValuePropertyLabel);
		c.gridy = 13;
		c.gridx = 0;
		layout.setConstraints(hasValueValueLabel, c);
		conditionsPanel1.add(hasValueValueLabel);
		c.gridy = 14;
		c.gridx = 0;
		layout.setConstraints(someValuesPropertyLabel, c);
		conditionsPanel1.add(someValuesPropertyLabel);
		c.gridy = 15;
		c.gridx = 0;
		layout.setConstraints(someValuesValueLabel, c);
		conditionsPanel1.add(someValuesValueLabel);

		for (int i = 0; i < ifConditionComboBoxes.size(); i++) {
			c.gridx = 1;
			c.gridy = i + 1;
			layout.setConstraints(ifConditionComboBoxes.get(i), c);
			conditionsPanel1.add(ifConditionComboBoxes.get(i));
			c.gridx = 2;
			layout.setConstraints(ifConditionValues[i], c);
			conditionsPanel1.add(ifConditionValues[i]);
		}

		c.gridy = 2;
		c.gridx = 3;
		layout.setConstraints(superClassAllLevelCheckBox, c);
		conditionsPanel1.add(superClassAllLevelCheckBox);

		this.add(conditionsPanel, BorderLayout.CENTER);
	}

	private void loadCondition() {
		tracer.debug("Loading transformation condition configuration to the panel");
		ifConditionComboBoxes.get(0).setSelectedItem(transformationCondition.classNameIfCondition);
		ifConditionValues[0].setText(transformationCondition.classNameIfConditionValue);
		ifConditionComboBoxes.get(1).setSelectedItem(transformationCondition.superClassIfCondition);
		ifConditionValues[1].setText(transformationCondition.superClassIfConditionValue);
		if (transformationCondition.isSuperClassForAllLevels)
			superClassAllLevelCheckBox.setSelected(true);
		else
			superClassAllLevelCheckBox.setSelected(false);
		ifConditionComboBoxes.get(2).setSelectedItem(transformationCondition.complementOfIfCondition);
		ifConditionValues[2].setText(transformationCondition.complementOfIfConditionValue);
		ifConditionComboBoxes.get(3).setSelectedItem(transformationCondition.intersectionOfIfCondition);
		ifConditionValues[3].setText(transformationCondition.intersectionOfIfConditionValue);
		ifConditionComboBoxes.get(4).setSelectedItem(transformationCondition.unionOfIfCondition);
		ifConditionValues[4].setText(transformationCondition.unionOfIfConditionValue);
		ifConditionComboBoxes.get(5).setSelectedItem(transformationCondition.minCardinalityPropertyIfCondition);
		ifConditionValues[5].setText(transformationCondition.minCardinalityPropertyIfConditionValue);
		ifConditionComboBoxes.get(6).setSelectedItem(transformationCondition.minCardinalityValueIfCondition);
		ifConditionValues[6].setText(transformationCondition.minCardinalityValueIfConditionValue);
		ifConditionComboBoxes.get(7).setSelectedItem(transformationCondition.maxCardinalityPropertyIfCondition);
		ifConditionValues[7].setText(transformationCondition.maxCardinalityPropertyIfConditionValue);
		ifConditionComboBoxes.get(8).setSelectedItem(transformationCondition.maxCardinalityValueIfCondition);
		ifConditionValues[8].setText(transformationCondition.maxCardinalityValueIfConditionValue);
		ifConditionComboBoxes.get(9).setSelectedItem(transformationCondition.allValuesPropertyIfCondition);
		ifConditionValues[9].setText(transformationCondition.allValuesPropertyIfConditionValue);
		ifConditionComboBoxes.get(10).setSelectedItem(transformationCondition.allValuesValueIfCondition);
		ifConditionValues[10].setText(transformationCondition.allValuesValueIfConditionValue);
		ifConditionComboBoxes.get(11).setSelectedItem(transformationCondition.hasValuePropertyIfCondition);
		ifConditionValues[11].setText(transformationCondition.hasValuePropertyIfConditionValue);
		ifConditionComboBoxes.get(12).setSelectedItem(transformationCondition.hasValueValueIfCondition);
		ifConditionValues[12].setText(transformationCondition.hasValueValueIfConditionValue);
		ifConditionComboBoxes.get(13).setSelectedItem(transformationCondition.someValuesPropertyIfCondition);
		ifConditionValues[13].setText(transformationCondition.someValuesPropertyIfConditionValue);
		ifConditionComboBoxes.get(14).setSelectedItem(transformationCondition.someValuesValueIfCondition);
		ifConditionValues[14].setText(transformationCondition.someValuesValueIfConditionValue);
	}

	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getActionCommand().equals(CLOSE_COMMAND)) {
				tracer.debug("closing the condition panel without saving");
				ownerDialog.dispose();
			} else if (arg0.getActionCommand().equals(SAVE_COMMAND)) {
				tracer.debug("closing the condition panel with saving");
				transformationCondition.classNameIfCondition = (String) ifConditionComboBoxes.get(0).getSelectedItem();
				transformationCondition.classNameIfConditionValue = (String) ifConditionValues[0].getText();
				transformationCondition.superClassIfCondition = (String) ifConditionComboBoxes.get(1).getSelectedItem();
				transformationCondition.superClassIfConditionValue = ifConditionValues[1].getText();
				transformationCondition.isSuperClassForAllLevels = superClassAllLevelCheckBox.isSelected();
				transformationCondition.complementOfIfCondition = (String) ifConditionComboBoxes.get(2)
						.getSelectedItem();
				transformationCondition.complementOfIfConditionValue = ifConditionValues[2].getText();
				transformationCondition.intersectionOfIfCondition = (String) ifConditionComboBoxes.get(3)
						.getSelectedItem();
				transformationCondition.intersectionOfIfConditionValue = ifConditionValues[3].getText();
				transformationCondition.unionOfIfCondition = (String) ifConditionComboBoxes.get(4).getSelectedItem();
				transformationCondition.unionOfIfConditionValue = ifConditionValues[4].getText();
				transformationCondition.minCardinalityPropertyIfCondition = (String) ifConditionComboBoxes.get(5)
						.getSelectedItem();
				transformationCondition.minCardinalityPropertyIfConditionValue = ifConditionValues[5].getText();
				transformationCondition.minCardinalityValueIfCondition = (String) ifConditionComboBoxes.get(6)
						.getSelectedItem();
				transformationCondition.minCardinalityValueIfConditionValue = ifConditionValues[6].getText();
				transformationCondition.maxCardinalityPropertyIfCondition = (String) ifConditionComboBoxes.get(7)
						.getSelectedItem();
				transformationCondition.maxCardinalityPropertyIfConditionValue = ifConditionValues[7].getText();
				transformationCondition.maxCardinalityValueIfCondition = (String) ifConditionComboBoxes.get(8)
						.getSelectedItem();
				transformationCondition.maxCardinalityValueIfConditionValue = ifConditionValues[8].getText();
				transformationCondition.allValuesPropertyIfCondition = (String) ifConditionComboBoxes.get(9)
						.getSelectedItem();
				transformationCondition.allValuesPropertyIfConditionValue = ifConditionValues[9].getText();
				transformationCondition.allValuesValueIfCondition = (String) ifConditionComboBoxes.get(10)
						.getSelectedItem();
				transformationCondition.allValuesValueIfConditionValue = ifConditionValues[10].getText();
				transformationCondition.hasValuePropertyIfCondition = (String) ifConditionComboBoxes.get(11)
						.getSelectedItem();
				transformationCondition.hasValuePropertyIfConditionValue = ifConditionValues[11].getText();
				transformationCondition.hasValueValueIfCondition = (String) ifConditionComboBoxes.get(12)
						.getSelectedItem();
				transformationCondition.hasValueValueIfConditionValue = ifConditionValues[12].getText();
				transformationCondition.someValuesPropertyIfCondition = (String) ifConditionComboBoxes.get(13)
						.getSelectedItem();
				transformationCondition.someValuesPropertyIfConditionValue = ifConditionValues[13].getText();
				transformationCondition.someValuesValueIfCondition = (String) ifConditionComboBoxes.get(14)
						.getSelectedItem();
				transformationCondition.someValuesValueIfConditionValue = ifConditionValues[14].getText();
				ownerDialog.dispose();
			}
		}
	}

	private void initializeVariables() {
		for (int i = 0; i < ifConditionValues.length; i++)
			ifConditionValues[i] = new JTextField("");
		for (int i = 0; i < 15; i++) {
			JComboBox<String> ifConditionComboBox = new JComboBox<String>();
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
			ifConditionComboBoxes.add(ifConditionComboBox);
		}
	}
}
