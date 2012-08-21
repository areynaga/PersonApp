package be.steria.datapoc.dao;

public class DuplicatedPersonId extends Exception {

	private String personId;
	
	public DuplicatedPersonId(String personId) {
		super();
		this.personId = personId; 
	}

	public String getPersonId() {
		return personId;
	}

	
}
