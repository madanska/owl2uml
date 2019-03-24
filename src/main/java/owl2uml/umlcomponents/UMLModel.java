package owl2uml.umlcomponents;

import java.io.PrintStream;
import java.util.Vector;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;

/**
 * XMIModel model that keeps the id, name and classes in this XMIModel.
 * 
 * @author Ozer Ozdikis
 * @version June 2006
 */
public class UMLModel {
	static private int counter = 1;
	private String id;
	private String name;
	private String packageName;
	private Vector<UMLClass> umlClasses = new Vector<UMLClass>();
	private Category tracer = Logger.getLogger(UMLModel.class);

	/**
	 * constructor
	 */
	public UMLModel(String id, String packageName, String name) {
		tracer.debug("Creating UMLModel with ID : " + id);
		this.id = id;
		this.name = name;
		this.packageName = packageName;
	}

	/**
	 * returns XMIModel+counter as ID and increments counter
	 */
	static public String generateModelID() {
		return "UMLModel" + counter++;
	}

	/**
	 * returns the ID of the XMIModel
	 */
	public String getID() {
		return id;
	}

	/**
	 * Adds the given XMIClass to this XMIModel
	 */
	public void addUMLClass(UMLClass umlClass) {
		tracer.debug("Adding XMIClass ID : " + umlClass.getID() + " to XMIModel ID : " + getID());
		umlClasses.add(umlClass);
	}

	/**
	 * returns the XMIClass with the given name in this XMIModel
	 */
	public UMLClass getUMLClass(String className) {
		for (int i = 0; i < umlClasses.size(); i++) {
			UMLClass registeredClass = (UMLClass) umlClasses.get(i);
			if (registeredClass.getName().equals(className))
				return registeredClass;
		}
		return null;
	}

	public Vector<UMLClass> getUMLClasses() {
		return umlClasses;
	}

	/**
	 * prints this XMIModel to the XMI file. It delegates the printing of classes to
	 * the corresponsing objects.
	 */
	public void print(PrintStream printStream) {
		printStream.println(
				"<XMI xmi.version=\"1.1\" xmlns:UML=\"http://www.omg.org/UML\" xmlns:xlink=\"http://www.w3c.org/xlink\" xmlns:XDE=\"http://www.rational.com/products/XDE\">");
		printStream.println("    <XMI.content>");
		printStream
				.println("        <UML:Model xmi.id=\"" + id + "\" name=\"" + name + "\" isSpecification=\"false\">");
		printStream.println("           <UML:Namespace.ownedElement>");
		printStream.println(
				"			<UML:Package xmi.id=\"package1\" name=\"" + packageName + "\" isSpecification=\"false\">");
		if (umlClasses.size() > 0)
			printStream.println("           <UML:Namespace.ownedElement>");
		for (int i = 0; i < umlClasses.size(); i++) {
			UMLClass xmiClass = (UMLClass) umlClasses.get(i);
			xmiClass.print(printStream);
		}
		if (umlClasses.size() > 0)
			printStream.println("           </UML:Namespace.ownedElement>");
		printStream.println("           </UML:Package>");
		printStream.println("           </UML:Namespace.ownedElement>");
		printStream.println("        </UML:Model>");
		printStream.println("    </XMI.content>");
		printStream.println("</XMI>");
	}
}
