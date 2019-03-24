package owl2uml.graph.cell;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.io.PrintStream;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.w3c.dom.Element;

import owl2uml.graph.ATransformationGraph;
import owl2uml.transformation.condition.ATransformationCondition;

/**
 * Class that represents the domain OWL Graph Cells (Class, DatatypeProperty
 * or ObjectProeprty) on the graph gui. It has the coordinate variables.
 * 
 * @author Ozer Ozdikis
 * @version June 2006
 */
public abstract class AOWLGraphCell extends DefaultGraphCell {
	private static final long serialVersionUID = 1L;
	protected static final int CELL_WIDTH = 30;
	protected static final int CELL_HEIGHT = 20;
	protected ATransformationGraph graphPanel;
	protected ATransformationCondition transformationCondition;
	protected Category tracer = Logger.getLogger(AOWLGraphCell.class);

	/**
	 * constructor
	 */
	public AOWLGraphCell(ATransformationGraph graphPanel, int x, int y) {
		super();
		this.graphPanel = graphPanel;
		GraphConstants.setBounds(this.getAttributes(), new Rectangle2D.Double(x, y, CELL_WIDTH, CELL_HEIGHT));
		GraphConstants.setBorderColor(this.getAttributes(), Color.black);
		this.addPort();
	}

	public abstract void openPopupMenu(int x, int y);

	public abstract void saveRulesConfiguration(PrintStream printStream);

	public abstract void loadRulesConfiguration(Element owlClassGraphCellNode);

	public abstract void displayTransformationConditionPanel();

	public abstract ATransformationCondition getTransformationCondition();

	/**
	 * Class that extends the popup Menu. It displays the "Transformation Condition"
	 * menu on the popup menus.
	 */
	protected class OWLGraphCellPopupMenu extends JPopupMenu implements ActionListener {
		private static final long serialVersionUID = 1L;
		public static final String SHOW_CONDITION_TEXT = "Transformation Condition";
		private JMenuItem showConditionMenuItem;

		/**
		 * constructor
		 */
		public OWLGraphCellPopupMenu() {
			super();
			showConditionMenuItem = new JMenuItem(SHOW_CONDITION_TEXT);
			this.add(showConditionMenuItem);
			showConditionMenuItem.addActionListener(this);
		}

		/**
		 * ActionListener for the popup menu. If "Transformation Condition" menu is
		 * chosen, calls the displayTransformationConditionPanel function.
		 */
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getActionCommand().equals(SHOW_CONDITION_TEXT)) {
				tracer.debug("Transformation Condition menu clicked on OWLGraphCell");
				displayTransformationConditionPanel();
			}
		}
	}

}
