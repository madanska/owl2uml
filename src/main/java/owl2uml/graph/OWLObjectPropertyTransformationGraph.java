package owl2uml.graph;

import java.awt.event.MouseEvent;
import java.io.PrintStream;
import java.util.Vector;

import javax.swing.JFrame;

import org.jgraph.graph.GraphModel;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import owl2uml.graph.cell.OWLClassGraphCell;
import owl2uml.graph.cell.OWLObjectPropertyGraphCell;
import owl2uml.graph.cell.UMLAssociationGraphCell;
import owl2uml.graph.cell.UMLClassGraphCell;
import owl2uml.graph.cell.UMLOperationGraphCell;
import owl2uml.graph.edge.RelationGraphEdge;

/**
 * Extension of the TransformationGraph, specialized for owlObjectPropertyGraphCell.
 * It includes always one owlObjectPropertyGraphCell.
 * Several umlClassGraphCells and umlAssociationGraphCells can be added/deleted to/from the graph.
 * As the new UML graph objects are added, edges between these UML objects and OWL object are managed.
 * This graph is part of a tab, it has a tabCounter and a title.        
 * 
 * @author Ozer Ozdikis
 * @version June 2006
 */
public class OWLObjectPropertyTransformationGraph extends ATransformationGraph {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor 
	 */
	public OWLObjectPropertyTransformationGraph(GraphModel model, String tabTitle, JFrame frame) {
		super(model, tabTitle, frame);
		owlGraphCell = new OWLObjectPropertyGraphCell(this,100,100);
		getGraphLayoutCache().insert(owlGraphCell);
	}

	/**
	 * Saves the transformation configuration. After adding <owlObjectPropertyTab> tag, adds the <tabTitle> and  
	 * delegates the saving to OWLObjectPropertyGraphCell, UMLClassGraphCells and UMLAssociationGraphCells.  
	 */
	public void saveRulesConfiguration(PrintStream printStream) {
		tracer.debug("Saving owlObjectPropertyGraphCell transformation configuration");
		printStream.println("	<owlObjectPropertyTab>");
		printStream.println("		<title>" + tabTitle + "</title>");
		owlGraphCell.saveRulesConfiguration(printStream);
		for(int i=0; i<umlClassCellsVector.size(); i++) {
			tracer.debug("Saving " + i + "th umlClassGraphCell in the file");
			UMLClassGraphCell umlClassGraphCell = (UMLClassGraphCell) umlClassCellsVector.get(i);
			umlClassGraphCell.saveRulesConfiguration(printStream);
		}
		for(int i=0; i<umlAssociationCellsVector.size(); i++) {
			tracer.debug("Saving " + i + "th umlAssociationGraphCell in the file");
			UMLAssociationGraphCell umlAssociationGraphCell = (UMLAssociationGraphCell) umlAssociationCellsVector.get(i);
			umlAssociationGraphCell.saveRulesConfiguration(printStream);
		}
		for(int i=0; i<umlOperationCellsVector.size(); i++) {
			tracer.debug("Saving " + i + "th umlOperationGraphCell in the file");
			UMLOperationGraphCell umlOperationGraphCell = (UMLOperationGraphCell) umlOperationCellsVector.get(i);
			umlOperationGraphCell.saveRulesConfiguration(printStream);
		}
		printStream.println("	</owlObjectPropertyTab>");
	}
	
