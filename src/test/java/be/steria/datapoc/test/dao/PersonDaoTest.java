package be.steria.datapoc.test.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;


import be.steria.datapoc.dao.DuplicatedPersonId;
import be.steria.datapoc.dao.PersonDao;
import be.steria.datapoc.dao.PersonIdNotFound;
import be.steria.datapoc.model.Address;
import be.steria.datapoc.model.Person;

@ContextConfiguration(locations = { "file:src/test/resources/applicationContext-test.xml" })
@TransactionConfiguration(transactionManager = "jtaTransactionManager", defaultRollback = true)
@Transactional
public class PersonDaoTest extends AbstractTransactionalJUnit4SpringContextTests  {
	@Autowired
    private PersonDao personDao;
 
	protected EntityManager entityManager;
	
	private List<Person> testPersons;
	
	private Connection connection; 
	

	public EntityManager getEntityManager() {
		return entityManager;
	}
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Before
	public void before() {
		try {
			Class.forName("org.hsqldb.jdbcDriver");
		
			connection = DriverManager.getConnection("jdbc:hsqldb:mem:inMemoryPersonDB","SA", "");
			connection.setAutoCommit(false);
			
			testPersons = new ArrayList<Person>();
			
			Person person = new Person();
	        person.setIdPerson("ABC");
	        person.setFirstName("Pedro");
	        person.setLastName("Perez");
	        person.setBirthDate(new java.util.Date());
	        
	        Address address = new Address();
	        address.setCity("La Paz");
	        address.setCountry("Bolivia");
	        address.setNumber(92);
	        address.setStreet("Avaroa");
	    	person.getAddress().add(address);
	    	
	    	testPersons.add(person);
	    	
	    	person = new Person();
	        person.setIdPerson("XYZ");
	        person.setFirstName("John");
	        person.setLastName("Wayne");
	        person.setBirthDate(new java.util.Date());
	        
	        address = new Address();
	        address.setCity("New York");
	        address.setCountry("USA");
	        address.setNumber(5644);
	        address.setStreet("Main");
	    	person.getAddress().add(address);
	    	
	    	address = new Address();
	        address.setCity("Chicago");
	        address.setCountry("USA");
	        address.setNumber(112222);
	        address.setStreet("First");
	    	person.getAddress().add(address);
	    	
	    	testPersons.add(person);
	    	
	    	
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	
	@After
	public void after() {
		
		if (connection != null) {try {connection.close();}catch(SQLException sx){}}
	}
	
    @Test
    public void testCreate() {
        try {
        	createPerson(testPersons.get(0));
        	createPerson(testPersons.get(1));
			entityManager.flush();
			
        	for (Person person : testPersons)
    			verifyPersonInDB(person);
		} catch (Exception e) {
			e.printStackTrace();
			org.junit.Assert.fail(e.toString());
		}
    	
        
    }
    
    @Transactional
    private void createPerson(Person person) throws DuplicatedPersonId {
    	personDao.createPerson(person);
    }
    
    @Transactional
    private void updatePerson(Person person) throws PersonIdNotFound {
    	personDao.updatePerson(person);
    }
    
    @Transactional
    private void deletePerson(String personId) throws PersonIdNotFound {
    	personDao.deletePerson(personId);
    }
    
    
    @Test(expected=PersonIdNotFound.class)
    public void testUpdPersonNotFound() throws PersonIdNotFound {
    	try {
    		createPerson(testPersons.get(0));
        	createPerson(testPersons.get(1));
			
    	} catch (DuplicatedPersonId dip) {
    		Assert.fail(dip.getMessage());
    	}
    	
		entityManager.flush();
    	
    	Person updPerson ;
    	updPerson = new Person();
    	updPerson.setIdPerson("UNK");
    	updatePerson(updPerson);
    	
    }
    
    
    @Test(expected=DuplicatedPersonId.class)
    public void testCreateDuplicateId() throws DuplicatedPersonId {
    	try {
    		createPerson(testPersons.get(0));
	    	
    	} catch (DuplicatedPersonId dip) {
    		Assert.fail(dip.getMessage());
    	}
    	
    	Person newPerson ;
    	newPerson = new Person();
    	newPerson.setIdPerson("ABC");
    	createPerson(newPerson);
    	
    }
    
    
    @Test
    public void testUpdate() {
    	try {
    		createPerson(testPersons.get(0));
	    	
		} catch (DuplicatedPersonId dip) {
    		Assert.fail(dip.getMessage());
    	}
    	
    	
    	Person updPerson = new Person();
        updPerson.setIdPerson("ABC");
        updPerson.setFirstName("Pedro");
        updPerson.setLastName("Perez Gomez");
        updPerson.setBirthDate(new java.util.Date());
        
        Address address = new Address();
        address.setCity("La Paz");
        address.setCountry("Bolivia");
        address.setNumber(100);
        address.setStreet("Avaroa");
    	updPerson.getAddress().add(address);
    	
    	address = new Address();
        address.setCity("Ottignies");
        address.setCountry("Belgium");
        address.setNumber(43);
        address.setStreet("Combatants");
    	updPerson.getAddress().add(address);
    	
    	try {
    		updatePerson(updPerson);
    		
    		entityManager.flush();
    		verifyPersonInDB(updPerson);
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		Assert.fail(ex.getMessage());
    	}
    	
    	
    	
    }
    
    @Test
    public void testDelete() {
    	try {
    		createPerson(testPersons.get(0));
    		createPerson(testPersons.get(1));
    		
	    	
			deletePerson(testPersons.get(0).getIdPerson());
			
			entityManager.flush();
			
			verifyNoPersonInDB(testPersons.get(0).getIdPerson());
			verifyPersonInDB(testPersons.get(1));
			
    	} catch (Exception ex) {
    		Assert.fail(ex.getMessage());
    	}
    	
		
		
		
    }
    
    private void verifyNoPersonInDB(String personId) throws SQLException {
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	try {
    		ps = connection.prepareStatement("select count(*) from person where id_person = ?");
    		ps.setString(1, personId);
    		rs = ps.executeQuery();
    		
    		rs.next();
    		Assert.assertTrue(rs.getInt(1) == 0);
    	
    	} finally {
    		if (rs!=null){try {rs.close();}catch(SQLException sx){}}
    		if (ps!=null){try {ps.close();}catch(SQLException sx){}}
    	}
    }
   
    
    private void verifyPersonInDB(Person person) throws SQLException {
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	int c = 0;
    	try {
    		ps = connection.prepareStatement("select first_name, last_name from person where id_person = ?");
    		ps.setString(1, person.getIdPerson());
    		rs = ps.executeQuery();
    		
    		Assert.assertTrue(rs.next());
    		System.out.println(rs.getString(1) + ":" + rs.getString(2));
    		
    		Assert.assertTrue(rs.getString(1).equals(person.getFirstName()) &&
    				rs.getString(2).equals(person.getLastName()));
    		rs.close();
    		ps.close();
    		
    		ps = connection.prepareStatement("select street, number, city from address " +
    					"where id_address in (select id_address from person_address where " +
    					"id_person = ?)");
    		ps.setString(1, person.getIdPerson());
    		rs = ps.executeQuery();
    		
    		while (rs.next()) {
    			Assert.assertTrue(rs.getString(1).equals(person.getAddress().get(c).getStreet()) &&
        				rs.getInt(2) == person.getAddress().get(c).getNumber() && 
        				rs.getString(3).equals(person.getAddress().get(c).getCity()));
    			c++;
    		}
    		
    		Assert.assertTrue((c)==person.getAddress().size());
    		
    		
    		rs.close();
    		
    	} finally {
    		if (rs!=null){try {rs.close();}catch(SQLException sx){}}
    		if (ps!=null){try {ps.close();}catch(SQLException sx){}}
    	}
    	
    	
    	
    	
    }
    
    
}