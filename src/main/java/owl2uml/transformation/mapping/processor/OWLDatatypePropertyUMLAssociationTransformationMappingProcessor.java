package owl2uml.transformation.mapping.processor;

import java.util.Iterator;

import org.eclipse.eodm.owl.OWLDatatypeProperty;
import org.eclipse.eodm.owl.impl.OWLClassImpl;
import org.eclipse.eodm.owl.impl.OWLDataRangeImpl;
import org.eclipse.eodm.owl.impl.OWLDatatypePropertyImpl;
import org.eclipse.eodm.rdfs.RDFProperty;
import org.eclipse.eodm.rdfs.RDFSResource;
import org.eclipse.eodm.rdfs.impl.TypedLiteralImpl;

import owl2uml.GlobalOperations;
import owl2uml.GlobalVariables;
import owl2uml.transformation.mapping.OWLDatatypePropertyUMLAssociationTransformationMapping;
import owl2uml.umlcomponents.UMLClass;
import owl2uml.umlcomponents.UMLModel;

/**
 * @author Ozer Ozdikis
 * @version June 2006
 */
public class OWLDatatypePropertyUMLAssociationTransformationMappingProcessor extends UMLAssociationTransformationMappingProcessor {
	protected OWLDatatypeProperty owlDatatypeProperty;
	protected OWLDatatypePropertyUMLAssociationTransformationMapping transformationMapping;

	public OWLDatatypePropertyUMLAssociationTransformationMappingProcessor(OWLDatatypeProperty owlDatatypeProperty, UMLModel umlModel, OWLDatatypePropertyUMLAssociationTransformationMapping transformationMapping) {
		super(umlModel);
		this.owlDatatypeProperty = owlDatatypeProperty;
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
		Iterator<?> superPropertyList = owlDatatypeProperty.getRDFSSubPropertyOf().iterator();
		UMLClass umlClass = umlModel.getUMLClass(owlDatatypeProperty.getLocalName());
		while(superPropertyList.hasNext()) {
			RDFProperty owlSuperProperty = (RDFProperty) superPropertyList.next();
			if(!owlSuperProperty.getClass().getName().equals(OWLDatatypePropertyImpl.class.getName()))
				continue;
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getIfCondition(), owlSuperProperty.getLocalName(), transformationMapping.getIfConditionValue()))
				continue;
			addAssociation(umlClass, owlSuperProperty.getLocalName(), transformationMapping.getUmlVariable());
		}
	}

	private void runMappingForDomain() {
		Iterator<?> domainList = owlDatatypeProperty.getRDFSDomain().iterator();
		UMLClass umlClass = umlModel.getUMLClass(owlDatatypeProperty.getLocalName());
		while(domainList.hasNext()) {
			RDFSResource owlDomainClass = (RDFSResource) domainList.next();
			if(!owlDomainClass.getClass().getName().equals(OWLClassImpl.class.getName()))
				continue;
			if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getIfCondition(), owlDomainClass.getLocalName(), transformationMapping.getIfConditionValue()))
				continue;
			addAssociation(umlClass, owlDomainClass.getLocalName(), transformationMapping.getUmlVariable());
		}
	}

	private void runMappingForRange() {
		Iterator<?> rangeList = owlDatatypeProperty.getRDFSRange().iterator();
		UMLClass umlClass = umlModel.getUMLClass(owlDatatypeProperty.getLocalName());
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
				if(!GlobalOperations.isStringConditionSatisfied(transformationMapping.getIfCondition(), typedLiteral.getLexicalForm(), transformationMapping.getIfConditionValue()))
					continue;
				addAssociation(umlClass, typedLiteral.getLexicalForm(), transformationMapping.getUmlVariable());
			}
		}
	}
}
