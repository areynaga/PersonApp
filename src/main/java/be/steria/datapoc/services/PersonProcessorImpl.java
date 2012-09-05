package be.steria.datapoc.services;

import org.springframework.beans.factory.annotation.Autowired;

import be.steria.datapoc.EventLogger.model.NodeEvent;
import be.steria.datapoc.EventLogger.model.NodeEventType;
import be.steria.datapoc.dao.DuplicatedPersonId;
import be.steria.datapoc.dao.PersonDao;
import be.steria.datapoc.dao.PersonIdNotFound;
import be.steria.datapoc.model.CUDRequest;
import be.steria.datapoc.model.CreatePersonRequest;
import be.steria.datapoc.model.DeletePersonRequest;
import be.steria.datapoc.model.ServiceResponse;
import be.steria.datapoc.model.UpdatePersonRequest;

public class PersonProcessorImpl implements PersonProcessor {
	
	@Autowired
	private NodeLoggerImpl nodeLogger;
	
	private String logEvents;
	
	private NodesInformation nodesInformation;
	
	private PersonDao personDao;
	
	public CUDRequest processRequest(CUDRequest cudRequest ) throws DuplicatedPersonId, PersonIdNotFound {
		if (cudRequest instanceof CreatePersonRequest)
			return createPerson((CreatePersonRequest) cudRequest);
		else if (cudRequest instanceof UpdatePersonRequest)
			return updatePerson((UpdatePersonRequest) cudRequest);
		else if (cudRequest instanceof DeletePersonRequest)
			return deletePerson((DeletePersonRequest) cudRequest);
		else 
			throw new RuntimeException("Operation not known");
	}
	
	
	private CreatePersonRequest createPerson(CreatePersonRequest createPersonRequest) throws DuplicatedPersonId {
		if (logEvents.equals("true"))
			nodeLogger.registerEvent(new NodeEvent(nodesInformation.getCurrentNodeId(), NodeEventType.CREATION, 
						"PersonId: " + createPersonRequest.getPerson().getIdPerson()));
		System.out.println(nodesInformation.getCurrentNodeId() + ": Creating person with id: " + createPersonRequest.getPerson().getIdPerson());
		personDao.createPerson(createPersonRequest.getPerson());
		return createPersonRequest;
	}
	
	
	private UpdatePersonRequest updatePerson(UpdatePersonRequest updatePersonRequest) throws PersonIdNotFound {
		if (logEvents.equals("true"))
			nodeLogger.registerEvent(new NodeEvent(nodesInformation.getCurrentNodeId(), NodeEventType.UPDATE, 
				"Personid: " + updatePersonRequest.getPerson().getIdPerson()));

		System.out.println(nodesInformation.getCurrentNodeId() + ": Updating person with id: " + updatePersonRequest.getPerson().getIdPerson());
		personDao.updatePerson(updatePersonRequest.getPerson());
		return updatePersonRequest;
	}
	
	
	private DeletePersonRequest deletePerson(DeletePersonRequest deletePersonRequest) throws PersonIdNotFound {
		if (logEvents.equals("true"))
			nodeLogger.registerEvent(new NodeEvent(nodesInformation.getCurrentNodeId(), NodeEventType.DELETE, 
				"Personid: " + deletePersonRequest.getIdPerson()));
		System.out.println(nodesInformation.getCurrentNodeId() + ": Deleting person with id: " + deletePersonRequest.getIdPerson());
		personDao.deletePerson(deletePersonRequest.getIdPerson());
		return deletePersonRequest;
	}
	
	public ServiceResponse createResponse() {
		ServiceResponse response = new ServiceResponse();
		response.setIdResponse("1");
		response.setMessage("OK");
		return response;
	}



	public PersonDao getPersonDao() {
		return personDao;
	}

	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}

	public NodesInformation getNodesInformation() {
		return nodesInformation;
	}

	public void setNodesInformation(NodesInformation nodesInformation) {
		this.nodesInformation = nodesInformation;
	}


	public String getLogEvents() {
		return logEvents;
	}


	public void setLogEvents(String logEvents) {
		this.logEvents = logEvents;
	}
}
