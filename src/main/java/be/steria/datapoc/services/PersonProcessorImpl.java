package be.steria.datapoc.services;

import be.steria.datapoc.dao.DuplicatedPersonId;
import be.steria.datapoc.dao.PersonDao;
import be.steria.datapoc.dao.PersonIdNotFound;
import be.steria.datapoc.model.CUDRequest;
import be.steria.datapoc.model.CreatePersonRequest;
import be.steria.datapoc.model.DeletePersonRequest;
import be.steria.datapoc.model.ServiceResponse;
import be.steria.datapoc.model.UpdatePersonRequest;

public class PersonProcessorImpl implements PersonProcessor {
	
	private String serverName;
	
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
		System.out.println(serverName + ": Creating person with id: " + createPersonRequest.getPerson().getIdPerson());
		personDao.createPerson(createPersonRequest.getPerson());
		return createPersonRequest;
	}
	
	private UpdatePersonRequest updatePerson(UpdatePersonRequest updatePersonRequest) throws PersonIdNotFound {
		System.out.println(serverName + ": Updating person with id: " + updatePersonRequest.getPerson().getIdPerson());
		personDao.updatePerson(updatePersonRequest.getPerson());
		return updatePersonRequest;
	}
	
	private DeletePersonRequest deletePerson(DeletePersonRequest deletePersonRequest) throws PersonIdNotFound {
		System.out.println(serverName + ": Deleting person with id: " + deletePersonRequest.getIdPerson());
		personDao.deletePerson(deletePersonRequest.getIdPerson());
		return deletePersonRequest;
	}
	
	public ServiceResponse createResponse() {
		ServiceResponse response = new ServiceResponse();
		response.setIdResponse("1");
		response.setMessage("OK");
		return response;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public PersonDao getPersonDao() {
		return personDao;
	}

	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}
}
