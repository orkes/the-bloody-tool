package org.seerc.tbt.utils.iface;

/**
 * Interface for measuring execution time. Subclasses should implement here
 * functionality of sending RDFs to the monitoring queue
 */
public interface ISensor {

    /**
     * Method for sensing execution time.
     */
    public void senseExecutionTime();

    /**
     * Method for sensing queue size. Subclasses should implement here
     * functionality of sending RDFs to the monitoring queue.
     */
    public void senseQueueSize();
}
