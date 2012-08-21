package be.steria.datapoc.test.camelroutes;


import junit.framework.Assert;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.impl.JndiRegistry;
import org.apache.camel.spi.DataFormat;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Before;
import org.junit.Test;

import be.steria.datapoc.model.CUDReplica;
import be.steria.datapoc.model.CUDRequest;
import be.steria.datapoc.model.DeletePersonRequest;
import be.steria.datapoc.services.ServiceReplicator;
import be.steria.datapoc.services.UnexpectedError;
import be.steria.datapoc.services.routes.InputQueueRoute;
import be.steria.datapoc.test.PipeBean;
import be.steria.datapoc.test.jaxb.CommonJAXBTools;

public class InputQueueTest extends CamelTestSupport {

	private boolean centralNode;

	private String outputQueue;
	private String inputQueue;
	private Predicate predicate;
	private String logServiceReplicator;
	
	private TestServiceReplicator testServiceReplicator = new TestServiceReplicator();

	private CommonJAXBTools jaxbTools;

	private DeletePersonRequest request;

	

	private class TestCentralNodePredicate implements Predicate {

		public boolean matches(Exchange exchange) {
			return centralNode;
		}

	}
	
	private class TestServiceReplicator implements ServiceReplicator {

		public CUDReplica createReplicaForBroadcast(CUDRequest cudRequest) {
			CUDReplica cudReplica = new CUDReplica();
			cudReplica.getDestination().add("DEST_A");
			cudReplica.getDestination().add("DEST_B");
			cudReplica.getDestination().add("DEST_C");
			cudReplica.setRequest(request);
			logServiceReplicator = "BROADCAST";
			
			return cudReplica;
		}

		public CUDReplica createReplicaForCentral(CUDRequest cudRequest) {
			CUDReplica cudReplica = new CUDReplica();
			cudReplica.getDestination().add("CENTRAL");
			cudReplica.setRequest(request);
			logServiceReplicator = "CENTRAL";
			
			return cudReplica;
		}

		public CUDReplica sendReplica(CUDReplica cudReplica)
				throws UnexpectedError {
			// TODO Auto-generated method stub
			return null;
		}
		
	}


	@Override
	protected JndiRegistry createRegistry() throws Exception {
		JndiRegistry registry = super.createRegistry();
		registry.bind("personProcessor", new PipeBean());
		registry.bind("serviceReplicator", testServiceReplicator);
		return registry;
	}

	@Before
	public void before() throws Exception {
		request = new DeletePersonRequest();
		request.setIdPerson("12");
		request.setSource("AB");

		jaxbTools = new CommonJAXBTools("be.steria.datapoc.model");

	}


	@Override
	protected CamelContext createCamelContext() throws Exception {
		CamelContext context = super.createCamelContext();
		
		outputQueue = "mock:outputQueue";
		inputQueue = "seda:inputQueue";
		predicate = new TestCentralNodePredicate();

		return context;
	}

	

	@Override
	protected RouteBuilder createRouteBuilder() throws Exception {
		DataFormat jaxb = new JaxbDataFormat("be.steria.datapoc.model");
		return new InputQueueRoute(outputQueue, inputQueue, predicate, jaxb);

	}

	@Test
	public void testCentralNode() throws Exception {
		MockEndpoint mockQueue = getMockEndpoint("mock:outputQueue");
		mockQueue.expectedMessageCount(1);
		String xml = jaxbTools.marshal(request);
		
		
		CUDReplica expectedCudReplica = new CUDReplica();
		expectedCudReplica.getDestination().add("DEST_A");
		expectedCudReplica.getDestination().add("DEST_B");
		expectedCudReplica.getDestination().add("DEST_C");
		expectedCudReplica.setRequest(request);
		String expectedXml = jaxbTools.marshal(expectedCudReplica);
		
		centralNode = true;

		template.sendBody("seda:inputQueue", xml);
		
		
		
		
		mockQueue.expectedBodiesReceived(expectedXml);

		mockQueue.assertIsSatisfied();
		
		Assert.assertEquals("BROADCAST", logServiceReplicator);

	}
	
	@Test
	public void testNoCentralNode() throws Exception {
		MockEndpoint mockQueue = getMockEndpoint("mock:outputQueue");
		mockQueue.expectedMessageCount(0);
		String xml = jaxbTools.marshal(request);
		centralNode = false;

		template.sendBody("seda:inputQueue", xml);
		
		mockQueue.assertIsSatisfied();
		
		
	}
	
	
	

}
