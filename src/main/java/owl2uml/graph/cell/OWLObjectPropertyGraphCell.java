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
 * Class that extends the OWLGraphCell, specialized for OWLObjectProperty.
 * 
 * @author Ozer Ozdikis
 * @version June 2006
 */
public class OWLObjectPropertyGraphCell extends AOWLGraphCell {
	private static final long serialVersionUID = 1L;
	public static String CELL_TYPE = "OWLObjectPropertyGraphCell";

	/**
	 * constructor
	 */
	public OWLObjectPropertyGraphCell(ATransformationGraph graphPanel, int x, int y) {
		super(graphPanel, x, y);
		GraphConstants.setIcon(this.getAttributes(), new ImageIcon(GlobalVariables.ICON_OWL_OBJECT_PROPERTY));
		transformationCondition = new OWLPropertyTransformationCondition();
	}

	/**
	 * Opens the popup menu when right clicked on this OWLObjectPropertyGraphCell
	 * instance
	 */
	public void openPopupMenu(int x, int y) {
		tracer.debug("OpenPopupMenu is called for the OWLObjectPropertyGraphCell...");
		OWLGraphCellPopupMenu popupMenu = new OWLGraphCellPopupMenu();
		popupMenu.show(graphPanel, x, y);
	}

	/**
	 * Saves the transformation condition. After adding <OWLObjectProperty> tag, and
	 * <TransformationCondition> tag. Then delegates saving to the
	 * TransformationCondition instance in this owlClassGraphCell.
	 */
	public void saveRulesConfiguration(PrintStream printStream) {
		tracer.debug("Saving the owlObjectPropertyGraphCell transformation condition");
		Rectangle2D boundRectangle = GraphConstants.getBounds(this.getAttributes());
		int xCoordinate = (int) boundRectangle.getX();
		int yCoordinate = (int) boundRectangle.getY();
		printStream.println("		<OWLObjectProperty>");
		printStream.println("			<xCoordinate>" + xCoordinate + "</xCoordinate>");
		printStream.println("			<yCoordinate>" + yCoordinate + "</yCoordinate>");
		printStream.println("			<TransformationCondition>");
		transformationCondition.saveConditionConfiguration(printStream);
		printStream.println("			</TransformationCondition>");
		printStream.println("		</OWLObjectProperty>");
	}

	/**
	 * Loads the transformation condition. Reads the <TransformationCondition> tag,
	 * reads the configuration into the transformationCondition of this
	 * OWLObjectPropertyGraphCell.
	 */
	public void loadRulesConfiguration(Element owlObjectPropertyGraphCellNode) {
		tracer.debug("Loading the owlObjectPropertyGraphCell transformation condition");
		NodeList xCoordinateNodes = owlObjectPropertyGraphCellNode.getElementsByTagName("xCoordinate");
		Node xCoordinateNode = xCoordinateNodes.item(0);
		Node xCoordinateNodeContent = xCoordinateNode.getFirstChild();
		int xCoordinate = Integer.parseInt(xCoordinateNodeContent.getTextContent());
		NodeList yCoordinateNodes = owlObjectPropertyGraphCellNode.getElementsByTagName("yCoordinate");
		Node yCoordinateNode = yCoordinateNodes.item(0);
		Node yCoordinateNodeContent = yCoordinateNode.getFirstChild();
		int yCoordinate = Integer.parseInt(yCoordinateNodeContent.getTextContent());
		GraphConstants.setBounds(this.getAttributes(),
				new Rectangle2D.Double(xCoordinate, yCoordinate, CELL_WIDTH, CELL_HEIGHT));
		NodeList conditionNodes = owlObjectPropertyGraphCellNode.getElementsByTagName("TransformationCondition");
		Node conditionNode = conditionNodes.item(0);
		NodeList nodesOfCondition = conditionNode.getChildNodes();
		transformationCondition.loadConditionConfiguration(nodesOfCondition);
	}

	/**
	 * Displays the transformation condition panel for this
	 * OWLObjectPropertyGraphCell
	 */
	public void displayTransformationConditionPanel() {
		tracer.debug("Transformation Condition menu clicked on OWLObjectPropertyGraphCell");
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
