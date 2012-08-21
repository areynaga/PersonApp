package be.steria.datapoc.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;





@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement()
@XmlSeeAlso({CreatePersonRequest.class,UpdatePersonRequest.class, DeletePersonRequest.class})

public abstract class CUDRequest {
	
	public abstract void setSource(String source);
	public abstract String getSource();
	
	
}
