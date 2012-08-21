package be.steria.datapoc.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cudReplica", propOrder = {
    "request",
    "destination"
})
public class CUDReplica {
	private CUDRequest request;
	private List<String> destination;
	
	public CUDReplica() {
		this.destination = new ArrayList<String>();
	}
	
	
	public CUDRequest getRequest() {
		return request;
	}
	public void setRequest(CUDRequest request) {
		this.request = request;
	}
	
	public List<String> getDestination() {
		if (destination == null) {
			destination = new ArrayList<String>();
        }
		
		return destination;
	}
	public void setDestination(List<String> destination) {
		this.destination = destination;
	}
	
	
}
