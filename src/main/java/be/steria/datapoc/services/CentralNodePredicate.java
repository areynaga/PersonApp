package be.steria.datapoc.services;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;

public class CentralNodePredicate implements Predicate {

	private NodesInformation nodesInformation;
	
	

	public boolean matches(Exchange exchange) {
		return nodesInformation.getCurrentNodeId().equals(nodesInformation.getCentralNodeId());
	}



	public NodesInformation getNodesInformation() {
		return nodesInformation;
	}



	public void setNodesInformation(NodesInformation nodesInformation) {
		this.nodesInformation = nodesInformation;
	}

}
