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
import owl2uml.transformation.mapping.OWLClassUMLOperationTransformationMapping;
import owl2uml.transformation.mapping.OWLDatatypePropertyUMLOperationTransformationMapping;
import owl2uml.transformation.mapping.OWLObjectPropertyUMLOperationTransformationMapping;
import owl2uml.transformation.mapping.panel.OWLClassUMLOperationTransformationMappingPanel;
import owl2uml.transformation.mapping.panel.OWLDatatypePropertyUMLOperationTransformationMappingPanel;
import owl2uml.transformation.mapping.panel.OWLObjectPropertyUMLOperationTransformationMappingPanel;

/**
 * @author Ozer Ozdikis
 * @version June 2006
 */
public class UMLOperationGraphCell extends AUMLGraphCell {
	private static final long serialVersionUID = 1L;
	public static String CELL_TYPE = "UMLOperationGraphCell";
	private OWLClassUMLOperationTransformationMapping owlClassUmlOperationMapping = new OWLClassUMLOperationTransformationMapping();
	private OWLDatatypePropertyUMLOperationTransformationMapping owlDatatypePropertyUmlOperationMapping = new OWLDatatypePropertyUMLOperationTransformationMapping();
	private OWLObjectPropertyUMLOperationTransformationMapping owlObjectPropertyUmlOperationMapping = new OWLObjectPropertyUMLOperationTransformationMapping();

	/**
	 * constructor
	 */
	public UMLOperationGraphCell(ATransformationGraph graphPanel, int x, int y) {
		super(graphPanel, x, y);
		GraphConstants.setIcon(this.getAttributes(), new ImageIcon(GlobalVariables.ICON_UML_OPERATION));
	}

	/**
	 * Opens the popup menu when right clicked on this UMLClassGraphCell instance
	 */
	public void openPopupMenu(int x, int y) {
		tracer.debug("OpenPopupMenu is called for the UMLOperationGraphCell...");
		UMLOperationGraphCellPopupMenu popupMenu = new UMLOperationGraphCellPopupMenu(this);
		popupMenu.show(graphPanel, x, y);
	}

	public void setOWLClassUMLOperationTransformationMapping(OWLClassUMLOperationTransformationMapping mapping) {
		this.owlClassUmlOperationMapping = mapping;
	}

	public OWLClassUMLOperationTransformationMapping getOWLClassUMLOperationTransformationMapping() {
		return owlClassUmlOperationMapping;
	}

	public OWLDatatypePropertyUMLOperationTransformationMapping getOWLDatatypePropertyUMLOperationTransformationMapping() {
		return owlDatatypePropertyUmlOperationMapping;
	}

	public void setOWLDatatypePropertyUMLOperationTransformationMapping(
			OWLDatatypePropertyUMLOperationTransformationMapping owlDatatypePropertyUmlOperationMapping) {
		this.owlDatatypePropertyUmlOperationMapping = owlDatatypePropertyUmlOperationMapping;
	}

	public OWLObjectPropertyUMLOperationTransformationMapping getOWLObjectPropertyUMLOperationTransformationMapping() {
		return owlObjectPropertyUmlOperationMapping;
	}

	public void setOWLObjectPropertyUMLOperationTransformationMapping(
			OWLObjectPropertyUMLOperationTransformationMapping owlObjectPropertyUmlOperationMapping) {
		this.owlObjectPropertyUmlOperationMapping = owlObjectPropertyUmlOperationMapping;
	}

