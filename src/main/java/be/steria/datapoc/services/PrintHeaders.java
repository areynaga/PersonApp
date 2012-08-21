package be.steria.datapoc.services;

import org.apache.camel.Exchange;

public class PrintHeaders {
	public void process(Exchange exchange) throws Exception {
        System.out.println(
        exchange.getIn().getHeader(org.apache.camel.component.cxf.common.
        		message.CxfConstants.ADDRESS));

        System.out.println(
                exchange.getIn().getHeader(org.apache.camel.component.cxf.common.
                		message.CxfConstants.SERVICE_NAME));
                
    }
}
