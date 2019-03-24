package owl2uml.umlcomponents;

import java.io.PrintStream;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;

import owl2uml.GlobalVariables;

/**
 * XMIAttribute model that keeps the id and name an XMI Attribute.
 * 
 * @author Ozer Ozdikis
 * @version June 2006
 */
public class UMLAssociation {
	static private int counter = 1;
	private String id;
	private String associationEnd1;
	private String associationEnd2;
	private String associationType;
	private boolean multiplicityIncluded;
	private String multiplicityEnd;
	private String multiplicityLowerEnd;
	private String multiplicityUpperEnd;

	private Category tracer = Logger.getLogger(UMLAssociation.class);

	/**
	 * constructor
	 */
	public UMLAssociation(String id) {
		tracer.debug("Creating XMIAssociation with ID : " + id);
		this.id = id;
	}

	/**
	 * constructor
	 */
	public UMLAssociation() {
		this(generateModelID());
	}

	/**
	 * returns XMIAttribute+counter as ID and increments counter
	 */
	static public String generateModelID() {
		return "XMIAssociation" + counter++;
	}

	public String getAssociationEnd1() {
		return associationEnd1;
	}

	public void setAssociationEnd1(String associationEnd1) {
		this.associationEnd1 = associationEnd1;
	}

	public String getAssociationEnd2() {
		return associationEnd2;
	}

	public void setAssociationEnd2(String associationEnd2) {
		this.associationEnd2 = associationEnd2;
	}

	public String getAssociationType() {
		return associationType;
	}

	public void setAssociationType(String associationType) {
		this.associationType = associationType;
	}

	public String getMultiplicityEnd() {
		return multiplicityEnd;
	}

	public void setMultiplicityEnd(String multiplicityEnd) {
		this.multiplicityEnd = multiplicityEnd;
	}

	public String getMultiplicityLowerEnd() {
		return multiplicityLowerEnd;
	}

	public void setMultiplicityLowerEnd(String multiplicityLowerEnd) {
		this.multiplicityLowerEnd = multiplicityLowerEnd;
	}

	public String getMultiplicityUpperEnd() {
		return multiplicityUpperEnd;
	}

	public void setMultiplicityUpperEnd(String multiplicityUpperEnd) {
		this.multiplicityUpperEnd = multiplicityUpperEnd;
	}

	public void setMultiplicityIncluded(boolean multiplicityIncluded) {
		this.multiplicityIncluded = multiplicityIncluded;
	}

	public boolean isMultiplicityIncluded() {
		return multiplicityIncluded;
	}

	/**
	 * prints this XMIAttribute to the XMI file
	 */
	public void print(PrintStream printStream) {
		printStream.println(
				"                        <UML:Association xmi.id=\"" + id + "\" name=\"\" isSpecification=\"false\">");
		printStream.println("                            <UML:Association.connection>");
		printStream.println("                            	<UML:AssociationEnd xmi.id=\"" + id
				+ "-1\" aggregation=\"none\" isNavigable=\"false\" participant=\"" + associationEnd1
				+ "\" changeability=\"changeable\" targetScope=\"instance\" visibility=\"private\" ordering=\"unordered\" name=\"\" isSpecification=\"false\">");
		if (multiplicityIncluded && multiplicityEnd.equals(GlobalVariables.UML_ASSOCIATION_MULTIPLICITY_END1)) {
			printStream.println("                                    <UML:AssociationEnd.multiplicity>");
			printStream.println("                                        <UML:Multiplicity>");
			printStream.println("                                            <UML:Multiplicity.range>");
			printStream.println("                                                <UML:MultiplicityRange lower=\""
					+ multiplicityLowerEnd + "\" upper=\"" + multiplicityUpperEnd + "\"/>");
			printStream.println("                                            </UML:Multiplicity.range>");
			printStream.println("                                        </UML:Multiplicity>");
			printStream.println("                                    </UML:AssociationEnd.multiplicity>");
		}
		printStream.println("                            	</UML:AssociationEnd>");
		printStream.println("                            	<UML:AssociationEnd xmi.id=\"" + id + "-2\" aggregation=\""
				+ associationType + "\" isNavigable=\"true\" participant=\"" + associationEnd2
				+ "\" changeability=\"changeable\" targetScope=\"instance\" visibility=\"private\" ordering=\"unordered\" name=\"\" isSpecification=\"false\">");
		if (multiplicityIncluded && multiplicityEnd.equals(GlobalVariables.UML_ASSOCIATION_MULTIPLICITY_END2)) {
			printStream.println("                                    <UML:AssociationEnd.multiplicity>");
			printStream.println("                                        <UML:Multiplicity>");
			printStream.println("                                            <UML:Multiplicity.range>");
			printStream.println("                                                <UML:MultiplicityRange lower=\""
					+ multiplicityLowerEnd + "\" upper=\"" + multiplicityUpperEnd + "\"/>");
			printStream.println("                                            </UML:Multiplicity.range>");
			printStream.println("                                        </UML:Multiplicity>");
			printStream.println("                                    </UML:AssociationEnd.multiplicity>");
		}
		printStream.println("                            	</UML:AssociationEnd>");
		printStream.println("                            </UML:Association.connection>");
		printStream.println("                        </UML:Association>");
	}

}
