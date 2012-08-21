package be.steria.datapoc.services.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.spi.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;

import be.steria.datapoc.services.CentralNodePredicate;
import be.steria.datapoc.services.routes.InputQueueRoute;
import be.steria.datapoc.services.routes.OutputQueueRoute;
import be.steria.datapoc.services.routes.SoapServiceRoute;


public class MainServiceRoute extends RouteBuilder {
	
	private String serverName;
	
	private String inputQueueName;
	private String outputQueueName;
	
	
	
	public String getInputQueueName() {
		return inputQueueName;
	}

	public void setInputQueueName(String inputQueueName) {
		this.inputQueueName = inputQueueName;
	}

	public String getOutputQueueName() {
		return outputQueueName;
	}

	public void setOutputQueueName(String outputQueueName) {
		this.outputQueueName = outputQueueName;
	}

	@Autowired
	private CentralNodePredicate centralNodePredicate;

	@Override
	public void configure() throws Exception {
		
		DataFormat jaxb = new JaxbDataFormat("be.steria.datapoc.model");
		
		
		getContext().addRoutes(new SoapServiceRoute("cxf:bean:personEndpoint", jaxb, 
				"activemq:queue:" + inputQueueName, 
				"activemq:queue:" + outputQueueName));
		
		getContext().addRoutes(new OutputQueueRoute(jaxb, 
				"activemq:queue:" + outputQueueName));
		
		getContext().addRoutes(new InputQueueRoute("activemq:queue:" + outputQueueName, 
				"activemq:queue:" + inputQueueName, centralNodePredicate, jaxb));
		
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

}
