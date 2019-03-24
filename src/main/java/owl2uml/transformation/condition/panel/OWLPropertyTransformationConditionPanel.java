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
import owl2uml.transformation.condition.OWLPropertyTransformationCondition;

/**
 * @author Ozer Ozdikis
 * @version June 2006
 */
public class OWLPropertyTransformationConditionPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	static private String SAVE_COMMAND = "Save";
	static private String CLOSE_COMMAND = "Close";
	private JDialog ownerDialog;
	private OWLPropertyTransformationCondition transformationCondition;

	private JPanel conditionsPanel = new JPanel(new BorderLayout());
	private JLabel label1 = new JLabel("OWL Variable");
	private JLabel label2 = new JLabel("If condition");
	private JLabel label3 = new JLabel("If value");

	private JLabel propertyNameLabel = new JLabel(GlobalVariables.OWL_PROPERTY_NAME);
	private JLabel superPropertyLabel = new JLabel(GlobalVariables.OWL_SUPERPROPERTY_NAME);
	private JLabel domainLabel = new JLabel(GlobalVariables.OWL_DOMAIN);
	private JLabel rangeLabel = new JLabel(GlobalVariables.OWL_RANGE);
	private List<JComboBox<String>> ifConditionComboBoxes = new LinkedList<JComboBox<String>>();
	private JTextField[] ifConditionValues = new JTextField[4];

	private JCheckBox superPropertyAllLevelCheckBox = new JCheckBox();
	private JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	private JButton saveButton = new JButton(new ImageIcon(GlobalVariables.ICON_SAVE));
	private JButton closeButton = new JButton(new ImageIcon(GlobalVariables.ICON_CLOSE));
	private ButtonActionListener buttonActionListener = new ButtonActionListener();
	private Category tracer = Logger.getLogger(OWLPropertyTransformationConditionPanel.class);

	/**
	 * constructor
	 */
	public OWLPropertyTransformationConditionPanel(JDialog ownerDialog,
			OWLPropertyTransformationCondition transformationCondition) {
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
		conditionsPanel1.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "Domain OWL Property Requirements"));
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridwidth = 1;
		c.insets.top = 0;
		c.insets.left = 5;
		c.insets.bottom = 2;
		c.insets.right = 5;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0;

		label1.setPreferredSize(new Dimension(160, 20));
		propertyNameLabel.setPreferredSize(new Dimension(160, 20));
		superPropertyLabel.setPreferredSize(new Dimension(160, 20));
		domainLabel.setPreferredSize(new Dimension(160, 20));
		rangeLabel.setPreferredSize(new Dimension(160, 20));

		label2.setPreferredSize(new Dimension(120, 20));
		for (int i = 0; i < ifConditionComboBoxes.size(); i++)
			ifConditionComboBoxes.get(i).setPreferredSize(new Dimension(120, 20));

		label3.setPreferredSize(new Dimension(120, 20));
		for (int i = 0; i < ifConditionValues.length; i++)
			ifConditionValues[i].setPreferredSize(new Dimension(120, 20));

		superPropertyAllLevelCheckBox.setPreferredSize(new Dimension(20, 20));
		superPropertyAllLevelCheckBox.setToolTipText("For all levels");

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
		layout.setConstraints(propertyNameLabel, c);
		conditionsPanel1.add(propertyNameLabel);
		c.gridy = 2;
		c.gridx = 0;
		layout.setConstraints(superPropertyLabel, c);
		conditionsPanel1.add(superPropertyLabel);
		c.gridy = 3;
		c.gridx = 0;
		layout.setConstraints(domainLabel, c);
		conditionsPanel1.add(domainLabel);
		c.gridy = 4;
		c.gridx = 0;
		layout.setConstraints(rangeLabel, c);
		conditionsPanel1.add(rangeLabel);

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
		layout.setConstraints(superPropertyAllLevelCheckBox, c);
		conditionsPanel1.add(superPropertyAllLevelCheckBox);

		this.add(conditionsPanel, BorderLayout.CENTER);
	}

	private void loadCondition() {
		tracer.debug("Loading transformation condition configuration to the panel");
		ifConditionComboBoxes.get(0).setSelectedItem(transformationCondition.propertyNameIfCondition);
		ifConditionValues[0].setText(transformationCondition.propertyNameIfConditionValue);
		ifConditionComboBoxes.get(1).setSelectedItem(transformationCondition.superPropertyIfCondition);
		ifConditionValues[1].setText(transformationCondition.superPropertyIfConditionValue);
		ifConditionComboBoxes.get(2).setSelectedItem(transformationCondition.domainIfCondition);
		ifConditionValues[2].setText(transformationCondition.domainIfConditionValue);
		ifConditionComboBoxes.get(3).setSelectedItem(transformationCondition.rangeIfCondition);
		ifConditionValues[3].setText(transformationCondition.rangeIfConditionValue);
		if (transformationCondition.isSuperPropertyForAllLevels)
			superPropertyAllLevelCheckBox.setSelected(true);
		else
			superPropertyAllLevelCheckBox.setSelected(false);
	}

	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getActionCommand().equals(CLOSE_COMMAND)) {
				tracer.debug("closing the condition panel without saving");
				ownerDialog.dispose();
			} else if (arg0.getActionCommand().equals(SAVE_COMMAND)) {
				tracer.debug("closing the condition panel with saving");
				transformationCondition.propertyNameIfCondition = (String) ifConditionComboBoxes.get(0).getSelectedItem();
				transformationCondition.propertyNameIfConditionValue = ifConditionValues[0].getText();
				transformationCondition.superPropertyIfCondition = (String) ifConditionComboBoxes.get(1).getSelectedItem();
				transformationCondition.superPropertyIfConditionValue = ifConditionValues[1].getText();
				transformationCondition.domainIfCondition = (String) ifConditionComboBoxes.get(2).getSelectedItem();
				transformationCondition.domainIfConditionValue = ifConditionValues[2].getText();
				transformationCondition.rangeIfCondition = (String) ifConditionComboBoxes.get(3).getSelectedItem();
				transformationCondition.rangeIfConditionValue = ifConditionValues[3].getText();
				transformationCondition.isSuperPropertyForAllLevels = superPropertyAllLevelCheckBox.isSelected();
				ownerDialog.dispose();
			}
		}
	}

	private void initializeVariables() {
		for (int i = 0; i < ifConditionValues.length; i++)
			ifConditionValues[i] = new JTextField("");
		for (int i = 0; i < 4; i++) {
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
