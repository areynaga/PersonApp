package be.steria.datapoc.services;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import be.steria.datapoc.EventLogger.EventLogger;
import be.steria.datapoc.EventLogger.model.NodeEvent;

public class NodeLoggerImpl implements NodeLogger {

	private String loggerAddress;
	
	public void registerEvent(NodeEvent event) throws RuntimeException {
		
		JaxWsProxyFactoryBean clientFactory = new JaxWsProxyFactoryBean();
        clientFactory.setAddress(loggerAddress);
		clientFactory.setServiceClass(EventLogger.class);
		EventLogger eventLogger = (EventLogger) clientFactory.create();
		eventLogger.logEvent(event);
	}

	public String getLoggerAddress() {
		return loggerAddress;
	}

	public void setLoggerAddress(String loggerAddress) {
		this.loggerAddress = loggerAddress;
	}

}
