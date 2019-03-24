package owl2uml.transformation.mapping.processor;

import java.util.Vector;

import owl2uml.umlcomponents.UMLModel;
import owl2uml.umlcomponents.UMLOperation;
import owl2uml.umlcomponents.UMLParameter;

/**
 * 
 * @author Ozer Ozdikis
 * @version June 2006
 */
public class UMLOperationTransformationMappingProcessor extends ATransformationMappingProcessor {
	
	public UMLOperationTransformationMappingProcessor(UMLModel umlModel) {
		super(umlModel);
	}
	
	protected void addParameters(UMLOperation umlOperation, Vector<String> parameterNames, String parameterType) {
		for(int i=0; i<parameterNames.size(); i++) {
			String parameterName = parameterNames.get(i);
			UMLParameter umlParameter = new UMLParameter(parameterName, parameterType);
			umlOperation.addUMLParameter(umlParameter);
		}
	}
}
