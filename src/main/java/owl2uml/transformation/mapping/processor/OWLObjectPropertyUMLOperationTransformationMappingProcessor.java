package owl2uml.transformation.mapping.processor;

import java.util.Iterator;
import java.util.Vector;

import org.eclipse.eodm.owl.OWLObjectProperty;
import org.eclipse.eodm.owl.impl.OWLClassImpl;
import org.eclipse.eodm.owl.impl.OWLObjectPropertyImpl;
import org.eclipse.eodm.rdfs.RDFProperty;
import org.eclipse.eodm.rdfs.RDFSResource;

import owl2uml.GlobalOperations;
import owl2uml.GlobalVariables;
import owl2uml.transformation.mapping.OWLObjectPropertyUMLOperationTransformationMapping;
import owl2uml.umlcomponents.UMLClass;
import owl2uml.umlcomponents.UMLModel;
import owl2uml.umlcomponents.UMLOperation;

/**
 * 
 * @author Ozer Ozdikis
 * @version June 2006
 */
public class OWLObjectPropertyUMLOperationTransformationMappingProcessor extends UMLOperationTransformationMappingProcessor {
	protected OWLObjectProperty owlObjectProperty;
	protected OWLObjectPropertyUMLOperationTransformationMapping transformationMapping;

	public OWLObjectPropertyUMLOperationTransformationMappingProcessor(OWLObjectProperty owlObjectProperty, UMLModel umlModel, OWLObjectPropertyUMLOperationTransformationMapping transformationMapping) {
		super(umlModel);
		this.owlObjectProperty = owlObjectProperty;
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
			UMLOperation registeredUMLOperation = registeredUMLClass.getUMLOperation(owlObjectProperty.getLocalName());
			if(registeredUMLOperation == null)
				continue;
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getOwnerNameIfCondition(), registeredUMLClass.getName(), transformationMapping.getOwnerNameIfConditionValue()))
				continue;
			addParameters(registeredUMLOperation, parameterNames, transformationMapping.getParameterType());
		}
	}

	private Vector<String> getParameterNamesForSuperProperty() {
		Vector<String> parameterNames = new Vector<String>();
		Iterator<?> superPropertyList = owlObjectProperty.getRDFSSubPropertyOf().iterator();
		while(superPropertyList.hasNext()) {
			RDFProperty owlSuperProperty = (RDFProperty) superPropertyList.next();
			if(!owlSuperProperty.getClass().getName().equals(OWLObjectPropertyImpl.class.getName()))
				continue;
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getParameterNameIfCondition(), owlSuperProperty.getLocalName(), transformationMapping.getParameterNameIfConditionValue()))
				continue;
			parameterNames.add(owlSuperProperty.getLocalName());
		}
		return parameterNames;
	}

	private Vector<String> getParameterNamesForDomain() {
		Vector<String> parameterNames = new Vector<String>();
		Iterator<?> domainList = owlObjectProperty.getRDFSDomain().iterator();
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
		Iterator<?> rangeList = owlObjectProperty.getRDFSRange().iterator();
		while(rangeList.hasNext()) {
			RDFSResource owlRangeClass = (RDFSResource) rangeList.next();
			if(!owlRangeClass.getClass().getName().equals(OWLClassImpl.class.getName()))
				continue;
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getParameterNameIfCondition(), owlRangeClass.getLocalName(), transformationMapping.getParameterNameIfConditionValue()))
				continue;
			parameterNames.add(owlRangeClass.getLocalName());
		}
		return parameterNames;
	}
}
