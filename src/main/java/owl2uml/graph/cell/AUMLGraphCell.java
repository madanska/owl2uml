package owl2uml.graph.cell;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.io.PrintStream;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.w3c.dom.Element;

import owl2uml.graph.ATransformationGraph;

/**
 * @author Ozer Ozdikis
 * @version June 2006
 */
public abstract class AUMLGraphCell extends DefaultGraphCell {
	private static final long serialVersionUID = 1L;
	protected static final int CELL_WIDTH = 30;
	protected static final int CELL_HEIGHT = 20;
	protected ATransformationGraph graphPanel;
	protected Category tracer = Logger.getLogger(AUMLGraphCell.class);

	public AUMLGraphCell(ATransformationGraph graphPanel, int x, int y) {
		this.graphPanel = graphPanel;
		GraphConstants.setBounds(this.getAttributes(), new Rectangle2D.Double(x, y, CELL_WIDTH, CELL_HEIGHT));
		GraphConstants.setBorderColor(this.getAttributes(), Color.black);
		this.addPort();
	}

	public abstract void openPopupMenu(int x, int y);

	public abstract void saveRulesConfiguration(PrintStream printStream);

	public abstract void loadRulesConfiguration(Element umlClassNode);
}
