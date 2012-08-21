package be.steria.datapoc.services;


import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import be.steria.datapoc.model.CUDReplica;
import be.steria.datapoc.model.CUDRequest;
import be.steria.datapoc.model.CreatePersonRequest;
import be.steria.datapoc.model.DeletePersonRequest;
import be.steria.datapoc.model.UpdatePersonRequest;

public class ServiceReplicatorImpl implements ServiceReplicator{
	
	
	
	private NodesInformation nodesInformation;
	
	
	

	
	
	private void replicateToNode(CUDRequest cudRequest, String destination) throws UnexpectedError {
		PersonService personService = null;
		JaxWsProxyFactoryBean clientFactory = new JaxWsProxyFactoryBean();
        clientFactory.setAddress(nodesInformation.getAddress(destination));
		clientFactory.setServiceClass(PersonService.class);
		personService = (PersonService) clientFactory.create();
		System.out.println("Sending to " + destination);
		
		if (cudRequest instanceof CreatePersonRequest)
			personService.createPerson((CreatePersonRequest) cudRequest);
		else if (cudRequest instanceof UpdatePersonRequest)
			personService.updatePerson((UpdatePersonRequest) cudRequest);
		else if (cudRequest instanceof DeletePersonRequest)
			personService.deletePerson((DeletePersonRequest) cudRequest);
		
	}

	


	public NodesInformation getNodesInformation() {
		return nodesInformation;
	}


	public void setNodesInformation(NodesInformation nodesInformation) {
		this.nodesInformation = nodesInformation;
	}
	
		
	public CUDReplica createReplicaForBroadcast(CUDRequest cudRequest) {
		CUDReplica replica = new CUDReplica();
		replica.setRequest(cudRequest);
		replica.setDestination(getDestinations(cudRequest.getSource()));
		cudRequest.setSource(nodesInformation.getCurrentNodeName());
		
		return replica;
	}
	
	public CUDReplica createReplicaForCentral(CUDRequest cudRequest) {
		CUDReplica replica = new CUDReplica();
		replica.setRequest(cudRequest);
		cudRequest.setSource(nodesInformation.getCurrentNodeName());
		replica.getDestination().add(nodesInformation.getCentralNodeName());
		return replica;
	}
	
	private List<String> getDestinations(String source) {
		List<String> destinations = new ArrayList<String>();
		for (String nodeName : nodesInformation.getLocalNodeList()) {
			if (!nodeName.equals(source) && !nodeName.equals(nodesInformation.getCurrentNodeName())) {
				destinations.add(nodeName);
				System.out.println(nodesInformation.getCurrentNodeName() +  " Destination: " + nodeName);
			}
		}
		
		
		
		return destinations;
	}
	
	
	
	
	public CUDReplica sendReplica(CUDReplica cudReplica) throws UnexpectedError {
		List<String> failedDeliveries = new ArrayList<String>();
		for (String destination : cudReplica.getDestination()) {
			try {
				replicateToNode(cudReplica.getRequest(), destination);
			} catch (Exception ex) {
				failedDeliveries.add(destination);
			}
		}
		
		
		if (cudReplica.getDestination().size() > 0 && failedDeliveries.size() == cudReplica.getDestination().size())
			throw new UnexpectedError("No replica sent");
		
		
		cudReplica.setDestination(failedDeliveries);
		return cudReplica;
	}
	
}
