package be.steria.datapoc.services;

import java.util.List;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;



public class NodesInformationImpl implements NodesInformation {

	@Autowired
	private NodeControler nodeControler;
	
	public NodeControler getNodeControler() {
		return nodeControler;
	}


	public void setNodeControler(NodeControler nodeControler) {
		this.nodeControler = nodeControler;
	}


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
		
		return String.valueOf(nodeControler.getCurrentNodeId());
	}

	

}
