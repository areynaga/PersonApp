package be.steria.datapoc.test.jaxb;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

public class CommonJAXBTools {
	
	private JAXBContext context;
	
	
	public CommonJAXBTools(String packageName) throws JAXBException {
		context = JAXBContext.newInstance(packageName);
	}
	
	@SuppressWarnings("rawtypes")
	public CommonJAXBTools(Class jaxbClass) throws JAXBException {
		context = JAXBContext.newInstance(jaxbClass);
	}



	public String marshal(Object object) throws JAXBException {
		Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
 
        OutputStream output = new OutputStream() {
            private StringBuilder string = new StringBuilder();
            @Override
            public void write(int b) throws IOException {
                this.string.append((char) b );
            }
            @Override
            public String toString(){
                return this.string.toString();
            }
        };
 
        marshaller.marshal(object, output);
        return output.toString();

	}
	
	public Object unMarshal(String xml) throws JAXBException {
		Unmarshaller u = context.createUnmarshaller();
		StringBuffer xmlStr = new StringBuffer(xml);
	    return u.unmarshal( new StreamSource( new StringReader( xmlStr.toString() ) ) );
	}
}
