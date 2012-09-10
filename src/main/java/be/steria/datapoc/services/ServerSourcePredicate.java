package be.steria.datapoc.services;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;

import be.steria.datapoc.model.CUDRequest;



public class ServerSourcePredicate implements Predicate {

	private NodesInformation nodesInformation;
	
	public NodesInformation getNodesInformation() {
		return nodesInformation;
	}

	public void setNodesInformation(NodesInformation nodesInformation) {
		this.nodesInformation = nodesInformation;
	}

	public boolean matches(Exchange exchange) {
		
		String source = exchange.getIn().getBody(CUDRequest.class).getSource();
		int sourceId;
		
		if (source == null)
			return false;
		
		try{
			sourceId = Integer.parseInt(source);
			
			if (0<=sourceId && sourceId<nodesInformation.getLocalNodeList().size())
				return true;
			else
				return false;

		} catch (NumberFormatException nfx) {
			return false;
		}
			
		
	}
	
}
