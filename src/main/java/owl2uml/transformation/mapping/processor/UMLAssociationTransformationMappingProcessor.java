package owl2uml.transformation.mapping.processor;

import owl2uml.GlobalVariables;
import owl2uml.umlcomponents.UMLAssociation;
import owl2uml.umlcomponents.UMLClass;
import owl2uml.umlcomponents.UMLModel;

/**
 * 
 * @author Ozer Ozdikis
 * @version June 2006
 */
public class UMLAssociationTransformationMappingProcessor extends ATransformationMappingProcessor {
	
	public UMLAssociationTransformationMappingProcessor(UMLModel umlModel) {
		super(umlModel);
	}

	protected void addAssociation(UMLClass umlClass, String targetClassName, String associationType) {
		if(umlClass.getUMLAssociation(targetClassName) != null)
			return;
		UMLClass targetClass = umlModel.getUMLClass(targetClassName);
		if(targetClass == null)
			return;
		UMLAssociation newAssociation = new UMLAssociation();
		newAssociation.setAssociationEnd1(umlClass.getID());
		newAssociation.setAssociationEnd2(targetClass.getID());
		if(associationType.equals(GlobalVariables.UML_ASSOCIATION_DIRECTED))
			newAssociation.setAssociationType("none");
		else if(associationType.equals(GlobalVariables.UML_ASSOCIATION_AGGREGATION))
			newAssociation.setAssociationType("aggregate");
		else if(associationType.equals(GlobalVariables.UML_ASSOCIATION_COMPOSITION))
			newAssociation.setAssociationType("composite");
		newAssociation.setMultiplicityIncluded(false);
		umlClass.addUMLAssociation(newAssociation);
	}

	protected void addAssociation(UMLClass umlClass, String targetClassName, String associationType, String cardinalityValue, String multiplicityType, String multiplicityEnd) {
		if(umlClass.getUMLAssociation(targetClassName) != null)
			return;
		UMLClass targetClass = umlModel.getUMLClass(targetClassName);
		if(targetClass == null)
			return;
		UMLAssociation newAssociation = new UMLAssociation();
		newAssociation.setAssociationEnd1(umlClass.getID());
		newAssociation.setAssociationEnd2(targetClass.getID());
		if(associationType.equals(GlobalVariables.UML_ASSOCIATION_DIRECTED))
			newAssociation.setAssociationType("none");
		else if(associationType.equals(GlobalVariables.UML_ASSOCIATION_AGGREGATION))
			newAssociation.setAssociationType("aggregate");
		else if(associationType.equals(GlobalVariables.UML_ASSOCIATION_COMPOSITION))
			newAssociation.setAssociationType("composite");
		newAssociation.setMultiplicityIncluded(true);
		newAssociation.setMultiplicityEnd(multiplicityEnd);
		if(multiplicityType.equals(GlobalVariables.UML_ASSOCIATION_TYPE_EQUAL)) {
			newAssociation.setMultiplicityLowerEnd(cardinalityValue);
			newAssociation.setMultiplicityUpperEnd(cardinalityValue);
		} else if(multiplicityType.equals(GlobalVariables.UML_ASSOCIATION_TYPE_MIN)) {
			newAssociation.setMultiplicityLowerEnd(cardinalityValue);
			newAssociation.setMultiplicityUpperEnd("*");
		} else if(multiplicityType.equals(GlobalVariables.UML_ASSOCIATION_TYPE_MAX)) {
			newAssociation.setMultiplicityLowerEnd("0");
			newAssociation.setMultiplicityUpperEnd(cardinalityValue);
		}
		umlClass.addUMLAssociation(newAssociation);
	}


}
