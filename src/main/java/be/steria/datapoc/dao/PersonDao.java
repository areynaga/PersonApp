package be.steria.datapoc.dao;

import java.util.List;

import be.steria.datapoc.model.Person;

public interface PersonDao {
	public void createPerson(Person person) throws DuplicatedPersonId;
	
	public void updatePerson(Person person) throws PersonIdNotFound;
	
	public void deletePerson(String personId) throws PersonIdNotFound;
	
	public List<Person> getListPersons();
	
	public Person getPersonById(String personId);
}
