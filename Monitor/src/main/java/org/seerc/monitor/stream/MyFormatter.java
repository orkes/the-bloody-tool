package org.seerc.monitor.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.larkc.csparql.common.RDFTable;
import eu.larkc.csparql.common.streams.format.GenericObservable;
import eu.larkc.csparql.core.ResultFormatter;
import eu.larkc.csparql.streams.formats.CSparqlQuery;

/**
 * Formatter for SPARQL queries.
 */
public class MyFormatter extends ResultFormatter {

    /** Integer value. */
    private static final int TEN = 10;

    /** The logger. */
    private final Logger logger = LoggerFactory.getLogger(MyFormatter.class);

    private final String queryId;

    private final String queryCommand;

    public MyFormatter(String aId, String aCommand) {
        super();
        queryId = aId;
        queryCommand = aCommand;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void update(final GenericObservable<RDFTable> arg0,
            final RDFTable aRDFTable) {
        if (TEN < aRDFTable.size()) {
            logger.warn("Query " + queryId + " [" + queryCommand
                    + "] warns: THERE ARE ALREADY " + aRDFTable.size()
                    + " INSTANCES OF HIGH RESPONSE TIME!");
        }
    }
}
