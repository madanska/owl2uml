package owl2uml.transformation.condition;

import java.io.PrintStream;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import owl2uml.GlobalVariables;

/**
 * @author Ozer Ozdikis
 * @version June 2006
 */
public class OWLClassTransformationCondition extends ATransformationCondition {
	public String classNameIfCondition = GlobalVariables.IF_NO_CONDITION;
	public String classNameIfConditionValue = "";
	public String superClassIfCondition = GlobalVariables.IF_NO_CONDITION;
	public String superClassIfConditionValue = "";
	public boolean isSuperClassForAllLevels = false;
	public String complementOfIfCondition = GlobalVariables.IF_NO_CONDITION;
	public String complementOfIfConditionValue = "";
	public String intersectionOfIfCondition = GlobalVariables.IF_NO_CONDITION;
	public String intersectionOfIfConditionValue = "";
	public String unionOfIfCondition = GlobalVariables.IF_NO_CONDITION;
	public String unionOfIfConditionValue = "";
	public String minCardinalityPropertyIfCondition = GlobalVariables.IF_NO_CONDITION;
	public String minCardinalityPropertyIfConditionValue = "";
	public String minCardinalityValueIfCondition = GlobalVariables.IF_NO_CONDITION;
	public String minCardinalityValueIfConditionValue = "";
	public String maxCardinalityPropertyIfCondition = GlobalVariables.IF_NO_CONDITION;
	public String maxCardinalityPropertyIfConditionValue = "";
	public String maxCardinalityValueIfCondition = GlobalVariables.IF_NO_CONDITION;
	public String maxCardinalityValueIfConditionValue = "";
	public String allValuesPropertyIfCondition = GlobalVariables.IF_NO_CONDITION;
	public String allValuesPropertyIfConditionValue = "";
	public String allValuesValueIfCondition = GlobalVariables.IF_NO_CONDITION;
	public String allValuesValueIfConditionValue = "";
	public String hasValuePropertyIfCondition = GlobalVariables.IF_NO_CONDITION;
	public String hasValuePropertyIfConditionValue = "";
	public String hasValueValueIfCondition = GlobalVariables.IF_NO_CONDITION;
	public String hasValueValueIfConditionValue = "";
	public String someValuesPropertyIfCondition = GlobalVariables.IF_NO_CONDITION;
	public String someValuesPropertyIfConditionValue = "";
	public String someValuesValueIfCondition = GlobalVariables.IF_NO_CONDITION;
	public String someValuesValueIfConditionValue = "";

	public OWLClassTransformationCondition() {
	}

