package be.steria.datapoc.services.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spi.DataFormat;

import be.steria.datapoc.EventLogger.model.NodeEvent;
import be.steria.datapoc.model.CUDRequest;
import be.steria.datapoc.services.NodeLogger;
import be.steria.datapoc.services.ServerSourcePredicate;

public class SoapServiceRoute extends RouteBuilder{

	private String cxfEndPoint;
	private DataFormat jaxb;
	private String inputQueue;
	private String outputQueue;
	
	private NodeLogger nodeLogger;
	
	
	public SoapServiceRoute(String cxfEndPoint, DataFormat jaxb,
			String inputQueue, String outputQueue) {
		super();
		this.cxfEndPoint = cxfEndPoint;
		this.jaxb = jaxb;
		this.inputQueue = inputQueue;
		this.outputQueue = outputQueue;
	}



	@Override
	public void configure() throws Exception {
		from(cxfEndPoint)
		.choice()
			.when(new ServerSourcePredicate())
				.process(new Processor() {
					public void process(Exchange exchange) throws Exception {
						exchange.getOut().setBody(exchange.getIn().getBody(CUDRequest.class));
					}
				})
				.marshal(jaxb)
				.inOnly(inputQueue)
				.beanRef("personProcessor", "createResponse")
			.otherwise()
				.beanRef("personProcessor", "processRequest")
				.beanRef("serviceReplicator", "createReplicaForCentral")
				.marshal(jaxb)
				.inOnly(outputQueue)
				.beanRef("personProcessor", "createResponse");
		
	}



	public NodeLogger getNodeLogger() {
		return nodeLogger;
	}



	public void setNodeLogger(NodeLogger nodeLogger) {
		this.nodeLogger = nodeLogger;
	}

}
