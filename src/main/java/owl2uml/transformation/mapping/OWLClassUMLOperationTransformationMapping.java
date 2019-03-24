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
public class OWLClassUMLOperationTransformationMapping {
	private String ownerNameIfCondition = "";
	private String ownerNameIfConditionValue = "";
	private String parameterName = "";
	private String parameterType = "";
	private String parameterNameIfCondition = "";
	private String parameterNameIfConditionValue = "";
	private String parameterName2 = "";
	private String parameterNameIfCondition2 = "";
	private String parameterNameIfConditionValue2 = "";
	private Category tracer = Logger.getLogger(OWLClassUMLOperationTransformationMapping.class);

	public OWLClassUMLOperationTransformationMapping() {
		setOwnerNameIfCondition(GlobalVariables.IF_NO_CONDITION);
		setOwnerNameIfConditionValue("");
		setParameterName(GlobalVariables.OWL_SUPERCLASS_NAME);
		setParameterType(GlobalVariables.UML_PARAMETER_TYPE_IN);
		setParameterNameIfCondition(GlobalVariables.IF_NO_CONDITION);
		setParameterNameIfConditionValue("");
		setParameterName2("");
		setParameterNameIfCondition2(GlobalVariables.IF_NO_CONDITION);
		setParameterNameIfConditionValue2("");
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

	public String getParameterName2() {
		return parameterName2;
	}

	public void setParameterName2(String parameterName2) {
		this.parameterName2 = parameterName2;
	}

	public String getParameterNameIfCondition2() {
		return parameterNameIfCondition2;
	}

	public void setParameterNameIfCondition2(String parameterNameIfCondition2) {
		this.parameterNameIfCondition2 = parameterNameIfCondition2;
	}

	public String getParameterNameIfConditionValue2() {
		return parameterNameIfConditionValue2;
	}

	public void setParameterNameIfConditionValue2(String parameterNameIfConditionValue2) {
		this.parameterNameIfConditionValue2 = parameterNameIfConditionValue2;
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
		printStream.println("				<parameterName2>" + parameterName2 + "</parameterName2>");
		printStream.println("				<parameterNameIfCondition2>" + parameterNameIfCondition2
				+ "</parameterNameIfCondition2>");
		printStream.println("				<parameterNameIfConditionValue2>" + parameterNameIfConditionValue2
				+ "</parameterNameIfConditionValue2>");
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
			} else if (elementOfSingleRule.getTagName().equals("parameterName2")) {
				setParameterName2(valueOfElement);
				tracer.debug("Setting parameterName2 to : " + valueOfElement);
			} else if (elementOfSingleRule.getTagName().equals("parameterNameIfCondition2")) {
				setParameterNameIfCondition2(valueOfElement);
				tracer.debug("Setting parameterNameIfCondition2 to : " + valueOfElement);
			} else if (elementOfSingleRule.getTagName().equals("parameterNameIfConditionValue2")) {
				setParameterNameIfConditionValue2(valueOfElement);
				tracer.debug("Setting parameterNameIfConditionValue2 to : " + valueOfElement);
			}
		}
	}
}
