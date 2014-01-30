package org.seerc.tbt.utils;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.AMQP.Queue.DeclareOk;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.DefaultConsumer;

/**
 * Manager class for managing all messaging-related activities.
 */
public class MessagingManager {

    /** Logger */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(MessagingManager.class);

    /** Single ton instance */
    private static MessagingManager _instance = null;

    /** An instance of CloudAMQP channel for sending monitored values */
    private Channel _clientMonitorChannel;

    /** An instance of CloudAMQP channel for sending monitored values */
    private Channel _workerMonitorChannel;

    /** An instance of the CloudAMQP channel for sending jobs */
    private Channel _cloudAmqpTaskChannel;

    /** An instance of the RabbitMQ Bigwig channel for sending jobs */
    private Channel _bigwigTaskChannel;

    /** The currently active messaging channel */
    private Channel _activeTaskChannel;

    /** TODO This is just a stub */
    private long _timestamp = 0;

    /**
     * Constructor
     */
    protected MessagingManager() {}

    /**
     * Get singleton instance
     * 
     * @return singleton instance of the messaging manager
     */
    public static MessagingManager getInstance() {
        if (_instance == null) {
            _instance = new MessagingManager();
        }
        return _instance;
    }

    /**
     * Initialize access to the CloudAMQP service for monitoring
     * 
     * @param aClient flag to specify if it is the client and should listen to
     *            the feedback from the monitor
     * @throws IOException exception
     */
    public void initClientMonitorMessaging(boolean aClient) throws IOException {

        String uri = Constants.CLIENT_MONITOR_QUEUE_HOST;

        ConnectionFactory factory = new ConnectionFactory();

        try {
            factory.setUri(uri);
        } catch (Exception e) {
            LOGGER.error("Error while setting the URI for the queue service!",
                    e);
        }

        Connection connection = factory.newConnection();
        _clientMonitorChannel = connection.createChannel();
        _clientMonitorChannel.queueDeclare(Constants.CLIENT_MONITOR_QUEUE,
                false, false, false, null);

        _clientMonitorChannel.queueDeclare(
                Constants.CLIENT_MONITOR_FEEDBACK_QUEUE, false, false, false,
                null);

        if (aClient) {
            _clientMonitorChannel.basicConsume(
                    Constants.CLIENT_MONITOR_FEEDBACK_QUEUE, true,
                    new DefaultConsumer(_clientMonitorChannel) {

                        @Override
                        public void handleDelivery(String aConsumerTag,
                                Envelope aEnvelope,
                                BasicProperties aProperties, byte[] aBody)
                                throws IOException {
                            changeActiveMessagingService();
                        }
                    });
        }

    }

    /**
     * Initialize access to the CloudAMQP service for feedback from monitoring
     * 
     * @throws IOException exception
     */
    public void initClientMonitorFeedbackMessaging() throws IOException {

        String uri = Constants.CLIENT_MONITOR_QUEUE_HOST;

        ConnectionFactory factory = new ConnectionFactory();

        try {
            factory.setUri(uri);
        } catch (Exception e) {
            LOGGER.error("Error while setting the URI for the queue service!",
                    e);
        }

        Connection connection = factory.newConnection();
        _clientMonitorChannel = connection.createChannel();
        _clientMonitorChannel.queueDeclare(Constants.CLIENT_MONITOR_QUEUE,
                false, false, false, null);

        _clientMonitorChannel.queueDeclare(
                Constants.CLIENT_MONITOR_FEEDBACK_QUEUE, false, false, false,
                null);

        _clientMonitorChannel.basicConsume(
                Constants.CLIENT_MONITOR_FEEDBACK_QUEUE, true,
                new DefaultConsumer(_clientMonitorChannel) {

                    @Override
                    public void handleDelivery(String aConsumerTag,
                            Envelope aEnvelope, BasicProperties aProperties,
                            byte[] aBody) throws IOException {
                        changeActiveMessagingService();
                    }
                });

    }

