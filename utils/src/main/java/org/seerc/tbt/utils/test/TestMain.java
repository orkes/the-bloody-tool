package org.seerc.tbt.utils.test;

import java.io.IOException;

import org.seerc.tbt.utils.MessagingManager;
import org.seerc.tbt.utils.OntologyManager;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectProperty;

/**
 * 
 */
public class TestMain {

    /**
     * @param args
     */
    public static void main(String[] args) {

        String string = "\"100\"^^<http://www.w3.org/2001/XMLSchema#int>";

        OntologyManager oManager = OntologyManager.getInstance();

        OWLDataProperty prop =
                oManager.getDataProperty("http://seerc.org/ontology.owl#hasDatabaseSizeCritical");
        OWLObjectProperty oprop =
                oManager.getObjectProperty("http://seerc.org/ontology.owl#hasDatabaseSizeCritical");
        System.out.println(prop);
        System.out.println(oprop);

        // OWLLiteral literal = oManager.toOWLLiteral(string);

        MessagingManager mm = MessagingManager.getInstance();

        try {
            mm.initBigwigMonitorConsuming();
            mm.initBigwigMonitorPublishing();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mm.sendToMonitorQueue("Hello World!1");
        mm.sendToMonitorQueue("Hello World!2");
        mm.sendToMonitorQueue("Hello World!3");
        mm.sendToMonitorQueue("Hello World!4");

        try {
            Thread.currentThread();
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("DONE!");

    }

}
