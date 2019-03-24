package owl2uml.graph;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.PrintStream;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;
import org.jgraph.JGraph;
import org.jgraph.graph.GraphModel;
import org.w3c.dom.Element;

import owl2uml.graph.cell.AOWLGraphCell;
import owl2uml.graph.cell.UMLAssociationGraphCell;
import owl2uml.graph.cell.UMLClassGraphCell;
import owl2uml.graph.cell.UMLOperationGraphCell;
import owl2uml.graph.edge.RelationGraphEdge;

/**
 * JGraph subclass for the mapping design. It is an abstract class for the
 * OWLClassTransformationGraph, OWLDatatypePropertyTransformationGraph and
 * OWLObjectPropertyTransformationGraph. It includes always one owlGraphCell.
 * This owlGraphCell can be an instance of OWLClassGraphCell,
 * OWLDatatypePropertyGraphCell or OWLObjectPropertyGraphCell, depending on the
 * implementing class. Several umlClassGraphCells and umlAssociationGraphCells
 * can be added/deleted to/from the graph. As the new UML graph objects are
 * added, edges between these UML objects and OWL object are managed. This graph
 * is part of a tab, it has a tabCounter and a title. Right mouse button
 * behavior, save/load configurations to/from file and transformation processes
 * are in subclasses.
 * 
 * @author Ozer Ozdikis
 * @version June 2006
 */
public abstract class ATransformationGraph extends JGraph implements MouseListener {
	private static final long serialVersionUID = 1L;
	private static int tabCounter = 0;
	protected Vector<UMLClassGraphCell> umlClassCellsVector = new Vector<UMLClassGraphCell>();
	protected Vector<UMLAssociationGraphCell> umlAssociationCellsVector = new Vector<UMLAssociationGraphCell>();
	protected Vector<UMLOperationGraphCell> umlOperationCellsVector = new Vector<UMLOperationGraphCell>();
	protected Vector<RelationGraphEdge> relationEdgesVector = new Vector<RelationGraphEdge>();
	protected String tabTitle;
	protected JFrame frame;
	protected Category tracer = Logger.getLogger(ATransformationGraph.class);
	protected AOWLGraphCell owlGraphCell;

	/**
	 * Constructor
	 */
	public ATransformationGraph(GraphModel model, String tabTitle, JFrame frame) {
		super(model);
		this.tabTitle = tabTitle;
		this.frame = frame;
		addMouseListener(this);
	}

	/**
	 * Returns the tab counter to be used in the next tab title
	 */
	static public int getTabCounter() {
		return ATransformationGraph.tabCounter;
	}

	/**
	 * Sets the tab counter to be used in the next tab title
	 */
	static public void setTabCounter(int tabCounter) {
		ATransformationGraph.tabCounter = tabCounter;
	}

	/**
	 * Returns the transformation window frame that includes the panel.
	 */
	public JFrame getFrame() {
		return frame;
	}

	public Vector<UMLClassGraphCell> getUMLClassCellsVector() {
		return umlClassCellsVector;
	}

	public Vector<UMLAssociationGraphCell> getUMLAssociationCellsVector() {
		return umlAssociationCellsVector;
	}

	public Vector<UMLOperationGraphCell> getUMLOperationCellsVector() {
		return umlOperationCellsVector;
	}

	/**
	 * Removes the given umlClassCell object from the graph. Also removes the
	 * attached edge.
	 */
	public void removeUMLClassCell(UMLClassGraphCell umlClassCell) {
		tracer.debug("Removing the umlClassGraphCell");
		for (int i = 0; i < umlClassCellsVector.size(); i++) {
			UMLClassGraphCell registeredUMLClassCell = umlClassCellsVector.get(i);
			if (umlClassCell == registeredUMLClassCell) {
				for (int j = 0; j < relationEdgesVector.size(); j++) {
					RelationGraphEdge relationEdge = relationEdgesVector.get(j);
					if (relationEdge.getTarget() == registeredUMLClassCell.getChildAt(0)) {
						tracer.debug("Removing the relation edge bound to umlGraphClassCell");
						relationEdgesVector.remove(j);
						Object[] toRemove = { relationEdge };
						getGraphLayoutCache().remove(toRemove);
					}
				}
				Object[] toRemove = { umlClassCell };
				getGraphLayoutCache().remove(toRemove);
				umlClassCellsVector.remove(i);
				break;
			}
		}
	}

