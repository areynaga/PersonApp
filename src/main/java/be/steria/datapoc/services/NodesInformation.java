package be.steria.datapoc.services;

import java.util.List;

public interface NodesInformation {
	public List<String> getLocalNodeList();
	public String getAddress(String nodeName);
	public String getCentralNodeName();
	public String getCurrentNodeName();
	
}
