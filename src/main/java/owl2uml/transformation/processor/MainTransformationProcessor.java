package owl2uml.transformation.processor;

import java.util.Vector;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;
import org.eclipse.eodm.owl.OWLClass;
import org.eclipse.eodm.owl.OWLDatatypeProperty;
import org.eclipse.eodm.owl.OWLObjectProperty;
import org.eclipse.eodm.owl.OWLOntology;

import owl2uml.GlobalVariables;
import owl2uml.graph.OWLClassTransformationGraph;
import owl2uml.graph.OWLDatatypePropertyTransformationGraph;
import owl2uml.graph.OWLObjectPropertyTransformationGraph;
import owl2uml.graph.cell.UMLAssociationGraphCell;
import owl2uml.graph.cell.UMLClassGraphCell;
import owl2uml.graph.cell.UMLOperationGraphCell;
import owl2uml.transformation.condition.processor.OWLClassTransformationConditionProcessor;
import owl2uml.transformation.condition.processor.OWLDatatypePropertyTransformationConditionProcessor;
import owl2uml.transformation.condition.processor.OWLObjectPropertyTransformationConditionProcessor;
import owl2uml.transformation.mapping.OWLClassUMLAssociationTransformationMapping;
import owl2uml.transformation.mapping.OWLClassUMLClassTransformationMapping;
import owl2uml.transformation.mapping.OWLClassUMLOperationTransformationMapping;
import owl2uml.transformation.mapping.OWLDatatypePropertyUMLAssociationTransformationMapping;
import owl2uml.transformation.mapping.OWLDatatypePropertyUMLClassTransformationMapping;
import owl2uml.transformation.mapping.OWLDatatypePropertyUMLOperationTransformationMapping;
import owl2uml.transformation.mapping.OWLObjectPropertyUMLAssociationTransformationMapping;
import owl2uml.transformation.mapping.OWLObjectPropertyUMLClassTransformationMapping;
import owl2uml.transformation.mapping.OWLObjectPropertyUMLOperationTransformationMapping;
import owl2uml.transformation.mapping.processor.OWLClassUMLAssociationTransformationMappingProcessor;
import owl2uml.transformation.mapping.processor.OWLClassUMLClassTransformationMappingProcessor;
import owl2uml.transformation.mapping.processor.OWLClassUMLOperationTransformationMappingProcessor;
import owl2uml.transformation.mapping.processor.OWLDatatypePropertyUMLAssociationTransformationMappingProcessor;
import owl2uml.transformation.mapping.processor.OWLDatatypePropertyUMLClassTransformationMappingProcessor;
import owl2uml.transformation.mapping.processor.OWLDatatypePropertyUMLOperationTransformationMappingProcessor;
import owl2uml.transformation.mapping.processor.OWLObjectPropertyUMLAssociationTransformationMappingProcessor;
import owl2uml.transformation.mapping.processor.OWLObjectPropertyUMLClassTransformationMappingProcessor;
import owl2uml.transformation.mapping.processor.OWLObjectPropertyUMLOperationTransformationMappingProcessor;
import owl2uml.umlcomponents.UMLClass;
import owl2uml.umlcomponents.UMLModel;

/**
 * Main transformation processor to execute OWL to UML transformations.
 * 
 * @author Ozer Ozdikis
 * @version June 2006
 */
public class MainTransformationProcessor {
	private OWLOntology ontology;
	private UMLModel umlModel;
	private Vector<?> transformationGraphs;
	private Category tracer = Logger.getLogger(MainTransformationProcessor.class);

	public MainTransformationProcessor(OWLOntology ontology, UMLModel umlModel, Vector<?> transformationGraphs) {
		this.ontology = ontology;
		this.umlModel = umlModel;
		this.transformationGraphs = transformationGraphs;
	}

