package be.steria.datapoc.services;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;

import be.steria.datapoc.model.CUDRequest;



public class ServerSourcePredicate implements Predicate {

	public boolean matches(Exchange exchange) {
		return (exchange.getIn().getBody(CUDRequest.class).getSource() != null 
				&& !exchange.getIn().getBody(CUDRequest.class).getSource().equals("client"));
	}
	
}
