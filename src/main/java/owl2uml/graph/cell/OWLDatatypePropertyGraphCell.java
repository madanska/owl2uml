package owl2uml.graph.cell;

import java.awt.geom.Rectangle2D;
import java.io.PrintStream;

import javax.swing.ImageIcon;
import javax.swing.JDialog;

import org.jgraph.graph.GraphConstants;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import owl2uml.GlobalVariables;
import owl2uml.graph.ATransformationGraph;
import owl2uml.transformation.condition.OWLPropertyTransformationCondition;
import owl2uml.transformation.condition.panel.OWLPropertyTransformationConditionPanel;

/**
 * Class that extends the OWLGrapchCell, specialized for OWLDatatypeProperty.
 * 
 * @author Ozer Ozdikis
 * @version June 2006
 */
public class OWLDatatypePropertyGraphCell extends AOWLGraphCell {
	private static final long serialVersionUID = 1L;
	public static String CELL_TYPE = "OWLDatatypePropertyGraphCell";

	/**
	 * constructor
	 */
	public OWLDatatypePropertyGraphCell(ATransformationGraph graphPanel, int x, int y) {
		super(graphPanel, x, y);
		GraphConstants.setIcon(this.getAttributes(), new ImageIcon(GlobalVariables.ICON_OWL_DATATYPE_PROPERTY));
		transformationCondition = new OWLPropertyTransformationCondition();
	}

	/**
	 * Opens the popup menu when right clicked on this OWLDatatypePropertyGraphCell
	 * instance
	 */
	public void openPopupMenu(int x, int y) {
		tracer.debug("OpenPopupMenu is called for the OWLDatatypePropertyGraphCell...");
		OWLGraphCellPopupMenu popupMenu = new OWLGraphCellPopupMenu();
		popupMenu.show(graphPanel, x, y);
	}

	/**
	 * Saves the transformation condition. After adding <OWLDatatypeProperty> tag,
	 * and <TransformationCondition> tag. Then delegates saving to the
	 * TransformationCondition instance in this owlClassGraphCell.
	 */
	public void saveRulesConfiguration(PrintStream printStream) {
		tracer.debug("Saving the owlDatatypePropertyGraphCell transformation condition");
		Rectangle2D boundRectangle = GraphConstants.getBounds(this.getAttributes());
		int xCoordinate = (int) boundRectangle.getX();
		int yCoordinate = (int) boundRectangle.getY();
		printStream.println("		<OWLDatatypeProperty>");
		printStream.println("			<xCoordinate>" + xCoordinate + "</xCoordinate>");
		printStream.println("			<yCoordinate>" + yCoordinate + "</yCoordinate>");
		printStream.println("			<TransformationCondition>");
		transformationCondition.saveConditionConfiguration(printStream);
		printStream.println("			</TransformationCondition>");
		printStream.println("		</OWLDatatypeProperty>");
	}

	/**
	 * Loads the transformation condition. Reads the <TransformationCondition> tag,
	 * reads the configuration into the transformationCondition of this
	 * OWLDatatypePropertyGraphCell.
	 */
	public void loadRulesConfiguration(Element owlDatatypePropertyGraphCellNode) {
		tracer.debug("Loading the owlDatatypePropertyGraphCell transformation condition");
		NodeList xCoordinateNodes = owlDatatypePropertyGraphCellNode.getElementsByTagName("xCoordinate");
		Node xCoordinateNode = xCoordinateNodes.item(0);
		Node xCoordinateNodeContent = xCoordinateNode.getFirstChild();
		int xCoordinate = Integer.parseInt(xCoordinateNodeContent.getTextContent());
		NodeList yCoordinateNodes = owlDatatypePropertyGraphCellNode.getElementsByTagName("yCoordinate");
		Node yCoordinateNode = yCoordinateNodes.item(0);
		Node yCoordinateNodeContent = yCoordinateNode.getFirstChild();
		int yCoordinate = Integer.parseInt(yCoordinateNodeContent.getTextContent());
		GraphConstants.setBounds(this.getAttributes(),
				new Rectangle2D.Double(xCoordinate, yCoordinate, CELL_WIDTH, CELL_HEIGHT));
		NodeList conditionNodes = owlDatatypePropertyGraphCellNode.getElementsByTagName("TransformationCondition");
		Node conditionNode = conditionNodes.item(0);
		NodeList nodesOfCondition = conditionNode.getChildNodes();
		transformationCondition.loadConditionConfiguration(nodesOfCondition);
	}

	/**
	 * Displays the transformation condition panel for this
	 * OWLDatatypePropertyGraphCell
	 */
	public void displayTransformationConditionPanel() {
		tracer.debug("Transformation Condition menu clicked on OWLDatatypePropertyGraphCell");
		JDialog dialog = new JDialog(graphPanel.getFrame(), OWLGraphCellPopupMenu.SHOW_CONDITION_TEXT);
		OWLPropertyTransformationConditionPanel transformationConditionPanel = new OWLPropertyTransformationConditionPanel(
				dialog, (OWLPropertyTransformationCondition) transformationCondition);
		dialog.getContentPane().add(transformationConditionPanel);
		dialog.setResizable(false);
		dialog.pack();
		dialog.setModal(true);
		dialog.setVisible(true);
	}

	public OWLPropertyTransformationCondition getTransformationCondition() {
		return (OWLPropertyTransformationCondition) transformationCondition;
	}

}
