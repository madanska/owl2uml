package owl2uml.transformation.mapping.processor;

import java.util.Vector;

import owl2uml.umlcomponents.UMLAttribute;
import owl2uml.umlcomponents.UMLClass;
import owl2uml.umlcomponents.UMLGeneralization;
import owl2uml.umlcomponents.UMLModel;
import owl2uml.umlcomponents.UMLOperation;
import owl2uml.umlcomponents.UMLParameter;

/**
 * 
 * @author Ozer Ozdikis
 * @version June 2006
 */
public class UMLClassTransformationMappingProcessor extends ATransformationMappingProcessor {
	
	public UMLClassTransformationMappingProcessor(UMLModel umlModel) {
		super(umlModel);
	}
	
	protected void addGeneralization(UMLClass umlClass, String superClassName) {
		UMLClass parentUMLClass = umlModel.getUMLClass(superClassName); 
		if(parentUMLClass == null)
			return;
		UMLGeneralization registeredGeneralization = umlClass.getUMLGeneralization(superClassName);
		if(registeredGeneralization != null)
			return;
		umlClass.addUMLGeneralization(new UMLGeneralization("", umlClass.getID(), parentUMLClass.getID()));
	}
	
	protected void addAttribute(UMLClass umlClass, String attributeName) {
		UMLAttribute registeredAttribute = umlClass.getUMLAttribute(attributeName);
		if(registeredAttribute != null)
			return;
		umlClass.addUMLAttribute(new UMLAttribute(attributeName));
	}

	protected void addOperation(UMLClass umlClass, String operationName) {
		UMLOperation registeredOperation = umlClass.getUMLOperation(operationName);
		if(registeredOperation != null)
			return;
		umlClass.addUMLOperation(new UMLOperation(operationName));
	}
	
	protected void addOperation(UMLClass umlClass, String operationName, String parameterName, String parameterKind) {
		Vector<String> parameterNames = new Vector<String>();
		parameterNames.add(parameterName);
		UMLOperation registeredOperation = umlClass.getUMLOperation(operationName);
		if(registeredOperation != null)
			return;
		UMLOperation operation = new UMLOperation(operationName);
		operation.addUMLParameter(new UMLParameter(parameterName, parameterKind));
		umlClass.addUMLOperation(operation);
	}

	protected void addOperation(UMLClass umlClass, String operationName, Vector<String> parameterNames, String parameterKind) {
		UMLOperation registeredOperation = umlClass.getUMLOperation(operationName);
		if(registeredOperation != null)
			return;
		UMLOperation operation = new UMLOperation(operationName);
		for(int i=0; i<parameterNames.size(); i++) {
			String parameterName = (String) parameterNames.get(i);
			operation.addUMLParameter(new UMLParameter(parameterName, parameterKind));
		}
		umlClass.addUMLOperation(operation);
	}
}
