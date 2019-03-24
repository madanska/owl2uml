package owl2uml.transformation.mapping.processor;

import java.util.Iterator;
import java.util.Vector;

import org.eclipse.eodm.owl.AllValuesFromRestriction;
import org.eclipse.eodm.owl.EnumeratedClass;
import org.eclipse.eodm.owl.HasValueRestriction;
import org.eclipse.eodm.owl.Individual;
import org.eclipse.eodm.owl.MaxCardinalityRestriction;
import org.eclipse.eodm.owl.MinCardinalityRestriction;
import org.eclipse.eodm.owl.OWLClass;
import org.eclipse.eodm.owl.SomeValuesFromRestriction;
import org.eclipse.eodm.owl.impl.AllValuesFromRestrictionImpl;
import org.eclipse.eodm.owl.impl.ComplementClassImpl;
import org.eclipse.eodm.owl.impl.EnumeratedClassImpl;
import org.eclipse.eodm.owl.impl.HasValueRestrictionImpl;
import org.eclipse.eodm.owl.impl.IndividualImpl;
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
import owl2uml.transformation.mapping.OWLClassUMLClassTransformationMapping;
import owl2uml.umlcomponents.UMLClass;
import owl2uml.umlcomponents.UMLModel;

/**
 * @author Ozer Ozdikis
 * @version June 2006
 */
public class OWLClassUMLClassTransformationMappingProcessor extends UMLClassTransformationMappingProcessor {
	protected OWLClass owlClass;
	protected OWLClassUMLClassTransformationMapping transformationMapping;

	
	public OWLClassUMLClassTransformationMappingProcessor(OWLClass owlClass, UMLModel umlModel, OWLClassUMLClassTransformationMapping transformationMapping) {
		super(umlModel);
		this.transformationMapping = transformationMapping;
		this.owlClass = owlClass;
	}
	
	public void runMappingProcessor() {
		if(transformationMapping.getOwlVariable1().equals(GlobalVariables.OWL_SUPERCLASS_NAME))
			runMappingForSuperClass();
		else if(transformationMapping.getOwlVariable1().equals(GlobalVariables.OWL_COMPLEMENT_OF))
			runMappingForComplementOf();
		else if(transformationMapping.getOwlVariable1().equals(GlobalVariables.OWL_INTERSECTION_OF))
			runMappingForIntersectionOf();
		else if(transformationMapping.getOwlVariable1().equals(GlobalVariables.OWL_UNION_OF))
			runMappingForUnionOf();
		else if(transformationMapping.getOwlVariable1().equals(GlobalVariables.OWL_MINCARDINALITY_ONPROPERTY))
			runMappingForMinCardProperty();
		else if(transformationMapping.getOwlVariable1().equals(GlobalVariables.OWL_MINCARDINALITY_VALUE))
			runMappingForMinCardValue();
		else if(transformationMapping.getOwlVariable1().equals(GlobalVariables.OWL_MAXCARDINALITY_ONPROPERTY))
			runMappingForMaxCardProperty();
		else if(transformationMapping.getOwlVariable1().equals(GlobalVariables.OWL_MAXCARDINALITY_VALUE))
			runMappingForMaxCardValue();
		else if(transformationMapping.getOwlVariable1().equals(GlobalVariables.OWL_ALLVALUESFROM_ONPROPERTY))
			runMappingForAllValuesFromProperty();
		else if(transformationMapping.getOwlVariable1().equals(GlobalVariables.OWL_ALLVALUESFROM_VALUE))
			runMappingForAllValuesFromValue();
		else if(transformationMapping.getOwlVariable1().equals(GlobalVariables.OWL_SOMEVALUESFROM_ONPROPERTY))
			runMappingForSomeValuesFromProperty();
		else if(transformationMapping.getOwlVariable1().equals(GlobalVariables.OWL_SOMEVALUESFROM_VALUE))
			runMappingForSomeValuesFromValue();
		else if(transformationMapping.getOwlVariable1().equals(GlobalVariables.OWL_HASVALUE_ONPROPERTY))
			runMappingForHasValueProperty();
		else if(transformationMapping.getOwlVariable1().equals(GlobalVariables.OWL_HASVALUE_VALUE))
			runMappingForHasValueValue();
		else if(transformationMapping.getOwlVariable1().equals(GlobalVariables.OWL_ONE_OF))
			runMappingForOneOf();
	}
	
