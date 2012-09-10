package be.steria.datapoc.services.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spi.DataFormat;

import be.steria.datapoc.model.CUDRequest;
import be.steria.datapoc.services.ServerSourcePredicate;

public class SoapServiceRoute extends RouteBuilder{

	private String cxfEndPoint;
	private DataFormat jaxb;
	private String inputQueue;
	private String outputQueue;
	
	
	private ServerSourcePredicate serverSourcePredicate;
	
	
	public SoapServiceRoute(String cxfEndPoint, DataFormat jaxb,
			String inputQueue, String outputQueue,
			ServerSourcePredicate serverSourcePredicate) {
		super();
		this.cxfEndPoint = cxfEndPoint;
		this.jaxb = jaxb;
		this.inputQueue = inputQueue;
		this.outputQueue = outputQueue;
		this.serverSourcePredicate = serverSourcePredicate;
	}



	@Override
	public void configure() throws Exception {
		from(cxfEndPoint)
		.choice()
			.when(serverSourcePredicate)
				.process(new Processor() {
					public void process(Exchange exchange) throws Exception {
						exchange.getOut().setBody(exchange.getIn().getBody(CUDRequest.class));
					}
				})
				.marshal(jaxb)
				.inOnly(inputQueue)
				.beanRef("personProcessor", "createResponse")
			.otherwise()
				.transacted()
				.beanRef("personProcessor", "processRequest")
				.beanRef("serviceReplicator", "createReplicaForCentral")
				.marshal(jaxb)
				.to("log:out")
				.inOnly(outputQueue)
				.beanRef("personProcessor", "createResponse");
		
	}




}
