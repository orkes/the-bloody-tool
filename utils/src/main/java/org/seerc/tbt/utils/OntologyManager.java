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
    private OWLOntologyManager _manager = null;

    /** SSN ontology */
    private OWLOntology _ontology = null;

    /** OWL data factory */
    private OWLDataFactory _factory = null;

    /** Prefix */
    private PrefixOWLOntologyFormat _pm = null;

    /**
     * Constructor
     */
    protected OntologyManager() {

        _manager = OWLManager.createOWLOntologyManager();
        // TODO move to constants
        IRI iri =
                IRI.create("https://www.dropbox.com/s/v8d85befvh7x0h2/mytest.owl?dl=1");
        try {
            _ontology = _manager.loadOntologyFromOntologyDocument(iri);
            LOGGER.info("Loaded ontology: " + _ontology);
        } catch (OWLOntologyCreationException e) {
            LOGGER.error("Could not load an ontology!", e);
        }
        // _reasonerFactory = PelletReasonerFactory.getInstance();
        // _reasoner =
        // _reasonerFactory.createReasoner(_ontology,
        // new SimpleConfiguration());
        _factory = _manager.getOWLDataFactory();
        _pm = (PrefixOWLOntologyFormat) _manager.getOntologyFormat(_ontology);
        _pm.setDefaultPrefix(Constants.SEERC_URL);

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
        _manager = OWLManager.createOWLOntologyManager();
        IRI iri = IRI.create(aURI);
        try {
            _ontology = _manager.loadOntologyFromOntologyDocument(iri);
            LOGGER.info("Loaded ontology: " + _ontology);
        } catch (OWLOntologyCreationException e) {
            LOGGER.error("Could not load an ontology!", e);
        }
        _factory = _manager.getOWLDataFactory();
        _pm = (PrefixOWLOntologyFormat) _manager.getOntologyFormat(_ontology);
        _pm.setDefaultPrefix(Constants.SEERC_URL);
    }

    /**
     * Reloads active ontology
     */
    public void reloadOntology() {
        _manager = OWLManager.createOWLOntologyManager();
        // TODO move to constants
        IRI iri =
                IRI.create("https://www.dropbox.com/s/v8d85befvh7x0h2/mytest.owl?dl=1");
        try {
            _ontology = _manager.loadOntologyFromOntologyDocument(iri);
            LOGGER.info("Loaded ontology: " + _ontology);
        } catch (OWLOntologyCreationException e) {
            LOGGER.error("Could not load an ontology!", e);
        }
        _factory = _manager.getOWLDataFactory();
        _pm = (PrefixOWLOntologyFormat) _manager.getOntologyFormat(_ontology);
        _pm.setDefaultPrefix(Constants.SEERC_URL);

    }

    /**
     * Returns an OWL class asserting that it exists in the ontology
     * 
     * @param aName name of the class *
     * @return class expression
     */
    public OWLClass getClass(String aName) {
        OWLClass returnClass = _factory.getOWLClass(":" + aName, _pm);
        Iterator<OWLClass> iter = _ontology.getClassesInSignature().iterator();
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
        OWLObjectProperty property =
                _factory.getOWLObjectProperty(":" + aName, _pm);
        Iterator<OWLObjectProperty> iter =
                _ontology.getObjectPropertiesInSignature().iterator();
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
        OWLDataProperty property =
                _factory.getOWLDataProperty(":" + aName, _pm);
        Iterator<OWLDataProperty> iter =
                _ontology.getDataPropertiesInSignature().iterator();
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
        OWLIndividual individual =
                _factory.getOWLNamedIndividual(":" + aName, _pm);
        return individual;
    }

    /**
     * Return the Subclass property
     * 
     * @return the Subclass property
     */
    public OWLDataProperty getSubclassProperty() {
        OWLDataProperty property =
                _factory.getOWLDataProperty(IRI
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
                _factory.getOWLObjectProperty(IRI
                        .create("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"));
        return property;
    }

}
