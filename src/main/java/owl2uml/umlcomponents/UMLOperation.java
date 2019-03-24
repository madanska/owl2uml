package owl2uml.umlcomponents;

import java.io.PrintStream;
import java.util.Vector;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;

/**
 * XMIOperation model that keeps the id, name and parameters of an XMI
 * Operation.
 * 
 * @author Ozer Ozdikis
 * @version June 2006
 */
public class UMLOperation {
	static private int counter = 1;
	private String id;
	private String name;
	private Vector<UMLParameter> umlParameters = new Vector<UMLParameter>();
	private Category tracer = Logger.getLogger(UMLOperation.class);

	/**
	 * constructor
	 */
	public UMLOperation(String id, String name) {
		tracer.debug("Creating umlOperation with ID : " + id);
		this.id = id;
		this.name = name;
	}

	/**
	 * constructor
	 */
	public UMLOperation(String name) {
		this(generateModelID(), name);
	}

	/**
	 * returns umlOperation+counter as ID and increments counter
	 */
	static public String generateModelID() {
		return "UMLOperation" + counter++;
	}

	/**
	 * returns the name of the umlOperation
	 */
	public String getName() {
		return name;
	}

	/**
	 * returns the ID of the umlOperation
	 */
	public String getID() {
		return id;
	}

	/**
	 * Adds the given umlParameter to the umlOperation
	 */
	public void addUMLParameter(UMLParameter parameter) {
		tracer.debug("Adding umlParameter ID : " + parameter.getID() + " to umlOperation ID : " + getID());
		umlParameters.add(parameter);
	}

	/**
	 * returns the umlParameter with the given name in this umlOperation
	 */
	public UMLParameter getUMLParameter(String parameterName) {
		for (int i = 0; i < umlParameters.size(); i++) {
			UMLParameter registeredParameter = (UMLParameter) umlParameters.get(i);
			if (registeredParameter.getName().equals(parameterName))
				return registeredParameter;
		}
		return null;
	}

	public Vector<UMLParameter> getUMLParameters() {
		return umlParameters;
	}

	/**
	 * prints this umlOperation to the uml file. It delegates the printing of
	 * parameters to the corresponsing objects.
	 */
	public void print(PrintStream printStream) {
		printStream.println("                        <UML:Operation xmi.id=\"" + id
				+ "\" concurrency=\"sequential\" isRoot=\"false\" isLeaf=\"false\" isAbstract=\"false\" isQuery=\"false\" ownerScope=\"instance\" visibility=\"public\" specification=\"\" name=\""
				+ name + "\" isSpecification=\"false\">");
		if (umlParameters.size() > 0)
			printStream.println("                            <UML:BehavioralFeature.parameter>");
		for (int i = 0; i < umlParameters.size(); i++) {
			UMLParameter parameter = (UMLParameter) umlParameters.get(i);
			parameter.print(printStream);
		}
		if (umlParameters.size() > 0)
			printStream.println("                            </UML:BehavioralFeature.parameter>");
		printStream.println("                        </UML:Operation>");
	}

}
