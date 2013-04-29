package org.seerc.webclient;

/**
 * Class for storing parameters for invoking a web service
 */
public class InvocationParams {

    /** The number of parallel threads accessing a web-service */
    private static int numberOfThreads;

    /** The number of invocations for each thread */
    private static int numberOfInvocations;

    /**
     * Gets the number of threads
     * 
     * @return number of threads
     */
    public int getNumberOfThreads() {
        return numberOfThreads;
    }

    /**
     * Sets the number of threads
     * 
     * @param aNumber number of threads
     */
    public void setNumberOfThreads(int aNumber) {
        numberOfThreads = aNumber;
    }

    /**
     * Gets the number of invocations
     * 
     * @return number of invocations
     */
    public int getNumberOfInvocations() {
        return numberOfInvocations;
    }

    /**
     * Sets the number of invocations
     * 
     * @param aNumber number of invocations
     */
    public void setNumberOfInvocations(int aNumber) {
        numberOfInvocations = aNumber;
    }

    @Override
    public String toString() {
        return "Invocation parameters [Number of threads = " + numberOfThreads
                + " Number of invocations = " + numberOfInvocations + "]";
    }

}
