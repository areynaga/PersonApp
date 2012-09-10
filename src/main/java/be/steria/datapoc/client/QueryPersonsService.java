
package be.steria.datapoc.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.3-b02-
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "QueryPersonsService", targetNamespace = "http://services.datapoc.steria.be/", wsdlLocation = "file:/C:/data_cons_poc/wspace/PersonApp/src/main/resources/QueryPersons.wsdl")
public class QueryPersonsService
    extends Service
{

    private final static URL QUERYPERSONSSERVICE_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(be.steria.datapoc.client.QueryPersonsService.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = be.steria.datapoc.client.QueryPersonsService.class.getResource(".");
            url = new URL(baseUrl, "file:/C:/data_cons_poc/wspace/PersonApp/src/main/resources/QueryPersons.wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'file:/C:/data_cons_poc/wspace/PersonApp/src/main/resources/QueryPersons.wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        QUERYPERSONSSERVICE_WSDL_LOCATION = url;
    }

    public QueryPersonsService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public QueryPersonsService() {
        super(QUERYPERSONSSERVICE_WSDL_LOCATION, new QName("http://services.datapoc.steria.be/", "QueryPersonsService"));
    }

    /**
     * 
     * @return
     *     returns QueryPersons
     */
    @WebEndpoint(name = "QueryPersonsPort")
    public QueryPersons getQueryPersonsPort() {
        return super.getPort(new QName("http://services.datapoc.steria.be/", "QueryPersonsPort"), QueryPersons.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns QueryPersons
     */
    @WebEndpoint(name = "QueryPersonsPort")
    public QueryPersons getQueryPersonsPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://services.datapoc.steria.be/", "QueryPersonsPort"), QueryPersons.class, features);
    }

}
