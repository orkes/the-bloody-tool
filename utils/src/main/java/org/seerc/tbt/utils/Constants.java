package org.seerc.tbt.utils;

/**
 * Class for storing static constant values
 */
public class Constants {

    /** Name of the monitoring queue */
    public final static String MONITOR_QUEUE = "monitor.queue";

    /** Name of the monitoring queue */
    public final static String MONITOR_FEEDBACK_QUEUE =
            "monitor.feedback.queue";

    /** Name of the monitoring queue */
    public final static String CLIENT_MONITOR_QUEUE = "client.monitor.queue";

    /** Name of the monitoring queue */
    public final static String CLIENT_MONITOR_FEEDBACK_QUEUE =
            "client.monitor.feedback.queue";

    /** Name of the monitoring queue */
    public final static String WORKER_MONITOR_QUEUE = "worker.monitor.queue";

    /** Name of the monitoring queue */
    public final static String WORKER_MONITOR_FEEDBACK_QUEUE =
            "worker.monitor.feedback.queue";

    /** Name of the task queue */
    public final static String TASK_QUEUE = "task.queue";

    /** Client-Monitor CloudAMQP host URI */
    public final static String CLIENT_MONITOR_QUEUE_HOST =
            "amqp://tibjeufo:ySm9zqnsASn15GXoYklxGGAOFbecvP5w@turtle.rmq.cloudamqp.com/tibjeufo";

    /** Worker-Monitor RabbitMQ host URI */
    public final static String WORKER_MONITOR_QUEUE_HOST =
            "amqp://qlwhlvrg:rK6BmSSW65Z_1JqdLVrsejEK0KWkkpDv@turtle.rmq.cloudamqp.com/qlwhlvrg";

    /** CloudAMQP task host URI */
    public final static String CLOUD_AMQP_TASK_HOST =
            "amqp://dbcslueu:UZzSVnwdPV_6G3VxuOl-pVjR2BtyYLxV@lemur.cloudamqp.com/dbcslueu";

    /** RabbitMQ Bigwig task host URI (for publishing only!) */
    public final static String BIGWIG_TASK_PUBLISH_HOST =
            "amqp://yGO5zjrp:H6voXgeHCBvQgZvFsz5WQzIQvzHYl3lF@lean-thunder-12.bigwig.lshift.net:10692/ZzCEPvxmyW_D";

    /** RabbitMQ Bigwig task host URI (for consuming only!) */
    public final static String BIGWIG_TASK_CONSUME_HOST =
            "amqp://yGO5zjrp:H6voXgeHCBvQgZvFsz5WQzIQvzHYl3lF@lean-thunder-12.bigwig.lshift.net:10693/ZzCEPvxmyW_D";

    /** RabbitMQ Bigwig monitoring host URI (for publishing only!) */
    public final static String BIGWIG_MONITORING_PUBLISH_HOST =
            "amqp://wS1xGpmf:IrOoQpkyUwG1E-H4QHU-Ae6ydDzEEU1X@white-coltsfoot-20.bigwig.lshift.net:10728/d2S82rvynciz";

    /** RabbitMQ Bigwig monitoring host URI (for consuming only!) */
    public final static String BIGWIG_MONITORING_CONSUME_HOST =
            "amqp://wS1xGpmf:IrOoQpkyUwG1E-H4QHU-Ae6ydDzEEU1X@white-coltsfoot-20.bigwig.lshift.net:10729/d2S82rvynciz";

    /** Web URL of the main ontology */
    public final static String ONTOLOGY_URI =
            "https://www.dropbox.com/s/ss4642mfzt0cktk/ontology.owl#";

    /** Web URL of the rules */
    public final static String RULES_URI =
            "https://www.dropbox.com/s/q4vyi7lyiwd4p53/rules.owl#";

    /** URI of my ontology */
    public static final String SEERC_URL =
            "http://seerc.org/ontologies/ontology.owl#";

    /** URI of my rules */
    public static final String SEERC_RULES_URL =
            "http://seerc.org/ontologies/rules.owl#";

    /** The address of the SSN ontology */
    public static final String BASE_URL = "http://purl.oclc.org/NET/ssnx/ssn#";

    /** URI of the RDF stream */
    public static final String RDF_STREAM = "http://seerc.org/rdf/stream/";

    /** Integer value 0 */
    public static final int ZERO = 0;

    /** Integer value 1 */
    public static final int ONE = 1;

    /** Integer value 2 */
    public static final int TWO = 2;

    /** Integer value 5 */
    public static final int FIVE = 5;

    /** Integer value 10 */
    public static final int TEN = 10;

    /** Integer value 100 */
    public static final int HUNDRED = 100;

    /** Integer value 1000 */
    public static final int THOUSAND = 1000;

}
