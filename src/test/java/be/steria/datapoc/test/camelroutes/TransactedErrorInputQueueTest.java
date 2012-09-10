package be.steria.datapoc.test.camelroutes;

import java.io.File;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.spi.DataFormat;
import org.apache.camel.test.junit4.CamelSpringTestSupport;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import be.steria.datapoc.model.Address;
import be.steria.datapoc.model.CreatePersonRequest;
import be.steria.datapoc.model.Person;
import be.steria.datapoc.services.QueryPersons;
import be.steria.datapoc.test.jaxb.CommonJAXBTools;

public class TransactedErrorInputQueueTest extends CamelSpringTestSupport {

private CommonJAXBTools jaxbTools;
	
	
	private CreatePersonRequest request;
	
	
	
	
	
	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("applicationContext-test.xml");
	}
	
	
	
	
	@Override
	protected RouteBuilder createRouteBuilder() throws Exception {
		
		
		return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
            	DataFormat jaxb = new JaxbDataFormat("be.steria.datapoc.model");
            	
            	from("activemq:queue:inputQueue")
            	.transacted()
            	.unmarshal(jaxb)
        		.beanRef("personProcessor", "processRequest")
        		.marshal(jaxb)
        		.to("activemq:queue:outputQueue")
        		.throwException(new Exception("Test exception"))
        		;
            }
		};
		
		
		

	}
	
	
	
	
	@Before
	public void before() throws Exception {
		request = new CreatePersonRequest();
		request.setSource("AB");
		Person person = new Person();
        person.setIdPerson("ABC");
        person.setFirstName("Pedro");
        person.setLastName("Perez");
        person.setBirthDate(new java.util.Date());
        
        Address address = new Address();
        address.setCity("La Paz");
        address.setCountry("Bolivia");
        address.setNumber(92);
        address.setStreet("Avaroa");
    	person.getAddress().add(address);
    	request.setPerson(person);

		jaxbTools = new CommonJAXBTools("be.steria.datapoc.model");
	}
	
	@Test
	public void testWithExceptions() throws Exception {
		String xml = jaxbTools.marshal(request);
		
		template.sendBody("activemq:queue:inputQueue", xml);
		Thread.sleep(5000);
		
		String outputXml = consumer.receiveBodyNoWait("activemq:queue:outputQueue", String.class);
		
		QueryPersons qryPersons = (QueryPersons) applicationContext.getBean("queryPersons");
		
		assertNull(qryPersons.getPersonById("ABC"));

		
		assertNull(outputXml);
		
		String inputXml = consumer.receiveBodyNoWait("activemq:queue:ActiveMQ.DLQ", String.class);
		assertNotNull("Should not lose message", inputXml);
		
		
	}
	
	@Override
	@After
	public void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
		FileUtils.deleteDirectory(new File("./temp"));
	}
}
