package be.steria.datapoc.services;

import be.steria.datapoc.dao.DuplicatedPersonId;
import be.steria.datapoc.dao.PersonIdNotFound;
import be.steria.datapoc.model.CUDRequest;
import be.steria.datapoc.model.ServiceResponse;

public interface PersonProcessor {
	public ServiceResponse createResponse();
	public CUDRequest processRequest(CUDRequest cudRequest ) throws DuplicatedPersonId, PersonIdNotFound;
}
