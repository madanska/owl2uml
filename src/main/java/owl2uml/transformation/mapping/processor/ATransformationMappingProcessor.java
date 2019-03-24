package owl2uml.transformation.mapping.processor;

import java.util.Iterator;
import java.util.Vector;

import org.eclipse.eodm.owl.OWLClass;
import org.eclipse.eodm.owl.impl.IntersectionClassImpl;
import org.eclipse.eodm.owl.impl.UnionClassImpl;

import owl2uml.umlcomponents.UMLModel;

/**
 * 
 * @author Ozer Ozdikis
 * @version June 2006
 */
public abstract class ATransformationMappingProcessor {
	protected UMLModel umlModel;
	
	public ATransformationMappingProcessor(UMLModel umlModel) {
		this.umlModel = umlModel;
	}
	
	protected Vector<OWLClass> getSuperAndEquivalentClassVector(OWLClass owlClass, String typeOfSuperAndEquivalentClass) {
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
