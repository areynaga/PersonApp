package be.steria.datapoc.test.camelroutes;

import junit.framework.Assert;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
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
import be.steria.datapoc.services.routes.OutputQueueRoute;
import be.steria.datapoc.test.jaxb.CommonJAXBTools;

public class OutputQueueTest extends CamelTestSupport{
	
	

	private String outputQueue;
	

	private CommonJAXBTools jaxbTools;

	private DeletePersonRequest request;
	private String replicationOperation; 
	private TestServiceReplicator testServiceReplicator = new TestServiceReplicator();
	
	@Override
	protected JndiRegistry createRegistry() throws Exception {
		JndiRegistry registry = super.createRegistry();
		registry.bind("serviceReplicator", testServiceReplicator);
		return registry;
	}
	
	
	
	private class TestServiceReplicator implements ServiceReplicator{

		public CUDReplica createReplicaForBroadcast(CUDRequest cudRequest) {
			// TODO Auto-generated method stub
			return null;
		}

		public CUDReplica createReplicaForCentral(CUDRequest cudRequest) {
			// TODO Auto-generated method stub
			return null;
		}

		public CUDReplica sendReplica(CUDReplica cudReplica)
				throws UnexpectedError {
			
			
			replicationOperation = "SEND";
			
			return null;
		}

		
		
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
		
		outputQueue = "seda:outputQueue";
		

		return context;
	}

	

	@Override
	protected RouteBuilder createRouteBuilder() throws Exception {
		DataFormat jaxb = new JaxbDataFormat("be.steria.datapoc.model");
		return new OutputQueueRoute(jaxb, outputQueue);

	}
	
	
	@Test
	public void test() throws Exception {
		CUDReplica cudReplica = new CUDReplica();
		cudReplica.setRequest(request);
		cudReplica.getDestination().add("A");
		cudReplica.getDestination().add("B");
		
		String xml = jaxbTools.marshal(cudReplica);
		
		template.sendBody("seda:outputQueue", xml);
		
		Thread.sleep(2000);
				
		Assert.assertEquals("SEND", replicationOperation);
		

	}
	
	
	
}
