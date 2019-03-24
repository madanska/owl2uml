package owl2uml.transformation.condition;

import java.io.PrintStream;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;
import org.w3c.dom.NodeList;

/**
 * @author Ozer Ozdikis
 * @version June 2006
 */
public abstract class ATransformationCondition {

	protected Category tracer = Logger.getLogger(ATransformationCondition.class);

	public abstract void saveConditionConfiguration(PrintStream printStream);

	public abstract void loadConditionConfiguration(NodeList nodesOfCondition);
}
