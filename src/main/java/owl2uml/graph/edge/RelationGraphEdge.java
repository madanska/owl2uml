package owl2uml.graph.edge;

import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.GraphConstants;

import owl2uml.graph.cell.AOWLGraphCell;
import owl2uml.graph.cell.UMLAssociationGraphCell;
import owl2uml.graph.cell.UMLClassGraphCell;
import owl2uml.graph.cell.UMLOperationGraphCell;

/** 
* @author Ozer Ozdikis
* @version June 2006
*/
public class RelationGraphEdge extends DefaultEdge {
	private static final long serialVersionUID = 1L;
	public static String CELL_TYPE = "RelationEdge";

	/**
	 * constructor
	 */
	public RelationGraphEdge(AOWLGraphCell sourceCell, UMLClassGraphCell targetCell) {
		super();
		this.setSource(sourceCell.getChildAt(0));
		this.setTarget(targetCell.getChildAt(0));
		GraphConstants.setLineEnd(this.getAttributes(), GraphConstants.ARROW_CLASSIC);
		GraphConstants.setEndFill(this.getAttributes(), true);
	}

	/**
	 * constructor
	 */
	public RelationGraphEdge(AOWLGraphCell sourceCell, UMLAssociationGraphCell targetCell) {
		super();
		this.setSource(sourceCell.getChildAt(0));
		this.setTarget(targetCell.getChildAt(0));
		GraphConstants.setLineEnd(this.getAttributes(), GraphConstants.ARROW_CLASSIC);
		GraphConstants.setEndFill(this.getAttributes(), true);
	}

	public RelationGraphEdge(AOWLGraphCell sourceCell, UMLOperationGraphCell targetCell) {
		super();
		this.setSource(sourceCell.getChildAt(0));
		this.setTarget(targetCell.getChildAt(0));
		GraphConstants.setLineEnd(this.getAttributes(), GraphConstants.ARROW_CLASSIC);
		GraphConstants.setEndFill(this.getAttributes(), true);
	}
}
