package be.steria.datapoc.dao;

public class PersonIdNotFound extends Exception {
	private String personId;
	
	public PersonIdNotFound(String personId) {
		super();
		this.personId = personId; 
	}

	public String getPersonId() {
		return personId;
	}
}
