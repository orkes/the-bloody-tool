
package org.seerc.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.seerc.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetHelloWorldResponse_QNAME = new QName("http://ws.seerc.org/", "getHelloWorldResponse");
    private final static QName _GetHelloWorld_QNAME = new QName("http://ws.seerc.org/", "getHelloWorld");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.seerc.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetHelloWorld }
     * 
     */
    public GetHelloWorld createGetHelloWorld() {
        return new GetHelloWorld();
    }

    /**
     * Create an instance of {@link GetHelloWorldResponse }
     * 
     */
    public GetHelloWorldResponse createGetHelloWorldResponse() {
        return new GetHelloWorldResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetHelloWorldResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.seerc.org/", name = "getHelloWorldResponse")
    public JAXBElement<GetHelloWorldResponse> createGetHelloWorldResponse(GetHelloWorldResponse value) {
        return new JAXBElement<GetHelloWorldResponse>(_GetHelloWorldResponse_QNAME, GetHelloWorldResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetHelloWorld }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.seerc.org/", name = "getHelloWorld")
    public JAXBElement<GetHelloWorld> createGetHelloWorld(GetHelloWorld value) {
        return new JAXBElement<GetHelloWorld>(_GetHelloWorld_QNAME, GetHelloWorld.class, null, value);
    }

}
