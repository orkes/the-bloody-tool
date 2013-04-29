package org.seerc.monitor;

/**
 * Class for storing parameters for C-SPARQL queries
 */
public class QueryParams {

    /** The number of C-SPARQL queries */
    private static int numberOfQueries;

    /** The initial threshold value for the query */
    private static int initialThreshold;

    /**
     * Gets the number of queries
     * 
     * @return number of queries
     */
    public int getNumberOfQueries() {
        return numberOfQueries;
    }

    /**
     * Sets the number of queries
     * 
     * @param aNumber number of queries
     */
    public void setNumberOfQueries(int aNumber) {
        numberOfQueries = aNumber;
    }

    /**
     * Gets the initial threshold value
     * 
     * @return threshold value
     */
    public int getInitialThreshold() {
        return initialThreshold;
    }

    /**
     * Sets the initial threshold value
     * 
     * @param aThreshold threshold value
     */
    public void setInitialThreshold(int aThreshold) {
        initialThreshold = aThreshold;
    }

    @Override
    public String toString() {
        return "Query parameters [Number of queries = " + numberOfQueries
                + " Initial value " + initialThreshold + "]";
    }

}