	/**
	 * saves this transformation condition configuration to the file
	 */
	public void saveConditionConfiguration(PrintStream printStream) {
		tracer.debug("Printing the transformation condition to the file");
		printStream.println("				<classNameIfCondition>" + classNameIfCondition + "</classNameIfCondition>");
		printStream.println("				<classNameIfConditionValue>" + classNameIfConditionValue
				+ "</classNameIfConditionValue>");
		printStream
				.println("				<superClassIfCondition>" + superClassIfCondition + "</superClassIfCondition>");
		printStream.println("				<superClassIfConditionValue>" + superClassIfConditionValue
				+ "</superClassIfConditionValue>");
		printStream.println(
				"				<isSuperClassForAllLevels>" + isSuperClassForAllLevels + "</isSuperClassForAllLevels>");
		printStream.println(
				"				<complementOfIfCondition>" + complementOfIfCondition + "</complementOfIfCondition>");
		printStream.println("				<complementOfIfConditionValue>" + complementOfIfConditionValue
				+ "</complementOfIfConditionValue>");
		printStream.println("				<intersectionOfIfCondition>" + intersectionOfIfCondition
				+ "</intersectionOfIfCondition>");
		printStream.println("				<intersectionOfIfConditionValue>" + intersectionOfIfConditionValue
				+ "</intersectionOfIfConditionValue>");
		printStream.println("				<unionOfIfCondition>" + unionOfIfCondition + "</unionOfIfCondition>");
		printStream.println(
				"				<unionOfIfConditionValue>" + unionOfIfConditionValue + "</unionOfIfConditionValue>");
		printStream.println("				<minCardinalityPropertyIfCondition>" + minCardinalityPropertyIfCondition
				+ "</minCardinalityPropertyIfCondition>");
		printStream.println("				<minCardinalityPropertyIfConditionValue>"
				+ minCardinalityPropertyIfConditionValue + "</minCardinalityPropertyIfConditionValue>");
		printStream.println("				<minCardinalityValueIfCondition>" + minCardinalityValueIfCondition
				+ "</minCardinalityValueIfCondition>");
		printStream.println("				<minCardinalityValueIfConditionValue>" + minCardinalityValueIfConditionValue
				+ "</minCardinalityValueIfConditionValue>");
		printStream.println("				<maxCardinalityPropertyIfCondition>" + maxCardinalityPropertyIfCondition
				+ "</maxCardinalityPropertyIfCondition>");
		printStream.println("				<maxCardinalityPropertyIfConditionValue>"
				+ maxCardinalityPropertyIfConditionValue + "</maxCardinalityPropertyIfConditionValue>");
		printStream.println("				<maxCardinalityValueIfCondition>" + maxCardinalityValueIfCondition
				+ "</maxCardinalityValueIfCondition>");
		printStream.println("				<maxCardinalityValueIfConditionValue>" + maxCardinalityValueIfConditionValue
				+ "</maxCardinalityValueIfConditionValue>");
		printStream.println("				<allValuesPropertyIfCondition>" + allValuesPropertyIfCondition
				+ "</allValuesPropertyIfCondition>");
		printStream.println("				<allValuesPropertyIfConditionValue>" + allValuesPropertyIfConditionValue
				+ "</allValuesPropertyIfConditionValue>");
		printStream.println("				<allValuesValueIfCondition>" + allValuesValueIfCondition
				+ "</allValuesValueIfCondition>");
		printStream.println("				<allValuesValueIfConditionValue>" + allValuesValueIfConditionValue
				+ "</allValuesValueIfConditionValue>");
		printStream.println("				<hasValuePropertyIfCondition>" + hasValuePropertyIfCondition
				+ "</hasValuePropertyIfCondition>");
		printStream.println("				<hasValuePropertyIfConditionValue>" + hasValuePropertyIfConditionValue
				+ "</hasValuePropertyIfConditionValue>");
		printStream.println(
				"				<hasValueValueIfCondition>" + hasValueValueIfCondition + "</hasValueValueIfCondition>");
		printStream.println("				<hasValueValueIfConditionValue>" + hasValueValueIfConditionValue
				+ "</hasValueValueIfConditionValue>");
		printStream.println("				<someValuesPropertyIfCondition>" + someValuesPropertyIfCondition
				+ "</someValuesPropertyIfCondition>");
		printStream.println("				<someValuesPropertyIfConditionValue>" + someValuesPropertyIfConditionValue
				+ "</someValuesPropertyIfConditionValue>");
		printStream.println("				<someValuesValueIfCondition>" + someValuesValueIfCondition
				+ "</someValuesValueIfCondition>");
		printStream.println("				<someValuesValueIfConditionValue>" + someValuesValueIfConditionValue
				+ "</someValuesValueIfConditionValue>");
	}