	/**
	 * Saves the transformation configuration. After adding <UMLClass> tag and the
	 * coordinates, adds the <TransformationRule> tag and delegates the operation to
	 * UMLClassTransformationRule.
	 */
	public void saveRulesConfiguration(PrintStream printStream) {
		tracer.debug("Saving the transformation rules");
		printStream.println("		<UMLOperation>");
		Rectangle2D boundRectangle = GraphConstants.getBounds(this.getAttributes());
		int xCoordinate = (int) boundRectangle.getX();
		int yCoordinate = (int) boundRectangle.getY();
		printStream.println("			<xCoordinate>" + xCoordinate + "</xCoordinate>");
		printStream.println("			<yCoordinate>" + yCoordinate + "</yCoordinate>");
		String panelClassName = graphPanel.getClass().getName();
		if (panelClassName.equals(OWLClassTransformationGraph.class.getName())) {
			printStream.println("			<OWLClassUMLOperationTransformationMapping>");
			owlClassUmlOperationMapping.saveMappingConfiguration(printStream);
			printStream.println("			</OWLClassUMLOperationTransformationMapping>");
		} else if (panelClassName.equals(OWLDatatypePropertyTransformationGraph.class.getName())) {
			printStream.println("			<OWLDatatypePropertyUMLOperationTransformationMapping>");
			owlDatatypePropertyUmlOperationMapping.saveMappingConfiguration(printStream);
			printStream.println("			</OWLDatatypePropertyUMLOperationTransformationMapping>");
		} else if (panelClassName.equals(OWLObjectPropertyTransformationGraph.class.getName())) {
			printStream.println("			<OWLObjectPropertyUMLOperationTransformationMapping>");
			owlObjectPropertyUmlOperationMapping.saveMappingConfiguration(printStream);
			printStream.println("			</OWLObjectPropertyUMLOperationTransformationMapping>");
		}
		printStream.println("		</UMLOperation>");
	}

	/**
	 * Loads the coordinate data and transformation configuration. Reads the
	 * <TransformationRule> tag and loads it to the UMLClassTransformationRule
	 * instance of this UMLClassGraphCell.
	 */
	public void loadRulesConfiguration(Element umlClassNode) {
		tracer.debug("Loading the transformation rules");
		NodeList xCoordinateNodes = umlClassNode.getElementsByTagName("xCoordinate");
		Node xCoordinateNode = xCoordinateNodes.item(0);
		Node xCoordinateNodeContent = xCoordinateNode.getFirstChild();
		int xCoordinate = Integer.parseInt(xCoordinateNodeContent.getTextContent());
		NodeList yCoordinateNodes = umlClassNode.getElementsByTagName("yCoordinate");
		Node yCoordinateNode = yCoordinateNodes.item(0);
		Node yCoordinateNodeContent = yCoordinateNode.getFirstChild();
		int yCoordinate = Integer.parseInt(yCoordinateNodeContent.getTextContent());
		GraphConstants.setBounds(this.getAttributes(),
				new Rectangle2D.Double(xCoordinate, yCoordinate, CELL_WIDTH, CELL_HEIGHT));
		String panelClassName = graphPanel.getClass().getName();
		if (panelClassName.equals(OWLClassTransformationGraph.class.getName())) {
			owlClassUmlOperationMapping = new OWLClassUMLOperationTransformationMapping();
			Node transformationNode = umlClassNode.getElementsByTagName("OWLClassUMLOperationTransformationMapping")
					.item(0);
			NodeList nodesOfSingleMapping = transformationNode.getChildNodes();
			owlClassUmlOperationMapping.loadMappingConfiguration(nodesOfSingleMapping);
		} else if (panelClassName.equals(OWLDatatypePropertyTransformationGraph.class.getName())) {
			owlDatatypePropertyUmlOperationMapping = new OWLDatatypePropertyUMLOperationTransformationMapping();
			Node transformationNode = umlClassNode
					.getElementsByTagName("OWLDatatypePropertyUMLOperationTransformationMapping").item(0);
			NodeList nodesOfSingleMapping = transformationNode.getChildNodes();
			owlDatatypePropertyUmlOperationMapping.loadMappingConfiguration(nodesOfSingleMapping);
		} else if (panelClassName.equals(OWLObjectPropertyTransformationGraph.class.getName())) {
			owlObjectPropertyUmlOperationMapping = new OWLObjectPropertyUMLOperationTransformationMapping();
			Node transformationNode = umlClassNode
					.getElementsByTagName("OWLObjectPropertyUMLOperationTransformationMapping").item(0);
			NodeList nodesOfSingleMapping = transformationNode.getChildNodes();
			owlObjectPropertyUmlOperationMapping.loadMappingConfiguration(nodesOfSingleMapping);
		}
	}

