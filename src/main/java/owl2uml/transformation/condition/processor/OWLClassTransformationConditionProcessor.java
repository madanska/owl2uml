package owl2uml.transformation.condition.processor;

import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;
import org.eclipse.eodm.owl.AllValuesFromRestriction;
import org.eclipse.eodm.owl.EnumeratedClass;
import org.eclipse.eodm.owl.HasValueRestriction;
import org.eclipse.eodm.owl.Individual;
import org.eclipse.eodm.owl.MaxCardinalityRestriction;
import org.eclipse.eodm.owl.MinCardinalityRestriction;
import org.eclipse.eodm.owl.OWLClass;
import org.eclipse.eodm.owl.OWLOntology;
import org.eclipse.eodm.owl.SomeValuesFromRestriction;
import org.eclipse.eodm.owl.impl.AllValuesFromRestrictionImpl;
import org.eclipse.eodm.owl.impl.ComplementClassImpl;
import org.eclipse.eodm.owl.impl.EnumeratedClassImpl;
import org.eclipse.eodm.owl.impl.HasValueRestrictionImpl;
import org.eclipse.eodm.owl.impl.IntersectionClassImpl;
import org.eclipse.eodm.owl.impl.MaxCardinalityRestrictionImpl;
import org.eclipse.eodm.owl.impl.MinCardinalityRestrictionImpl;
import org.eclipse.eodm.owl.impl.OWLClassImpl;
import org.eclipse.eodm.owl.impl.SomeValuesFromRestrictionImpl;
import org.eclipse.eodm.owl.impl.UnionClassImpl;
import org.eclipse.eodm.rdfs.RDFProperty;
import org.eclipse.eodm.rdfs.RDFSLiteral;
import org.eclipse.eodm.rdfs.RDFSResource;
import org.eclipse.eodm.rdfs.impl.TypedLiteralImpl;

import owl2uml.GlobalOperations;
import owl2uml.GlobalVariables;
import owl2uml.graph.OWLClassTransformationGraph;
import owl2uml.graph.cell.OWLClassGraphCell;
import owl2uml.transformation.condition.OWLClassTransformationCondition;

/**
 * Class that gets the OWLClasses in the input ontology, and checks if they
 * satisfy the transformation condition. Some are filtered out and will not be
 * processed.
 * 
 * @author Ozer Ozdikis
 * @version June 2006
 */
public class OWLClassTransformationConditionProcessor {
	private OWLClassTransformationGraph transformationGraph;
	private OWLOntology ontology;
	private Category tracer = Logger.getLogger(OWLClassTransformationConditionProcessor.class);

	/**
	 * constructor
	 */
	public OWLClassTransformationConditionProcessor(OWLClassTransformationGraph transformationGraph,
			OWLOntology ontology) {
		tracer.debug("Creating OWLCLassTransformationConditionProcessor");
		this.transformationGraph = transformationGraph;
		this.ontology = ontology;
	}

	/**
	 * Gets the classes of type OWLClassImpl from the Ontology, and sends each of
	 * them to the isTransformationConditionSatisfied() function. If satisfied, adds
	 * them to the list.
	 */
	public Vector<OWLClass> getFilteredOWLClasses() {
		tracer.info("getFilteredOWLClasses called");
		Vector<OWLClass> filteredOWLClasses = new Vector<OWLClass>();
		OWLClassGraphCell owlGraphCell = transformationGraph.getOWLGraphCell();
		OWLClassTransformationCondition transformationCondition = owlGraphCell.getTransformationCondition();
		Iterator<?> classList = ontology.getResourceList(org.eclipse.eodm.owl.OWLClass.class).iterator();
		tracer.debug("Begining the transformation condition check on OWLClass instances of the given ontology");
		while (classList.hasNext()) {
			OWLClass owlClass = (OWLClass) classList.next();
			if (owlClass.getClass().getName().equals(OWLClassImpl.class.getName())) {
				tracer.debug(owlClass.getLocalName() + " is of type OWLClassImpl. Accepted.");
			} else if (owlClass.getClass().getName().equals(EnumeratedClassImpl.class.getName())) {
				tracer.debug(owlClass.getLocalName() + " is of type EnumeratedClassImpl.");
				if (owlClass.getLocalName().startsWith("_")) {
					tracer.debug(owlClass.getLocalName() + " begins with _. It is ignored.");
					continue;
				} else {
					tracer.debug(owlClass.getLocalName() + " does not begin with _. Accepted.");
				}
			} else {
				tracer.debug(owlClass.getLocalName() + " is not of type any accepted type. It is ignored.");
				continue;
			}
			if (isTransformationConditionSatisfied(owlClass, transformationCondition))
				filteredOWLClasses.add(owlClass);
		}
		return filteredOWLClasses;
	}

