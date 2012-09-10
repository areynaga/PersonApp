
package be.steria.datapoc.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the be.steria.datapoc.client package. 
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

    private final static QName _ServiceResponse_QNAME = new QName("http://datapoc.steria.be/model", "serviceResponse");
    private final static QName _UpdatePersonRequest_QNAME = new QName("http://datapoc.steria.be/model", "updatePersonRequest");
    private final static QName _CreatePersonRequest_QNAME = new QName("http://datapoc.steria.be/model", "createPersonRequest");
    private final static QName _DeletePersonRequest_QNAME = new QName("http://datapoc.steria.be/model", "deletePersonRequest");
    private final static QName _UnexpectedError_QNAME = new QName("http://datapoc.steria.be/model", "unexpectedError");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: be.steria.datapoc.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DeletePersonRequest }
     * 
     */
    public DeletePersonRequest createDeletePersonRequest() {
        return new DeletePersonRequest();
    }

    /**
     * Create an instance of {@link UnexpectedError }
     * 
     */
    public UnexpectedError createUnexpectedError() {
        return new UnexpectedError();
    }

    /**
     * Create an instance of {@link CreatePersonRequest }
     * 
     */
    public CreatePersonRequest createCreatePersonRequest() {
        return new CreatePersonRequest();
    }

    /**
     * Create an instance of {@link ServiceResponse }
     * 
     */
    public ServiceResponse createServiceResponse() {
        return new ServiceResponse();
    }

    /**
     * Create an instance of {@link Address }
     * 
     */
    public Address createAddress() {
        return new Address();
    }

    /**
     * Create an instance of {@link Person }
     * 
     */
    public Person createPerson() {
        return new Person();
    }

    /**
     * Create an instance of {@link UpdatePersonRequest }
     * 
     */
    public UpdatePersonRequest createUpdatePersonRequest() {
        return new UpdatePersonRequest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServiceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://datapoc.steria.be/model", name = "serviceResponse")
    public JAXBElement<ServiceResponse> createServiceResponse(ServiceResponse value) {
        return new JAXBElement<ServiceResponse>(_ServiceResponse_QNAME, ServiceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdatePersonRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://datapoc.steria.be/model", name = "updatePersonRequest")
    public JAXBElement<UpdatePersonRequest> createUpdatePersonRequest(UpdatePersonRequest value) {
        return new JAXBElement<UpdatePersonRequest>(_UpdatePersonRequest_QNAME, UpdatePersonRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreatePersonRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://datapoc.steria.be/model", name = "createPersonRequest")
    public JAXBElement<CreatePersonRequest> createCreatePersonRequest(CreatePersonRequest value) {
        return new JAXBElement<CreatePersonRequest>(_CreatePersonRequest_QNAME, CreatePersonRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeletePersonRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://datapoc.steria.be/model", name = "deletePersonRequest")
    public JAXBElement<DeletePersonRequest> createDeletePersonRequest(DeletePersonRequest value) {
        return new JAXBElement<DeletePersonRequest>(_DeletePersonRequest_QNAME, DeletePersonRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UnexpectedError }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://datapoc.steria.be/model", name = "unexpectedError")
    public JAXBElement<UnexpectedError> createUnexpectedError(UnexpectedError value) {
        return new JAXBElement<UnexpectedError>(_UnexpectedError_QNAME, UnexpectedError.class, null, value);
    }

}
