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
import owl2uml.transformation.mapping.OWLClassUMLAssociationTransformationMapping;
import owl2uml.umlcomponents.UMLClass;
import owl2uml.umlcomponents.UMLModel;

/**
 * @author Ozer Ozdikis
 * @version June 2006
 */
public class OWLClassUMLAssociationTransformationMappingProcessor extends UMLAssociationTransformationMappingProcessor {
	protected OWLClass owlClass;
	protected OWLClassUMLAssociationTransformationMapping transformationMapping;
	
	public OWLClassUMLAssociationTransformationMappingProcessor(OWLClass owlClass, UMLModel umlModel, OWLClassUMLAssociationTransformationMapping transformationMapping) {
		super(umlModel);
		this.owlClass = owlClass;
		this.transformationMapping = transformationMapping;
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
			addAssociation(umlClass, owlSuperClass.getLocalName(), transformationMapping.getUmlVariable());
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
			addAssociation(umlClass, owlTargetClass.getLocalName(), transformationMapping.getUmlVariable());
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
				addAssociation(umlClass, intersectionClass.getLocalName(), transformationMapping.getUmlVariable());
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
				addAssociation(umlClass, unionClass.getLocalName(), transformationMapping.getUmlVariable());
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
			if(transformationMapping.isMultiplicityIncluded()) {
				addAssociation(umlClass, onProperty.getLocalName(), transformationMapping.getUmlVariable(), minValue, transformationMapping.getMultiplicityType(), transformationMapping.getMultiplicityEnd());
			} else {
				addAssociation(umlClass, onProperty.getLocalName(), transformationMapping.getUmlVariable());
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
			if(transformationMapping.isMultiplicityIncluded()) {
				addAssociation(umlClass, minValue, transformationMapping.getUmlVariable(), minValue, transformationMapping.getMultiplicityType(), transformationMapping.getMultiplicityEnd());
			} else {
				addAssociation(umlClass, minValue, transformationMapping.getUmlVariable());
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
			if(transformationMapping.isMultiplicityIncluded()) {
				addAssociation(umlClass, onProperty.getLocalName(), transformationMapping.getUmlVariable(), maxValue, transformationMapping.getMultiplicityType(), transformationMapping.getMultiplicityEnd());
			} else {
				addAssociation(umlClass, onProperty.getLocalName(), transformationMapping.getUmlVariable());
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
			if(transformationMapping.isMultiplicityIncluded()) {
				addAssociation(umlClass, maxValue, transformationMapping.getUmlVariable(), maxValue, transformationMapping.getMultiplicityType(), transformationMapping.getMultiplicityEnd());
			} else {
				addAssociation(umlClass, maxValue, transformationMapping.getUmlVariable());
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
			addAssociation(umlClass, onProperty.getLocalName(), transformationMapping.getUmlVariable());
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
			addAssociation(umlClass, allValuesFromClass.getLocalName(), transformationMapping.getUmlVariable());
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
			addAssociation(umlClass, onProperty.getLocalName(), transformationMapping.getUmlVariable());
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
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getIfCondition2(), onProperty.getLocalName(), transformationMapping.getIfConditionValue2()))
				continue;
			if(someValuesFromClass.getClass().getName().equals(OWLClassImpl.class.getName())) {
				if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getIfCondition1(), someValuesFromClass.getLocalName(), transformationMapping.getIfConditionValue1()))
					continue;
				addAssociation(umlClass, someValuesFromClass.getLocalName(), transformationMapping.getUmlVariable());
			} else if(someValuesFromClass.getClass().getName().equals(EnumeratedClassImpl.class.getName())) {
				EnumeratedClass enumeratedClass = (EnumeratedClass)someValuesFromClass;
				Iterator<?> oneOfList = enumeratedClass.getOWLOneOf().iterator();
				while(oneOfList.hasNext()) {
					Individual individual = (Individual) oneOfList.next();
					if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getIfCondition1(), individual.getLocalName(), transformationMapping.getIfConditionValue1()))
						continue;
					addAssociation(umlClass, individual.getLocalName(), transformationMapping.getUmlVariable());
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
			}
			addAssociation(umlClass, onProperty.getLocalName(), transformationMapping.getUmlVariable());
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
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getIfCondition2(), onProperty.getLocalName(), transformationMapping.getIfConditionValue2()))
				continue;
			if(valueClass.getClass().getName().equals(TypedLiteralImpl.class.getName())) {
				TypedLiteralImpl typedLiteral = (TypedLiteralImpl) valueClass;
				if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getIfCondition1(), typedLiteral.getLexicalForm(), transformationMapping.getIfConditionValue1()))
					continue;
				addAssociation(umlClass, typedLiteral.getLexicalForm(), transformationMapping.getUmlVariable());
			}
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
				addAssociation(umlClass, individual.getLocalName(), transformationMapping.getUmlVariable());
			}
		}
	}

}