    /**
     * Initialize access to the CloudAMQP service for monitoring
     * 
     * @throws IOException exception
     */
    public void initWorkerMonitorMessaging() throws IOException {

        String uri = Constants.WORKER_MONITOR_QUEUE_HOST;

        ConnectionFactory factory = new ConnectionFactory();

        try {
            factory.setUri(uri);
        } catch (Exception e) {
            LOGGER.error("Error while setting the URI for the queue service!",
                    e);
        }

        Connection connection = factory.newConnection();
        _workerMonitorChannel = connection.createChannel();
        _workerMonitorChannel.queueDeclare(Constants.WORKER_MONITOR_QUEUE,
                false, false, false, null);

        _workerMonitorChannel.queueDeclare(
                Constants.WORKER_MONITOR_FEEDBACK_QUEUE, false, false, false,
                null);

        // _monitorChannel.basicConsume(Constants.MONITOR_FEEDBACK_QUEUE, true,
        // new DefaultConsumer(_monitorChannel) {
        //
        // @Override
        // public void handleDelivery(String aConsumerTag,
        // Envelope aEnvelope,
        // AMQP.BasicProperties aProperties, byte[] aBody)
        // throws IOException {
        // changeActiveMessagingService();
        // }
        // });

    }

    /**
     * Initialize access to the CloudAMQP service for sending tasks
     * 
     * @throws IOException exception
     */
    public void initCloudAMQPTaskMessaging() throws IOException {

        String uri = Constants.CLOUD_AMQP_TASK_HOST;
        // System.getenv("CLOUDAMQP_URL");
        // if (uri == null) {
        // uri = "amqp://guest:guest@localhost";
        // }
        LOGGER.info(uri);
        ConnectionFactory factory = new ConnectionFactory();

        try {
            factory.setUri(uri);
        } catch (Exception e) {
            LOGGER.error("Error while setting the URI for the queue service!",
                    e);
        }

        Connection connection = factory.newConnection();
        _cloudAmqpTaskChannel = connection.createChannel();
        _cloudAmqpTaskChannel.queueDeclare(Constants.TASK_QUEUE, false, false,
                false, null);

        _activeTaskChannel = _cloudAmqpTaskChannel;

    }

    /**
     * Initialize access to the RabbitMQ Bigwig service for sending tasks
     * 
     * @throws IOException exception
     */
    public void initBigwigTaskMessaging() throws IOException {

        String uri = Constants.BIGWIG_TASK_PUBLISH_HOST;
        // System.getenv("CLOUDAMQP_URL");
        // if (uri == null) {
        // uri = "amqp://guest:guest@localhost";
        // }
        LOGGER.info("TX = " + System.getenv("RABBITMQ_BIGWIG_TX_URL"));
        LOGGER.info("RX = " + System.getenv("RABBITMQ_BIGWIG_RX_URL"));
        ConnectionFactory factory = new ConnectionFactory();

        try {
            factory.setUri(uri);
        } catch (Exception e) {
            LOGGER.error("Error while setting the URI for the queue service!",
                    e);
        }

        Connection connection = factory.newConnection();
        _bigwigTaskChannel = connection.createChannel();
        _bigwigTaskChannel.queueDeclare(Constants.TASK_QUEUE, false, false,
                false, null);

    }

    /**
     * Send a message to the client-monitor queue
     * 
     * @param aMessage message to be sent
     */
    public void sendToClientMonitorQueue(String aMessage) {
        try {
            _clientMonitorChannel.basicPublish("",
                    Constants.CLIENT_MONITOR_QUEUE, null, aMessage.getBytes());
        } catch (IOException e) {
            LOGGER.error(
                    "Error sending he message to the client-monitor queue!", e);
        }
    }

    /**
     * Send a message to the client-monitor queue
     * 
     * @param aMessage message to be sent
     */
    public void sendToWorkerMonitorQueue(String aMessage) {
        try {
            _workerMonitorChannel.basicPublish("",
                    Constants.WORKER_MONITOR_QUEUE, null, aMessage.getBytes());
        } catch (IOException e) {
            LOGGER.error(
                    "Error sending he message to the worker-monitor queue!", e);
        }
    }

