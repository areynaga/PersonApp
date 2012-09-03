package be.steria.datapoc.services;

import java.util.List;

public interface NodesInformation {
	public List<String> getLocalNodeList();
	public String getAddress(String nodeId);
	public String getCentralNodeId();
	public String getCurrentNodeId();
	
}