	private void runMappingForSuperClass() {
		Iterator<?> superClassList = owlClass.getRDFSSubClassOf().iterator();
		UMLClass umlClass = umlModel.getUMLClass(owlClass.getLocalName());
		while(superClassList.hasNext()) {
			OWLClass owlSuperClass = (OWLClass) superClassList.next();
			if(!owlSuperClass.getClass().getName().equals(OWLClassImpl.class.getName()))
				continue;
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getIfCondition1(), owlSuperClass.getLocalName(), transformationMapping.getIfConditionValue1()))
				continue;
			if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_SUPER_CLASS_NAME)) {
				addGeneralization(umlClass, owlSuperClass.getLocalName());
			} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_ATTRIBUTE)) {
				addAttribute(umlClass, owlSuperClass.getLocalName());
			} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_OPERATION_NAME)) {
				addOperation(umlClass, owlSuperClass.getLocalName());
			}
		}
	}

	private void runMappingForComplementOf() {
		Vector<OWLClass> superAndEquivalentClasses = getSuperAndEquivalentClassVector(owlClass, ComplementClassImpl.class.getName());
		UMLClass umlClass = umlModel.getUMLClass(owlClass.getLocalName());
		for(int i=0; i<superAndEquivalentClasses.size(); i++) {
			OWLClass owlSuperEqClass = superAndEquivalentClasses.get(i);
			ComplementClassImpl owlComplementClass = (ComplementClassImpl)owlSuperEqClass;
			OWLClass owlTargetClass = owlComplementClass.getOWLComplementOf();
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getIfCondition1(), owlTargetClass.getLocalName(), transformationMapping.getIfConditionValue1()))
				continue;
			if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_SUPER_CLASS_NAME)) {
				addGeneralization(umlClass, owlTargetClass.getLocalName());
			} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_ATTRIBUTE)) {
				addAttribute(umlClass, owlTargetClass.getLocalName());
			} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_OPERATION_NAME)) {
				addOperation(umlClass, owlTargetClass.getLocalName());
			}
		}
	}

	private void runMappingForIntersectionOf() {
		Vector<OWLClass> superAndEquivalentClasses = getSuperAndEquivalentClassVector(owlClass, IntersectionClassImpl.class.getName());
		UMLClass umlClass = umlModel.getUMLClass(owlClass.getLocalName());
		for(int i=0; i<superAndEquivalentClasses.size(); i++) {
			OWLClass owlSuperEqClass = superAndEquivalentClasses.get(i);
			IntersectionClassImpl owlIntersectionClass = (IntersectionClassImpl)owlSuperEqClass;
			Iterator<?> intersectionClasses = owlIntersectionClass.getOWLIntersectionOf().iterator();
			while(intersectionClasses.hasNext()) {
				OWLClass intersectionClass = (OWLClass) intersectionClasses.next();
				if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getIfCondition1(), intersectionClass.getLocalName(), transformationMapping.getIfConditionValue1()))
					continue;
				if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_SUPER_CLASS_NAME)) {
					addGeneralization(umlClass, intersectionClass.getLocalName());
				} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_ATTRIBUTE)) {
					addAttribute(umlClass, intersectionClass.getLocalName());
				} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_OPERATION_NAME)) {
					addOperation(umlClass, intersectionClass.getLocalName());
				}
			}
		}
	}

	private void runMappingForUnionOf() {
		Vector<OWLClass> superAndEquivalentClasses = getSuperAndEquivalentClassVector(owlClass, UnionClassImpl.class.getName());
		UMLClass umlClass = umlModel.getUMLClass(owlClass.getLocalName());
		for(int i=0; i<superAndEquivalentClasses.size(); i++) {
			OWLClass owlSuperEqClass = superAndEquivalentClasses.get(i);
			UnionClassImpl owlUnionClass = (UnionClassImpl)owlSuperEqClass;
			Iterator<?> unionClasses = owlUnionClass.getOWLUnionOf().iterator();
			while(unionClasses.hasNext()) {
				OWLClass unionClass = (OWLClass) unionClasses.next();
				if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getIfCondition1(), unionClass.getLocalName(), transformationMapping.getIfConditionValue1()))
					continue;
				if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_SUPER_CLASS_NAME)) {
					addGeneralization(umlClass, unionClass.getLocalName());
				} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_ATTRIBUTE)) {
					addAttribute(umlClass, unionClass.getLocalName());
				} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_OPERATION_NAME)) {
					addOperation(umlClass, unionClass.getLocalName());
				}
			}
		}
	}

	private void runMappingForMinCardProperty() {
		Vector<OWLClass> superAndEquivalentClasses = getSuperAndEquivalentClassVector(owlClass, MinCardinalityRestrictionImpl.class.getName());
		UMLClass umlClass = umlModel.getUMLClass(owlClass.getLocalName());
		for(int i=0; i<superAndEquivalentClasses.size(); i++) {
			OWLClass owlSuperEqClass = superAndEquivalentClasses.get(i);
			MinCardinalityRestriction restriction = (MinCardinalityRestriction)owlSuperEqClass;
			RDFProperty onProperty = restriction.getOWLOnProperty();
			String minValue = ((RDFSLiteral)restriction.getOWLMinCardinality()).getLexicalForm();
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getIfCondition1(), onProperty.getLocalName(), transformationMapping.getIfConditionValue1()))
				continue;
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getIfCondition2(), minValue, transformationMapping.getIfConditionValue2()))
				continue;
			if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_SUPER_CLASS_NAME)) {
				addGeneralization(umlClass, onProperty.getLocalName());
			} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_ATTRIBUTE)) {
				addAttribute(umlClass, onProperty.getLocalName());
			} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_OPERATION_NAME) && transformationMapping.isOperationParameter()) {
				addOperation(umlClass, onProperty.getLocalName(), minValue, transformationMapping.getParameterKind());
			} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_OPERATION_NAME) && (!transformationMapping.isOperationParameter())) {
				addOperation(umlClass, onProperty.getLocalName());
			}
		}
	}
	
	private void runMappingForMinCardValue() {
		Vector<OWLClass> superAndEquivalentClasses = getSuperAndEquivalentClassVector(owlClass, MinCardinalityRestrictionImpl.class.getName());
		UMLClass umlClass = umlModel.getUMLClass(owlClass.getLocalName());
		for(int i=0; i<superAndEquivalentClasses.size(); i++) {
			OWLClass owlSuperEqClass = superAndEquivalentClasses.get(i);
			MinCardinalityRestriction restriction = (MinCardinalityRestriction)owlSuperEqClass;
			RDFProperty onProperty = restriction.getOWLOnProperty();
			String minValue = ((RDFSLiteral)restriction.getOWLMinCardinality()).getLexicalForm();
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getIfCondition1(), minValue, transformationMapping.getIfConditionValue1()))
				continue;
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getIfCondition2(), onProperty.getLocalName(), transformationMapping.getIfConditionValue2()))
				continue;
			if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_SUPER_CLASS_NAME)) {
				addGeneralization(umlClass, minValue);
			} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_ATTRIBUTE)) {
				addAttribute(umlClass, minValue);
			} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_OPERATION_NAME) && transformationMapping.isOperationParameter()) {
				addOperation(umlClass, minValue, onProperty.getLocalName(), transformationMapping.getParameterKind());
			} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_OPERATION_NAME) && (!transformationMapping.isOperationParameter())) {
				addOperation(umlClass, minValue);
			}
		}
	}
	
	private void runMappingForMaxCardProperty() {
		Vector<OWLClass> superAndEquivalentClasses = getSuperAndEquivalentClassVector(owlClass, MaxCardinalityRestrictionImpl.class.getName());
		UMLClass umlClass = umlModel.getUMLClass(owlClass.getLocalName());
		for(int i=0; i<superAndEquivalentClasses.size(); i++) {
			OWLClass owlSuperEqClass = superAndEquivalentClasses.get(i);
			MaxCardinalityRestriction restriction = (MaxCardinalityRestriction)owlSuperEqClass;
			RDFProperty onProperty = restriction.getOWLOnProperty();
			String maxValue = ((RDFSLiteral)restriction.getOWLMaxCardinality()).getLexicalForm();
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getIfCondition1(), onProperty.getLocalName(), transformationMapping.getIfConditionValue1()))
				continue;
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getIfCondition2(), maxValue, transformationMapping.getIfConditionValue2()))
				continue;
			if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_SUPER_CLASS_NAME)) {
				addGeneralization(umlClass, onProperty.getLocalName());
			} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_ATTRIBUTE)) {
				addAttribute(umlClass, onProperty.getLocalName());
			} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_OPERATION_NAME) && transformationMapping.isOperationParameter()) {
				addOperation(umlClass, onProperty.getLocalName(), maxValue, transformationMapping.getParameterKind());
			} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_OPERATION_NAME) && (!transformationMapping.isOperationParameter())) {
				addOperation(umlClass, onProperty.getLocalName());
			}
		}
	}

	private void runMappingForMaxCardValue() {
		Vector<OWLClass> superAndEquivalentClasses = getSuperAndEquivalentClassVector(owlClass, MaxCardinalityRestrictionImpl.class.getName());
		UMLClass umlClass = umlModel.getUMLClass(owlClass.getLocalName());
		for(int i=0; i<superAndEquivalentClasses.size(); i++) {
			OWLClass owlSuperEqClass = superAndEquivalentClasses.get(i);
			MaxCardinalityRestriction restriction = (MaxCardinalityRestriction)owlSuperEqClass;
			RDFProperty onProperty = restriction.getOWLOnProperty();
			String maxValue = ((RDFSLiteral)restriction.getOWLMaxCardinality()).getLexicalForm();
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getIfCondition1(), maxValue, transformationMapping.getIfConditionValue1()))
				continue;
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getIfCondition2(), onProperty.getLocalName(), transformationMapping.getIfConditionValue2()))
				continue;
			if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_SUPER_CLASS_NAME)) {
				addGeneralization(umlClass, maxValue);
			} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_ATTRIBUTE)) {
				addAttribute(umlClass, maxValue);
			} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_OPERATION_NAME) && transformationMapping.isOperationParameter()) {
				addOperation(umlClass, maxValue, onProperty.getLocalName(), transformationMapping.getParameterKind());
			} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_OPERATION_NAME) && (!transformationMapping.isOperationParameter())) {
				addOperation(umlClass, maxValue);
			}
		}
	}

	private void runMappingForAllValuesFromProperty() {
		Vector<OWLClass> superAndEquivalentClasses = getSuperAndEquivalentClassVector(owlClass, AllValuesFromRestrictionImpl.class.getName());
		UMLClass umlClass = umlModel.getUMLClass(owlClass.getLocalName());
		for(int i=0; i<superAndEquivalentClasses.size(); i++) {
			OWLClass owlSuperEqClass = superAndEquivalentClasses.get(i);
			AllValuesFromRestriction restriction = (AllValuesFromRestriction)owlSuperEqClass;
			RDFProperty onProperty = restriction.getOWLOnProperty();
			RDFSResource allValuesFromClass = restriction.getOWLAllValuesFrom();  
			if(!allValuesFromClass.getClass().getName().equals(OWLClassImpl.class.getName()))
				continue;
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getIfCondition1(), onProperty.getLocalName(), transformationMapping.getIfConditionValue1()))
				continue;
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getIfCondition2(), allValuesFromClass.getLocalName(), transformationMapping.getIfConditionValue2()))
				continue;
			if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_SUPER_CLASS_NAME)) {
				addGeneralization(umlClass, onProperty.getLocalName());
			} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_ATTRIBUTE)) {
				addAttribute(umlClass, onProperty.getLocalName());
			} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_OPERATION_NAME) && transformationMapping.isOperationParameter()) {
				if(!allValuesFromClass.getClass().getName().equals(OWLClassImpl.class.getName()))
					continue;
				addOperation(umlClass, onProperty.getLocalName(), allValuesFromClass.getLocalName(), transformationMapping.getParameterKind());
			} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_OPERATION_NAME) && (!transformationMapping.isOperationParameter())) {
				addOperation(umlClass, onProperty.getLocalName());
			}
		}
	}

	private void runMappingForAllValuesFromValue() {
		Vector<OWLClass> superAndEquivalentClasses = getSuperAndEquivalentClassVector(owlClass, AllValuesFromRestrictionImpl.class.getName());
		UMLClass umlClass = umlModel.getUMLClass(owlClass.getLocalName());
		for(int i=0; i<superAndEquivalentClasses.size(); i++) {
			OWLClass owlSuperEqClass = superAndEquivalentClasses.get(i);
			AllValuesFromRestriction restriction = (AllValuesFromRestriction)owlSuperEqClass;
			RDFProperty onProperty = restriction.getOWLOnProperty();
			RDFSResource allValuesFromClass = restriction.getOWLAllValuesFrom();  
			if(!allValuesFromClass.getClass().getName().equals(OWLClassImpl.class.getName()))
				continue;
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getIfCondition1(), allValuesFromClass.getLocalName(), transformationMapping.getIfConditionValue1()))
				continue;
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getIfCondition2(), onProperty.getLocalName(), transformationMapping.getIfConditionValue2()))
				continue;
			if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_SUPER_CLASS_NAME)) {
				addGeneralization(umlClass, allValuesFromClass.getLocalName());
			} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_ATTRIBUTE)) {
				addAttribute(umlClass, allValuesFromClass.getLocalName());
			} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_OPERATION_NAME) && transformationMapping.isOperationParameter()) {
				addOperation(umlClass, allValuesFromClass.getLocalName(), onProperty.getLocalName(), transformationMapping.getParameterKind());
			} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_OPERATION_NAME) && (!transformationMapping.isOperationParameter())) {
				addOperation(umlClass, allValuesFromClass.getLocalName());
			}
		}
	}

	private void runMappingForSomeValuesFromProperty() {
		Vector<OWLClass> superAndEquivalentClasses = getSuperAndEquivalentClassVector(owlClass, SomeValuesFromRestrictionImpl.class.getName());
		UMLClass umlClass = umlModel.getUMLClass(owlClass.getLocalName());
		for(int i=0; i<superAndEquivalentClasses.size(); i++) {
			OWLClass owlSuperEqClass = superAndEquivalentClasses.get(i);
			SomeValuesFromRestriction restriction = (SomeValuesFromRestriction)owlSuperEqClass;
			RDFProperty onProperty = restriction.getOWLOnProperty();
			RDFSResource someValuesFromClass = restriction.getOWLSomeValuesFrom();  
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getIfCondition1(), onProperty.getLocalName(), transformationMapping.getIfConditionValue1()))
				continue;
			if(someValuesFromClass.getClass().getName().equals(OWLClassImpl.class.getName())) {
				if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getIfCondition2(), someValuesFromClass.getLocalName(), transformationMapping.getIfConditionValue2()))
					continue;
			} else if(someValuesFromClass.getClass().getName().equals(EnumeratedClassImpl.class.getName())) {
					Iterator<?> oneOfList = ((EnumeratedClass)someValuesFromClass).getOWLOneOf().iterator();
					boolean satisfies = false;
					while(oneOfList.hasNext()) {
						Individual individual = (Individual) oneOfList.next();
						if(GlobalOperations.isStringConditionSatisfied(transformationMapping.getIfCondition2(), individual.getLocalName(), transformationMapping.getIfConditionValue2())) {
							satisfies = true;
							break;
						}
					}
					if(!satisfies)
						continue;
			} else
				continue;
			if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_SUPER_CLASS_NAME)) {
				addGeneralization(umlClass, onProperty.getLocalName());
			} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_ATTRIBUTE)) {
				addAttribute(umlClass, onProperty.getLocalName());
			} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_OPERATION_NAME) && transformationMapping.isOperationParameter()) {
				if(someValuesFromClass.getClass().getName().equals(OWLClassImpl.class.getName()))
					addOperation(umlClass, onProperty.getLocalName(), someValuesFromClass.getLocalName(), transformationMapping.getParameterKind());
				else if(someValuesFromClass.getClass().getName().equals(EnumeratedClassImpl.class.getName())) {
					EnumeratedClass enumeratedClass = (EnumeratedClass)someValuesFromClass;
					Iterator<?> oneOfList = enumeratedClass.getOWLOneOf().iterator();
					Vector<String> parameters = new Vector<String>();
					while(oneOfList.hasNext()) {
						Individual individual = (Individual) oneOfList.next();
						parameters.add(individual.getLocalName());
					}
					addOperation(umlClass, onProperty.getLocalName(), parameters, transformationMapping.getParameterKind());
				} else
					continue;
			} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_OPERATION_NAME) && (!transformationMapping.isOperationParameter())) {
				addOperation(umlClass, onProperty.getLocalName());
			}
		}
	}

	private void runMappingForSomeValuesFromValue() {
		Vector<OWLClass> superAndEquivalentClasses = getSuperAndEquivalentClassVector(owlClass, SomeValuesFromRestrictionImpl.class.getName());
		UMLClass umlClass = umlModel.getUMLClass(owlClass.getLocalName());
		for(int i=0; i<superAndEquivalentClasses.size(); i++) {
			OWLClass owlSuperEqClass = superAndEquivalentClasses.get(i);
			SomeValuesFromRestriction restriction = (SomeValuesFromRestriction)owlSuperEqClass;
			RDFProperty onProperty = restriction.getOWLOnProperty();
			RDFSResource someValuesFromClass = restriction.getOWLSomeValuesFrom();
			if(someValuesFromClass.getClass().getName().equals(OWLClassImpl.class.getName())) {
				if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getIfCondition1(), someValuesFromClass.getLocalName(), transformationMapping.getIfConditionValue1()))
					continue;
				if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getIfCondition2(), onProperty.getLocalName(), transformationMapping.getIfConditionValue2()))
					continue;
				if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_SUPER_CLASS_NAME)) {
					addGeneralization(umlClass, someValuesFromClass.getLocalName());
				} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_ATTRIBUTE)) {
					addAttribute(umlClass, someValuesFromClass.getLocalName());
				} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_OPERATION_NAME) && transformationMapping.isOperationParameter()) {
					addOperation(umlClass, someValuesFromClass.getLocalName(), onProperty.getLocalName(), transformationMapping.getParameterKind());
				} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_OPERATION_NAME) && (!transformationMapping.isOperationParameter())) {
					addOperation(umlClass, someValuesFromClass.getLocalName());
				}
			} else if(someValuesFromClass.getClass().getName().equals(EnumeratedClassImpl.class.getName())) {
				EnumeratedClass enumeratedClass = (EnumeratedClass)someValuesFromClass;
				Iterator<?> oneOfList = enumeratedClass.getOWLOneOf().iterator();
				while(oneOfList.hasNext()) {
					Individual individual = (Individual) oneOfList.next();
					if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getIfCondition1(), individual.getLocalName(), transformationMapping.getIfConditionValue1()))
						continue;
					if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getIfCondition2(), onProperty.getLocalName(), transformationMapping.getIfConditionValue2()))
						continue;
					if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_SUPER_CLASS_NAME)) {
						addGeneralization(umlClass, individual.getLocalName());
					} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_ATTRIBUTE)) {
						addAttribute(umlClass, individual.getLocalName());
					} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_OPERATION_NAME) && transformationMapping.isOperationParameter()) {
						addOperation(umlClass, individual.getLocalName(), onProperty.getLocalName(), transformationMapping.getParameterKind());
					} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_OPERATION_NAME) && (!transformationMapping.isOperationParameter())) {
						addOperation(umlClass, individual.getLocalName());
					}
				}
			}
		}
	}
	
	private void runMappingForHasValueProperty() {
		Vector<OWLClass> superAndEquivalentClasses = getSuperAndEquivalentClassVector(owlClass, HasValueRestrictionImpl.class.getName());
		UMLClass umlClass = umlModel.getUMLClass(owlClass.getLocalName());
		for(int i=0; i<superAndEquivalentClasses.size(); i++) {
			OWLClass owlSuperEqClass = superAndEquivalentClasses.get(i);
			HasValueRestriction restriction = (HasValueRestriction)owlSuperEqClass;
			RDFProperty onProperty = restriction.getOWLOnProperty();
			RDFSResource valueClass = restriction.getOWLHasValue();  
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getIfCondition1(), onProperty.getLocalName(), transformationMapping.getIfConditionValue1()))
				continue;
			if(valueClass.getClass().getName().equals(TypedLiteralImpl.class.getName())) {
				TypedLiteralImpl typedLiteral = (TypedLiteralImpl) valueClass;
				if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getIfCondition2(), typedLiteral.getLexicalForm(), transformationMapping.getIfConditionValue2()))
					continue;
			} else 
				continue;
			if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_SUPER_CLASS_NAME)) {
				addGeneralization(umlClass, onProperty.getLocalName());
			} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_ATTRIBUTE)) {
				addAttribute(umlClass, onProperty.getLocalName());
			} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_OPERATION_NAME) && transformationMapping.isOperationParameter()) {
				if(valueClass.getClass().getName().equals(TypedLiteralImpl.class.getName())) {
					TypedLiteralImpl typedLiteral = (TypedLiteralImpl) valueClass;
					addOperation(umlClass, onProperty.getLocalName(), typedLiteral.getLexicalForm(), transformationMapping.getParameterKind());
				} else
					continue;
			} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_OPERATION_NAME) && (!transformationMapping.isOperationParameter())) {
				addOperation(umlClass, onProperty.getLocalName());
			}
		}
	}

	private void runMappingForHasValueValue() {
		Vector<OWLClass> superAndEquivalentClasses = getSuperAndEquivalentClassVector(owlClass, HasValueRestrictionImpl.class.getName());
		UMLClass umlClass = umlModel.getUMLClass(owlClass.getLocalName());
		for(int i=0; i<superAndEquivalentClasses.size(); i++) {
			OWLClass owlSuperEqClass = superAndEquivalentClasses.get(i);
			HasValueRestriction restriction = (HasValueRestriction)owlSuperEqClass;
			RDFProperty onProperty = restriction.getOWLOnProperty();
			RDFSResource valueClass = restriction.getOWLHasValue();
			if(valueClass.getClass().getName().equals(TypedLiteralImpl.class.getName())) {
				TypedLiteralImpl typedLiteral = (TypedLiteralImpl) valueClass;
				if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getIfCondition1(), typedLiteral.getLexicalForm(), transformationMapping.getIfConditionValue1()))
					continue;
				if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getIfCondition2(), onProperty.getLocalName(), transformationMapping.getIfConditionValue2()))
					continue;
				if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_SUPER_CLASS_NAME)) {
					addGeneralization(umlClass, typedLiteral.getLexicalForm());
				} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_ATTRIBUTE)) {
					addAttribute(umlClass, typedLiteral.getLexicalForm());
				} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_OPERATION_NAME) && transformationMapping.isOperationParameter()) {
					addOperation(umlClass, typedLiteral.getLexicalForm(), onProperty.getLocalName(), transformationMapping.getParameterKind());
				} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_OPERATION_NAME) && (!transformationMapping.isOperationParameter())) {
					addOperation(umlClass, typedLiteral.getLexicalForm());
				}
			} else
				continue;
		}
	}

	private void runMappingForOneOf() {
		if(!owlClass.getClass().getName().equals(EnumeratedClassImpl.class.getName())) {
			return;
		}
		EnumeratedClassImpl enumClass = (EnumeratedClassImpl)owlClass;
		UMLClass umlClass = umlModel.getUMLClass(owlClass.getLocalName());
		Iterator<?> oneOfList = enumClass.getOWLOneOf().iterator();
		while(oneOfList.hasNext()) {
			Object obj = oneOfList.next();
			if(obj.getClass().getName().equals(IndividualImpl.class.getName())) {
				IndividualImpl individual = (IndividualImpl)obj;
				if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getIfCondition1(), individual.getLocalName(), transformationMapping.getIfConditionValue1()))
					continue;
				if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_SUPER_CLASS_NAME)) {
					addGeneralization(umlClass, individual.getLocalName());
				} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_ATTRIBUTE)) {
					addAttribute(umlClass, individual.getLocalName());
				} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_OPERATION_NAME)) {
					addOperation(umlClass, individual.getLocalName());
				}
			}
		}

	}
}