    /**
     * Send messages to the monitoring queue
     * 
     * @param aMessage message to be sent
     */
    public void sendToMonitoringFeedbackQueue(String aMessage) {
        try {
            _clientMonitorChannel.basicPublish("",
                    Constants.CLIENT_MONITOR_FEEDBACK_QUEUE, null,
                    aMessage.getBytes());
        } catch (IOException e) {
            LOGGER.error("Error sending he message to the monitoring queue!", e);
        }
    }

    /**
     * Send messages to the task queue
     * 
     * @param aMessage message to be sent
     */
    public void sendToTaskQueue(String aMessage) {
        try {
            _activeTaskChannel.basicPublish("", Constants.TASK_QUEUE, null,
                    aMessage.getBytes());
            LOGGER.info("Message was sent to the task queue: " + aMessage);
        } catch (IOException e) {
            LOGGER.error("Error sending he message to the task queue!", e);
        }

    }

    /**
     * Set new value to the active messaging service TODO have a enumerated list
     * of values to pick from
     */
    public void changeActiveMessagingService() {

        // TODO a real criteria to prevent the service from infinitely switching
        // between services
        if ((null != _activeTaskChannel)
                && ((System.currentTimeMillis() - _timestamp) > (Constants.THOUSAND * Constants.HUNDRED))) {
            if (_cloudAmqpTaskChannel == _activeTaskChannel) {
                _activeTaskChannel = _bigwigTaskChannel;
            } else {
                _activeTaskChannel = _cloudAmqpTaskChannel;
            }
            _timestamp = System.currentTimeMillis();
            LOGGER.info("The active messaging service has been set to "
                    + _activeTaskChannel.toString());
        }
    }

    /**
     * Returns the client-monitor channel
     * 
     * @return the client-monitor channel
     */
    public Channel getClientMonitorChannel() {
        return _clientMonitorChannel;
    }

    /**
     * Returns the worker-monitor channel
     * 
     * @return the worker-monitor channel
     */
    public Channel getWorkerMonitorChannel() {
        return _workerMonitorChannel;
    }

    /**
     * Returns the Cloud AMQP task channel
     * 
     * @return the Cloud AMQP task channel
     */
    public Channel getCloudAmqpTaskChannel() {
        return _cloudAmqpTaskChannel;
    }

    /**
     * Returns the RabbitMQ Bigwig task channel
     * 
     * @return the RabbitMQ Bigwig task channel
     */
    public Channel getBigwigTaskChannel() {
        return _bigwigTaskChannel;
    }

    /**
     * Return current number of messages in the queue
     * 
     * @return current number of messages
     */
    public int getMessageCount() {
        DeclareOk status = null;
        try {
            status =
                    _activeTaskChannel
                            .queueDeclarePassive(Constants.TASK_QUEUE);
            return status.getMessageCount();
        } catch (IOException e) {
            LOGGER.error("Error accessing the messaging channel!", e);
        }
        return 0;
    }

    /**
     * Purge the client-monitor queues
     */
    public void purgeClientMonitorQueue() {
        try {
            _clientMonitorChannel.queuePurge(Constants.CLIENT_MONITOR_QUEUE);
            _clientMonitorChannel
                    .queuePurge(Constants.CLIENT_MONITOR_FEEDBACK_QUEUE);
        } catch (IOException e) {
            LOGGER.error("Error when purging the client-monitor queues!", e);
        }
    }

    /**
     * Purge the worker-monitor queues
     */
    public void purgeWorkerMonitorQueue() {
        try {
            _workerMonitorChannel.queuePurge(Constants.WORKER_MONITOR_QUEUE);
            _workerMonitorChannel
                    .queuePurge(Constants.WORKER_MONITOR_FEEDBACK_QUEUE);
        } catch (IOException e) {
            LOGGER.error("Error when purging the worker-monitor queues!", e);
        }
    }

    /**
     * Purge the task queues
     */
    public void purgeTaskQueue() {
        try {
            _cloudAmqpTaskChannel.queuePurge(Constants.TASK_QUEUE);
            _bigwigTaskChannel.queuePurge(Constants.TASK_QUEUE);
        } catch (IOException e) {
            LOGGER.error("Error when purging the task queue!", e);
        }

    }

}
