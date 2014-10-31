package org.seerc.tbt.utils;

import java.util.Iterator;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for managing the ontology
 */
public class OntologyManager {

    /** Logger */
    protected static final Logger LOGGER = LoggerFactory
            .getLogger(OntologyManager.class);

    /** Single ton instance */
    private static OntologyManager _instance = null;

    /** Ontology manager */
    protected OWLOntologyManager manager = null;

    /** CSO ontology */
    protected OWLOntology ontology = null;

    /** Rules */
    protected OWLOntology rules = null;

    /** OWL data factory */
    protected OWLDataFactory factory = null;

    /** Prefix */
    protected PrefixOWLOntologyFormat pm = null;

    /**
     * Constructor
     */
    protected OntologyManager() {

        manager = OWLManager.createOWLOntologyManager();
        IRI csoIRI = IRI.create(Constants.CSO_URI);
        IRI rulesIRI = IRI.create(Constants.RULES_URI);
        try {
            ontology = manager.loadOntologyFromOntologyDocument(csoIRI);
            rules = manager.loadOntologyFromOntologyDocument(rulesIRI);
            LOGGER.info("Loaded CSO (" + ontology + ") and rule set (" + rules
                    + ")");
        } catch (OWLOntologyCreationException e) {
            LOGGER.error("Could not load an ontology!", e);
        }
        factory = manager.getOWLDataFactory();
        pm = (PrefixOWLOntologyFormat) manager.getOntologyFormat(ontology);
        pm.setDefaultPrefix(Constants.SEERC_URL);

    }

    /**
     * Get singleton instance
     * 
     * @return singleton instance of the ontology manager
     */
    public static OntologyManager getInstance() {
        if (_instance == null) {
            _instance = new OntologyManager();
        }
        return _instance;
    }

    /**
     * Reloads active ontology from the given URI
     * 
     * @param aURI URI of a new ontology
     */
    public void reloadOntology(String aURI) {
        manager = OWLManager.createOWLOntologyManager();
        IRI iri = IRI.create(aURI);
        try {
            ontology = manager.loadOntologyFromOntologyDocument(iri);
            LOGGER.info("Loaded ontology: " + ontology);
        } catch (OWLOntologyCreationException e) {
            LOGGER.error("Could not load an ontology!", e);
        }
        factory = manager.getOWLDataFactory();
        pm = (PrefixOWLOntologyFormat) manager.getOntologyFormat(ontology);
        pm.setDefaultPrefix(Constants.SEERC_URL);
    }

    /**
     * Returns an OWL class asserting that it exists in the ontology
     * 
     * @param aName name of the class *
     * @return class expression
     */
    public OWLClass getClass(String aName) {

        OWLClass returnClass;
        if (aName.startsWith(pm.getDefaultPrefix())) {
            returnClass = factory.getOWLClass(IRI.create(aName));
        } else {
            returnClass = factory.getOWLClass(":" + aName, pm);
        }
        Iterator<OWLClass> iter = ontology.getClassesInSignature().iterator();
        while (iter.hasNext()) {
            if (iter.next().equals(returnClass)) {
                return returnClass;
            }
        }
        LOGGER.error("The ontology does not contain the specified class: "
                + aName);
        return null;
    }

    /**
     * Returns an OWL object property asserting that it exists in the ontology
     * 
     * @param aName name of the object property
     * @return object property expression
     */
    public OWLObjectProperty getObjectProperty(String aName) {

        OWLObjectProperty property;
        if (aName.startsWith(pm.getDefaultPrefix())) {
            property = factory.getOWLObjectProperty(IRI.create(aName));
        } else {
            property = factory.getOWLObjectProperty(":" + aName, pm);
        }
        Iterator<OWLObjectProperty> iter =
                ontology.getObjectPropertiesInSignature().iterator();
        while (iter.hasNext()) {
            if (iter.next().equals(property)) {
                return property;
            }
        }
        LOGGER.error("The ontology does not contain the specified object property: "
                + aName);
        return null;
    }

    /**
     * Returns an OWL data property asserting that it exists in the ontology
     * 
     * @param aName name of the data property
     * @return data property expression
     */
    public OWLDataProperty getDataProperty(String aName) {

        OWLDataProperty property;
        if (aName.startsWith(pm.getDefaultPrefix())) {
            property = factory.getOWLDataProperty(IRI.create(aName));
        } else {
            property = factory.getOWLDataProperty(":" + aName, pm);
        }
        Iterator<OWLDataProperty> iter =
                ontology.getDataPropertiesInSignature().iterator();
        while (iter.hasNext()) {
            if (iter.next().equals(property)) {
                return property;
            }
        }
        LOGGER.error("The ontology does not contain the specified data property: "
                + aName);
        return null;
    }

    /**
     * Returns a newly created OWL individual
     * 
     * @param aName name of the individual
     * @return individual
     */
    public OWLIndividual getIndividual(String aName) {

        OWLIndividual individual;
        if (aName.startsWith(pm.getDefaultPrefix())) {
            individual = factory.getOWLNamedIndividual(IRI.create(aName));
        } else {
            individual = factory.getOWLNamedIndividual(":" + aName, pm);
        }
        return individual;
    }

    /**
     * Return the Subclass property
     * 
     * @return the Subclass property
     */
    public OWLDataProperty getSubclassProperty() {
        OWLDataProperty property =
                factory.getOWLDataProperty(IRI
                        .create("http://www.w3.org/2000/01/rdf-schema#subclassOf"));
        return property;
    }

    /**
     * Returns the Type property
     * 
     * @return the Type property
     */
    public OWLObjectProperty getTypeProperty() {
        OWLObjectProperty property =
                factory.getOWLObjectProperty(IRI
                        .create("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"));
        return property;
    }

    /**
     * TODO a quick check if a property is object
     * 
     * @param aProperty property
     * @return true or false
     */
    public boolean isObjectProperty(String aProperty) {
        return false;
    }

    /**
     * TODO a quick check if a property is data
     * 
     * @param aProperty property
     * @return true or false
     */
    public boolean isDataProperty(String aProperty) {
        return false;
    }

}
