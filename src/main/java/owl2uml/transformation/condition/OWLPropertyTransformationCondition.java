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
public class OWLPropertyTransformationCondition extends ATransformationCondition {

	public String propertyNameIfCondition = GlobalVariables.IF_NO_CONDITION;
	public String propertyNameIfConditionValue = "";
	public String superPropertyIfCondition = GlobalVariables.IF_NO_CONDITION;
	public String superPropertyIfConditionValue = "";
	public boolean isSuperPropertyForAllLevels = false;
	public String domainIfCondition = GlobalVariables.IF_NO_CONDITION;
	public String domainIfConditionValue = "";
	public String rangeIfCondition = GlobalVariables.IF_NO_CONDITION;
	public String rangeIfConditionValue = "";

	public OWLPropertyTransformationCondition() {
	}

	/**
	 * saves this transformation condition configuration to the file
	 */
	public void saveConditionConfiguration(PrintStream printStream) {
		tracer.debug("Printing the transformation condition to the file");
		printStream.println(
				"				<propertyNameIfCondition>" + propertyNameIfCondition + "</propertyNameIfCondition>");
		printStream.println("				<propertyNameIfConditionValue>" + propertyNameIfConditionValue
				+ "</propertyNameIfConditionValue>");
		printStream.println(
				"				<superPropertyIfCondition>" + superPropertyIfCondition + "</superPropertyIfCondition>");
		printStream.println("				<superPropertyIfConditionValue>" + superPropertyIfConditionValue
				+ "</superPropertyIfConditionValue>");
		printStream.println("				<isSuperPropertyForAllLevels>" + isSuperPropertyForAllLevels
				+ "</isSuperPropertyForAllLevels>");
		printStream.println("				<domainIfCondition>" + domainIfCondition + "</domainIfCondition>");
		printStream.println(
				"				<domainIfConditionValue>" + domainIfConditionValue + "</domainIfConditionValue>");
		printStream.println("				<rangeIfCondition>" + rangeIfCondition + "</rangeIfCondition>");
		printStream
				.println("				<rangeIfConditionValue>" + rangeIfConditionValue + "</rangeIfConditionValue>");
	}

	/**
	 * reads the transformation condition configuration from file and loads it to
	 * this instance
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
			if (elementOfCondition.getTagName().equals("propertyNameIfCondition")) {
				tracer.debug("Setting propertyNameIfCondition to : " + valueOfElement);
				this.propertyNameIfCondition = valueOfElement;
			} else if (elementOfCondition.getTagName().equals("propertyNameIfConditionValue")) {
				tracer.debug("Setting propertyNameIfConditionValue to : " + valueOfElement);
				this.propertyNameIfConditionValue = valueOfElement;
			} else if (elementOfCondition.getTagName().equals("superPropertyIfCondition")) {
				tracer.debug("Setting superPropertyIfCondition to : " + valueOfElement);
				this.superPropertyIfCondition = valueOfElement;
			} else if (elementOfCondition.getTagName().equals("superPropertyIfConditionValue")) {
				tracer.debug("Setting superPropertyIfConditionValue to : " + valueOfElement);
				this.superPropertyIfConditionValue = valueOfElement;
			} else if (elementOfCondition.getTagName().equals("isSuperPropertyForAllLevels")) {
				tracer.debug("Setting isSuperPropertyForAllLevels to : " + valueOfElement);
				this.isSuperPropertyForAllLevels = Boolean.parseBoolean(valueOfElement);
			} else if (elementOfCondition.getTagName().equals("domainIfCondition")) {
				tracer.debug("Setting domainIfCondition to : " + valueOfElement);
				this.domainIfCondition = valueOfElement;
			} else if (elementOfCondition.getTagName().equals("domainIfConditionValue")) {
				tracer.debug("Setting domainIfConditionValue to : " + valueOfElement);
				this.domainIfConditionValue = valueOfElement;
			} else if (elementOfCondition.getTagName().equals("rangeIfCondition")) {
				tracer.debug("Setting rangeIfCondition to : " + valueOfElement);
				this.rangeIfCondition = valueOfElement;
			} else if (elementOfCondition.getTagName().equals("rangeIfConditionValue")) {
				tracer.debug("Setting rangeIfConditionValue to : " + valueOfElement);
				this.rangeIfConditionValue = valueOfElement;
			}
		}
	}
}
