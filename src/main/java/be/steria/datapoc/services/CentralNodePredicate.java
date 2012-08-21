package be.steria.datapoc.services;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;

public class CentralNodePredicate implements Predicate {

	private String centralNode;
	
	public String getCentralNode() {
		return centralNode;
	}

	public void setCentralNode(String centralNode) {
		this.centralNode = centralNode;
	}

	public boolean matches(Exchange exchange) {
		return centralNode.equals("true");
	}

}
