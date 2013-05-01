package org.seerc.webclient;

import java.text.DateFormat;
import java.util.Date;

import javax.xml.namespace.QName;

import org.seerc.webclient.config.QueueNames;
import org.seerc.ws.HelloWorldWS;
import org.seerc.ws.HelloWorldWSService;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.ChannelCallback;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rabbitmq.client.AMQP.Queue.DeclareOk;
import com.rabbitmq.client.Channel;

import com.thoughtworks.xstream.XStream;

import eu.larkc.csparql.cep.api.RdfQuadruple;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

    /** Logger */
    private static final Logger logger = LoggerFactory
            .getLogger(HomeController.class);

    /** RabbitMQ template */
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /** Monitroing queue */
    @Autowired
    private Queue monitoringQueue;

    /** The XStream manager */
    private final XStream xstream;

    /** The name of the web-service. */
    private static final QName SERVICE_NAME = new QName(
            "http://bean.ws.seerc.org/", "WebServiceBeanService");

    /** The address of the SSN ontology */
    private static final String BASE_URL = "http://purl.oclc.org/NET/ssnx/ssn#";

    /** Integer value. */
    private static final int THOUSAND = 1000;

    // SSN fields

    private OWLOntologyManager manager;

    private OWLOntology ssnOntology;

    private HelloWorldWS port;

    private OWLDataFactory factory;

    private OWLObjectProperty observes;

    private OWLDataProperty hasDataValue;

    private OWLClass responseTimeClass;

    private OWLIndividual sensorIndividual;

    public HomeController() {
        try {
            initSsnOntology();
        } catch (OWLOntologyCreationException e) {
            logger.error("Could not initialize the SSN ontology!", e);
        }
        initWebService();
        xstream = new XStream();
    }

    /**
     * Simply selects the home view to render by returning its name.
     * 
     * @param aModel model
     * @return string
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model aModel) {
        logger.info("Welcome home!");

        Date date = new Date();
        DateFormat dateFormat =
                DateFormat
                        .getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
        String formattedDate = dateFormat.format(date);
        aModel.addAttribute("serverTime", formattedDate);

        aModel.addAttribute(new InvocationParams());

        return "home";
    }

    /**
     * Purges all exisiitng queues
     * 
     * @return string
     */
    @RequestMapping(value = "/purge", method = RequestMethod.GET)
    @ResponseBody
    public String purgeQueue() {
        RabbitAdmin admin =
                new RabbitAdmin(rabbitTemplate.getConnectionFactory());
        admin.purgeQueue(QueueNames.MONITORING_QUEUE_NAME, true);
        return QueueNames.MONITORING_QUEUE_NAME + " was purged.";

    }

    /**
     * Lists the size of existing queues
     * 
     * @return string
     */
    @RequestMapping(value = "/queue", method = RequestMethod.GET)
    @ResponseBody
    public String queue() {
        return "Message count for " + QueueNames.MONITORING_QUEUE_NAME + " = "
                + declareQueuePassive(monitoringQueue).getMessageCount();
    }

    /**
     * Executes the web service invocation
     * 
     * @param aModel model
     * @param aParams invocation parameters
     * @return string
     * @throws OWLOntologyCreationException exception
     */
    @RequestMapping(value = "/execute", method = RequestMethod.POST)
    public String execute(Model aModel, InvocationParams aParams)
            throws OWLOntologyCreationException {

        callWebService1(aParams.getNumberOfThreads(),
                aParams.getNumberOfInvocations());

        aModel.addAttribute("numberOfThreads", aParams.getNumberOfThreads());
        aModel.addAttribute("numberOfInvocations",
                aParams.getNumberOfInvocations());
        aModel.addAttribute("executed", true);
        return home(aModel);
    }

    /**
     * Initializes an access to the SSN ontology
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
        sensorIndividual =
                factory.getOWLNamedIndividual(IRI.create(BASE_URL
                        + SERVICE_NAME.getLocalPart()));
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
     * Initializes an access to the web service
     */
    public void initWebService() {

        HelloWorldWSService service = new HelloWorldWSService();
        port = service.getHelloWorldWSPort();
    }

    /**
     * Calls the web service
     * 
     * @param aNumberOfThreads number of parallel threads
     * @throws OWLOntologyCreationException exception
     */
    public void callWebService1(final int aNumberOfThreads,
            final int aNumberOfInvocations) throws OWLOntologyCreationException {

        for (int i = 0; i < aNumberOfThreads; i++) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    for (int i = 0; i < aNumberOfInvocations; i++) {

                        long time = System.currentTimeMillis();
                        String result = port.getHelloWorld();
                        time = System.currentTimeMillis() - time;
                        logger.info(result + " in " + String.valueOf(time)
                                + " milliseconds");
                        try {
                            Thread.currentThread();
                            Thread.sleep(THOUSAND);
                        } catch (InterruptedException e) {
                            logger.error("Error with threads!", e);
                        }

                        final OWLIndividual responseTimeIndividual =
                                factory.getOWLNamedIndividual(IRI
                                        .create(BASE_URL + "RT"
                                                + System.currentTimeMillis()));
                        OWLAxiom axiom =
                                factory.getOWLClassAssertionAxiom(
                                        responseTimeClass,
                                        responseTimeIndividual);
                        manager.addAxiom(ssnOntology, axiom);

                        OWLObjectPropertyAssertionAxiom propertyAssertion =
                                factory.getOWLObjectPropertyAssertionAxiom(
                                        observes, sensorIndividual,
                                        responseTimeIndividual);

                        manager.addAxiom(ssnOntology, propertyAssertion);

                        sendToQueue(sensorIndividual.toStringID(),
                                observes.toStringID(),
                                responseTimeIndividual.toStringID());

                        OWLDataPropertyAssertionAxiom dataPropertyAssertion =
                                factory.getOWLDataPropertyAssertionAxiom(
                                        hasDataValue, responseTimeIndividual,
                                        time);

                        manager.addAxiom(ssnOntology, dataPropertyAssertion);

                        sendToQueue(responseTimeIndividual.toStringID(),
                                hasDataValue.toStringID(), time
                                        + "^^http://www.w3.org/2001/"
                                        + "XMLSchema#long");

                    }
                }
            }).start();
        }

    }

    /**
     * Sends an RDF message to queue
     * 
     * @param aSubject subject
     * @param aPredicate predicate
     * @param aObject object
     */
    private void sendToQueue(final String aSubject, final String aPredicate,
            final String aObject) {

        final RdfQuadruple quadruple =
                new RdfQuadruple(aSubject, aPredicate, aObject,
                        System.nanoTime());
        rabbitTemplate.convertAndSend(QueueNames.MONITORING_QUEUE_NAME,
                xstream.toXML(quadruple));
        logger.info("Sent: " + quadruple.toString());
    }

    /**
     * Declares a queue passively
     * 
     * @param aQueue a queue to be declared
     * @return status of declaration
     */
    public DeclareOk declareQueuePassive(final Queue aQueue) {

        return rabbitTemplate.execute(new ChannelCallback<DeclareOk>() {

            @Override
            public DeclareOk doInRabbit(Channel aChannel) throws Exception {
                return aChannel.queueDeclarePassive(aQueue.getName());
            }
        });
    }

}
