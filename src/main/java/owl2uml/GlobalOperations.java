package owl2uml;

import java.util.Iterator;
import java.util.Vector;
import java.util.regex.Pattern;

import org.eclipse.eodm.owl.OWLClass;
import org.eclipse.eodm.owl.impl.IntersectionClassImpl;
import org.eclipse.eodm.owl.impl.OWLClassImpl;
import org.eclipse.eodm.owl.impl.UnionClassImpl;
import org.eclipse.eodm.rdfs.RDFProperty;

public class GlobalOperations {

	public static boolean isStringConditionSatisfied(String condition, String sourceValue, String conditionValue) {
		if(condition.equals(GlobalVariables.IF_NO_CONDITION)) {
				return true;
		} else if(condition.equals(GlobalVariables.IF_REGULAR_EXPRESSION)) {
			if(conditionValue.trim().equals(""))
				return true;
			return Pattern.matches(conditionValue, sourceValue);
		} else if(condition.equals(GlobalVariables.IF_STRING_EQUALS)) {
			if(!sourceValue.equals(conditionValue))
				return false;
		} else if(condition.equals(GlobalVariables.IF_STRING_BEGINS_WITH)) {
			if(!sourceValue.startsWith(conditionValue))
				return false;
		} else if(condition.equals(GlobalVariables.IF_STRING_ENDS_WITH)) {
			if(!sourceValue.endsWith(conditionValue))
				return false;
		} else if(condition.equals(GlobalVariables.IF_STRING_INCLUDES)) {
			if(sourceValue.indexOf(conditionValue) == -1)
				return false;
		} else if(condition.equals(GlobalVariables.IF_STRING_NOT_EQUALS)) {
			if(sourceValue.equals(conditionValue))
				return false;
		} else if(condition.equals(GlobalVariables.IF_STRING_NOT_BEGINS_WITH)) {
			if(sourceValue.startsWith(conditionValue))
				return false;
		} else if(condition.equals(GlobalVariables.IF_STRING_NOT_ENDS_WITH)) {
			if(sourceValue.endsWith(conditionValue))
				return false;
		} else if(condition.equals(GlobalVariables.IF_STRING_NOT_INCLUDES)) {
			if(sourceValue.indexOf(conditionValue) != -1)
				return false;
		}
		return true;
	}

	public static Vector<OWLClass> getRecursiveRDFSSubClassOf(OWLClass owlClass) {
		Vector<OWLClass> resultVector = new Vector<OWLClass>();
		Iterator<?> superClassList = owlClass.getRDFSSubClassOf().iterator();
		while(superClassList.hasNext()) {
			OWLClass owlSuperClass = (OWLClass)superClassList.next();
			if(!owlSuperClass.getClass().getName().equals(OWLClassImpl.class.getName()))
				continue;
			boolean alreadyExists = false;
			for(int i=0; i<resultVector.size(); i++) {
				OWLClass registeredSuperClass = (OWLClass) resultVector.get(i);
				if(registeredSuperClass.getLocalName().equals(owlSuperClass.getLocalName())) {
					alreadyExists = true;
					break;
				}
			}
			if(!alreadyExists)
				resultVector.add(owlSuperClass);
			Vector<?> superClassesOfSuperClass = getRecursiveRDFSSubClassOf(owlSuperClass);
			for(int i=0; i<superClassesOfSuperClass.size(); i++) {
				alreadyExists = false;
				OWLClass superClassOfSuperClass = (OWLClass) superClassesOfSuperClass.get(i);
				for(int j=0; j<resultVector.size(); j++) {
					OWLClass registeredSuperClass = (OWLClass) resultVector.get(j);
					if(registeredSuperClass.getLocalName().equals(superClassOfSuperClass.getLocalName())) {
						alreadyExists = true;
						break;
					}
				}
				if(!alreadyExists)
					resultVector.add(superClassOfSuperClass);
			}
		}
		return resultVector;
	}