	/**
	 * Class that extends the popup Menu. It displays the "Transformation Rules"
	 * menu on the popup menus.
	 */
	private class UMLOperationGraphCellPopupMenu extends JPopupMenu implements ActionListener {
		private static final long serialVersionUID = 1L;
		public static final String SHOW_RULES_TEXT = "Transformation Rule";
		public static final String REMOVE_CELL_TEXT = "Remove the UMLOperationParameter Cell";

		private UMLOperationGraphCell umlOperationGraphCell;
		private JMenuItem showRulesMenuItem;
		private JMenuItem removeCellMenuItem;
		private OWLClassUMLOperationTransformationMappingPanel owlClassUMLOperationMappingPanel;
		private OWLDatatypePropertyUMLOperationTransformationMappingPanel owlDatatypePropertyUMLOperationMappingPanel;
		private OWLObjectPropertyUMLOperationTransformationMappingPanel owlObjectPropertyUMLOperationMappingPanel;

		/**
		 * constructor
		 */
		public UMLOperationGraphCellPopupMenu(UMLOperationGraphCell umlOperationGraphCell) {
			super();
			this.umlOperationGraphCell = umlOperationGraphCell;
			showRulesMenuItem = new JMenuItem(SHOW_RULES_TEXT);
			removeCellMenuItem = new JMenuItem(REMOVE_CELL_TEXT);
			this.add(showRulesMenuItem);
			this.add(removeCellMenuItem);
			showRulesMenuItem.addActionListener(this);
			removeCellMenuItem.addActionListener(this);
		}

		/**
		 */
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getActionCommand().equals(SHOW_RULES_TEXT)) {
				tracer.debug("Transformation Rules menu clicked on UMLOperationGraphCell");
				JDialog dialog = new JDialog(graphPanel.getFrame(), SHOW_RULES_TEXT);
				String panelClassName = graphPanel.getClass().getName();
				if (panelClassName.equals(OWLClassTransformationGraph.class.getName())) {
					owlClassUMLOperationMappingPanel = new OWLClassUMLOperationTransformationMappingPanel(dialog,
							umlOperationGraphCell);
					owlClassUMLOperationMappingPanel.readTransformationMapping(owlClassUmlOperationMapping);
					dialog.getContentPane().add(owlClassUMLOperationMappingPanel);
					dialog.setResizable(false);
					dialog.pack();
					dialog.setModal(true);
					dialog.setVisible(true);
				} else if (panelClassName.equals(OWLDatatypePropertyTransformationGraph.class.getName())) {
					owlDatatypePropertyUMLOperationMappingPanel = new OWLDatatypePropertyUMLOperationTransformationMappingPanel(
							dialog, umlOperationGraphCell);
					owlDatatypePropertyUMLOperationMappingPanel
							.readTransformationMapping(owlDatatypePropertyUmlOperationMapping);
					dialog.getContentPane().add(owlDatatypePropertyUMLOperationMappingPanel);
					dialog.setResizable(false);
					dialog.pack();
					dialog.setModal(true);
					dialog.setVisible(true);
				} else if (panelClassName.equals(OWLObjectPropertyTransformationGraph.class.getName())) {
					owlObjectPropertyUMLOperationMappingPanel = new OWLObjectPropertyUMLOperationTransformationMappingPanel(
							dialog, umlOperationGraphCell);
					owlObjectPropertyUMLOperationMappingPanel
							.readTransformationMapping(owlObjectPropertyUmlOperationMapping);
					dialog.getContentPane().add(owlObjectPropertyUMLOperationMappingPanel);
					dialog.setResizable(false);
					dialog.pack();
					dialog.setModal(true);
					dialog.setVisible(true);
				}

			} else if (arg0.getActionCommand().equals(REMOVE_CELL_TEXT)) {
				graphPanel.removeUMLOperationCell(umlOperationGraphCell);
			}
		}
	}

}