	/**
	 * Loads the transformation configuration. Reads the <OWLObjectPropertyGraphCell>, <UMLClass> and <UMLAssociation> tags 
	 * and delegates loading to them.  
	 */
	public void loadRulesConfiguration(Element tabNode) {
		tracer.debug("Loading owlObjectPropertyGraphCell transformation configuration");
		NodeList owlObjectPropertyNodes = tabNode.getElementsByTagName("OWLObjectProperty");
		Element owlObjectPropertyNode = (Element) owlObjectPropertyNodes.item(0);
		owlGraphCell.loadRulesConfiguration(owlObjectPropertyNode);
		NodeList umlClassNodes = tabNode.getElementsByTagName("UMLClass");
		umlClassCellsVector = new Vector<UMLClassGraphCell>();
		for(int i=0; i<umlClassNodes.getLength(); i++) {
			tracer.debug(i + "th umlClassGraphCell found in file, creating instance");
			Element umlClassNode = (Element) umlClassNodes.item(i);
			UMLClassGraphCell umlClassCell = new UMLClassGraphCell(this, 0, 0);
			umlClassCell.loadRulesConfiguration(umlClassNode);
			umlClassCellsVector.add(umlClassCell);
			RelationGraphEdge relationEdge = new RelationGraphEdge(owlGraphCell, umlClassCell);
			relationEdgesVector.add(relationEdge);
			getGraphLayoutCache().insert(umlClassCell);
			getGraphLayoutCache().insert(relationEdge);
		}
		NodeList umlAssociationNodes = tabNode.getElementsByTagName("UMLAssociation");
		umlAssociationCellsVector = new Vector<UMLAssociationGraphCell>();
		for(int i=0; i<umlAssociationNodes.getLength(); i++) {
			tracer.debug(i + "th umlAssociationGraphCell found in file, creating instance");
			Element umlAssociationNode = (Element) umlAssociationNodes.item(i);
			UMLAssociationGraphCell umlAssociationCell = new UMLAssociationGraphCell(this, 0, 0);
			umlAssociationCell.loadRulesConfiguration(umlAssociationNode);
			umlAssociationCellsVector.add(umlAssociationCell);
			RelationGraphEdge relationEdge = new RelationGraphEdge(owlGraphCell, umlAssociationCell);
			relationEdgesVector.add(relationEdge);
			getGraphLayoutCache().insert(umlAssociationCell);
			getGraphLayoutCache().insert(relationEdge);
		}
		NodeList umlOperationNodes = tabNode.getElementsByTagName("UMLOperation");
		umlOperationCellsVector = new Vector<UMLOperationGraphCell>();
		for(int i=0; i<umlOperationNodes.getLength(); i++) {
			tracer.debug(i + "th umlOperationGraphCell found in file, creating instance");
			Element umlOperationNode = (Element) umlOperationNodes.item(i);
			UMLOperationGraphCell umlOperationCell = new UMLOperationGraphCell(this, 0, 0);
			umlOperationCell.loadRulesConfiguration(umlOperationNode);
			umlOperationCellsVector.add(umlOperationCell);
			RelationGraphEdge relationEdge = new RelationGraphEdge(owlGraphCell, umlOperationCell);
			relationEdgesVector.add(relationEdge);
			getGraphLayoutCache().insert(umlOperationCell);
			getGraphLayoutCache().insert(relationEdge);
		}
	}

	/**
	 * If mouse is right clicked on the OWLObjectPropertyGraphCell, UMLClassGraphCell or UMLAssociationGraphCell on the graph, 
	 * opens the corresponding popup menu
	 */
	protected void rightButtonSingleClicked(MouseEvent arg0) {
		Object selectedCell = getFirstCellForLocation(arg0.getX(), arg0.getY());
		if(selectedCell==null) {
			tracer.debug("rightButtonSingleClicked on nothing");
			EmptyAreaPopupMenu popupMenu = new EmptyAreaPopupMenu(this, arg0.getX(), arg0.getY());
			popupMenu.show(this, arg0.getX(), arg0.getY());
		} else if(selectedCell.getClass().getName().equals(OWLClassGraphCell.class.getName())) {
			tracer.debug("rightButtonSingleClicked on UML Class");
			UMLClassGraphCell umlClassCell = (UMLClassGraphCell) getFirstCellForLocation(arg0.getX(), arg0.getY());
			umlClassCell.openPopupMenu(arg0.getX(), arg0.getY());
		} else if(selectedCell.getClass().getName().equals(OWLObjectPropertyGraphCell.class.getName())) {
			tracer.debug("rightButtonSingleClicked on OWL ObjectProperty");
			OWLObjectPropertyGraphCell owlObjectPropertyCell= (OWLObjectPropertyGraphCell) getFirstCellForLocation(arg0.getX(), arg0.getY());
			owlObjectPropertyCell.openPopupMenu(arg0.getX(), arg0.getY());
		} else if(selectedCell.getClass().getName().equals(UMLAssociationGraphCell.class.getName())) {
			tracer.debug("rightButtonSingleClicked on UML Association");
			UMLAssociationGraphCell umlAssociationCell = (UMLAssociationGraphCell) getFirstCellForLocation(arg0.getX(), arg0.getY());
			umlAssociationCell.openPopupMenu(arg0.getX(), arg0.getY());
		} else if(selectedCell.getClass().getName().equals(UMLOperationGraphCell.class.getName())) {
			tracer.debug("rightButtonSingleClicked on UML Operation");
			UMLOperationGraphCell umlOperationCell = (UMLOperationGraphCell) getFirstCellForLocation(arg0.getX(), arg0.getY());
			umlOperationCell.openPopupMenu(arg0.getX(), arg0.getY());
		} else if(selectedCell.getClass().getName().equals(RelationGraphEdge.class.getName())) {
			tracer.debug("rightButtonSingleClicked on Edge");
		}
	}

	public OWLObjectPropertyGraphCell getOWLGraphCell() {
		return (OWLObjectPropertyGraphCell) owlGraphCell;
	}

}
