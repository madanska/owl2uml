package owl2uml.umlcomponents;

import java.io.PrintStream;
import java.util.Vector;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;

/**
 * XMIClass model that keeps the id, name, attributes, super classes and
 * operations of an XMI Class.
 * 
 * @author Ozer Ozdikis
 * @version June 2006
 */
public class UMLClass {
	static private int counter = 1;
	private String id;
	private String name;
	private Vector<UMLAttribute> umlAttributes = new Vector<UMLAttribute>();
	private Vector<UMLGeneralization> umlGeneralizations = new Vector<UMLGeneralization>();
	private Vector<UMLOperation> umlOperations = new Vector<UMLOperation>();
	private Vector<UMLAssociation> umlAssociations = new Vector<UMLAssociation>();
	private Category tracer = Logger.getLogger(UMLClass.class);

	/**
	 * constructor
	 */
	public UMLClass(String id, String name) {
		tracer.debug("Creating umlClass with ID : " + id);
		this.id = id;
		this.name = name;
	}

	/**
	 * constructor
	 */
	public UMLClass(String name) {
		this(generateModelID(), name);
	}

	/**
	 * returns umlClass+counter as ID and increments counter
	 */
	static public String generateModelID() {
		return "UMLClass" + counter++;
	}

	/**
	 * returns the name of the umlClass
	 */
	public String getName() {
		return name;
	}

	/**
	 * returns the ID of the umlClass
	 */
	public String getID() {
		return id;
	}

	/**
	 * Adds the given umlAttribute ot the umlClass
	 */
	public void addUMLAttribute(UMLAttribute attribute) {
		tracer.debug("Adding UMLAttribute ID : " + attribute.getID() + " to umlClass ID : " + getID());
		umlAttributes.add(attribute);
	}

	/**
	 * returns the umlAttribute with the given name in this umlClass
	 */
	public UMLAttribute getUMLAttribute(String attributeName) {
		for (int i = 0; i < umlAttributes.size(); i++) {
			UMLAttribute registeredAttribute = (UMLAttribute) umlAttributes.get(i);
			if (registeredAttribute.getName().equals(attributeName))
				return registeredAttribute;
		}
		return null;
	}

	/**
	 * Adds the given umlGeneralization ot the umlClass
	 */
	public void addUMLGeneralization(UMLGeneralization generalization) {
		tracer.debug("Adding umlGeneralization ID : " + generalization.getID() + " to umlClass ID : " + getID());
		umlGeneralizations.add(generalization);
	}

	/**
	 * returns the umlGeneralization with the given superclass (parent) name in this
	 * umlClass
	 */
	public UMLGeneralization getUMLGeneralization(String parentName) {
		for (int i = 0; i < umlGeneralizations.size(); i++) {
			UMLGeneralization registeredGeneralization = (UMLGeneralization) umlGeneralizations.get(i);
			if (registeredGeneralization.getParent().equals(parentName)
					&& (registeredGeneralization.getChild().equals(name)))
				return registeredGeneralization;
		}
		return null;
	}

	/**
	 * Adds the given umlOperation ot the umlClass
	 */
	public void addUMLOperation(UMLOperation operation) {
		tracer.debug("Adding umlOperation ID : " + operation.getID() + " to umlClass ID : " + getID());
		umlOperations.add(operation);
	}

	public UMLOperation getUMLOperation(String operationName) {
		for (int i = 0; i < umlOperations.size(); i++) {
			UMLOperation registeredOperation = (UMLOperation) umlOperations.get(i);
			if (registeredOperation.getName().equals(operationName))
				return registeredOperation;
		}
		return null;
	}

	public void addUMLAssociation(UMLAssociation association) {
		tracer.debug("Adding umlAssocation to class : " + association.getAssociationEnd2());
		umlAssociations.add(association);
	}

	public UMLAssociation getUMLAssociation(String end2Name) {
		for (int i = 0; i < umlAssociations.size(); i++) {
			UMLAssociation registeredAssociation = (UMLAssociation) umlAssociations.get(i);
			if (registeredAssociation.getAssociationEnd2().equals(end2Name))
				return registeredAssociation;
		}
		return null;
	}

	/**
	 * prints this umlClass to the uml file. It delegates the printing of
	 * attributes, operations and generalizations to the corresponsing objects.
	 */
	public void print(PrintStream printStream) {
		printStream.print("                <UML:Class xmi.id=\"");
		printStream.print(id);
		printStream.print(
				"\" isActive=\"false\" isAbstract=\"false\" isLeaf=\"false\" isRoot=\"false\" visibility=\"public\" name=\"");
		printStream.print(name);
		printStream.println("\" isSpecification=\"false\">");

		if ((umlAttributes.size() > 0) || (umlOperations.size() > 0))
			printStream.println("                    <UML:Classifier.feature>");
		for (int i = 0; i < umlAttributes.size(); i++) {
			UMLAttribute attribute = (UMLAttribute) umlAttributes.get(i);
			attribute.print(printStream);
		}
		for (int i = 0; i < umlOperations.size(); i++) {
			UMLOperation operation = (UMLOperation) umlOperations.get(i);
			operation.print(printStream);
		}
		if ((umlAttributes.size() > 0) || (umlOperations.size() > 0))
			printStream.println("                    </UML:Classifier.feature>");

		if ((umlGeneralizations.size() > 0) || (umlAssociations.size() > 0))
			printStream.println("                    <UML:Namespace.ownedElement>");
		for (int i = 0; i < umlGeneralizations.size(); i++) {
			UMLGeneralization generalization = (UMLGeneralization) umlGeneralizations.get(i);
			generalization.print(printStream);
		}
		for (int i = 0; i < umlAssociations.size(); i++) {
			UMLAssociation association = (UMLAssociation) umlAssociations.get(i);
			association.print(printStream);
		}
		if ((umlGeneralizations.size() > 0) || (umlAssociations.size() > 0))
			printStream.println("                    </UML:Namespace.ownedElement>");

		printStream.println("                </UML:Class>");
	}
}
