
package org.seerc.ws.bean;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.seerc.ws.bean package. 
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

    private final static QName _GenerateHash_QNAME = new QName("http://bean.ws.seerc.org/", "generateHash");
    private final static QName _GenerateHashResponse_QNAME = new QName("http://bean.ws.seerc.org/", "generateHashResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.seerc.ws.bean
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GenerateHash }
     * 
     */
    public GenerateHash createGenerateHash() {
        return new GenerateHash();
    }

    /**
     * Create an instance of {@link GenerateHashResponse }
     * 
     */
    public GenerateHashResponse createGenerateHashResponse() {
        return new GenerateHashResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GenerateHash }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bean.ws.seerc.org/", name = "generateHash")
    public JAXBElement<GenerateHash> createGenerateHash(GenerateHash value) {
        return new JAXBElement<GenerateHash>(_GenerateHash_QNAME, GenerateHash.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GenerateHashResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bean.ws.seerc.org/", name = "generateHashResponse")
    public JAXBElement<GenerateHashResponse> createGenerateHashResponse(GenerateHashResponse value) {
        return new JAXBElement<GenerateHashResponse>(_GenerateHashResponse_QNAME, GenerateHashResponse.class, null, value);
    }

}
