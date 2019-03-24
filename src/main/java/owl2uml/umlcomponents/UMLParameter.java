package owl2uml.umlcomponents;

import java.io.PrintStream;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;

/**
 * XMIParameter model that keeps the id, name and kind of an XMIParameter.
 * 
 * @author Ozer Ozdikis
 * @version June 2006
 */
public class UMLParameter {
	static private int counter = 1;
	private String id;
	private String name;
	private String kind;
	private Category tracer = Logger.getLogger(UMLParameter.class);

	/**
	 * constructor
	 */
	public UMLParameter(String id, String name, String kind) {
		tracer.debug("Creating XMIParameter with ID : " + id);
		this.id = id;
		this.name = name;
		this.kind = kind;
	}

	/**
	 * constructor
	 */
	public UMLParameter(String name, String kind) {
		this(generateModelID(), name, kind);
	}

	/**
	 * returns XMIParameter+counter as ID and increments counter
	 */
	static public String generateModelID() {
		return "XMIParameter" + counter++;
	}

	/**
	 * returns the name of the XMIParameter
	 */
	public String getName() {
		return name;
	}

	/**
	 * returns the ID of the XMIParameter
	 */
	public String getID() {
		return id;
	}

	/**
	 * returns the kind of the XMIParameter
	 */
	public String getKind() {
		return kind;
	}

	/**
	 * prints this XMIParameter to the XMI file.
	 */
	public void print(PrintStream printStream) {
		printStream.println("                                <UML:Parameter xmi.id=\"" + id + "\" kind=\"" + kind
				+ "\" name=\"" + name + "\" isSpecification=\"false\"/>");
	}

}
