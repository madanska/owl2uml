package owl2uml.graph.cell;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.io.PrintStream;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.jgraph.graph.GraphConstants;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import owl2uml.graph.OWLClassTransformationGraph;
import owl2uml.graph.OWLDatatypePropertyTransformationGraph;
import owl2uml.graph.OWLObjectPropertyTransformationGraph;
import owl2uml.GlobalVariables;
import owl2uml.graph.ATransformationGraph;
import owl2uml.transformation.mapping.OWLClassUMLAssociationTransformationMapping;
import owl2uml.transformation.mapping.OWLDatatypePropertyUMLAssociationTransformationMapping;
import owl2uml.transformation.mapping.OWLObjectPropertyUMLAssociationTransformationMapping;
import owl2uml.transformation.mapping.panel.OWLClassUMLAssociationTransformationMappingPanel;
import owl2uml.transformation.mapping.panel.OWLDatatypePropertyUMLAssociationTransformationMappingPanel;
import owl2uml.transformation.mapping.panel.OWLObjectPropertyUMLAssociationTransformationMappingPanel;

/**
 * Class that represents the range UML associations on the graph gui. It has the
 * coordinate variables, and includes a UMLAssociationTransformationRule
 * instance. It has a PopupMenu to enable the configuration of
 * UMLAssociationTransformationRule, and to delete this UMLAssociationGraphCell
 * from the graph gui.
 * 
 * @author Ozer Ozdikis
 * @version June 2006
 */
public class UMLAssociationGraphCell extends AUMLGraphCell {
	private static final long serialVersionUID = 1L;
	public static String CELL_TYPE = "XMIAssociationCell";
	private OWLClassUMLAssociationTransformationMapping owlClassUmlAssociationMapping = new OWLClassUMLAssociationTransformationMapping();
	private OWLDatatypePropertyUMLAssociationTransformationMapping owlDatatypePropertyUmlAssociationMapping = new OWLDatatypePropertyUMLAssociationTransformationMapping();
	private OWLObjectPropertyUMLAssociationTransformationMapping owlObjectPropertyUmlAssociationMapping = new OWLObjectPropertyUMLAssociationTransformationMapping();

	/**
	 * constructor
	 */
	public UMLAssociationGraphCell(ATransformationGraph graphPanel, int x, int y) {
		super(graphPanel, x, y);
		GraphConstants.setIcon(this.getAttributes(), new ImageIcon(GlobalVariables.ICON_UML_ASSOCIATION));
	}

	/**
	 * Opens the popup menu when right clicked on this UMLAssociationGraphCell
	 * instance
	 */
	public void openPopupMenu(int x, int y) {
		tracer.debug("OpenPopupMenu is called for the UMLAssociationGraphCell...");
		UMLAssociationGraphCellPopupMenu popupMenu = new UMLAssociationGraphCellPopupMenu(this);
		popupMenu.show(graphPanel, x, y);
	}

	public OWLClassUMLAssociationTransformationMapping getOwlClassUmlAssociationTransformationMapping() {
		return owlClassUmlAssociationMapping;
	}

	public void setOwlClassUmlAssociationTransformationMapping(
			OWLClassUMLAssociationTransformationMapping owlClassUmlAssociationMapping) {
		this.owlClassUmlAssociationMapping = owlClassUmlAssociationMapping;
	}

	public OWLDatatypePropertyUMLAssociationTransformationMapping getOwlDatatypePropertyUmlAssociationTransformationMapping() {
		return owlDatatypePropertyUmlAssociationMapping;
	}

	public void setOwlDatatypePropertyUmlAssociationTransformationMapping(
			OWLDatatypePropertyUMLAssociationTransformationMapping owlDatatypePropertyUmlAssociationMapping) {
		this.owlDatatypePropertyUmlAssociationMapping = owlDatatypePropertyUmlAssociationMapping;
	}

	public OWLObjectPropertyUMLAssociationTransformationMapping getOwlObjectPropertyUmlAssociationTransformationMapping() {
		return owlObjectPropertyUmlAssociationMapping;
	}

	public void setOwlObjectPropertyUmlAssociationTransformationMapping(
			OWLObjectPropertyUMLAssociationTransformationMapping owlObjectPropertyUmlAssociationMapping) {
		this.owlObjectPropertyUmlAssociationMapping = owlObjectPropertyUmlAssociationMapping;
	}