	public void runTransformation() {
		for (int i = 0; i < transformationGraphs.size(); i++) {
			if (transformationGraphs.get(i).getClass().getName().equals(OWLClassTransformationGraph.class.getName()))
				createUMLClassesForSingleClassTransformationGraph(
						(OWLClassTransformationGraph) transformationGraphs.get(i));
			else if (transformationGraphs.get(i).getClass().getName()
					.equals(OWLDatatypePropertyTransformationGraph.class.getName()))
				createUMLClassesForSingleDatatypePropertyTransformationGraph(
						(OWLDatatypePropertyTransformationGraph) transformationGraphs.get(i));
			else if (transformationGraphs.get(i).getClass().getName()
					.equals(OWLObjectPropertyTransformationGraph.class.getName()))
				createUMLClassesForSingleObjectPropertyTransformationGraph(
						(OWLObjectPropertyTransformationGraph) transformationGraphs.get(i));
			else
				tracer.error("Unknown type of trnasformation graph");
		}

		for (int i = 0; i < transformationGraphs.size(); i++) {
			if (transformationGraphs.get(i).getClass().getName().equals(OWLClassTransformationGraph.class.getName()))
				runTransformationForSingleClassTransformationGraph(
						(OWLClassTransformationGraph) transformationGraphs.get(i));
			else if (transformationGraphs.get(i).getClass().getName()
					.equals(OWLDatatypePropertyTransformationGraph.class.getName()))
				runTransformationForSingleDatatypePropertyTransformationGraph(
						(OWLDatatypePropertyTransformationGraph) transformationGraphs.get(i));
			else if (transformationGraphs.get(i).getClass().getName()
					.equals(OWLObjectPropertyTransformationGraph.class.getName()))
				runTransformationForSingleObjectPropertyTransformationGraph(
						(OWLObjectPropertyTransformationGraph) transformationGraphs.get(i));
			else
				tracer.error("Unknown type of trnasformation graph");
		}
	}

	private void createUMLClassesForSingleClassTransformationGraph(OWLClassTransformationGraph transformationGraph) {
		if (transformationGraph.getUMLClassCellsVector().size() == 0)
			return;
		OWLClassTransformationConditionProcessor filter = new OWLClassTransformationConditionProcessor(
				transformationGraph, ontology);
		Vector<OWLClass> filteredOWLClasses = filter.getFilteredOWLClasses();
		for (int i = 0; i < filteredOWLClasses.size(); i++) {
			OWLClass owlClass = filteredOWLClasses.get(i);
			if (umlModel.getUMLClass(owlClass.getLocalName()) == null) {
				umlModel.addUMLClass(new UMLClass(owlClass.getLocalName()));
			}
		}
	}

	private void createUMLClassesForSingleDatatypePropertyTransformationGraph(
			OWLDatatypePropertyTransformationGraph transformationGraph) {
		if (transformationGraph.getUMLClassCellsVector().size() == 0)
			return;
		OWLDatatypePropertyTransformationConditionProcessor filter = new OWLDatatypePropertyTransformationConditionProcessor(
				transformationGraph, ontology);
		Vector<OWLDatatypeProperty> filteredOWLProperties = filter.getFilteredOWLDatatypeProperties();
		for (int i = 0; i < filteredOWLProperties.size(); i++) {
			OWLDatatypeProperty owlProperty = filteredOWLProperties.get(i);
			if (umlModel.getUMLClass(owlProperty.getLocalName()) == null) {
				umlModel.addUMLClass(new UMLClass(owlProperty.getLocalName()));
			}
		}
	}

	private void createUMLClassesForSingleObjectPropertyTransformationGraph(
			OWLObjectPropertyTransformationGraph transformationGraph) {
		if (transformationGraph.getUMLClassCellsVector().size() == 0)
			return;
		OWLObjectPropertyTransformationConditionProcessor filter = new OWLObjectPropertyTransformationConditionProcessor(
				transformationGraph, ontology);
		Vector<OWLObjectProperty> filteredOWLProperties = filter.getFilteredOWLObjectProperties();
		for (int i = 0; i < filteredOWLProperties.size(); i++) {
			OWLObjectProperty owlProperty = filteredOWLProperties.get(i);
			if (umlModel.getUMLClass(owlProperty.getLocalName()) == null) {
				umlModel.addUMLClass(new UMLClass(owlProperty.getLocalName()));
			}
		}
	}

