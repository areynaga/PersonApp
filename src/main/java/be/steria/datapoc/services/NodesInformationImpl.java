package be.steria.datapoc.services;

import java.util.List;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;



/**
 * @author areynaga
 * Static nodes information implementation; in this scenario there are two servers:
 * SERVER_A, SERVER_B, the cetral server is SERVER_A
 */
public class NodesInformationImpl implements NodesInformation {

	private String currentNodeId;
	private String nodeInfoAddress;
	private JaxWsProxyFactoryBean clientFactory;
	
	public String getNodeInfoAddress() {
		return nodeInfoAddress;
	}


	public void setNodeInfoAddress(String nodeInfoAddress) {
		this.nodeInfoAddress = nodeInfoAddress;
	}


	public NodesInformationImpl() {
		
	}
	

	public List<String> getLocalNodeList() {
		clientFactory = new JaxWsProxyFactoryBean();
		clientFactory.setAddress(nodeInfoAddress);
		clientFactory.setServiceClass(NodeInfoService.class);
		return ((NodeInfoService)clientFactory.create()).getNodeList();
	}

	public String getAddress(String nodeId) {
		clientFactory = new JaxWsProxyFactoryBean();
		clientFactory.setAddress(nodeInfoAddress);
		clientFactory.setServiceClass(NodeInfoService.class);
		return ((NodeInfoService)clientFactory.create()).getAddress(nodeId);
	}

	public String getCentralNodeId() {
		clientFactory = new JaxWsProxyFactoryBean();
		clientFactory.setAddress(nodeInfoAddress);
		clientFactory.setServiceClass(NodeInfoService.class);
		return ((NodeInfoService)clientFactory.create()).getCentralNodeId();
	}

	public String getCurrentNodeId() {
		return this.currentNodeId;
	}

	public void setCurrentNodeId(String currentNodeId) {
		this.currentNodeId = currentNodeId;
	}

}
