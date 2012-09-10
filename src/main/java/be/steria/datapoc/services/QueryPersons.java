package be.steria.datapoc.services;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import be.steria.datapoc.model.Person;

@SuppressWarnings("restriction")
@WebService
public interface QueryPersons {
	public List<Person> getAllPersons();
	public Person getPersonById(@WebParam(name="personId")String personId);
}
