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
public class OWLObjectPropertyUMLOperationTransformationMapping {
	private String ownerNameIfCondition = "";
	private String ownerNameIfConditionValue = "";
	private String parameterName = "";
	private String parameterType = "";
	private String parameterNameIfCondition = "";
	private String parameterNameIfConditionValue = "";
	private Category tracer = Logger.getLogger(OWLObjectPropertyUMLOperationTransformationMapping.class);

	public OWLObjectPropertyUMLOperationTransformationMapping() {
		setOwnerNameIfCondition(GlobalVariables.IF_NO_CONDITION);
		setOwnerNameIfConditionValue("");
		setParameterName(GlobalVariables.OWL_SUPERPROPERTY_NAME);
		setParameterType(GlobalVariables.UML_PARAMETER_TYPE_IN);
		setParameterNameIfCondition(GlobalVariables.IF_NO_CONDITION);
		setParameterNameIfConditionValue("");
	}

	public String getOwnerNameIfCondition() {
		return ownerNameIfCondition;
	}

	public void setOwnerNameIfCondition(String ownerNameIfCondition) {
		this.ownerNameIfCondition = ownerNameIfCondition;
	}

	public String getOwnerNameIfConditionValue() {
		return ownerNameIfConditionValue;
	}

	public void setOwnerNameIfConditionValue(String ownerNameIfConditionValue) {
		this.ownerNameIfConditionValue = ownerNameIfConditionValue;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public String getParameterNameIfCondition() {
		return parameterNameIfCondition;
	}

	public void setParameterNameIfCondition(String parameterNameIfCondition) {
		this.parameterNameIfCondition = parameterNameIfCondition;
	}

	public String getParameterNameIfConditionValue() {
		return parameterNameIfConditionValue;
	}

	public void setParameterNameIfConditionValue(String parameterNameIfConditionValue) {
		this.parameterNameIfConditionValue = parameterNameIfConditionValue;
	}

	public String getParameterType() {
		return parameterType;
	}

	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}

	public void saveMappingConfiguration(PrintStream printStream) {
		tracer.debug("Printing the transformation configuration to the file");
		printStream.println("				<ownerNameIfCondition>" + ownerNameIfCondition + "</ownerNameIfCondition>");
		printStream.println("				<ownerNameIfConditionValue>" + ownerNameIfConditionValue
				+ "</ownerNameIfConditionValue>");
		printStream.println("				<parameterName>" + parameterName + "</parameterName>");
		printStream.println("				<parameterType>" + parameterType + "</parameterType>");
		printStream.println(
				"				<parameterNameIfCondition>" + parameterNameIfCondition + "</parameterNameIfCondition>");
		printStream.println("				<parameterNameIfConditionValue>" + parameterNameIfConditionValue
				+ "</parameterNameIfConditionValue>");
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
			if (elementOfSingleRule.getTagName().equals("ownerNameIfCondition")) {
				setOwnerNameIfCondition(valueOfElement);
				tracer.debug("Setting ownerNameIfCondition to : " + valueOfElement);
			} else if (elementOfSingleRule.getTagName().equals("ownerNameIfConditionValue")) {
				setOwnerNameIfConditionValue(valueOfElement);
				tracer.debug("Setting ownerNameIfConditionValue to : " + valueOfElement);
			} else if (elementOfSingleRule.getTagName().equals("parameterName")) {
				setParameterName(valueOfElement);
				tracer.debug("Setting parameterName to : " + valueOfElement);
			} else if (elementOfSingleRule.getTagName().equals("parameterType")) {
				setParameterType(valueOfElement);
				tracer.debug("Setting parameterType to : " + valueOfElement);
			} else if (elementOfSingleRule.getTagName().equals("parameterNameIfCondition")) {
				setParameterNameIfCondition(valueOfElement);
				tracer.debug("Setting parameterNameIfCondition to : " + valueOfElement);
			} else if (elementOfSingleRule.getTagName().equals("parameterNameIfConditionValue")) {
				setParameterNameIfConditionValue(valueOfElement);
				tracer.debug("Setting parameterNameIfConditionValue to : " + valueOfElement);
			}
		}
	}
}
