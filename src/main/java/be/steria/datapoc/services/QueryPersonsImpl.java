package be.steria.datapoc.services;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import be.steria.datapoc.dao.PersonDao;
import be.steria.datapoc.model.Person;


@SuppressWarnings("restriction")
@WebService
public class QueryPersonsImpl implements QueryPersons{

	private PersonDao personDao;
	
	public List<Person> getAllPersons() {
		return personDao.getListPersons();
	}

	public Person getPersonById(@WebParam(name = "personId") String personId) {
		return personDao.getPersonById(personId);
	}

	public PersonDao getPersonDao() {
		return personDao;
	}

	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}

}
