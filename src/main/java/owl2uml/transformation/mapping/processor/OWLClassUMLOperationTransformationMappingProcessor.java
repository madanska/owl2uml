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
import owl2uml.transformation.mapping.OWLClassUMLOperationTransformationMapping;
import owl2uml.umlcomponents.UMLClass;
import owl2uml.umlcomponents.UMLModel;
import owl2uml.umlcomponents.UMLOperation;

/**
 * @author Ozer Ozdikis
 * @version June 2006
 */
public class OWLClassUMLOperationTransformationMappingProcessor extends UMLOperationTransformationMappingProcessor {
	protected OWLClass owlClass;
	protected OWLClassUMLOperationTransformationMapping transformationMapping;
	
	public OWLClassUMLOperationTransformationMappingProcessor(OWLClass owlClass, UMLModel umlModel, OWLClassUMLOperationTransformationMapping transformationMapping) {
		super(umlModel);
		this.owlClass = owlClass;
		this.transformationMapping = transformationMapping;
	}
	
	public void runMappingProcessor() {
		Vector<String> parameterNames = new Vector<String>();
		if(transformationMapping.getParameterName().equals(GlobalVariables.OWL_SUPERCLASS_NAME))
			parameterNames = getParametersForSuperclass();
		else if(transformationMapping.getParameterName().equals(GlobalVariables.OWL_COMPLEMENT_OF))
			parameterNames = getParametersForComplementOf();
		else if(transformationMapping.getParameterName().equals(GlobalVariables.OWL_INTERSECTION_OF))
			parameterNames = getParametersForIntersectionOf();
		else if(transformationMapping.getParameterName().equals(GlobalVariables.OWL_UNION_OF))
			parameterNames = getParametersForUnionOf();
		else if(transformationMapping.getParameterName().equals(GlobalVariables.OWL_MINCARDINALITY_ONPROPERTY))
			parameterNames = getParametersForMinCardProperty();
		else if(transformationMapping.getParameterName().equals(GlobalVariables.OWL_MINCARDINALITY_VALUE))
			parameterNames = getParametersForMinCardValue();
		else if(transformationMapping.getParameterName().equals(GlobalVariables.OWL_MAXCARDINALITY_ONPROPERTY))
			parameterNames = getParametersForMaxCardProperty();
		else if(transformationMapping.getParameterName().equals(GlobalVariables.OWL_MAXCARDINALITY_VALUE))
			parameterNames = getParametersForMaxCardValue();
		else if(transformationMapping.getParameterName().equals(GlobalVariables.OWL_ALLVALUESFROM_ONPROPERTY))
			parameterNames = getParametersForAllValuesFromProperty();
		else if(transformationMapping.getParameterName().equals(GlobalVariables.OWL_ALLVALUESFROM_VALUE))
			parameterNames = getParametersForAllValuesFromValue();
		else if(transformationMapping.getParameterName().equals(GlobalVariables.OWL_SOMEVALUESFROM_ONPROPERTY))
			parameterNames = getParametersForSomeValuesFromProperty();
		else if(transformationMapping.getParameterName().equals(GlobalVariables.OWL_SOMEVALUESFROM_VALUE))
			parameterNames = getParametersForSomeValuesFromValue();
		else if(transformationMapping.getParameterName().equals(GlobalVariables.OWL_HASVALUE_ONPROPERTY))
			parameterNames = getParametersForHasValueProperty();
		else if(transformationMapping.getParameterName().equals(GlobalVariables.OWL_HASVALUE_VALUE))
			parameterNames = getParametersForHasValueValue();
		else if(transformationMapping.getParameterName().equals(GlobalVariables.OWL_ONE_OF))
			parameterNames = getParametersForOneOf();
		
		Vector<UMLClass> registeredUMLClasses = umlModel.getUMLClasses();
		for(int i=0; i<registeredUMLClasses.size(); i++) {
			UMLClass registeredUMLClass = registeredUMLClasses.get(i);
			UMLOperation registeredUMLOperation = registeredUMLClass.getUMLOperation(owlClass.getLocalName());
			if(registeredUMLOperation == null)
				continue;
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getOwnerNameIfCondition(), registeredUMLClass.getName(), transformationMapping.getOwnerNameIfConditionValue()))
				continue;
			addParameters(registeredUMLOperation, parameterNames, transformationMapping.getParameterType());
		}
	}
	
	private Vector<String> getParametersForSuperclass() {
		Vector<String> parameterNames = new Vector<String>();
		Iterator<?> superClassList = owlClass.getRDFSSubClassOf().iterator();
		while(superClassList.hasNext()) {
			OWLClass owlSuperClass = (OWLClass) superClassList.next();
			if(!owlSuperClass.getClass().getName().equals(OWLClassImpl.class.getName()))
				continue;
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getParameterNameIfCondition(), owlSuperClass.getLocalName(), transformationMapping.getParameterNameIfConditionValue()))
				continue;
			parameterNames.add(owlSuperClass.getLocalName());
		}
		return parameterNames;
	}
	
	private Vector<String> getParametersForComplementOf() {
		Vector<String> parameterNames = new Vector<String>();
		Vector<OWLClass> superAndEquivalentClasses = getSuperAndEquivalentClassVector(owlClass, ComplementClassImpl.class.getName());
		for(int i=0; i<superAndEquivalentClasses.size(); i++) {
			OWLClass owlSuperEqClass = superAndEquivalentClasses.get(i);
			ComplementClassImpl owlComplementClass = (ComplementClassImpl)owlSuperEqClass;
			OWLClass owlTargetClass = owlComplementClass.getOWLComplementOf();
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getParameterNameIfCondition(), owlTargetClass.getLocalName(), transformationMapping.getParameterNameIfConditionValue()))
				continue;
			parameterNames.add(owlTargetClass.getLocalName());
		}
		return parameterNames;
	}
		
	private Vector<String> getParametersForIntersectionOf() {
		Vector<String> parameterNames = new Vector<String>();
		Vector<OWLClass> superAndEquivalentClasses = getSuperAndEquivalentClassVector(owlClass, IntersectionClassImpl.class.getName());
		for(int i=0; i<superAndEquivalentClasses.size(); i++) {
			OWLClass owlSuperEqClass = superAndEquivalentClasses.get(i);
			IntersectionClassImpl owlIntersectionClass = (IntersectionClassImpl)owlSuperEqClass;
			Iterator<?> intersectionClasses = owlIntersectionClass.getOWLIntersectionOf().iterator();
			while(intersectionClasses.hasNext()) {
				OWLClass intersectionClass = (OWLClass) intersectionClasses.next();
				if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getParameterNameIfCondition(), intersectionClass.getLocalName(), transformationMapping.getParameterNameIfConditionValue()))
					continue;
				parameterNames.add(intersectionClass.getLocalName());
			}
		}
		return parameterNames;
	}
	
	private Vector<String> getParametersForUnionOf() {
		Vector<String> parameterNames = new Vector<String>();
		Vector<OWLClass> superAndEquivalentClasses = getSuperAndEquivalentClassVector(owlClass, UnionClassImpl.class.getName());
		for(int i=0; i<superAndEquivalentClasses.size(); i++) {
			OWLClass owlSuperEqClass = (OWLClass)superAndEquivalentClasses.get(i);
			UnionClassImpl owlUnionClass = (UnionClassImpl)owlSuperEqClass;
			Iterator<?> unionClasses = owlUnionClass.getOWLUnionOf().iterator();
			while(unionClasses.hasNext()) {
				OWLClass unionClass = (OWLClass) unionClasses.next();
				if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getParameterNameIfCondition(), unionClass.getLocalName(), transformationMapping.getParameterNameIfConditionValue()))
					continue;
				parameterNames.add(unionClass.getLocalName());
			}
		}
		return parameterNames;
	}
		
	private Vector<String> getParametersForMinCardProperty() {
		Vector<String> parameterNames = new Vector<String>();
		Vector<OWLClass> superAndEquivalentClasses = getSuperAndEquivalentClassVector(owlClass, MinCardinalityRestrictionImpl.class.getName());
		for(int i=0; i<superAndEquivalentClasses.size(); i++) {
			OWLClass owlSuperEqClass = (OWLClass)superAndEquivalentClasses.get(i);
			MinCardinalityRestriction restriction = (MinCardinalityRestriction)owlSuperEqClass;
			RDFProperty onProperty = restriction.getOWLOnProperty();
			String minValue = ((RDFSLiteral)restriction.getOWLMinCardinality()).getLexicalForm();
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getParameterNameIfCondition(), onProperty.getLocalName(), transformationMapping.getParameterNameIfConditionValue()))
				continue;
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getParameterNameIfCondition2(), minValue, transformationMapping.getParameterNameIfConditionValue2()))
				continue;
			parameterNames.add(onProperty.getLocalName());
		}
		return parameterNames;
	}
		
	private Vector<String> getParametersForMinCardValue() {
		Vector<String> parameterNames = new Vector<String>();
		Vector<OWLClass> superAndEquivalentClasses = getSuperAndEquivalentClassVector(owlClass, MinCardinalityRestrictionImpl.class.getName());
		for(int i=0; i<superAndEquivalentClasses.size(); i++) {
			OWLClass owlSuperEqClass = (OWLClass)superAndEquivalentClasses.get(i);
			MinCardinalityRestriction restriction = (MinCardinalityRestriction)owlSuperEqClass;
			RDFProperty onProperty = restriction.getOWLOnProperty();
			String minValue = ((RDFSLiteral)restriction.getOWLMinCardinality()).getLexicalForm();
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getParameterNameIfCondition(), minValue, transformationMapping.getParameterNameIfConditionValue()))
				continue;
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getParameterNameIfCondition2(), onProperty.getLocalName(), transformationMapping.getParameterNameIfConditionValue2()))
				continue;
			parameterNames.add(minValue);
		}
		return parameterNames;
	}

	private Vector<String> getParametersForMaxCardProperty() {
		Vector<String> parameterNames = new Vector<String>();
		Vector<OWLClass> superAndEquivalentClasses = getSuperAndEquivalentClassVector(owlClass, MaxCardinalityRestrictionImpl.class.getName());
		for(int i=0; i<superAndEquivalentClasses.size(); i++) {
			OWLClass owlSuperEqClass = (OWLClass)superAndEquivalentClasses.get(i);
			MaxCardinalityRestriction restriction = (MaxCardinalityRestriction)owlSuperEqClass;
			RDFProperty onProperty = restriction.getOWLOnProperty();
			String maxValue = ((RDFSLiteral)restriction.getOWLMaxCardinality()).getLexicalForm();
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getParameterNameIfCondition(), onProperty.getLocalName(), transformationMapping.getParameterNameIfConditionValue()))
				continue;
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getParameterNameIfCondition2(), maxValue, transformationMapping.getParameterNameIfConditionValue2()))
				continue;
			parameterNames.add(onProperty.getLocalName());
		}
		return parameterNames;
	}
		
	private Vector<String> getParametersForMaxCardValue() {
		Vector<String> parameterNames = new Vector<String>();
		Vector<OWLClass> superAndEquivalentClasses = getSuperAndEquivalentClassVector(owlClass, MaxCardinalityRestrictionImpl.class.getName());
		for(int i=0; i<superAndEquivalentClasses.size(); i++) {
			OWLClass owlSuperEqClass = (OWLClass)superAndEquivalentClasses.get(i);
			MaxCardinalityRestriction restriction = (MaxCardinalityRestriction)owlSuperEqClass;
			String maxValue = ((RDFSLiteral)restriction.getOWLMaxCardinality()).getLexicalForm();
			RDFProperty onProperty = restriction.getOWLOnProperty();
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getParameterNameIfCondition(), maxValue, transformationMapping.getParameterNameIfConditionValue()))
				continue;
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getParameterNameIfCondition2(), onProperty.getLocalName(), transformationMapping.getParameterNameIfConditionValue2()))
				continue;
			parameterNames.add(maxValue);
		}
		return parameterNames;
	}

	private Vector<String> getParametersForAllValuesFromProperty() {
		Vector<String> parameterNames = new Vector<String>();
		Vector<OWLClass> superAndEquivalentClasses = getSuperAndEquivalentClassVector(owlClass, AllValuesFromRestrictionImpl.class.getName());
		for(int i=0; i<superAndEquivalentClasses.size(); i++) {
			OWLClass owlSuperEqClass = superAndEquivalentClasses.get(i);
			AllValuesFromRestriction restriction = (AllValuesFromRestriction)owlSuperEqClass;
			RDFProperty onProperty = restriction.getOWLOnProperty();
			RDFSResource allValuesFromClass = restriction.getOWLAllValuesFrom();  
			if(!allValuesFromClass.getClass().getName().equals(OWLClassImpl.class.getName()))
				continue;
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getParameterNameIfCondition(), onProperty.getLocalName(), transformationMapping.getParameterNameIfConditionValue()))
				continue;
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getParameterNameIfCondition2(), allValuesFromClass.getLocalName(), transformationMapping.getParameterNameIfConditionValue2()))
				continue;
			parameterNames.add(onProperty.getLocalName());
		}
		return parameterNames;
	}
		
	private Vector<String> getParametersForAllValuesFromValue() {
		Vector<String> parameterNames = new Vector<String>();
		Vector<OWLClass> superAndEquivalentClasses = getSuperAndEquivalentClassVector(owlClass, AllValuesFromRestrictionImpl.class.getName());
		for(int i=0; i<superAndEquivalentClasses.size(); i++) {
			OWLClass owlSuperEqClass = superAndEquivalentClasses.get(i);
			AllValuesFromRestriction restriction = (AllValuesFromRestriction)owlSuperEqClass;
			RDFSResource allValuesFromClass = restriction.getOWLAllValuesFrom();  
			RDFProperty onProperty = restriction.getOWLOnProperty();
			if(!allValuesFromClass.getClass().getName().equals(OWLClassImpl.class.getName()))
				continue;
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getParameterNameIfCondition(), allValuesFromClass.getLocalName(), transformationMapping.getParameterNameIfConditionValue()))
				continue;
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getParameterNameIfCondition2(), onProperty.getLocalName(), transformationMapping.getParameterNameIfConditionValue2()))
				continue;
			parameterNames.add(allValuesFromClass.getLocalName());
		}
		return parameterNames;
	}
		
	private Vector<String> getParametersForSomeValuesFromProperty() {
		Vector<String> parameterNames = new Vector<String>();
		Vector<OWLClass> superAndEquivalentClasses = getSuperAndEquivalentClassVector(owlClass, SomeValuesFromRestrictionImpl.class.getName());
		for(int i=0; i<superAndEquivalentClasses.size(); i++) {
			OWLClass owlSuperEqClass = superAndEquivalentClasses.get(i);
			SomeValuesFromRestriction restriction = (SomeValuesFromRestriction)owlSuperEqClass;
			RDFProperty onProperty = restriction.getOWLOnProperty();
			RDFSResource someValuesFromClass = restriction.getOWLSomeValuesFrom();  
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getParameterNameIfCondition(), onProperty.getLocalName(), transformationMapping.getParameterNameIfConditionValue()))
				continue;
			if(someValuesFromClass.getClass().getName().equals(OWLClassImpl.class.getName())) {
				if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getParameterNameIfCondition2(), someValuesFromClass.getLocalName(), transformationMapping.getParameterNameIfConditionValue2()))
					continue;
			} else if(someValuesFromClass.getClass().getName().equals(EnumeratedClassImpl.class.getName())) {
					Iterator<?> oneOfList = ((EnumeratedClass)someValuesFromClass).getOWLOneOf().iterator();
					boolean satisfies = false;
					while(oneOfList.hasNext()) {
						Individual individual = (Individual) oneOfList.next();
						if(GlobalOperations.isStringConditionSatisfied(transformationMapping.getParameterNameIfCondition2(), individual.getLocalName(), transformationMapping.getParameterNameIfConditionValue2())) {
							satisfies = true;
							break;
						}
					}
					if(!satisfies)
						continue;
			} else
				continue;
			parameterNames.add(onProperty.getLocalName());
		}
		return parameterNames;
	}
		
	private Vector<String> getParametersForSomeValuesFromValue() {
		Vector<String> parameterNames = new Vector<String>();
		Vector<OWLClass> superAndEquivalentClasses = getSuperAndEquivalentClassVector(owlClass, SomeValuesFromRestrictionImpl.class.getName());
		for(int i=0; i<superAndEquivalentClasses.size(); i++) {
			OWLClass owlSuperEqClass = superAndEquivalentClasses.get(i);
			SomeValuesFromRestriction restriction = (SomeValuesFromRestriction)owlSuperEqClass;
			RDFSResource someValuesFromClass = restriction.getOWLSomeValuesFrom();
			RDFProperty onProperty = restriction.getOWLOnProperty();
			if(someValuesFromClass.getClass().getName().equals(OWLClassImpl.class.getName())) {
				if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getParameterNameIfCondition(), someValuesFromClass.getLocalName(), transformationMapping.getParameterNameIfConditionValue()))
					continue;
				if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getParameterNameIfCondition2(), onProperty.getLocalName(), transformationMapping.getParameterNameIfConditionValue2()))
					continue;
				parameterNames.add(someValuesFromClass.getLocalName());
			} else if(someValuesFromClass.getClass().getName().equals(EnumeratedClassImpl.class.getName())) {
				EnumeratedClass enumeratedClass = (EnumeratedClass)someValuesFromClass;
				Iterator<?> oneOfList = enumeratedClass.getOWLOneOf().iterator();
				while(oneOfList.hasNext()) {
					Individual individual = (Individual) oneOfList.next();
					if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getParameterNameIfCondition(), individual.getLocalName(), transformationMapping.getParameterNameIfConditionValue()))
						continue;
					if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getParameterNameIfCondition2(), onProperty.getLocalName(), transformationMapping.getParameterNameIfConditionValue2()))
						continue;
					parameterNames.add(individual.getLocalName());
				}
			}
		}
		return parameterNames;
	}
		
	private Vector<String> getParametersForHasValueProperty() {
		Vector<String> parameterNames = new Vector<String>();
		Vector<OWLClass> superAndEquivalentClasses = getSuperAndEquivalentClassVector(owlClass, HasValueRestrictionImpl.class.getName());
		for(int i=0; i<superAndEquivalentClasses.size(); i++) {
			OWLClass owlSuperEqClass = superAndEquivalentClasses.get(i);
			HasValueRestriction restriction = (HasValueRestriction)owlSuperEqClass;
			RDFProperty onProperty = restriction.getOWLOnProperty();
			RDFSResource valueClass = restriction.getOWLHasValue();  
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getParameterNameIfCondition(), onProperty.getLocalName(), transformationMapping.getParameterNameIfConditionValue()))
				continue;
			if(valueClass.getClass().getName().equals(TypedLiteralImpl.class.getName())) {
				TypedLiteralImpl typedLiteral = (TypedLiteralImpl) valueClass;
				if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getParameterNameIfCondition2(), typedLiteral.getLexicalForm(), transformationMapping.getParameterNameIfConditionValue2()))
					continue;
			} else 
				continue;
			parameterNames.add(onProperty.getLocalName());
		}
		return parameterNames;
	}
		
	private Vector<String> getParametersForHasValueValue() {
		Vector<String> parameterNames = new Vector<String>();
		Vector<OWLClass> superAndEquivalentClasses = getSuperAndEquivalentClassVector(owlClass, HasValueRestrictionImpl.class.getName());
		for(int i=0; i<superAndEquivalentClasses.size(); i++) {
			OWLClass owlSuperEqClass = superAndEquivalentClasses.get(i);
			HasValueRestriction restriction = (HasValueRestriction)owlSuperEqClass;
			RDFSResource valueClass = restriction.getOWLHasValue();
			RDFProperty onProperty = restriction.getOWLOnProperty();
			if(valueClass.getClass().getName().equals(TypedLiteralImpl.class.getName())) {
				TypedLiteralImpl typedLiteral = (TypedLiteralImpl) valueClass;
				if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getParameterNameIfCondition(), typedLiteral.getLexicalForm(), transformationMapping.getParameterNameIfConditionValue()))
					continue;
				if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getParameterNameIfCondition2(), onProperty.getLocalName(), transformationMapping.getParameterNameIfConditionValue2()))
					continue;
				parameterNames.add(typedLiteral.getLexicalForm());
			}
		}
		return parameterNames;
	}

	private Vector<String> getParametersForOneOf() {
		Vector<String> parameterNames = new Vector<String>();
		if(!owlClass.getClass().getName().equals(EnumeratedClassImpl.class.getName())) {
			return parameterNames;
		}
		EnumeratedClassImpl enumClass = (EnumeratedClassImpl)owlClass;
		Iterator<?> oneOfList = enumClass.getOWLOneOf().iterator();
		while(oneOfList.hasNext()) {
			Object obj = oneOfList.next();
			if(obj.getClass().getName().equals(IndividualImpl.class.getName())) {
				IndividualImpl individual = (IndividualImpl)obj;
				if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getParameterNameIfCondition(), individual.getLocalName(), transformationMapping.getParameterNameIfConditionValue()))
					continue;
				parameterNames.add(individual.getLocalName());
			}
		}
		return parameterNames;
	}

}
