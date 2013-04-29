package org.seerc.monitor.messaging;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.seerc.monitor.HomeController;
import org.seerc.monitor.stream.CSparqlStream;
import org.seerc.monitor.stream.MyFormatter;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.thoughtworks.xstream.XStream;

import eu.larkc.csparql.cep.api.RdfQuadruple;
import eu.larkc.csparql.engine.CsparqlEngine;
import eu.larkc.csparql.engine.CsparqlEngineImpl;
import eu.larkc.csparql.engine.CsparqlQueryResultProxy;
import eu.larkc.csparql.streams.formats.CSparqlQuery;

public class MonitorListener implements MessageListener {

    /** Logger */
    private static final Logger logger = LoggerFactory
            .getLogger(MonitorListener.class);

    /** The address of the SSN ontology */
    private static final String BASE_URL = "http://purl.oclc.org/NET/ssnx/ssn#";

    /** The Xstream engine */
    private final XStream xstream;

    /** C-SPARQL stream */
    private final CSparqlStream stream;

    /** C-SPARQL engine */
    private final static CsparqlEngine engine = new CsparqlEngineImpl();

    /** List of ids of registered queries */
    private final static List<String> queryIdList = new ArrayList<String>();

    // SSN fields

    private OWLOntologyManager manager;

    private OWLOntology ssnOntology;

    private OWLDataFactory factory;

    private OWLObjectProperty observes;

    private OWLDataProperty hasDataValue;

    private OWLClass responseTimeClass;

    private OWLIndividual sensorIndividual;

    @Autowired
    private RabbitTemplate template;

    public MonitorListener() {
        super();

        System.out.println("Constructor called " + toString());
        // init XStream
        xstream = new XStream();

        // init the C-SPARQL engine and stream
        engine.initialize();
        stream = new CSparqlStream("http://seerc.org/rdf/stream/");
        engine.registerStream(stream);
        final Thread thread = new Thread(stream);
        thread.start();

        registerQueries();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onMessage(Message message) {

        RdfQuadruple quadruple =
                (RdfQuadruple) xstream.fromXML(new String(message.getBody()));
        stream.put(quadruple);

    }

    /**
     * Initializes the SSN ontology
     * 
     * @throws OWLOntologyCreationException
     */
    public void initSsnOntology() throws OWLOntologyCreationException {

        manager = OWLManager.createOWLOntologyManager();
        IRI iri = IRI.create(BASE_URL);
        ssnOntology = manager.loadOntologyFromOntologyDocument(iri);

        logger.info("Loaded ontology: " + ssnOntology);

        factory = manager.getOWLDataFactory();
        OWLClass sensorClass =
                factory.getOWLClass(IRI.create(BASE_URL + "Sensor"));

        responseTimeClass =
                factory.getOWLClass(IRI.create(BASE_URL + "ResponseTime"));

        // sensorIndividual =
        // factory.getOWLNamedIndividual(IRI.create(BASE_URL
        // + SERVICE_NAME.getLocalPart()));

        OWLAxiom axiom =
                factory.getOWLClassAssertionAxiom(sensorClass, sensorIndividual);
        manager.addAxiom(ssnOntology, axiom);

        observes =
                factory.getOWLObjectProperty(IRI.create(BASE_URL + "observes"));
        hasDataValue =
                factory.getOWLDataProperty(IRI
                        .create(BASE_URL + "hasDataValue"));

    }

    /**
     * Registers queries
     */
    public static void registerQueries() {

        for (String id : queryIdList) {
            engine.unregisterQuery(id);
            System.out.println("deleted query " + id);
        }
        queryIdList.clear();

        int threshold = 5;

        for (int i = 0; i < HomeController.NUM_QUERIES; i++) {
            String query =
                    "REGISTER QUERY HelloWorld AS "
                            + "PREFIX ssn: <"
                            + BASE_URL
                            + "> "
                            + "SELECT ?s ?p ?o "
                            + "FROM STREAM <http://seerc.org/rdf/stream/> "
                            + "[RANGE 1m STEP 1s] "
                            + "WHERE { ?s ssn:observes ?o . ?o ssn:hasDataValue ?v . "
                            + "FILTER ( ?v >= " + threshold + ")}";

            CsparqlQueryResultProxy resultProxy = null;
            try {
                resultProxy = engine.registerQuery(query);
                queryIdList.add(resultProxy.getId());
            } catch (final ParseException ex) {
                logger.error("Error parsing the query: " + ex.getMessage());
            }
            if (resultProxy != null) {
                resultProxy.addObserver(new MyFormatter(resultProxy.getId(),
                        query));
            }
            threshold++;
        }
        for (CSparqlQuery query : engine.getAllQueries()) {
            System.out.println(query);
        }
    }

}
