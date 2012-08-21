package be.steria.datapoc.services.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spi.DataFormat;

public class OutputQueueRoute extends RouteBuilder {

	
	private DataFormat jaxb;
	private String outputQueue;
	
	
	
	
	
	public OutputQueueRoute(DataFormat jaxb, String outputQueue) {
		super();
		this.jaxb = jaxb;
		this.outputQueue = outputQueue;
		
	}




	@Override
	public void configure() throws Exception {
		from(outputQueue)
		.unmarshal(jaxb)
		.beanRef("serviceReplicator", "sendReplica");
	}

}