	/**
	 * Saves the transformation configuration. After adding <UMLAssociation> tag and
	 * the coordinates, adds the <TransformationRule> tag and delegates the
	 * operation to UMLAssociationTransformationRule.
	 */
	public void saveRulesConfiguration(PrintStream printStream) {
		tracer.debug("Saving the transformation rules");
		printStream.println("		<UMLAssociation>");
		Rectangle2D boundRectangle = GraphConstants.getBounds(this.getAttributes());
		int xCoordinate = (int) boundRectangle.getX();
		int yCoordinate = (int) boundRectangle.getY();
		printStream.println("			<xCoordinate>" + xCoordinate + "</xCoordinate>");
		printStream.println("			<yCoordinate>" + yCoordinate + "</yCoordinate>");
		String panelClassName = graphPanel.getClass().getName();
		if (panelClassName.equals(OWLClassTransformationGraph.class.getName())) {
			printStream.println("			<OWLClassUMLAssociationTransformationMapping>");
			owlClassUmlAssociationMapping.saveMappingConfiguration(printStream);
			printStream.println("			</OWLClassUMLAssociationTransformationMapping>");
		} else if (panelClassName.equals(OWLDatatypePropertyTransformationGraph.class.getName())) {
			printStream.println("			<OWLDatatypePropertyUMLAssociationTransformationMapping>");
			owlDatatypePropertyUmlAssociationMapping.saveMappingConfiguration(printStream);
			printStream.println("			</OWLDatatypePropertyUMLAssociationTransformationMapping>");
		} else if (panelClassName.equals(OWLObjectPropertyTransformationGraph.class.getName())) {
			printStream.println("			<OWLObjectPropertyUMLAssociationTransformationMapping>");
			owlObjectPropertyUmlAssociationMapping.saveMappingConfiguration(printStream);
			printStream.println("			</OWLObjectPropertyUMLAssociationTransformationMapping>");
		}
		printStream.println("		</UMLAssociation>");
	}

	/**
	 * Loads the coordinate data and transformation configuration. Reads the
	 * <TransformationRule> tag and loads it to the UMLAssociationTransformationRule
	 * instance of this UMLAssociationGraphCell.
	 */
	public void loadRulesConfiguration(Element umlAssociationNode) {
		tracer.debug("Loading the transformation rules");
		NodeList xCoordinateNodes = umlAssociationNode.getElementsByTagName("xCoordinate");
		Node xCoordinateNode = xCoordinateNodes.item(0);
		Node xCoordinateNodeContent = xCoordinateNode.getFirstChild();
		int xCoordinate = Integer.parseInt(xCoordinateNodeContent.getTextContent());
		NodeList yCoordinateNodes = umlAssociationNode.getElementsByTagName("yCoordinate");
		Node yCoordinateNode = yCoordinateNodes.item(0);
		Node yCoordinateNodeContent = yCoordinateNode.getFirstChild();
		int yCoordinate = Integer.parseInt(yCoordinateNodeContent.getTextContent());
		GraphConstants.setBounds(this.getAttributes(),
				new Rectangle2D.Double(xCoordinate, yCoordinate, CELL_WIDTH, CELL_HEIGHT));
		String panelTypeName = graphPanel.getClass().getName();
		if (panelTypeName.equals(OWLClassTransformationGraph.class.getName())) {
			owlClassUmlAssociationMapping = new OWLClassUMLAssociationTransformationMapping();
			Node transformationNode = umlAssociationNode
					.getElementsByTagName("OWLClassUMLAssociationTransformationMapping").item(0);
			NodeList nodesOfSingleMapping = transformationNode.getChildNodes();
			owlClassUmlAssociationMapping.loadMappingConfiguration(nodesOfSingleMapping);
		} else if (panelTypeName.equals(OWLDatatypePropertyTransformationGraph.class.getName())) {
			owlDatatypePropertyUmlAssociationMapping = new OWLDatatypePropertyUMLAssociationTransformationMapping();
			Node transformationNode = umlAssociationNode
					.getElementsByTagName("OWLDatatypePropertyUMLAssociationTransformationMapping").item(0);
			NodeList nodesOfSingleMapping = transformationNode.getChildNodes();
			owlDatatypePropertyUmlAssociationMapping.loadMappingConfiguration(nodesOfSingleMapping);
		} else if (panelTypeName.equals(OWLObjectPropertyTransformationGraph.class.getName())) {
			owlObjectPropertyUmlAssociationMapping = new OWLObjectPropertyUMLAssociationTransformationMapping();
			Node transformationNode = umlAssociationNode
					.getElementsByTagName("OWLObjectPropertyUMLAssociationTransformationMapping").item(0);
			NodeList nodesOfSingleMapping = transformationNode.getChildNodes();
			owlObjectPropertyUmlAssociationMapping.loadMappingConfiguration(nodesOfSingleMapping);
		}
	}

