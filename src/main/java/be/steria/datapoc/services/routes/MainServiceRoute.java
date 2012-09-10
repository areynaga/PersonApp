package be.steria.datapoc.services.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.spi.DataFormat;


import be.steria.datapoc.services.CentralNodePredicate;
import be.steria.datapoc.services.ServerSourcePredicate;
import be.steria.datapoc.services.NodesInformation;
import be.steria.datapoc.services.routes.InputQueueRoute;
import be.steria.datapoc.services.routes.OutputQueueRoute;
import be.steria.datapoc.services.routes.SoapServiceRoute;


public class MainServiceRoute extends RouteBuilder {
	
	private NodesInformation nodesInformation;

	private CentralNodePredicate centralNodePredicate;
	
	private ServerSourcePredicate serverSourcePredicate;
	

	public ServerSourcePredicate getServerSourcePredicate() {
		return serverSourcePredicate;
	}



	public void setServerSourcePredicate(ServerSourcePredicate serverSourcePredicate) {
		this.serverSourcePredicate = serverSourcePredicate;
	}



	public NodesInformation getNodesInformation() {
		return nodesInformation;
	}



	public void setNodesInformation(NodesInformation nodesInformation) {
		this.nodesInformation = nodesInformation;
	}



	public CentralNodePredicate getCentralNodePredicate() {
		return centralNodePredicate;
	}



	public void setCentralNodePredicate(CentralNodePredicate centralNodePredicate) {
		this.centralNodePredicate = centralNodePredicate;
	}



	@Override
	public void configure() throws Exception {
		
		String inputQueueName = "inputQueue" + nodesInformation.getCurrentNodeId();
		String outputQueueName = "outputQueue" + nodesInformation.getCurrentNodeId();
		
		DataFormat jaxb = new JaxbDataFormat("be.steria.datapoc.model");
		
		
		
		getContext().addRoutes(new SoapServiceRoute("cxf:bean:personEndpoint", jaxb, 
				"activemq:queue:" + inputQueueName, 
				"activemq:queue:" + outputQueueName,
				serverSourcePredicate));
		
		getContext().addRoutes(new OutputQueueRoute(jaxb, 
				"activemq:queue:" + outputQueueName));
		
		getContext().addRoutes(new InputQueueRoute("activemq:queue:" + outputQueueName, 
				"activemq:queue:" + inputQueueName, centralNodePredicate, jaxb));
		
	}



	
	

}