	public static Vector<RDFProperty> getRecursiveRDFSSubPropertyOf(RDFProperty owlProperty) {
		Vector<RDFProperty> resultVector = new Vector<RDFProperty>();
		Iterator<?> superProperyList = owlProperty.getRDFSSubPropertyOf().iterator();
		while(superProperyList.hasNext()) {
			RDFProperty owlSuperProperty = (RDFProperty)superProperyList.next();
			boolean alreadyExists = false;
			for(int i=0; i<resultVector.size(); i++) {
				RDFProperty registeredSuperProperty = (RDFProperty) resultVector.get(i);
				if(registeredSuperProperty.getLocalName().equals(owlSuperProperty.getLocalName())) {
					alreadyExists = true;
					break;
				}
			}
			if(!alreadyExists)
				resultVector.add(owlSuperProperty);
			Vector<?> superPropertiesOfSuperProperty = getRecursiveRDFSSubPropertyOf(owlSuperProperty);
			for(int i=0; i<superPropertiesOfSuperProperty.size(); i++) {
				alreadyExists = false;
				RDFProperty superPropertyOfSuperProperty = (RDFProperty) superPropertiesOfSuperProperty.get(i);
				for(int j=0; j<resultVector.size(); j++) {
					RDFProperty registeredSuperProperty = (RDFProperty) resultVector.get(j);
					if(registeredSuperProperty.getLocalName().equals(superPropertyOfSuperProperty.getLocalName())) {
						alreadyExists = true;
						break;
					}
				}
				if(!alreadyExists)
					resultVector.add(superPropertyOfSuperProperty);
			}
		}
		return resultVector;
	}

	public static Vector<OWLClass> getSuperAndEquivalentClassVector(OWLClass owlClass, String typeOfSuperAndEquivalentClass) {
		Vector<OWLClass> resultVector = new Vector<OWLClass>();
		Iterator<?> superClassList = owlClass.getRDFSSubClassOf().iterator();
		while(superClassList.hasNext()) {
			OWLClass owlSuperClass = (OWLClass) superClassList.next();
			if(owlSuperClass.getClass().getName().equals(typeOfSuperAndEquivalentClass))
				resultVector.add(owlSuperClass);
			else if(owlSuperClass.getClass().getName().equals(IntersectionClassImpl.class.getName())) {
				IntersectionClassImpl owlIntersectionClass = (IntersectionClassImpl)owlSuperClass;
				Iterator<?> intersectionClasses = owlIntersectionClass.getOWLIntersectionOf().iterator();
				while(intersectionClasses.hasNext()) {
					OWLClass intersectionClass = (OWLClass) intersectionClasses.next();
					if(intersectionClass.getClass().getName().equals(typeOfSuperAndEquivalentClass))
						resultVector.add(intersectionClass);
				}
			}
			else if(owlSuperClass.getClass().getName().equals(UnionClassImpl.class.getName())) {
				UnionClassImpl owlUnionClass = (UnionClassImpl)owlSuperClass;
				Iterator<?> unionClasses = owlUnionClass.getOWLUnionOf().iterator();
				while(unionClasses.hasNext()) {
					OWLClass unionClass = (OWLClass) unionClasses.next();
					if(unionClass.getClass().getName().equals(typeOfSuperAndEquivalentClass))
						resultVector.add(unionClass);
				}
			}
		}
		Iterator<?> equivalentClassList = owlClass.getOWLEquivalentClass().iterator();
		while(equivalentClassList.hasNext()) {
			OWLClass owlEquivalentClass = (OWLClass) equivalentClassList.next();
			if(owlEquivalentClass.getClass().getName().equals(typeOfSuperAndEquivalentClass))
				resultVector.add(owlEquivalentClass);
			else if(owlEquivalentClass.getClass().getName().equals(IntersectionClassImpl.class.getName())) {
				IntersectionClassImpl owlIntersectionClass = (IntersectionClassImpl)owlEquivalentClass;
				Iterator<?> intersectionClasses = owlIntersectionClass.getOWLIntersectionOf().iterator();
				while(intersectionClasses.hasNext()) {
					OWLClass intersectionClass = (OWLClass) intersectionClasses.next();
					if(intersectionClass.getClass().getName().equals(typeOfSuperAndEquivalentClass))
						resultVector.add(intersectionClass);
				}
			}
			else if(owlEquivalentClass.getClass().getName().equals(UnionClassImpl.class.getName())) {
				UnionClassImpl owlUnionClass = (UnionClassImpl)owlEquivalentClass;
				Iterator<?> unionClasses = owlUnionClass.getOWLUnionOf().iterator();
				while(unionClasses.hasNext()) {
					OWLClass unionClass = (OWLClass) unionClasses.next();
					if(unionClass.getClass().getName().equals(typeOfSuperAndEquivalentClass))
						resultVector.add(unionClass);
				}
			}
		}
		return resultVector;
	}

}