	private void runTransformationForSingleClassTransformationGraph(OWLClassTransformationGraph transformationGraph) {
		OWLClassTransformationConditionProcessor filter = new OWLClassTransformationConditionProcessor(
				transformationGraph, ontology);
		Vector<OWLClass> filteredOWLClasses = filter.getFilteredOWLClasses();
		Vector<UMLClassGraphCell> umlClassCellsVector = transformationGraph.getUMLClassCellsVector();
		Vector<UMLAssociationGraphCell> umlAssociationCellsVector = transformationGraph.getUMLAssociationCellsVector();
		Vector<UMLOperationGraphCell> umlOperationCellsVector = transformationGraph.getUMLOperationCellsVector();
		for (int i = 0; i < umlClassCellsVector.size(); i++) {
			UMLClassGraphCell umlClassCell = umlClassCellsVector.get(i);
			OWLClassUMLClassTransformationMapping transformationMapping = umlClassCell
					.getOWLClassUMLClassTransformationMapping();
			if (transformationMapping.getUmlVariable().equals(GlobalVariables.UML_EMPTY))
				continue;
			for (int j = 0; j < filteredOWLClasses.size(); j++) {
				OWLClass owlClass = filteredOWLClasses.get(j);
				OWLClassUMLClassTransformationMappingProcessor mappingProcessor = new OWLClassUMLClassTransformationMappingProcessor(
						owlClass, umlModel, transformationMapping);
				mappingProcessor.runMappingProcessor();
			}
		}
		for (int i = 0; i < umlAssociationCellsVector.size(); i++) {
			UMLAssociationGraphCell umlAssociationCell = umlAssociationCellsVector.get(i);
			OWLClassUMLAssociationTransformationMapping transformationMapping = umlAssociationCell
					.getOwlClassUmlAssociationTransformationMapping();
			if (transformationMapping.getUmlVariable().equals(GlobalVariables.UML_EMPTY))
				continue;
			for (int j = 0; j < filteredOWLClasses.size(); j++) {
				OWLClass owlClass = (OWLClass) filteredOWLClasses.get(j);
				OWLClassUMLAssociationTransformationMappingProcessor mappingProcessor = new OWLClassUMLAssociationTransformationMappingProcessor(
						owlClass, umlModel, transformationMapping);
				mappingProcessor.runMappingProcessor();
			}
		}
		for (int i = 0; i < umlOperationCellsVector.size(); i++) {
			UMLOperationGraphCell umlOperationCell = umlOperationCellsVector.get(i);
			OWLClassUMLOperationTransformationMapping transformationMapping = umlOperationCell
					.getOWLClassUMLOperationTransformationMapping();
			for (int j = 0; j < filteredOWLClasses.size(); j++) {
				OWLClass owlClass = (OWLClass) filteredOWLClasses.get(j);
				OWLClassUMLOperationTransformationMappingProcessor mappingProcessor = new OWLClassUMLOperationTransformationMappingProcessor(
						owlClass, umlModel, transformationMapping);
				mappingProcessor.runMappingProcessor();
			}
		}
	}

	private void runTransformationForSingleDatatypePropertyTransformationGraph(
			OWLDatatypePropertyTransformationGraph transformationGraph) {
		OWLDatatypePropertyTransformationConditionProcessor filter = new OWLDatatypePropertyTransformationConditionProcessor(
				transformationGraph, ontology);
		Vector<OWLDatatypeProperty> filteredOWLProperties = filter.getFilteredOWLDatatypeProperties();
		Vector<UMLClassGraphCell> umlClassCellsVector = transformationGraph.getUMLClassCellsVector();
		Vector<UMLAssociationGraphCell> umlAssociationCellsVector = transformationGraph.getUMLAssociationCellsVector();
		Vector<UMLOperationGraphCell> umlOperationCellsVector = transformationGraph.getUMLOperationCellsVector();
		for (int i = 0; i < umlClassCellsVector.size(); i++) {
			UMLClassGraphCell umlClassCell = umlClassCellsVector.get(i);
			OWLDatatypePropertyUMLClassTransformationMapping transformationMapping = umlClassCell
					.getOWLDatatypePropertyUMLClassTransformationMapping();
			if (transformationMapping.getUmlVariable().equals(GlobalVariables.UML_EMPTY))
				continue;
			for (int j = 0; j < filteredOWLProperties.size(); j++) {
				OWLDatatypeProperty owlDatatypeProperty = filteredOWLProperties.get(j);
				OWLDatatypePropertyUMLClassTransformationMappingProcessor mappingProcessor = new OWLDatatypePropertyUMLClassTransformationMappingProcessor(
						owlDatatypeProperty, umlModel, transformationMapping);
				mappingProcessor.runMappingProcessor();
			}
		}
		for (int i = 0; i < umlAssociationCellsVector.size(); i++) {
			UMLAssociationGraphCell umlAssociationCell = umlAssociationCellsVector.get(i);
			OWLDatatypePropertyUMLAssociationTransformationMapping transformationMapping = umlAssociationCell
					.getOwlDatatypePropertyUmlAssociationTransformationMapping();
			if (transformationMapping.getUmlVariable().equals(GlobalVariables.UML_EMPTY))
				continue;
			for (int j = 0; j < filteredOWLProperties.size(); j++) {
				OWLDatatypeProperty owlDatatypeProperty = filteredOWLProperties.get(j);
				OWLDatatypePropertyUMLAssociationTransformationMappingProcessor mappingProcessor = new OWLDatatypePropertyUMLAssociationTransformationMappingProcessor(
						owlDatatypeProperty, umlModel, transformationMapping);
				mappingProcessor.runMappingProcessor();
			}
		}
		for (int i = 0; i < umlOperationCellsVector.size(); i++) {
			UMLOperationGraphCell umlOperationCell = umlOperationCellsVector.get(i);
			OWLDatatypePropertyUMLOperationTransformationMapping transformationMapping = umlOperationCell
					.getOWLDatatypePropertyUMLOperationTransformationMapping();
			for (int j = 0; j < filteredOWLProperties.size(); j++) {
				OWLDatatypeProperty owlDatatypeProperty = filteredOWLProperties.get(j);
				OWLDatatypePropertyUMLOperationTransformationMappingProcessor mappingProcessor = new OWLDatatypePropertyUMLOperationTransformationMappingProcessor(
						owlDatatypeProperty, umlModel, transformationMapping);
				mappingProcessor.runMappingProcessor();
			}
		}
	}

