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
import owl2uml.transformation.mapping.OWLClassUMLClassTransformationMapping;
import owl2uml.transformation.mapping.OWLDatatypePropertyUMLClassTransformationMapping;
import owl2uml.transformation.mapping.OWLObjectPropertyUMLClassTransformationMapping;
import owl2uml.transformation.mapping.panel.OWLClassUMLClassTransformationMappingPanel;
import owl2uml.transformation.mapping.panel.OWLDatatypePropertyUMLClassTransformationMappingPanel;
import owl2uml.transformation.mapping.panel.OWLObjectPropertyUMLClassTransformationMappingPanel;

/**
 * Class that represents the range UML classes on the graph gui. It has the
 * coordinate variables, and includes a UMLClassTransformationRule instance. It
 * has a PopupMenu to enable the configuration of UMLClassTransformationRule,
 * and to delete this UMLClassGraphCell from the graph gui.
 * 
 * @author Ozer Ozdikis
 * @version June 2006
 */
public class UMLClassGraphCell extends AUMLGraphCell {
	private static final long serialVersionUID = 1L;
	public static String CELL_TYPE = "UMLClassGraphCell";
	private OWLClassUMLClassTransformationMapping owlClassUmlClassMapping = new OWLClassUMLClassTransformationMapping();
	private OWLDatatypePropertyUMLClassTransformationMapping owlDatatypePropertyUmlClassMapping = new OWLDatatypePropertyUMLClassTransformationMapping();
	private OWLObjectPropertyUMLClassTransformationMapping owlObjectPropertyUmlClassMapping = new OWLObjectPropertyUMLClassTransformationMapping();

	/**
	 * constructor
	 */
	public UMLClassGraphCell(ATransformationGraph graphPanel, int x, int y) {
		super(graphPanel, x, y);
		GraphConstants.setIcon(this.getAttributes(), new ImageIcon(GlobalVariables.ICON_UML_CLASS));
	}

	/**
	 * Opens the popup menu when right clicked on this UMLClassGraphCell instance
	 */
	public void openPopupMenu(int x, int y) {
		tracer.debug("OpenPopupMenu is called for the UMLClassGraphCell...");
		UMLClassGraphCellPopupMenu popupMenu = new UMLClassGraphCellPopupMenu(this);
		popupMenu.show(graphPanel, x, y);
	}

	public void setOWLClassUMLClassTransformationMapping(OWLClassUMLClassTransformationMapping mapping) {
		this.owlClassUmlClassMapping = mapping;
	}

	public OWLClassUMLClassTransformationMapping getOWLClassUMLClassTransformationMapping() {
		return owlClassUmlClassMapping;
	}

	public OWLDatatypePropertyUMLClassTransformationMapping getOWLDatatypePropertyUMLClassTransformationMapping() {
		return owlDatatypePropertyUmlClassMapping;
	}

	public void setOWLDatatypePropertyUMLClassTransformationMapping(
			OWLDatatypePropertyUMLClassTransformationMapping owlDatatypePropertyUmlClassMapping) {
		this.owlDatatypePropertyUmlClassMapping = owlDatatypePropertyUmlClassMapping;
	}

	public OWLObjectPropertyUMLClassTransformationMapping getOWLObjectPropertyUMLClassTransformationMapping() {
		return owlObjectPropertyUmlClassMapping;
	}

	public void setOWLObjectPropertyUMLClassTransformationMapping(
			OWLObjectPropertyUMLClassTransformationMapping owlObjectPropertyUmlClassMapping) {
		this.owlObjectPropertyUmlClassMapping = owlObjectPropertyUmlClassMapping;
	}