	/**
	 * Gets an owlClass and depending on the configured transformationCondition,
	 * evaluates it.
	 */
	private boolean isTransformationConditionSatisfied(OWLClass owlClass,
			OWLClassTransformationCondition transformationCondition) {
		tracer.info("Running isTransformationConditionSatisfied for class : " + owlClass.getLocalName());
		if (!transformationCondition.classNameIfCondition.equals(GlobalVariables.IF_NO_CONDITION)) {
			tracer.debug("Class has a name condition for : " + transformationCondition.classNameIfCondition);
			if (!GlobalOperations.isStringConditionSatisfied(transformationCondition.classNameIfCondition,
					owlClass.getLocalName(), transformationCondition.classNameIfConditionValue)) {
				tracer.debug("OWLClass does not satisfy the className condition.");
				return false;
			}
			tracer.debug("OWLClass satisfies the className condition.");
		}
		if (!transformationCondition.superClassIfCondition.equals(GlobalVariables.IF_NO_CONDITION)) {
			tracer.debug("Class has a superClass condition for : " + transformationCondition.superClassIfCondition);
			Vector<OWLClass> superClassVector = new Vector<OWLClass>();
			if (transformationCondition.isSuperClassForAllLevels)
				superClassVector = GlobalOperations.getRecursiveRDFSSubClassOf(owlClass);
			else {
				Iterator<?> superClassList = owlClass.getRDFSSubClassOf().iterator();
				while (superClassList.hasNext()) {
					OWLClass owlSuperClass = (OWLClass) superClassList.next();
					superClassVector.add(owlSuperClass);
				}
			}
			boolean conditionSatisfied = false;
			for (int i = 0; i < superClassVector.size(); i++) {
				OWLClass owlSuperClass = (OWLClass) superClassVector.get(i);
				if (!owlSuperClass.getClass().getName().equals(OWLClassImpl.class.getName()))
					continue;
				tracer.debug("Checking superClass : " + owlSuperClass.getLocalName());
				if (GlobalOperations.isStringConditionSatisfied(transformationCondition.superClassIfCondition,
						owlSuperClass.getLocalName(), transformationCondition.superClassIfConditionValue)) {
					tracer.debug("Superclass satisfies the condition");
					conditionSatisfied = true;
					break;
				}
			}
			if (!conditionSatisfied) {
				tracer.debug("No satisfying superClass is found for " + owlClass.getLocalName());
				return false;
			}
		}
		if (!transformationCondition.complementOfIfCondition.equals(GlobalVariables.IF_NO_CONDITION)) {
			tracer.debug("Class has a complementOf condition for : " + transformationCondition.complementOfIfCondition);
			Vector<OWLClass> superAndEquivalentClasses = GlobalOperations.getSuperAndEquivalentClassVector(owlClass,
					ComplementClassImpl.class.getName());
			boolean conditionSatisfied = false;
			for (int i = 0; i < superAndEquivalentClasses.size(); i++) {
				OWLClass owlSuperEqClass = superAndEquivalentClasses.get(i);
				ComplementClassImpl owlComplementClass = (ComplementClassImpl) owlSuperEqClass;
				OWLClass owlTargetClass = owlComplementClass.getOWLComplementOf();
				if (GlobalOperations.isStringConditionSatisfied(transformationCondition.complementOfIfCondition,
						owlTargetClass.getLocalName(), transformationCondition.complementOfIfConditionValue)) {
					conditionSatisfied = true;
					break;
				}
			}
			if (!conditionSatisfied) {
				tracer.debug("No satisfying complementOf is found for " + owlClass.getLocalName());
				return false;
			}
		}
		if (!transformationCondition.intersectionOfIfCondition.equals(GlobalVariables.IF_NO_CONDITION)) {
			tracer.debug(
					"Class has an intersectionOf condition for : " + transformationCondition.intersectionOfIfCondition);
			Vector<OWLClass> superAndEquivalentClasses = GlobalOperations.getSuperAndEquivalentClassVector(owlClass,
					IntersectionClassImpl.class.getName());
			boolean conditionSatisfied = false;
			for (int i = 0; i < superAndEquivalentClasses.size(); i++) {
				OWLClass owlSuperEqClass = superAndEquivalentClasses.get(i);
				IntersectionClassImpl owlIntersectionClass = (IntersectionClassImpl) owlSuperEqClass;
				Iterator<?> intersectionClasses = owlIntersectionClass.getOWLIntersectionOf().iterator();
				while (intersectionClasses.hasNext()) {
					OWLClass intersectionClass = (OWLClass) intersectionClasses.next();
					if (GlobalOperations.isStringConditionSatisfied(transformationCondition.intersectionOfIfCondition,
							intersectionClass.getLocalName(), transformationCondition.intersectionOfIfConditionValue)) {
						conditionSatisfied = true;
						i = superAndEquivalentClasses.size();
						break;
					}
				}
			}
			if (!conditionSatisfied) {
				tracer.debug("No satisfying intersectionOf is found for " + owlClass.getLocalName());
				return false;
			}
		}
		if (!transformationCondition.unionOfIfCondition.trim().equals(GlobalVariables.IF_NO_CONDITION)) {
			tracer.debug("Class has a unionOf condition for : " + transformationCondition.unionOfIfCondition);
			Vector<OWLClass> superAndEquivalentClasses = GlobalOperations.getSuperAndEquivalentClassVector(owlClass,
					UnionClassImpl.class.getName());
			boolean conditionSatisfied = false;
			for (int i = 0; i < superAndEquivalentClasses.size(); i++) {
				OWLClass owlSuperEqClass = superAndEquivalentClasses.get(i);
				UnionClassImpl owlUnionClass = (UnionClassImpl) owlSuperEqClass;
				Iterator<?> unionClasses = owlUnionClass.getOWLUnionOf().iterator();
				while (unionClasses.hasNext()) {
					OWLClass unionClass = (OWLClass) unionClasses.next();
					if (GlobalOperations.isStringConditionSatisfied(transformationCondition.unionOfIfCondition,
							unionClass.getLocalName(), transformationCondition.unionOfIfConditionValue)) {
						conditionSatisfied = true;
						i = superAndEquivalentClasses.size();
						break;
					}
				}
			}
			if (!conditionSatisfied) {
				tracer.debug("No satisfying unionOf is found for " + owlClass.getLocalName());
				return false;
			}
		}
		if (!transformationCondition.minCardinalityPropertyIfCondition.equals(GlobalVariables.IF_NO_CONDITION)) {
			tracer.debug("Class has a minCardinality Property condition for : "
					+ transformationCondition.minCardinalityPropertyIfCondition);
			Vector<OWLClass> superAndEquivalentClasses = GlobalOperations.getSuperAndEquivalentClassVector(owlClass,
					MinCardinalityRestrictionImpl.class.getName());
			boolean conditionSatisfied = false;
			for (int i = 0; i < superAndEquivalentClasses.size(); i++) {
				OWLClass owlSuperEqClass = superAndEquivalentClasses.get(i);
				MinCardinalityRestriction restriction = (MinCardinalityRestriction) owlSuperEqClass;
				RDFProperty onProperty = restriction.getOWLOnProperty();
				if (GlobalOperations.isStringConditionSatisfied(
						transformationCondition.minCardinalityPropertyIfCondition, onProperty.getLocalName(),
						transformationCondition.minCardinalityPropertyIfConditionValue)) {
					conditionSatisfied = true;
					break;
				}
			}
			if (!conditionSatisfied) {
				tracer.debug("No satisfying minCardinality Property is found for " + owlClass.getLocalName());
				return false;
			}
		}
		if (!transformationCondition.minCardinalityValueIfCondition.equals(GlobalVariables.IF_NO_CONDITION)) {
			tracer.debug("Class has a minCardinality value condition for : "
					+ transformationCondition.minCardinalityValueIfCondition);
			Vector<OWLClass> superAndEquivalentClasses = GlobalOperations.getSuperAndEquivalentClassVector(owlClass,
					MinCardinalityRestrictionImpl.class.getName());
			boolean conditionSatisfied = false;
			for (int i = 0; i < superAndEquivalentClasses.size(); i++) {
				OWLClass owlSuperEqClass = superAndEquivalentClasses.get(i);
				MinCardinalityRestriction restriction = (MinCardinalityRestriction) owlSuperEqClass;
				String minValue = ((RDFSLiteral) restriction.getOWLMinCardinality()).getLexicalForm();
				if (GlobalOperations.isStringConditionSatisfied(transformationCondition.minCardinalityValueIfCondition,
						minValue, transformationCondition.minCardinalityValueIfConditionValue)) {
					conditionSatisfied = true;
					break;
				}
			}
			if (!conditionSatisfied) {
				tracer.debug("No satisfying minCardinality value is found for " + owlClass.getLocalName());
				return false;
			}
		}
		if (!transformationCondition.maxCardinalityPropertyIfCondition.equals(GlobalVariables.IF_NO_CONDITION)) {
			tracer.debug("Class has a maxCardinality property condition for : "
					+ transformationCondition.maxCardinalityPropertyIfCondition);
			Vector<OWLClass> superAndEquivalentClasses = GlobalOperations.getSuperAndEquivalentClassVector(owlClass,
					MaxCardinalityRestrictionImpl.class.getName());
			boolean conditionSatisfied = false;
			for (int i = 0; i < superAndEquivalentClasses.size(); i++) {
				OWLClass owlSuperEqClass = superAndEquivalentClasses.get(i);
				MaxCardinalityRestriction restriction = (MaxCardinalityRestriction) owlSuperEqClass;
				RDFProperty onProperty = restriction.getOWLOnProperty();
				if (GlobalOperations.isStringConditionSatisfied(
						transformationCondition.maxCardinalityPropertyIfCondition, onProperty.getLocalName(),
						transformationCondition.maxCardinalityPropertyIfConditionValue)) {
					conditionSatisfied = true;
					break;
				}
			}
			if (!conditionSatisfied) {
				tracer.debug("No satisfying maxCardinality property is found for " + owlClass.getLocalName());
				return false;
			}
		}
		if (!transformationCondition.maxCardinalityValueIfCondition.equals(GlobalVariables.IF_NO_CONDITION)) {
			tracer.debug("Class has a maxCardinality value condition for : "
					+ transformationCondition.maxCardinalityValueIfCondition);
			Vector<OWLClass> superAndEquivalentClasses = GlobalOperations.getSuperAndEquivalentClassVector(owlClass,
					MaxCardinalityRestrictionImpl.class.getName());
			boolean conditionSatisfied = false;
			for (int i = 0; i < superAndEquivalentClasses.size(); i++) {
				OWLClass owlSuperEqClass = superAndEquivalentClasses.get(i);
				MaxCardinalityRestriction restriction = (MaxCardinalityRestriction) owlSuperEqClass;
				String minValue = ((RDFSLiteral) restriction.getOWLMaxCardinality()).getLexicalForm();
				if (GlobalOperations.isStringConditionSatisfied(transformationCondition.maxCardinalityValueIfCondition,
						minValue, transformationCondition.maxCardinalityValueIfConditionValue)) {
					conditionSatisfied = true;
					break;
				}
			}
			if (!conditionSatisfied) {
				tracer.debug("No satisfying maxCardinality value is found for " + owlClass.getLocalName());
				return false;
			}
		}
		if (!transformationCondition.allValuesPropertyIfCondition.equals(GlobalVariables.IF_NO_CONDITION)) {
			tracer.debug("Class has a allValuesFrom property condition for : "
					+ transformationCondition.allValuesPropertyIfCondition);
			Vector<OWLClass> superAndEquivalentClasses = GlobalOperations.getSuperAndEquivalentClassVector(owlClass,
					AllValuesFromRestrictionImpl.class.getName());
			boolean conditionSatisfied = false;
			for (int i = 0; i < superAndEquivalentClasses.size(); i++) {
				OWLClass owlSuperEqClass = superAndEquivalentClasses.get(i);
				AllValuesFromRestriction restriction = (AllValuesFromRestriction) owlSuperEqClass;
				RDFProperty onProperty = restriction.getOWLOnProperty();
				if (GlobalOperations.isStringConditionSatisfied(transformationCondition.allValuesPropertyIfCondition,
						onProperty.getLocalName(), transformationCondition.allValuesPropertyIfConditionValue)) {
					conditionSatisfied = true;
					break;
				}
			}
			if (!conditionSatisfied) {
				tracer.debug("No satisfying allValuesFrom Property is found for " + owlClass.getLocalName());
				return false;
			}
		}
		if (!transformationCondition.allValuesValueIfCondition.equals(GlobalVariables.IF_NO_CONDITION)) {
			tracer.debug("Class has a allValuesFrom value condition for : "
					+ transformationCondition.allValuesValueIfCondition);
			Vector<OWLClass> superAndEquivalentClasses = GlobalOperations.getSuperAndEquivalentClassVector(owlClass,
					AllValuesFromRestrictionImpl.class.getName());
			boolean conditionSatisfied = false;
			for (int i = 0; i < superAndEquivalentClasses.size(); i++) {
				OWLClass owlSuperEqClass = superAndEquivalentClasses.get(i);
				AllValuesFromRestriction restriction = (AllValuesFromRestriction) owlSuperEqClass;
				RDFSResource allValuesFromClass = restriction.getOWLAllValuesFrom();
				if (!allValuesFromClass.getClass().getName().equals(OWLClassImpl.class.getName()))
					continue;
				if (GlobalOperations.isStringConditionSatisfied(transformationCondition.allValuesValueIfCondition,
						allValuesFromClass.getLocalName(), transformationCondition.allValuesValueIfConditionValue)) {
					conditionSatisfied = true;
					break;
				}
			}
			if (!conditionSatisfied) {
				tracer.debug("No satisfying allValuesFrom value is found for " + owlClass.getLocalName());
				return false;
			}
		}
		if (!transformationCondition.hasValuePropertyIfCondition.equals(GlobalVariables.IF_NO_CONDITION)) {
			tracer.debug("Class has a hasValue property condition for : "
					+ transformationCondition.hasValuePropertyIfCondition);
			Vector<OWLClass> superAndEquivalentClasses = GlobalOperations.getSuperAndEquivalentClassVector(owlClass,
					HasValueRestrictionImpl.class.getName());
			boolean conditionSatisfied = false;
			for (int i = 0; i < superAndEquivalentClasses.size(); i++) {
				OWLClass owlSuperEqClass = superAndEquivalentClasses.get(i);
				HasValueRestriction restriction = (HasValueRestriction) owlSuperEqClass;
				RDFProperty onProperty = restriction.getOWLOnProperty();
				if (GlobalOperations.isStringConditionSatisfied(transformationCondition.hasValuePropertyIfCondition,
						onProperty.getLocalName(), transformationCondition.hasValuePropertyIfConditionValue)) {
					conditionSatisfied = true;
					break;
				}
			}
			if (!conditionSatisfied) {
				tracer.debug("No satisfying hasValue Property is found for " + owlClass.getLocalName());
				return false;
			}
		}
		if (!transformationCondition.hasValueValueIfCondition.equals(GlobalVariables.IF_NO_CONDITION)) {
			tracer.debug(
					"Class has a hasValue value condition for : " + transformationCondition.hasValueValueIfCondition);
			Vector<OWLClass> superAndEquivalentClasses = GlobalOperations.getSuperAndEquivalentClassVector(owlClass,
					HasValueRestrictionImpl.class.getName());
			boolean conditionSatisfied = false;
			for (int i = 0; i < superAndEquivalentClasses.size(); i++) {
				OWLClass owlSuperEqClass = superAndEquivalentClasses.get(i);
				HasValueRestriction restriction = (HasValueRestriction) owlSuperEqClass;
				RDFSResource valueClass = restriction.getOWLHasValue();
				if (!valueClass.getClass().getName().equals(TypedLiteralImpl.class.getName()))
					continue;
				TypedLiteralImpl typedLiteral = (TypedLiteralImpl) valueClass;
				if (GlobalOperations.isStringConditionSatisfied(transformationCondition.hasValueValueIfCondition,
						typedLiteral.getLexicalForm(), transformationCondition.hasValueValueIfConditionValue)) {
					conditionSatisfied = true;
					break;
				}
			}
			if (!conditionSatisfied) {
				tracer.debug("No satisfying hasValue values is found for " + owlClass.getLocalName());
				return false;
			}
		}
		if (!transformationCondition.someValuesPropertyIfCondition.equals(GlobalVariables.IF_NO_CONDITION)) {
			tracer.debug("Class has a someValuesFrom property condition for : "
					+ transformationCondition.someValuesPropertyIfCondition);
			Vector<OWLClass> superAndEquivalentClasses = GlobalOperations.getSuperAndEquivalentClassVector(owlClass,
					SomeValuesFromRestrictionImpl.class.getName());
			boolean conditionSatisfied = false;
			for (int i = 0; i < superAndEquivalentClasses.size(); i++) {
				OWLClass owlSuperEqClass = superAndEquivalentClasses.get(i);
				SomeValuesFromRestriction restriction = (SomeValuesFromRestriction) owlSuperEqClass;
				RDFProperty onProperty = restriction.getOWLOnProperty();
				if (GlobalOperations.isStringConditionSatisfied(transformationCondition.someValuesPropertyIfCondition,
						onProperty.getLocalName(), transformationCondition.someValuesPropertyIfConditionValue)) {
					conditionSatisfied = true;
					break;
				}
			}
			if (!conditionSatisfied) {
				tracer.debug("No satisfying someValuesFrom property  is found for " + owlClass.getLocalName());
				return false;
			}
		}
		if (!transformationCondition.someValuesValueIfCondition.equals(GlobalVariables.IF_NO_CONDITION)) {
			tracer.debug("Class has a someValuesFrom value condition for : "
					+ transformationCondition.someValuesValueIfCondition);
			Vector<OWLClass> superAndEquivalentClasses = GlobalOperations.getSuperAndEquivalentClassVector(owlClass,
					SomeValuesFromRestrictionImpl.class.getName());
			boolean conditionSatisfied = false;
			for (int i = 0; i < superAndEquivalentClasses.size(); i++) {
				OWLClass owlSuperEqClass = superAndEquivalentClasses.get(i);
				SomeValuesFromRestriction restriction = (SomeValuesFromRestriction) owlSuperEqClass;
				RDFSResource someValuesFromClass = restriction.getOWLSomeValuesFrom();
				if (someValuesFromClass.getClass().getName().equals(OWLClassImpl.class.getName())) {
					if (GlobalOperations.isStringConditionSatisfied(transformationCondition.someValuesValueIfCondition,
							someValuesFromClass.getLocalName(),
							transformationCondition.someValuesValueIfConditionValue)) {
						conditionSatisfied = true;
						break;
					}
				} else if (someValuesFromClass.getClass().getName()
						.equals(EnumeratedClassImpl.class.getName())) {
					EnumeratedClass enumeratedClass = (EnumeratedClass) someValuesFromClass;
					Iterator<?> oneOfList = enumeratedClass.getOWLOneOf().iterator();
					while (oneOfList.hasNext()) {
						Individual individual = (Individual) oneOfList.next();
						if (GlobalOperations.isStringConditionSatisfied(
								transformationCondition.someValuesValueIfCondition, individual.getLocalName(),
								transformationCondition.someValuesValueIfConditionValue)) {
							conditionSatisfied = true;
							i = superAndEquivalentClasses.size();
							break;
						}
					}
				}
			}
			if (!conditionSatisfied) {
				tracer.debug("No satisfying someValuesFrom value is found for " + owlClass.getLocalName());
				return false;
			}
		}
		tracer.debug("Conditions are satisfied for the class " + owlClass.getLocalName());
		return true;
	}

}
