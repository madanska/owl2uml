package owl2uml.transformation.mapping.processor;

import java.util.Iterator;
import java.util.Vector;

import org.eclipse.eodm.owl.OWLDatatypeProperty;
import org.eclipse.eodm.owl.impl.OWLClassImpl;
import org.eclipse.eodm.owl.impl.OWLDataRangeImpl;
import org.eclipse.eodm.owl.impl.OWLDatatypePropertyImpl;
import org.eclipse.eodm.rdfs.RDFProperty;
import org.eclipse.eodm.rdfs.RDFSResource;
import org.eclipse.eodm.rdfs.impl.TypedLiteralImpl;

import owl2uml.GlobalOperations;
import owl2uml.GlobalVariables;
import owl2uml.transformation.mapping.OWLDatatypePropertyUMLOperationTransformationMapping;
import owl2uml.umlcomponents.UMLClass;
import owl2uml.umlcomponents.UMLModel;
import owl2uml.umlcomponents.UMLOperation;

/**
 * 
 * @author Ozer Ozdikis
 * @version June 2006
 */
public class OWLDatatypePropertyUMLOperationTransformationMappingProcessor extends UMLOperationTransformationMappingProcessor {
	protected OWLDatatypeProperty owlDatatypeProperty;
	protected OWLDatatypePropertyUMLOperationTransformationMapping transformationMapping;
	
	public OWLDatatypePropertyUMLOperationTransformationMappingProcessor(OWLDatatypeProperty owlDatatypeProperty, UMLModel umlModel, OWLDatatypePropertyUMLOperationTransformationMapping transformationMapping) {
		super(umlModel);
		this.owlDatatypeProperty = owlDatatypeProperty;
		this.transformationMapping = transformationMapping;
	}
	
	public void runMappingProcessor() {
		Vector<String> parameterNames = new Vector<String>();
		if(transformationMapping.getParameterName().equals(GlobalVariables.OWL_SUPERPROPERTY_NAME))
			getParameterNamesForSuperProperty();
		else if(transformationMapping.getParameterName().equals(GlobalVariables.OWL_DOMAIN))
			getParameterNamesForDomain();
		else if(transformationMapping.getParameterName().equals(GlobalVariables.OWL_RANGE))
			getParameterNamesForRange();

		Vector<UMLClass> registeredUMLClasses = umlModel.getUMLClasses();
		for(int i=0; i<registeredUMLClasses.size(); i++) {
			UMLClass registeredUMLClass = registeredUMLClasses.get(i);
			UMLOperation registeredUMLOperation = registeredUMLClass.getUMLOperation(owlDatatypeProperty.getLocalName());
			if(registeredUMLOperation == null)
				continue;
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getOwnerNameIfCondition(), registeredUMLClass.getName(), transformationMapping.getOwnerNameIfConditionValue()))
				continue;
			addParameters(registeredUMLOperation, parameterNames, transformationMapping.getParameterType());
		}
	}

	private Vector<String> getParameterNamesForSuperProperty() {
		Vector<String> parameterNames = new Vector<String>();
		Iterator<?> superPropertyList = owlDatatypeProperty.getRDFSSubPropertyOf().iterator();
		while(superPropertyList.hasNext()) {
			RDFProperty owlSuperProperty = (RDFProperty) superPropertyList.next();
			if(!owlSuperProperty.getClass().getName().equals(OWLDatatypePropertyImpl.class.getName()))
				continue;
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getParameterNameIfCondition(), owlSuperProperty.getLocalName(), transformationMapping.getParameterNameIfConditionValue()))
				continue;
			parameterNames.add(owlSuperProperty.getLocalName());
		}
		return parameterNames;
	}

	private Vector<String> getParameterNamesForDomain() {
		Vector<String> parameterNames = new Vector<String>();
		Iterator<?> domainList = owlDatatypeProperty.getRDFSDomain().iterator();
		while(domainList.hasNext()) {
			RDFSResource owlDomainClass = (RDFSResource) domainList.next();
			if(!owlDomainClass.getClass().getName().equals(OWLClassImpl.class.getName()))
				continue;
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getParameterNameIfCondition(), owlDomainClass.getLocalName(), transformationMapping.getParameterNameIfConditionValue()))
				continue;
			parameterNames.add(owlDomainClass.getLocalName());
		}
		return parameterNames;
	}
	
	private Vector<String> getParameterNamesForRange() {
		Vector<String> parameterNames = new Vector<String>();
		Iterator<?> rangeList = owlDatatypeProperty.getRDFSRange().iterator();
		while(rangeList.hasNext()) {
			RDFSResource rdfsRange = (RDFSResource) rangeList.next();
			if(!rdfsRange.getClass().getName().equals(OWLDataRangeImpl.class.getName()))
				continue;
			OWLDataRangeImpl owlDataRange = (OWLDataRangeImpl) rdfsRange;
			Iterator<?> rangeIterator = owlDataRange.getOWLOneOf().iterator();
			while(rangeIterator.hasNext()) {
				RDFSResource res = (RDFSResource) rangeIterator.next();
				if(!res.getClass().getName().equals(TypedLiteralImpl.class.getName()))
					continue;
				TypedLiteralImpl typedLiteral = (TypedLiteralImpl) res;
				if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getParameterNameIfCondition(), typedLiteral.getLexicalForm(), transformationMapping.getParameterNameIfConditionValue()))
					continue;
				parameterNames.add(typedLiteral.getLexicalForm());
			}
		}
		return parameterNames;
	}

}
