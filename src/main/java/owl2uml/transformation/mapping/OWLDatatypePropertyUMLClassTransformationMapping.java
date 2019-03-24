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
public class OWLDatatypePropertyUMLClassTransformationMapping {
	private String umlVariable = "";
	private String owlVariable = "";
	private String ifCondition = "";
	private String ifConditionValue = "";
	private Category tracer = Logger.getLogger(OWLDatatypePropertyUMLClassTransformationMapping.class);

	public OWLDatatypePropertyUMLClassTransformationMapping() {
		setUmlVariable(GlobalVariables.UML_EMPTY);
		setOwlVariable(GlobalVariables.OWL_SUPERPROPERTY_NAME);
		setIfCondition(GlobalVariables.IF_NO_CONDITION);
		setIfConditionValue("");
	}

	public String getIfCondition() {
		return ifCondition;
	}

	public void setIfCondition(String ifCondition) {
		this.ifCondition = ifCondition;
	}

	public String getIfConditionValue() {
		return ifConditionValue;
	}

	public void setIfConditionValue(String ifConditionValue) {
		this.ifConditionValue = ifConditionValue;
	}

	public String getOwlVariable() {
		return owlVariable;
	}

	public void setOwlVariable(String owlVariable) {
		this.owlVariable = owlVariable;
	}

	public String getUmlVariable() {
		return umlVariable;
	}

	public void setUmlVariable(String umlVariable) {
		this.umlVariable = umlVariable;
	}

	public void saveMappingConfiguration(PrintStream printStream) {
		tracer.debug("Printing the class transformation rule to the file");
		printStream.println("				<umlvariable>" + umlVariable + "</umlvariable>");
		printStream.println("				<owlvariable>" + owlVariable + "</owlvariable>");
		printStream.println("				<ifcondition>" + ifCondition + "</ifcondition>");
		printStream.println("				<ifconditionValue>" + ifConditionValue + "</ifconditionValue>");
	}

	/**
	 * reads single rule configuration from file and loads into this class
	 */
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
				tracer.debug("Setting UmlVariable to : " + valueOfElement);
			} else if (elementOfSingleRule.getTagName().equals("owlvariable")) {
				setOwlVariable(valueOfElement);
				tracer.debug("Setting OwlVariable to : " + valueOfElement);
			} else if (elementOfSingleRule.getTagName().equals("ifcondition")) {
				setIfCondition(valueOfElement);
				tracer.debug("Setting ifCondition to : " + valueOfElement);
			} else if (elementOfSingleRule.getTagName().equals("ifconditionValue")) {
				setIfConditionValue(valueOfElement);
				tracer.debug("Setting ifConditionValue to : " + valueOfElement);
			}
		}
	}
}
