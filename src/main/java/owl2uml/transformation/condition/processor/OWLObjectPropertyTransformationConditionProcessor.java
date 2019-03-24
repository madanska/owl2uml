package owl2uml.transformation.condition.processor;

import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;
import org.eclipse.eodm.owl.OWLObjectProperty;
import org.eclipse.eodm.owl.OWLOntology;
import org.eclipse.eodm.owl.Property;
import org.eclipse.eodm.rdfs.RDFProperty;
import org.eclipse.eodm.rdfs.RDFSResource;

import owl2uml.GlobalOperations;
import owl2uml.GlobalVariables;
import owl2uml.graph.OWLObjectPropertyTransformationGraph;
import owl2uml.graph.cell.OWLObjectPropertyGraphCell;
import owl2uml.transformation.condition.OWLPropertyTransformationCondition;

/**
 * Class that gets the OWLObjectProperties in the input ontology, and checks if
 * they satisfy the transformation condition. Some are filtered out and will not
 * be processed.
 * 
 * @author Ozer Ozdikis
 * @version June 2006
 */
public class OWLObjectPropertyTransformationConditionProcessor {
	private OWLObjectPropertyTransformationGraph transformationGraph;
	private OWLOntology ontology;
	private Category tracer = Logger.getLogger(OWLObjectPropertyTransformationConditionProcessor.class);

	/**
	 * constructor
	 */
	public OWLObjectPropertyTransformationConditionProcessor(OWLObjectPropertyTransformationGraph transformationGraph,
			OWLOntology ontology) {
		tracer.debug("Creating OWLObjectPropertyTransformationConditionProcessor");
		this.transformationGraph = transformationGraph;
		this.ontology = ontology;
	}

	/**
	 * Gets the classes of type OWLObjectProperty from the Ontology, and sends each
	 * of them to the isTransformationConditionSatisfied() function. If satisfied,
	 * adds them to the list.
	 */
	public Vector<OWLObjectProperty> getFilteredOWLObjectProperties() {
		tracer.info("getFilteredOWLObjectProperties called");
		Vector<OWLObjectProperty> filteredOWLProperties = new Vector<OWLObjectProperty>();
		OWLObjectPropertyGraphCell owlGraphCell = transformationGraph.getOWLGraphCell();
		OWLPropertyTransformationCondition transformationCondition = owlGraphCell.getTransformationCondition();
		Iterator<?> propertyList = ontology.getResourceList(org.eclipse.eodm.owl.OWLObjectProperty.class).iterator();
		tracer.debug(
				"Begining the transformation condition check on OWLObjectProperty instances of the given ontology");
		while (propertyList.hasNext()) {
			OWLObjectProperty owlProperty = (OWLObjectProperty) propertyList.next();
			if (isTransformationConditionSatisfied(owlProperty, transformationCondition))
				filteredOWLProperties.add(owlProperty);
		}
		return filteredOWLProperties;
	}

	/**
	 * Gets an owlObjectProperty and depending on the configured
	 * transformationCondition, evaluates it.
	 */
	private boolean isTransformationConditionSatisfied(OWLObjectProperty owlProperty,
			OWLPropertyTransformationCondition transformationCondition) {
		tracer.info("Running isTransformationConditionSatisfied for objectProperty : " + owlProperty.getLocalName());
		if (!transformationCondition.propertyNameIfCondition.equals(GlobalVariables.IF_NO_CONDITION)) {
			tracer.debug("Property has a name condition for : " + transformationCondition.propertyNameIfCondition
					+ " : " + transformationCondition.propertyNameIfCondition);
			if (!GlobalOperations.isStringConditionSatisfied(transformationCondition.propertyNameIfCondition,
					owlProperty.getLocalName(), transformationCondition.propertyNameIfConditionValue)) {
				tracer.debug("OWLObjectProperty does not satisfy the propertyName condition.");
				return false;
			}
			tracer.debug("OWLObjectProperty satisfies the propertyName condition.");
		}
		if (!transformationCondition.superPropertyIfCondition.equals(GlobalVariables.IF_NO_CONDITION)) {
			tracer.debug("ObjectProperty has a superProperty condition for : "
					+ transformationCondition.superPropertyIfCondition + " : "
					+ transformationCondition.superPropertyIfCondition);
			Vector<RDFProperty> superPropertyVector = new Vector<RDFProperty>();
			if (transformationCondition.isSuperPropertyForAllLevels)
				superPropertyVector = GlobalOperations.getRecursiveRDFSSubPropertyOf(owlProperty);
			else {
				Iterator<?> superPropertyList = owlProperty.getRDFSSubPropertyOf().iterator();
				while (superPropertyList.hasNext()) {
					Property owlSuperProperty = (Property) superPropertyList.next();
					superPropertyVector.add(owlSuperProperty);
				}
			}
			boolean conditionSatisfied = false;
			for (int i = 0; i < superPropertyVector.size(); i++) {
				Property owlSuperProperty = (Property) superPropertyVector.get(i);
				tracer.debug("Checking superProperty : " + owlSuperProperty.getLocalName());
				if (GlobalOperations.isStringConditionSatisfied(transformationCondition.superPropertyIfCondition,
						owlSuperProperty.getLocalName(), transformationCondition.superPropertyIfConditionValue)) {
					tracer.debug("Superproperty satisfies the condition");
					conditionSatisfied = true;
					break;
				}
			}
			if (!conditionSatisfied) {
				tracer.debug("No satisfying superProperty is found for " + owlProperty.getLocalName());
				return false;
			}
		}
		if (!transformationCondition.domainIfCondition.equals(GlobalVariables.IF_NO_CONDITION)) {
			tracer.debug("ObjectProperty has a domain condition for : " + transformationCondition.domainIfCondition
					+ " : " + transformationCondition.domainIfCondition);
			Iterator<?> domainList = owlProperty.getRDFSDomain().iterator();
			boolean conditionSatisfied = false;
			while (domainList.hasNext()) {
				RDFSResource domainResource = (RDFSResource) domainList.next();
				tracer.debug("Checking domainResource : " + domainResource.getLocalName());
				if (GlobalOperations.isStringConditionSatisfied(transformationCondition.domainIfCondition,
						domainResource.getLocalName(), transformationCondition.domainIfConditionValue)) {
					tracer.debug("domain satisfies the condition");
					conditionSatisfied = true;
					break;
				}
			}
			if (!conditionSatisfied) {
				tracer.debug("No satisfying domain is found for " + owlProperty.getLocalName());
				return false;
			}
		}
		if (!transformationCondition.rangeIfCondition.equals(GlobalVariables.IF_NO_CONDITION)) {
			tracer.debug("ObjectProperty has a range condition for : " + transformationCondition.rangeIfCondition
					+ " : " + transformationCondition.rangeIfCondition);
			Iterator<?> rangeList = owlProperty.getRDFSRange().iterator();
			boolean conditionSatisfied = false;
			while (rangeList.hasNext()) {
				RDFSResource rangeResource = (RDFSResource) rangeList.next();
				tracer.debug("Checking rangeResource : " + rangeResource.getLocalName());
				if (GlobalOperations.isStringConditionSatisfied(transformationCondition.rangeIfCondition,
						rangeResource.getLocalName(), transformationCondition.rangeIfConditionValue)) {
					tracer.debug("range satisfies the condition");
					conditionSatisfied = true;
					break;
				}
			}
			if (!conditionSatisfied) {
				tracer.debug("No satisfying range is found for " + owlProperty.getLocalName());
				return false;
			}
		}
		tracer.debug("Conditions are satisfied for the property " + owlProperty.getLocalName());
		return true;
	}
}
