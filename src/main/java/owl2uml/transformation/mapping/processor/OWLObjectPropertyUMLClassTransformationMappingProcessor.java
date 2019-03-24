package owl2uml.transformation.mapping.processor;

import java.util.Iterator;

import org.eclipse.eodm.owl.OWLObjectProperty;
import org.eclipse.eodm.owl.impl.OWLClassImpl;
import org.eclipse.eodm.owl.impl.OWLObjectPropertyImpl;
import org.eclipse.eodm.rdfs.RDFProperty;
import org.eclipse.eodm.rdfs.RDFSResource;

import owl2uml.GlobalOperations;
import owl2uml.GlobalVariables;
import owl2uml.transformation.mapping.OWLObjectPropertyUMLClassTransformationMapping;
import owl2uml.umlcomponents.UMLClass;
import owl2uml.umlcomponents.UMLModel;

/**
 * 
 * @author Ozer Ozdikis
 * @version June 2006
 */
public class OWLObjectPropertyUMLClassTransformationMappingProcessor extends UMLClassTransformationMappingProcessor {
	protected OWLObjectProperty owlObjectProperty;
	protected OWLObjectPropertyUMLClassTransformationMapping transformationMapping;

	public OWLObjectPropertyUMLClassTransformationMappingProcessor(OWLObjectProperty owlObjectProperty, UMLModel umlModel, OWLObjectPropertyUMLClassTransformationMapping transformationMapping) {
		super(umlModel);
		this.owlObjectProperty = owlObjectProperty;
		this.transformationMapping = transformationMapping;
	}
	
	public void runMappingProcessor() {
		if(transformationMapping.getOwlVariable().equals(GlobalVariables.OWL_SUPERPROPERTY_NAME))
			runMappingForSuperProperty();
		else if(transformationMapping.getOwlVariable().equals(GlobalVariables.OWL_DOMAIN))
			runMappingForDomain();
		else if(transformationMapping.getOwlVariable().equals(GlobalVariables.OWL_RANGE))
			runMappingForRange();
	}

	private void runMappingForSuperProperty() {
		Iterator<?> superPropertyList = owlObjectProperty.getRDFSSubPropertyOf().iterator();
		UMLClass umlClass = umlModel.getUMLClass(owlObjectProperty.getLocalName());
		while(superPropertyList.hasNext()) {
			RDFProperty owlSuperProperty = (RDFProperty) superPropertyList.next();
			if(!owlSuperProperty.getClass().getName().equals(OWLObjectPropertyImpl.class.getName()))
				continue;
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getIfCondition(), owlSuperProperty.getLocalName(), transformationMapping.getIfConditionValue()))
				continue;
			if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_SUPER_CLASS_NAME)) {
				addGeneralization(umlClass, owlSuperProperty.getLocalName());
			} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_ATTRIBUTE)) {
				addAttribute(umlClass, owlSuperProperty.getLocalName());
			} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_OPERATION_NAME)) {
				addOperation(umlClass, owlSuperProperty.getLocalName());
			}
		}
	}

	private void runMappingForDomain() {
		Iterator<?> domainList = owlObjectProperty.getRDFSDomain().iterator();
		UMLClass umlClass = umlModel.getUMLClass(owlObjectProperty.getLocalName());
		while(domainList.hasNext()) {
			RDFSResource owlDomainClass = (RDFSResource) domainList.next();
			if(!owlDomainClass.getClass().getName().equals(OWLClassImpl.class.getName()))
				continue;
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getIfCondition(), owlDomainClass.getLocalName(), transformationMapping.getIfConditionValue()))
				continue;
			if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_SUPER_CLASS_NAME)) {
				addGeneralization(umlClass, owlDomainClass.getLocalName());
			} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_ATTRIBUTE)) {
				addAttribute(umlClass, owlDomainClass.getLocalName());
			} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_OPERATION_NAME)) {
				addOperation(umlClass, owlDomainClass.getLocalName());
			}
		}
	}
	
	private void runMappingForRange() {
		Iterator<?> rangeList = owlObjectProperty.getRDFSRange().iterator();
		UMLClass umlClass = umlModel.getUMLClass(owlObjectProperty.getLocalName());
		while(rangeList.hasNext()) {
			RDFSResource owlRangeClass = (RDFSResource) rangeList.next();
			if(!owlRangeClass.getClass().getName().equals(OWLClassImpl.class.getName()))
				continue;
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getIfCondition(), owlRangeClass.getLocalName(), transformationMapping.getIfConditionValue()))
				continue;
			if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_SUPER_CLASS_NAME)) {
				addGeneralization(umlClass, owlRangeClass.getLocalName());
			} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_ATTRIBUTE)) {
				addAttribute(umlClass, owlRangeClass.getLocalName());
			} else if(transformationMapping.getUmlVariable().equals(GlobalVariables.UML_OPERATION_NAME)) {
				addOperation(umlClass, owlRangeClass.getLocalName());
			}
		}
	}
}
