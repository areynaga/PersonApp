package be.steria.datapoc.services;

import java.util.ArrayList;
import java.util.List;



/**
 * @author areynaga
 * Static nodes information implementation; in this scenario there are three servers:
 * SERVER_A, SERVER_B, SERVER_C, the cetral server is SERVER_A
 */
public class LocalNodesInformation implements NodesInformation {

	private String currentNodeName;
	
	public List<String> getLocalNodeList() {
		List<String> valRet = new ArrayList<String>();
		valRet.add("SERVER_B");
		valRet.add("SERVER_C");
		return valRet;
	}

	public String getAddress(String nodeName) {
		String address = null;
		if (nodeName.equals("SERVER_A")) 
			address = "http://localhost:8080/PersonApp_A/services/PersonService";
		else if (nodeName.equals("SERVER_B"))
			address = "http://localhost:8080/PersonApp_B/services/PersonService";
		else if (nodeName.equals("SERVER_C"))
			address = "http://localhost:8080/PersonApp_C/services/PersonService";
		
		return address;
	}

	public String getCentralNodeName() {
		
		return "SERVER_A";
	}

	public String getCurrentNodeName() {
		return this.currentNodeName;
	}

	public void setCurrentNodeName(String currentNodeName) {
		this.currentNodeName = currentNodeName;
	}

}
