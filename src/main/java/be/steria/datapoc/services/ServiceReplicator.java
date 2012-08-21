package be.steria.datapoc.services;



import be.steria.datapoc.model.CUDReplica;
import be.steria.datapoc.model.CUDRequest;

public interface ServiceReplicator {
	public CUDReplica createReplicaForBroadcast(CUDRequest cudRequest);
	public CUDReplica createReplicaForCentral(CUDRequest cudRequest);
	public CUDReplica sendReplica(CUDReplica cudReplica) throws UnexpectedError;
}
