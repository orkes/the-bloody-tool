package org.seerc.tbt.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;

/**
 * Utilities
 */
public class Utils {

    /** Logger */
    protected static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

    /**
     * Construct an RDF triple
     * 
     * @param aSubject subject
     * @param aPredicate predicate
     * @param aObject object
     * @return RDF triple
     */
    public static Triple toTriple(String aSubject, String aPredicate,
            String aObject) {
        Node subject = Node.createURI(aSubject);
        Node predicate = Node.createURI(aPredicate);
        Node object = Node.createURI(aObject);

        Triple result = new Triple(subject, predicate, object);

        return result;

    }

}