	/**
	 * Removes the given umlAssociationCell object from the graph. Also removes the
	 * attached edge.
	 */
	public void removeUMLAssociationCell(UMLAssociationGraphCell umlAssociationCell) {
		tracer.debug("Removing the umlAssociationGraphCell");
		for (int i = 0; i < umlAssociationCellsVector.size(); i++) {
			UMLAssociationGraphCell registeredUMLAssociationCell = umlAssociationCellsVector.get(i);
			if (umlAssociationCell == registeredUMLAssociationCell) {
				for (int j = 0; j < relationEdgesVector.size(); j++) {
					RelationGraphEdge relationEdge = relationEdgesVector.get(j);
					if (relationEdge.getTarget() == registeredUMLAssociationCell.getChildAt(0)) {
						tracer.debug("Removing the relation edge bound to umlAssociationGraphCell");
						relationEdgesVector.remove(j);
						Object[] toRemove = { relationEdge };
						getGraphLayoutCache().remove(toRemove);
					}
				}
				Object[] toRemove = { umlAssociationCell };
				getGraphLayoutCache().remove(toRemove);
				umlAssociationCellsVector.remove(i);
				break;
			}
		}
	}

	/**
	 * Removes the given umlOperationCell object from the graph. Also removes the
	 * attached edge.
	 */
	public void removeUMLOperationCell(UMLOperationGraphCell umlOperationCell) {
		tracer.debug("Removing the umlOperationGraphCell");
		for (int i = 0; i < umlOperationCellsVector.size(); i++) {
			UMLOperationGraphCell registeredUMLOperationCell = umlOperationCellsVector.get(i);
			if (umlOperationCell == registeredUMLOperationCell) {
				for (int j = 0; j < relationEdgesVector.size(); j++) {
					RelationGraphEdge relationEdge = relationEdgesVector.get(j);
					if (relationEdge.getTarget() == registeredUMLOperationCell.getChildAt(0)) {
						tracer.debug("Removing the relation edge bound to umlOperationGraphCell");
						relationEdgesVector.remove(j);
						Object[] toRemove = { relationEdge };
						getGraphLayoutCache().remove(toRemove);
					}
				}
				Object[] toRemove = { umlOperationCell };
				getGraphLayoutCache().remove(toRemove);
				umlOperationCellsVector.remove(i);
				break;
			}
		}
	}

	/**
	 * Returns the title of the tab that encapsulates this Transformation Graph
	 */
	public String getTabTitle() {
		return tabTitle;
	}