	/**
	 * Class that extends the popup Menu. It displays the "Transformation Rules"
	 * menu on the popup menus.
	 */
	private class UMLAssociationGraphCellPopupMenu extends JPopupMenu implements ActionListener {
		private static final long serialVersionUID = 1L;
		public static final String SHOW_RULES_TEXT = "Transformation Rules";
		public static final String REMOVE_CELL_TEXT = "Remove the UMLAssociation Cell";

		private JMenuItem showRulesMenuItem;
		private JMenuItem removeCellMenuItem;
		private UMLAssociationGraphCell umlAssociationGraphCell;
		private OWLClassUMLAssociationTransformationMappingPanel owlClassUmlAssociationMappingPanel;
		private OWLDatatypePropertyUMLAssociationTransformationMappingPanel owlDatatypePropertyUmlAssociationMappingPanel;
		private OWLObjectPropertyUMLAssociationTransformationMappingPanel owlObjectPropertyUmlAssociationMappingPanel;

		/**
		 * constructor
		 */
		public UMLAssociationGraphCellPopupMenu(UMLAssociationGraphCell umlAssociationGraphCell) {
			super();
			this.umlAssociationGraphCell = umlAssociationGraphCell;
			showRulesMenuItem = new JMenuItem(SHOW_RULES_TEXT);
			removeCellMenuItem = new JMenuItem(REMOVE_CELL_TEXT);
			this.add(showRulesMenuItem);
			this.add(removeCellMenuItem);
			showRulesMenuItem.addActionListener(this);
			removeCellMenuItem.addActionListener(this);
		}

		/**
		 * ActionListener for the popup menu. If "Transformation Rules" menu is chosen,
		 * creates an instance of UMLAssociationTransformationRulesPanel and displays it
		 * for the transformation rule associated with this UMLAssociationGraphCell.
		 */
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getActionCommand().equals(SHOW_RULES_TEXT)) {
				tracer.debug("Transformation Rules menu clicked on UMLAssociationGraphCell");
				JDialog dialog = new JDialog(graphPanel.getFrame(), SHOW_RULES_TEXT);
				String panelClassName = graphPanel.getClass().getName();
				if (panelClassName.equals(OWLClassTransformationGraph.class.getName())) {
					owlClassUmlAssociationMappingPanel = new OWLClassUMLAssociationTransformationMappingPanel(dialog,
							umlAssociationGraphCell);
					owlClassUmlAssociationMappingPanel.readTransformationMapping(owlClassUmlAssociationMapping);
					dialog.getContentPane().add(owlClassUmlAssociationMappingPanel);
				} else if (panelClassName.equals(OWLDatatypePropertyTransformationGraph.class.getName())) {
					owlDatatypePropertyUmlAssociationMappingPanel = new OWLDatatypePropertyUMLAssociationTransformationMappingPanel(
							dialog, umlAssociationGraphCell);
					owlDatatypePropertyUmlAssociationMappingPanel
							.readTransformationMapping(owlDatatypePropertyUmlAssociationMapping);
					dialog.getContentPane().add(owlDatatypePropertyUmlAssociationMappingPanel);
				} else if (panelClassName.equals(OWLObjectPropertyTransformationGraph.class.getName())) {
					owlObjectPropertyUmlAssociationMappingPanel = new OWLObjectPropertyUMLAssociationTransformationMappingPanel(
							dialog, umlAssociationGraphCell);
					owlObjectPropertyUmlAssociationMappingPanel
							.readTransformationMapping(owlObjectPropertyUmlAssociationMapping);
					dialog.getContentPane().add(owlObjectPropertyUmlAssociationMappingPanel);
				}
				dialog.setResizable(false);
				dialog.pack();
				dialog.setModal(true);
				dialog.setVisible(true);
			} else if (arg0.getActionCommand().equals(REMOVE_CELL_TEXT)) {
				graphPanel.removeUMLAssociationCell(umlAssociationGraphCell);
			}
		}
	}

}