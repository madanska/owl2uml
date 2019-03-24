package owl2uml.transformation.mapping;

import java.io.PrintStream;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import owl2uml.GlobalVariables;

/**
 * 
 * @author Ozer Ozdikis
 * @version June 2006
 */
public class OWLClassUMLAssociationTransformationMapping {
	private String umlVariable = "";
	private boolean multiplicityIncluded = false;
	private String multiplicityType = "";
	private String multiplicityEnd = "";
	private String owlVariable1 = "";
	private String owlVariable2 = "";
	private String owlVariable3 = "";
	private String ifCondition1 = "";
	private String ifConditionValue1 = "";
	private String ifCondition2 = "";
	private String ifConditionValue2 = "";
	private Category tracer = Logger.getLogger(OWLClassUMLAssociationTransformationMapping.class);

	public OWLClassUMLAssociationTransformationMapping() {
		setUmlVariable(GlobalVariables.UML_EMPTY);
		setOwlVariable1(GlobalVariables.OWL_SUPERCLASS_NAME);
		setIfCondition1(GlobalVariables.IF_NO_CONDITION);
		setIfCondition2(GlobalVariables.IF_NO_CONDITION);
		setIfConditionValue1("");
		setIfConditionValue2("");
	}

	public String getIfCondition1() {
		return ifCondition1;
	}

	public void setIfCondition1(String ifCondition1) {
		this.ifCondition1 = ifCondition1;
	}

	public String getIfCondition2() {
		return ifCondition2;
	}

	public void setIfCondition2(String ifCondition2) {
		this.ifCondition2 = ifCondition2;
	}

	public String getIfConditionValue1() {
		return ifConditionValue1;
	}

	public void setIfConditionValue1(String ifConditionValue1) {
		this.ifConditionValue1 = ifConditionValue1;
	}

	public String getIfConditionValue2() {
		return ifConditionValue2;
	}

	public void setIfConditionValue2(String ifConditionValue2) {
		this.ifConditionValue2 = ifConditionValue2;
	}

	public String getMultiplicityEnd() {
		return multiplicityEnd;
	}

	public void setMultiplicityEnd(String multiplicityEnd) {
		this.multiplicityEnd = multiplicityEnd;
	}

	public boolean isMultiplicityIncluded() {
		return multiplicityIncluded;
	}

	public void setMultiplicityIncluded(boolean multiplicityIncluded) {
		this.multiplicityIncluded = multiplicityIncluded;
	}

	public String getMultiplicityType() {
		return multiplicityType;
	}

	public void setMultiplicityType(String multiplicityType) {
		this.multiplicityType = multiplicityType;
	}

	public String getOwlVariable1() {
		return owlVariable1;
	}

	public void setOwlVariable1(String owlVariable1) {
		this.owlVariable1 = owlVariable1;
	}

	public String getOwlVariable2() {
		return owlVariable2;
	}

	public void setOwlVariable2(String owlVariable2) {
		this.owlVariable2 = owlVariable2;
	}

	public String getOwlVariable3() {
		return owlVariable3;
	}

	public void setOwlVariable3(String owlVariable3) {
		this.owlVariable3 = owlVariable3;
	}

	public String getUmlVariable() {
		return umlVariable;
	}

	public void setUmlVariable(String umlVariable) {
		this.umlVariable = umlVariable;
	}

	public void saveMappingConfiguration(PrintStream printStream) {
		tracer.debug("Printing the association transformation configuration to the file");
		printStream.println("				<umlvariable>" + umlVariable + "</umlvariable>");
		printStream.println("				<multiplicityIncluded>" + multiplicityIncluded + "</multiplicityIncluded>");
		printStream.println("				<multiplicityType>" + multiplicityType + "</multiplicityType>");
		printStream.println("				<multiplicityEnd>" + multiplicityEnd + "</multiplicityEnd>");
		printStream.println("				<owlvariable1>" + owlVariable1 + "</owlvariable1>");
		printStream.println("				<owlvariable2>" + owlVariable2 + "</owlvariable2>");
		printStream.println("				<owlvariable3>" + owlVariable3 + "</owlvariable3>");
		printStream.println("				<ifcondition1>" + ifCondition1 + "</ifcondition1>");
		printStream.println("				<ifconditionValue1>" + ifConditionValue1 + "</ifconditionValue1>");
		printStream.println("				<ifcondition2>" + ifCondition2 + "</ifcondition2>");
		printStream.println("				<ifconditionValue2>" + ifConditionValue2 + "</ifconditionValue2>");
	}

	public void loadMappingConfiguration(NodeList nodesOfSingleRule) {
		for (int j = 0; j < nodesOfSingleRule.getLength(); j++) {
			Node nodeOfSingleRule = nodesOfSingleRule.item(j);
			if (nodeOfSingleRule.getNodeType() != Node.ELEMENT_NODE)
				continue;
			Element elementOfSingleRule = (Element) nodeOfSingleRule;
			if (elementOfSingleRule.getFirstChild() == null)
				continue;
			String valueOfElement = elementOfSingleRule.getFirstChild().getTextContent();
			if (elementOfSingleRule.getTagName().equals("umlvariable")) {
				setUmlVariable(valueOfElement);
				tracer.debug("Setting umlVariable to : " + valueOfElement);
			} else if (elementOfSingleRule.getTagName().equals("multiplicityIncluded")) {
				boolean multiplicityIncluded = Boolean.parseBoolean(valueOfElement);
				setMultiplicityIncluded(multiplicityIncluded);
				tracer.debug("Setting multiplicityIncluded to : " + valueOfElement);
			} else if (elementOfSingleRule.getTagName().equals("multiplicityType")) {
				setMultiplicityType(valueOfElement);
				tracer.debug("Setting multiplicityType to : " + valueOfElement);
			} else if (elementOfSingleRule.getTagName().equals("multiplicityEnd")) {
				setMultiplicityEnd(valueOfElement);
				tracer.debug("Setting multiplicityEnd to : " + valueOfElement);
			} else if (elementOfSingleRule.getTagName().equals("owlvariable1")) {
				setOwlVariable1(valueOfElement);
				tracer.debug("Setting owlVariable1 to : " + valueOfElement);
			} else if (elementOfSingleRule.getTagName().equals("owlvariable2")) {
				setOwlVariable2(valueOfElement);
				tracer.debug("Setting owlVariable2 to : " + valueOfElement);
			} else if (elementOfSingleRule.getTagName().equals("owlvariable3")) {
				setOwlVariable3(valueOfElement);
				tracer.debug("Setting owlVariable3 to : " + valueOfElement);
			} else if (elementOfSingleRule.getTagName().equals("ifcondition1")) {
				setIfCondition1(valueOfElement);
				tracer.debug("Setting ifCondition1 to : " + valueOfElement);
			} else if (elementOfSingleRule.getTagName().equals("ifconditionValue1")) {
				setIfConditionValue1(valueOfElement);
				tracer.debug("Setting ifConditionValue1 to : " + valueOfElement);
			} else if (elementOfSingleRule.getTagName().equals("ifcondition2")) {
				setIfCondition2(valueOfElement);
				tracer.debug("Setting ifCondition2 to : " + valueOfElement);
			} else if (elementOfSingleRule.getTagName().equals("ifconditionValue2")) {
				setIfConditionValue2(valueOfElement);
				tracer.debug("Setting ifConditionValue2 to : " + valueOfElement);
			}
		}
	}
}
