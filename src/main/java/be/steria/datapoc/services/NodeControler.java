package be.steria.datapoc.services;

import javax.jws.WebParam;
import javax.jws.WebService;


@SuppressWarnings("restriction")
@WebService
public interface NodeControler {
	public void setCurrentNodeId(@WebParam(name="nodeId")int nodeId);
	public int getCurrentNodeId();
	public void startCamel() throws Exception;
}