	/**
	 * Forwards the mouse click actions to the correct functions
	 */
	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getButton() == MouseEvent.BUTTON1) {
			if (arg0.getClickCount() == 1) {
				leftButtonSingleClicked(arg0);
			} else if (arg0.getClickCount() == 2) {
				leftButtonDoubleClicked(arg0);
			}
		} else if (arg0.getButton() == MouseEvent.BUTTON3) {
			if (arg0.getClickCount() == 1) {
				rightButtonSingleClicked(arg0);
			} else if (arg0.getClickCount() == 2) {
				rightButtonDoubleClicked(arg0);
			}
		}
	}

	public void mousePressed(MouseEvent arg0) {
	}

	public void mouseReleased(MouseEvent arg0) {
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	protected void leftButtonSingleClicked(MouseEvent arg0) {
	}

	protected void leftButtonDoubleClicked(MouseEvent arg0) {
	}

	protected void rightButtonDoubleClicked(MouseEvent arg0) {
	}

	protected abstract void rightButtonSingleClicked(MouseEvent arg0);

	public abstract void saveRulesConfiguration(PrintStream printStream);

	public abstract void loadRulesConfiguration(Element tabNode);

	public abstract AOWLGraphCell getOWLGraphCell();

	/**
	 * Class that extends the popup Menu. It displays the "Add UMLClass" and "Add
	 * UMLAssociation" menus on the popup menus.
	 */
	protected class EmptyAreaPopupMenu extends JPopupMenu implements ActionListener {
		private static final long serialVersionUID = 1L;
		public static final String ADD_UMLCLASS_TEXT = "Add UMLClass";
		public static final String ADD_UMLASSOCIATION_TEXT = "Add UMLAssociation";
		public static final String ADD_UMLOPERATION_TEXT = "Add UMLOperationParameter";

		private ATransformationGraph graphPanel;
		private int xCoordinate;
		private int yCoordinate;
		private JMenuItem addUMLClassMenuItem;
		private JMenuItem addUMLAssociationMenuItem;
		private JMenuItem addUMLOperationMenuItem;

		/**
		 * constructor
		 */
		public EmptyAreaPopupMenu(ATransformationGraph graphPanel, int xCoordinate, int yCoordinate) {
			super();
			this.graphPanel = graphPanel;
			this.xCoordinate = xCoordinate;
			this.yCoordinate = yCoordinate;
			addUMLClassMenuItem = new JMenuItem(ADD_UMLCLASS_TEXT);
			addUMLAssociationMenuItem = new JMenuItem(ADD_UMLASSOCIATION_TEXT);
			addUMLOperationMenuItem = new JMenuItem(ADD_UMLOPERATION_TEXT);
			this.add(addUMLClassMenuItem);
			this.add(addUMLAssociationMenuItem);
			this.add(addUMLOperationMenuItem);
			addUMLClassMenuItem.addActionListener(this);
			addUMLAssociationMenuItem.addActionListener(this);
			addUMLOperationMenuItem.addActionListener(this);
		}

		/**
		 * ActionListener for the popup menu. If "Add UMLClass" menu is chosen, creates
		 * an instance of UMLClassGraphCell, adds the edge between this
		 * UMLClassGraphCell and the OWLGraphCell on the graph. Makes the same for "Add
		 * XMIAssociation" menu.
		 */
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getActionCommand().equals(ADD_UMLCLASS_TEXT)) {
				tracer.debug("AddUMLClass item of popup menu is clicked");
				UMLClassGraphCell umlClassCell = new UMLClassGraphCell(graphPanel, xCoordinate, yCoordinate);
				umlClassCellsVector.add(umlClassCell);
				RelationGraphEdge relationEdge = new RelationGraphEdge(owlGraphCell, umlClassCell);
				relationEdgesVector.add(relationEdge);
				getGraphLayoutCache().insert(umlClassCell);
				getGraphLayoutCache().insert(relationEdge);
			} else if (arg0.getActionCommand().equals(ADD_UMLASSOCIATION_TEXT)) {
				tracer.debug("AddUMLAssociation item of popup menu is clicked");
				UMLAssociationGraphCell umlAssociationCell = new UMLAssociationGraphCell(graphPanel, xCoordinate,
						yCoordinate);
				umlAssociationCellsVector.add(umlAssociationCell);
				RelationGraphEdge relationEdge = new RelationGraphEdge(owlGraphCell, umlAssociationCell);
				relationEdgesVector.add(relationEdge);
				getGraphLayoutCache().insert(umlAssociationCell);
				getGraphLayoutCache().insert(relationEdge);
			} else if (arg0.getActionCommand().equals(ADD_UMLOPERATION_TEXT)) {
				tracer.debug("AddUMLOperation item of popup menu is clicked");
				UMLOperationGraphCell umlOperationCell = new UMLOperationGraphCell(graphPanel, xCoordinate,
						yCoordinate);
				umlOperationCellsVector.add(umlOperationCell);
				RelationGraphEdge relationEdge = new RelationGraphEdge(owlGraphCell, umlOperationCell);
				relationEdgesVector.add(relationEdge);
				getGraphLayoutCache().insert(umlOperationCell);
				getGraphLayoutCache().insert(relationEdge);
			}
		}
	}

}
