package be.steria.datapoc.services;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.camel.CamelContext;
import org.apache.camel.CamelContextAware;

import be.steria.datapoc.services.routes.MainServiceRoute;

@SuppressWarnings("restriction")
@WebService
public class NodeControlerImpl implements NodeControler, CamelContextAware {

	private MainServiceRoute serviceRoute;
	
	private int currentNodeId;
	
	private static CamelContext ctx;
	
	public void setCurrentNodeId(@WebParam(name = "nodeId") int nodeId) {
		this.currentNodeId = nodeId;

	}

	public int getCurrentNodeId() {
		return currentNodeId;
	}

	public void startCamel() throws Exception {
		 ctx.addRoutes(serviceRoute);
		 ctx.start();
	}

	

	public void setCamelContext(CamelContext camelContext) {
		ctx = camelContext;
	}

	public CamelContext getCamelContext() {
		// TODO Auto-generated method stub
		return null;
	}

	public MainServiceRoute getServiceRoute() {
		return serviceRoute;
	}

	public void setServiceRoute(MainServiceRoute serviceRoute) {
		this.serviceRoute = serviceRoute;
	}

}
