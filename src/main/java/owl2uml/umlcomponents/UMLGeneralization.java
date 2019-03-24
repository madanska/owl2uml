package owl2uml.umlcomponents;

import java.io.PrintStream;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;

/**
 * XMIGeneralization model that keeps the id, name, parent(superclass) and
 * child(subclass) classes that represents an XMIGeneralization.
 * 
 * @author Ozer Ozdikis
 * @version June 2006
 */
public class UMLGeneralization {
	static private int counter = 1;
	private String id;
	private String name;
	private String parent;
	private String child;
	private Category tracer = Logger.getLogger(UMLGeneralization.class);

	/**
	 * constructor
	 */
	public UMLGeneralization(String id, String name, String child, String parent) {
		tracer.debug("Creating XMIGeneralization with ID : " + id);
		this.id = id;
		this.name = name;
		this.parent = parent;
		this.child = child;
	}

	/**
	 * constructor
	 */
	public UMLGeneralization(String name, String child, String parent) {
		this(generateModelID(), name, child, parent);
	}

	/**
	 * returns XMIGeneralization+counter as ID and increments counter
	 */
	static public String generateModelID() {
		return "XMIGeneralization" + counter++;
	}

	/**
	 * returns the name of the XMIGeneralization
	 */
	public String getName() {
		return name;
	}

	/**
	 * returns the ID of the XMIGeneralization
	 */
	public String getID() {
		return id;
	}

	/**
	 * returns the name of the parent class in this XMIGeneralization
	 */
	public String getParent() {
		return parent;
	}

	/**
	 * returns the name of the child class in this XMIGeneralization
	 */
	public String getChild() {
		return child;
	}

	/**
	 * prints this XMIGeneralization to the XMI file.
	 */
	public void print(PrintStream printStream) {
		printStream.println("                        <UML:Generalization xmi.id=\"" + id + "\" child=\"" + child
				+ "\" parent=\"" + parent + "\" discriminator=\"\" visibility=\"public\" name=\"" + name
				+ "\" isSpecification=\"false\"/>");
	}
}
