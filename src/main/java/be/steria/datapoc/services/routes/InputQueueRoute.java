package be.steria.datapoc.services.routes;

import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spi.DataFormat;

public class InputQueueRoute extends RouteBuilder {
	private String outputQueue;
	private String inputQueue;
	private Predicate predicate;
	private DataFormat jaxb;

	
	public InputQueueRoute(String outputQueue, String inputQueue,
			Predicate predicate, DataFormat jaxb) {
		super();
		this.outputQueue = outputQueue;
		this.inputQueue = inputQueue;
		this.predicate = predicate;
		this.jaxb = jaxb;
	}


	@Override
	public void configure() throws Exception {
		
		
		from(inputQueue)
		.unmarshal(jaxb)
		.beanRef("personProcessor", "processRequest")
		.filter(predicate)
		.beanRef("serviceReplicator", "createReplicaForBroadcast")
		.marshal(jaxb)
		.to(outputQueue);
		
	}
}