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
import owl2uml.transformation.condition.OWLClassTransformationCondition;
import owl2uml.transformation.condition.panel.OWLClassTransformationConditionPanel;

/**
 * Class that extends the OWLGraphCell, specialized for OWLClass.
 * 
 * @author Ozer Ozdikis
 * @version June 2006
 */
public class OWLClassGraphCell extends AOWLGraphCell {
	private static final long serialVersionUID = 1L;

	/**
	 * constructor
	 */
	public OWLClassGraphCell(ATransformationGraph graphPanel, int x, int y) {
		super(graphPanel, x, y);
		GraphConstants.setIcon(this.getAttributes(), new ImageIcon(GlobalVariables.ICON_OWL_CLASS));
		transformationCondition = new OWLClassTransformationCondition();
	}

	/**
	 * Opens the popup menu when right clicked on this OWLClassGraphCell instance
	 */
	public void openPopupMenu(int x, int y) {
		tracer.debug("OpenPopupMenu is called for the OWLClassGraphCell...");
		OWLGraphCellPopupMenu popupMenu = new OWLGraphCellPopupMenu();
		popupMenu.show(graphPanel, x, y);
	}

	/**
	 * Saves the transformation condition. After adding <OWLClass> tag, and
	 * <TransformationCondition> tag. Then delegates saving to the
	 * TransformationCondition instance in this owlClassGraphCell.
	 */
	public void saveRulesConfiguration(PrintStream printStream) {
		tracer.debug("Saving the owlClassGraphCell transformation condition");
		Rectangle2D boundRectangle = GraphConstants.getBounds(this.getAttributes());
		int xCoordinate = (int) boundRectangle.getX();
		int yCoordinate = (int) boundRectangle.getY();
		printStream.println("		<OWLClass>");
		printStream.println("			<xCoordinate>" + xCoordinate + "</xCoordinate>");
		printStream.println("			<yCoordinate>" + yCoordinate + "</yCoordinate>");
		printStream.println("			<TransformationCondition>");
		transformationCondition.saveConditionConfiguration(printStream);
		printStream.println("			</TransformationCondition>");
		printStream.println("		</OWLClass>");
	}

	/**
	 * Loads the transformation condition. Reads the <TransformationCondition> tag,
	 * reads the configuration into the transformationCondition of this
	 * owlClassGraphCell.
	 */
	public void loadRulesConfiguration(Element owlClassGraphCellNode) {
		tracer.debug("Loading the owlClassGraphCell transformation condition");
		NodeList xCoordinateNodes = owlClassGraphCellNode.getElementsByTagName("xCoordinate");
		Node xCoordinateNode = xCoordinateNodes.item(0);
		Node xCoordinateNodeContent = xCoordinateNode.getFirstChild();
		int xCoordinate = Integer.parseInt(xCoordinateNodeContent.getTextContent());
		NodeList yCoordinateNodes = owlClassGraphCellNode.getElementsByTagName("yCoordinate");
		Node yCoordinateNode = yCoordinateNodes.item(0);
		Node yCoordinateNodeContent = yCoordinateNode.getFirstChild();
		int yCoordinate = Integer.parseInt(yCoordinateNodeContent.getTextContent());
		GraphConstants.setBounds(this.getAttributes(),
				new Rectangle2D.Double(xCoordinate, yCoordinate, CELL_WIDTH, CELL_HEIGHT));
		NodeList conditionNodes = owlClassGraphCellNode.getElementsByTagName("TransformationCondition");
		Node conditionNode = conditionNodes.item(0);
		NodeList nodesOfCondition = conditionNode.getChildNodes();
		transformationCondition.loadConditionConfiguration(nodesOfCondition);
	}

	/**
	 * Displays the transformation condition panel for this OWLClassGraphCell
	 */
	public void displayTransformationConditionPanel() {
		tracer.debug("Transformation Condition menu clicked on OWLClassGraphCell");
		JDialog dialog = new JDialog(graphPanel.getFrame(), OWLGraphCellPopupMenu.SHOW_CONDITION_TEXT);
		OWLClassTransformationConditionPanel transformationConditionPanel = new OWLClassTransformationConditionPanel(
				dialog, (OWLClassTransformationCondition) transformationCondition);
		dialog.getContentPane().add(transformationConditionPanel);
		dialog.setResizable(false);
		dialog.pack();
		dialog.setModal(true);
		dialog.setVisible(true);
	}

	public OWLClassTransformationCondition getTransformationCondition() {
		return (OWLClassTransformationCondition) transformationCondition;
	}
}
