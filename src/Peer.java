public class Peer {
	
	// Return codes
	// This is a defined set of return codes.
	// Using enum here is a bad idea, unless you spell out the values, since it is
	// shared with code you don't write.

	final int errOK             =  0; // Everything good
	final int errUnknownWarning =  1; // Unknown warning
	final int errUnknownFatal   = -2; // Unknown error
	final int errCannotConnect  = -3; // Cannot connect to anything; fatal error
	final int errNoPeersFound   = -4; // Cannot find any peer (e.g., no peers in a peer file); fatal
	final int errPeerNotFound   =  5; // Cannot find some peer; warning, since others may be connectable
	
	
    Peers peers;
	
    private enum State
    {
        connected, disconnected, unknown
    }
    
	public int insert(String filename)
	{
		//Take string filename
		//Add to local peer
		
		//Store with local files
		
		//Push file out
		
		//If files DNE, return negative
		//Return 0 if successful
		return 0;
	}
	
	public int query(Status status)
	{
		//Populate parameter status with details for each file
		//1. The fraction of the file that is available locally
		//2. The fraction of the file that is available in the system
		//3. The least replication level
		//4. The weighted least-replication level
		return 0;
	}
	
	public int join(Peers peers)
	{
		//Container is Peers
		//Attempt to join set
		//Push all local files
		//Pull all files that don't exist in set
		return 0;
	}
	
	public int leave()
	{
		//Leave set of peers
		//Close socket connections
		//Inform peers that it is leaving
		//Ideally, unique blocks are pushed before disconnection
		//Only push if the number of blocks is low
		return 0;
	}
}
