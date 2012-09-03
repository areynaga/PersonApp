package be.steria.datapoc.services;

import be.steria.datapoc.EventLogger.model.NodeEvent;

public interface NodeLogger {
	public void registerEvent(NodeEvent event) throws RuntimeException;
}
