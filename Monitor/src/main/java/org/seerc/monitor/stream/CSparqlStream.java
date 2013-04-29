package org.seerc.monitor.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.larkc.csparql.cep.api.RdfQuadruple;
import eu.larkc.csparql.cep.api.RdfStream;

/**
 * C-SPARQL stream.
 */
public class CSparqlStream extends RdfStream implements Runnable {

    /** The logger. */
    private final Logger logger = LoggerFactory.getLogger(CSparqlStream.class);

    /**
     * Constructor.
     * 
     * @param aIri IRI of the RDF stream
     */
    public CSparqlStream(final String aIri) {
        super(aIri);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {

    }

    /**
     * Appends a new triple to the RDF stream.
     * 
     * @param aSubject subject
     * @param aPredicate predicate
     * @param aObject object
     */
    public final void append(final String aSubject, final String aPredicate,
            final String aObject) {
        final RdfQuadruple quadruple =
                new RdfQuadruple(aSubject, aPredicate, aObject,
                        System.nanoTime());
        put(quadruple);
        logger.info(quadruple.toString());
    }

}