	/**
	 * Saves the transformation configuration. After adding <UMLClass> tag and the
	 * coordinates, adds the <TransformationRule> tag and delegates the operation to
	 * UMLClassTransformationRule.
	 */
	public void saveRulesConfiguration(PrintStream printStream) {
		tracer.debug("Saving the transformation rules");
		printStream.println("		<UMLClass>");
		Rectangle2D boundRectangle = GraphConstants.getBounds(this.getAttributes());
		int xCoordinate = (int) boundRectangle.getX();
		int yCoordinate = (int) boundRectangle.getY();
		printStream.println("			<xCoordinate>" + xCoordinate + "</xCoordinate>");
		printStream.println("			<yCoordinate>" + yCoordinate + "</yCoordinate>");
		String panelClassName = graphPanel.getClass().getName();
		if (panelClassName.equals(OWLClassTransformationGraph.class.getName())) {
			printStream.println("			<OWLClassUMLClassTransformationMapping>");
			owlClassUmlClassMapping.saveMappingConfiguration(printStream);
			printStream.println("			</OWLClassUMLClassTransformationMapping>");
		} else if (panelClassName.equals(OWLDatatypePropertyTransformationGraph.class.getName())) {
			printStream.println("			<OWLDatatypePropertyUMLClassTransformationMapping>");
			owlDatatypePropertyUmlClassMapping.saveMappingConfiguration(printStream);
			printStream.println("			</OWLDatatypePropertyUMLClassTransformationMapping>");
		} else if (panelClassName.equals(OWLObjectPropertyTransformationGraph.class.getName())) {
			printStream.println("			<OWLObjectPropertyUMLClassTransformationMapping>");
			owlObjectPropertyUmlClassMapping.saveMappingConfiguration(printStream);
			printStream.println("			</OWLObjectPropertyUMLClassTransformationMapping>");
		}
		printStream.println("		</UMLClass>");
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
			owlClassUmlClassMapping = new OWLClassUMLClassTransformationMapping();
			Node transformationNode = umlClassNode.getElementsByTagName("OWLClassUMLClassTransformationMapping")
					.item(0);
			NodeList nodesOfSingleMapping = transformationNode.getChildNodes();
			owlClassUmlClassMapping.loadMappingConfiguration(nodesOfSingleMapping);
		} else if (panelClassName.equals(OWLDatatypePropertyTransformationGraph.class.getName())) {
			owlDatatypePropertyUmlClassMapping = new OWLDatatypePropertyUMLClassTransformationMapping();
			Node transformationNode = umlClassNode
					.getElementsByTagName("OWLDatatypePropertyUMLClassTransformationMapping").item(0);
			NodeList nodesOfSingleMapping = transformationNode.getChildNodes();
			owlDatatypePropertyUmlClassMapping.loadMappingConfiguration(nodesOfSingleMapping);
		} else if (panelClassName.equals(OWLObjectPropertyTransformationGraph.class.getName())) {
			owlObjectPropertyUmlClassMapping = new OWLObjectPropertyUMLClassTransformationMapping();
			Node transformationNode = umlClassNode
					.getElementsByTagName("OWLObjectPropertyUMLClassTransformationMapping").item(0);
			NodeList nodesOfSingleMapping = transformationNode.getChildNodes();
			owlObjectPropertyUmlClassMapping.loadMappingConfiguration(nodesOfSingleMapping);
		}
	}

	/**
	 * Class that extends the popup Menu. It displays the "Transformation Rules"
	 * menu on the popup menus.
	 */
	private class UMLClassGraphCellPopupMenu extends JPopupMenu implements ActionListener {
		private static final long serialVersionUID = 1L;
		public static final String SHOW_RULES_TEXT = "Transformation Rule";
		public static final String REMOVE_CELL_TEXT = "Remove the UMLClass Cell";

		private UMLClassGraphCell umlClassGraphCell;
		private JMenuItem showRulesMenuItem;
		private JMenuItem removeCellMenuItem;
		private OWLClassUMLClassTransformationMappingPanel owlClassUMLClassMappingPanel;
		private OWLDatatypePropertyUMLClassTransformationMappingPanel owlDatatypePropertyUMLClassMappingPanel;
		private OWLObjectPropertyUMLClassTransformationMappingPanel owlObjectPropertyUMLClassMappingPanel;

		/**
		 * constructor
		 */
		public UMLClassGraphCellPopupMenu(UMLClassGraphCell umlClassGraphCell) {
			super();
			this.umlClassGraphCell = umlClassGraphCell;
			showRulesMenuItem = new JMenuItem(SHOW_RULES_TEXT);
			removeCellMenuItem = new JMenuItem(REMOVE_CELL_TEXT);
			this.add(showRulesMenuItem);
			this.add(removeCellMenuItem);
			showRulesMenuItem.addActionListener(this);
			removeCellMenuItem.addActionListener(this);
		}

		/**
		 * ActionListener for the popup menu. If "Transformation Rules" menu is chosen,
		 * creates an instance of UMLClassTransformationRulesPanel and displays it for
		 * the transformation rules associated with this UMLClassGraphCell.
		 */
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getActionCommand().equals(SHOW_RULES_TEXT)) {
				tracer.debug("Transformation Rules menu clicked on UMLClassGraphCell");
				JDialog dialog = new JDialog(graphPanel.getFrame(), SHOW_RULES_TEXT);
				String panelClassName = graphPanel.getClass().getName();
				if (panelClassName.equals(OWLClassTransformationGraph.class.getName())) {
					owlClassUMLClassMappingPanel = new OWLClassUMLClassTransformationMappingPanel(dialog,
							umlClassGraphCell);
					owlClassUMLClassMappingPanel.readTransformationMapping(owlClassUmlClassMapping);
					dialog.getContentPane().add(owlClassUMLClassMappingPanel);
					dialog.setResizable(false);
					dialog.pack();
					dialog.setModal(true);
					dialog.setVisible(true);
				} else if (panelClassName.equals(OWLDatatypePropertyTransformationGraph.class.getName())) {
					owlDatatypePropertyUMLClassMappingPanel = new OWLDatatypePropertyUMLClassTransformationMappingPanel(
							dialog, umlClassGraphCell);
					owlDatatypePropertyUMLClassMappingPanel
							.readTransformationMapping(owlDatatypePropertyUmlClassMapping);
					dialog.getContentPane().add(owlDatatypePropertyUMLClassMappingPanel);
					dialog.setResizable(false);
					dialog.pack();
					dialog.setModal(true);
					dialog.setVisible(true);
				} else if (panelClassName.equals(OWLObjectPropertyTransformationGraph.class.getName())) {
					owlObjectPropertyUMLClassMappingPanel = new OWLObjectPropertyUMLClassTransformationMappingPanel(
							dialog, umlClassGraphCell);
					owlObjectPropertyUMLClassMappingPanel.readTransformationMapping(owlObjectPropertyUmlClassMapping);
					dialog.getContentPane().add(owlObjectPropertyUMLClassMappingPanel);
					dialog.setResizable(false);
					dialog.pack();
					dialog.setModal(true);
					dialog.setVisible(true);
				}

			} else if (arg0.getActionCommand().equals(REMOVE_CELL_TEXT)) {
				graphPanel.removeUMLClassCell(umlClassGraphCell);
			}
		}
	}

}
