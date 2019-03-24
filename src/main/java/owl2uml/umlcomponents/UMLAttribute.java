package owl2uml.umlcomponents;

import java.io.PrintStream;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;

/**
 * XMIAttribute model that keeps the id and name an XMI Attribute.
 * 
 * @author Ozer Ozdikis
 * @version June 2006
 */
public class UMLAttribute {
	static private int counter = 1;
	private String id;
	private String name;
	private Category tracer = Logger.getLogger(UMLAttribute.class);

	/**
	 * constructor
	 */
	public UMLAttribute(String id, String name) {
		tracer.debug("Creating XMIAttribute with ID : " + id);
		this.id = id;
		this.name = name;
	}

	/**
	 * constructor
	 */
	public UMLAttribute(String name) {
		this(generateModelID(), name);
	}

	/**
	 * returns XMIAttribute+counter as ID and increments counter
	 */
	static public String generateModelID() {
		return "XMIAttribute" + counter++;
	}

	/**
	 * returns the ID of the XMIAttribute
	 */
	public String getID() {
		return id;
	}

	/**
	 * returns the name of the XMIAttribute
	 */
	public String getName() {
		return name;
	}

	/**
	 * prints this XMIAttribute to the XMI file
	 */
	public void print(PrintStream printStream) {
		printStream.println("                        <UML:Attribute xmi.id=\"" + id
				+ "\" changeability=\"changeable\" targetScope=\"instance\" ownerScope=\"instance\" visibility=\"public\" ordering=\"unordered\" name=\""
				+ name + "\" isSpecification=\"false\"/>");
	}

}
