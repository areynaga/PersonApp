package be.steria.datapoc.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import be.steria.datapoc.model.Address;
import be.steria.datapoc.model.Person;

public class PersonDaoImpl implements PersonDao {

	protected EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	
	public void createPerson(Person person) throws DuplicatedPersonId {
		if (getPersonById(person.getIdPerson()) == null) 
			getEntityManager().persist(person);
		else 
			throw new DuplicatedPersonId(person.getIdPerson());
	}

	
	public void updatePerson(Person person) throws PersonIdNotFound {
		Person storedPerson = getPersonById(person.getIdPerson()); 

		if (storedPerson != null ) {
			for (Address adr : storedPerson.getAddress())
				getEntityManager().remove(adr);

			getEntityManager().merge(person);
		} else {
			throw new PersonIdNotFound(person.getIdPerson());
		}
	}

	
	public void deletePerson(String personId) throws PersonIdNotFound {
		Person storedPerson = getPersonById(personId); 

		if (storedPerson != null ) 
			getEntityManager().remove(storedPerson);
		else 
			throw new PersonIdNotFound(personId);
		
	}

	@SuppressWarnings("unchecked")
	public List<Person> getListPersons() {
		Query query = getEntityManager().createQuery("select o from Person o");
		
		return query.getResultList();
	}

	public Person getPersonById(String personId) {
		return getEntityManager().find(Person.class, personId);
	}

}