	private void runTransformationForSingleObjectPropertyTransformationGraph(
			OWLObjectPropertyTransformationGraph transformationGraph) {
		OWLObjectPropertyTransformationConditionProcessor filter = new OWLObjectPropertyTransformationConditionProcessor(
				transformationGraph, ontology);
		Vector<OWLObjectProperty> filteredOWLProperties = filter.getFilteredOWLObjectProperties();
		Vector<UMLClassGraphCell> umlClassCellsVector = transformationGraph.getUMLClassCellsVector();
		Vector<UMLAssociationGraphCell> umlAssociationCellsVector = transformationGraph.getUMLAssociationCellsVector();
		Vector<UMLOperationGraphCell> umlOperationCellsVector = transformationGraph.getUMLOperationCellsVector();
		for (int i = 0; i < umlClassCellsVector.size(); i++) {
			UMLClassGraphCell umlClassCell = umlClassCellsVector.get(i);
			OWLObjectPropertyUMLClassTransformationMapping transformationMapping = umlClassCell
					.getOWLObjectPropertyUMLClassTransformationMapping();
			if (transformationMapping.getUmlVariable().equals(GlobalVariables.UML_EMPTY))
				continue;
			for (int j = 0; j < filteredOWLProperties.size(); j++) {
				OWLObjectProperty owlObjectProperty = filteredOWLProperties.get(j);
				OWLObjectPropertyUMLClassTransformationMappingProcessor mappingProcessor = new OWLObjectPropertyUMLClassTransformationMappingProcessor(
						owlObjectProperty, umlModel, transformationMapping);
				mappingProcessor.runMappingProcessor();
			}
		}
		for (int i = 0; i < umlAssociationCellsVector.size(); i++) {
			UMLAssociationGraphCell umlAssociationCell = umlAssociationCellsVector.get(i);
			OWLObjectPropertyUMLAssociationTransformationMapping transformationMapping = umlAssociationCell
					.getOwlObjectPropertyUmlAssociationTransformationMapping();
			if (transformationMapping.getUmlVariable().equals(GlobalVariables.UML_EMPTY))
				continue;
			for (int j = 0; j < filteredOWLProperties.size(); j++) {
				OWLObjectProperty owlObjectProperty = filteredOWLProperties.get(j);
				OWLObjectPropertyUMLAssociationTransformationMappingProcessor mappingProcessor = new OWLObjectPropertyUMLAssociationTransformationMappingProcessor(
						owlObjectProperty, umlModel, transformationMapping);
				mappingProcessor.runMappingProcessor();
			}
		}
		for (int i = 0; i < umlOperationCellsVector.size(); i++) {
			UMLOperationGraphCell umlOperationCell = umlOperationCellsVector.get(i);
			OWLObjectPropertyUMLOperationTransformationMapping transformationMapping = umlOperationCell
					.getOWLObjectPropertyUMLOperationTransformationMapping();
			for (int j = 0; j < filteredOWLProperties.size(); j++) {
				OWLObjectProperty owlObjectProperty = filteredOWLProperties.get(j);
				OWLObjectPropertyUMLOperationTransformationMappingProcessor mappingProcessor = new OWLObjectPropertyUMLOperationTransformationMappingProcessor(
						owlObjectProperty, umlModel, transformationMapping);
				mappingProcessor.runMappingProcessor();
			}
		}
	}

}