	/**
	 * reads the transformation condition configuration from file and loads it to
	 * this instance
	 * 
	 * ????
	 */
	public void loadConditionConfiguration(NodeList nodesOfCondition) {
		tracer.debug("loadConditionConfiguration called");
		for (int i = 0; i < nodesOfCondition.getLength(); i++) {
			Node nodeOfCondition = nodesOfCondition.item(i);
			if (nodeOfCondition.getNodeType() != Node.ELEMENT_NODE)
				continue;
			Element elementOfCondition = (Element) nodeOfCondition;
			if (elementOfCondition.getFirstChild() == null)
				continue;
			String valueOfElement = elementOfCondition.getFirstChild().getTextContent();
			if (elementOfCondition.getTagName().equals("classNameIfCondition")) {
				tracer.debug("Setting classNameIfCondition to : " + valueOfElement);
				this.classNameIfCondition = valueOfElement;
			} else if (elementOfCondition.getTagName().equals("classNameIfConditionValue")) {
				tracer.debug("Setting classNameIfConditionValue to : " + valueOfElement);
				this.classNameIfConditionValue = valueOfElement;
			} else if (elementOfCondition.getTagName().equals("superClassIfCondition")) {
				tracer.debug("Setting superClassIfCondition to : " + valueOfElement);
				this.superClassIfCondition = valueOfElement;
			} else if (elementOfCondition.getTagName().equals("superClassIfConditionValue")) {
				tracer.debug("Setting superClassIfConditionValue to : " + valueOfElement);
				this.superClassIfConditionValue = valueOfElement;
			} else if (elementOfCondition.getTagName().equals("isSuperClassForAllLevels")) {
				tracer.debug("Setting isSuperClassForAllLevels to : " + isSuperClassForAllLevels);
				this.isSuperClassForAllLevels = Boolean.parseBoolean(valueOfElement);
			} else if (elementOfCondition.getTagName().equals("complementOfIfCondition")) {
				tracer.debug("Setting complementOfIfCondition to : " + valueOfElement);
				this.complementOfIfCondition = valueOfElement;
			} else if (elementOfCondition.getTagName().equals("complementOfIfConditionValue")) {
				tracer.debug("Setting complementOfIfConditionValue to : " + valueOfElement);
				this.complementOfIfConditionValue = valueOfElement;
			} else if (elementOfCondition.getTagName().equals("intersectionOfIfCondition")) {
				tracer.debug("Setting intersectionOfIfCondition to : " + valueOfElement);
				this.intersectionOfIfCondition = valueOfElement;
			} else if (elementOfCondition.getTagName().equals("intersectionOfIfConditionValue")) {
				tracer.debug("Setting intersectionOfIfConditionValue to : " + valueOfElement);
				this.intersectionOfIfConditionValue = valueOfElement;
			} else if (elementOfCondition.getTagName().equals("unionOfIfCondition")) {
				tracer.debug("Setting unionOfIfCondition to : " + valueOfElement);
				this.unionOfIfCondition = valueOfElement;
			} else if (elementOfCondition.getTagName().equals("unionOfIfConditionValue")) {
				tracer.debug("Setting unionOfIfConditionValue to : " + valueOfElement);
				this.unionOfIfConditionValue = valueOfElement;
			} else if (elementOfCondition.getTagName().equals("minCardinalityPropertyIfCondition")) {
				tracer.debug("Setting minCardinalityPropertyIfCondition to : " + valueOfElement);
				this.minCardinalityPropertyIfCondition = valueOfElement;
			} else if (elementOfCondition.getTagName().equals("minCardinalityPropertyIfConditionValue")) {
				tracer.debug("Setting minCardinalityPropertyIfConditionValue to : " + valueOfElement);
				this.minCardinalityPropertyIfConditionValue = valueOfElement;
			} else if (elementOfCondition.getTagName().equals("minCardinalityValueIfCondition")) {
				tracer.debug("Setting minCardinalityValueIfCondition to : " + valueOfElement);
				this.minCardinalityValueIfCondition = valueOfElement;
			} else if (elementOfCondition.getTagName().equals("minCardinalityValueIfConditionValue")) {
				tracer.debug("Setting minCardinalityValueIfConditionValue to : " + valueOfElement);
				this.minCardinalityValueIfConditionValue = valueOfElement;
			} else if (elementOfCondition.getTagName().equals("maxCardinalityPropertyIfCondition")) {
				tracer.debug("Setting maxCardinalityPropertyIfCondition to : " + valueOfElement);
				this.maxCardinalityPropertyIfCondition = valueOfElement;
			} else if (elementOfCondition.getTagName().equals("maxCardinalityPropertyIfConditionValue")) {
				tracer.debug("Setting maxCardinalityPropertyIfConditionValue to : " + valueOfElement);
				this.maxCardinalityPropertyIfConditionValue = valueOfElement;
			} else if (elementOfCondition.getTagName().equals("maxCardinalityValueIfCondition")) {
				tracer.debug("Setting maxCardinalityValueIfCondition to : " + valueOfElement);
				this.maxCardinalityValueIfCondition = valueOfElement;
			} else if (elementOfCondition.getTagName().equals("maxCardinalityValueIfConditionValue")) {
				tracer.debug("Setting maxCardinalityValueIfConditionValue to : " + valueOfElement);
				this.maxCardinalityValueIfConditionValue = valueOfElement;
			} else if (elementOfCondition.getTagName().equals("allValuesPropertyIfCondition")) {
				tracer.debug("Setting allValuesPropertyIfCondition to : " + valueOfElement);
				this.allValuesPropertyIfCondition = valueOfElement;
			} else if (elementOfCondition.getTagName().equals("allValuesPropertyIfConditionValue")) {
				tracer.debug("Setting allValuesPropertyIfConditionValue to : " + valueOfElement);
				this.allValuesPropertyIfConditionValue = valueOfElement;
			} else if (elementOfCondition.getTagName().equals("allValuesValueIfCondition")) {
				tracer.debug("Setting allValuesValueIfCondition to : " + valueOfElement);
				this.allValuesValueIfCondition = valueOfElement;
			} else if (elementOfCondition.getTagName().equals("allValuesValueIfConditionValue")) {
				tracer.debug("Setting allValuesValueIfConditionValue to : " + valueOfElement);
				this.allValuesValueIfConditionValue = valueOfElement;
			} else if (elementOfCondition.getTagName().equals("hasValuePropertyIfCondition")) {
				tracer.debug("Setting hasValuePropertyIfCondition to : " + valueOfElement);
				this.hasValuePropertyIfCondition = valueOfElement;
			} else if (elementOfCondition.getTagName().equals("hasValuePropertyIfConditionValue")) {
				tracer.debug("Setting hasValuePropertyIfConditionValue to : " + valueOfElement);
				this.hasValuePropertyIfConditionValue = valueOfElement;
			} else if (elementOfCondition.getTagName().equals("hasValueValueIfCondition")) {
				tracer.debug("Setting hasValueValueIfCondition to : " + valueOfElement);
				this.hasValueValueIfCondition = valueOfElement;
			} else if (elementOfCondition.getTagName().equals("hasValueValueIfConditionValue")) {
				tracer.debug("Setting hasValueValueIfConditionValue to : " + valueOfElement);
				this.hasValueValueIfConditionValue = valueOfElement;
			} else if (elementOfCondition.getTagName().equals("someValuesPropertyIfCondition")) {
				tracer.debug("Setting someValuesPropertyIfCondition to : " + valueOfElement);
				this.someValuesPropertyIfCondition = valueOfElement;
			} else if (elementOfCondition.getTagName().equals("someValuesPropertyIfConditionValue")) {
				tracer.debug("Setting someValuesPropertyIfConditionValue to : " + valueOfElement);
				this.someValuesPropertyIfConditionValue = valueOfElement;
			} else if (elementOfCondition.getTagName().equals("someValuesValueIfCondition")) {
				tracer.debug("Setting someValuesValueIfCondition to : " + valueOfElement);
				this.someValuesValueIfCondition = valueOfElement;
			} else if (elementOfCondition.getTagName().equals("someValuesValueIfConditionValue")) {
				tracer.debug("Setting someValuesValueIfConditionValue to : " + valueOfElement);
				this.someValuesValueIfConditionValue = valueOfElement;
			}
		}
	}
}
